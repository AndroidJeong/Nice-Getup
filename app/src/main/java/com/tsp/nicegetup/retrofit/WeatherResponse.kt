package com.tsp.nicegetup.retrofit

data class WeatherResponse(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<WeatherData>,
    val city: City,
)

data class WeatherData(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Long,
    val pop: Long,
    val sys: Sys,
    val dt_txt: String,
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val sea_level: Long,
    val grnd_level: Long,
    val humidity: Long,
    val temp_kf: Double,
)

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

data class Clouds(
    val all: Long,
)

data class Wind(
    val speed: Double,
    val deg: Long,
    val gust: Double,
)

data class Sys(
    val pod: String,
)

data class City(
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Long,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long,
)

data class Coord(
    val lat: Double,
    val lon: Double,
)

// serialize로 이름변경?