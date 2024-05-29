package com.tsp.nicegetup

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {


    val appContainer = (application as NiceApplication).appContainer


    private val viewModel: ViewModel by lazy {
        ViewModelProvider(this)[ViewModel::class.java]

    }
    lateinit var myLocationManager: MyLocationManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainActivityViewModel = MainActivityViewModel(appContainer.myLocationManger)

        myLocationManager = MyLocationManger(this)
        myLocationManager.getCurrentLocation()

    }
}

class PermissionChecker(val context: Context){



}

class MyLocationManger(val context: Context){

    val locationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
    fun getCurrentLocation(){

    }
}

//뷰모델 팩토리
//의존성주입