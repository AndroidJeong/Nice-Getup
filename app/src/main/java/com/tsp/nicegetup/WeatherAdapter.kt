package com.tsp.nicegetup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tsp.nicegetup.databinding.WeatherItemBinding
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class WeatherAdapter(private val onItemClick: Unit,private val Context: Context) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
        //ViewHolder를 생성하는 함수
        //View객체만큼만 호출되고 종료
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
//        val currentItem = [position]
//        holder.bind(currentItem)

        if (position % 2 == 1){
            holder.binding.weatherItem.setBackgroundColor(ContextCompat.getColor(Context,R.color.navy))
        } else{
            holder.binding.weatherItem.setBackgroundColor(ContextCompat.getColor(Context,R.color.sky_blue))
        }
        //생성된 ViewHolder에 데이터를 압축
        //데이터가 스크롤되어, 맨위 ViewHolder가 맨 아래로 이동한다면, 해당 레이아웃을 다시 보내며 데이터만 바뀌고 그때마다 onBindViewHolder가 호출됩니다.
        //표시질 데이터의 인덱스 값을 위치 값으로 알 수 있습니다.
        //데이터가 목록의 20번째 데이터라면, 위치로 20이 들어옴
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
//        return .size
        //View 에줄 데이터의 전체 길이 반환
    }

    inner class WeatherViewHolder(val binding: WeatherItemBinding) :
      RecyclerView.ViewHolder(binding.root) {

          fun bind(){
              with(binding) {

              }
          }
    }


}
