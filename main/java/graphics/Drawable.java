package main.java.graphics;

import static org.lwjgl.opengl.GL33.*;

public abstract class Drawable {
	protected final int VBO = glGenBuffers();
	protected final int EBO = glGenBuffers();
	protected final int VAO = glGenVertexArrays();
	protected Shader shader;

	public abstract void Render();
}
