package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import fujingdong.com.mobilesafe.R;
import fujingdong.com.mobilesafe.utils.PrefUtils;
import fujingdong.com.mobilesafe.utils.ToastUtils;
import fujingdong.com.mobilesafe.view.SettingItemView;

/**
 * Created by Administrator on 2016/2/21.
 * 第二个设置向导页
 */
public class Setup2Activity extends Activity {

    private GestureDetector gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        final SettingItemView sivsim= (SettingItemView) findViewById(R.id.siv_sim);
        String gotsim = PrefUtils.getString(Setup2Activity.this, "Sim", null);
        if (TextUtils.isEmpty(gotsim)){
            sivsim.setChecked(false);
        }else {
            sivsim.setChecked(true);
        }
        sivsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivsim.isChecked()) {
                    sivsim.setChecked(false);
                    PrefUtils.removeString(Setup2Activity.this, "Sim");//删除已绑定的sim卡
                } else {
                    sivsim.setChecked(true);
                    //保存sim卡信息
                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String simSerialNumber = tm.getSimSerialNumber();//调用系统api（telephonymanager）获取sim卡序列号
                    System.out.println(simSerialNumber);
                    PrefUtils.setString(Setup2Activity.this, "Sim", simSerialNumber);
                }
            }
        });


        gd = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            //监听手势滑动
            //e1表示滑动起始点，e2表示滑动终点
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float abs = Math.abs(e2.getRawY() - e1.getRawY());
                if (e2.getRawX()-e1.getRawX()>200 && abs<100) {
                    //向右滑，上一页
                    showPreviousPage();
                }else if (e1.getRawX()-e2.getRawX()>200 && abs<100){
                    //向左滑，下一页
                    showNextPage();
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    //方法的封装
    public void showPreviousPage(){
        startActivity(new Intent(Setup2Activity.this, Setup1Activity.class));
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);//回切时的动画
        finish();
    }
    //方法的封装
    public  void showNextPage(){
        String sim=PrefUtils.getString(this,"Sim",null);
        if (TextUtils.isEmpty(sim)){
            ToastUtils.showToast(this,"必须先绑定sim卡才能进行下一步");
            return;
        }
        startActivity(new Intent(Setup2Activity.this, Setup3Activity.class));
        finish();
    }
    //下一页
    public void next(View view) {
        showNextPage();
    }

    //上一页
    public void previous(View view) {
        showPreviousPage();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);//让activity的ontouchevent把滑动事件交给gesturedetector
        return super.onTouchEvent(event);
    }
}
