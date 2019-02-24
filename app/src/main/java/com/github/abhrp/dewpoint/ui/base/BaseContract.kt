package com.github.abhrp.dewpoint.ui.base

interface BaseContract {
    interface BasePresenter<in T> {
        fun subscribe()
        fun unsubscribe()
        fun attach(view: T)
    }

    interface BaseView {
        fun showProgressLoader()
        fun hideProgressLoader()
        fun showError(error: String)
    }
}