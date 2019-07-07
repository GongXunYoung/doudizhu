package com.itjob.doudizhu.http;

import java.util.Map;

public interface  HttpPolicy {

    <T> void post(String url, Map<String, String> param,
                  HttpCallBack<T> httpCallBack);
    <T> void get(String url, Map<String, String> param,
                 HttpCallBack<T> httpCallBack);
    void cancel(Object obj);
    void cancelAll();
    <T> void uploadFile(String url,Map<String, Object> param,FileEntity fileEntity,
                        HttpCallBack<T> httpCallBack);

}
