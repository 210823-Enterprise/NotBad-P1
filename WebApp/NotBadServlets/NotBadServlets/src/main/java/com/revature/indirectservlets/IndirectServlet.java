package com.revature.indirectservlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndirectServlet extends HttpServlet{

	private static final long serialVersionUID = 1925216559092012843L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// let's redirect the user to some spearate static resource

		// When you use sendRedirect, a new request is created and redirected to a
		// separate resource
		response.sendRedirect("http://localhost:8080/HelloServlets/dirserv");

		// YOU CAN UNCOMMENT THE SOURCE BELOW!

		// response.sendRedirect("http://localhost:8080/HelloServlets/somepage.html");
		// // we can redirect the user to a separate page.
		
		System.out.println("Config Example: " + getServletConfig().getInitParameter("configExample"));
	}

	// What is the difference between sendRedirect() and forward() form the
	// RequestDispatcher?

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rdis = request.getRequestDispatcher("/dirserv");

		rdis.forward(request, response);

	}

	/*
	 * Here is a list of major differences between servlet forward and
	 * sendRedirect()
	 * 
	 * sendRedirect():
	 * 
	 * The request is redirected to a different resource The client will see the URL
	 * change after the redirect A totally new request is created Redirect is
	 * normally used within Post/Redirect/Get web development pattern
	 * 
	 * 
	 * 
	 * forward():
	 * 
	 * The request will be further processed on the server side The client isn't
	 * impacted by forward, URL in a browser stays the same Request and response
	 * objects will remain the same object after forwarding. Request-scope objects
	 * will be still available
	 */

	// destroy()...

}

