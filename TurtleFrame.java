package turtle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/** タートルを表示するウィンドウのクラス。 */

public class TurtleFrame extends JPanel implements ActionListener, ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	JRadioButtonMenuItem[] speedMenu;
	static String[] speedMenuString = { "no turtle", "fast", "normal", "slow" };

	Vector<Line> history = new Vector<Line>(10);
	Vector<Turtle> turtles = new Vector<Turtle>(10);
	boolean mesh = false;

	/** TurtleFrame を、デフォルトの大きさ(400x400) で作成する。 */
	public TurtleFrame(int width, int height) {
		super();
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.white);
		setForeground(Color.black);

		frame = new JFrame("Turtle");
		JFrame.setDefaultLookAndFeelDecorated(true);

		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File", true);
		menubar.add(file);
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		quit.setActionCommand("quit");
		file.add(quit);

		JMenu speed = new JMenu("Speed", true);
		menubar.add(speed);
		speedMenu = new JRadioButtonMenuItem[speedMenuString.length];
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < speedMenuString.length; i++) {
			speedMenu[i] = new JRadioButtonMenuItem(speedMenuString[i], false);
			speedMenu[i].addItemListener(this);
			speed.add(speedMenu[i]);
			bg.add(speedMenu[i]);
		}
		speedMenu[Turtle.speedNormal].doClick();
		frame.setJMenuBar(menubar);

		Container c = frame.getContentPane();
		c.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(3); // EXIT_ON_CLOSE
		frame.setVisible(true);
	}

	/** TurtlePanel を、デフォルトの大きさ(400x400) で作成する。 */
	public TurtleFrame() {
		this(400, 400);
	}

	// menu bar event handler
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("quit"))
			System.exit(0);
	}

	// menu event handler
	@Override
	public void itemStateChanged(ItemEvent e) {
		JRadioButtonMenuItem s = (JRadioButtonMenuItem) e.getItem();
		for (int i = 0; i < speedMenuString.length; i++) {
			if (s == speedMenu[i]) {
				if (i > 0)
					Turtle.speedAll(i);
				else
					Turtle.withTurtleAll = false;
				break;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mesh) {
			drawmesh(g);
		}
		for (int i = 0; i < history.size(); i++) {
			Line line = history.elementAt(i);
			g.setColor(line.c);
			g.drawLine(line.bx, line.by, line.ex, line.ey);
		}
		for (int i = 0; i < turtles.size(); i++) {
			Turtle t = turtles.elementAt(i);
			t.show(g);
		}
	}

	// class for line segment
	class Line {
		public int bx;
		public int by;
		public int ex;
		public int ey;
		public Color c;

		public Line(int bx, int by, int ex, int ey, Color c) {
			this.bx = bx;
			this.by = by;
			this.ex = ex;
			this.ey = ey;
			this.c = c;
		}
	}

	/** t をこのフレームの上に追加する。 */
	public void add(Turtle t) {
		turtles.addElement(t);
		t.setFrame(this);
		repaint();
	}

	/** t をこのフレームから削除する。 */
	public void remove(Turtle t) {
		turtles.removeElement(t);
		repaint();
	}

	/** 今までに書かれた全ての線を消す。 */
	public void clear() {
		history.clear();
		repaint();
	}

	void addLineElement(int xx, int yy, int x, int y, Color c) {
		history.addElement(new Line(xx, yy, x, y, c));
	}

	static Color meshDark = new Color(230, 230, 100);
	static Color meshLight = new Color(230, 230, 230);

	void drawmesh(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		g.setColor(meshLight);
		for (int x = 0; x < width; x += 10) {
			g.drawLine(x, 0, x, height);
		}
		for (int x = 0; x < height; x += 10) {
			g.drawLine(0, x, width, x);
		}
		g.setColor(meshDark);
		for (int x = 0; x < width; x += 50) {
			g.drawLine(x, 0, x, height);
		}
		for (int x = 0; x < height; x += 50) {
			g.drawLine(0, x, width, x);
		}
	}

	public void addMesh() {
		mesh = true;
		repaint();
	}
}
