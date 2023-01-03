package shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

//페이징 처리를 위해, JTable을 사용하지 않고 손수 행을 재정의하여 사용해보자
public class Row extends JPanel{
	int count; //개수
	Product product;
	ProductPage productPage;
	
	JLabel la_name;
	JLabel la_price;
	Item item;
	boolean flag=false;
	
	public Row(Product product, ProductPage productPage) {
		this.product=product;
		this.productPage=productPage;
		
		Border border = new LineBorder(Color.GRAY);
		this.setBorder(border); //보더적용
		this.setPreferredSize(new Dimension(700, 70));
		
		//라벨 생성
		la_name=new JLabel(product.getName()); //작성자 채우기
		la_price = new JLabel(Integer.toString(product.getPrice())+"원"); //작성일 채우기
		
		//부착하기전에 라벨들의 간격조정을 위한 너비를 설정
		la_name.setPreferredSize(new Dimension(120, 70));
		la_price.setPreferredSize(new Dimension(100, 70));
		
		add(la_name);
		add(la_price);
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				createItem();
			}
		});
	}
	
	public void createItem() {
		if(flag==false) {
			productPage.p_inventory.add(item=new Item(product, productPage, this));
			productPage.itemList.add(item);
			flag=true;
		} else {
			item.count++;
			item.price=item.count*product.getPrice();
			
			item.la_hit.setText("x"+item.count);
			item.la_price.setText(Integer.toString(item.count*product.getPrice())+"원");
		}
			
		productPage.p_inventory.updateUI();
	}
	
}
