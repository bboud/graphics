#include <iostream>
#include <math.h>

#include <glad/glad.h>
#include <GLFW/glfw3.h>

#include "shader.h"

void framebuffer_size_callback(GLFWwindow* window, int width, int height);  
void processInput(GLFWwindow *window);
void render(Shader *shader, GLuint VAO);
GLuint triangle();
GLuint square();

int main()
{
    glfwInit();
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	
	//Required for APPLE, otherwise the glfw won't open.
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
	
	GLFWwindow* window = glfwCreateWindow(800, 600, "OpenGL", NULL, NULL);
	if (window == NULL)
	{
	    std::cout << "Failed to create GLFW window" << std::endl;
	    glfwTerminate();
	    return -1;
	}
	glfwMakeContextCurrent(window);
	
	if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
	{
	    std::cout << "Failed to initialize GLAD" << std::endl;
	    return -1;
	}  
	
	glViewport(0, 0, 800, 600);
	
	glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
	
	GLuint VAO = triangle();
	
	Shader shader("shaders/triangle.vert","shaders/triangle.frag");
	
	while(!glfwWindowShouldClose(window))
	{
		processInput(window);
		
		render(&shader, VAO);
		
	    glfwSwapBuffers(window);
	    glfwPollEvents();
	}
	
	glfwTerminate();
	return 0;
}

void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
    glViewport(0, 0, width, height);
}

void processInput(GLFWwindow *window) { if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
glfwSetWindowShouldClose(window, true); }

GLuint triangle(){
	float vertices[] = {
	    // positions         // colors
	     0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
	    -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
	     0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top 
	};
	
	float texCoords[] = {
		0.0f, 0.0f,
		1.0f, 0.0f,
		0.5f, 1.0f
	};
	
	//Can we take this shader behavior and move it out to another file?
	GLuint VBO, VAO;
	
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);
	glGenBuffers(1, &VBO);
	
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
	
	//Use layout 0, vec3, float, don't normalize, how large is each component, pointer to offset.
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 6*sizeof(GL_FLOAT), (void*)0);
	glEnableVertexAttribArray(0);
	
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 6*sizeof(GL_FLOAT), (void*)(3*sizeof(float)));
	glEnableVertexAttribArray(1);
	
	return VAO;
}

GLuint square(){
	float vertices[] = {
	    // positions         // colors
	     0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
	    -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
	     0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top 
	};
	
	//Can we take this shader behavior and move it out to another file?
	GLuint VBO, VAO;
	
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);
	glGenBuffers(1, &VBO);
	
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
	
	//Use layout 0, vec3, float, don't normalize, how large is each component, pointer to offset.
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 6*sizeof(GL_FLOAT), (void*)0);
	glEnableVertexAttribArray(0);
	
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 6*sizeof(GL_FLOAT), (void*)(3*sizeof(float)));
	glEnableVertexAttribArray(1);
	
	return VAO;
}

void render(Shader *shader, GLuint VAO){
	
	glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT);
	
	shader->use();
	
	glBindVertexArray(VAO);
	glDrawArrays(GL_TRIANGLES, 0, 3);
}
