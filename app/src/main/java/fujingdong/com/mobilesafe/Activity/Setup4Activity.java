package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import fujingdong.com.mobilesafe.R;
import fujingdong.com.mobilesafe.utils.PrefUtils;

/**
 * Created by Administrator on 2016/2/21.
 * 第三个设置向导页
 */
public class Setup4Activity extends Activity {
    private GestureDetector gd;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        checkBox= (CheckBox) findViewById(R.id.cb_protect);
        boolean ischecked = PrefUtils.getBoolean(Setup4Activity.this, "protect", false);
        if(ischecked){
            checkBox.setChecked(true);
            checkBox.setText("防盗保护已经开启");
        }else{
            checkBox.setChecked(false);
            checkBox.setText("防盗保护没有开启");
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //当checkbox的状态发生变化，就回调此方法
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkBox.setText("防盗保护已经开启");
                    PrefUtils.setBoolean(Setup4Activity.this,"protect",true);
                }else {
                    checkBox.setText("防盗保护没有开启");
                    PrefUtils.setBoolean(Setup4Activity.this, "protect", false);
                }
            }
        });
        gd = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            //监听手势滑动
            //e1表示滑动起始点，e2表示滑动终点
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float abs = Math.abs(e2.getRawY() - e1.getRawY());
                if (e2.getRawX() - e1.getRawX() > 200 && abs<100) {
                    //向右滑，上一页
                    showPreviousPage();
                } else if (e1.getRawX() - e2.getRawX() > 200 && abs<100) {
                    //向左滑，下一页
                    //这里是最后一页了
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    private void showPreviousPage() {
        startActivity(new Intent(Setup4Activity.this,Setup3Activity.class));
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);//回切时的动画
        finish();
    }

    //下一页
    public void next(View view) {
        startActivity(new Intent(Setup4Activity.this, LostFindActivity.class));
        PrefUtils.setBoolean(Setup4Activity.this, "showedguide", true);
        finish();
    }
    //上一页
    public void previous(View view){
        showPreviousPage();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);//让activity的ontouchevent把滑动事件交给gesturedetector
        return super.onTouchEvent(event);
    }
}
