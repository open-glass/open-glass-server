package de.seine_eloquenz.open_glass

import java.awt.event.KeyEvent
import java.lang.reflect.Field


object Keys {

    private val keyMap: Map<String, Int>

    init {
        keyMap = HashMap(fillKeyMap())
    }

    fun convert(string: String): Int? {
        return keyMap[string]
    }

    private fun fillKeyMap(): MutableMap<String, Int> {
        val map: MutableMap<String, Int> = HashMap()
        val fields: Array<Field> = KeyEvent::class.java.declaredFields
        for (f in fields) {
            if (f.name.startsWith("VK_")) {
                val code = f.get(null) as Int
                map[f.name.substring(3)] = code
            }
        }
        return map
    }
}