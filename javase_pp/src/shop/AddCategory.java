package shop;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class AddCategory extends Page{

	JLabel la_name;
	JTextField t_category;
	JButton bt_regist;
	JButton bt_list;
	JComboBox c_category;
	JTextField t_edit;
	JButton bt_edit;
	JButton bt_delete;
	
	int category_index;
	AddCategory addCategory=this;
	
	
	CategoryDAO categoryDAO;
	ArrayList<Category> categoryList;
	
	public AddCategory(AdminMain adminMain) {
		super(adminMain);
		this.setLayout(null);
		categoryDAO = adminMain.categoryDAO;
		
		
		categoryList = (ArrayList<Category>) categoryDAO.selectAll();
		
		la_name = new JLabel("이름");
		t_category = new JTextField();
		bt_regist = new JButton("등록");
		bt_list = new JButton("목록");
		
		c_category=new JComboBox();
		t_edit=new JTextField();
		bt_edit=new JButton("수정");
		bt_delete=new JButton("삭제");
		
		la_name.setBounds(150, 50, 50, 50);
		t_category.setBounds(200, 50, 500, 50);
		bt_regist.setBounds(720, 50, 100, 50);
		
		c_category.setBounds(200, 200, 300, 50);
		t_edit.setBounds(520, 200, 400, 50);
		bt_edit.setBounds(520, 270, 100, 50);
		bt_delete.setBounds(200, 270, 100, 50);
		
		createCombo();
		
		add(la_name);
		add(t_category);
		add(bt_regist);
		add(bt_list);
		add(c_category);
		add(t_edit);
		add(bt_edit);
		add(bt_delete);
		
		//등록버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
		
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//데이터베이스 목록 가져오기 + 화면전환
				ProductPage productPage = (ProductPage)adminMain.page[AdminMain.PRODUCTPAGE];
				productPage.getList(); //db갱신
				
				adminMain.showHide(AdminMain.PRODUCTPAGE); //화면전환
			}
		});
		
		c_category.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				category_index = c_category.getSelectedIndex();			
				System.out.println("category_index = "+category_index);
			}
		});
		
		bt_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp_index=category_index;
				//System.out.println("eqweqwe"+temp_index);
				edit();
				adminMain.productPage.columnList.get(temp_index).la_name.setText(t_edit.getText());
				//System.out.println("qweqweqwe"+category_index);
				
				c_category.removeItemAt(temp_index);
				c_category.insertItemAt(t_edit.getText(), temp_index);
				
				adminMain.addProduct.c_category.removeItemAt(temp_index);
				adminMain.addProduct.c_category.insertItemAt(t_edit.getText(), temp_index);
				
				adminMain.addProduct.c_category2.removeItemAt(temp_index);
				adminMain.addProduct.c_category2.insertItemAt(t_edit.getText(), temp_index);

				adminMain.addProduct.c_category3.removeItemAt(temp_index);
				adminMain.addProduct.c_category3.insertItemAt(t_edit.getText(), temp_index);
				
				adminMain.addProduct.updateUI();
				//adminMain.showHide(AdminMain.PRODUCTPAGE);
			}
		});
		
		bt_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("index"+category_index);
				delete();
			}
		});
		
	}
	
	public void print() {
		System.out.println("category_index = "+category_index);
	}

	public void regist() {
		Category category = new Category(); //empty DTO
		if(t_category.getText().length()>=1) {
			category.setCategory_name(t_category.getText());
			int result=categoryDAO.insert(category); //DTO
			//System.out.println("결과"+result);
			if(result>0) {
				JOptionPane.showMessageDialog(this, "원글 등록 성공");
				category.setCategory_idx(categoryDAO.getSequence());
				categoryList.add(category);
				adminMain.addProduct.categoryList.add(category);
				createCombo();
				adminMain.addProduct.createCombo();
				adminMain.productPage.getList();
				//System.out.println("idx"+category.getCategory_idx());
				//System.out.println("idx"+categoryList.get(categoryList.size()-1).getCategory_idx());
			}
		} else {
			JOptionPane.showMessageDialog(adminMain, "글자를 적어주세요~");
		}
	}
	
	public void createCombo() {
		c_category.removeAllItems();
		for(int i=0;i<categoryList.size();i++) {
			Category category = categoryList.get(i);
			c_category.addItem(category.getCategory_name());
		}
		this.updateUI();
	}
	
	public void edit() {
		Category category = new Category();
		if(t_edit.getText().length()>=1) {
			category.setCategory_name(t_edit.getText());
			category.setCategory_idx(categoryList.get(category_index).getCategory_idx());
			//System.out.println("size"+categoryList.size());
			int n=categoryDAO.update(category);
			if(n>0) {
				JOptionPane.showMessageDialog(this, "수정완료");
				categoryList.get(category_index).setCategory_name(t_edit.getText());
				adminMain.addProduct.categoryList.get(category_index).setCategory_name(t_edit.getText());
				createCombo();
				adminMain.addProduct.createCombo();
				adminMain.productPage.getList();
			}
		} else {
			JOptionPane.showMessageDialog(this, "글자를 적어주세요~");
		}
		adminMain.showHide(AdminMain.ADDCATEGORY);
	}
	
	//더 최근에 생성된 카테고리를 제거했을 때, 이후 생성된 카테고리가 삭제되지 않음.
	public void delete() {
		//System.out.println("categoryindex="+category_index);
		Category category = categoryList.get(category_index);
		if(JOptionPane.showConfirmDialog(adminMain, "삭제하실래요?")==JOptionPane.OK_OPTION) {
			//System.out.println("확인"+categoryList.get(category_index));
			//System.out.println("확인"+category_index);
			int result = categoryDAO.delete(categoryList.get(category_index).getCategory_idx());
			if(result>0) {
				c_category.removeItemAt(category_index);
				adminMain.addProduct.c_category.removeItemAt(category_index);
				adminMain.addProduct.c_category2.removeItemAt(category_index);
				adminMain.addProduct.c_category3.removeItemAt(category_index);
				adminMain.productPage.getList();
				categoryList.remove(category_index);
			} else {
				JOptionPane.showMessageDialog(adminMain, "카테고리 내부에 상품이 존재합니다");
			}
			//System.out.println("categoryindex="+category_index);
			updateUI();
			adminMain.productPage.p_list.updateUI();
		} 
	}
	
}
