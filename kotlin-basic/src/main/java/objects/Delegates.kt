package objects.delegates

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

interface A {

    fun say() {
        println("I'm, A")
    }
}

class Example(var a: A) : A by a {
    var p: String by Delegate()

    val lazyValue: String by lazy {
        println("computed!")
        "Hello"
    }

    var name: String by Delegates.observable("fjay") {
        d, old, new ->
        println("$old - $new")
    }

    override fun toString() = "Example Class"
}

class Delegate() {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${prop.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {
        println("$value has been assigned to ${prop.name} in $thisRef")
    }
}

fun main(args: Array<String>) {
    val e = Example(object : A {
    })

    println(e.p)
    e.p = "NEW"

    println("lazy = ${e.lazyValue}")

    e.name = "blue"

    e.say()
}
