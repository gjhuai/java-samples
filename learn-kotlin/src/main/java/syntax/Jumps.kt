package syntax.jumps

fun main(args: Array<String>) {

    loop@ for (i in 1..10) {
        println("\ni:$i")
        for (j in 1..5) {
            print("$j,")
            if (j == 3) {
                break@loop
            }
        }
    }

    println("")
    println("end of line")
}