package shopping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Item;
import bean.Member;
import dao.MemberDAO;
import dao.ProceedsDAO;
import dao.PurchaseDAO;
import jp.pay.Payjp;
import jp.pay.model.Card;
import jp.pay.model.Charge;
import jp.pay.model.Customer;
import tool.Action;

public class PurchaseAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		String price = request.getParameter("price");  // 税込み合計金額
		String payjpToken = request.getParameter("payjp-token");  // カード情報入力後、生成されたトークン
		String registerCard = request.getParameter("registerCard");  // 顧客情報（カード）登録の有無
		List<Item> cart = (List<Item>)session.getAttribute("CART");  // カートオブジェクト
		Member member = (Member)session.getAttribute("MEMBER");  // 顧客オブジェクト
		
		/******* PAY.JPのAPIで支払い処理 *******/
		Payjp.apiKey = "***************************";
		Customer customer = null;
		Charge charge = null;
		Card card = null;
		String property = null; // 与信枠確保用のパラメータ変数
		String value = null; // 与信枠確保用のパラメータ変数
		
		/*** 与信枠の確保に必要なパラメータ取得 ***/
		// PAY.JP登録者
		if (!(member.getCustomer_id().equals("N/A"))) { 
			property = "customer";
			value = member.getCustomer_id();
		// PAY.JP「新規」登録者
		} else if (registerCard != null) {
			// 顧客作成
			Map<String, Object> customerParams = new HashMap<String, Object>();
			customerParams.put("card", payjpToken);
			try {
				customer = Customer.create(customerParams);
				System.out.println(customer);
			} catch (Exception e) {
	            e.printStackTrace();
			}
			property = "customer";
			value = customer.getId();
		// PAY.JP登録なし
		} else {
			property = "card";
			value = payjpToken; // 1回限りのトークン払い
		}
		
		/*** ① 与信枠の確保 ***/
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put(property, value); // 上記で取得したパラメータ設定
		chargeParams.put("amount", price);
		chargeParams.put("currency", "jpy");
		chargeParams.put("capture", "false"); // 支払い確定しない
		try {
			charge = Charge.create(chargeParams);
			System.out.println(charge);
        } catch (Exception e) {
        	e.printStackTrace();
        	return "credit_error.jsp";
        }		
		// PAY.JP「新規」登録情報は会員DBへ追加（顧客ID＆カードID）
		if (registerCard != null) {
			card = charge.getCard();
  			MemberDAO daoMem = new MemberDAO();
			int line = daoMem.r️egister(customer, card, member);
			if (line != 1) {
				return "/member/r️egister-iderror.jsp";
			}
		}
		
		/*** ② 購入明細DBへ追加 ***/
		PurchaseDAO daoPur = new PurchaseDAO();
		if (cart == null || !daoPur.insert(charge, cart)) {
			return "purchase-insert-error.jsp";
		}
		
		/*** ③ 支払い確定 ***/
		String ch_id = charge.getId(); // 課金IDを取得
		try {
			Charge ch = Charge.retrieve(ch_id);
			System.out.println(ch.capture()); // 確定処理と結果出力（支払いオブジェクト）
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		/*** ④ 売上DBへ追加 ***/
		ProceedsDAO daoPro = new ProceedsDAO();
		boolean result = daoPro.insert(charge, member, price);
		if (!result) {
			return "proceeds-insert-error.jsp";
		}
		
		session.removeAttribute("CART");
		if (registerCard != null) {
			return "purchase-out_register.jsp";
		}
		return "purchase-out_member.jsp";
	}

}