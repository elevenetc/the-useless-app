package com.elevenetc.the.useless.app

fun <T> List<T>.forRandom(handler: (T) -> Unit) {
    var i = this.size - 1

    val list = mutableListOf<T>()

    this.forEach { value ->
        list.add(value)
    }

    while (i >= 0) {
        val rndIdx = (Math.random() * i).toInt()
        handler(list.removeAt(rndIdx))
        i--
    }
}