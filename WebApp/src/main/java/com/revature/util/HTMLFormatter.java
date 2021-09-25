package com.revature.util;

public class HTMLFormatter {
	
	public String format(final String format, final String str) {
		String out = str;
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
		return out;
	}
	
	public String header3(final String str) {
		return "<h3>" + str + "</h3>";
	}
	
	public String header2(final String str) {
		return "<h2>" + str + "</h2>";
	}
	
	public String header1(final String str) {
		return "<h1>" + str + "</h1>";
	}
	
	public String newLine(final String str) {
		return str + "</br>";
	}
	
	public String italic(final String str) {
		return "<i>" + str + "</i>";
	}
	
	public String bold(final String str) {
		return "<b>" + str + "</b>";
	}
	
}
