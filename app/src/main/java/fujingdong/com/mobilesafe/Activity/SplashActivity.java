package fujingdong.com.mobilesafe.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import fujingdong.com.mobilesafe.R;
import fujingdong.com.mobilesafe.domain.Version;
import fujingdong.com.mobilesafe.utils.PrefUtils;
import fujingdong.com.mobilesafe.utils.StreamUtils;

public class SplashActivity extends AppCompatActivity {

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URLl = 1;
    private static final int CODE_NET = 2;
    private static final int CODE_ENTERHOME = 3;
    private TextView tvversion;
    private int newVersionCode;
    private String newVersionName;
    private String newDescription;
    private String newDownloadUrl;
    private TextView tvProgress;
    //    private String mVersionName;//版本号
//    private int mVersionCode;
//    private String mDesc;
//    private String mDownloadUrl;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_URLl:
                    Toast.makeText(SplashActivity.this, "url异常", Toast.LENGTH_SHORT).show();
                    enterHome();//跳转主页面
                    break;
                case CODE_NET:
                    Toast.makeText(SplashActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    enterHome();//跳转主页面
                    break;
                case CODE_ENTERHOME:
                    enterHome();//跳转主页面
                    break;
                default:
                    break;
            }
        }
    };
    private RelativeLayout rlsplash;//根布局


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        rlsplash = (RelativeLayout) findViewById(R.id.rl_splash);

        tvversion = (TextView) findViewById(R.id.tv_version);
        tvversion.setText("版本号：" + getVersionName());
        tvProgress = (TextView) findViewById(R.id.tv_progress);

        //判断是否需要用户点了更新
        boolean isNeedCheckUpdate = PrefUtils.getBoolean(SplashActivity.this, "update",true);
        if (isNeedCheckUpdate) {
            checkVersion();
        }else {
            mhandler.sendEmptyMessageDelayed(CODE_ENTERHOME,2000);//发送一个延时两秒的消息
        }
        startAnim();


    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        String versionName = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);//获取包的信息
            versionName = packageInfo.versionName;
            System.out.println("版本号：" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            //未找到包名走这个
            e.printStackTrace();
        }
        return versionName;
    }

    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);//获取包的信息
            versionCode = packageInfo.versionCode;
            System.out.println("版本代码：" + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            //未找到包名走这个
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 从服务器获取版本信息进行校验
     */
    private void checkVersion() {


        final long starttime = System.currentTimeMillis();

        //用子线程异步加载
        new Thread() {
            @Override
            public void run() {

                Message msg = Message.obtain();//拿到一个消息
                HttpURLConnection urlConnection = null;
                try {
                    //本机地址用localhost，但是用模拟器要访问本地地址要用10.0.0.2
//                    URL url = new URL("http://10.0.2.2:8080/updata.json");
                    URL url = new URL("http://fjddhd-update.stor.sinaapp.com/update.json");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");//设置请求方法
                    urlConnection.setConnectTimeout(5000);//连接超时时间
                    urlConnection.setConnectTimeout(5000);//响应超时时间
                    urlConnection.connect();//连接服务器
                    int responseCode = urlConnection.getResponseCode();//获取响应码
//                    System.out.println(responseCode);
                    if (responseCode == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        String result = StreamUtils.readFromStream(inputStream);

                        System.out.println("网络返回：" + result);
                        //解析json
//                        JSONObject jo=new JSONObject(result);
//                        mVersionName = jo.getString("versionName");
//                        mVersionCode = jo.getInt("versionCode");
//                        mDesc = jo.getString("description");
//                        mDownloadUrl = jo.getString("downloadUrl");
//                        System.out.println("版本描述："+mDesc);
                        Gson gson = new Gson();
                        Version versiondata = gson.fromJson(result, Version.class);
                        System.out.println("网络获取新的版本号" + versiondata.versionName);
//                        String mversionName = getVersionName();
                        int versionCode = getVersionCode();
                        newVersionCode = versiondata.versionCode;
                        newVersionName = versiondata.versionName;
                        newDescription = versiondata.description;
                        newDownloadUrl = versiondata.downloadUrl;


                        if (versionCode < newVersionCode) {
                            //如果当前版本低于最新版本，则弹出升级对话框
                            msg.what = CODE_UPDATE_DIALOG;//what默认为0
                        } else {
                            //当前为最新版本
                            msg.what = CODE_ENTERHOME;
                        }

                    }


                } catch (MalformedURLException e) {
                    //URL错误的异常
                    msg.what = CODE_URLl;
                    e.printStackTrace();
                } catch (IOException e) {
                    //网络错误异常
                    msg.what = CODE_NET;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long duratime = endTime - starttime;
                    if (duratime < 2000) {
                        try {
                            Thread.sleep(2000 - duratime);//强制休眠至两秒钟再发消息前，为了显示闪屏页
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mhandler.sendMessage(msg);//handler发送消息

                    if (urlConnection != null) {
                        urlConnection.disconnect();//断开连接
                    }
                }
//                 catch (JSONException e) {
//                    //json解析失败异常
//                    e.printStackTrace();
//                    System.out.println("json出错！！！！");
//                }
            }
        }.start();

    }

    /**
     * 升级对话框
     */
    protected void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本：" + newVersionName);
        builder.setMessage(newDescription);
//        builder.setCancelable(false);//不让用户点取消，最好别用，用户体验太差
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("立即更新");
                downloadNewUpdate();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("以后再说");
                enterHome();//跳转主页面
            }
        });
        //设置取消的监听，用户点击返回键时会触发
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();


    }

    /**
     * 下载apk
     */
    private void downloadNewUpdate() {
//        String targetsd;
        tvProgress.setVisibility(View.VISIBLE);//显示进度
//        String target = Environment.getDataDirectory()+"/update.apk";//获取下载路径
        String target = "/storage/sdcard0/Download/mobilesafe2.0.apk";//获取下载路径
//        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
//            //判断有没有sd卡
//            targetsd = Environment.getExternalStorageDirectory() + "/update.apk";
//
//            tvProgress.setVisibility(View.VISIBLE);

        //这个不用URLHttpconnection了，咱们用Xutils
        if (newDownloadUrl != null) {
            System.out.println(newDownloadUrl);
            HttpUtils utils = new HttpUtils();
            utils.download(newDownloadUrl, target, new RequestCallBack<File>() {
                //文件的下载进度
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    tvProgress.setText("下载进度" + current * 100 / total + "%");//显示下载百分比
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    System.out.println("下载成功");

                    //跳转到系统的下载页面
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
//                    startActivity(intent);
                    startActivityForResult(intent, 0);//打开“下载”这个活动时需要返回结果，会回调下面的onActivityResult（）方法。参数与下面的onActivityResult匹配。
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                }
            });
//        }else {
//            Toast.makeText(SplashActivity.this,"尚未安装sd卡",Toast.LENGTH_SHORT).show();
//        }
        }
    }

    /**
     * 上面的startActivityForResult（）执行后会回调，若用户取消安装，会调用
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 跳转主页面
     */

    public void enterHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();//把闪屏页关掉，防止返回键再次回到闪屏页
    }
    /**
     * 开启动画
     */
    private void startAnim(){
        //动画集合
        AnimationSet set =new AnimationSet(false);

        //旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);//旋转时间
        rotate.setFillAfter(true);//保持动画状态
        //缩放动画
        ScaleAnimation scale=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(2000);//时间
        scale.setFillAfter(true);//保持动画状态
        //渐变动画
        AlphaAnimation alpha=new AlphaAnimation(0.3f,1f);
        alpha.setDuration(2000);//时间
        alpha.setFillAfter(true);//保持动画状态

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        //设定动画监听
//        set.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        rlsplash.startAnimation(set);
    }


}
