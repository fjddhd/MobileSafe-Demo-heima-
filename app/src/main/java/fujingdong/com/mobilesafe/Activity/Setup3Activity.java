package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;

import fujingdong.com.mobilesafe.R;
import fujingdong.com.mobilesafe.utils.PrefUtils;
import fujingdong.com.mobilesafe.utils.ToastUtils;

/**
 * Created by Administrator on 2016/2/21.
 * 第三个设置向导页
 */
public class Setup3Activity extends Activity {
    private GestureDetector gd;
    private EditText etphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        etphone = (EditText) findViewById(R.id.et_phone);
        gd = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            //监听手势滑动
            //e1表示滑动起始点，e2表示滑动终点
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float abs = Math.abs(e2.getRawY() - e1.getRawY());
                if (e2.getRawX() - e1.getRawX() > 200 && abs < 100) {
                    //向右滑，上一页
                    showPreviousPage();
                } else if (e1.getRawX() - e2.getRawX() > 200 && abs < 100) {
                    //向左滑，下一页
                    showNextPage();
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        String safephone = PrefUtils.getString(this, "safephone", "");
        etphone.setText(safephone);
    }

    private void showNextPage() {

        String phonetext = etphone.getText().toString().trim();//过滤空格
        System.out.println(phonetext);
        if (TextUtils.isEmpty(phonetext)) {
//            Toast.makeText(Setup3Activity.this,"必须填写安全号码才可以进行下一步设置",Toast.LENGTH_SHORT).show();
            ToastUtils.showToast(this, "必须填写安全号码才可以进行下一步设置");

        } else {
            PrefUtils.setString(this,"safephone",phonetext);//保存安全号码
            startActivity(new Intent(Setup3Activity.this, Setup4Activity.class));
            finish();
        }

    }

    private void showPreviousPage() {
        String phonetext = etphone.getText().toString().trim();//过滤空格
        PrefUtils.setString(this,"safephone",phonetext);//保存安全号码
        startActivity(new Intent(Setup3Activity.this, Setup2Activity.class));
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);//回切时的动画
        finish();
    }


    /**
     * 选择联系人
     *
     * @param view
     */
    public void contact(View view) {
        startActivityForResult(new Intent(this, ContactActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String phone = data.getStringExtra("phone");

            String s = phone.replaceAll("-", "").replaceAll(" ", "");//去掉所有短线和空格
            etphone.setText(s);//把电话号码设置给输入框
            super.onActivityResult(requestCode, resultCode, data);
        }
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
