package syntax.references

fun main(args: Array<String>) {
    val numbers = listOf(1, 2, 3)

    println(numbers.filter(::isOdd))
    A().filter(A::isOdd)

    // References to local functions
    fun isOddLocal(x: Int) = x % 2 == 0
    println(numbers.filter(::isOddLocal))
}

fun isOdd(x: Int) = x % 2 != 0

class A {
    fun isOdd(x: Int) = x % 2 != 0

    fun filter(predicate: A.(Int) -> Boolean) {
        println(predicate(1))
    }
}

