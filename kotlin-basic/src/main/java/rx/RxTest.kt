package rx.RxTest

import rx.Observable

public object T1 {

    fun from() {
        println("start")
        Observable.from(arrayOf("1", "2", "3", "4")).map {
            println(Thread.currentThread())
            Thread.sleep(1000)
            it + "~"
        }.subscribe {
            println(Thread.currentThread())
            println(it)
        }
        println("end")
    }

    fun defer(): Observable<Unit> {
        return Observable.defer {
            Observable.just (from())
        }
    }
}

fun main(args: Array<String>) {
    T1.defer().subscribe()
}