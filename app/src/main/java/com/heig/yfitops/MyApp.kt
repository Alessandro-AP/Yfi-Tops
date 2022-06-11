package com.heig.yfitops

import android.app.Application
import com.heig.yfitops.domain.repositories.FirebaseRepository

/**
 * Reference to the Repository singleton of the Application
 */
class MyApp : Application() {

    val repository by lazy {
        FirebaseRepository()
    }
}