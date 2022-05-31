package main.java.graphics;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    //Camera Qualities
    private Vector3f position;
    //By default, the camera will always look at world origin.
    private Vector3f target = new Vector3f(0.0f, 0.0f, 0.0f);

    private final Vector3f direction = new Vector3f();

    //Here we define some direction normals
    private final Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
    private Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);

    private final Vector3f vecDest = new Vector3f();

    private boolean firstMouse = true;
    private double lastX, lastY;

    private final float sensitivity = 0.2f;

    private double yaw, pitch = 0.0d;

    private float cameraSpeed;


    public Camera(Vector3f position) {
        this.position = position;
    }

    public Camera(Vector3f position, Vector3f target) {
        this.position = position;
        this.target = target;
    }

    public void SetPos(Vector3f position) {
        this.position = position;
    }

    public void SetPos(float x, float y, float z) {
        this.position = position.set(x, y, z);
    }

    public Vector3f GetPos() {
        return position;
    }

    public Vector3f GetCameraFront() {
        return cameraFront;
    }

    public Vector3f GetCameraUp() {
        return cameraUp;
    }

    public void keyboard(long window, float deltaTime) {
        float cameraSpeed = 20.0f * deltaTime;

        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS){
            position.add(cameraFront.mul(cameraSpeed, vecDest));
        }

        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS){
            position.sub(cameraFront.mul(cameraSpeed, vecDest));
        }

        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS){
            Vector3f cameraLeft = cameraUp.cross(cameraFront, vecDest).normalize();
            position.add(cameraLeft.mul(cameraSpeed, vecDest));
        }

        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS){
            Vector3f cameraRight = cameraFront.cross(cameraUp, vecDest).normalize();
            position.add(cameraRight.mul(cameraSpeed, vecDest));
        }

        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS){
            position.add(cameraUp.mul(cameraSpeed, vecDest));
        }
    }

    public void mouseCallback(long window, double xpos, double ypos, float deltaTime) {
        if (firstMouse) {
            lastX = xpos;
            lastY = ypos;
            firstMouse = false;
        }

        double xoffset = xpos - lastX;
        double yoffset = lastY - ypos;
        lastX = xpos;
        lastY = ypos;

        xoffset *= sensitivity;
        yoffset *= sensitivity;

        yaw += xoffset;
        pitch += yoffset;

        if (pitch > 89.0f)
            pitch = 89.0f;
        if (pitch < -89.0f)
            pitch = -89.0f;

        direction.set((float) Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)), (float) Math.sin(Math.toRadians(pitch)), (float) Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        cameraFront = direction.normalize();
    }
}
