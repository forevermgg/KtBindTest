package com.kheiron.ktbind

/**
 * Represents a class that is instantiated in native code.
 */
abstract class NativeObject : AutoCloseable {
    /**
     * Holds a reference to an object that exists in the native code execution context.
     */
    @Suppress("unused")
    private val nativePointer: Long = 0
}