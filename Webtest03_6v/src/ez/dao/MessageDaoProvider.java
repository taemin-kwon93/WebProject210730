package ez.dao;

import ez.dao.MessageDao;
import ez.dao.MessageDaoProvider;

//import ssol.dao.mssql.MSSQLMessageDao; MSS를 위한 import
//import ssol.dao.mysql.MySQLMessageDao; MySQL을 위한 import

//MessageDao md = MessageDaoProvider.getInstnace().getMessageDao()
import ez.dao.oracle.OracleMessageDao;

public class MessageDaoProvider {

	private static MessageDaoProvider instance = new MessageDaoProvider();
	
	public static MessageDaoProvider getInstatnce() {
		return instance;
	}
	
	private MessageDaoProvider() {}
	
	
	private OracleMessageDao oracleDao = new OracleMessageDao();
	private String dbms;

	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	
	public MessageDao getMessageDao() {
		if("oracle".equals(dbms)) {
			return oracleDao;
		}
		return null;
	}
	
}









