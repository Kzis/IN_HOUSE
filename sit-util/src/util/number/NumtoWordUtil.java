/**
 * Convert Number
 * @author : SD
 * @version : 1.0
 *
 */
package util.number;

import java.util.Locale;
import java.util.ResourceBundle;

import util.bundle.BundleUtil;


/**
 * Class จัดการแปลงตัวเลขเป็นคำ
 */
public class NumtoWordUtil {
	public static void main(String args[]) {

		ResourceBundle bundle = null;
		try {
			bundle = BundleUtil.getBundle("util/number/Message", Locale.getDefault());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//bundle = BundleUtil.getBundle("E:/workspace/wksit-util/sit-util/src/util/number/Message", Locale.getDefault());
		String h = bundle.getString("one");

		NumtoWordUtil num = new NumtoWordUtil();
		double y = 5200.25;

//		DecimalFormat format = new DecimalFormat("#,##0.00");
//		String r = format.format(y);
//
//
//		Object a[] = { "Real's HowTo", "http://www.rgagnon.com" ,
//		        java.util.Calendar.getInstance()};
//
//		String s = String.format("Welcome %1$s at %2$s ( %3$tY %3$tm %3$te )", a);
//		System.out.println(s);
		//  output : Welcome Real's HowTo at http://www.rgagnon.com (2010 06 26)


		//double y = 0.0;
		String io = "";
		try {
			io = num.numToWord(y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(io);
	}


	/**
	 * แปลงตัวเลขเป็นข้อความ.
	 * <p>
	 * [Example : String word =  numWord.numToWord(150.25) : return หนึ่งร้อยห้าสิบบาทยี่สิบห้าสตางค์]
	 * [Example : String word =  numWord.numToWord(150.257) : return หนึ่งร้อยห้าสิบบาทยี่สิบหกสตางค์]
	 *
	 * @param dSNX : number
	 * @return String : จำนวนเลขภาษาไทย
	 *
	 */
	public String numToWord(Double dSNX) throws Exception{
		try{
			// String SNX = String.valueOf(dSNX);

			// thanchanok.n 20090108
			//FormatNumberUtil format = new FormatNumberUtil();
			//String SNX = format.customFormat("####.00", dSNX);
			String SNX = FormatNumberUtil.customFormat("0.00", dSNX);
			//String SNX = new DecimalFormat("#.000").format(dSNX);

			int LST, LBT, LX, I, SEQ;
			String WX, NX, ST = "", BT = "", WBT = "", WST = "";
			boolean Wminus;
			if (SNX.charAt(0) == '-') {
				Wminus = true;
			} else {
				Wminus = false;
			}
			NX = SNX;
			LX = SNX.length();
			for (I = 1; I <= LX; I++) {
				if (SNX.charAt(I) == '.') {
					BT = SNX.substring(0, I);
					ST = SNX.substring(I + 1, SNX.length());
					break;
				}
			}
			I = I - LX;
			if (BT.trim().length() == 0) {
				BT = SNX;
			}
			SEQ = 1;
			LBT = BT.length();
			for (I = LBT - 1; I >= 0; I--) {
				if (LBT == 1) {
					WBT = chkNumber(String.valueOf(BT.charAt(I))) + WBT;
				} else {
					switch (SEQ) {
					case 1:
						if ((I - 1) > -1) {
							if (Integer.parseInt(String.valueOf(BT.charAt(I - 1))) > 0
									&& Integer.parseInt(String.valueOf(BT.charAt(I))) == 1) {
								WBT = getWord("ones") + WBT;
							} else {
								WBT = chkNumber(String.valueOf(BT.charAt(I))) + WBT;
							}
						}
						break;
					case 2:
						switch (Integer.parseInt(String.valueOf(BT.charAt(I)))) {
						case 2:
							WBT = getWord("twenty") + WBT;
							break;
						case 1:
							WBT = getWord("ten") + WBT;
							break;
						default:
							if (Integer.parseInt(String.valueOf(BT.charAt(I))) > 0) {
								WBT = chkNumber(String.valueOf(BT.charAt(I))) + getWord("ten") + WBT;
							}
							break;
						}
						break;
					case 3:
						if (Integer.parseInt(String.valueOf(BT.charAt(I))) != 0) {
							WBT = chkNumber(String.valueOf(BT.charAt(I))) + getWord("hundred") + WBT;
						}
						break;
					case 4:
						if (Integer.parseInt(String.valueOf(BT.charAt(I))) != 0) {
							WBT = chkNumber(String.valueOf(BT.charAt(I))) + getWord("thousand") + WBT;
						}
						break;
					case 5:
						if (Integer.parseInt(String.valueOf(BT.charAt(I))) != 0) {
							WBT = chkNumber(String.valueOf(BT.charAt(I))) + getWord("tenthousand") + WBT;
						}
						break;
					case 6:
						if (Integer.parseInt(String.valueOf(BT.charAt(I))) != 0) {
							WBT = chkNumber(String.valueOf(BT.charAt(I))) + getWord("hundredthousand") + WBT;
						}
						break;
					case 7:
						if (((LBT - 1) % 6) == 0) {
							WBT = chkNumber(String.valueOf(BT.charAt(I))) + getWord("million") + WBT;
						} else {
							if (Integer.parseInt(String.valueOf(BT.charAt(I - 1))) > 0
									&& Integer.parseInt(String.valueOf(BT.charAt(I))) == 1) {
								WBT = getWord("onesmillion") + WBT;
							}
							if (Integer.parseInt(String.valueOf(BT.charAt(I))) != 0
									&& Integer.parseInt(String.valueOf(BT.charAt(I))) != 1) {
								WBT = chkNumber(String.valueOf(BT.charAt(I))) + getWord("million") + WBT;
							}
							if (Integer.parseInt(String.valueOf(BT.charAt(I))) == 0
									&& Integer.parseInt(String.valueOf(BT.charAt(I))) != 1) {
								WBT = getWord("million") + WBT;
							}
						}
						SEQ = 1;
						break;
					}
					SEQ = SEQ + 1;
				}
			}

			if (ST.trim().length() == 0) {
				ST = "00";
			}
			if (ST.trim().length() == 1) {
				ST = ST + "0";
			}
			SEQ = 1;
			if (Long.parseLong(ST) != 0) {
				for (I = ST.length() - 1; I >= 0; I--) {
					if (ST.length() == 1) {
						WST = chkNumber(String.valueOf(ST.charAt(I))) + WST;
					} else {
						switch (SEQ) {
						case 1:
							if ((I - 1) > -1) {
								if (Integer.parseInt(String.valueOf(ST.charAt(I - 1))) > 0
										&& Integer.parseInt(String.valueOf(ST.charAt(I))) == 1) {
									WST = getWord("ones") + WST;
								} else {
									WST = chkNumber(String.valueOf(ST.charAt(I))) + WST;
								}
							}
							break;
						case 2:
							switch (Integer.parseInt(String.valueOf(ST.charAt(I)))) {
							case 2:
								WST = getWord("twenty") + WST;
								break;
							case 1:
								WST = getWord("ten") + WST;
								break;
							default:
								if (Integer.parseInt(String.valueOf(ST.charAt(I))) > 0) {
									WST = chkNumber(String.valueOf(ST.charAt(I))) + getWord("ten") + WST;
								}
								break;
							}
							break;
						case 3:
							if (Integer.parseInt(String.valueOf(ST.charAt(I))) != 0) {
								WST = chkNumber(String.valueOf(ST.charAt(I))) + getWord("hundred") + WST;
							}
							break;
						case 4:
							if (Integer.parseInt(String.valueOf(ST.charAt(I))) != 0) {
								WST = chkNumber(String.valueOf(ST.charAt(I))) + getWord("thousand") + WST;
							}
							break;
						case 5:
							if (Integer.parseInt(String.valueOf(ST.charAt(I))) != 0) {
								WST = chkNumber(String.valueOf(ST.charAt(I))) + getWord("tenthousand") + WST;
							}
							break;
						case 6:
							if (Integer.parseInt(String.valueOf(ST.charAt(I))) != 0) {
								WST = chkNumber(String.valueOf(ST.charAt(I))) + getWord("hundredthousand") + WST;
							}
							break;
						case 7:
							if (((ST.length() - 1) % 6) == 0) {
								WST = chkNumber(String.valueOf(ST.charAt(I))) + getWord("million") + WST;
							} else {
								if (Integer.parseInt(String.valueOf(ST.charAt(I - 1))) > 0
										&& Integer.parseInt(String.valueOf(ST.charAt(I))) == 1) {
									WST = getWord("onesmillion") + WST;
								}
								if (Integer.parseInt(String.valueOf(ST.charAt(I))) != 0
										&& Integer.parseInt(String.valueOf(ST.charAt(I))) != 1) {
									WST = chkNumber(String.valueOf(ST.charAt(I))) + getWord("million") + WST;
								}
								if (Integer.parseInt(String.valueOf(ST.charAt(I))) == 0
										&& Integer.parseInt(String.valueOf(ST.charAt(I))) != 1) {
									WST = getWord("million") + WST;
								}
							}
							SEQ = 1;
							break;
						}
						SEQ = SEQ + 1;
					}
				}
			}
			if (WBT.length() > 0) {
				WBT = WBT + getWord("bath") + "";
			}
			if (WST.length() > 0) {
				WST = WST + getWord("satang") + "";
			} else {
				// nanthaporn.p 20090511
				// WBT = WBT + getWord("ful");
				if (WBT.length() > 0) {
					WBT = WBT + getWord("ful");
				} else {
					// WBT = getWord("zero") + getWord("bath") + getWord("ful");
					WBT = "-";
				}
				// nanthaporn.p 20090511
			}
			return WBT + WST;
		}catch(Exception e){
			throw e;
		}
	}

	private String chkNumber(String X) throws Exception {
		try{
			String ChNumber = "";
			switch (Integer.parseInt(X)) {
			case 1:
				ChNumber = getWord("one");
				break;
			case 2:
				ChNumber = getWord("two");
				break;
			case 3:
				ChNumber = getWord("three");
				break;
			case 4:
				ChNumber = getWord("four");
				break;
			case 5:
				ChNumber = getWord("five");
				break;
			case 6:
				ChNumber = getWord("six");
				break;
			case 7:
				ChNumber = getWord("seven");
				break;
			case 8:
				ChNumber = getWord("eight");
				break;
			case 9:
				ChNumber = getWord("nine");
				break;
			}
			return ChNumber;
		}catch(Exception e){
			throw e;
		}
	}

	private String getWord(String key) throws Exception{
		ResourceBundle bundle = null;
		try {
			bundle = BundleUtil.getBundle("util.number.Message", Locale.getDefault());
			bundle.getString(key);
			return bundle.getString(key);
		} catch (Exception e) {
			throw e;
		}
	}
}
