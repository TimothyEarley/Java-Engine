package games;

import graphics.Sprite;
import gui.Display;
import gui.GraphicsHelper;
import in.Input;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import util.Sound;
import util.vectors.Vector2i;

public abstract class BasicGame implements Runnable {

	public Display display;

	public final static int defWidth = 640, defHeight = 360;

	Thread run;

	Applet applet;

	boolean running, isApplet;

	public double ups;

	long time, delta;

	Input input;

	boolean alwaysUpdate;

	/**
	 * In an applet
	 * 
	 * @param applet
	 */
	public BasicGame(Applet applet, int width, int height) {
		this.applet = applet;
		setUpApplet(width, height);
		isApplet = true;
		// Pseudo display
		display = new Display(width, height);

		run = new Thread(this, "run");
		running = false;
		ups = 30;
		input = new Input(this);
		applet.addKeyListener(input);
		applet.addMouseListener(input);
		applet.addMouseMotionListener(input);
		applet.addFocusListener(input);
		alwaysUpdate = true;
	}

	private void setUpApplet(int width, int height) {
		applet.setSize(width, height);
	}

	public BasicGame(String title) {
		this(title, defWidth, defHeight);
	}

	public BasicGame(String title, int width, int height) {
		display = new Display(title, width, height, true);
		run = new Thread(this, "run");
		running = false;
		ups = 30;
		input = new Input(this);
		display.addKeyListener(input);
		display.addMouseListener(input);
		display.addMouseMotionListener(input);
		display.addFocusListener(input);
		alwaysUpdate = true;
	}

	public void start() {
		running = true;
		if (!isApplet)
			display.setVisible(true);

		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
			close();
			return;
		}

		run.start();
	}

	private void close() {
		System.exit(0);
	}

	@Override
	public void run() {
		delta = 0;
		time = System.currentTimeMillis();
		int ticks = 0;
		int frames = 0;
		while (running) {
			// System.out.println(delta + ", " + getTicks());
			addDelta();
			while (ticks < getTicks()) {
				addDelta();
				tryUpdate(input);
				ticks++;
			}
			if (frames <= ticks) {
				render();
				frames++;
			}
		}
	}

	private void tryUpdate(Input input) {
		if ((isApplet || display.gh != null) && (alwaysUpdate || input.hasFocus())) {
			update(input);
			input.nextLoop();
		}
	}

	private int getTicks() {
		return (int) (delta * ups / 1000);
	}

	private void addDelta() {
		long timeNow = System.currentTimeMillis();
		delta += timeNow - time;
		time = timeNow;
	}

	private void render() {
		if (isApplet) {
			applet.repaint();
		} else if (input.hasFocus()) {
			display.render(this);
		}
	}

	protected void doStretch(boolean stretch) {
		display.doStretch = stretch;
	}

	protected abstract void init() throws IOException;

	public abstract void render(BasicGame game, GraphicsHelper gh);

	protected abstract void update(Input input);

	public Vector2i mouseToScreen(Vector2i v) {
		if (!isApplet) {
			return display.mouseToScreen(v);
		} else {
			return v;
		}
	}

	public void setAlwaysUpdate(boolean b) {
		this.alwaysUpdate = b;
	}

	public void setCursor(Sprite img, Point hotSpot) {
		if (img == null)
			img = new Sprite(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(img.getImage(), hotSpot, "Custom Cursor");
		display.setCursor(c);
	}

	public int getWidth() {
		return display.size.x;
	}

	public int getHeight() {
		return display.size.y;
	}

	public Color getBackground() {
		if (!isApplet) {
			return Display.backgroundColor;
		} else {
			return applet.getBackground();
		}
	}

	public void setBackground(Color color) {
		if (!isApplet) {
			Display.backgroundColor = color;
		} else {
			applet.setBackground(color);
		}
	}

	public void playSound(Sound sound) {
		if (sound == null)
			return;
		if (isApplet)
			sound.start(applet);
		else
			sound.start();
	}

	public void setFont(String name, int style, int size) {
		GraphicsHelper.font = new Font(name, style, size);
	}

	public void loadFont(String path, int style, int size) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, BasicGame.class.getResourceAsStream(path));
			font = font.deriveFont(style, size);
			GraphicsHelper.font = font;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
