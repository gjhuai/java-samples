package objects.extension.sub

import objects.extension.A
import objects.extension.toJson


fun main(args: Array<String>) {
    // 手工引入扩展
    println(A().toJson())
}