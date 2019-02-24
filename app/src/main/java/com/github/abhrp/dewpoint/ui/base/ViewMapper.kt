package com.github.abhrp.dewpoint.ui.base

interface ViewMapper<in M, out V> {
    fun mapToView(model: M): V
}