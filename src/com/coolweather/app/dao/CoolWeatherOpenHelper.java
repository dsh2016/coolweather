package com.coolweather.app.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper{
	//����ʡ��
public static final String Create_province="create table province ("
											+"id integer primayr key autoincrement ,"
											+"name text ,"+"code text)";
//�������б�
public static final String Create_City="create table city ("
		+"id integer primayr key autoincrement ,"
		+"name text ,"+"code text ,"
		+"province_id integer)";
public static final String Create_County="create table county ("
		+"id integer primayr key autoincrement ,"
		+"name text ,"+"code text ,"
		+"City_id integer)";
	public CoolWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Create_province);
		db.execSQL(Create_City);
		db.execSQL(Create_County);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
