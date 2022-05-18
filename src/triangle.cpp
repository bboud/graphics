#include "drawable.h"

class Triangle: public Drawable{
protected:
	float vertices[] = {
	    // positions         // colors
	     0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
	    -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
	     0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top 
	};
public:
	Drawable(const char* vert, const char* frag){
		GLuint VBO;
	
		glGenVertexArrays(1, &VAO);
		glBindVertexArray(VAO);
		glGenBuffers(1, &VBO);
	
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, sizeof(this->vertices), this->vertices, GL_STATIC_DRAW);
	
		//Use layout 0, vec3, float, don't normalize, how large is each component, pointer to offset.
		glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 6*sizeof(GL_FLOAT), (void*)0);
		glEnableVertexAttribArray(0);
	
		glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 6*sizeof(GL_FLOAT), (void*)(3*sizeof(float)));
		glEnableVertexAttribArray(1);
	}
	~Drawable(){
		
	}
	void DrawShape(){
		
	}
};