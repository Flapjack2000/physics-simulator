package com.flapjackzach;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Main {

    private static final String VERSION = "0.1.0";

    private long window;
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;

    public static void main(String[] args) {

        // Display version numbers
        if (args.length > 0 && (args[0].equals("--version") || args[0].equals("-v"))) {
            System.out.println("Simulator version: " + VERSION);
            System.out.println("LWJGL version: " + Version.getVersion());
            return;
        }

        // Welcome message
        System.out.println("Welcome to Zach's Physics Simulator!");

        // Start simulator
        new Main().run();
    }


    public void run() {
        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Free the error callbacks and terminate GLFW
        if (keyCallback != null) keyCallback.free();
        if (errorCallback != null) errorCallback.free();
        glfwTerminate();
    }


    private void init() {

        // Set up error callback
        errorCallback = GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Disable resizing
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Create a bordered window sized to the usable desktop area
        try (MemoryStack stack = stackPush()) {
            IntBuffer wx = stack.mallocInt(1);
            IntBuffer wy = stack.mallocInt(1);
            IntBuffer ww = stack.mallocInt(1);
            IntBuffer wh = stack.mallocInt(1);

            // Get dimensions of usable desktop area
            glfwGetMonitorWorkarea(glfwGetPrimaryMonitor(), wx, wy, ww, wh);

            // Important for NanoVG
            glfwWindowHint(GLFW_STENCIL_BITS, 8);
            glfwWindowHint(GLFW_DEPTH_BITS, 24);

            // Create the window
            window = glfwCreateWindow(ww.get(0), wh.get(0), "Physics Simulator", NULL, NULL);
            if (window == NULL) {
                throw new RuntimeException("Failed to create the GLFW window.");
            }

            // Get title bar height and offset position
            IntBuffer left = stack.mallocInt(1);
            IntBuffer top = stack.mallocInt(1);
            IntBuffer right = stack.mallocInt(1);
            IntBuffer bottom = stack.mallocInt(1);
            glfwGetWindowFrameSize(window, left, top, right, bottom);

            glfwSetWindowPos(window, wx.get(0), wy.get(0) + top.get(0));
        }

        // Close after escape key is pressed
        keyCallback = glfwSetKeyCallback(window, (window, key, _, action, _) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }


    private void loop() {

        // Allow interoperation between LWJGL and OpenGL context
        GL.createCapabilities();

        // Background color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        // Create NanoVG context
        long vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
        if (vg == NULL) {
            throw new RuntimeException("Failed to create NanoVG context.");
        }

        while (!glfwWindowShouldClose(window)) {

            // Clear the framebuffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Swap the color buffers
            glfwSwapBuffers(window);

            // Poll for window events
            glfwPollEvents();
        }
    }
}
