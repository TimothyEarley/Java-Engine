package timmy.engine.geometry;

import timmy.engine.util.vectors.Vector2f;

public class Polygon extends Shape {

	public Vector2f[] points;

	public Polygon(Vector2f[] points) {
		this.points = points;
	}
}
