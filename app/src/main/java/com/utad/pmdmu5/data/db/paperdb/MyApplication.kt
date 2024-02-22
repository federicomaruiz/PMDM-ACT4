package com.utad.pmdmu5.data.db.paperdb

import android.app.Application
import io.paperdb.Paper

class MyApplication : Application() {

    override fun onCreate(){
        super.onCreate()
        Paper.init(applicationContext)

    }
}