package graphics;

import static org.lwjgl.opengl.GL33.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Shader {
	
	private final int program = glCreateProgram();
	
	private static void ShaderErrorCheck(int shader) throws Exception {
		int success = glGetShaderi(shader, GL_COMPILE_STATUS);
		if(success == 0){
			System.out.println("Shader error check failed.");
			String infoLog = glGetShaderInfoLog(shader);
			throw new Exception(infoLog);
		}
	}
	
	private static void ProgramErrorCheck(int program) throws Exception {
		int success = glGetProgrami(program, GL_LINK_STATUS);
		if(success == 0){
			System.out.println("Program error check failed.");
			String infoLog = glGetProgramInfoLog(program);
			throw new Exception(infoLog);
		}
	}
	
	private static String ReadSource(String path) {
		String data = "";
		try {
			File source = new File(path);
			Scanner scan = new Scanner(source);
			
			while(scan.hasNextLine()) {
				data = data + scan.nextLine() + "\n";
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return data;
	}
	
	public void Use() {
		glUseProgram(program);
	}
	
	public int GetProgramID() {
		return program;
	}
	
	//The value needs to be passed as either 1 or 0.
	public void SetBool(String name, int value) {
		glUniform1i(glGetUniformLocation(program, name), value);
	}
	
	public void SetInt(String name, int value) {
		glUniform1i(glGetUniformLocation(program, name), value);
	}
	
	public void SetFloat(String name, int value) {
		glUniform1f(glGetUniformLocation(program, name), value);
	}
	
	public Shader(String vertexPath, String fragmentPath) {
		try {
			int vertexShader = glCreateShader(GL_VERTEX_SHADER);
			glShaderSource(vertexShader, ReadSource(vertexPath));
			glCompileShader(vertexShader);
	
			ShaderErrorCheck(vertexShader);
			
			int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
			glShaderSource(fragmentShader, ReadSource(fragmentPath));
			glCompileShader(fragmentShader);
	
			ShaderErrorCheck(fragmentShader);
			
			glAttachShader(program, vertexShader);
			glAttachShader(program, fragmentShader);
			glLinkProgram(program);
			
			ProgramErrorCheck(program);
			
			glDeleteShader(vertexShader);
			glDeleteShader(fragmentShader);
		}catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}
	}
	
	public void Delete() {
		glDeleteProgram(program);
	}
}
