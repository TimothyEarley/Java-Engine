package timmy.engine.util.vectors;

public class Vector3i {

	public int x, y, z;

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(float x, float y, float z) {
		this((int) x, (int) y, (int) z);
	}

	@Override
	public String toString() {
		return "[Vector3i: x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	public Vector3i copy() {
		return new Vector3i(x, y, z);
	}
	
	public Vector2i toVector2i() {
		
		Vector2i v = new Vector2i(y, z);
		
		v.add(new Vector2i((int) (x * Math.sin(Math.PI/2)),(int) (x * Math.cos(Math.PI/2))));
		
		return v;
	}

}
