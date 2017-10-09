package util.string;

public enum StringDelimeter {
	
	DOT("."), BLANK(" "), EMPTY(""), STAR("*"), COMMA(","), COLON(":")
	, SHARP("#"), BACKSLASH("/"), SLASH("\\"), PIPE("|") 
	, MINUS("-"), PLUS("+"), EQUAL("="), UNDERLINE("_")
	;

	private String value;

	private StringDelimeter(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
