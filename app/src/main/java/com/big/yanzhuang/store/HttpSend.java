package com.big.yanzhuang.store;

/**
 * http访问服务器基础类
 */

import android.text.TextUtils;

import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpSend {
    private static String res="";
    //https
    public static String doPost(String path, Map<String,String> params) throws NoSuchProviderException, NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLContext sslcontext = SSLContext.getInstance("SSL");//第一个参数为协议,第二个参数为提供者(可以缺省)
        TrustManager[] tm = {
                new MyX509TrustManager()
        };
        sslcontext.init(null, tm, new SecureRandom());
        HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslsession) {
                System.out.println("WARNING: Hostname is not matched for cert.");
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--";
        String LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";


        conn.setReadTimeout(30 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
        StringBuilder sb = new StringBuilder();
        if (params!=null) {
            // 首先组拼文本类型的参数
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\""
                        + entry.getKey() + "\"" + LINEND);
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }

        }

        DataOutputStream outStream = new DataOutputStream(
                conn.getOutputStream());
        if (!TextUtils.isEmpty(sb.toString())) {
            outStream.write(sb.toString().getBytes());
        }
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();

        // 得到响应码
        int res = conn.getResponseCode();
        InputStream in = conn.getInputStream();
        if (res == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                buffer.append(line);
            }

//          int ch;
//          StringBuilder sb2 = new StringBuilder();
//          while ((ch = in.read()) != -1) {
//              sb2.append((char) ch);
//          }
            return buffer.toString();
        }
        outStream.close();
        conn.disconnect();
        return in.toString();
    }
    //json格式访问
    public static String jsonPost(String url, JsonObject jsonObject, String encoding){
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String response = null;
        try {
            StringEntity s = new StringEntity(jsonObject.toString());
            s.setContentEncoding(encoding);
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    private static class MyX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    //
    public static String doPost2(String url){
        String result = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        HttpEntity entity;

        List<BasicNameValuePair> params =
                new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("usernum", "662995"));
        params.add(new BasicNameValuePair("userpwd", "662662"));
        try {
            entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(entity);//设置参数实体
            HttpResponse res = client.execute(post);//获取HttpResponse实例
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(res.getEntity(),"UTF-8");
            } else {
                //响应未通过
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
