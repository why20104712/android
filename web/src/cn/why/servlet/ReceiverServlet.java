package cn.why.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReceiverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReceiverServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String content = request.getParameter("content");
		String receiveTime = request.getParameter("receivetime");
		String senderNumber = request.getParameter("sendernumber");
		System.out.println("�������ݣ�"+ content);
		System.out.println("����ʱ�䣺"+ receiveTime);
		System.out.println("�����ߣ�"+ senderNumber);
	}

}
