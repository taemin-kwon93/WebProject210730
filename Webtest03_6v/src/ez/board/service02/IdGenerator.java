package ez.board.service02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ez.loader.JdbcUtil;
import ez.jdbc.connection.ConnectionProvider;

public class IdGenerator {
	private static IdGenerator instance = new IdGenerator();
	public static IdGenerator getInstance() {
		return instance;
	}
	
	private IdGenerator() {}
	//IdGenerator 클래스를 사용할 때에는 쿼리에서 sequence_name 칼럼에 입력한 'article'값을 사용한다.
	
	//글의 그룹번호 생성메소드.
	public int generateNextId(String sequenceName)throws IdGenerationFailedException{
		Connection conn = null;
		PreparedStatement pstmtSelect = null;
		ResultSet rs = null;
		PreparedStatement pstmtUpdate = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			pstmtSelect = conn.prepareStatement(
					"select next_value from id_sequence " +
					" where sequence_name = ? for update ");
			pstmtSelect.setString(1, sequenceName);
			/*select next_value from id_sequence where sequence_name = 'article';
			  결과 예시, next_value = 26 */
			rs = pstmtSelect.executeQuery();
			rs.next();
			int id = rs.getInt(1);//따라서 id에 getInt(1)값 26을 저장
			id++;//글번호 증가
			
			pstmtUpdate = conn.prepareStatement(
					" update id_sequence set next_value = ? "+
					" where sequence_name = ?");//글번호 증가를 위한 쿼리문
			pstmtUpdate.setInt(1, id);
			pstmtUpdate.setString(2, sequenceName);
			pstmtUpdate.executeUpdate();
			
			conn.commit();
			return id;
			}catch(SQLException ex) {
			JdbcUtil.rollback(conn);
			throw new IdGenerationFailedException(ex);
		}finally {
			if(conn != null) {
				try {
					conn.setAutoCommit(true);
				}catch(SQLException e) {
				}JdbcUtil.close(conn);
			}
		}
	}//public int generateNextId(String sequenceName)
	
	
}
