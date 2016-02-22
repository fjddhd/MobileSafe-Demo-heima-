package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import fujingdong.com.mobilesafe.R;
import fujingdong.com.mobilesafe.utils.PrefUtils;
import fujingdong.com.mobilesafe.view.SettingItemView;

/**
 * Created by Administrator on 2016/2/20.
 * 设置中心
 */
public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final SettingItemView sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
//        sivUpdate.setTitle("自动更新设置");
        if (PrefUtils.getBoolean(SettingActivity.this, "update", true)) {
//            sivUpdate.setDec("自动更新开启");
            sivUpdate.setChecked(true);
        } else {
//            sivUpdate.setDec("自动更新已关闭");
            sivUpdate.setChecked(false);
        }
        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前勾选状态
                if (sivUpdate.isChecked()) {
                    sivUpdate.setChecked(false);
//                    sivUpdate.setDec("自动更新已关闭");
                    PrefUtils.setBoolean(SettingActivity.this, "update", false);
                } else {
                    sivUpdate.setChecked(true);
//                    sivUpdate.setDec("自动更新开启");
                    PrefUtils.setBoolean(SettingActivity.this, "update", true);
                }
            }
        });

    }
}
