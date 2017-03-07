package net.business.system.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import net.business.system.entity.TsUser;
import net.business.system.entity.WeatherData;
import net.business.system.service.DashboardService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.Const;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sun.istack.internal.logging.Logger;



@Service("dashboardService")
@Transactional
public class DashboardServiceImpl extends BaseDao<TsUser,String>
		implements DashboardService{
	
	private static Logger log = Logger.getLogger(DashboardService.class);
	
	@Override
	public String getInfoByTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = "";
		String endDate = format.format(new Date());
		List<Object[]> objs = new ArrayList<Object[]>();
		Integer day = 7;
		Calendar calendar = Calendar.getInstance();
		try{
			if(time.equals("week")){
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE, -6);
				startDate = format.format(calendar.getTime());
			}else if(time.equals("month")){
				startDate = endDate.substring(0,endDate.lastIndexOf("-"))+"-01";
				day = Integer.parseInt(endDate.substring(endDate.lastIndexOf("-")+1,endDate.length()));
			}
			
			startDate += " 00:00:00";
			endDate += " 23:59:59";
			
			//时间
			Object[] dates = new Object[day];
			calendar.setTime(format.parse(startDate));
			for(int i=0;i<day;i++){
				dates[i] = format.format(calendar.getTime());
				calendar.add(Calendar.DATE, 1);
				System.out.println("time:"+dates[i]);
			}
			objs.add(dates);
			
			//用户数统计
			String userSql = "select count(*) as a,CONVERT(VARCHAR(10),CREATE_TIME,120) as b from TS_USER where STATUS = 1 "
					+ " and CREATE_TIME >= '"+startDate+"' and CREATE_TIME <= '"+endDate+"'"
					+ " group by CONVERT(VARCHAR(10),CREATE_TIME,120)"
					+ " order by CONVERT(VARCHAR(10),CREATE_TIME,120)";
			List<Object[]> users = findListBySql(userSql, 0, 0);
			Object[] user = new Object[day];
			if(users != null && users.size() != 0){
				int j = 0;
				calendar.setTime(format.parse(startDate));
				for(int i=0;i<day;i++){
					if(j < users.size()){
						String date = users.get(j)[1].toString();
						if(date.equals(format.format(calendar.getTime()))){
							user[i] = Integer.parseInt(users.get(j)[0].toString());
							j++;
						}else{
							user[i] = 0;
						}
					}else{
						user[i] = 0;
					}
					calendar.add(Calendar.DATE, 1);
				}
			}else{
				for(int i=0;i<day;i++){
					user[i] = 0;
				}
			}
			objs.add(user);
			
			//资讯新闻统计
			String contentSql = "select count(*) as a,CONVERT(VARCHAR(10),CREATE_TIME,120) as b from CMS_CONTENT where STATUS = 2 "
					+ " and CREATE_TIME >= '"+startDate+"' and CREATE_TIME <= '"+endDate+"'"
					+ " group by CONVERT(VARCHAR(10),CREATE_TIME,120)"
					+ " order by CONVERT(VARCHAR(10),CREATE_TIME,120)";
			List<Object[]> contents = findListBySql(contentSql, 0, 0);
			Object[] content = new Object[day];
			if(contents != null && contents.size() != 0){
				int j = 0;
				calendar.setTime(format.parse(startDate));
				for(int i=0;i<day;i++){
					if(j < contents.size()){
						String date = contents.get(j)[1].toString();
						if(date.equals(format.format(calendar.getTime()))){
							content[i] = Integer.parseInt(contents.get(j)[0].toString());
							j++;
						}else{
							content[i] = 0;
						}
					}else{
						content[i] = 0;
					}
					calendar.add(Calendar.DATE, 1);
				}
			}else{
				for(int i=0;i<day;i++){
					content[i] = 0;
				}
			}
			objs.add(content);
			
			//视频新闻统计
			String videoSql = "select count(*) as a,CONVERT(VARCHAR(10),CREATE_TIME,120) as b from CMS_VIDEO where STATUS = 2 "
					+ " and CREATE_TIME >= '"+startDate+"' and CREATE_TIME <= '"+endDate+"'"
					+ " group by CONVERT(VARCHAR(10),CREATE_TIME,120)"
					+ " order by CONVERT(VARCHAR(10),CREATE_TIME,120)";
			List<Object[]> videos = findListBySql(videoSql, 0, 0);
			Object[] video = new Object[day];
			if(videos != null && videos.size() != 0){
				int j = 0;
				calendar.setTime(format.parse(startDate));
				for(int i=0;i<day;i++){
					if(j < videos.size()){
						String date = videos.get(j)[1].toString();
						if(date.equals(format.format(calendar.getTime()))){
							video[i] = Integer.parseInt(videos.get(j)[0].toString());
							j++;
						}else{
							video[i] = 0;
						}
					}else{
						video[i] = 0;
					}
					calendar.add(Calendar.DATE, 1);
				}
			}else{
				for(int i=0;i<day;i++){
					video[i] = 0;
				}
			}
			objs.add(video);
			
			//图集新闻统计
			String atlasSql = "select count(*) as a,CONVERT(VARCHAR(10),CREATE_TIME,120) as b from CMS_ATLAS where STATUS = 2 "
					+ " and CREATE_TIME >= '"+startDate+"' and CREATE_TIME <= '"+endDate+"'"
					+ " group by CONVERT(VARCHAR(10),CREATE_TIME,120)"
					+ " order by CONVERT(VARCHAR(10),CREATE_TIME,120)";
			List<Object[]> atlass = findListBySql(userSql, 0, 0);
			Object[] atlas = new Object[day];
			if(atlass != null && atlass.size() != 0){
				int j = 0;
				calendar.setTime(format.parse(startDate));
				for(int i=0;i<day;i++){
					if(j < atlass.size()){
						String date = users.get(j)[1].toString();
						if(date.equals(format.format(calendar.getTime()))){
							atlas[i] = Integer.parseInt(atlass.get(j)[0].toString());
							j++;
						}else{
							atlas[i] = 0;
						}
					}else{
						atlas[i] = 0;
					}
					calendar.add(Calendar.DATE, 1);
				}
			}else{
				for(int i=0;i<day;i++){
					atlas[i] = 0;
				}
			}
			objs.add(atlas);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSON.toJSONString(objs);
	}

	@Override
	public String getOrganData() {
		String sql = "select ORG_NAME,LONGITUDE,LATITUDE from TS_ORGAN where PARENT_ID = ''";
		List<Object[]> objs = findListBySql(sql, 0, 0);
		
		return JSON.toJSONString(objs);
	}
	
	private String mCtiyName;
	private URLConnection connectionData;
	private StringBuilder sb;
	private BufferedReader br;// 读取data数据流
	private JSONObject jsonData;
	
	@Override
	public String getWeather(String addr) {
		String json = "";
		try {
//    		String city = new String(addr.getBytes(),"UTF-8");
			json = dataParse(addr,GetBaiduWeather(addr));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public String GetBaiduWeather(String ctiyName) throws IOException,
			NullPointerException {
		this.mCtiyName = ctiyName;
//		String ak="Ly02LHQnl5O7A41a5xWcxBTUGOL2dW8c";
		String ak=Const.BAIDU_MAP_KEY;
		
		String mCtiyNamecodeString = java.net.URLEncoder.encode(mCtiyName,"utf-8");
		
		String urlString = 	"http://api.map.baidu.com/telematics/v3/weather?location="
				+ mCtiyNamecodeString
				+ "&output=json&ak="+ak;
		System.out.print(urlString);
		URL url = new URL(urlString);
		connectionData = url.openConnection();
		connectionData.setConnectTimeout(5000);
		try {
			br = new BufferedReader(new InputStreamReader(
					connectionData.getInputStream(), "UTF-8"));
			sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line);
		} catch (SocketTimeoutException e) {
			log.info("百度天气连接超时");
		} catch (FileNotFoundException e) {
			log.info("百度天气加载文件出错");
		}
		String datas = sb.toString();
		return datas;
		
		
	}
	
	/*
	 * 解析返回的请求结果
	 */
	private String dataParse(String cityName, String datas) {
		JSONObject jsonData = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curDate = df.format(new Date());
		WeatherData w = new WeatherData();
		List<WeatherData> weatherList = new ArrayList<WeatherData>();
		try {
			jsonData = JSONObject.parseObject(datas);
		} catch (JSONException e) {
			return null;
		}
		
		log.info("==>>" + jsonData.toString());

		String error = jsonData.getString("error");
		String status = jsonData.getString("status");

		if (error.equals("0") && status.equals("success")) {
			
			//保存最新数据
			log.info("--->>>获取【" + cityName + "】天气成功！");
			// 日期
			String dateTime = jsonData.getString("date");
			// 天气结果集
			JSONArray results = jsonData.getJSONArray("results");

			for (int i = 0; i < results.size(); i++) {

				JSONObject resultsItem = results.getJSONObject(i);
				JSONArray weather_data = resultsItem.getJSONArray("weather_data");
				
				
				
				for (int j = 0; j < weather_data.size(); j++) {

					JSONObject item = weather_data.getJSONObject(j);

					// 星期
					String date = item.getString("date");
					// 白天温度图片
					String dayPictureUrl = item.getString("dayPictureUrl");
					// 夜间图片
					String nightPictureUrl = item.getString("nightPictureUrl");
					// 天气情况
					String weather = item.getString("weather");
					// 风力
					String wind = item.getString("wind");
					// 温度
					String temperature = item.getString("temperature");

					/*System.out.println(j + "--星期->" + date.toString());
					System.out.println(j + "--白天图片->"
							+ dayPictureUrl.toString());
					System.out.println(j + "--夜间图片->"
							+ nightPictureUrl.toString());
					System.out.println(j + "--天气情况->" + weather.toString());
					System.out.println(j + "--风力->" + wind.toString());
					System.out.println(j + "--温度->" + temperature);
					System.out
							.println("======================================");
*/
					// 把这条记录保存到WeatherData表里
					
					w = new WeatherData();
					w.setRegionName(cityName);
					w.setRequestTime(new Date());
					w.setCurrentDate(dateTime);
					w.setDate(date);
					w.setDayPictureUrl(dayPictureUrl);
					w.setNightPictureUrl(nightPictureUrl);
					w.setWeather(weather);
					w.setWind(wind);
					w.setTemperature(temperature);
					weatherList.add(w);

				}
			}

		} else {
			log.info("--->>>获取百度天气失败！");
			log.info("--->>>返回的错误代码是-->" + error);

		}
		return JSON.toJSONString(weatherList);

	}

}
