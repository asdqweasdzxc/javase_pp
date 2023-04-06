package shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import util.PagingManager;

public class ProductPage extends Page{
	
	JPanel p_product; //상품
	JPanel p_inventory; //구매목록
	
	JPanel p_menu;
	JPanel p_category;
	JPanel p_list;
	
	JLabel la_product;
	JTextField t_product;
	
	JScrollPane s_category;
	JScrollPane s_list;
	JScrollPane s_inventory;
	
	BorderLayout border;
	
	ArrayList<Product> productList = new ArrayList<Product>();
	ArrayList<Row> rowList = new ArrayList<Row>();
	ArrayList<Category> categoryList = new ArrayList<Category>();
	ArrayList<Column> columnList = new ArrayList<Column>();
	ArrayList<Item> itemList = new ArrayList<Item>();
	
	PagingManager pagingManager_row = new PagingManager();
	PagingManager pagingManager_col = new PagingManager();
	
	JButton bt_plus;
	JButton bt_minus;
	JButton bt_remove;
	JButton bt_pay;
	
	int start=0;
	int total_price;
	JLabel la_total_price;
	
	public ProductPage(AdminMain adminMain) {
		super(adminMain);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		
		p_product = new JPanel();
		p_inventory = new JPanel();
		
		p_menu = new JPanel();
		p_category = new JPanel();
		p_list = new JPanel();
		
		JButton bt_category = new JButton("+카테고리");
		JButton bt_product = new JButton("+상품");
		
		//p_list의 크기는 동적으로 변화시킬 수 있어야 한다...
		p_list.setPreferredSize(new Dimension(800, 1000));
		
		s_category = new JScrollPane(p_category);
		s_list = new JScrollPane(p_list);
		s_inventory = new JScrollPane(p_inventory);
		
		la_product = new JLabel("상품");
		la_product.setFont(new Font("상품", Font.BOLD, 30));
		t_product = new JTextField(30);
		
		bt_plus=new JButton("+");
		bt_minus=new JButton("-");
		bt_remove=new JButton("제거");
		bt_pay=new JButton("결제");
		
		p_product.setLayout(border = new BorderLayout());
		p_product.setBackground(Color.CYAN);
		p_product.setPreferredSize(new Dimension(800, 700));
		p_product.setBounds(0, 0, 800, 700);
		
		p_inventory.setBackground(Color.PINK);
		p_inventory.setPreferredSize(new Dimension(380, 700));
		p_inventory.setLayout(new FlowLayout());
		p_inventory.setBounds(800, 0, 380, 700);
		
		p_menu.setPreferredSize(new Dimension(800, 70));
		p_menu.setBounds(0, 0, 800, 70);
		p_menu.setLayout(null);
		
		s_category.setPreferredSize(new Dimension(800, 70));
		s_category.setBounds(0, 67, 800, 60);
		s_category.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		s_list.setPreferredSize(new Dimension(800, 560));
		s_list.setBounds(0, 140, 800, 580);
		s_list.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		s_inventory.setPreferredSize(new Dimension(380, 700));
		s_inventory.setBounds(800, 0, 380, 500);
		s_inventory.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		la_product.setBounds(0, 0, 80, 70);
		t_product.setBounds(100, 0, 400, 70);
		t_product.setFont(new Font("", Font.BOLD, 30));
		bt_category.setBounds(520, 0, 100, 60);
		bt_product.setBounds(650, 0, 100, 60);
		
		bt_plus.setBounds(850, 550, 100, 50);
		bt_minus.setBounds(1000, 550, 100, 50);
		bt_remove.setBounds(850, 620, 100, 50);
		bt_pay.setBounds(1000, 620, 100, 50);
		
		p_menu.add(la_product);
		p_menu.add(t_product);
		p_menu.add(bt_category);
		p_menu.add(bt_product);
		
		p_product.add(p_menu, border.NORTH);
		p_product.add(s_category, border.CENTER);
		p_product.add(s_list, border.SOUTH);
		
		add(bt_plus);
		add(bt_minus);
		add(bt_remove);
		add(bt_pay);
		
		add(p_product);
		add(s_inventory);
		
		getList();
	
		bt_category.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminMain.showHide(AdminMain.ADDCATEGORY);
			}
		});
		
		bt_product.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminMain.showHide(AdminMain.ADDPRODUCT);
			}
		});
		
		bt_plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<itemList.size();i++) {
					if(itemList.get(i).flag==true) {
						itemList.get(i).count++;
						itemList.get(i).price=itemList.get(i).count*itemList.get(i).product.getPrice();
						
						itemList.get(i).la_hit.setText("x"+itemList.get(i).count);
						itemList.get(i).la_price.setText(Integer.toString(itemList.get(i).count*itemList.get(i).product.getPrice())+"원");
					}
				}
			}
		});
		bt_minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<itemList.size();i++) {
					if(itemList.get(i).flag==true&&itemList.get(i).count>=2) {
						itemList.get(i).count--;
						itemList.get(i).price=itemList.get(i).count*itemList.get(i).product.getPrice();
						
						itemList.get(i).la_hit.setText("x"+itemList.get(i).count);
						itemList.get(i).la_price.setText(Integer.toString(itemList.get(i).count*itemList.get(i).product.getPrice())+"원");
					} else if(itemList.get(i).flag==true&&itemList.get(i).count<2) {
						p_inventory.remove(itemList.get(i));
						p_inventory.updateUI();
						itemList.get(i).row.flag=false;
						itemList.remove(i);
					}
				}
			}
		});
		bt_remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<itemList.size();i++) {
					if(itemList.get(i).flag==true) {
						p_inventory.remove(itemList.get(i));
						p_inventory.updateUI();
						itemList.get(i).row.flag=false;
						itemList.remove(i);
					}
				}
			}
		});
		
		bt_pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminMain.showHide(5);
				for(int i=0;i<start;i++) {
					adminMain.paymentPage.p_list.remove(itemList.get(i));
				}
				adminMain.paymentPage.p_total.removeAll();
				for(int i=start;i<itemList.size();i++) {
					Item item = itemList.get(i);
					System.out.println("아이템리스트"+itemList.size());
					adminMain.paymentPage.p_list.add(item);
					itemList.get(i).row.flag=false;
					start=itemList.size();
					System.out.println(start);
					total_price+=itemList.get(i).price;
				}
				System.out.println("총액은"+total_price);
				la_total_price=new JLabel(Integer.toString(total_price)+"원 입니다.");
				la_total_price.setFont(new Font("Dotum", Font.BOLD, 30));
				la_total_price.setBounds(50, 120, 400, 60);
				adminMain.paymentPage.p_total.add(la_total_price);
				
				total_price=0;
			}
		});
		
		t_product.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				search(t_product.getText());
			}
			public void keyPressed(KeyEvent e) {
			}
		});
	}
	
	public void search(String text) {
		p_list.removeAll();
		rowList.removeAll(rowList);
		productList = (ArrayList) adminMain.productDAO.search(text);
		createRow();
		p_product.updateUI();
	}
	
	//row 생성하기
	public void createRow(){
		
		for(int i=0;i<productList.size();i++) {
			Product product = productList.get(i);
			Row row = new Row(product, this);
			rowList.add(row);
			p_list.add(row);
		}
		
		this.updateUI();
	}
	
	public void createColumn() {
		//System.out.println("categorysize="+categoryList.size());
		for(int i=0;i<categoryList.size();i++) {
			Category category = categoryList.get(i);
			Column column = new Column(category, this);
			columnList.add(column);
			p_category.add(column);
		}
		this.updateUI();
	}
	
	public void getList() {
		
		p_list.removeAll();
		p_category.removeAll();
		
		rowList.removeAll(rowList);
		columnList.removeAll(columnList);
		
		categoryList = (ArrayList) adminMain.categoryDAO.selectAll();
		productList = (ArrayList) adminMain.productDAO.selectAll();
		
		createColumn();
		createRow();
		
		p_product.updateUI();
	}
	
	public void getProduct(int category_idx) {
		p_list.removeAll();
		rowList.removeAll(rowList);
		
		productList = (ArrayList) adminMain.productDAO.select(category_idx);
		createRow();
		
		p_product.updateUI();
	}
	
	
}
