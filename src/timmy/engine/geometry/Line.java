package timmy.engine.geometry;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.vectors.Vector2f;

public class Line extends Shape {

	public Vector2f a, b;
	
	public Line(Vector2f a, Vector2f b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public void render(Vector2f offset, GraphicsHelper gh) {
		gh.drawLine(a.toVector2i(), b.toVector2i());
	}

}
