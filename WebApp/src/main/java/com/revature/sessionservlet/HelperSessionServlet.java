package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.CharacterModel;
import com.revature.orm.ORM;
import com.revature.util.ServletConfiguration;

public class HelperSessionServlet extends HttpServlet {

	private static final long serialVersionUID = -7393813005277884981L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();

		// when we trigger a get request at this servlet, it will return the character
		// object that's saved to the session
		
		// 1. grab the session
		final HttpSession session = request.getSession();
		
		// 2. save the object retrieved from the session to a character object
		CharacterModel characterUsername = new CharacterModel();
		session.setAttribute("username", characterUsername.getUsername());
		
		final List<CharacterModel> characterList = ORM.getInstance().getAllObjectsFromDb(CharacterModel.class);
		
		// 3. after capturing the object, print the object's info to the screen
		final PrintWriter out = response.getWriter();
		
		
		characterUsername = ORM.getInstance().getObjectFromDb(CharacterModel.class, "username", request.getParameter("username"));
		System.out.println(characterUsername == null);
		
		if(request.getParameter("password").equals(characterUsername.getPassword())) {
			out.println("<html><style>\r\n"
					+ "\r\n"
					+ "@font-face\r\n"
					+ "{\r\n"
					+ "    font-family: 'warcraft';\r\n"
					+ "  src: url('warcraft.woff') format('woff'),\r\n"
					+ "\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "* {\r\n"
					+ "   font-family: warcraft;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "</style><title>Welcome Player</title><link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\"><body><div class=\"character-container\">");
			out.println("<h1>Welcome Player</h1>");
			out.println("<form method=POST action=playserv><p>Play</p><input type=submit value=Start></form>");
			out.println("<br>");
			out.println("		<form method=\"POST\" action=\"editserv\">\r\n"
					+ "			\r\n"
					+ "			<p>Edit Character</p>\r\n"
					+ "			<input type=\"text\" name=\"username\" placeholder=\"Edit your username\"/> <br />\r\n"
					+ "			\r\n"
					+ "			<label for=\"gender\">Gender:</label>\r\n"
					+ "			<select id=\"gender\" name=\"gender\">\r\n"
					+ "			<option value=\"male\">male</option>\r\n"
					+ "			<option value=\"female\">female</option>\r\n"
					+ "			</select>\r\n"
					+ "			\r\n"
					+ "			<label for=\"race\">Race:</label>\r\n"
					+ "			<select id=\"race\" name=\"race\">\r\n"
					+ "			<option value=\"human\">human</option>\r\n"
					+ "			<option value=\"elf\">elf</option>\r\n"
					+ "			<option value=\"orc\">orc</option>\r\n"
					+ "			<option value=\"dwarf\">dwarf</option>\r\n"
					+ "			<option value=\"fairy\">fairy</option>\r\n"
					+ "			<option value=\"undead\">undead</option>\r\n"
					+ "			</select>\r\n"
					+ "			\r\n"
					+ "			<label for=\"class\">Class:</label>\r\n"
					+ "			<select id=\"class\" name=\"class\">\r\n"
					+ "			<option value=\"fighter\">fighter</option>\r\n"
					+ "			<option value=\"assasian\">assasian</option>\r\n"
					+ "			<option value=\"mage\">mage</option>\r\n"
					+ "			<option value=\"tank\">tank</option>\r\n"
					+ "			<option value=\"healer\">healer</option>\r\n"
					+ "			<option value=\"marksman\">marksman</option>\r\n"
					+ "			</select>\r\n"
					+ "			\r\n"
					+ "			<label for=\"specialability\">Special Ability:</label>\r\n"
					+ "			<select id=\"specialability\" name=\"specialability\">\r\n"
					+ "			<option value=\"leader\">leader</option>\r\n"
					+ "			<option value=\"charisma\">charisma</option>\r\n"
					+ "			<option value=\"jack-of-all-trades\">jack-of-all-trades</option>\r\n"
					+ "			<option value=\"blacksmith\">blacksmith</option>\r\n"
					+ "			<option value=\"farmer\">farmer</option>\r\n"
					+ "			<option value=\"banker\">banker</option>\r\n"
					+ "			</select>\r\n"
					+ "			\r\n"
					+ "			<input type=\"submit\" value=\"Save Changes\">\r\n"
					+ "		\r\n"
					+ "		</form>");
			out.println("<br>");
			out.println("<form method=POST action=deleteserv><p>Delete Character :c</p><input type=submit value=Delete></form>");
			out.println("</body></html>");
		} else {
			out.println("Incorrect username/password. Please try again.");
		}
	}
		
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();
		
		
		
		doGet(request, response);
	}

}