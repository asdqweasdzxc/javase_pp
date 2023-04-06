package shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class CategoryDAO {
	
	DBManager dbManager=DBManager.getInstance();
	
	//카테고리 1건 등록
	public int insert(Category category) {
		Connection con = null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		int result = 0;
		
		String sql="insert into category(category_idx, category_name)";
		sql+=" values(seq_category.nextval, ?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			
			//pstmt.setInt(1, category.getCategory_idx());
			pstmt.setString(1, category.getCategory_name());
			
			result=pstmt.executeUpdate(); //DML 수행
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	//insert 성공 시 seq의 currval 가져오기
	public int getSequence() {
		int currval = 0;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		con=dbManager.getConnection();
		
		String sql="select seq_category.currval from dual";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("씨발");
				Category category = new Category();
				category.setCategory_idx(rs.getInt("currval"));
				currval= category.getCategory_idx();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return currval;
	}
	
	//모두 가져오기
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		ArrayList<Category> list = new ArrayList<Category>();
		
		con=dbManager.getConnection();
		
		String sql="select * from category order by category_idx asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Category category = new Category();
				category.setCategory_idx(rs.getInt("category_idx"));
				category.setCategory_name(rs.getString("category_name"));
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		
		//for(int i=0;i<list.size();i++) {
			//System.out.println(list.get(i).getCategory_name());
		//}
		return list;
	}
	
	
	public Category select(int category_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Category category=null;
		
		con=dbManager.getConnection();
		
		
		StringBuilder sb=new StringBuilder();
		sb.append("select * from category");
		sb.append(" WHERE category_idx = ?");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, category_idx);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				category=new Category();
				
				category.setCategory_idx(rs.getInt("category_idx"));
				category.setCategory_name(rs.getString("category_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		
		return category;
	}
	
	//레코드 1건 삭제
	public int delete(int category_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0;
		
		con=dbManager.getConnection();
		
		String sql="delete category where category_idx=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, category_idx);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
	
	//레코드 1건 수정
	public int update(Category category) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0;
		
		con=dbManager.getConnection();
				
		StringBuilder sb=new StringBuilder();
		sb.append("update category set category_name=?");
		sb.append(" where category_idx=? ");
		
		String sql=sb.toString();
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, category.getCategory_name());
			pstmt.setInt(2, category.getCategory_idx());
		
			result=pstmt.executeUpdate(); //쿼리 수행
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
}
