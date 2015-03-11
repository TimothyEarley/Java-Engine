package timmy.engine.in;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import timmy.engine.games.BasicGame;
import timmy.engine.util.vectors.Vector2i;

public class Input implements KeyListener, MouseMotionListener, MouseListener, FocusListener {

	BasicGame parent;
	
	private Vector2i mouse = new Vector2i(-1, -1);
	private boolean[] keyPressed = new boolean[65535];
	private boolean[] keyTyped = new boolean[65535];
	private boolean[] mousePressed = new boolean[4];
	private boolean[] mouseClicked = new boolean[4];
	private boolean focus = false;
	private boolean mouseOver = false;
	
	public Input(BasicGame game) {
		parent = game;
	}

	public void nextLoop() {
		for (int i = 0; i < mouseClicked.length; i++) mouseClicked[i] = false;
		for (int i = 0; i < keyTyped.length; i++) keyTyped[i] = false;
	}
	
	public Vector2i getRawMouse(){
		return mouse.copy();
	}
	
	public Vector2i getMouse(){
		return parent.mouseToScreen(mouse.copy());
	}
	
	public boolean isKeyPressed(int i) {
		if (i < 0 || i > 65535)
			return false;
		return keyPressed[i];
	}
	
	public boolean isKeyTyped(int i) {
		if (i < 0 || i > 65535)
			return false;
		return keyTyped[i];
	}

	public boolean isMouseDown(int i) {
		return mousePressed[i];
	}
	
	public boolean didMouseClicked(int i) {
		return mouseClicked[i];
	}

	public boolean hasFocus() {
		return focus;
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		focus = true;
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		focus = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClicked[e.getButton()] = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mouseOver = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mouseOver = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed[e.getButton()] = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouse.x = e.getX();
		mouse.y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse.x = e.getX();
		mouse.y = e.getY();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed[e.getKeyCode()] = true;
		keyTyped[(e.getKeyCode())] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		keyTyped[e.getKeyCode()] = true;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}



}
