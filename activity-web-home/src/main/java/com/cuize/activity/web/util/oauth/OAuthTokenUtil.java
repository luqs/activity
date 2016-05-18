package com.cuize.activity.web.util.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;



/**
 * OAuthToken获取access_token
 * @author JackieLan
 *
 */
public class OAuthTokenUtil {
	
	public final static String AppID = "wxa80956dc1efa927b";  //AppID(应用ID)
	public final static String AppSecret = "72be0f11d1e1adfc0d88cf59d8482bab"; //AppSecret(应用密钥)
	
	//获取Token
	public final static String TokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
		
	public static String access_token = "";//保存获取的凭证
	
	//获取Token
    public static void getToken(IcallBackBase callback){
    	String url = TokenURL +"&appid="+AppID+"&secret="+AppSecret;
        baseAjax(url,"",callback);
    }
    
    /**
	 * 所有请求入口
	 * @param url
	 * @param param
	 * @param type（get,post）
	 * @return 
	 */
	public static void baseAjax(String url,String param,IcallBackBase callback){
		sendPost(url, param,callback);
	}
	/**
	 * 请求ajax
	 * @param url
	 * @param param
	 * @return 
	 */
    public static void sendPost(String url, String param,IcallBackBase callback) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            if(null != param){
            	OutputStream os = conn.getOutputStream();
            	os.write(param.getBytes("UTF-8"));
            	os.close();
            }
            
           // out = new PrintWriter(conn.getOutputStream());// 获取URLConnection对象对应的输出流
            //out.print(param);// 发送请求参数
            //out.flush();// flush输出流的缓冲
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }finally{//使用finally块来关闭输出流、输入流
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        callback.callback(result);
    }
    
    /**
	 * Json 转成 Map<>
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getMap(JSONObject jsonObject){
		try {
			Set<String> keyIter= jsonObject.keySet();
			Object value ;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			for (String keyStr : keyIter) {
				value = jsonObject.get(keyStr);
				valueMap.put(keyStr, value);
			}
			/*String key;
			Object value ;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}*/
			return valueMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * JSONArray 转成 List<Map<>>
	 * @param [{key:val,key:val,....},{key:val,key:val,....},.....]
	 * @return
	 */
	public static List<Map<String, Object>> getJSONArray(JSONArray jsonArray){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject jsonObj;
		try {
			for(int i = 0 ; i < jsonArray.size() ; i ++){
				jsonObj = jsonArray.getJSONObject(i);
				list.add(getMap(jsonObj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * JSONObject 转成 List<Map<>>
	 * @param {key:val,key:val,....}
	 * @return
	 */
	public static Map<String, Object> getJSONObject(JSONObject jsonObjcet){
		return getMap(jsonObjcet);
	}
    
}
