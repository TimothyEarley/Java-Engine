package timmy.engine.geometry;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.vectors.Vector2f;

public class Polygon extends Shape {

	public Vector2f[] points;

	public Polygon(Vector2f[] points) {
		this.points = points;
	}

	@Override
	public void render(Vector2f offset, GraphicsHelper gh) {
		gh.fillPolygon(toPolygon());
	}

	private java.awt.Polygon toPolygon() {
		int npoints = points.length;
		int[] xpoints = new int[npoints];
		int[] ypoints = new int[npoints];
		for (int i = 0; i < npoints; i++) {
			xpoints[i] = (int) points[i].x;
			xpoints[i] = (int) points[i].y;
		}
		return new java.awt.Polygon(xpoints, ypoints, npoints);
	}
}
