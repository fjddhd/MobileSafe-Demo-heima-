package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fujingdong.com.mobilesafe.R;

/**
 * Created by Administrator on 2016/2/21.
 * 第一个设置向导页
 */
public class Setup1Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }
    //下一页
    public void next(View view){
        startActivity(new Intent(Setup1Activity.this,Setup2Activity.class));
        finish();

    }
}
