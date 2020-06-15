package de.tesis.dynaware.grapheditor.demo.vvk

import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javafx.application.Application.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
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
    delay(5000)
    return "Hi there"
}

fun sayHelloSingle(): Single<String> = rxSingle { sayHello() }

fun sayHelloSingle2(): Flow<String> = rxFlowable<> { sayHello() }


class Model {
    val obs = BehaviorSubject.createDefault(22);
    @ExperimentalCoroutinesApi
    val channel = ConflatedBroadcastChannel<String>()

    suspend fun start() {
        delay(2000)
        channel.send("aaa");
    }

    fun observe(): Flow<String> {
        return channel.asFlow();
    }
}