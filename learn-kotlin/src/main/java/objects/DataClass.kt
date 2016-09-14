package objects.dataClass

data class User(val name: String = "Alex", val id: Int) {
    var blue: String? = null

    override fun toString(): String {
        return "name = ${name}, id = ${id}"
    }
}

fun main(args: Array<String>) {
    val user = User(id = 1)
    println(user)
    val (name, id) = user
    println("name = $name, id = $id")

    println("name = ${user.component1()}, id = ${user.component2()}")

    val other = user.copy(name = "fjay")
    println(other)
}
