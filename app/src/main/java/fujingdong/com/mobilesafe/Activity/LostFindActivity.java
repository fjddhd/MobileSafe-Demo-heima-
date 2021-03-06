package fujingdong.com.mobilesafe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fujingdong.com.mobilesafe.R;
import fujingdong.com.mobilesafe.utils.PrefUtils;

/**
 * Created by Administrator on 2016/2/21.
 * 手机防盗
 */
public class LostFindActivity extends Activity {

    private TextView phonenum;
    private ImageView ivlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean showedguide = PrefUtils.getBoolean(LostFindActivity.this, "showedguide", false);//是否展示过设置向导，默认没有
        boolean ischecked = PrefUtils.getBoolean(LostFindActivity.this, "protect", false);
        String safephone = PrefUtils.getString(this, "safephone", null);
        if (showedguide) {
            setContentView(R.layout.activity_lost_find);
            phonenum = (TextView) findViewById(R.id.tv_phonenum);
            ivlock = (ImageView) findViewById(R.id.iv_lock);
            phonenum.setText(safephone);
            if (ischecked){
                ivlock.setImageResource(R.drawable.lock);
            }else{
                ivlock.setImageResource(R.drawable.unlock);
            }
        } else {
            //跳转设置向导页
            startActivity(new Intent(LostFindActivity.this, Setup1Activity.class));
            finish();
        }

    }

    /**
     * 重新进入向导
     * @param view
     */
    public void reEnter(View view) {
        startActivity(new Intent(LostFindActivity.this,Setup1Activity.class));
        finish();


    }
}
