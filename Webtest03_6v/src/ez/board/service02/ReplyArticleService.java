package ez.board.service02;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import ez.board.dao.ArticleDao;
import ez.model.Article;
import ez.model.ReplyingRequest;
import ez.loader.JdbcUtil;
import ez.jdbc.connection.ConnectionProvider;
//답변을 달아주는 로직이 담겨있다.
public class ReplyArticleService {
	private static ReplyArticleService instance = new ReplyArticleService();
	public static ReplyArticleService getInstance() {
		return instance;
	}
	
	private ReplyArticleService() {}
	//reply메소드에 자바빈 객체타입을 담아준다.
	//ArticleNotFoundException 아티클이 없을때,
	//CannotReplyArticleException 답변이 없을때,
	//LastChildAleadyExistsException 답변에 답변에 답변 뭐시기 뭐시기
	public Article reply(ReplyingRequest replyingRequest)throws ArticleNotFoundException, 
		CannotReplyArticleException,LastChildAleadyExistsException{//댓글입력의 예외경우 
		//1. 글이없는경우, 2. 댓글을 달 수 없는 경우, 3. 마지막 댓글이 이미 있는 경우.
		Connection conn = null;
		
		Article article = replyingRequest.toArticle();//ReplyingRequest는 WritingRequest로 부터 상속받는다.
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);//트랜잭션 시작점
			
			ArticleDao articleDao = ArticleDao.getInstance();
			Article parent = articleDao.selectById(conn, replyingRequest.getParentArticleId());
			//int i = (parent.getId());
			//System.out.println("갖고온 아이디(글번호) 값" + i);
			/* ReplyingRequest는 WritingRequest를 상속받는다.
			 * ReplyingRequest로 부터 받아온 int값으로 selectById메소드를 실행한다.
			 * 실행된 결과로 갖고온 article을 parent에 저장한다.*/
			try {
				checkParent(parent, replyingRequest.getParentArticleId());
			}catch(Exception e) {//예외처리
				JdbcUtil.rollback(conn);//예외가 발생했을 때, rollback처리 한다.
				if(e instanceof ArticleNotFoundException) {//예외의 경우 분류 1
					throw(ArticleNotFoundException)e;
				}else if(e instanceof CannotReplyArticleException) {//예외의 경우 분류 2
					throw(CannotReplyArticleException)e;
				}else if(e instanceof LastChildAleadyExistsException) {//예외의 경우 분류 3
					throw(LastChildAleadyExistsException)e;
				}
			}
			String searchMaxSeqNum = parent.getSequenceNumber();//0000000026999999 아런식으로 시퀀스 넘버 가지고옴
			String searchMinSeqNum = getSearchMinSeqNum(parent);//레벨 조건(parent.getLevel())에 따라 시퀀스번호를 포맷해온다.
			
			String lastChildSeq = articleDao.selectLastSequenceNumber(conn, searchMaxSeqNum, searchMinSeqNum);
			String sequenceNumber = getSequenceNumber(parent, lastChildSeq);
			
			article.setGroupId(parent.getGroupId());
			article.setSequenceNumber(sequenceNumber);
			article.setPostingDate(new Date());
			
			int articleId = articleDao.insert(conn, article);
			if(articleId == -1) {
				throw new RuntimeException("DB 삽입 안됨: " + articleId);
			}
			conn.commit();
			article.setId(articleId);
			return article;
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB 작업 실패: " + e.getMessage(), e);
		}finally {
			if(conn != null) {
				try {
					conn.setAutoCommit(true);
				}catch(SQLException e) {}
			}
			JdbcUtil.close(conn);
		}
	}//public Article reply(ReplyingRequest replyingRequest)
	private void checkParent(Article parent, int parentId)throws ArticleNotFoundException, 
		CannotReplyArticleException, LastChildAleadyExistsException{
		
		if(parent == null) {
			throw new ArticleNotFoundException(
					"부모글이 존재하지 않음: " + parentId);
		}
		int parentLevel = parent.getLevel();
		if(parentLevel == 3) {
			throw new CannotReplyArticleException(
					"마지막 레벨 글에는 답글을 달 수 없습니다. " + parent.getId());
		}
	}//private void checkParent(Article parent, int parentId)
	
	private String getSearchMinSeqNum(Article parent){//매개변수로 받아온 부모글을 포맷하는 메소드
		String parentSeqNum = parent.getSequenceNumber();//시퀀스넘버는 String형 16글자이다. parent의 시퀀스 번호를 같은 타입의 변수 parentSeqNum에 저장한다.
		DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");//0으로 채워진 16자리의 숫자 형태
		long parentSeqLongValue = Long.parseLong(parentSeqNum);//String타입을 Long으로 변환하고 그 값을 parentSeqLongValue에 넣는다.
		/*시퀀스 번호 확인*///System.out.println("test1_parentSeqNum: "+ parentSeqLongValue);
		long searchMinLongValue = 0;
		switch (parent.getLevel()) {
		case 0://본문글일때, 글의 레벨은 0
			searchMinLongValue = parentSeqLongValue/1000000L * 1000000L;
			break;
		case 1://본문글의 댓글일때 레벨은 1
			searchMinLongValue = parentSeqLongValue/10000L*10000L;
			break;
		case 2:
			searchMinLongValue = parentSeqLongValue / 100L * 100L;
			break;
		}
		//System.out.println("test2_searchMinLongValue: " + searchMinLongValue);
		return decimalFormat.format(searchMinLongValue);
	}//getSearchMinSeqNum 처리할때 system.out.println 으로 내용 찍어보자.
	
	/*getSearchMinSeqNum 메소드 처리 방식 테스트 내용
	long parentSeqLongValue =50999999L;
	long searchMinLongValue;
	long x = parentSeqLongValue/1000000L;
	long y = x*1000000L;
	System.out.println("1: " + x);
	System.out.println("2: " + y);
	searchMinLongValue = parentSeqLongValue/1000000L * 1000000L;
	System.out.println("3: " + searchMinLongValue);
	
	DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
	System.out.println(decimalFormat.format(searchMinLongValue));
	[출력결과]
	1: x=50
	2: y=50000000
	3: 50000000
	0000000050000000	*/
	
	private String getSequenceNumber(Article parent, String lastChildSeq)throws LastChildAleadyExistsException{
		long parentSeqLong = Long.parseLong(parent.getSequenceNumber());
		int parentLevel = parent.getLevel();
		
		long decUnit = 0;
		if(parentLevel == 0) {
			decUnit = 10000L;
		}else if(parentLevel == 1) {
			decUnit = 100L;
		}else if(parentLevel == 2) {
			decUnit = 1L;
		}
		
		String sequenceNumber = null;
		
		DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
		if(lastChildSeq == null) {//답변글이 없다면
			sequenceNumber = decimalFormat.format(parentSeqLong-decUnit);	
		}else {// 답변글 있음.
			//마지막 답변글 인지 확인
			String orderOfLastChildSeq = null;
			if(parentLevel ==0) {
				orderOfLastChildSeq = lastChildSeq.substring(10, 12);
				sequenceNumber = lastChildSeq.substring(0, 12)+"9999";
			}else if(parentLevel == 1) {
				orderOfLastChildSeq = lastChildSeq.substring(12, 14);
				sequenceNumber = lastChildSeq.substring(0, 14) + "99";
			}else if(parentLevel == 2) {
				orderOfLastChildSeq = lastChildSeq.substring(14, 16);
				sequenceNumber = lastChildSeq;
			}
			
			if(orderOfLastChildSeq.equals("00")) {
				throw new LastChildAleadyExistsException(
						"마지막 자식 글이 이미 존재합니다." + lastChildSeq);
			}
			long seq = Long.parseLong(sequenceNumber) - decUnit;
			sequenceNumber = decimalFormat.format(seq);
		}
		return sequenceNumber;
	}//private String getSequenceNumber(Article parent, String lastChildSeq)

}