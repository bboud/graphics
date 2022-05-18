#include "shader.h"

void Shader::shaderErrorCheck(GLuint* shader){
	int success;
	char infoLog[512];
	glGetShaderiv(*shader, GL_COMPILE_STATUS, &success);
	if(!success){
		glGetShaderInfoLog(*shader, 512, NULL, infoLog);
		std::cout << "Error compiling shader: " << infoLog << std::endl;
	}
}
void Shader::programErrorCheck(GLuint* program){
	int success;
	char infoLog[512];
	glGetShaderiv(*program, GL_LINK_STATUS, &success);
	if(!success){
		glGetProgramInfoLog(*program, 512, NULL, infoLog);
		std::cout << "Error linking shaders: " << infoLog << std::endl;
	}
}
Shader::Shader(const char* vertexPath, const char* fragmentPath){
	std::string vertexCode;
	std::string fragmentCode;
	
	std::ifstream vShaderFile;
	std::ifstream fShaderFile;
	
    vShaderFile.exceptions (std::ifstream::failbit | std::ifstream::badbit);
    fShaderFile.exceptions (std::ifstream::failbit | std::ifstream::badbit);
	
	try{
		vShaderFile.open(vertexPath);
		fShaderFile.open(fragmentPath);
		
		std::stringstream vShaderStream, fShaderStream;
		vShaderStream << vShaderFile.rdbuf();
		fShaderStream << fShaderFile.rdbuf();
		
		vShaderFile.close();
		fShaderFile.close();
		
		vertexCode = vShaderStream.str();
		fragmentCode = fShaderStream.str();
	}catch(std::ifstream::failure e){
		std::cout << "Cannot read file!" << std::endl;
	}
	
	const char* vShaderCode = vertexCode.c_str();
	const char* fShaderCode = fragmentCode.c_str();
	
	GLuint vertexShader = glCreateShader(GL_VERTEX_SHADER);
	glShaderSource(vertexShader, 1, &vShaderCode, NULL);
	glCompileShader(vertexShader);

	shaderErrorCheck(&vertexShader);
	
	GLuint fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
	glShaderSource(fragmentShader, 1, &fShaderCode, NULL);
	glCompileShader(fragmentShader);

	shaderErrorCheck(&fragmentShader);
	
	ID = glCreateProgram();
	glAttachShader(ID, vertexShader);
	glAttachShader(ID, fragmentShader);
	glLinkProgram(ID);
	
	programErrorCheck(&ID);
	
	glDeleteShader(vertexShader);
	glDeleteShader(fragmentShader);
}

void Shader::use(){
	glUseProgram(ID);
}

void Shader::setBool(const std::string &name, bool value) const{
	glUniform1i(glGetUniformLocation(ID, name.c_str()), (int)value);
}
void Shader::setInt(const std::string &name, int value) const{
	glUniform1i(glGetUniformLocation(ID, name.c_str()), value);
}  
void Shader::setFloat(const std::string &name, float value) const{
	glUniform1i(glGetUniformLocation(ID, name.c_str()), value);
}
