package sec02.ex01;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/member/*")
public class MemberController extends HttpServlet{
		MemberDAO memberDAO;
		
		public void init() throws ServletException{
			memberDAO = new MemberDAO();
		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doHandle(req, resp);
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doHandle(req, resp);
		}
		
		private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			String nextPage = null;
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String action = request.getPathInfo(); // URL에서 요청명을 가져옵니다.
			System.out.println("action:" + action);
			
			if(action == null || action.equals("/listMembers.do")) {
				List membersList = memberDAO.listMembers();
				request.setAttribute("membersList", membersList);
				nextPage = "/test02/listMembers.jsp";
			}else if (action.equals("/addMember.do")) {
				String id =request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				MemberVO memberVO = new MemberVO(id, pwd, name, email);
				memberDAO.addMember(memberVO);
				nextPage = "/member/listMembers.do";
			}else if (action.equals("/memberForm.do")) {
				nextPage = "/test02/memberForm.jsp";
			}else {
				List membersList = memberDAO.listMembers();
				request.setAttribute("membersList", membersList);
				nextPage ="/test02/listMembers.jsp";
			}
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
		}
}
