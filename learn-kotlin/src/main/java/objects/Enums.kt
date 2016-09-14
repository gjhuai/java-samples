package objects.enums


enum class BizCode(val message: String = "系统错误") : ErrorCode {

    TIMEOUT("超时"),

    ERROR();

    override fun message(): String {
        return message
    }

    override fun name(): String {
        return this.name
    }

    override fun toString(): String {
        return name() + ":" + message()
    }
}

interface ErrorCode {

    fun name(): String;

    fun message(): String;

}

fun main(args: Array<String>) {
    println(BizCode.TIMEOUT.toString())
    println(BizCode.ERROR.toString())
}