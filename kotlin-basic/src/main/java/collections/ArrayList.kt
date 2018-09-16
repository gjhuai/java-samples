package collections.ArrayList

import org.jetbrains.annotations.ReadOnly

fun main(args: Array<String>) {
    val list = arrayListOf(1, 2, 3)
    list.forEach { println(it) }

    val list2 = arrayListOf(Pair("Bill", "Clinton"),
            Pair("George W.", "Bush"),
            "Barack" to "Obama")

    list2.add("Hillary" to "Clinton")

    list2.forEach { println("${it.first} - ${it.second}") }

    println(list.mapTo(arrayListOf<String>()) {
        "$it is here"
    }.joinToString())

    // which means that we can no longer call the add() on it for instance
    readOnlyList()


}

//ReadOnly
fun readOnlyList(): List<String> {
    return arrayListOf("1", "2")
}