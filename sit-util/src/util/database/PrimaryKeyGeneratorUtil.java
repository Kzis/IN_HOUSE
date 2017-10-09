/**
 * Get Sequence Primary Key
 * @author : SD
 * @version : 1.0
 *
 */
package util.database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Get Primary Key
 *
 */

public class PrimaryKeyGeneratorUtil {

	/**
	 * Get Sequence From Oracle Database.
	 * <p>
	 * [Example : String seq = genSequence(seq_user,conn)]
	 *
	 * @param sequencename : Sequence Table
	 * @param conn : Database Connection
	 * @return String : sequence No.
	 *
	 */
	public static String genSequence(String sequencename, Connection conn) throws Exception{
		Statement stmt = null;
		ResultSet rst = null;
		String c = "";
		String sql = "select " + sequencename.trim() + ".nextval as c from dual ";
		try{
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			if(rst.next()){
				c = rst.getString("c");
			}
		} catch(Exception e){
			throw e;
		}
		return c;
	}

}
