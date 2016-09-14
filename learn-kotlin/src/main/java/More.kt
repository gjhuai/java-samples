fun main(args: Array<String>) {
    var x = """class A%d(val name:String, val age:Int)"""

    for (i in 1..2) {
        println(x.format(i))
    }

    Thread { println(1234) }.start();
}