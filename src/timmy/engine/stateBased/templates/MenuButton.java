package timmy.engine.stateBased.templates;

import timmy.engine.graphics.Sprite;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.stateBased.GameState;
import timmy.engine.stateBased.StateBasedGame;
import timmy.engine.stateBased.Transition;
import timmy.engine.util.Sound;

public class MenuButton {

	protected Sprite image, imageOver;
	public int x, y, w, h;
	protected boolean over;
	protected Transition leave;
	protected Action action;
	protected Sound sound;

	/**
	 * With action
	 * 
	 * @param img
	 * @param imgOver
	 * @param x
	 * @param y
	 * @param action
	 */
	public MenuButton(String img, String imgOver, int x, int y, Action action) {
		this(new Sprite(img), new Sprite(imgOver), x, y, action);
	}

	/**
	 * With action
	 * 
	 * @param img
	 * @param imgOver
	 * @param x
	 * @param y
	 * @param action
	 */
	public MenuButton(Sprite img, Sprite imgOver, int x, int y, Action action) {
		this(img, imgOver, x, y);
		this.action = action;
	}

	/**
	 * without transition, direct to state
	 */
	public MenuButton(String img, String imgOver, int x, int y, int id) {
		this(img, imgOver, x, y, new EmptyTransition(id));
	}

	/**
	 * with transition
	 */
	public MenuButton(String img, String imgOver, int x, int y, Transition leave) {
		this(new Sprite(img), new Sprite(imgOver), x, y, leave);
	}

	/**
	 * Main constructor + leave Transition
	 * 
	 * @param img
	 * @param imgOver
	 * @param x
	 * @param y
	 * @param leave
	 */
	public MenuButton(Sprite img, Sprite imgOver, int x, int y, Transition leave) {
		this(img, imgOver, x, y);
		this.leave = leave;
	}

	/**
	 * Main constructor
	 * 
	 * @param img
	 * @param imgOver
	 * @param x
	 * @param y
	 */
	public MenuButton(Sprite img, Sprite imgOver, int x, int y) {
		this.image = img;
		this.imageOver = imgOver;

		// can chane size on hover
		// imageOver = imageOver.getScaledCopy(1.1f);

		this.x = x;
		this.y = y;
		this.w = image.getWidth();
		this.h = image.getHeight();

		center();

	}
	
	public MenuButton addSound(String sound) {
		this.sound = new Sound(sound);
		return this;
	}

	public void center() {
		move(-image.getWidth() / 2, -image.getHeight() / 2);
	}

	public void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	public void render(GraphicsHelper gh) {

		if (isOver()) {
			gh.drawImage(imageOver, x - (imageOver.getWidth() - image.getWidth()) / 2, y - (imageOver.getHeight() - image.getHeight()) / 2);
		} else {
			gh.drawImage(image, x, y);
		}
	}

	public void update(GameState menu, StateBasedGame game, Input input) {
		boolean overBefore = over;
		// check if over
		over = (x <= input.getMouse().x && x + w >= input.getMouse().x && y <= input.getMouse().y && y + h >= input.getMouse().y);

		if (over != overBefore && sound != null)
			sound.start();
		
		if (isOver() && input.didMouseClicked(1)) {
			if (action != null) {
				action.action();
			}
			if (leave != null) {
				game.enter(leave);
			}
		}
	}

	public int getHeight() {
		return image.getHeight();
	}

	public int getWidth() {
		return image.getWidth();
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public boolean isOver() {
		return over;
	}

	public void setImage(Sprite image) {
		this.image = image;
	}

	public void setImageOver(Sprite imageOver) {
		this.imageOver = imageOver;
	}

}
