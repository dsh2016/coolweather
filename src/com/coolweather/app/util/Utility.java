package com.coolweather.app.util;

import android.R.string;
import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.dao.CommonDao;
import com.coolweather.app.dao.CoolWeatherOpenHelper;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility {
	// 解析和处理服务器返回结果和省级数据
	public synchronized static boolean handleProvinceResponse(
			CommonDao commondao, String response) {
		if (!TextUtils.isEmpty(response)) {
			Log.d("FirstActivity", "utility 解析数据");
			String[] allprovince = response.split(",");
			if (allprovince != null && allprovince.length > 0) {

				for (String p : allprovince) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setCode(array[0]);
					province.setName(array[1]);
					commondao.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	// 解析城市信息
	public synchronized static boolean handleCityResponse(CommonDao commondao,
			String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCity = response.split(",");
			if (allCity != null && allCity.length > 0) {

				for (String c : allCity) {
					String[] array = c.split("//|");

					City city = new City();
					city.setCode(array[0]);
					city.setName(array[1]);
					city.setProvince_id(provinceId);
					commondao.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	// 解析县信息
	public synchronized static boolean handleCountyResponse(
			CommonDao commondao, String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounty = response.split(",");
			if (allCounty != null && allCounty.length > 0) {

				for (String county : allCounty) {
					String[] array = county.split("//|");
					County cou = new County();
					cou.setCode(array[0]);
					cou.setName(array[1]);
					cou.setCity_id(cityId);
					commondao.saveCounty(cou);

				}
				return true;
			}
		}
		return false;
	}
}
