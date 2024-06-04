package com.tsp.nicegetup


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

class LocationProvider(val context: Context) {
    //위도와 경도를 담는 객체
    private var location: Location? =  null
    private var locationManager: LocationManager? = null

    init {
        getLocation()
    }

    private fun getLocation(): Location? {
        try {
            //사용자의 위치를 얻을 때는 LocationManager라는 시스템 서비스를 이용한다
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            var gpsLocation: Location? = null
            var networkLocation: Location? = null

            //GPS or Network가 활성화 되었는지 확인
            val isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


            if(!isGPSEnabled && !isNetworkEnabled){
                return null
            } else {
                //location 권한 확인
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return null
                }

                if (isNetworkEnabled){
                    //이 부분은 권한 체크가 필요해서 자동완성 누름
                    networkLocation = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }

                if (isGPSEnabled){
                    gpsLocation = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }

                if (gpsLocation != null && networkLocation != null){
                    if (gpsLocation.accuracy > networkLocation.accuracy){
                        location = gpsLocation
                    } else {
                        //location = networkLocation
                        location = gpsLocation
                    }

                } else {
                    if (gpsLocation != null) {
                        location = gpsLocation
                    }
                    if (networkLocation != null) {
                        location = networkLocation
                    }
                }
            }

        } catch (e: Exception){
            e.printStackTrace()
        }

        return location
    }


    //위도 경도 가져오기
    fun getLocationLatitude(): Double? {
        return location?.latitude
    }

    fun getLocationLongitude(): Double? {
        return location?.longitude
    }


}