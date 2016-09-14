package syntax.ranges

fun main(args: Array<String>) {
    val x = 2
    val y = 10
    if (x in 1..y - 1)
        println("OK")

    //Iterate over a range:
    for (a in 1..5)
        print("${a} ")

    println()

    for (a in 5 downTo 1) {
        print("$a ")
    }

    //Check if a number is out of range:
    println()
    val array = arrayListOf("aaa", "bbb", "ccc")

    if (x !in 0..array.size - 1)
        println("Out: array has only ${array.size} elements. x = ${x}")
    //Check if a collection contains an object:
    if ("aaa" in array) // collection.contains(obj) is called
        println("Yes: array contains aaa")

    if ("ddd" in array) // collection.contains(obj) is called
        println("Yes: array contains ddd")
    else
        println("No: array doesn't contains ddd")
}


