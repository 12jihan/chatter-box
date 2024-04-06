package com.blinktek.game

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.*
import kotlin.io.println;
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.NULL

class Game {

    var window: Long = 0;
    val width: Int = 800;
    val height: Int = 600;
    
    constructor() {
        println("Game Class Created")
    }

    fun run() {
        println("Game is running...")
        init()
        loop()
        cleanup()
        println("Chatter Box cleaning loader...")
    }

    fun init() {
        init_window()
    }

    fun loop() {
        println("window: \n" + window.toString())
        try {
            println(!glfwWindowShouldClose(window))
        } catch (e: Exception) {
            println("Error: " + e)
        }
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()
        }
    }

    fun input() {}

    fun update() {}

    fun render() {}

    fun cleanup() {
        glfwDestroyWindow(window)
        glfwTerminate()
    }

    // Random functions that I won't focus on for now:

    fun keyboard_input() {}

    fun mouse_input() {}

    private fun init_window() {
        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW.")
        }

        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
        window = glfwCreateWindow(width, height, "Vulkan", NULL, NULL)
        println("Created window: \n" + window.toString())
    }
}
