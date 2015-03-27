package timmy.engine.geometry;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.vectors.Vector2f;

public class Rectangle extends Shape {

	public Vector2f size;

	public Rectangle(Vector2f pos, Vector2f size) {
		this.pos = pos;
		this.size = size;
	}

	@Override
	public void render(Vector2f offset, GraphicsHelper gh) {
		gh.fillRect(pos.toVector2i().sub((int) size.x, (int) size.y) , (int) size.x, (int) size.y); 
	}

}
