package com.heig.yfitops

import android.app.Application
import com.heig.yfitops.domain.services.FirebaseRepository

class MyApp : Application() {

    val repository by lazy {
        FirebaseRepository()
    }
}