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
	public boolean[] keyPressed = new boolean[65535];
	public boolean[] keyTyped = new boolean[65535];
	public boolean[] mousePressed = new boolean[4];
	public boolean[] mouseClicked = new boolean[4];
	public boolean focus = false;
	public boolean mouseOver = false;
	
	public Input(BasicGame game) {
		parent = game;
	}

	public void nextLoop() {
		for (int i = 0; i < mouseClicked.length; i++) mouseClicked[i] = false;
		for (int i = 0; i < keyTyped.length; i++) keyTyped[i] = false;
	}
	
	public Vector2i getMouse(){
		return parent.mouseToScreen(mouse.copy());
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



}
