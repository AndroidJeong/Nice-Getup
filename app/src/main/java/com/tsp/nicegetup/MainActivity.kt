package com.tsp.nicegetup

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tsp.nicegetup.databinding.ActivityMainBinding
import com.tsp.nicegetup.retrofit.Api
import com.tsp.nicegetup.retrofit.RetrofitInstance
import com.tsp.nicegetup.retrofit.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherFragment = WeatherFragment()
    private val getupFragment = GetupFragment()


    private lateinit var locationProvider: LocationProvider

    //런타임 권한 요청 시 필요한 요청 코드
    //결과를 받을 때 어떤 요청이였는지 알기 위해, 식별값
    private val PERMISSON_REQUEST_CODE = 100

    //요청할 권한 목록
    val REQUIED_PERMISSONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    //ActivityResultLauncher 결과값을 반환해야 하는 인텐트를 실행가능
    //런처를 통해 A -> B 이동후 B -> A 데이터 보내주기
    lateinit var getGPSPermissionLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // splash screen 설정, 관리 API함수
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        selectedListener()

        // 앱 실행 시 첫화면 설정 코드
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.botNavi_menu_weather
        }


        checkAllPermissions()


    }

    // bottom navigation 아이템 클릭 리스너 설정
    private fun selectedListener() {

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.botNavi_menu_weather -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentsFrame, weatherFragment)
                        .addToBackStack(null)
                        .setReorderingAllowed(true)
                        .commit()
                    true
                }
                R.id.botNavi_menu_getup -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentsFrame, getupFragment)
                        .addToBackStack(null)
                        .setReorderingAllowed(true)
                        .commit()
                    true
                }

                else -> false
            }

        }

    }

    private fun checkAllPermissions() {
        //(기기 권한) 위치 서비스가 켜져있는지
        if (!isLocationServicesAvailable()) {
            showDialogForLocationServiceSetting()
        } else {
            //런타임 권한(앱 권한)
            isRunTimePermissionsGranted()
        }
    }

    private fun isLocationServicesAvailable(): Boolean {

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        //GPS는 위성신호를 수신해서 위치 판독, 네트워크는 와아파이 네크워크나 기지국 등으로 위치 판독
        //둘 중 하나라도 enabled라면 true 반환
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ))
    }

    private fun isRunTimePermissionsGranted() {

        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        //퍼미션이 하나라도 없다면 권한 요청
        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                //array
                REQUIED_PERMISSONS,
                PERMISSON_REQUEST_CODE
            )
        }
    }

    //권한요청의 결과값 처리를 override해서 구현
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //내가 보낸 리퀘스트가 맞다면
        if (requestCode == PERMISSON_REQUEST_CODE && grantResults.size == REQUIED_PERMISSONS.size) {
            var checkResult = true

            //둘다 권한이 허용되었는지 확인
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }

            if (checkResult) {
                useWeatherAPI()
                //위치값을 가져와서 할일, 프레그먼트에 표시하기
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "권한이 거부되었습니다. 앱을 다시 실행해서 권한을 허용해주세요.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }

    }

    private fun showDialogForLocationServiceSetting() {
        getGPSPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
            // result를 받았을 때 콜백 함수가 실행
        ) { result ->
            //Activity.RESULT_OK == 설정 여부가 아니라 돌아왔는지 여부
            if (result.resultCode == Activity.RESULT_OK) {
                if (isLocationServicesAvailable()) {
                    isRunTimePermissionsGranted()
                } else {
                    Toast.makeText(this@MainActivity, "위치 서비스를 사용할 수 없습니다.", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage("위치 서비스가 꺼져있습니다. 설정해야 앱을 사용할 수 있습니다.")
        //다이얼로그 창 밖 터치시 끌 수 있게
        builder.setCancelable(true)
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, which ->
            //안드로이드 기기 설정앱의 GPS 설정 페이지 이동
            val callGPSSettingIntent =
                Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            getGPSPermissionLauncher.launch(callGPSSettingIntent)
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            Toast.makeText(this@MainActivity, "위치 서비스를 사용할 수 없습니다.", Toast.LENGTH_LONG).show()
            finish()
        })
        builder.create().show()
    }

    private fun useWeatherAPI() {
        //retofit 객체.create -> 인터페이스 구현체들을 레트로핏에서 만들어준다
        var retrofitAPI = RetrofitInstance.getInstance().create(
            Api::class.java
        )

        locationProvider = LocationProvider(this@MainActivity)
        //내위치 위도 & 경도
        val latitude: Double? = locationProvider.getLocationLatitude()
        val longitude: Double? = locationProvider.getLocationLongitude()

        retrofitAPI.getWeatherList(
            latitude.toString(),
            longitude.toString(),
            "fba6c31414337e08cd1c788fc40addf7",
            units = "metric"
        ).enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                response.body()?.let { getWeatherData(it) }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                //에러 출력
                t.printStackTrace()
                Toast.makeText(this@MainActivity, "데이터를 가져오는데 실패했습니다.", Toast.LENGTH_LONG).show()
            }
        }
        )
    }

    private fun getWeatherData(data: WeatherResponse) {

        //데이터를 받아 쓸 데이터만 정제하는 부분
        val todayTem = data.list[0].main.temp
        Log.d("taag", "${data.list}")

    }










}

