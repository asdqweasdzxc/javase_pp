package shop;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaymentPage extends Page{

	JPanel p_list;
	JPanel p_total;
	
	JLabel la_total;
	
	public PaymentPage(AdminMain adminMain) {
		super(adminMain);
		this.setLayout(null);
		
		p_list=new JPanel();
		p_total=new JPanel();
		
		la_total=new JLabel("결제 금액은");

		p_list.setBounds(0, 0, 400, 700);
		p_list.setBackground(Color.white);
		
		p_total.setLayout(null);
		
		la_total.setBounds(50, 50, 200, 60);
		la_total.setFont(new Font("", Font.BOLD, 30));
		
		p_total.setBounds(400, 0, 800, 700);
		
		p_total.add(la_total);
		
		add(p_list);
		add(p_total);
				
	}

}
