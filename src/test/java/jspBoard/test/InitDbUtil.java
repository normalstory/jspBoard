package jspBoard.test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import jspBoard.boardCategory.dao.BoardCategoryDao;
import jspBoard.boardCategory.dao.IBoardCategoryDao;
import jspBoard.mybatis.SqlMapSessionFactory;

public class InitDbUtil {
	protected	SqlSession sqlSession;
	protected	IBoardCategoryDao boardCategoryDao;
	
	@Before
	public void setup() {
		//db 초기화
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jspBoard/dbinit/jspBoardInit.sql" ));
		populator.setContinueOnError(false);
		
		BasicDataSource bds = new BasicDataSource();
		bds.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUsername("jspboardTest");
		bds.setPassword("jspboardTest");
		DatabasePopulatorUtils.execute(populator , bds);
		
		//테스트 대상
		sqlSession = SqlMapSessionFactory.getSqlSessionFactory().openSession();
		boardCategoryDao = new BoardCategoryDao();
	}
	
	@After
	public void tearDown() {
		sqlSession.commit();
		sqlSession.close();
	}
}