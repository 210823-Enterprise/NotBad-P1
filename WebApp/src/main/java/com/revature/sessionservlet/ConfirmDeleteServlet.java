package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.util.HTMLFormatter;


public class ConfirmDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public ConfirmDeleteServlet() {
        super();
    }

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter out = response.getWriter();
		out.println(
				HTMLFormatter.format("P H2", "Are you sure you want to permanently delete your character?") +
				HTMLFormatter.createForm(HTMLFormatter.createButton("confirm"), "deleteserv") +
				HTMLFormatter.createForm(HTMLFormatter.createButton("cancel"), "loginserv")
			);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
