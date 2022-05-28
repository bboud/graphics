package graphics;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Triangle extends Drawable{

	private static final float vertexData[] = {
		    // positions
		     0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
		    -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
		     0.0f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f
	};
	
	public Triangle() {
		String vert = getClass().getClassLoader().getResource("resources/shaders/triangle.vert").getPath();
		String frag = getClass().getClassLoader().getResource("resources/shaders/triangle.frag").getPath();
		
		this.shader = new Shader(vert, frag);
		
		this.VAO = glGenVertexArrays();
		glBindVertexArray(this.VAO);
		
		//Bind the vertex buffer object to the vertex array
		this.VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.VBO);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
		
		int aPos = glGetAttribLocation(this.shader.GetProgramID(), "aPos");
		int aColor = glGetAttribLocation(this.shader.GetProgramID(), "aColor");
		
		glVertexAttribPointer(aPos, 3, GL_FLOAT, false, 6*Float.BYTES, NULL);
		glEnableVertexAttribArray(aPos);
		
		glVertexAttribPointer(aColor, 3, GL_FLOAT, false, 6*Float.BYTES, 3*Float.BYTES);
		glEnableVertexAttribArray(aColor);
	}
	
	public void SetShader(Shader shader) {
		this.shader = shader;
	}
	
	public int GetVAO() {
		return this.VAO;
	}
	
	@Override
	public void Render() {
		this.shader.Use();
		glBindVertexArray(this.VAO);
		glDrawArrays(GL_TRIANGLES, 0, 3);	
	}

}
