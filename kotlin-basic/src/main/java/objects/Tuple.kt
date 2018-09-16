package objects.tuple

open class A {

    var a: String? = "fjay"

    var b: Int? = 100

    operator fun component1(): String? {
        return a
    }

    operator fun component2(): Int? {
        return b
    }
}

data class B(val a: String = "fjay", val b: Int = 100) {
}

fun main(args: Array<String>) {
    val a = Pair(1, 2)
    val b = Triple(1, 2, 3)
    val c = 1 to 2

    println(a.first)
    println(b.third)
    println(c.second)

    val (name, age) = A()
    println(name + ":" + age)
	
    val (name1, age1) = B()
    println(name1 + ":" + age1)
    
}