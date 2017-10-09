package util.cryptography.AES128;

public class HexUtils {
    public static byte[] toBytes(String s) {
        byte[] result = new byte[s.length()];
        for(int i = 0; i < s.length(); i++) {
            result[i] = toByte(s.charAt(i));
        }
        return result;
    }

    public static byte toByte(char ch) {
        return (byte)ch;
    }
    
    public static String toHexString(byte value) {
        String hex = Integer.toHexString(value);
        if(hex.length() > 2) hex = hex.substring(hex.length() - 2);
        if(hex.length() == 1) hex = "0" + hex;
        hex = hex.toUpperCase();
        return hex;
    }
    
    public static String toHexString(byte[] data) {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < data.length; i++) {
            buffer.append(toHexString(data[i]));
        }
        return buffer.toString();
    }
}
