package com.kheiron.ktbind

data class Data(
    val b: Boolean = false,
    val s: Short = 0,
    val i: Int = 0,
    val l: Long = 0,
    val f: Float = 0.0f,
    val d: Double = 0.0,
    val str: String = "",
    val short_arr: ShortArray = ShortArray(0),
    val int_arr: IntArray = IntArray(0),
    val long_arr: LongArray = LongArray(0),
    val map: Map<String, List<String>> = emptyMap()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (b != other.b) return false
        if (s != other.s) return false
        if (i != other.i) return false
        if (l != other.l) return false
        if (f != other.f) return false
        if (d != other.d) return false
        if (str != other.str) return false
        if (!short_arr.contentEquals(other.short_arr)) return false
        if (!int_arr.contentEquals(other.int_arr)) return false
        if (!long_arr.contentEquals(other.long_arr)) return false
        if (map != other.map) return false

        return true
    }

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + s
        result = 31 * result + i
        result = 31 * result + l.hashCode()
        result = 31 * result + f.hashCode()
        result = 31 * result + d.hashCode()
        result = 31 * result + str.hashCode()
        result = 31 * result + short_arr.contentHashCode()
        result = 31 * result + int_arr.contentHashCode()
        result = 31 * result + long_arr.contentHashCode()
        result = 31 * result + map.hashCode()
        return result
    }
}

class Sample private constructor() : NativeObject() {
    external override fun close()
    external fun get_data(): Data
    external fun set_data(data: Data)
    external fun duplicate(): Sample

    companion object {
        @JvmStatic
        external fun create(): Sample
        @JvmStatic
        external fun create(str: String): Sample
        @JvmStatic
        external fun returns_void()
        @JvmStatic
        external fun returns_bool(): Boolean
        @JvmStatic
        external fun returns_short(): Short
        @JvmStatic
        external fun returns_int(): Int
        @JvmStatic
        external fun returns_long(): Long
        @JvmStatic
        external fun returns_int16(): Short
        @JvmStatic
        external fun returns_int32(): Int
        @JvmStatic
        external fun returns_int64(): Long
        @JvmStatic
        external fun returns_float(): Float
        @JvmStatic
        external fun returns_double(): Double
        @JvmStatic
        external fun returns_string(): String
        @JvmStatic
        external fun pass_arguments_by_value(
            str: String,
            b: Boolean,
            s: Short,
            i: Int,
            l: Long,
            i16: Short,
            i32: Int,
            i64: Long,
            f: Float,
            d: Double
        ): Boolean

        @JvmStatic
        external fun pass_arguments_by_reference(
            str: String,
            b: Boolean,
            s: Short,
            i: Int,
            l: Long,
            i16: Short,
            i32: Int,
            i64: Long,
            f: Float,
            d: Double
        ): Boolean

        @JvmStatic
        external fun array_of_char(list: ByteArray): ByteArray
        @JvmStatic
        external fun array_of_int(list: IntArray): IntArray
        @JvmStatic
        external fun array_of_string(list: List<String>): List<String>
        @JvmStatic
        external fun list_of_int(list: List<Int>): List<Int>
        @JvmStatic
        external fun list_of_string(list: List<String>): List<String>
        @JvmStatic
        external fun unordered_set(set: Set<String>): Set<String>
        @JvmStatic
        external fun ordered_set(set: Set<String>): Set<String>
        @JvmStatic
        external fun unordered_map(map: Map<String, String>): Map<String, String>
        @JvmStatic
        external fun ordered_map_of_int(map: Map<Long, Long>): Map<Long, Long>
        @JvmStatic
        external fun ordered_map_of_string(map: Map<String, String>): Map<String, String>
        @JvmStatic
        external fun native_composite(map: Map<String, List<String>>): Map<String, List<String>>
        @JvmStatic
        external fun pass_callback(callback: () -> Unit)
        @JvmStatic
        external fun pass_callback_returns_string(callback: () -> String): String
        @JvmStatic
        external fun pass_callback_string_returns_int(str: String, callback: (String) -> Int): Int
        @JvmStatic
        external fun pass_callback_string_returns_string(str: String, callback: (String) -> String): String
        @JvmStatic
        external fun pass_callback_arguments(str: String, callback: (String, Short, Int, Long) -> String): String
        @JvmStatic
        external fun callback_on_native_thread(callback: () -> Unit)
        @JvmStatic
        external fun raise_native_exception()
        @JvmStatic
        external fun catch_java_exception(callback: () -> Unit)
    }
}