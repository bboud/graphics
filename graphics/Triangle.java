package graphics;

import static org.lwjgl.opengl.GL33.*;

public class Triangle extends Drawable{

	private final float verticies[] = {
		    // positions         // colors
		     0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
		    -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
		     0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top 
	};
	
	public Triangle() {
		this.VAO = glGenVertexArrays();
		glBindVertexArray(this.VAO);
		
		//Bind the vertex buffer object to the vertex array
		this.VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.VBO);
		glBufferData(GL_ARRAY_BUFFER, verticies, GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 6*Float.SIZE, 0);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 6*Float.SIZE, 3*Float.SIZE);
		glEnableVertexAttribArray(1);
		
		String vert = getClass().getClassLoader().getResource("resources/shaders/triangle.vert").getPath();
		String frag = getClass().getClassLoader().getResource("resources/shaders/triangle.frag").getPath();
		
		this.SetShader(new Shader(vert, frag));
	}
	
	public void SetShader(Shader shader) {
		this.shader = shader;
	}
	
	@Override
	public void Render() {
		this.shader.Use();
		glBindVertexArray(this.VAO);
		glDrawArrays(GL_TRIANGLES, 0, 3);
	}

}
