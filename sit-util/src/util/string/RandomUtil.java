/**
 * Manage String
 * @author: prasit.s
 * @version: 1.0
 **/

package util.string;
import java.util.Random;

/**
 * Random String
 *
 */
public class RandomUtil {
	private static final String PASSWORD_FOEMAT_NUMMERIC = "0123456789";
	private static final String PASSWORD_FOEMAT_ALPHABETIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String PASSWORD_FOEMAT_SPECIAL = "!#$%^&*?+_()[]|";//!@#$-_. POS
	//private static final String PASSWORD_FOEMAT_SPECIAL = "!#$%^&*?+_()[]|\\:\";<>,./-";

	private static final String PASSWORD_FOEMAT_ALPHABETIC_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String PASSWORD_FOEMAT_ALPHABETIC_LOWER = "abcdefghijklmnopqrstuvwxyz";


	/**
	 * Random String(แยก charector เล็กใหญ่).
	 * [Example : RandomUtil.getRandomString(15, true, true, true, true);]
	 *
	 * @param passwordLength : ความว้างตัวอักษร
	 * @param numeric : ข้อมูลตัวเลข
	 * @param alphabeticLower : ข้อมูลตัวอักษรตัวเล็ก
	 * @param alphabeticUpper : ข้อมูลตัวอักษรตัวใหญ่
	 * @param special : ตัวอักษรพิเศษ
	 * @return String : number format
	 *
	 */
	public static String getRandomString(int passwordLength, boolean nummeric,boolean alphabeticLower, boolean alphabeticUpper, boolean special) throws Exception {
		try{
			Random rand = new Random();
			int nummericSize = 0;
			int alphabeticSize = 0;
			int specialSize = 0;

			int alphabeticUpperSize = 0;
			int alphabeticLowerSize = 0;

			if(passwordLength == 0){return "";}
			if (nummeric && (alphabeticLower||alphabeticUpper) && special) {
				nummericSize = passwordLength / 3;
				specialSize = passwordLength / 3;
				alphabeticSize = passwordLength / 3 + passwordLength % 3;

				if(alphabeticUpper && alphabeticLower){
					alphabeticUpperSize = alphabeticSize / 2 + passwordLength % 2;
					alphabeticLowerSize = alphabeticSize / 2 ;
				}else if(alphabeticUpper){
					alphabeticUpperSize = alphabeticSize;
				}else{
					alphabeticLowerSize = alphabeticSize;
				}
			} else if (nummeric && (alphabeticLower||alphabeticUpper)) {
				nummericSize = passwordLength / 2;
				alphabeticSize = passwordLength / 2 + passwordLength % 2;

				if(alphabeticUpper && alphabeticLower){
					alphabeticUpperSize = alphabeticSize / 2 + passwordLength % 2;
					alphabeticLowerSize = alphabeticSize / 2 ;
					if(alphabeticSize / 2 >0){
						alphabeticLowerSize = alphabeticLowerSize+1;
					}


				}else if(alphabeticUpper){
					alphabeticUpperSize = alphabeticSize;
				}else{
					alphabeticLowerSize = alphabeticSize;
				}

			} else if (nummeric && special) {
				nummericSize = passwordLength / 2;
				specialSize = passwordLength / 2 + passwordLength % 2;
			} else if ((alphabeticLower||alphabeticUpper) && special) {
				alphabeticSize = passwordLength / 2;
				specialSize = passwordLength / 2 + passwordLength % 2;

				if(alphabeticUpper && alphabeticLower){
					alphabeticUpperSize = alphabeticSize / 2 + passwordLength % 2;
					alphabeticLowerSize = alphabeticSize / 2 ;
				}else if(alphabeticUpper){
					alphabeticUpperSize = alphabeticSize;
				}else{
					alphabeticLowerSize = alphabeticSize;
				}

			} else if (nummeric) {
				nummericSize = passwordLength;
			} else if (special) {
				specialSize = passwordLength;
			} else {
				alphabeticSize = passwordLength;

				if(alphabeticUpper && alphabeticLower){
					alphabeticUpperSize = alphabeticSize / 2 + passwordLength % 2;
					alphabeticLowerSize = alphabeticSize / 2 ;
				}else if(alphabeticUpper){
					alphabeticUpperSize = alphabeticSize;
				}else{
					alphabeticLowerSize = alphabeticSize;
				}
			}
			char[] password = new char[passwordLength];

			String index = ",";
			for (int i = 0; i < passwordLength; i++) {
				index += i + ",";
			}
			int posID;
			String strPosID = "";
			String subIndex = index.substring(0, index.length() - 1);
			for (int i = 0; i < passwordLength; i++) {

				posID = rand.nextInt(index.length());
				if (posID == 0) {
					strPosID = index.substring(1, index.indexOf(",", 1));
				} else if (posID >= subIndex.lastIndexOf(",")) {
					strPosID = subIndex.substring(subIndex.lastIndexOf(",") + 1);
				} else {
					strPosID = index.substring(index.indexOf(",", posID) + 1, index.indexOf(",", index.indexOf(",", posID) + 1));
				}

				if (nummericSize != 0) {
					password[Integer.parseInt(strPosID)] = PASSWORD_FOEMAT_NUMMERIC.charAt(rand.nextInt(PASSWORD_FOEMAT_NUMMERIC.length()));
					nummericSize--;
				} else if(alphabeticUpperSize != 0) {
					password[Integer.parseInt(strPosID)] = PASSWORD_FOEMAT_ALPHABETIC_UPPER.charAt(rand.nextInt(PASSWORD_FOEMAT_ALPHABETIC_UPPER.length()));
					alphabeticUpperSize--;
				} else if(alphabeticLowerSize != 0) {
					password[Integer.parseInt(strPosID)] = PASSWORD_FOEMAT_ALPHABETIC_LOWER.charAt(rand.nextInt(PASSWORD_FOEMAT_ALPHABETIC_LOWER.length()));
					alphabeticLowerSize--;
				} else if(specialSize != 0) {
					password[Integer.parseInt(strPosID)] = PASSWORD_FOEMAT_SPECIAL.charAt(rand.nextInt(PASSWORD_FOEMAT_SPECIAL.length()));
					specialSize--;
				}

				index = index.replaceFirst(strPosID + ",", "");
				subIndex = index.substring(0, index.length() - 1);
			}
			return new String(password);
		}catch(Exception e){
			throw e;
		}
	}



	/**
	 * Random String.
	 * [Example : RandomUtil.getRandomString(15, true, true, true);]
	 *
	 * @param pattern : Format
	 * @param value : number
	 * @return String : number format
	 *
	 */
	public static String getRandomString(int passwordLength, boolean nummeric, boolean alphabetic, boolean special) throws Exception{
		try{
			Random rand = new Random();
			int nummericSize = 0;
			int alphabeticSize = 0;
			int specialSize = 0;
			if(passwordLength == 0){return "";}
			if (nummeric && alphabetic && special) {
				nummericSize = passwordLength / 3;
				alphabeticSize = passwordLength / 3 + passwordLength % 3;
				specialSize = passwordLength / 3;
			} else if (nummeric && alphabetic) {
				nummericSize = passwordLength / 2;
				alphabeticSize = passwordLength / 2 + passwordLength % 2;
			} else if (nummeric && special) {
				nummericSize = passwordLength / 2;
				specialSize = passwordLength / 2 + passwordLength % 2;
			} else if (alphabetic && special) {
				alphabeticSize = passwordLength / 2;
				specialSize = passwordLength / 2 + passwordLength % 2;
			} else if (nummeric) {
				nummericSize = passwordLength;
			} else if (special) {
				specialSize = passwordLength;
			} else {
				alphabeticSize = passwordLength;
			}
			char[] password = new char[passwordLength];

			String index = ",";
			for (int i = 0; i < passwordLength; i++) {
				index += i + ",";
			}
			int posID;
			String strPosID = "";
			String subIndex = index.substring(0, index.length() - 1);
			for (int i = 0; i < passwordLength; i++) {
				posID = rand.nextInt(index.length());
				if (posID == 0) {
					strPosID = index.substring(1, index.indexOf(",", 1));
				} else if (posID >= subIndex.lastIndexOf(",")) {
					strPosID = subIndex.substring(subIndex.lastIndexOf(",") + 1);
				} else {
					strPosID = index.substring(index.indexOf(",", posID) + 1, index.indexOf(",", index.indexOf(",", posID) + 1));
				}

				if (nummericSize != 0) {
					password[Integer.parseInt(strPosID)] = PASSWORD_FOEMAT_NUMMERIC.charAt(rand.nextInt(PASSWORD_FOEMAT_NUMMERIC.length()));
					nummericSize--;
				} else if(alphabeticSize != 0) {
					password[Integer.parseInt(strPosID)] = PASSWORD_FOEMAT_ALPHABETIC.charAt(rand.nextInt(PASSWORD_FOEMAT_ALPHABETIC.length()));
					alphabeticSize--;
				} else if(specialSize != 0) {
					password[Integer.parseInt(strPosID)] = PASSWORD_FOEMAT_SPECIAL.charAt(rand.nextInt(PASSWORD_FOEMAT_SPECIAL.length()));
					specialSize--;
				}

				index = index.replaceFirst(strPosID + ",", "");
				subIndex = index.substring(0, index.length() - 1);
			}
			return new String(password);
		}catch(Exception e){
			throw e;
		}
	}
}