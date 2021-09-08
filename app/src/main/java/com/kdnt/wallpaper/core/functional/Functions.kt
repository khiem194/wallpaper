package com.kdnt.wallpaper.core.functional

typealias Supplier<T> = () -> T

interface Consumer<T> {
    fun accept(t: T)
}