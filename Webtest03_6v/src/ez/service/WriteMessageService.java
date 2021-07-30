package ez.service;

import java.sql.*;
import ez.dao.MessageDao;
import ez.dao.MessageDaoProvider;
import ez.model.Message;
import ez.loader.JdbcUtil;
import ez.jdbc.connection.ConnectionProvider;

public class WriteMessageService {

	private static WriteMessageService instance = new WriteMessageService();
	
	public static WriteMessageService getInstance () {
		return instance;
	}
	private WriteMessageService() {}
	
	public void write(Message message) throws ServiceException {
		Connection conn =null;
		try {
			conn=ConnectionProvider.getConnection();
			MessageDao messageDao = MessageDaoProvider.getInstatnce().getMessageDao();
			messageDao.insert(conn, message);
			
		}catch(SQLException e) {
			throw new ServiceException (
				"메시지 등록 실패" + e.getMessage(),e);
			}finally {
				JdbcUtil.close(conn);			
		}	
	}

	
	
}