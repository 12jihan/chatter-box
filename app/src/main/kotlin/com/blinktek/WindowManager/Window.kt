package com.blinktek.display

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.*

class Window {
    private var window: Long = NULL
    val width: Int = 800
    val height: Int = 600

    constructor() {
        println("Window Class Created")
    }

    fun init() {
        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
        window = glfwCreateWindow(width, height, "Vulkan", NULL, NULL)
    }

    fun poll_events() {
        glfwPollEvents()
    }

    fun run(){
    }

    fun cleanup(){
        glfwDestroyWindow(window)
    }

    fun get_window(): Long {
        return window
    }

    fun set_window(window: Long) {
        this.window = window
    }


}
