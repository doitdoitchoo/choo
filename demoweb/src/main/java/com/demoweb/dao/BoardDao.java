package com.demoweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.demoweb.dto.Board;
import com.demoweb.dto.Member;


public class BoardDao {
	
	public void insertBoard(Board board) {
	
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 준비
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 데이터베이스에 연결 ( 연결객체 준비 )
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoweb", // db server url
					"knit", "mysql"); // 계정 정보

			// 3. SQL 작성 + 명령 객체 만들기
			String sql = "insert into board (title, writer, content) " + 
						 "values (?, ?, ?) ";
			pstmt = conn.prepareStatement(sql); // 명령객체 만들기
			pstmt.setString(1, board.getTitle()); // SQL의 1번째 ?에 사용될 데이터
			pstmt.setString(2, board.getWriter()); // SQL의 2번째 ?에 사용될 데이터
			pstmt.setString(3, board.getContent()); // SQL의 3번째 ?에 사용될 데이터

			// 4. 명령실행 ( select인 경우 ResultSet 형식의 결과 반환 )
			pstmt.executeUpdate(); // executeQuery : select, exeucteUpdate : select 이외의 sql

			// 5. 결과가 있으면 (select 명령인 경우) 결과 처리

		} catch (Exception ex) {
			ex.printStackTrace(); // 오류 메시지를 화면에 출력
		} finally {
				// 6. 연결 종료
				try { pstmt.close(); } catch (Exception ex) {}
				try { conn.close(); } catch (Exception ex) {}
			}
	}

	public List<Board> selectAll() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		ArrayList<Board> boardList = new ArrayList<Board>(); // 조회 결과를 저장할 변수
				
		try {
			// 1. JDBC 드라이버 준비
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 데이터베이스에 연결 ( 연결객체 준비 ) 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoweb", // db server url
												"knit","mysql"); // 계정 정보
						
			// 3-2. SQL 작성 + 명령 객체 만들기2 ( SQL과 데이터를 분리 )
			String sql = "select boardno, title, writer, readcount, regdate, deleted " +
						 "from board " +
						 "order by boardno desc "; // 최근에 작성된 글을 앞에 표시			
			pstmt = conn.prepareStatement(sql); // 명령객체 만들기		
			
			// 4. 명령실행 ( select인 경우 ResultSet 형식의 결과 반환 )
			rs = pstmt.executeQuery(); // executeQuery : select, exeucteUpdate : select 이외의 sql
			
			// 5. 결과가 있으면 (select 명령인 경우) 결과 처리
			while (rs.next()) {
				Board board = new Board();
				board.setBoardNo(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setReadCount(rs.getInt(4));
				board.setRegDate(rs.getDate(5));
				board.setDeleted(rs.getBoolean(6));
				
				boardList.add(board);
			}	
			
		} catch (Exception ex) {
			ex.printStackTrace(); // 오류 메시지를 화면에 출력			
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close();} catch (Exception ex ) {}
			try { conn.close();} catch (Exception ex ) {}
		}
		return boardList;
	}
	
	public List<Board> selectByRange(int from, int count) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		ArrayList<Board> boardList = new ArrayList<Board>(); // 조회 결과를 저장할 변수
				
		try {
			// 1. JDBC 드라이버 준비
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 데이터베이스에 연결 ( 연결객체 준비 ) 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoweb", // db server url
												"knit","mysql"); // 계정 정보
						
			// 3-2. SQL 작성 + 명령 객체 만들기2 ( SQL과 데이터를 분리 )
			String sql = "select boardno, title, writer, readcount, regdate, deleted " +
						 "from board " +
						 "order by boardno desc " +
						 "limit ? , ? "; // 최근에 작성된 글을 앞에 표시			
			pstmt = conn.prepareStatement(sql); // 명령객체 만들기		
			pstmt.setInt(1, from);
			pstmt.setInt(2, count);
			
			// 4. 명령실행 ( select인 경우 ResultSet 형식의 결과 반환 )
			rs = pstmt.executeQuery(); // executeQuery : select, exeucteUpdate : select 이외의 sql
			
			// 5. 결과가 있으면 (select 명령인 경우) 결과 처리
			while (rs.next()) {
				Board board = new Board();
				board.setBoardNo(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setReadCount(rs.getInt(4));
				board.setRegDate(rs.getDate(5));
				board.setDeleted(rs.getBoolean(6));
				
				boardList.add(board);
			}	
			
		} catch (Exception ex) {
			ex.printStackTrace(); // 오류 메시지를 화면에 출력			
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close();} catch (Exception ex ) {}
			try { conn.close();} catch (Exception ex ) {}
		}
		return boardList;
	}

	public Board selectByBoardNo(int boardNo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		Board board = null; // 조회 결과를 저장할 변수
				
		try {
			// 1. JDBC 드라이버 준비
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 데이터베이스에 연결 ( 연결객체 준비 ) 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoweb", // db server url
												"knit","mysql"); // 계정 정보
						
			// 3-2. SQL 작성 + 명령 객체 만들기2 ( SQL과 데이터를 분리 )
			String sql = "select boardno, title, writer, content, readcount, regdate " +
						 "from board " +
						 "where boardno = ? and deleted = false";
			pstmt = conn.prepareStatement(sql); // 명령객체 만들기		
			pstmt.setInt(1, boardNo);
			
			// 4. 명령실행 ( select인 경우 ResultSet 형식의 결과 반환 )
			rs = pstmt.executeQuery(); // executeQuery : select, exeucteUpdate : select 이외의 sql
			
			// 5. 결과가 있으면 (select 명령인 경우) 결과 처리
			if (rs.next()) {
				board = new Board();
				board.setBoardNo(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setContent(rs.getString(4)); // 추가
				board.setReadCount(rs.getInt(5));
				board.setRegDate(rs.getDate(6));
				// board.setDeleted(rs.getBoolean(7));				
			}	
			
		} catch (Exception ex) {
			ex.printStackTrace(); // 오류 메시지를 화면에 출력			
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close();} catch (Exception ex ) {}
			try { conn.close();} catch (Exception ex ) {}
		}
		return board;
	}

	public void delete(int boardNo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 준비
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 데이터베이스에 연결 ( 연결객체 준비 )
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoweb", // db server url
					"knit", "mysql"); // 계정 정보

			// 3. SQL 작성 + 명령 객체 만들기
			// String sql = "delete from  board where boardno= ? " ;
			String sql = "update board set deleted = true where boardno = ? ";
			pstmt = conn.prepareStatement(sql); // 명령객체 만들기
			pstmt.setInt(1, boardNo); 
			
			// 4. 명령실행 ( select인 경우 ResultSet 형식의 결과 반환 )
			pstmt.executeUpdate(); // executeQuery : select, exeucteUpdate : select 이외의 sql

			// 5. 결과가 있으면 (select 명령인 경우) 결과 처리

		} catch (Exception ex) {
			ex.printStackTrace(); // 오류 메시지를 화면에 출력
		} finally {
				// 6. 연결 종료
				try { pstmt.close(); } catch (Exception ex) {}
				try { conn.close(); } catch (Exception ex) {}
			}
		
	}

	public void update(Board board) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 준비
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 데이터베이스에 연결 ( 연결객체 준비 )
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoweb", // db server url
					"knit", "mysql"); // 계정 정보

			// 3. SQL 작성 + 명령 객체 만들기
			String sql = "update board " + 
						 "set title = ?, content = ? " +						 
						 "where boardno= ? ";
			pstmt = conn.prepareStatement(sql); // 명령객체 만들기
			pstmt.setString(1, board.getTitle()); // SQL의 1번째 ?에 사용될 데이터
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getBoardNo());

			// 4. 명령실행 ( select인 경우 ResultSet 형식의 결과 반환 )
			pstmt.executeUpdate(); // executeQuery : select, exeucteUpdate : select 이외의 sql

			// 5. 결과가 있으면 (select 명령인 경우) 결과 처리

		} catch (Exception ex) {
			ex.printStackTrace(); // 오류 메시지를 화면에 출력
		} finally {
				// 6. 연결 종료
				try { pstmt.close(); } catch (Exception ex) {}
				try { conn.close(); } catch (Exception ex) {}
			}	
	}

	public int selectBoardCount() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		int count = 0; // 조회 결과를 저장할 변수
						
		try {
			// 1. JDBC 드라이버 준비
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 데이터베이스에 연결 ( 연결객체 준비 ) 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoweb", // db server url
												"knit","mysql"); // 계정 정보
						
			// 3-2. SQL 작성 + 명령 객체 만들기2 ( SQL과 데이터를 분리 )
			String sql = "select count(*) from board " ;						 			
			pstmt = conn.prepareStatement(sql); // 명령객체 만들기		
			
			// 4. 명령실행 ( select인 경우 ResultSet 형식의 결과 반환 )
			rs = pstmt.executeQuery(); // executeQuery : select, exeucteUpdate : select 이외의 sql
			
			// 5. 결과가 있으면 (select 명령인 경우) 결과 처리
			rs.next(); // 언제나 결과가 있는 조회이기 때문에 if 또는 while 생략
			count = rs.getInt(1);
			
		} catch (Exception ex) {
			ex.printStackTrace(); // 오류 메시지를 화면에 출력			
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close();} catch (Exception ex ) {}
			try { conn.close();} catch (Exception ex ) {}
		}
		return count;
	}

	
	
}
