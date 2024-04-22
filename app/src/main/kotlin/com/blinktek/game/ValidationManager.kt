package com.blinktek.game

import java.nio.*
import org.lwjgl.PointerBuffer
import org.lwjgl.system.*
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.KHRSurface.*
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VK13.*

class ValidationManager {
    // Validation layers, obviously:
    val validation_layers: List<String> = listOf("VK_LAYER_KHRONOS_validation")

    // Enable Layers:
    val enable_validation_layers: Boolean = true

    // I'm guessing the screen width and height?:
    val WIDTH: Int = 800
    val HEIGHT: Int = 600

    public fun init() {
        if (enable_validation_layers) {
            check_validation_layer_support()
        }
    }

    fun check_validation_layer_support(): Boolean {
        MemoryStack.stackPush().use { stack ->
            // Buffer to store the count of layer properties:
            var layer_count: IntBuffer = stack.mallocInt(1)
            var err = vkEnumerateInstanceLayerProperties(layer_count, null)
            if (err != VK_SUCCESS) {
                throw RuntimeException(
                        "Failed to get the count of Vulkan instance layer properties: error code: ${err}"
                )
            }

            // Allocate a buffer to hold the layer properties:
            val layer_props = VkLayerProperties.malloc(layer_count.get(0), stack)
            err = vkEnumerateInstanceLayerProperties(layer_count, layer_props)
            if (err != VK_SUCCESS) {
                throw RuntimeException(
                        "Failed to get vulkan instance layer properties: error code: ${err}"
                )
            }

            // Print the layer properties:
            println("Available Vulkan Layers:")
            for (i in 0 until layer_count.get(0)) {
                val layer = layer_props.get(i)
                println("\t- ${layer.layerNameString()}: ${layer.descriptionString()}")
            }

            println("Layer name: ${validation_layers[0]}")
            return validation_layers.all { layer_name ->
                // println("Layer name: ${it.layerNameString()}")
                layer_props.any { 
                    println("test: ${it.layerNameString() == layer_name} \n" + it.layerNameString() + " and " + layer_name)
                    it.layerNameString() == layer_name 
                }
            }
        }
    }

    fun get_required_layers_buffer(stack: MemoryStack): PointerBuffer {
        if (!check_validation_layer_support()) {
            throw RuntimeException("Validation layers requested, but not available!")
        }
        // Allocate a PointerBuffer to hold pointers to the encoded layer names
        val layerNamesBuffer = stack.mallocPointer(validation_layers.size)
        validation_layers.forEach { layerName -> layerNamesBuffer.put(stack.UTF8(layerName)) }
        layerNamesBuffer.flip()

        return layerNamesBuffer
    }
}
