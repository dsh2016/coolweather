package com.coolweather.app.dao;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommonDao {
public static final String DB_Name="cool_weather";
public static final int version=1;
public static CommonDao commondao;
private SQLiteDatabase db;
//创建数据库
private CommonDao(Context context){
	CoolWeatherOpenHelper dbhelper=new CoolWeatherOpenHelper(context, DB_Name,null, version);
	Log.d("FirstActivity", "执行db");
	db=dbhelper.getWritableDatabase();
}
public void delelteDatabase(){
	db.delete("province", null, null);
	db.delete("city", null, null);
	db.delete("county", null, null);
	
}
//获取commondao实例
public synchronized static CommonDao getInstance(Context context){
	if (commondao==null) {
		commondao=new CommonDao(context);
	}
	return commondao;
}
//存储省信息
public void saveProvince(Province province){
	if (province!=null) {
		ContentValues contentvalue=new ContentValues();
		contentvalue.put("name", province.getName());
		contentvalue.put("code", province.getCode());
		db.insert("province", null, contentvalue);
		Log.d("FirstActivity", province.getCode()+"code");
		Log.d("FirstActivity", province.getName()+"name");
		Log.d("FirstActivity", province.getId()+"id");
	}
}

//获取全部省信息
public List<Province> queryAllProvince(){
	List<Province> li=new ArrayList<Province>();
	Cursor consor=db.query("province", null, null, null, null, null, null);
	Log.d("FirstActivity", "到这开看");
	int a=1;
	if (consor.moveToFirst()) {
		
		do {
			Province province=new Province();
			province.setId(consor.getInt(consor.getColumnIndex("id")));
			province.setName(consor.getString(consor.getColumnIndex("name")));
			province.setCode(consor.getString(consor.getColumnIndex("code")));
			li.add(province);
			Log.d("FirstActivity", ""+a++);
		} while (consor.moveToNext());
		
	}
	if (consor!=null) {
		consor.close();
	}
	return li;
}
//存储城市信息
public void saveCity(City city){
	if (city!=null) {
		ContentValues content=new ContentValues();
		content.put("name", city.getName());
		content.put("code", city.getCode());
		content.put("province_id", city.getProvince_id());
		db.insert("city", null, content);
	}
}
//获取城市信息
public List<City> queryAllCity(int province){
	List<City> li=new ArrayList<City>();
	Cursor cursor=db.query("city", null, "province_id=?", new String[]{String.valueOf(province)}, null, null, null);
	if (cursor.moveToFirst()) {
		do {
			City city=new City();
			city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			city.setName(cursor.getString(cursor.getColumnIndex("name")));
			city.setCode(cursor.getString(cursor.getColumnIndex("code")));
			city.setProvince_id(cursor.getInt(cursor.getColumnIndex("province_id")));
			li.add(city);
		} while (cursor.moveToNext());
		if (cursor!=null) {
			cursor.close();
		}
	}
	return li;
}
//存储县
public void saveCounty(County county){
	if (county!=null) {
		ContentValues value=new ContentValues();
		value.put("name", county.getName());
		value.put("code", county.getCode());
		value.put("City_id", county.getCity_id());
		db.insert("county", null, value);
	}
}
//获取县信息
public List<County> queyrAllCounty(int cityid){
	List<County> li=new ArrayList<County>();
	Cursor cursor=db.query("county", null, "City_id=?",new String[]{String.valueOf(cityid)}, null, null, null);
	if (cursor.moveToFirst()) {
		do {
			County county=new County();
			county.setCity_id(cursor.getInt(cursor.getColumnIndex("id")));
			county.setName(cursor.getString(cursor.getColumnIndex("name")));
			county.setCode(cursor.getString(cursor.getColumnIndex("code")));
			county.setCity_id(cursor.getInt(cursor.getColumnIndex("City_id")));
			li.add(county);
		} while (cursor.moveToNext());
		if (cursor!=null) {
			cursor.close();
		}
	}
	return li;
}
}
