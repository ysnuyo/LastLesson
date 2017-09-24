package Original;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class DAO {

	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int regist(String title, String tags, String content){// (引数にWord型のArraylistをセット) {
		int result = 0;

		@SuppressWarnings("unused")
		List tag_tmp = new <String> ArrayList();
		int content_id = 1;
		int tag_id = 1;

//		String[] tag_tmp = new String[];


		//一括で入力されたtagを分割
		if(tags.contains(" ")){
			tag_tmp = Arrays.asList(tags.split(" "));
		}else if(tags.contains("　")){
			tag_tmp = Arrays.asList(tags.split("　"));
		}else {
			tag_tmp.add(tags);
		}


		//コンテンツの登録

		try {
			// DB接続の記述

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useUnicode=true&characterEncoding=utf8","root","");

			if(con != null){
				System.out.println("DB接続完了 (getConnection後) \r\n----");
			}else{
				System.out.println("DB接続失敗\r\n");
			}

			String SQL ="INSERT INTO contents (title, content) values (?, ?)";

			stmt = con.prepareStatement(SQL);
			stmt.setString(1, title);
			stmt.setString(2, content);
			//stmt.setTimestamp(3,  new Timestamp(System.currentTimeMillis()));

			stmt.executeUpdate();

//			ResultSet rs = stmt.executeQuery();
//
//			//コンテンツidの取得
//			content_id = rs.getString("id");

		} catch (Exception e) {
			System.out.println("SQLException:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if ( stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}


		//tagの登録

		try {
			// DB接続の記述

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useUnicode=true&characterEncoding=utf8","root","");

			if(con != null){
				System.out.println("DB接続完了 (getConnection後) \r\n----");
			}else{
				System.out.println("DB接続失敗\r\n");
			}


			//コンテンツに登録したいタグの数だけループ
			for(int i = 0;i < tag_tmp.size();i++){

			//重複確認してなければ追加
			String SQL ="INSERT INTO tags (tag) SELECT ? WHERE NOT EXISTS (SELECT * FROM tags WHERE tag = ?);";

			stmt = con.prepareStatement(SQL);
			stmt.setString(1, (String) tag_tmp.get(i));
			stmt.setString(2, (String) tag_tmp.get(i));

			stmt.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println("SQLException:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if ( stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}


		//contents_tagsの登録

		try {
			// DB接続の記述
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useUnicode=true&characterEncoding=utf8","root","");

			if(con != null){
				System.out.println("DB接続完了 (getConnection後) \r\n----");
			}else{
				System.out.println("DB接続失敗\r\n");
			}


			//contentのidの取得

			String SQLcontentid ="SELECT * FROM contents WHERE title= ?";

			stmt = con.prepareStatement(SQLcontentid);
			stmt.setString(1, title);
			ResultSet rs2 = stmt.executeQuery();
			while(rs2.next()){
				content_id = rs2.getInt("id");
			}
			//コンテンツに登録したいタグの数だけループ
			for(int i = 0;i < tag_tmp.size();i++){

				//tagのidを取得
				String SQLtagid ="SELECT * FROM tags WHERE tag= ?";

				stmt = con.prepareStatement(SQLtagid);
				stmt.setString(1, (String) tag_tmp.get(i));
				ResultSet rs = stmt.executeQuery();

				while(rs.next()){
					tag_id = rs.getInt("id");
				}
//				System.out.println("id:" + id + "  tag:"+ tag);
				//タグのid取得完了


				String SQL ="INSERT INTO contents_tags (contents_id, tag_id) values (?, ?)";

				stmt = con.prepareStatement(SQL);
				stmt.setInt(1, content_id);
				stmt.setInt(2, tag_id);

				stmt.executeUpdate();

			}

		} catch (Exception e) {
			System.out.println("SQLException:" + e.getMessage());
					e.printStackTrace();
		} finally {
			if ( stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if ( con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result + 1;	// 結果を返す
	}

//	public List<Word> getWords(){
//
//		ArrayList<Word> WordA = new ArrayList<Word>();
//
//		try{
//
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://localhost/testdb","root","");
//
//			if(con != null){
//				System.out.println("DB接続完了 (getConnection後) \r\n----");
//			}else{
//				System.out.println("DB接続失敗\r\n");
//			}
//
////			@SuppressWarnings("resource")
////			int userId = new Scanner(System.in).nextInt();
////			String SQL = "SELECT * FROM users WHERE user_id= "+userId;
//
//			String SQL ="SELECT * FROM dectionary";
////			stmt= con.createStatement(); WHERE user_id =?
//
//			stmt = con.prepareStatement(SQL);
////			stmt.setInt(1,userId);
//
////			ResultSet rs = stmt.executeQuery(SQL);
//
//			ResultSet rs = stmt.executeQuery();
//
//			while(rs.next()){
//				String english = rs.getString("english");
//				String japanese = rs.getString("japanese");
////				System.out.println("英語:" + english + "  日本語:"+ japanese);
//				WordA.add(new Word(english,japanese));
//
//			}
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//
//			if(stmt != null){
//				try{
//					stmt.close();
//				}catch(SQLException e){
//					e.printStackTrace();
//				}
//			}
//
//			if(con != null){
//				try{
//					con.close();
//				}catch(SQLException e){
//					e.printStackTrace();
//				}
//			}
//			System.out.println("\n\n正常切断完了");
//		}
//
//
//		return WordA;
//
//	}
}