package com.blinktek.game

import com.blinktek.display.Window
import kotlin.io.println
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVulkan.*
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VK13.*

class Game {

    var window: Window = Window()
    var vk_instance: VkManager = VkManager()

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
        vk_instance.init()
        window.init()
    }

    fun loop() {
        while (!glfwWindowShouldClose(window.get_window())) {
            window.poll_events()
        }
    }

    fun input() {}

    fun update() {}

    fun render() {}

    fun cleanup() {
        vk_instance.cleanup()
        window.cleanup()
        glfwTerminate()
    }

    // Random functions that I won't focus on for now:

    fun keyboard_input() {}

    fun mouse_input() {}
}
