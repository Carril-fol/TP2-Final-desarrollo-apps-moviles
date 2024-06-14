package com.ifts4.tp2_final

import android.app.Application


class TpAppClass: Application() {

    companion object {
        lateinit var instance: TpAppClass
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}