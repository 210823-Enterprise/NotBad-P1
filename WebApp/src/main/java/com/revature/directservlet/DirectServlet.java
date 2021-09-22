package com.revature.directservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Character;

public class DirectServlet extends HttpServlet {

	private static final long serialVersionUID = -7080315697799404034L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// This method will hand incoming HTTP request to the server and we can send back a request upon receiving a GET request
		response.setContentType("text/html");

		// PrintWriter is an object that prints text data to a character stream which we can call on the response object
		PrintWriter out = response.getWriter();
		out.println("<html><body><h1>The Servlet is talking directly to the client!</h1></body></html>");
		
		System.out.println("Context Example: " + getServletContext().getInitParameter("contextExample"));
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		response.setContentType("application/json");
	
		// hard coded character
		Character character = new Character("Ho Chi Minh", "male", "human", "Jack-of-all-trades", "Leadership");
		
		// send character object in the form of JSON to the browser
		response.getWriter().write(new ObjectMapper().writeValueAsString(character));		
		
	}
}
