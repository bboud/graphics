package graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    //Camera Qualities
    private Vector3f position;
    //By default, the camera will always look at world origin.
    private Vector3f target = new Vector3f(0.0f, 0.0f, 0.0f);
    private Vector3f direction;


    //Here we define some direction normals
    private Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
    private Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    private Vector3f cameraRight;
    private Vector3f cameraUp;


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

	public void HandleInput(int key) {
//		float cameraSpeed = 0.2f;
//		if ( key == GLFW.GLFW_KEY_W  ) {
//			position.add(cameraFront).mul(cameraSpeed, new Vector3f());
//			System.out.println(position.toString());
//		}else if (key == GLFW.GLFW_KEY_S ) {
//			position.sub(cameraFront).mul(cameraSpeed, new Vector3f());
//			System.out.println(position.toString());
//		}
	}

	public Matrix4f Update() {
        //We need to update these values as they always change!
        //Note: Position should always change with the public function SetPos()
        direction = position.sub(target).normalize();
        cameraRight = direction.cross(up).normalize();
        cameraUp = cameraRight.cross(direction).normalize();

		//View
		Matrix4f view = new Matrix4f();
		//view.lookAt(position, position.add(cameraFront), cameraUp);
        view.lookAt(target, target, target) ;

		//Projection
		Matrix4f projection = new Matrix4f();
		projection.perspective(45.0f, 800.0f/600.0f, 0.1f, 100.0f);

        //Projection and view space matrix
        return projection.mul(view);
	}
}
