package graphics;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryStack;

public class Square extends Drawable {

	private final int texture = glGenTextures();

	private static final float vertexData[] = {
			// positions 		// colors 		  // texture coords
			0.5f, 0.5f, 0.0f, 	1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // top right
			0.5f, -0.5f, 0.0f, 	0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // bottom right
			-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // bottom left
			-0.5f, 0.5f, 0.0f, 	1.0f, 1.0f, 0.0f, 0.0f, 1.0f // top left
	};
	
	private static final int indicies[] = {
			0, 1, 3,
			1, 2, 3
	};

	private void SetUpShaders() {
		try {
			String vert = getClass().getClassLoader().getResource("resources/shaders/square.vert").getPath();
			String frag = getClass().getClassLoader().getResource("resources/shaders/square.frag").getPath();

			shader = new Shader(vert, frag);
		} catch (NullPointerException e) {
			System.err.println(
					"Cannot find the resource in the path. Please make sure there isn't a typo in the shader you are requesting.");
			System.exit(-1);
		}

		// SHADER LOADING
		int aPos = glGetAttribLocation(shader.GetProgramID(), "aPos");
		int aColor = glGetAttribLocation(shader.GetProgramID(), "aColor");

		glVertexAttribPointer(aPos, 3, GL_FLOAT, false, 8 * Float.BYTES, NULL);
		glEnableVertexAttribArray(aPos);

		glVertexAttribPointer(aColor, 3, GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
		glEnableVertexAttribArray(aColor);
	}
	
	private void SetUpTextures() {
		int aTexCoord = glGetAttribLocation(shader.GetProgramID(), "aTexCoord");
		
		// Texture Loading
		glVertexAttribPointer(aTexCoord, 2, GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);
		glEnableVertexAttribArray(aTexCoord);

		String text = getClass().getClassLoader().getResource("resources/images/wall.jpg").getPath();

		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer width = stack.ints(0);
			IntBuffer height = stack.ints(0);
			IntBuffer channels = stack.ints(0);

			ByteBuffer data = stbi_load(text, width, height, channels, 0);

			if (data != null) {
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, data);
				glGenerateMipmap(GL_TEXTURE_2D);
			} else {
				System.err.println("Could not load texture!");
				System.exit(-1);
			}

			stbi_image_free(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Square() {
		
		glBindVertexArray(VAO);

		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicies, GL_STATIC_DRAW);
		
		SetUpShaders();
		
		SetUpTextures();
		
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
		glBindTexture(GL_TEXTURE_2D, texture);
		shader.Use();
		shader.SetInt("ourTexture", 0);
		glBindVertexArray(VAO);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
	}
}