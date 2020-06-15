package de.tesis.dynaware.grapheditor.demo.vvk

import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javafx.application.Application.launch
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.rx2.rxFlowable
import kotlinx.coroutines.rx2.rxSingle

data class MyKot<T>(val value: T)

public fun <T> f(v: T) : T {
    return  MyKot(v).value
}

abstract class UseCase<Type, Params> {
    abstract fun call(params: Params) : Type
}
class NoParams

suspend fun sayHello(): String {
    delay(1000)
    return "Hi there"
}

fun sayHelloSingle(): Single<String> = rxSingle { sayHello() }


class Model {
    val obs = BehaviorSubject.createDefault(22);
    val onReady = PublishSubject.create<String>()

    fun start() {
        GlobalScope.launch {
            (1..10).forEach {
                onReady.onNext("$it")
                val job = GlobalScope.launch {
                    delay(600)
                }
                job.join()
            }
        }
    }

}