package graphics;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

public class Cube extends Drawable {

	private final int texture1 = glGenTextures();
	private final int texture2 = glGenTextures();

	private static final float vertexData[] = {
		    -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
		     0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
		     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
		     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
		    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
		    -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

		    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
		     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
		     0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
		     0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
		    -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
		    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

		    -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
		    -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
		    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
		    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
		    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
		    -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

		     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
		     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
		     0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
		     0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
		     0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
		     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

		    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
		     0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
		     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
		     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
		    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
		    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

		    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
		     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
		     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
		     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
		    -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
		    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
		};

	private void SetUpShaders() {
		try {
			String vert = getClass().getClassLoader().getResource("resources/shaders/cube.vert").getPath();
			String frag = getClass().getClassLoader().getResource("resources/shaders/cube.frag").getPath();

			shader = new Shader(vert, frag);
		} catch (NullPointerException e) {
			System.err.println(
					"Cannot find the resource in the path. Please make sure there isn't a typo in the shader you are requesting.");
			System.exit(-1);
		}

		// SHADER LOADING
		int aPos = glGetAttribLocation(shader.GetProgramID(), "aPos");
		int aColor = glGetAttribLocation(shader.GetProgramID(), "aColor");

		glVertexAttribPointer(aPos, 3, GL_FLOAT, false, 5 * Float.BYTES, NULL);
		glEnableVertexAttribArray(aPos);
	}
	
	private void SetUpTextures() {
		int aTexCoord = glGetAttribLocation(shader.GetProgramID(), "aTexCoord");
		
		// Texture Loading
		glVertexAttribPointer(aTexCoord, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
		glEnableVertexAttribArray(aTexCoord);

		glBindTexture(GL_TEXTURE_2D, texture1);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		LoadTexture("resources/images/container.jpg", GL_RGB);
		
		glBindTexture(GL_TEXTURE_2D, texture2);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		LoadTexture("resources/images/awesomeface.png", GL_RGBA);
		
		shader.Use();
		
		shader.SetInt("texture1", 0);
		shader.SetInt("texture2", 1);

	}
	
	private void LoadTexture(String path, int type) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			
			String text = getClass().getClassLoader().getResource(path).getPath();
			
			IntBuffer width = stack.ints(0);
			IntBuffer height = stack.ints(0);
			IntBuffer channels = stack.ints(0);
			
			stbi_set_flip_vertically_on_load(true);

			ByteBuffer data = stbi_load(text, width, height, channels, 0);
			
			if (data != null) {
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, type, GL_UNSIGNED_BYTE, data);
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

	public Cube() {
		
		glDeleteBuffers(EBO);
		
		glBindVertexArray(VAO);

		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

		SetUpShaders();
		
		SetUpTextures();
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindVertexArray(0);
	}
	
	public void Delete() {
		glDeleteVertexArrays(VAO);
		glDeleteBuffers(VBO);
		shader.Delete();
		
	}

	public void SetShader(Shader shader) {
		this.shader = shader;
	}

	public int GetVAO() {
		return VAO;
	}
	
	//Do some actions!
	public void Actor() {
		//World
		Matrix4f model = new Matrix4f();
		model.rotate((float)GLFW.glfwGetTime(), new Vector3f(1.0f, 1.0f, 1.0f).normalize());
		
		//View
		Matrix4f view = new Matrix4f();
		view.translate(new Vector3f(0.0f, 0.0f, -3.0f));
		
		//Projection
		Matrix4f projection = new Matrix4f();
		projection.perspective(45.0f, 800.0f/600.0f, 0.1f, 100.0f);
		
		shader.SetMat4("model", model);
		shader.SetMat4("view", view);
		shader.SetMat4("projection", projection);
	}

	@Override
	public void Render() {
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture1);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, texture2);
		
		shader.Use();
		
		glBindVertexArray(VAO);		
		glDrawArrays(GL_TRIANGLES, 0, 36);
	}
}