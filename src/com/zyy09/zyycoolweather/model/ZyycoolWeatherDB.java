package com.zyy09.zyycoolweather.model;

import java.util.ArrayList;
import java.util.List;

import com.zyy09.zyycoolweather.db.ZyyCoolWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ZyycoolWeatherDB {

	/**
	 * ���ݿ���
	 */
	public static final String DB_NAME = "zyycool_weather";

	/**
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	private static ZyycoolWeatherDB zyycoolWeatherDB;
	private SQLiteDatabase db;

	/**
	 * ���췽�� private
	 */
	private ZyycoolWeatherDB(Context context) {
		ZyyCoolWeatherOpenHelper dbHelper = new ZyyCoolWeatherOpenHelper(null,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * ��ȡZyycoolWeatherDB��ʵ��
	 */
	public synchronized static ZyycoolWeatherDB getInstance(Context context) {
		if (zyycoolWeatherDB == null) {
			zyycoolWeatherDB = new ZyycoolWeatherDB(context);
		}
		return zyycoolWeatherDB;
	}

	/**
	 * ��Provinceʵ���洢�����ݿ⡣
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * �����ݿ��ȡȫ�����е�ʡ����Ϣ
	 */
	public List<Province> loadProvince() {
		List<Province> provinceList = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				provinceList.add(province);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return provinceList;

	}

	/**
	 * ��Cityʵ���洢�����ݿ�
	 */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	/**
	 * �����ݿ��ȡĳ��ʡ�����еĳ�����Ϣ
	 */
	public List<City> loadCities(int provinceId) {
		List<City> cityList = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?",
				new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				cityList.add(city);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return cityList;
	}
	
	/**
	 * ��Countyʵ���洢�����ݿ�
	 */
	public void saveCounty(County county) {
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", county.getCountyName());
			values.put("city_code", county.getCountyCode());
			values.put("province_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	/**
	 * �����ݿ��ȡĳ���������е�����Ϣ
	 */
	public List<County> loadCounties(int cityId) {
		List<County> countyList = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				countyList.add(county);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return countyList;
	}
}
