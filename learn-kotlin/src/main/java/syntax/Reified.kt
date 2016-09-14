package syntax.Reified

inline fun <reified T> methodsOf() = T::class.javaClass.getMethods()

fun main(args: Array<String>) {
    println(methodsOf<String>().joinToString("\n"))
}