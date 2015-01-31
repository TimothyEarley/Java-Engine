package timmy.engine.games;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import timmy.engine.graphics.Animation;
import timmy.engine.graphics.Sprite;
import timmy.engine.gui.Display;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.util.Sound;
import timmy.engine.util.vectors.Vector2i;

public abstract class BasicGame implements Runnable {
	public static boolean DEBUG;

	public Display display;

	public final static int defWidth = 640, defHeight = 360;

	Thread run;

	Applet applet;

	boolean running, isApplet;

	public double ups;

	long time, sysTime, oldTime, lastUpdateTime;

	Input input;

	boolean alwaysUpdate, alwaysRender;


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
		time = 0;
		sysTime = System.currentTimeMillis();
		int ticks = 0;
		int frames = 0;


		boolean updated;
		
		// debug vars:
		long deltaCounter = 0;
		int tickCounter = 0;
		int frameCounter = 0;
		
		
		while (running) {
			// System.out.println(time + ", " + getTicks());
			updated = false;
			oldTime = time;
			while (time == oldTime) {
				addTime();
			}
			deltaCounter += time - oldTime;
			while (ticks < getTicks()) {
				addTime();
				tryUpdate(input);
				ticks++;
				updated = true;
			}
			if (DEBUG || (updated && frames <= ticks)) {
				render();
				frames++;
			}
			
			
			
			// debug
			if (deltaCounter >= 1000) {
				deltaCounter = 0;
				if (DEBUG) {
//					System.out.println("FPS: " + (frames - frameCounter));
					display.setTitle("FPS: " + (frames - frameCounter) + ", UPS: " + (ticks - tickCounter) + " (frames: " + frames + ", ticks: " + ticks + ")");
				}
				// if ups is not reached, reset to avoid trying to overcompensate after lag
				if (ticks - tickCounter < ups) {
					ticks = (int) (tickCounter + ups);
				}
				frameCounter = frames;
				tickCounter = ticks;
			}
		}
	}

	private void tryUpdate(Input input) {
		if ((isApplet || display.gh != null) && (alwaysUpdate || input.hasFocus())) {
			update(input, (int) (sysTime - lastUpdateTime));
			input.nextLoop();
			Animation.tickAll();
		}
		lastUpdateTime = sysTime;
	}

	private int getTicks() {
		return (int) (time * ups / 1000);
	}

	private void addTime() {
		long timeNow = System.currentTimeMillis();
		time += timeNow - sysTime;
		sysTime = timeNow;
	}

	private void render() {
		if (isApplet) {
			applet.repaint();
		} else if (alwaysRender || input.hasFocus()) {
			display.render(this);
		}
	}

	protected void doStretch(boolean stretch) {
		display.doStretch = stretch;
	}

	protected abstract void init() throws IOException;

	public abstract void render(BasicGame game, GraphicsHelper gh);

	protected abstract void update(Input input, int delta);

	public Vector2i mouseToScreen(Vector2i v) {
		if (!isApplet) {
			return display.mouseToScreen(v);
		} else {
			return v;
		}
	}
	
	public void setAlwaysRender(boolean b) {
		this.alwaysRender = b;
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
