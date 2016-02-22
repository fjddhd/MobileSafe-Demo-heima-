package fujingdong.com.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/2/20.
 */
public class PrefUtils {

        public static final String PREF_NAME="config";

        public static boolean getBoolean(Context ctx,String key,boolean defaultValue){
            SharedPreferences sp=ctx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
            return sp.getBoolean(key,defaultValue);
        }
        public static void setBoolean(Context ctx,String key,boolean Value){
            SharedPreferences sp=ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            sp.edit().putBoolean(key,Value).commit();
        }

        public static String getString(Context ctx, String key, String defaultValue){
            SharedPreferences sp=ctx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
            return sp.getString(key,defaultValue);
        }
        public static void setString(Context ctx, String key, String defaultValue) {
            SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            sp.edit().putString(key, defaultValue).commit();


    }
}
