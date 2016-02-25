package fujingdong.com.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import fujingdong.com.mobilesafe.utils.PrefUtils;

/**
 * 获取经纬度坐标的service
 * 需要添加权限
 * Created by Administrator on 2016/2/25.
 */
public class LocationService extends Service {
    private LocationManager lm;
    private MyLocationListener myLocationListener;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//        List<String> allProviders = lm.getAllProviders();//获取所有的位置提供者

        Criteria criteria=new Criteria();//标准
        criteria.setCostAllowed(true);//是否允许付费，比如3g网络等
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//精确度，跟性能耗电量有关

        String bestProvider = lm.getBestProvider(criteria, true);//获取到最佳提供者，第二个参数表示可用才行

        myLocationListener = new MyLocationListener();
//        lm.requestLocationUpdates(lm.GPS_PROVIDER, 0, 0, myLocationListener);//1 位置提供者 2 最短更新时间 3最短更新距离
        lm.requestLocationUpdates(bestProvider, 0, 0, myLocationListener);//1 位置提供者 2 最短更新时间 3最短更新距离


    }
    class MyLocationListener implements LocationListener {

        //位置发生变化
        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();//经度
            double latitude = location.getLatitude();//维度
            float accuracy = location.getAccuracy();//获取精确度
            double altitude = location.getAltitude();//获取海拔
            //接下来可以用SharedPreference保存，以供使用
            PrefUtils.setString(LocationService.this,"location","经度:"+longitude+"纬度:"+latitude);
        }

        //位置提供者状态发生变化
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        //打开gps的时候
        @Override
        public void onProviderEnabled(String provider) {

        }

        //gps关闭的时候
        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lm.removeUpdates(myLocationListener);//当activity销毁时，停止更新位置，节省电量
    }
}
