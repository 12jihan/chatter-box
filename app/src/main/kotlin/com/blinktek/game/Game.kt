package com.blinktek.game

import com.blinktek.display.Window
import kotlin.io.println
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.*

class Game {

    var window: Window = Window()

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
        window.cleanup()
        glfwTerminate()
    }

    // Random functions that I won't focus on for now:

    fun keyboard_input() {}

    fun mouse_input() {}
}
