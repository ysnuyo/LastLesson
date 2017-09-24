package Original;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class InputServlet extends HttpServlet {
	protected void service(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException{
		req.setCharacterEncoding("UTF-8");

		String title =req.getParameter("title");
		String tag = req.getParameter("tag");
		String content = req.getParameter("content");


		DAO dao = new DAO();

		dao.regist(title,tag,content);//登録


		req.getRequestDispatcher("result.jsp").forward(req,res);
	}
}