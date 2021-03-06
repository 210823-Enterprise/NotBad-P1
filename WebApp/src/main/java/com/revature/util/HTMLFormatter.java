package com.revature.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class HTMLFormatter {
	
	public static String format(final String format, final String str) {
		String out = str;
		if(format.contains("by"))
			out = body(out);
		if(format.contains("h1"))
			out = header1(out);
		if(format.contains("h2"))
			out = header2(out);
		if(format.contains("h3"))
			out = header3(out);
		if(format.contains("br"))
			out = header1(out);
		if(format.contains("I"))
			out = italic(out);
		if(format.contains("B"))
			out = bold(out);
		if(format.contains("P"))
			out = paragraph(out);
		return out;
	}
	
	public static String body(final String str) {
		return "<html><body>" + str + "</body></html>";
	}
	
	public static String header3(final String str) {
		return "<h3>" + str + "</h3>";
	}
	
	public static String header2(final String str) {
		return "<h2>" + str + "</h2>";
	}
	
	public static String header1(final String str) {
		return "<h1>" + str + "</h1>";
	}
	
	public static String newLine(final String str) {
		return str + "</br>";
	}
	
	public static String italic(final String str) {
		return "<i>" + str + "</i>";
	}
	
	public static String bold(final String str) {
		return "<b>" + str + "</b>";
	}
	
	public static String paragraph(final String str) {
		return "<p>" + str + "</p>";
	}
	
	public static String createForm(final String content, final String postServlet) {
		return String.format("<form method=POST action=%s>%s</form>", postServlet, content);
	}
	
	public static String createButton(final String label) {
		return String.format("<input type=submit value=%s>", label);
	}
	
	public static void writeFile(final String html, final PrintWriter writer, final String[] replace) {
		InputStream app = null;
		if(app == null) {
			try {
				app = new FileInputStream(new File("src/main/webapp/" + html));
			} catch (final FileNotFoundException e) {}
		}
		
		if(app == null) {
			try {
				app = new FileInputStream(new File("webapps/NotBadServlets/" + html));
			} catch (final FileNotFoundException e) {}
		}
		
		if(app == null) {
			writer.println("File " + html + " not found at " + new File("").getAbsolutePath());
		}
		
		try(final InputStreamReader reader = new InputStreamReader(app,"UTF-8"); final BufferedReader buffReader = new BufferedReader(reader)) {
			int index = 0;
			String line = "";
			while(line != null) {
				writer.write(line);
				line = buffReader.readLine();
				
				while(replace != null && index < replace.length && line.contains("%s")) {
					line = line.replaceFirst("%s", replace[index]);
					index += 1;
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static String createRadioButtion(final String str) {
		final String modified = str.toLowerCase().replaceAll(" ", "_").replace("*", "");
		return String.format("<label>%s</label><input type=radio value=\"%s\" name=\"action\">", str, modified, modified);
	}
	
}
