package shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

//페이징 처리를 위해, JTable을 사용하지 않고 손수 행을 재정의하여 사용해보자
public class Column extends JPanel{
	Category category;
	ProductPage productPage;
	
	JLabel la_name;
	JLabel la_price;
	Font font;
	
	public Column(Category category, ProductPage productPage) {
		this.category=category;
		this.productPage=productPage;
		Column column=this;
		
		
		Border border = new LineBorder(Color.BLACK);
		this.setBorder(border); //보더적용
		this.setPreferredSize(new Dimension(120, 45));
		
		//라벨 생성
		la_name=new JLabel(category.getCategory_name()); //카테고리명
		
		//부착하기전에 라벨들의 간격조정을 위한 너비를 설정
		la_name.setPreferredSize(new Dimension(120, 45));
		la_name.setFont(new Font("", Font.BOLD, 20));
		la_name.setHorizontalAlignment(JLabel.CENTER);
		
		add(la_name);
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				column.productPage.getProduct(column.category.getCategory_idx());
				System.out.println(column.category.getCategory_idx());
			}
		});
		
	}


	public void actionPerformed(ActionEvent e) {
		productPage.getProduct(this.category.getCategory_idx());
		
		//System.out.println("qweqwe");
	}
	
	
}
