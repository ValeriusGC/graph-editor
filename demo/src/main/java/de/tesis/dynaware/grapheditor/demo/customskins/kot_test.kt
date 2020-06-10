package de.tesis.dynaware.grapheditor.demo.customskins

////import arrow.optics.optics
//import arrow.core.extensions.listk.foldable.foldMap
//import arrow.core.extensions.monoid
//import arrow.core.identity
//import arrow.core.k
//import arrow.optics.*
//
//
//sealed class Maybe<out T> {
//    object None : Maybe<Nothing>()
//    data class Just<T>(val obj: T) : Maybe<T>()
//}
//
//data class Pet(val name: String, val age: Int)
//
//val alice = Pet("Alice", 6)
//
//@optics
//data class Dop(val name: String, val len: Int)
//
//@optics data class Street(val number: Int, val name: String){
//    companion object
//}
//@optics data class Address(val city: String, val street: Street){
//    companion object
//}
//@optics data class Company(val name: String, val address: Address){
//    companion object
//}
//@optics data class Employee(val name: String, val company: Company){
//    companion object
//}
//
//@optics data class Acc(val balance: Int, val available: Int) {
//    companion object
//}
//
//
//fun main() {
//    println("HI");
//
////    val j = Maybe.Just(1)
////    val (i) = j
////
////    val (name, age) = alice
////    println("$name, $age, $i")
////
////    val maybeAlice: Maybe<Pet> = Maybe.Just(alice)
////    when (maybeAlice) {
////        is Maybe.None -> println("NONE")
////        is Maybe.Just<Pet> -> println(maybeAlice.obj.age.toString())
////    }
//
//    val l = listOf(1, 2, 3, 4, 5).k().foldMap(Int.monoid(), ::identity)
//    println("l=$l")
//
//
////    val balanceLens: Lens<Acc, Int> = Acc.Companion.balance
//
////    val employee = Employee("John Doe", Company("Arrow",
////            Address("Functional city", Street(23, "lambda street"))))
////    val employeeCompanyName: Lens<Employee, String> = employee.company.name
//
//
//}