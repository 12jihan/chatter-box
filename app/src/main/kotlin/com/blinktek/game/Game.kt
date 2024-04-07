package com.blinktek.game

import com.blinktek.display.Window
import kotlin.io.println
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkApplicationInfo
import org.lwjgl.vulkan.VkInstanceCreateInfo
import org.lwjgl.vulkan.VkInstance
import org.lwjgl.glfw.GLFWVulkan.*

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

    fun init_vulkan() {
        create_instance()
    }

    fun create_instance() {
        MemoryStack.stackPush().use { stack ->
            val glfwExtensions = glfwGetRequiredInstanceExtensions()
            if (glfwExtensions == null) {
                throw RuntimeException("Failed to get required extensions")
            }
            val extensionCount = glfwExtensions.remaining()
            
            val appInfo: VkApplicationInfo =
            VkApplicationInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
                pApplicationName(stack.UTF8("Hello Triangle"))
                applicationVersion(VK_MAKE_VERSION(1, 0, 0))
                pEngineName(stack.UTF8("No Engine"))
                engineVersion(VK_MAKE_VERSION(1, 0, 0))
                apiVersion(VK_API_VERSION_1_0)
            }
            
            // Directly allocate the VkInstanceCreateInfo structure on the stack
            val createInfo =
            VkInstanceCreateInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
                pApplicationInfo(appInfo)
                ppEnabledExtensionNames(glfwExtensions)
            }
            
            val pInstance = stack.mallocPointer(1)
            val result = vkCreateInstance(createInfo, null, pInstance)
            if(result != VK_SUCCESS) {
                throw RuntimeException("Failed to create Vulkan instance: error code $result")
            }
        }
    }
}
