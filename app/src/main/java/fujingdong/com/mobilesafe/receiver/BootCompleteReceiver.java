package fujingdong.com.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import fujingdong.com.mobilesafe.utils.PrefUtils;

/**
 * 监听手机启动的广播，用的时候先注册
 * <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
 * <receiver android:name=".receiver.BootCompleteReceiver">
 <intent-filter>
 <action android:name="android.intent.action.BOOT_COMPLETED"/>
 </intent-filter>
 </receiver>
 * Created by Administrator on 2016/2/23.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String sim = PrefUtils.getString(context, "Sim", null);//获取以前绑定的sim卡序列号
        boolean ischecked = PrefUtils.getBoolean(context, "protect", false);
        if (ischecked) {//只有防盗保护开启了才做这个开机报警
            if (!TextUtils.isEmpty(sim)){
                //获取当前手机sim卡
                TelephonyManager tm= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String simCurrent = tm.getSimSerialNumber();//当前的sim卡序列号
                if (sim.equals(simCurrent)){
                    System.out.println("手机安全");
                    PrefUtils.setString(context,"boottest","手机安全！！！");
                }else {
                    System.out.println("sim卡已经发生变化，发送报警短信！");
                    PrefUtils.setString(context, "boottest", "手机不安全！！！");
                }
            }
        }
    }
}
