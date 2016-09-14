package objects.extension

import kotlin.system.measureTimeMillis

/**
 * 特定类扩展
 */
object AExtension {
    // 属性扩展
    val A.size: Int
        get() = name.length

    // 方法扩展
    fun A.say(): String {
        return "Extend say: hi, $name"
    }
}

class A {
    var name: String = "fjay"
}

fun main(args: Array<String>) {
    val exec: () -> Unit = {
        val a = A().with {
            name = "fjayblue"
        }

        val json = a.toJson()
        // 同包级别的扩展自动引入
        println("A toJson:\n" + json)
        assert(json.fromJson(A::class.java).name == a.name)

        // 特定扩展范围
        with(AExtension) {
            println("Extend size: " + a.size)
            println(a.say())
        }

        println("let return: " + a.let {
            it.name
        })
    }

    println("\nSpend time: %d ms".format(measureTimeMillis(exec)))
}