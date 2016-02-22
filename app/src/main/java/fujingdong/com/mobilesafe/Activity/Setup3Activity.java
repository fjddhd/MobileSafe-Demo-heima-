package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fujingdong.com.mobilesafe.R;

/**
 * Created by Administrator on 2016/2/21.
 * 第三个设置向导页
 */
public class Setup3Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }

    //下一页
    public void next(View view) {
        startActivity(new Intent(Setup3Activity.this, Setup4Activity.class));
        finish();
    }

    //上一页
    public void previous(View view) {
        startActivity(new Intent(Setup3Activity.this, Setup2Activity.class));
        overridePendingTransition(android.support.design.R.anim.design_snackbar_in, android.support.design.R.anim.design_snackbar_out);//回切时的动画
        finish();
    }
}
