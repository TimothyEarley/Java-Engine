package gui;

import games.BasicGame;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.lang.reflect.Method;

import javax.swing.JFrame;

import util.Crash;
import util.vectors.Vector2f;
import util.vectors.Vector2i;

public class Display extends JFrame {
	private static final long serialVersionUID = 1L;

	String title;

	public boolean resizable, doStretch;
	BufferStrategy bs;

	private int titleBar;
	private Vector2i offset;

	public Vector2i size, overScan;
	private Vector2f stretch, maxStretch;

	public static Color backgroundColor = Color.BLACK;
	
	public GraphicsHelper gh;

	boolean isApplet;

	/**
	 * Pseudo screen for applet
	 * 
	 * @param width
	 * @param height
	 */
	public Display(int width, int height) {
		size = new Vector2i(width, height);
		isApplet = true;
	}

	public Display(String title, int width, int height, boolean resizable) {
		this(title, new Vector2i(width, height), resizable);
	}

	public Display(String title, Vector2i size, boolean resizable) {
		this.title = title;
		this.size = size;
		this.resizable = resizable;

		// initial values
		this.doStretch = false;
		this.offset = new Vector2i(0, 0);
		this.stretch = new Vector2f(1, 1);
		this.maxStretch = new Vector2f(1, 1);
		this.overScan = new Vector2i(10, 10);

		setUp();
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			getTitlebar();
			this.setSize(this.getWidth(), this.getHeight() + titleBar);
		}
	}

	private void setUp() {
		this.setSize(size.x, size.y);
		this.setResizable(resizable);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setUndecorated(false);

		this.setLocationRelativeTo(null);

		checkOSX();
	}

	private void checkOSX() {
		if (System.getProperty("os.name").equals("Mac OS X")) {
			// at least 10.3
			String version = System.getProperty("os.version");
			String[] nums = version.split("\\.");
			if (nums.length >= 2) {
				if (Integer.parseInt(nums[0]) >= 10 && Integer.parseInt(nums[1]) >= 3) {
					enableOSXFullscreen(this);
				}
			}
		}
	}

	/**
	 * Never look at this again, but it works ;)
	 * 
	 * @param window
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void enableOSXFullscreen(Window window) {

		try {

			Class util = Class.forName("com.apple.eawt.FullScreenUtilities");

			Class params[] = new Class[] { Window.class, Boolean.TYPE };

			Method method = util.getMethod("setWindowCanFullScreen", params);

			method.invoke(util, window, true);

			Class c = Class.forName("com.apple.eawt.Application");
			Method toggle = c.getMethod("requestToggleFullScreen", Window.class);
			toggle.invoke(c.newInstance(), this);

		} catch (Exception e) {
			Crash.crash(e);
		}
	}

	public void render(BasicGame game) {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		getTitlebar();
		calcStretch();

		gh = new GraphicsHelper(bs, offset, stretch, this);

		gh.clearSreen(new Vector2i(getWidth() / stretch.x + overScan.x, getHeight() / stretch.y + overScan.y));

		game.render(game, gh);

		gh.clearSides();

		gh.dispose();

		bs.show();

	}

	private void getTitlebar() {
		Insets ins = this.getInsets();
		titleBar = ins.top;
	}

	private void calcStretch() {

		maxStretch.x = getWidth() / (float) size.x;
		maxStretch.y = getScreenHeight() / (float) size.y;

		stretch.x = maxStretch.x;
		stretch.y = maxStretch.y;

		if (doStretch) {
			offset.x = 0;
			offset.y = 0;
		} else {
			// stretch
			stretch.x = Math.min(stretch.x, stretch.y);
			stretch.y = stretch.x;
			// offset
			offset.x = (int) (getWidth() / 2 - (size.x * stretch.x) / 2);
			offset.y = (int) (getScreenHeight() / 2 - (size.y * stretch.y) / 2);
		}

		offset.y += titleBar;
	}

	private int getScreenHeight() {
		return super.getHeight() - titleBar;
	}

	@Override
	public int getWidth() {
		if (isApplet)
			return size.x;
		else
			return super.getWidth();
	}

	@Override
	public int getHeight() {
		if (isApplet)
			return size.y;
		else
			return super.getHeight();
	}

	public Vector2i mouseToScreen(Vector2i v) {
		if (gh == null)
			return v;
		return gh.mouseToScreen(v);
	}

}
