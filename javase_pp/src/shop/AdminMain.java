package shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.DBManager;


public class AdminMain extends JFrame{

	ProductPage productPage;
	StockPage stockPage;
	AnalysisPage analysisPage;
	AddCategory addCategory;
	AddProduct addProduct;
	PaymentPage paymentPage;
	
	JPanel p_container = new JPanel();
	JPanel p_south = new JPanel();
	
	Page[] page = new Page[6];
	Menu[] menu = new Menu[3];
	
	public static final int PRODUCTPAGE=0;
	public static final int STOCKPAGE=1;
	public static final int ANALYSISPAGE=2;
	public static final int ADDCATEGORY=3;
	public static final int ADDPRODUCT=4;
	public static final int PAYMENTPAGE=5;
	
	DBManager dbManager=DBManager.getInstance();
	
	CategoryDAO categoryDAO = new CategoryDAO();
	ProductDAO productDAO=new ProductDAO();
	
	public AdminMain() {
		p_container=new JPanel();
		p_south=new JPanel();
		
		this.setLayout(null);
		
		p_container.setPreferredSize(new Dimension(1200, 700));
		p_container.setBackground(Color.YELLOW);
		p_container.setBounds(0, 0, 1200, 700);
		p_south.setPreferredSize(new Dimension(1200,50));
		//p_south.setBackground(Color.MAGENTA);
		p_south.setBounds(0, 700, 1200, 50);
		p_south.setLayout(null);
		
		
		add(p_container);
		add(p_south);
		
		createMenu();
		createPage();
		showHide(PRODUCTPAGE);
		
		setSize(1200,788);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
	}
	
	public void createPage() {
		page[0] = new ProductPage(this);
		page[1] = new StockPage(this);
		page[2] = new AnalysisPage(this); 
		page[3] = new AddCategory(this);
		page[4] = new AddProduct(this);
		page[5] = new PaymentPage(this);
		
		productPage=(ProductPage) page[0];
		stockPage=(StockPage) page[1];
		analysisPage=(AnalysisPage) page[2];
		addCategory=(AddCategory) page[3];
		addProduct=(AddProduct) page[4];
		paymentPage=(PaymentPage) page[5];
		for(int i=0;i<page.length;i++) {
			p_container.add(page[i]);
		}
	}
	
	public void createMenu() {
		menu[0]=new Menu("상품", this, PRODUCTPAGE);
		menu[1]=new Menu("재고 관리", this, STOCKPAGE);
		menu[2]=new Menu("매장 분석", this, ANALYSISPAGE);
		
		for(int i=0;i<menu.length;i++) {
			p_south.add(menu[i]);
			menu[i].setBounds(400*i, 0, 400, 50);
		}
		menu[PRODUCTPAGE].setBackground(Color.GRAY);
	}
	
	public void showHide(int n) {
		for(int i=0;i<page.length;i++) {
			if(i==n) {
				page[i].setVisible(true);
			} else {
				page[i].setVisible(false);
			}
		}
	}
				
	
	public static void main(String[] args) {
		new AdminMain();
	}
}














