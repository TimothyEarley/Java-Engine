package timmy.engine.geometry;

import timmy.engine.util.vectors.Vector2f;

public class GeometryUtils {

	public static boolean intersects(Circle c1, Circle c2) {
		int minDistance = c1.radius + c2.radius;
		return (c1.pos.distanceSquare(c2.pos) <= minDistance * minDistance);
	}

	public static boolean intersects(Rectangle r1, Rectangle r2) {
		{
			if (Math.abs(r1.pos.x - r2.pos.x) < r1.size.x + r2.size.x) {
				if (Math.abs(r1.pos.y - r2.pos.y) < r1.size.y + r2.size.y) {
					return true;
				}
			}
			return false;
		}
	}

	public static boolean intersects(Circle circle, Rectangle rect) {
		Vector2f distance = circle.pos.copy().sub(rect.pos);

		if (distance.x > (rect.size.x / 2 + circle.radius)) {
			return false;
		}
		if (distance.y > (rect.size.y / 2 + circle.radius)) {
			return false;
		}

		if (distance.x <= (rect.size.x / 2)) {
			return true;
		}
		if (distance.y <= (rect.size.y / 2)) {
			return true;
		}

		double cornerDistance_sq = distance.sub(rect.size).lengthSquare();
		
		return (cornerDistance_sq <= (circle.radius ^ 2));
	}

}
