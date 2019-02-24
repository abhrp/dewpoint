package com.github.abhrp.dewpoint.util

import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoshiProvider @Inject constructor() {
    private var moshi: Moshi = Moshi.Builder().build()

    fun getMoshi() = moshi
}