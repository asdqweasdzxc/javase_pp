package shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Item extends JPanel{
	
	Product product;
	ProductPage productPage;
	Item item=this;
	Row row;
	String product_name;
	int count=1;
	int price;
	boolean flag=false;
	boolean flag2=false;
	Color color;
	
	JLabel la_name;
	JLabel la_hit;
	JLabel la_price;
	
	
	public Item(Product product, ProductPage productPage, Row row) {
		this.product=product;
		this.productPage=productPage;
		this.row=row;
		
		price=count*product.getPrice();
		color=this.getBackground();
		
		Border border = new LineBorder(Color.GRAY);
		this.setBorder(border); //보더적용
		this.setPreferredSize(new Dimension(320, 70));
		
		//라벨 생성
		la_name=new JLabel(product.getName());
		la_hit=new JLabel("x"+count);
		la_price = new JLabel(Integer.toString(count*product.getPrice())+"원");
		
		la_name.setPreferredSize(new Dimension(170, 70));
		la_hit.setPreferredSize(new Dimension(30, 70));
		la_price.setPreferredSize(new Dimension(100, 70));
		
		add(la_name);
		add(la_hit);
		add(la_price);
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				use();
			}
		});
	}
	
	public void use() {
		flag=!flag;
		if(flag==true) {
			this.setBackground(Color.YELLOW);
		} else {
			this.setBackground(color);
		}
	}
	
}
