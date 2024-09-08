package net.gwanghwa.study.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/mainProcess.do")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
        System.out.println("==> MainServlet 생성");
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
        System.out.println("==> init 생성");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        System.out.println("==> doGet 생성");
        
        response.setContentType("text/html;charset=utf-8");
		response.getWriter().append("").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
        System.out.println("==> doPost 생성");
	}

	
}
