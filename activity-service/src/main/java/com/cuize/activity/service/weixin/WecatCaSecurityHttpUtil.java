package com.cuize.activity.service.weixin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;

/**
 * 包含微信支付证书POST请求
 * @author JackieLan
 *
 */
public class WecatCaSecurityHttpUtil {

	@Deprecated
	private static void post2(String content) throws Exception{
	//指定读取证书格式为PKCS12
	KeyStore keyStore = KeyStore.getInstance("PKCS12");
	//读取本机存放的PKCS12证书文件
/*	FileInputStream instream = new FileInputStream(new File("D:/apiclient_cert.p12"));
	try {
	//指定PKCS12的密码(商户ID)
	keyStore.load(instream, "1326533901".toCharArray());
	} finally {
	instream.close();
	}
	SSLContext sslcontext = SSLContexts.custom()
			.loadKeyMaterial(keyStore, "1326533901".toCharArray()).build();*/
	FileInputStream instream = new FileInputStream(new File("D:/gzh/apiclient_cert.p12"));
	try {
	//指定PKCS12的密码(商户ID)
	keyStore.load(instream, "1251209101".toCharArray());
	} finally {
	instream.close();
	}
	SSLContext sslcontext = SSLContexts.custom()
			.loadKeyMaterial(keyStore, "1251209101".toCharArray()).build();
	
	//指定TLS版本
	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	sslcontext,new String[] { "TLSv1" },null,
	SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER); //设置httpclient的SSLSocketFactory
	//CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	 SSLSocketFactory ssf = sslcontext.getSocketFactory(); 
	
	 URL myURL = new URL("https://api.mch.weixin.qq.com/mmpaymkttransfers/hbpreorder"); 
    // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象 
    HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection(); 
    httpsConn.setSSLSocketFactory(ssf); 
    
    
  /*
    // 进行编码
    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
    // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
    // 要注意的是connection.getOutputStream会隐含的进行connect。
    connection.connect();
    DataOutputStream out = new DataOutputStream(connection
            .getOutputStream());
    // The URL-encoded contend
    // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
    String content = "account=" + URLEncoder.encode("一个大肥人", "UTF-8");
    content +="&pswd="+URLEncoder.encode("两个个大肥人", "UTF-8");;
    // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
    out.writeBytes(content);

    out.flush();
    out.close(); 
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String line;
    
    while ((line = reader.readLine()) != null){
        System.out.println(line);
    }
  
    reader.close();*/
    // http正文内，因此需要设为true
    httpsConn.setDoOutput(true);
    // Read from the connection. Default is true.
    httpsConn.setDoInput(true);
    httpsConn.setRequestMethod("POST");            
    httpsConn.setRequestProperty("Charset", "UTF-8");
    httpsConn.setRequestProperty("Connection", "Keep-Alive");
    httpsConn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
    
    
    DataOutputStream out = new DataOutputStream(httpsConn
            .getOutputStream());
    // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
    out.writeBytes(content);

    out.flush();
    out.close(); 
    
    // 取得该连接的输入流，以读取响应内容 
   // InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream()); 
    // 读取服务器的响应内容并显示 
	  InputStreamReader im = new InputStreamReader(httpsConn.getInputStream(),  
            "UTF-8");  
    BufferedReader reader = new BufferedReader(im);  
    StringBuffer sb = new StringBuffer();  
    String line = null;  
    while ((line = reader.readLine()) != null) {  
        sb.append(line + "\r\n");  
    }  
    System.out.println(sb);   
	}
	
	/**
	 * CA-POST
	 * 注意cerFilePath
	 * 区分微信公众号证书和服务商证书
	 * 
	 * @param content
	 * @param key
	 * @param cerFilePath
	 * @throws Exception
	 */
	public static String post(String content,String key, String cerFilePath, String url) throws Exception{
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(cerFilePath));
        //FileInputStream instream = new FileInputStream(new File("D:/shanghu/apiclient_cert.p12"));
        try {
            keyStore.load(instream, key.toCharArray());
        } finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, key.toCharArray())
                .build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        
        URL requestUrl = new URL(url); 
        HttpsURLConnection httpsConn = (HttpsURLConnection)requestUrl.openConnection();

        //设置套接工厂 
        httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());

        //加入数据 
        httpsConn.setRequestMethod("POST"); 
        httpsConn.setDoOutput(true); 
        DataOutputStream out = new DataOutputStream( 
        httpsConn.getOutputStream()); 

        out.writeBytes(content);          
        out.flush(); 
        out.close();
        
        InputStreamReader im = new InputStreamReader(httpsConn.getInputStream(),  
                "UTF-8");  
        BufferedReader reader = new BufferedReader(im);  
        StringBuffer sb = new StringBuffer();  
        String line = null;  
        while ((line = reader.readLine()) != null) {  
            sb.append(line + "\r\n");  
        }  
        return sb.toString();
	}
}
