package com.mad41ismailia.weatherforcast.repo.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.*

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)//needed
    fun addWeatherCityData(weatherData: CityWeatherData)

    @Query("update CityWeatherData set weatherData=:data where cityName =:city")//needed
    fun updateCityData(city:String,data:String)

    @Query("Delete from CityWeatherData where cityName =:city")//needed
    fun deleteWeatherCityData(city:String)

    @Query("SELECT * From CityWeatherData where cityName =:city")//needed in add or update, alarm and widget
    suspend fun getCityWeatherDataList(city: String):List<CityWeatherData>

    @Query("SELECT * From CityWeatherData")//needed in add or update
    suspend fun getAllWeatherDataList():List<CityWeatherData>


//    @Query("Delete from CityWeatherData where cityName not in (:cityList)")//fixed a bug
//    fun clearDBNotInList(cityList:List<String?>)

//    @Query("Delete from CityWeatherData")//not need yet, will be needed in refactor
//    fun deleteWeatherCityDataAll()

//    @Query("SELECT id From CityWeatherData where cityName=:city")//needed
//    fun getCurrentId(city:String):CityWeatherData

    @Query("SELECT * From CityWeatherData")//needed
    fun getWeatherLiveData():LiveData<List<CityWeatherData>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlarmToDB(newAlarm: AlarmData)

    @Query("Select * from Alarm")
    fun getAlarms():LiveData<List<AlarmData>>

    @Query("Delete from Alarm where uniqueID=:id")
    fun deleteAlarm(id: String)

    @Query("select * from Alarm where uniqueID=:id")
    fun getAlarm(id: String?): AlarmData

    @Query("select * from Alarm")//in updating alarm units
    fun getAlarmList(): List<AlarmData>

//    @Query("update  CityWeatherData set cityName=:name and weatherData=:data and lat=:lat and lon =:lon where  cityName=:oldName ")
//    fun updateCurrent(oldName:String,name:String,data: String,lat:Double,lon:Double)
}