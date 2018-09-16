package syntax.whens

fun main(args: Array<String>) {
    case(101)
    case(2)
    case(2.toLong())
}

fun case(x: Any) {
    val greater = { x: Int -> x > 100 }

    when (x) {
        is Int ->
            when {
                x in 1..50 -> println("In range")
                greater(x) -> println("Great")
            }
        !is String -> println("x")
        else -> print("Outside range")
    }
}