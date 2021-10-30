package com.mgg.variant

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kheiron.ktbind.Data
import com.kheiron.ktbind.Sample
import com.mgg.variant.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()
        test()
    }


    fun test() {
        Sample.pass_callback_string_returns_int("callback") { 82 }
        fundamentaltypes()
        callback_functions()
        native_and_java_exceptions()
        creation()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun lambdasignatures() {
        val fn: (String, Int, Long) -> String = { str, int, long -> "$str = $int + $long" }
        val c: Class<*> = fn::class.java
        println(c.name)
        for (classInterface in c.interfaces) {
            println(classInterface)
        }
        for (method in c.declaredMethods) {
            val methodName = method.name
            val signature = method.parameters.joinToString(separator = ", ") {
                "${it.name}: ${it.type}"
            }
            val returnType = method.returnType
            println("$methodName($signature): $returnType")
        }
    }

    /**
     * Tests fundamental and well-known types.
     */
    fun fundamentaltypes() {
        Sample.returns_void()
        Log.e("mgg", Sample.returns_bool().toString())
        Log.e("mgg", Sample.returns_short().toString())
        Log.e("mgg", Sample.returns_int().toString())
        Log.e("mgg", Sample.returns_long().toString())
        Log.e("mgg", Sample.returns_int16().toString())
        Log.e("mgg", Sample.returns_int32().toString())
        Log.e("mgg", Sample.returns_int64().toString())
        Log.e("mgg", Sample.returns_float().toString())
        Log.e("mgg", Sample.returns_double().toString())
        Log.e("mgg", Sample.returns_string().toString())

        val ref =
            "(string = string, bool = 1, short = 32000, int = 128000, long = 1000000000, int16_t = 32000, int32_t = 128000, int64_t = 1000000000, float = 3.14, double = 3.14)"
        Sample.pass_arguments_by_value("string", true, 32000, 128000, 1000000000, 32000, 128000, 1000000000, 3.14f, 3.14)
        Sample.pass_arguments_by_reference("string", true, 32000, 128000, 1000000000, 32000, 128000, 1000000000, 3.14f, 3.14)
    }

    fun callback_functions() {
        Sample.pass_callback { println("void callback") }
        Sample.pass_callback_returns_string { "alma" }
        Sample.pass_callback_string_returns_int("callback") { 82 }
        Sample.pass_callback_string_returns_string("callback") { "cpp($it) -> kotlin($it)" }
        Sample.pass_callback_arguments("callback") { str, short, int, long -> "($str, $short, $int, $long)" }

        // callback functions from new thread
        thread {
            Sample.list_of_int(listOf(1, 1, 2, 3, 5, 8, 13))
            Sample.pass_callback { println("void callback") }
            Sample.pass_callback_returns_string { "alma" }
            Sample.pass_callback_string_returns_int("callback") { 82 }
            Sample.pass_callback_string_returns_string("callback") { "cpp($it) -> kotlin($it)" }
            Sample.pass_callback_arguments("callback") { str, short, int, long -> "($str, $short, $int, $long)" }
        }.join()

        Sample.callback_on_native_thread { println("executed on native thread") }
    }


    fun native_and_java_exceptions() {
        Sample.raise_native_exception()
        Sample.pass_callback { throw Exception("non-critical error") }
        Sample.catch_java_exception { throw Exception("non-critical error") }
    }

    fun creation() {
        Sample.create().let {
            println("Kotlin: ${it.get_data()}")
            it.set_data(Data(true, 42, 65000, 12, 3.14f, 3.14, "a Kotlin string", ShortArray(0), IntArray(0), LongArray(0), emptyMap()))
        }
        Sample.create().let {
            val other = it.duplicate()
            it.set_data(Data())
            other.close()
        }
        Integer(100)
    }

    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("ktbindtest")
        }
    }
}