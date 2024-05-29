package com.tsp.nicegetup

import android.Manifest
import android.content.Intent
import android.location.LocationManager
import android.location.LocationProvider
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel

class MainActivityViewModel(private val myLocationManger: MyLocationManger): ViewModel() {


    //private lateinit var locationProvider: LocationProvider

    // 결과를 받을 때 어떤 요청이였는지 알기 위해, 식별값
    private val PERMISSON_REQUEST_CODE = 100

    val REQUIED_PERMISSONS = arrayOf(
        //안드로이드 Manifest
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var getGPSPermissionLauncher: ActivityResultLauncher<Intent>


    fun checkAllPermissions() {
        //(기기 설정) 위치 서비스가 켜져있는지
        if (!isLocationServicesAvailable()) {
            showDialogForLocationServiceSetting()
        } else {
            //런타임 권한(앱 실행)
            isRunTimePermissionsGranted()
        }
    }

    private fun isLocationServicesAvailable(): Boolean {

        //val locationManager = getS
        // val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager


    }






}