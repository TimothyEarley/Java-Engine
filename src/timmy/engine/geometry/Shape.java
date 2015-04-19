package timmy.engine.geometry;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.vectors.Vector2f;

public abstract class Shape {

	// Base class
	public Vector2f pos;
	public abstract void render(Vector2f offset, GraphicsHelper gh);
	
	public void render(GraphicsHelper gh) {
		render(new Vector2f(), gh);
	}
	
}
