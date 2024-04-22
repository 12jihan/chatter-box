package com.blinktek.game

import java.nio.*
import org.lwjgl.system.*
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.KHRSurface.*
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VK13.*

class VkInstanceManager {
    val validation_layer: ValidationManager = ValidationManager()
    var vk_instance: VkInstance? = null
        private set

    // Creates the instance for vulkan:
    fun init() {
        MemoryStack.stackPush().use { stack ->
            check_extensions()
            // Check for validation layers:
            validation_layer.init()

            // Application info setup:
            val appInfo: VkApplicationInfo = VkApplicationInfo.calloc(stack)
            appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
            appInfo.pApplicationName(stack.UTF8("None"))
            appInfo.applicationVersion(VK_MAKE_VERSION(1, 0, 0))
            appInfo.pEngineName(stack.UTF8("Chatter Box v0.0.1"))
            appInfo.engineVersion(VK_MAKE_VERSION(1, 0, 0))
            appInfo.apiVersion(VK_API_VERSION_1_3)
            println("Application information:\n\t- " + appInfo.toString())
            
            // Instance create info setup:
            val createInfo: VkInstanceCreateInfo = VkInstanceCreateInfo.calloc(stack)
            createInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            createInfo.pApplicationInfo(appInfo)
            createInfo.ppEnabledLayerNames(validation_layer.get_required_layers_buffer(stack))
            
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
            println("Instance information:\n\t- " + createInfo.toString())

            // Return the created instance:
            vk_instance = VkInstance(pInstance.get(0), createInfo)
        }
    }

    // Cleans up and destroys the vulkan instance:
    fun cleanup() {
        vk_instance?.let { vkDestroyInstance(it, null) }
        println("Vulkan instance destroyed successfully.")
    }

    // Print available extensions:
    fun check_extensions() {
        // Check for supported extensions:
        val supportedExtensions = vk_list_supported_extensions()
        println("Supported Vulkan Extensions:")
        supportedExtensions.forEach { println("\t- " + it) }
    }

    // function for listing all the supported vulkan extensions:
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
