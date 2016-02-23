package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import fujingdong.com.mobilesafe.R;

/**
 * Created by Administrator on 2016/2/21.
 * 第一个设置向导页
 */
public class Setup1Activity extends Activity {
    private GestureDetector gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        gd = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            //监听手势滑动
            //e1表示滑动起始点，e2表示滑动终点
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float abs = Math.abs(e2.getRawY() - e1.getRawY());
                if (e2.getRawX()-e1.getRawX()>200 && abs<100) {
                    //向右滑，上一页
                    //这里是第一页，不用这个
                }else if (e1.getRawX()-e2.getRawX()>200 && abs<100){
                    //向左滑，下一页
                    showNextPage();
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    private void showNextPage() {
        startActivity(new Intent(Setup1Activity.this, Setup2Activity.class));
        finish();

    }

    //下一页
    public void next(View view){
        showNextPage();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);//让activity的ontouchevent把滑动事件交给gesturedetector
        return super.onTouchEvent(event);
    }
}
