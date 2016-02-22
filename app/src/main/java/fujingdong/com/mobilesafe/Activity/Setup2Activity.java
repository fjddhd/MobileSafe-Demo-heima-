package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fujingdong.com.mobilesafe.R;

/**
 * Created by Administrator on 2016/2/21.
 * 第二个设置向导页
 */
public class Setup2Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    //下一页
    public void next(View view) {
        startActivity(new Intent(Setup2Activity.this, Setup3Activity.class));
        finish();
    }

    //上一页
    public void previous(View view) {
        startActivity(new Intent(Setup2Activity.this, Setup1Activity.class));
        overridePendingTransition(android.support.design.R.anim.design_snackbar_in, android.support.design.R.anim.design_snackbar_out);//回切时的动画
        finish();
    }
}
