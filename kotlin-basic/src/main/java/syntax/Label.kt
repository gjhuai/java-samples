package syntax

fun main(args: Array<String>) {
    val array = arrayListOf("aaa", "bbb", "ccc")

    fun t1() {
        println("t1")
        array.forEach {
            if (it == "aaa") return
            println(it)
        }
    }

    fun t2() {
        println("t2")
        array.forEach lit@{
            if (it == "aaa") return@lit
            println(it)
        }
    }

    t1()
    t2()
}