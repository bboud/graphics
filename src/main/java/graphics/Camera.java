package main.java.graphics;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    //Camera Qualities
    private Vector3f position;
    //By default, the camera will always look at world origin.
    private Vector3f target = new Vector3f(0.0f, 0.0f, 0.0f);


    //Here we define some direction normals
    private Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
    private Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);

    private final float cameraSpeed = 0.3f;


    public Camera(Vector3f position) {
        this.position = position;
	}
    public Camera(Vector3f position, Vector3f target) {
        this.position = position;
        this.target = target;
    }

    public void SetPos(Vector3f position){
        this.position = position;
    }

    public void SetPos(float x, float y, float z){
        this.position = position.set(x,y,z);
    }

    public Vector3f GetPos(){
        return position;
    }

    public Vector3f GetCameraFront(){
        return cameraFront;
    }

    public Vector3f GetCameraUp(){
        return cameraUp;
    }

    public float GetCameraSpeed(){
        return cameraSpeed;
    }

    private Vector3f vecDest = new Vector3f();
	public void HandleInput(int key) {
        if ( key == GLFW.GLFW_KEY_W  ) {
				position.add(cameraFront.mul(cameraSpeed,vecDest));
			}else if (key == GLFW.GLFW_KEY_S ) {
				position.sub(cameraFront.mul(cameraSpeed, vecDest));
			}else if (key == GLFW.GLFW_KEY_A ) {
				Vector3f cameraLeft = cameraUp.cross(cameraFront,vecDest).normalize();
				position.add(cameraLeft.mul(cameraSpeed, vecDest));
			}else if (key == GLFW.GLFW_KEY_D ) {
				Vector3f cameraLeft = cameraFront.cross(cameraUp,vecDest).normalize();
				position.add(cameraLeft.mul(cameraSpeed, vecDest));
			}
	}
}
