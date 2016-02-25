package fujingdong.com.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import fujingdong.com.mobilesafe.R;
import fujingdong.com.mobilesafe.service.LocationService;
import fujingdong.com.mobilesafe.utils.PrefUtils;

/**
 * 拦截短信
 * <receiver android:name=".receiver.SmsReceiver">
 <intent-filter android:priority="1000">
 <action android:name="android.provider.Telephony.SMS_RECEIVER"/>
 </intent-filter>
 </receiver>
 * Created by Administrator on 2016/2/24.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] o = (Object[]) intent.getExtras().get("pdus");
        for (Object object:o){//短信最多140个字节，超出会按多条，所以用数组，因为数据很多，所以for循环只执行一次
            SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
            String oriAddress=message.getOriginatingAddress();//短信来源号码
            String messageBody = message.getMessageBody();//短信信息

            if ("#*alarm*#".equals(messageBody)){
                //播放音乐
                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                mediaPlayer.setVolume(1f,1f);//设置音量
                mediaPlayer.setLooping(true);//单曲循环
                mediaPlayer.start();//播放
                abortBroadcast();//中断短信传递，系统的短信app收不到短信了
            }else if ("#*location*#".equals(messageBody)){
                //获取经纬度
                context.startActivity(new Intent(context, LocationService.class));

                String location = PrefUtils.getString(context, "location", "");

                System.out.println("获取到的经纬度："+location);
                abortBroadcast();//中断短信传递，系统的短信app收不到短信了
            }else if ("#*lockscreen*#".equals(messageBody)){
                abortBroadcast();//中断短信传递，系统的短信app收不到短信了

            }
        }
    }
}
