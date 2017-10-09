package util.type;

public class SplitterType {

	public enum Delimiter {
		
		COLON(":"), COMMA(",");
		
		private String value;

		private Delimiter(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum TextQualifier {
		
		SINGLE_QOUTE("'"), DOUBLE_QUOTE("\""), NONE("");
		
		private String value;

		private TextQualifier(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
