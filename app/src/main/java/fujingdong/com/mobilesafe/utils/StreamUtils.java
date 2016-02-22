package fujingdong.com.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/18.
 * 读取流的工具
 */
public class StreamUtils {
    /**
     * 将输入流读取成String返回
     * @param inputStream
     * @return
     */
    public static String readFromStream(InputStream inputStream) throws IOException{
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        int len=0;
        byte[] buffer=new byte[1024];
            while ((len= inputStream.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            String result=out.toString();
            inputStream.close();
            out.close();
//        System.out.println("第一次"+result);


        return result;
    }
}
