package tool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.action")
//フロントコントローラのサーブレットを末尾が.actionで終わるURLに対応づける。
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// パスの取得と文字列処理でアクションを実行し、結果を出力するjspファイルにフォワードするメソッド。
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();

		try {
			// Fコントローラが呼び出されたパスを取得し、先頭一文字を除去（/chapter24/Login.action → chapter24/Login.action）			
			String path = request.getServletPath().substring(1);
			// 文字列置換で、パッケージ名.クラス名の形式にし、アクションのクラス名を得る（chapter24/Login.action → chapter24.LoginAction）
			String name = path.replace(".a", "A").replace('/', '.');
			// アクションのクラス名からインスタンス（アクション）を生成。ポリモーフィズムで親クラスのActionとしてざっくり捉える。
			Action action = (Action)Class.forName(name).getDeclaredConstructor().newInstance();
			// 子クラスである〇〇Actionのインスタンスに対してexecute()メソッドを呼ぶ。戻り値はフォワード先のURL（jspファイル）
			String url = action.execute(request, response);
			// 指定されたフォワード先（jspファイル）へフォワード
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		//doGetもdoPostを呼ぶ。よってGETリクエストもPOSTリクエストも同じ処理を行う。
	}
}
