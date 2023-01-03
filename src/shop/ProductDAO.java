package shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import util.DBManager;

public class ProductDAO {

	//Product 테이블에 대한 CRUD 담당 객체
	
	DBManager dbManager = DBManager.getInstance();
	
	//원글 1건 등록 for Oracle
	public int insert(Product product) {
		Connection con = null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		int result = 0;
		
		String sql="insert into product(product_idx, category_idx, name, price)";
		sql+=" values(seq_product.nextval, ?, ?, ?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, product.getCategory().getCategory_idx());
			pstmt.setString(2, product.getName());
			pstmt.setInt(3, product.getPrice());
			//pstmt.setInt(4, product.getHit());
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
			
			String sql="select seq_product.currval from dual";
			try {
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					System.out.println("씨발");
					Product product = new Product();
					product.setProduct_idx(rs.getInt("currval"));
					currval= product.getProduct_idx();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dbManager.release(pstmt, rs);
			}
			return currval;
		}
	
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<Product>();
		
		con=dbManager.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT c.category_idx as category_idx, category_name,");
		sb.append(" product_idx, name, price, hit");
		sb.append(" FROM category c , product p");
		sb.append(" WHERE c.category_idx =p.category_idx ORDER BY product_idx ASC");
		
		String sql = sb.toString();
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Product product=new Product();
				Category category = new Category();
				
				category.setCategory_idx(rs.getInt("category_idx"));
				category.setCategory_name(rs.getString("category_name"));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setHit(rs.getInt("hit"));
				
				list.add(product);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		
		return list;
	}
	
	public List search(String text) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<Product>();
		
		char[] charArray=text.toCharArray();
		StringBuilder sb= new StringBuilder();
		sb.append('%');
		for(int i=0; i<charArray.length;i++) {
			sb.append(charArray[i]);
			sb.append('%');
		}
		String searchText=sb.toString();
		
		
		con=dbManager.getConnection();
		String sql = "SELECT * FROM product WHERE name LIKE '"+searchText+"' ORDER BY product_idx ASC";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();

			while(rs.next()) {
				Product product=new Product();
				Category category = new Category();
				product.setCategory(category);
				
				category.setCategory_idx(rs.getInt("category_idx"));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setHit(rs.getInt("hit"));
				
				list.add(product);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		
		return list;
	}
	
	public List select(int category_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List list = new ArrayList();
		Product product=null;
		
		con=dbManager.getConnection();
		
		
		StringBuilder sb=new StringBuilder();
		sb.append("select c.category_idx as category_idx, category_name");
		sb.append(", product_idx, name, price, hit");
		sb.append(" from category c, product p");
		sb.append(" WHERE c.category_idx = p.category_idx");
		sb.append(" AND p.category_idx =? ORDER BY product_idx ASC");
		
		
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, category_idx);
			rs=pstmt.executeQuery();
			
			//레코드가 있다면 커서 이동 시 true 반환
			while(rs.next()) {
				product=new Product();
				Category category=new Category();
				product.setCategory(category);
				
				category.setCategory_idx(rs.getInt("category_idx"));
				category.setCategory_name(rs.getString("category_name"));
				
				//상품담기
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setHit(rs.getInt("hit"));
				
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		
		return list;
	}
	
	//레코드 1건 삭제
		public int delete(int product_idx) {
			Connection con=null;
			PreparedStatement pstmt=null;
			int result=0;
			
			con=dbManager.getConnection();
			
			String sql="delete product where product_idx=?";
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, product_idx);
				result=pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbManager.release(pstmt);
			}
			
			return result;
		}
		
		//레코드 1건 수정
		public int update(Product product) {
			//System.out.println("idx="+product.getProduct_idx());
			Connection con=null;
			PreparedStatement pstmt=null;
			int result=0;
			
			con=dbManager.getConnection();
					
			StringBuilder sb=new StringBuilder();
			sb.append("update product set category_idx=?");
			sb.append(", name=?, price=?, Hit=?");
			sb.append(" where product_idx=?");
			
			String sql=sb.toString();
			
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, product.getCategory().getCategory_idx());
				pstmt.setString(2, product.getName());
				pstmt.setInt(3, product.getPrice());
				pstmt.setInt(4, product.getHit());
				pstmt.setInt(5, product.getProduct_idx());
				
				result=pstmt.executeUpdate(); //쿼리 수행
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dbManager.release(pstmt);
			}
			
			return result;
		}
}









