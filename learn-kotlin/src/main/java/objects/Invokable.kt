package objects.invokable

class A {
    operator fun invoke() {
        println ("B is here")
    }

    operator fun invoke(a: String) {
        println ("$a is here")
    }
}

fun main(args: Array<String>) {
    val a = A()
    a()
    a("fjay")
}