package util.mail.domain;

public class MailErrorType {
	public enum Message {
		AUTH_FAIL("Authentication Fail.")
		, CONNECT_FAIL("Can not connect Mail Server.");

		private String value;

		private Message(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
