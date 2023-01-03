package shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Menu extends JLabel{

	AdminMain adminMain;
	int targetPage;
	
	public Menu(String label, AdminMain adminMain, int targetPage) {
		super(label); //부모에게 전달
		this.adminMain=adminMain;
		this.targetPage=targetPage;
		
		setFont(new Font("Dotum", Font.BOLD, 30));
		this.setPreferredSize(new Dimension(400, 50));
		this.setForeground(Color.BLACK);
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				adminMain.showHide(targetPage);
				for(int i=0; i<adminMain.menu.length;i++) {
					if(i==targetPage) {
						adminMain.menu[i].setBackground(Color.GRAY);
					} else {
						adminMain.menu[i].setBackground(Color.LIGHT_GRAY);
					}
				}
			}
		});
	}
	
}
