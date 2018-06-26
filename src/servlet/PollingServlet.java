package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PollingServlet extends HttpServlet {
	public static final String CONTEXT_NAME = "contexts";

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		ServletContext servletContext = req.getServletContext();
		List<AsyncContext> contexts = (List<AsyncContext>) servletContext.getAttribute(CONTEXT_NAME);
		if (contexts == null) {
			contexts = new ArrayList<AsyncContext>();
			servletContext.setAttribute(CONTEXT_NAME, contexts);
		}

		final AsyncContext ac = req.startAsync();
		contexts.add(ac);
	}
}
