package shop;

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

public class AddProduct extends Page{

	JLabel la_name;
	JLabel la_price;
	JLabel la_name2;
	JLabel la_price2;
	JTextField t_name;
	JTextField t_price;
	JComboBox c_category;
	JButton bt_regist;
	JButton bt_list;
	
	
	JComboBox c_category2;
	JComboBox c_category3;
	JComboBox c_product;
	JTextField t_edit_name;
	JTextField t_edit_price;
	JButton bt_edit;
	JButton bt_delete;
	
	ArrayList<Category> categoryList; 
	ArrayList<Product> productList; 
	ProductDAO productDAO;
	
	int category_index; //0
	int category_index2; //0
	int category_index3; //0
	int product_index; //0
	
	AdminMain adminMain;
	
	public AddProduct(AdminMain adminMain) {
		super(adminMain);
		productDAO=adminMain.productDAO;
		this.adminMain=adminMain;
		
		this.setLayout(null);
		
		la_name=new JLabel("상품명");
		la_price=new JLabel("가격");
		la_name2=new JLabel("상품명");
		la_price2=new JLabel("가격");
		t_name=new JTextField();
		t_price=new JTextField();
		c_category=new JComboBox();
		bt_regist = new JButton("등록");
		bt_list = new JButton("목록");
		
		c_category2=new JComboBox();
		c_product=new JComboBox();
		t_edit_name=new JTextField();
		t_edit_price=new JTextField();
		c_category3=new JComboBox();
		bt_edit = new JButton("수정");
		bt_delete = new JButton("삭제");
		
		categoryList = (ArrayList<Category>)adminMain.categoryDAO.selectAll();
		productList = (ArrayList<Product>)adminMain.productDAO.select(category_index2);
		
		la_name.setBounds(40, 100, 60, 50);
		la_price.setBounds(40, 170, 60, 50);
		t_name.setBounds(100, 100, 400, 50);
		t_price.setBounds(100, 170, 400, 50);
		c_category.setBounds(100, 240, 400, 50);
		bt_regist.setBounds(100, 310, 100, 50);
		bt_list.setBounds(220, 310, 100, 50);
		
		c_category2.setBounds(600, 100, 400, 50);
		c_product.setBounds(600, 170, 400, 50);
		la_name2.setBounds(540, 300, 60, 50);
		la_price2.setBounds(540, 380, 60, 50);
		t_edit_name.setBounds(600, 300, 400, 50);
		t_edit_price.setBounds(600, 380, 400, 50);
		c_category3.setBounds(600, 450, 400, 50);
		bt_edit.setBounds(1020, 450, 100, 50);
		bt_delete.setBounds(1020, 100, 100, 50);
		
		add(la_name);
		add(la_price);
		add(la_name2);
		add(la_price2);
		add(t_name);
		add(t_price);
		add(c_category);
		add(bt_regist);
		add(bt_list);
		
		add(c_category2);
		add(c_product);
		add(t_edit_name);
		add(t_edit_price);
		add(c_category3);
		add(bt_edit);
		add(bt_delete);
		
		createCombo();
		createProduct();
		
		//등록버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
		
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//데이터베이스 목록 가져오기 + 화면전환
				ProductPage ProductPage = (ProductPage)adminMain.page[AdminMain.PRODUCTPAGE];
				ProductPage.getList(); //db갱신
				
				adminMain.showHide(AdminMain.PRODUCTPAGE); //화면전환
			}
		});
		
		bt_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});
		
		c_category.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				category_index = c_category.getSelectedIndex();			}
				
		});
				
		c_category2.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				createProduct();
			}
		});
		
		c_category3.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				category_index3 = c_category3.getSelectedIndex();			
				System.out.println(category_index3);
			}
		});
		
		c_product.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				product_index = c_product.getSelectedIndex();			}
			
		});
		
		bt_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
	}
	
	
	public void regist() {
		Product product = new Product();
		if(t_name.getText().length()>=1&&t_price.getText().length()>=1) {
			product.setCategory(categoryList.get(category_index));
			product.setName(t_name.getText());
			product.setPrice(Integer.parseInt(t_price.getText()));
			int result=productDAO.insert(product);
			System.out.println(result);
			if(result>0) {
				JOptionPane.showMessageDialog(adminMain, "등록 성공");
				product.setProduct_idx(productDAO.getSequence());
			}
		} else {
			JOptionPane.showMessageDialog(adminMain, "글자를 적어주세요~");
		}
	}
	
	//카테고리2에서 오류 발생... (프로덕트와 연결되어 있다)
	public void createCombo() {
		c_category.removeAllItems();
		c_product.removeAllItems();
		//c_category2.removeAllItems();
		c_category3.removeAllItems();
		for(int i=0;i<categoryList.size();i++) {
			Category category = categoryList.get(i);
			Category category2 = categoryList.get(i);
			Category category3 = categoryList.get(i);
			c_category.addItem(category.getCategory_name());
			c_category2.addItem(category.getCategory_name());
			c_category3.addItem(category3.getCategory_name());
		}
		this.updateUI();
	}
	
	public void createProduct() {
		category_index2 = c_category2.getSelectedIndex();
		System.out.println("category_index2="+category_index2);
		productList=(ArrayList<Product>)adminMain.productDAO.select(categoryList.get(category_index2).getCategory_idx());
		c_product.removeAllItems();
		for(int i=0;i<productList.size();i++) {
			c_product.addItem(productList.get(i).getName());
			updateUI();
		}
	}

	public void edit() {
		Product product = new Product();
		if(t_edit_name.getText().length()>=1&&t_edit_price.getText().length()>=1) {
			product.setProduct_idx(productList.get(product_index).getProduct_idx());
			product.setName(t_edit_name.getText());
			product.setPrice(Integer.parseInt(t_edit_price.getText()));
			product.setCategory(categoryList.get(category_index3));
			product.setHit(productList.get(product_index).getHit());
			int n=productDAO.update(product);
			if(n>0) {
				JOptionPane.showMessageDialog(adminMain, "수정완료");
			} else {
				System.out.println("n="+n);
			}
			createProduct();
			updateUI();
			adminMain.productPage.getList();
		} else {
			JOptionPane.showMessageDialog(adminMain, "글자를 적어주세요~");
		}
	}
	
	public void delete() {
		Product product = productList.get(product_index);
		if(JOptionPane.showConfirmDialog(adminMain, "삭제하실래요?")==JOptionPane.OK_OPTION) {
			int result = productDAO.delete(productList.get(product_index).getProduct_idx());
			if(result>0) {
				c_product.removeItemAt(product_index);
				adminMain.productPage.getList();
				productList.remove(product_index);
			}
			updateUI();
			adminMain.productPage.p_product.updateUI();
		}
	}
	
	
	
}
