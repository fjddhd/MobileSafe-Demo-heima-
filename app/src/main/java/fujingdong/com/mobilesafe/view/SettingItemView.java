package fujingdong.com.mobilesafe.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fujingdong.com.mobilesafe.R;

/**
 * Created by Administrator on 2016/2/20.
 */
public class SettingItemView extends RelativeLayout {

    private TextView tvTitle;
    private TextView tvDec;
    private CheckBox cbStatus;
    private static final String NAMESPACE="http://schemas.android.com/apk/res/fujingdong.com.mobilesafe";
    private String mtitlee;
    private String mdec_on;
    private String mdec_off;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);


//        int attributeCount = attrs.getAttributeCount();
//        for (int i=0;i<attributeCount;i++){
//            String attributeName = attrs.getAttributeName(i);
//            String attributeValue = attrs.getAttributeValue(i);
//            System.out.println(attributeName+"="+attributeValue);
//        }

        //根据属性名称获取属性的值
        mtitlee = attrs.getAttributeValue(NAMESPACE, "titlee");
        mdec_on = attrs.getAttributeValue(NAMESPACE, "dec_on");
        mdec_off = attrs.getAttributeValue(NAMESPACE, "dec_off");

        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        //将自定义好的布局文件设置给当前的SettingItemView
        View.inflate(getContext(), R.layout.view_setting_item, this);//第三个参数是将要成为parent的ViewGroup
        tvTitle = (TextView) findViewById(R.id.tv_1);
        tvDec = (TextView) findViewById(R.id.tv_dec1);
        cbStatus = (CheckBox) findViewById(R.id.cb_status);

        setTitle(mtitlee);//设置标题
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDec(String dec) {
        tvDec.setText(dec);
    }

    /**
     * 返回勾选状态
     * @return
     */
    public boolean isChecked(){
        return cbStatus.isChecked();
    }
    public void setChecked(boolean check){
        cbStatus.setChecked(check);
        //根据选择的状态更新文本描述
        if (check){
            setDec(mdec_on);
        }else {
            setDec(mdec_off);
        }
    }

}
