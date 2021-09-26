package com.revature.sessionservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ConfirmDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public ConfirmDeleteServlet() {
        super();
    }

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("confirm_delete.html");
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
