package com.revature.minigame;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Minigame {
	
	public static void generateResponse(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final HttpSession session = request.getSession();
		final PrintWriter out = response.getWriter();
		
		out.println("<html><body>");
		
		out.println("<head><style>body  {background-image: url(goblin.gif);background-repeat: no-repeat;background-position: center;}</style></head>");
		
		out.println("<form>");
		out.println("<h4><label for=choice>A wild goblin appeared!</label></h4>");
		out.println("<br><br>");
		out.println("<label for=Attack>Attack</label><input type=radio value=Attack id=Attack name=choice");
		out.println("<br>");
		out.println("<label for=Run>Run</label><input type=radio value=Run id=Run name=choice");
		out.println("<br>");
		out.println("<input type=submit for=choice value=Choose action>");
		out.println("</form>");
		out.println("</body></html>");
	}
}
