package timmy.engine.geometry;

import timmy.engine.util.vectors.Vector2f;

public class Rectangle extends Shape {

	public Vector2f size;

	public Rectangle(Vector2f pos, Vector2f size) {
		this.pos = pos;
		this.size = size;
	}

}
