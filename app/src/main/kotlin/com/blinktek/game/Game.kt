package com.blinktek.game

import com.blinktek.display.Window
import java.nio.Buffer
import java.nio.ByteBuffer
import kotlin.io.println
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVulkan.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.KHRSurface.VK_KHR_SURFACE_EXTENSION_NAME
import org.lwjgl.vulkan.VK13.*

class Game {

    var window: Window = Window()
    var vk_instance: Long? = null

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
        init_vulkan()
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
        vk_instance = vk_create_instance()
    }

    fun vk_create_instance(): Long {
        MemoryStack.stackPush().use { stack ->
            // Check for supported extensions:
            val supportedExtensions = vk_list_supported_extensions()
            println("Supported Vulkan Extensions:")
            supportedExtensions.forEach { println("\t- " + it) }

            // Application info setup:
            val appInfo: VkApplicationInfo = VkApplicationInfo.calloc(stack)
            appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
            appInfo.pApplicationName(stack.UTF8("Hello Triangle"))
            appInfo.applicationVersion(VK_MAKE_VERSION(1, 0, 0))
            appInfo.pEngineName(stack.UTF8("No Engine"))
            appInfo.engineVersion(VK_MAKE_VERSION(1, 0, 0))
            appInfo.apiVersion(VK_API_VERSION_1_3)
            println("application information:\n" + appInfo)
            
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
            println("Instance information:\n" + createInfo)
            
            // Return the created instance:
            return pInstance.get(0)
        }
    }

    fun vk_list_supported_extensions(): List<String> {
        MemoryStack.stackPush().use { stack ->
            // Allocate an int buffer to hold the extension count:
            val ip = stack.mallocInt(1)

            // First call to get the number of extensions:
            var err = vkEnumerateInstanceExtensionProperties(null as ByteBuffer?, ip, null)
            if (err != VK_SUCCESS) {
                throw RuntimeException(
                        "Failed to get the number instance extensions: err code\n $err"
                )
            }

            // Allocate a buffer to hold the extension properties:
            val extensions = VkExtensionProperties.malloc(ip.get(0), stack)

            // Second call to get the extension properties:
            err = vkEnumerateInstanceExtensionProperties(null as ByteBuffer?, ip, extensions)
            if (err != VK_SUCCESS) {
                throw RuntimeException(
                        "Failed to enumeratea Vulkan instance extensions: err code\n $err"
                )
            }

            // Extract the extension names and return them as a list:
            return extensions.map { it.extensionNameString() }
        }
    }
}
