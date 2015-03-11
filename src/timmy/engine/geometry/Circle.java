package timmy.engine.geometry;

import timmy.engine.util.vectors.Vector2f;

public class Circle extends Shape {

	public int radius;

	public Circle(Vector2f pos, int radius) {
		this.pos = pos;
		this.radius = radius;
	}
}
