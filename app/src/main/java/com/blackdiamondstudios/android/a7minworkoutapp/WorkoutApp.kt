package com.blackdiamondstudios.android.a7minworkoutapp

import android.app.Application

class WorkoutApp: Application(){
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}