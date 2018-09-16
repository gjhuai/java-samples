package objects.objectExpressions

fun main(args: Array<String>) {
    create().fight()
}

fun create(): A {
    val x = object : A("fjay"), B {
        fun other() {
            println("$name blue")
        }
    }

    x.other()
    x.say()
    return x
}

interface B {
    fun say() {
        println("BBBB")
    }
}

open class A(name: String) {
    open val name: String = name

    open fun fight() {
        println("$name can fight.")
    }
}
