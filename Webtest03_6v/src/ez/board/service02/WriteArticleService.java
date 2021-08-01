package ez.board.service02;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import ez.board.dao.ArticleDao;
import ez.model.Article;
import ez.model.WritingRequest;
import ez.loader.JdbcUtil;
import ez.jdbc.connection.ConnectionProvider;

public class WriteArticleService {
	private static WriteArticleService instance = new WriteArticleService();
	public static WriteArticleService getInstance() {
		return instance;
	}
	private WriteArticleService() {}
	
	public Article write(WritingRequest writingRequest)throws IdGenerationFailedException{
		//�� �Է��� ���� ����
		int groupId = IdGenerator.getInstance().generateNextId("article");/*DB���� sequence_name�÷� 'article'�� �� ��.27
		���� IdGenerator�� ���� �׷��ȣ�� ������� ���� intŸ�� groupId�� �����Ѵ�.*/
		Article article = writingRequest.toArticle();/*toArticle()���� �ۼ���, ��й�ȣ, ����, ������ �����Ͱ� ������ִ�.*/
		
		article.setGroupId(groupId);//�׷��ȣ�� �������ش�.
		article.setPostingDate(new Date());//������ ��¥�� �����ش�.
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");//DecimalFormat�� ���¸� 0���� 10�ڸ� �ְ�
		article.setSequenceNumber(decimalFormat.format(groupId)+"999999");/* �׷��ȣ�� ������ groupId�� ���� "0000000000"�������� ���߾�
		'0000000027'�� ���� �׷��ȣ�� �����ϰ� ��� �з��� ���� "999999"�� �����Ѵ�. �� 16�ڸ� ���ڰ� sequenceNumber(0000000027999999)�� �ȴ�. */
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();//DB����
			conn.setAutoCommit(false);//Ʈ����� ������
			
			int articleId = ArticleDao.getInstance().insert(conn, article);//articleId�� insert���ϰ��� �۹�ȣ ���.
			if(articleId == -1) {//insert(conn, article)���� �� ������ ���� ������, ���ϰ� -1�� ����´�. �׶��� ���ǹ�
				JdbcUtil.rollback(conn);//DB�ѹ�
				throw new RuntimeException("DB���Ծȵ�: " + articleId);
			}
			conn.commit();//�� ������ �Ǹ� Ŀ��!
			
			article.setId(articleId);
			//System.out.print("�۳��� ����ϱ�: " + article.getContent());  
			return article;
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB����: " + e.getMessage(), e);
		}finally {
			if(conn != null) {
				try {
					conn.setAutoCommit(true);
				}catch(SQLException e) {}
			}
			JdbcUtil.close(conn);
		}
	}//public class WriteArticleService
}
