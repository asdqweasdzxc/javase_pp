package shop;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Page extends JPanel{
	
	AdminMain adminMain;
	
	public Page(AdminMain adminMain) {
		this.adminMain=adminMain;
		setPreferredSize(new Dimension(1200, 700));
		this.setBounds(0, 0, 1200, 700);
	}
	
}
 