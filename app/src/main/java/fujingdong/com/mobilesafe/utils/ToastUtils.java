package fujingdong.com.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/24.
 * 普通的短吐司工具类
 * 用法：Toastutils.showToast(context,"你想吐司的内容")
 */
public class ToastUtils {
    public static void showToast(Context ctx,String text){
        Toast.makeText(ctx,text,Toast.LENGTH_SHORT).show();
    }
}
