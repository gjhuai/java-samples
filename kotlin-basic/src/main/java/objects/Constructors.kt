package objects.constructors

class Primary (firstName: String, age: Int = 30) {
    var firstName = firstName
    val age = age

    init {
        //this is an anonymous constructor
        //there is no other type of constructor
        this.firstName += ".jr"
    }


    var lastName: String? = null
        get() {
            return "lastName:"+field
        }
        set(value) {
            field = value
        }

    fun sayName() {
        println("My name is ${firstName} and I am $age years old.")
    }

    companion object {
        fun new(a: Int): Primary {
            return Primary("fjay", a)
        }
    }
}

fun main(args: Array<String>) {
    Primary("John Adams", age = 56).sayName()

    Primary("Bon Jovi").sayName()

    val a = Primary.new(1)
    a.sayName()
    a.lastName = "blue"
    println(a.lastName)
}