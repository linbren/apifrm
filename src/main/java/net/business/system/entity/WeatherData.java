package net.business.system.entity;

import java.io.Serializable;
import java.util.Date;
public class WeatherData implements Serializable {

	private Integer id;
	private String regionName;  //当前城市
	private Date requestTime;  //请求时间 更新时间
	private String currentDate;//当前时间
	private String date;//天气预报时间 如周三
	private String dayPictureUrl;  //白天的天气预报图片url 如 http://api.map.baidu.com/images/weather/day/duoyun.png
	private String nightPictureUrl;  //晚上的天气预报图片url
	private String weather;  //天气状况 如多云
	private String wind;  //风力
	private String temperature;  //温度
	
//	private String date2;//天气预报时间 如周四
//	private String dayPictureUrl2;  //白天的天气预报图片url
//	private String nightPictureUrl2;  //晚上的天气预报图片url
//	private String weather2;  //天气状况 如多云
//	private String wind2;  //风力
//	private String temperature2;  //温度
//	
//	private String date3;//天气预报时间 如周五
//	private String dayPictureUrl3;  //白天的天气预报图片url
//	private String nightPictureUrl3;  //晚上的天气预报图片url
//	private String weather3;  //天气状况 如多云
//	private String wind3;  //风力
//	private String temperature3;  //温度
//	
//	//白天可返回近期3天的天气情况（今天、明天、后天）、晚上可返回近期4天的天气情况（今天、明天、后天、大后天）
//	private String date4;//天气预报时间 如周六
//	private String dayPictureUrl4;  //白天的天气预报图片url
//	private String nightPictureUrl4;  //晚上的天气预报图片url
//	private String weather4;  //天气状况 如多云
//	private String wind4;  //风力
//	private String temperature4;  //温度
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDayPictureUrl() {
		return dayPictureUrl;
	}
	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}
	public String getNightPictureUrl() {
		return nightPictureUrl;
	}
	public void setNightPictureUrl(String nightPictureUrl) {
		this.nightPictureUrl = nightPictureUrl;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	
}