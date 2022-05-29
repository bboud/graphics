package graphics;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryStack;

public class Triangle extends Drawable{

	private static final float vertexData[] = {
		    // positions		Colors
		     0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
		    -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
		     0.0f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f
	};
	
	public Triangle() {
		try {
			String vert = getClass().getClassLoader().getResource("resources/shaders/triangle.vert").getPath();
			String frag = getClass().getClassLoader().getResource("resources/shaders/triangle.frag").getPath();
			
			shader = new Shader(vert, frag);
		}catch(NullPointerException e) {
			System.err.println("Cannot find the resource in the path. Please make sure there isn't a typo in the shader you are requesting.");
			System.exit(-1);
		}
				
		glBindVertexArray(VAO);
	
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
		
		int aPos = glGetAttribLocation(shader.GetProgramID(), "aPos");
		int aColor = glGetAttribLocation(shader.GetProgramID(), "aColor");
		
		glVertexAttribPointer(aPos, 3, GL_FLOAT, false, 6*Float.BYTES, NULL);
		glEnableVertexAttribArray(aPos);
		
		glVertexAttribPointer(aColor, 3, GL_FLOAT, false, 6*Float.BYTES, 3*Float.BYTES);
		glEnableVertexAttribArray(aColor);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void SetShader(Shader shader) {
		this.shader = shader;
	}
	
	public int GetVAO() {
		return VAO;
	}
	
	@Override
	public void Render() {
		shader.Use();
		glBindVertexArray(this.VAO);
		glDrawArrays(GL_TRIANGLES, 0, 3);	
	}
}
