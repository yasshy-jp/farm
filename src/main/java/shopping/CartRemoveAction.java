package shopping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Item;
import tool.Action;
//カート内の商品削除に伴う各種合計データの更新
public class CartRemoveAction extends Action {
	@SuppressWarnings("unchecked")
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));  // 削除商品のID取得
		int totalPrice = (int)session.getAttribute("TOTALPRICE"); // 現在の合計金額取得
		int totalCount = (int)session.getAttribute("TOTALCOUNT"); // 現在の合計個数取得
		int totalPrice_taxIn = (int)session.getAttribute("TOTALPRICE_TAXIN"); // 現在の税込合計金額取得
		List<Item> cart = (List<Item>)session.getAttribute("CART"); // 商品カート取得
		for (Item item : cart) {
			if (item.getProduct().getId() == id) {
				// カート内の合計個数と金額を再計算				 
				totalPrice -= item.getProduct().getPrice() * item.getCount();
				totalCount -= item.getCount();
				// 税込み合計金額を計算し、各更新情報をセッションスコープへ格納
				totalPrice_taxIn = (int)(totalPrice * 1.1);
				session.setAttribute("TOTALPRICE_TAXIN", totalPrice_taxIn);
				session.setAttribute("TOTALPRICE", totalPrice);
				session.setAttribute("TOTALCOUNT", totalCount);
				// カートから商品を削除
				cart.remove(item);
				break;
			}
		}
		return "cart.jsp";
	}
}
