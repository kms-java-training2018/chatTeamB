package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PushServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			request.setCharacterEncoding("utf-8");
			String text = request.getParameter("text");
			ServletContext servletContext = request.getServletContext();
			List<AsyncContext> contexts = (List<AsyncContext>) servletContext.getAttribute(PollingServlet.CONTEXT_NAME);
			if (contexts != null) {
				for (AsyncContext ac : contexts) {
					try {
						ac.getResponse().setCharacterEncoding("utf-8");
						PrintWriter writer = ac.getResponse().getWriter();
						writer.println(text);
						writer.close();
						ac.complete();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				contexts.clear();
			}
			out.println("ok");
		} finally {
			out.close();
		}
	}
}
