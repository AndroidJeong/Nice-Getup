package com.tsp.nicegetup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tsp.nicegetup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding
   private val weatherFragment = WeatherFragment()
   private val getupFragment = GetupFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

}