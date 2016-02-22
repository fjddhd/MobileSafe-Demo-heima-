package fujingdong.com.mobilesafe.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/2/20.
 * 获取焦点的textview
 */
public class FocusedTextView extends TextView {
    /**
     * 直接new一个textview时
     * */
    public FocusedTextView(Context context) {
        super(context);
    }

    /**
     * 有属性时
     * */
    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 有stule样式时
     * */
    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 表示有没有获取焦点，默认是false
     *
     * 跑马灯（ellipsize="marquee"）要运行，首先要调用这个函数判断是否有焦点，是true才可以跑马灯
     * 这里无论有没有焦点，都强制返回true，让跑马灯认为有焦点
     * */
    @Override
    public boolean isFocused() {
        return true;
    }
}
