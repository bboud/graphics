package graphics;

public abstract class Drawable {
	protected int VBO;
	protected int VAO;
	protected Shader shader;
	
	public abstract void Render();
}
