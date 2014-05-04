package turtle;

import java.awt.*;
import java.lang.Math;

/** �^�[�g���̃N���X�B */
public class Turtle implements Cloneable {
	/**
	 * �����Afalse �Ȃ�A�X�̃^�[�g���� withTurtle �̒l�Ɋւ�炸�A�S�Ẵ^�[�g���ɍ����ȕ`����s���B �����l�� true�B
	 */
	public static boolean withTurtleAll = true;
	public Color tcolor = Color.green;
	public double tscale = 0.4;

	// animation parameters
	private static double rotateStep = 5.0 * Math.PI / 180.0;
	private static int moveStep = 5;
	private static int[] speedStep = { 100000, 20, 5, 2 };

	// turtle animation speed constants
	public final static int speedNoTurtle = 0;
	public final static int speedFast = 1;
	public final static int speedNormal = 2;
	public final static int speedSlow = 3;

	// set the menu
	public static void speedAll(int s) {
		if (s <= 0 || s >= speedStep.length)
			return;
		rotateStep = speedStep[s] * Math.PI / 180.0;
		moveStep = speedStep[s];
	}

	private TurtleFrame f; // on this TurtleFrame
	private double angle; // turtle current angle
	private double x, y; // turtle current position
	private double dx, dy; // dx = sin(angle), dy = -cos(angle)
	private boolean penDown; // pen status (up or down)
	private Color c = Color.black; // pen color
	private int turtleType = 0;
	// turtle animation rubber line
	private int rx, ry;
	private boolean rubber = false;
	private int moveWait = 20;
	private int rotateWait = 20;

	/** (x, y) �Ƃ������W�� angle �̊p�x�ŁATurtle ���쐬�B */
	public Turtle(int x, int y, int ia) {
		this.x = ((double) x + 0.5);
		this.y = ((double) y + 0.5);
		setangle((double) ia * Math.PI / 180.0);
		penDown = true;
	}

	/** (200,200) �Ƃ������W�� 0 �x�̊p�x�ŁATurtle ���쐬�B */
	public Turtle() {
		this(200, 200, 0);
	}

	// turtle
	public static final int turtleFig[][] = {
			{ -12, -6, -12, 6, 0, 18, 12, 6, 12, -6, 0, -18, -12, -6 },
			{ -18, -12, -12, -6 },
			{ -6, -24, 0, -18, 6, -24 },
			{ 12, -6, 18, -12 },
			{ 12, 6, 18, 12 },
			{ -6, 24, 0, 18, 6, 24 },
			{ -18, 12, -12, 6 },
			{ -18, 12, -18, -12, -6, -24, 6, -24, 18, -12, 18, 12, 6, 24, -6,
					24, -18, 12 }, { -15, -15, -18, -24, -9, -21 },
			{ 9, -21, 18, -24, 15, -15 }, { 15, 15, 18, 24, 9, 21 },
			{ -9, 21, -18, 24, -15, 15 }, { -3, 24, 0, 30, 3, 24 },
			{ -6, -24, -12, -36, -6, -48, 6, -48, 12, -36, 6, -24 } };
	public static final int turtleRFig[][] = {
			{ -12, -6, -12, 6, 0, 18, 12, 6, 12, -6, 0, -18, -12, -6 },
			{ -18, -12, -12, -6 },
			{ -6, -24, 0, -18, 6, -24 },
			{ 12, -6, 18, -12 },
			{ 12, 6, 18, 12 },
			{ -6, 24, 0, 18, 6, 24 },
			{ -18, 12, -12, 6 },
			{ -18, 12, -18, -12, -6, -24, 6, -24, 18, -12, 18, 12, 6, 24, -6,
					24, -18, 12 }, { -15, -15, -24, -18, -9, -21 },
			{ -9, 21, -24, 18, -15, 15 }, { -3, 24, -3, 30, 3, 24 },
			{ -6, -24, -6, -36, 0, -48, 12, -48, 18, -36, 6, -24 },
			{ 9, -21, 18, -30, 15, -15 }, { 15, 15, 18, 30, 9, 21 } };
	public static final int turtleLFig[][] = {
			{ -12, -6, -12, 6, 0, 18, 12, 6, 12, -6, 0, -18, -12, -6 },
			{ -18, -12, -12, -6 },
			{ -6, -24, 0, -18, 6, -24 },
			{ 12, -6, 18, -12 },
			{ 12, 6, 18, 12 },
			{ -6, 24, 0, 18, 6, 24 },
			{ -18, 12, -12, 6 },
			{ -18, 12, -18, -12, -6, -24, 6, -24, 18, -12, 18, 12, 6, 24, -6,
					24, -18, 12 }, { -15, -15, -18, -30, -9, -21 },
			{ -9, 21, -18, 30, -15, 15 }, { -3, 24, 3, 30, 3, 24 },
			{ -6, -24, -18, -36, -12, -48, 0, -48, 6, -36, 6, -24 },
			{ 9, -21, 24, -18, 15, -15 }, { 15, 15, 24, 18, 9, 21 } };
	protected int[][] turtleC = turtleFig;
	protected int[][] turtleR = turtleRFig;
	protected int[][] turtleL = turtleLFig;

	// draw animation turtle
	private void turtleDraw(Graphics g, int[][] data) {
		int ix = (int) x, iy = (int) y;
		g.setColor(tcolor);
		for (int i = 0; i < data.length; i++) {
			int px = 0, py = 0;
			for (int j = 0; j < data[i].length; j += 2) {
				int kx = data[i][j], ky = data[i][j + 1];
				int nx = (int) ((kx * (-dy) + ky * (-dx)) * tscale);
				int ny = (int) ((kx * dx + ky * (-dy)) * tscale);
				if (j > 0)
					g.drawLine(ix + px, iy + py, ix + nx, iy + ny);
				px = nx;
				py = ny;
			}
		}
		g.setColor(c);
		g.fillOval(ix - 1, iy - 1, 2, 2);
	}

	void setFrame(TurtleFrame f) {
		this.f = f;
	}

	void show(Graphics g) {
		if (rubber) {
			g.setColor(c);
			g.drawLine(rx, ry, (int) x, (int) y);
		}
		if (withTurtleAll) {
			switch ((turtleType / 2) % 4) {
			case 0:
			case 2:
				turtleDraw(g, turtleC);
				break;
			case 1:
				turtleDraw(g, turtleR);
				break;
			case 3:
				turtleDraw(g, turtleL);
				break;
			}
		}
	}

	private void fcheck() {
		if (f == null) {
			System.out
					.println("Turtle �ɑ΂��� fd �Ȃǂ��Ăяo���܂��ɁCTurtleFrame �� add ���Ă��������B");
			System.exit(1);
		}
	}

	// turtle animation update
	private void turtleShow(int wait) {
		turtleType++;
		f.repaint();
		if (withTurtleAll) {
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
			}
		}
	}

	/** n �����O�ɐi�ށB */
	public void fd(int n) {
		dfd(n);
	}

	public void dfd(double n) {
		double xx = x;
		double yy = y;
		int back = 1;
		if (n < 0) {
			back = -1;
			n = -n;
		}
		fcheck();
		if (penDown) {
			rx = (int) x;
			ry = (int) y;
			rubber = true;
		}
		for (int i = moveStep; i < n; i += moveStep) {
			x = xx + back * dx * i;
			y = yy + back * dy * i;
			turtleShow(moveWait);
		}
		x = xx + back * dx * n;
		y = yy + back * dy * n;
		if (penDown) {
			f.addLineElement((int) xx, (int) yy, (int) x, (int) y, c);
			rubber = false;
		}
		turtleShow(moveWait);
	}

	/** n �������ɐi�ށB */
	public void bk(int n) {
		fd(-n);
	}

	private void setangle(double a) {
		angle = a;
		dx = Math.sin(a);
		dy = -Math.cos(a);
	}

	// set turtle angle
	private void turtleAngle(double a) {
		dx = Math.sin(a);
		dy = -Math.cos(a);
		turtleShow(rotateWait);
	}

	/** n �x�����E�ɉ��B */
	public void rt(int ia) {
		rtd((double) ia * Math.PI / 180.0);
	}

	private void rtd(double a) {
		fcheck();
		for (double i = rotateStep; i < a; i += rotateStep)
			turtleAngle(angle + i);
		angle = (angle + a);
		turtleAngle(angle);
	}

	/** n �x�������ɉ��B */
	public void lt(int ia) {
		ltd((double) ia * Math.PI / 180.0);
	}

	public void ltd(double a) {
		fcheck();
		for (double i = rotateStep; i < a; i += rotateStep)
			turtleAngle(angle - i);
		angle = (angle - a);
		turtleAngle(angle);
	}

	/** �y����������B */
	public void up() {
		penDown = false;
	}

	/** �y�������낷�B�y�������낵����ԂŐi�ނƁA���̋O�Ղ���ʂɎc��B */
	public void down() {
		penDown = true;
	}

	/** �y�����オ���Ă��邩�ǂ��� */
	public boolean isDown() {
		return (penDown);
	}

	/** �y���̐F�� nc �ɕύX����B */
	public void setColor(Color nc) {
		c = nc;
	}

	/** �����̑����� x �ɐݒ肷��Bx = 20 ���f�t�H���g�ł���B�������������قǑ����B */
	public void speed(int x) {
		moveWait = x;
		rotateWait = x;
	}

	/** ���݂̍��W�� X ������Ԃ��B */
	public int getX() {
		return (int) this.x;
	}

	/** ���݂̍��W�� Y ������Ԃ��B */
	public int getY() {
		return (int) this.y;
	}

	/** ���݂̊p�x��Ԃ��B */
	public int getAngle() {
		return (int) (this.angle * 180.0 / Math.PI);
	}

	public int moveTo(double x, double y) {
		double prevx = this.x;
		double prevy = this.y;
		// double a = Math.atan2(-( y - prevy), x - prevx);
		double a = Math.atan2(x - prevx, -(y - prevy));
		setangle(a);
		int r = (int) Math.sqrt((prevx - x) * (prevx - x) + (prevy - y)
				* (prevy - y));
		fd(r);
		this.x = ((double) x + 0.5);
		this.y = ((double) y + 0.5);
		turtleShow(moveWait);
		return (r);
	}

	public int moveTo(double x, double y, double aa) {
		int r = moveTo(x, y);
		setangle(aa);
		turtleShow(moveWait);
		return (r);
	}

	/**
	 * moveTo(x, y) �Ɠ��l�����C���̌�ɁCangle �̕����������B �ړ�����������Ԃ��B
	 */
	public int moveTo(int x, int y, int angle) {
		return moveTo((double) x, (double) y, (double) angle * Math.PI / 180.0);
	}

	/**
	 * (x, y) �Ƃ������W�܂ňړ�����B�y�������낵����ԂȂ�A�����`�悳���B �ړ�����������Ԃ��B
	 */
	public int moveTo(int x, int y) {
		return moveTo((double) x, (double) y);
	}

	/*
	 * t �Ɠ������W�܂ňړ�����B�y�������낵����ԂȂ�A�����`�悳���B �ړ�����������Ԃ��B
	 */
	public int moveTo(Turtle t) {
		return moveTo(t.x, t.y);
	}

	/*
	 * �����Ɠ�����Ԃ� Turtle ���쐬���C�Ԃ��B
	 */
	public Turtle clone() {
		try {
			return (Turtle) super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}
}