package timmy.engine.geometry;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.vectors.Vector2f;

public class Circle extends Shape {

	public int radius;

	public Circle(Vector2f pos, int radius) {
		this.pos = pos;
		this.radius = radius;
	}

	@Override
	public void render(Vector2f offset, GraphicsHelper gh) {
		gh.fillCircle(pos.copy().sub(offset).sub(new Vector2f(radius, radius)), radius);
	}
}
