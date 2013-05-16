package com.ht.scada.data.service.impl;

import com.ht.scada.data.service.RTUService;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RTUServiceImpl implements RTUService {
    public static final Logger log = LoggerFactory.getLogger(RTUServiceImpl.class);

    private DefaultHttpClient httpclient;

//    private RealtimeDataService realtimeDataService;

    public RTUServiceImpl() {
        System.out.println("----------=========");
    }

    @PostConstruct
    private void init() {
        httpclient = new DefaultHttpClient();
    }
    @PreDestroy
    private void destroy() {
        httpclient.getConnectionManager().shutdown();
    }

	@Override
	public boolean yk(String code, String varName, boolean value) throws Exception {
        log.info("执行遥控操作:{}-{}", code, varName);
        String url = null;//realtimeDataService.getValue(UrlService.COMM_URL_KEY);
        if (url == null) {
            log.warn("未找到通讯服务器");
            return false;
        }
        HttpPost httpPost = new HttpPost(url + "/yk");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("channelIndex", code));
        nvps.add(new BasicNameValuePair("varName", varName));
        nvps.add(new BasicNameValuePair("value", Boolean.toString(value)));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
        } finally {
            httpPost.releaseConnection();
        }
		return false;
	}

	@Override
	public boolean yt(String code, String varName, int value) throws Exception {
        log.info("执行遥调操作:{}-{}", code, varName);

        String url = null;//realtimeDataService.getValue(UrlService.COMM_URL_KEY);
        if (url == null) {
            log.warn("未找到通讯服务器");
            return false;
        }

        HttpPost httpPost = new HttpPost(url + "/yt");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("channelIndex", code));
        nvps.add(new BasicNameValuePair("varName", varName));
        nvps.add(new BasicNameValuePair("value", Integer.toString(value)));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
        } finally {
            httpPost.releaseConnection();
        }
		return false;
	}

    @Override
    public boolean yt(String code, Map<String, Float> value) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
