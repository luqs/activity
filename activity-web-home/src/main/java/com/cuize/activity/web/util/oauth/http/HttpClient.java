/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 

 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cuize.activity.web.util.oauth.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.cuize.activity.web.util.oauth.Configuration;
import com.cuize.activity.web.util.oauth.WeixinException;


/**
 * 请求微信平台及响应的客户端类
 *
 * <p>
 * 每一次请求即对应一个<tt>HttpClient</tt>，
 * 每次登陆产生一个<tt>OAuth</tt>用户连接,使用<tt>OAuthToken</tt>
 * 可以不用重复向微信平台发送登陆请求，在没有过期时间内，可继续请求。</p>
 *

 */
public class HttpClient implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3560181985777641426L;

	private Logger logger  =  LoggerFactory.getLogger(HttpClient.class);

    private static final int OK = 200;  // OK: Success!
    private static final int ConnectionTimeout = Configuration.getConnectionTimeout();
    private static final int ReadTimeout = Configuration.getReadTimeout();
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String _GET = "GET";
    private static final String _POST = "POST";

    public HttpClient() {
    }

    /**
     * Post JSON数据
     *
     * @param url 提交地址
     * @param json JSON数据
     * @return 输出流对象
     * @throws WeixinException
     */
    public Response post(String url, JSONObject json) throws WeixinException {
        //将JSON数据转换为String字符串
        String jsonString = json == null ? null : json.toString();
        if (Configuration.isDebug()) {
            logger.info("URL POST 数据：" + jsonString);
        }
        //提交数据
        return httpRequest(url, _POST, jsonString);
    }

    /**
     * Get 请求
     *
     * @param url 请求地址
     * @return 输出流对象
     * @throws WeixinException
     */
    public Response get(String url) throws WeixinException {
        return httpRequest(url, _GET, null);
    }

    /**
     * Post XML格式数据
     *
     * @param url 提交地址
     * @param xml XML数据
     * @return 输出流对象
     * @throws WeixinException
     */
    public Response postXml(String url, String xml) throws WeixinException {
        return httpRequest(url, _POST, xml);
    }

    /**
     * 通过http协议请求url
     *
     * @param url 提交地址
     * @param method 提交方式
     * @param postData 提交数据
     * @return 响应流
     * @throws WeixinException
     */
    private Response httpRequest(String url, String method, String postData)
            throws WeixinException {
        Response res = null;
        OutputStream output = null;
        HttpsURLConnection https = null;
        try {
            //创建https请求连接
            https = getHttpsURLConnection(url);
            //判断https是否为空，如果为空返回null响应
            if (https != null) {
                //设置Header信息，包括https证书
                setHttpsHeader(https, method);
                //判断是否需要提交数据
                if (method.equals(_POST) && null != postData) {
                    //讲参数转换为字节提交
                    byte[] bytes = postData.getBytes(DEFAULT_CHARSET);
                    //设置头信息
                    https.setRequestProperty("Content-Length", Integer.toString(bytes.length));
                    //开始连接
                    https.connect();
                    //获取返回信息
                    output = https.getOutputStream();
                    output.write(bytes);
                    output.flush();
                    output.close();
                } else {
                    //开始连接
                    https.connect();
                }
                //创建输出对象
                res = new Response(https);
                //获取响应代码
                if (res.getStatus() == OK) {
                    return res;
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new WeixinException(ex.getMessage(), ex);
        } catch (KeyManagementException ex) {
            throw new WeixinException(ex.getMessage(), ex);
        } catch (NoSuchProviderException ex) {
            throw new WeixinException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new WeixinException(ex.getMessage(), ex);
        }
        return res;
    }

    /**
     * 获取https请求连接
     *
     * @param url 连接地址
     * @return https连接对象
     * @throws IOException
     */
    private HttpsURLConnection getHttpsURLConnection(String url) throws IOException {
        URL urlGet = new URL(url);
        //创建https请求
        HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) urlGet.openConnection();
        return httpsUrlConnection;
    }

    /**
     * 获取http请求连接
     *
     * @param url 连接地址
     * @return https连接对象
     * @throws IOException
     */
    private HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL urlGet = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlGet.openConnection();
        return con;
    }

    private void setHttpsHeader(HttpsURLConnection httpsUrlConnection, String method)
            throws NoSuchAlgorithmException, KeyManagementException, NoSuchProviderException,
            IOException {
        //创建https请求证书
        TrustManager[] tm = {new MyX509TrustManager()};
        //创建证书上下文对象
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        //初始化证书信息
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象  
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        //设置header信息
        httpsUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //设置User-Agent信息
        httpsUrlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        //设置可接受信息
        httpsUrlConnection.setDoOutput(true);
        //设置可输入信息
        httpsUrlConnection.setDoInput(true);
        //设置请求方式
        httpsUrlConnection.setRequestMethod(method);
        //设置连接超时时间
        if (ConnectionTimeout > 0) {
            httpsUrlConnection.setConnectTimeout(ConnectionTimeout);
        } else {
            //默认10秒超时
            httpsUrlConnection.setConnectTimeout(10000);
        }
        //设置请求超时
        if (ReadTimeout > 0) {
            httpsUrlConnection.setReadTimeout(ReadTimeout);
        } else {
            //默认10秒超时
            httpsUrlConnection.setReadTimeout(10000);
        }
        //设置编码
        httpsUrlConnection.setRequestProperty("Charsert", "UTF-8");
        //设置ssl证书
        httpsUrlConnection.setSSLSocketFactory(ssf);
    }

    private void setHttpHeader(HttpURLConnection httpUrlConnection, String method)
            throws IOException {
        //设置header信息
        httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //设置User-Agent信息
        httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        //设置可接受信息
        httpUrlConnection.setDoOutput(true);
        //设置可输入信息
        httpUrlConnection.setDoInput(true);
        //设置请求方式
        httpUrlConnection.setRequestMethod(method);
        //设置连接超时时间
        if (ConnectionTimeout > 0) {
            httpUrlConnection.setConnectTimeout(ConnectionTimeout);
        } else {
            //默认10秒超时
            httpUrlConnection.setConnectTimeout(10000);
        }
        //设置请求超时
        if (ReadTimeout > 0) {
            httpUrlConnection.setReadTimeout(ReadTimeout);
        } else {
            //默认10秒超时
            httpUrlConnection.setReadTimeout(10000);
        }
        //设置编码
        httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
    }

    /**
     * 上传文件
     *
     * @param url 上传地址
     * @param file 上传文件对象
     * @return 服务器上传响应结果
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public String upload(String url, File file) throws IOException,
            NoSuchAlgorithmException, NoSuchProviderException,
            KeyManagementException {
        HttpURLConnection http = null;
        StringBuffer bufferRes = new StringBuffer();
        try {
            // 定义数据分隔线 
            String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL";
            //创建https请求连接
            http = getHttpURLConnection(url);
            //设置header和ssl证书
            setHttpHeader(http, _POST);
            //不缓存
            http.setUseCaches(false);
            //保持连接
            http.setRequestProperty("connection", "Keep-Alive");
            //设置文档类型
            http.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            //定义输出流
            OutputStream out = null;
            //定义输入流
            DataInputStream dataIns = null;
            try {
                out = new DataOutputStream(http.getOutputStream());
                byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线  
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;name=\"media\";filename=\"").append(file.getName()).append("\"\r\n");
                sb.append("Content-Type:application/octet-stream\r\n\r\n");
                byte[] data = sb.toString().getBytes();
                out.write(data);
                //读取文件流
                dataIns = new DataInputStream(new FileInputStream(file));
                int bytes;
                byte[] bufferOut = new byte[1024];
                while ((bytes = dataIns.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个  
                dataIns.close();
                out.write(end_data);
                out.flush();
            } finally {
                if (out != null) {
                    out.close();
                }
            }

            // 定义BufferedReader输入流来读取URL的响应  
            InputStream ins = null;
            try {
                ins = http.getInputStream();
                BufferedReader read = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                String valueString;
                bufferRes = new StringBuffer();
                while ((valueString = read.readLine()) != null) {
                    bufferRes.append(valueString);
                }
            } finally {
                if (ins != null) {
                    ins.close();
                }
            }
        } finally {
            if (http != null) {
                // 关闭连接
                http.disconnect();
            }
        }
        return bufferRes.toString();
    }

    /**
     * 上传文件
     *
     * @param url 上传地址
     * @param file 上传文件对象
     * @return 服务器上传响应结果
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public String uploadHttps(String url, File file) throws IOException,
            NoSuchAlgorithmException, NoSuchProviderException,
            KeyManagementException {
        HttpsURLConnection https = null;
        StringBuffer bufferRes = new StringBuffer();
        try {
            // 定义数据分隔线 
            String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL";
            //创建https请求连接
            https = getHttpsURLConnection(url);
            //设置header和ssl证书
            setHttpsHeader(https, _POST);
            //不缓存
            https.setUseCaches(false);
            //保持连接
            https.setRequestProperty("connection", "Keep-Alive");
            //设置文档类型
            https.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            //定义输出流
            OutputStream out = null;
            //定义输入流
            DataInputStream dataIns = null;
            try {
                out = new DataOutputStream(https.getOutputStream());
                byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线  
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;name=\"media\";filename=\"").append(file.getName()).append("\"\r\n");
                sb.append("Content-Type:application/octet-stream\r\n\r\n");
                byte[] data = sb.toString().getBytes();
                out.write(data);
                //读取文件流
                dataIns = new DataInputStream(new FileInputStream(file));
                int bytes;
                byte[] bufferOut = new byte[1024];
                while ((bytes = dataIns.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个  
                dataIns.close();
                out.write(end_data);
                out.flush();
            } finally {
                if (out != null) {
                    out.close();
                }
            }

            // 定义BufferedReader输入流来读取URL的响应  
            InputStream ins = null;
            try {
                ins = https.getInputStream();
                BufferedReader read = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                String valueString;
                bufferRes = new StringBuffer();
                while ((valueString = read.readLine()) != null) {
                    bufferRes.append(valueString);
                }
            } finally {
                if (ins != null) {
                    ins.close();
                }
            }
        } finally {
            if (https != null) {
                // 关闭连接
                https.disconnect();
            }
        }
        return bufferRes.toString();
    }

    /**
     * 下载附件
     *
     * @param url 附件地址
     * @return 附件对象
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public Attachment download(String url) throws IOException,
            NoSuchAlgorithmException, KeyManagementException,
            NoSuchProviderException {
        //定义下载附件对象
        Attachment attachment = null;
        HttpsURLConnection https = null;
        try {
            //创建https请求连接
            https = getHttpsURLConnection(url);
            //创建https请求连接
            https = getHttpsURLConnection(url);
            //设置header和ssl证书
            setHttpsHeader(https, _POST);
            //不缓存
            https.setUseCaches(false);
            //保持连接
            https.setRequestProperty("connection", "Keep-Alive");

            //初始化返回附件对象
            attachment = new Attachment();
            //根据下载响应内容创建输出流
            if (https.getContentType().equalsIgnoreCase("text/plain")) {
                // 定义BufferedReader输入流来读取URL的响应  
                InputStream in = https.getInputStream();
                BufferedReader read = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String valueString = null;
                StringBuilder bufferRes = new StringBuilder();
                while ((valueString = read.readLine()) != null) {
                    bufferRes.append(valueString);
                }
                in.close();
                attachment.setError(bufferRes.toString());
            } else {
                BufferedInputStream bis = new BufferedInputStream(https.getInputStream());
                String ds = https.getHeaderField("Content-disposition");
                String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
                String relName = fullName.substring(0, fullName.lastIndexOf("."));
                String suffix = fullName.substring(relName.length() + 1);

                attachment.setFullName(fullName);
                attachment.setFileName(relName);
                attachment.setSuffix(suffix);
                attachment.setContentLength(https.getHeaderField("Content-Length"));
                attachment.setContentType(https.getHeaderField("Content-Type"));

                attachment.setFileStream(bis);
            }
        } finally {
        }
        return attachment;
    }
}
