#ifndef DRAWABLE_H
#define DRAWABLE_H

#include <glad/glad.h>

class Drawable{
public:
	Shader shader;
	GLuint VAO;
	unsigned int ID;
	
	Drawable();
	~Drawable();
	void DrawShape();
};
#endif