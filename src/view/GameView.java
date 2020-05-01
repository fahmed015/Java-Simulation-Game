package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.people.Citizen;
import simulation.Address;

public class GameView extends JFrame {

	private JPanel pnl1;
	private JPanel pnl2;
	private JPanel pnl3;
	private JPanel pnl4;
	private JPanel pnl5;
    private JPanel Center;
	private JPanel units;
	private JPanel Citizens[][] = new JPanel[10][10];
	private JPanel units2[][] = new JPanel[10][10];
	private JPanel Building[][] = new JPanel[10][10];

	private JPanel struck;
	private JPanel active;
	private JPanel died;
	JPanel[][] panelHolder = new JPanel[10][10];

	private endbutton endcycle;
	private startgamebutton start;
	private JLabel cycle;
	private JLabel deadpeople;
	private JLabel s;
	private JLabel a;
	private JLabel d;

	private JLabel st;
	private JLabel ac;
	private JLabel di;

	private int cyclecounter = 0;
	private int deadpeoplecounter = 0;

	public GameView() {

		this.setTitle("Your Game");
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(this.MAXIMIZED_BOTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setSize(screenWidth,screenHeight);

		pnl1 = new JPanel();
		pnl2 = new JPanel();
		pnl3 = new JPanel();
		pnl4 = new JPanel();
		pnl5 = new JPanel();
		struck = new JPanel();
		active = new JPanel();
		died = new JPanel();
		units = new JPanel();
		Center= new JPanel();
		Center.setLayout(new GridLayout(2,1));

		this.setLayout(new BorderLayout());
		pnl1.setLayout(new GridLayout(3, 1));
		pnl2.setLayout(new GridLayout(10, 10, 5, 5));
		pnl3.setLayout(new GridLayout(3, 1));
		pnl5.setLayout(new BorderLayout());
		struck.setLayout(new BorderLayout());
		active.setLayout(new BorderLayout());
		died.setLayout(new BorderLayout());


		pnl1.setOpaque(true);
		pnl2.setOpaque(true);
		// pnl3.setOpaque(true);
		// pnllmain.setOpaque(true);
		// pnl4.setOpaque(true);
		pnl5.setOpaque(true);
		// struck.setOpaque(true);
		active.setOpaque(true);
		died.setOpaque(true);

		// test = new JButton();
		endcycle = new endbutton();
		start = new startgamebutton();
		cycle = new JLabel();
		deadpeople = new JLabel();
		s = new JLabel();
		a = new JLabel();
		d = new JLabel();
		st = new JLabel();
		ac = new JLabel();
		di = new JLabel();

		struck.add(st, BorderLayout.CENTER);
		active.add(ac, BorderLayout.CENTER);
		died.add(di, BorderLayout.CENTER);

		endcycle.setText("End Cycle");
		start.setText("Start Game");
		cycle.setText("Cycle : " + cyclecounter);
		deadpeople.setText("Dead People : " + deadpeoplecounter);

		s.setText("Struck Disasters");
		a.setText("Active Disasters");
		d.setText("Died");
		s.setForeground(Color.YELLOW);
		a.setForeground(Color.YELLOW);
		d.setForeground(Color.YELLOW);
		st.setForeground(Color.white);
		ac.setForeground(Color.white);
		di.setForeground(Color.white);
		pnl3.setBackground(Color.BLACK);
		struck.setBackground(Color.BLACK);

		endcycle.setSize(700, 100);
		start.setSize(700, 100);
		cycle.setSize(200, 50);
		deadpeople.setSize(100, 50);
		// test.setSize(200, 100);
		pnl5.setPreferredSize(new Dimension(80, 80));
		pnl3.setPreferredSize(new Dimension(150, 1000));
		

		pnl4.setPreferredSize(new Dimension(100, 50));
		endcycle.setPreferredSize(new Dimension(200, 50));

		endcycle.setBackground(Color.RED);
		endcycle.setForeground(Color.WHITE);
		start.setBackground(Color.GREEN);
		start.setForeground(Color.WHITE);

		endcycle.setFont(new Font("Arial", Font.BOLD, 30));
		start.setFont(new Font("Arial", Font.BOLD, 30));
		st.setFont(new Font("Arial", Font.ITALIC, 12));
		ac.setFont(new Font("Arial", Font.ITALIC, 12));
		di.setFont(new Font("Arial", Font.ITALIC, 12));

		s.setFont(new Font("Arial", Font.ITALIC, 20));
		a.setFont(new Font("Arial", Font.ITALIC, 20));
		d.setFont(new Font("Arial", Font.ITALIC, 20));
		cycle.setFont(new Font("Arial", Font.BOLD, 30));
		deadpeople.setFont(new Font("Arial", Font.BOLD, 30));
		
	
		pnl5.add(cycle,BorderLayout.WEST);
		pnl5.add(deadpeople,BorderLayout.EAST);
		cycle.setForeground(Color.YELLOW);
		deadpeople.setForeground(Color.YELLOW);
		pnl5.setBackground(Color.BLACK);
		pnl4.setBackground(Color.BLACK);
	
		pnl4.add(endcycle);
		pnl4.add(start);

		getContentPane().add(pnl4, BorderLayout.PAGE_END);
		getContentPane().add(pnl5, BorderLayout.PAGE_START);
		getContentPane().add(pnl2, BorderLayout.CENTER);
		getContentPane().add(pnl3, BorderLayout.WEST);

		struck.setBackground(Color.BLACK);
		active.setBackground(Color.BLACK);
		died.setBackground(Color.BLACK);

		struck.add(s, BorderLayout.PAGE_START);
		active.add(a, BorderLayout.PAGE_START);
		died.add(d, BorderLayout.PAGE_START);

		pnl3.add(struck);
		pnl3.add(active);
		pnl3.add(died);

		JScrollPane pane1 = new JScrollPane(struck, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		pnl3.add(pane1);

		JScrollPane pane2 = new JScrollPane(active, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnl3.add(pane2);
		
		JScrollPane pane3 = new JScrollPane(died, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		pnl3.add(pane3);
		
		
		
		
		
		pnl2.setBackground(Color.BLACK);

		for (int m = 0; m < 10; m++) {
			for (int n = 0; n < 10; n++) {
				panelHolder[m][n] = new JPanel();
				panelHolder[m][n].setPreferredSize(getMaximumSize());

				panelHolder[m][n].setBorder(BorderFactory.createCompoundBorder());

				if (m == 0 && n == 0) {
					panelHolder[m][n].setBackground(Color.BLACK);

					panelHolder[m][n].setLayout(new BorderLayout());
					units = new JPanel();
					units.setLayout(new FlowLayout());
					units.setBackground(Color.GRAY);

					Citizens[m][n] = new JPanel();
					Citizens[m][n].setBackground(Color.GRAY);
					panelHolder[m][n].add(Citizens[m][n], BorderLayout.PAGE_START);

					 //JLabel c = new JLabel("(" + m + "," + n + ")");
					 //panelHolder[m][n].add(c, BorderLayout.SOUTH);
					panelHolder[0][0].add(units, BorderLayout.CENTER);
					pnl2.add(panelHolder[m][n]);

				}

				else {
					panelHolder[m][n].setBackground(Color.BLACK);
					
					Building[m][n] = new JPanel();
					Building[m][n].setBackground(Color.GRAY);
					panelHolder[m][n].setLayout(new BorderLayout());
					Citizens[m][n] = new JPanel();
					Citizens[m][n].setLayout(new FlowLayout());
					Citizens[m][n].setBackground(Color.GRAY);
					units2[m][n] = new JPanel();
					units2[m][n].setLayout(new FlowLayout());
					units2[m][n].setBackground(Color.GRAY);

					//JLabel c = new JLabel("(" + m + "," + n + ")");
					panelHolder[m][n].add(Citizens[m][n], BorderLayout.NORTH);
					//panelHolder[m][n].add(c, BorderLayout.SOUTH);
					panelHolder[m][n].add(units2[m][n], BorderLayout.EAST);
					panelHolder[m][n].add(Building[m][n], BorderLayout.CENTER);

					pnl2.add(panelHolder[m][n]);
				}

			}

		}
		this.getContentPane().validate();
		this.validate();
		this.setVisible(true);

	}

	public endbutton getEndcycle() {
		return endcycle;
	}

	public JLabel getCycle() {
		return cycle;
	}

	public void setCycle(JLabel cycle) {
		this.cycle = cycle;
	}

	public void addUnit(unitbutton unit, int x, int y) {

		if (x == 0 && y == 0) {

			units.add(unit);
			units.setBackground(Color.YELLOW);
			Citizens[x][y].setBackground(Color.YELLOW);
			panelHolder[x][y].setBackground(Color.YELLOW);
			panelHolder[x][y].add(units, BorderLayout.CENTER);

		}

		else {

			units2[x][y].add(unit);
			units2[x][y].setBackground(Color.YELLOW);
			panelHolder[x][y].add(units2[x][y], BorderLayout.EAST);

		}

	}

	public void addBuilding(buildingbutton Buildingb, int x, int y) {
		Building[x][y].add(Buildingb);
		Building[x][y].setBackground(Color.YELLOW);
		Citizens[x][y].setBackground(Color.YELLOW);
		units2[x][y].setBackground(Color.YELLOW);
		panelHolder[x][y].setBackground(Color.YELLOW);
		
		panelHolder[x][y].add(Building[x][y], BorderLayout.CENTER);

	}

	public void addCitizen(citizenbutton Citizen, int x, int y) {
		Citizens[x][y].revalidate();
		Citizens[x][y].repaint();
		if(x==0&& y==0) {
			units.setBackground(Color.YELLOW);
			Citizens[x][y].setBackground(Color.YELLOW);
		}
		else {
			units2[x][y].setBackground(Color.YELLOW);
			Citizens[x][y].setBackground(Color.YELLOW);
			
			
			Building[x][y].setBackground(Color.YELLOW);
			
			
		}
		
		
		panelHolder[x][y].setBackground(Color.YELLOW);

		Citizens[x][y].add(Citizen);
		panelHolder[x][y].add(Citizens[x][y], BorderLayout.NORTH);

	}

	public void updatestruck(String disaster, String location, String target) {

		st.setText("<html>" + st.getText() + "<br>" + "———" + cycle.getText() + "<br>" + disaster
				+ " has targeted " + "<br>" + target + "<br>" + " in location " + location + "<html>");

	}

	public void updateactive(String disaster) {

		ac.setText("<html>" + ac.getText() + "<br>" + " - " + disaster + "<html>");

	}

	public void updatedied(Citizen c) {

		di.setText("<html>" + di.getText() + "<br>" + " - " + c.getName() + ",  " + "(" + c.getLocation().getX() + ","
				+ c.getLocation().getY() + ")" + "<html>");

	}

	public JLabel getDeadpeople() {
		return deadpeople;
	}

	public void setDeadpeople(JLabel deadpeople) {
		this.deadpeople = deadpeople;
	}

	public startgamebutton getStart() {
		return start;
	}

	public void setStart(startgamebutton start) {
		this.start = start;
	}

	public void erasecitizen() {

		for (int m = 0; m < 10; m++) {

			for (int n = 0; n < 10; n++) {

				

					Citizens[m][n].removeAll();
					Citizens[m][n].revalidate();
					Citizens[m][n].repaint();

				

			}

		}

	}

	public void erasebuilding() {

		for (int m = 0; m < 10; m++) {

			for (int n = 0; n < 10; n++) {

				if (m != 0 && n != 0) {

					Building[m][n].removeAll();
					Building[m][n].revalidate();
					Building[m][n].repaint();

				}

			}

		}

	}

	public void eraseunit() {

		for (int i = 0; i < 10; i++) {

			for (int j = 0; j < 10; j++) {

				if (i == 0 && j == 0) {

					units.removeAll();
					units.revalidate();
					units.repaint();

				} else {
					units2[i][j].removeAll();
					units2[i][j].revalidate();
					units2[i][j].repaint();
				}

			}

		}

	}
	public void erasebutton(unitbutton unit) {
		unit.removeAll();
		unit.revalidate();
		unit.repaint();
		
	}

	public void eraseactivedisasters() {
		ac.setText(" ");

	}

	public static void main(String[] args) {
		GameView g = new GameView();

	}

}
