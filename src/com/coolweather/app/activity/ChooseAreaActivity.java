package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.dao.CommonDao;
import com.coolweather.app.dao.CoolWeatherOpenHelper;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.HttpUtil.HttpCallbackListener;
import com.coolweather.app.util.Utility;

import android.R;
import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAreaActivity extends Activity{
	ListView listview;
	TextView textview;
	List<String> datalist=new ArrayList<String>();
	CommonDao commondao;
	private int currentlevel=0;
	private Province selectprovice;
	private City selectcity;
	private County selectcounty;
	private List<Province> provincelist;
	private List<City> citylist;
	private List<County> countylist;
	ArrayAdapter<String> adapter;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	
	setContentView(com.coolweather.app.R.layout.choose_province);
	listview=(ListView) findViewById(com.coolweather.app.R.id.listView1);
	textview=(TextView) findViewById(com.coolweather.app.R.id.textView1);
	adapter=new ArrayAdapter<String>(ChooseAreaActivity.this,android.R.layout.simple_list_item_1,datalist );
	listview.setAdapter(adapter);
	
	commondao=CommonDao.getInstance(ChooseAreaActivity.this);
	commondao.delelteDatabase();
	listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (currentlevel==0) {
				selectprovice=provincelist.get(arg2);
				Toast.makeText(ChooseAreaActivity.this, selectprovice.getName(), Toast.LENGTH_SHORT).show();
				querycity();
			}else if (currentlevel==1) {
				selectcity=citylist.get(arg2);
				querycount();
			}
		}
	});
	queryprovince();
}
private void queryprovince() {
	// TODO Auto-generated method stub
	provincelist=commondao.queryAllProvince();
	if (provincelist.size()>0&&provincelist.size()<100) {
		datalist.clear();
		for (Province pro : provincelist) {
			datalist.add(pro.getName());
		}
		adapter.notifyDataSetChanged();
		listview.setSelection(0);
		textview.setText("中华人民共和国");
		currentlevel=0;
	}else {					//privince
		
		queryfromServer(null,"privince");
	}
}
private void queryfromServer(final String object,final String type) {
	// TODO Auto-generated method stub
	String address;
	if (!TextUtils.isEmpty(object)) {
		address="http://www.weather.com.cn/data/list3/city"+object+".xml";
	}else {
		address="http://www.weather.com.cn/data/list3/city.xml";
	}
	showprogressDialog();
	HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
		
		@Override
		public void onFinish(String response) {
			// TODO Auto-generated method stub
			boolean result=false;
			if ("privince".equals(type)) {
				result=Utility.handleProvinceResponse(commondao, response);
				Log.e("FirstActivity", "执行到105行");
			}else if ("city".equals(type)) {
				result=Utility.handleCityResponse(commondao, response, selectprovice.getId());
			}else if ("county".equals(type)) {
				result=Utility.handleCountyResponse(commondao, response, selectcity.getId());
			}
			if (result) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						closeprogressDialog();
						if ("privince".equals(type)) {
							queryprovince();
						}else if ("city".equals(type)) {
							querycity();
						}else if ("county".equals(type)) {
							querycount();
						}
					}

					
				});
			}
			
		}
		
		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					closeprogressDialog();
					Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
				}
			});
		}
	});
}
//关闭对话框
private void closeprogressDialog() {
	// TODO Auto-generated method stub
	Log.d("FirstActivity", "关闭对话框进度条");
}
//进展对话框
private void showprogressDialog() {
	// TODO Auto-generated method stub
	Log.d("FirstActivity", "开启对话框进度条");
}
protected void querycount() {
	// TODO Auto-generated method stub
	
}
private void querycity() {
	// TODO Auto-generated method stub
	
}
}
