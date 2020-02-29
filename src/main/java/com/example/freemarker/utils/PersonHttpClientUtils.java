package com.example.freemarker.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PersonHttpClientUtils {

    public String getGetResult(String url,Map<String,String> map){
        String result = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = createAcceptSelfSignedCertificateClient();
//            httpClient = HttpClientPoolUtils.getHttpClientPool().borrowObject();
            URIBuilder builder = new URIBuilder(url);
            if (map != null){
                List<NameValuePair> pairs = new ArrayList<>();
                for(Map.Entry<String,String> entry : map.entrySet()){
                    pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }
                builder.setParameters(pairs);
            }
            HttpGet get = new HttpGet(builder.build());
            //代理启动
//            HttpHost proxy = new HttpHost("47.101.134.131",8080);
//            RequestConfig requestConfig=RequestConfig.custom().setProxy(proxy).build();
//            get.setConfig(requestConfig);
            response = httpClient.execute(get);
            if(response != null && response.getStatusLine().getStatusCode() == 200) {
                 HttpEntity entity = response.getEntity();
                 result = entityToString(entity);
             }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
//                HttpClientPoolUtils.getHttpClientPool().returnObject(httpClient);
                if (response != null){
                    response.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getPostResult(String url,Map<String,String> map){
        CloseableHttpResponse response = null;
        String result = null;
        CloseableHttpClient httpClient = null;
        try{
            httpClient = createAcceptSelfSignedCertificateClient();
//            httpClient = HttpClientPoolUtils.getHttpClientPool().borrowObject();
            HttpPost post = new HttpPost(url);
            //代理启动
//            HttpHost proxy = new HttpHost("47.101.134.131",8080);
//            RequestConfig requestConfig=RequestConfig.custom().setProxy(proxy).build();
//            get.setConfig(requestConfig);
            if (map != null){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                for(Map.Entry<String,String> entry : map.entrySet()){
                    pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }
                post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
            }
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
                 {
                     HttpEntity entity = response.getEntity();
                     result = entityToString(entity);
                 }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
//                HttpClientPoolUtils.getHttpClientPool().returnObject(httpClient);
                if (response != null){
                    response.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 忽略https请求错误
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    public static CloseableHttpClient createAcceptSelfSignedCertificateClient()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
        return HttpClients.custom().setSSLSocketFactory(connectionFactory).build();
    }

    /**
     * 把返回结果转为字符串
     * @param entity
     * @return
     * @throws IOException
     */
    private String entityToString(HttpEntity entity) throws IOException {
         String result = null;
         if(entity != null) {
             long lenth = entity.getContentLength();
             if(lenth != -1 && lenth < 2048) {
                     result = EntityUtils.toString(entity,"UTF-8");
             }else {
                 InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                 CharArrayBuffer buffer = new CharArrayBuffer(2048);
                 char[] tmp = new char[1024];
                 int l;
                 while((l = reader1.read(tmp)) != -1) {
                         buffer.append(tmp, 0, l);
                     }
                 result = buffer.toString();
             }
         }
         return result;
     }

}
