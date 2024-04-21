package com.blinktek.game

import com.blinktek.display.Window
import java.nio.Buffer
import kotlin.io.println
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVulkan.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.KHRSurface.VK_KHR_SURFACE_EXTENSION_NAME
import org.lwjgl.vulkan.VK13.*
import org.lwjgl.vulkan.VkApplicationInfo
import org.lwjgl.vulkan.VkInstanceCreateInfo

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
        vk_create_instance()
    }

    fun vk_create_instance(): Long {
        MemoryStack.stackPush().use { stack ->

            // Application info setup:
            val appInfo: VkApplicationInfo = VkApplicationInfo.calloc(stack)
            appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
            appInfo.pApplicationName(stack.UTF8("Hello Triangle"))
            appInfo.applicationVersion(VK_MAKE_VERSION(1, 0, 0))
            appInfo.pEngineName(stack.UTF8("No Engine"))
            appInfo.engineVersion(VK_MAKE_VERSION(1, 0, 0))
            appInfo.apiVersion(VK_API_VERSION_1_3)

            // Instance create info setup:
            val createInfo = VkInstanceCreateInfo.calloc(stack)
            createInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            createInfo.pApplicationInfo(appInfo)

            // Specify global extensions and validation layers:
            val extensionName = stack.UTF8Safe(VK_KHR_SURFACE_EXTENSION_NAME)
            if (extensionName == null) {
                throw RuntimeException("Required extens name is null")
            }
            val extensions = stack.pointers(extensionName as Buffer)
            createInfo.ppEnabledExtensionNames(extensions)

            // Create the vulkan instance:
            val pInstance = stack.mallocPointer(1)
            val err = vkCreateInstance(createInfo, null, pInstance)
            if (err != VK_SUCCESS) {
                throw RuntimeException("Failed to create Vulkan instance: error code $err")
            }

            // Return the created instance:
            return pInstance.get(0)
        }
    }
}
