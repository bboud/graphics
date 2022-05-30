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
    private float deltaTime = 0.0f;
    private float lastTime = 0.0f;

    private boolean firstMouse = true;
    private double lastX, lastY;

    private final float sensitivity = 0.05f;

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


    public void HandleInput(long window) {
        float sensitivity = 0.1f;

        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GLFW.glfwSetCursorPosCallback(window, this::mouseCallback);

        GLFW.glfwSetKeyCallback(window, this::keyboardCallback);
    }

    private void keyboardCallback(long window, int key, int scancode, int action, int mods) {

        float curTime = (float) GLFW.glfwGetTime();
        deltaTime = curTime - lastTime;
        lastTime = curTime;

        float cameraSpeed = 1.2f * deltaTime;

        cameraSpeed = 1.2f * deltaTime;
        if (key == GLFW.GLFW_KEY_W) {
            position.add(cameraFront.mul(cameraSpeed, vecDest));
        } else if (key == GLFW.GLFW_KEY_S) {
            position.sub(cameraFront.mul(cameraSpeed, vecDest));
        } else if (key == GLFW.GLFW_KEY_A) {
            Vector3f cameraLeft = cameraUp.cross(cameraFront, vecDest).normalize();
            position.add(cameraLeft.mul(cameraSpeed, vecDest));
        } else if (key == GLFW.GLFW_KEY_D) {
            Vector3f cameraLeft = cameraFront.cross(cameraUp, vecDest).normalize();
            position.add(cameraLeft.mul(cameraSpeed, vecDest));
        }
    }

    private void mouseCallback(long window, double xpos, double ypos) {
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

        direction.set((float) Math.cos(yaw) * Math.cos(pitch), (float) Math.sin(pitch), (float) Math.sin(yaw) * Math.cos(pitch));
        cameraFront = direction.normalize();
    }
}
