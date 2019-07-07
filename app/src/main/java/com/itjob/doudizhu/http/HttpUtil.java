package com.itjob.doudizhu.http;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itjob.doudizhu.app.MainApplication;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    private static HttpUtil mHttpUtil;
    private  static HttpVolley mPolicy;

    public static void setMpolicy(HttpPolicy mPolicy) {
       mPolicy = mPolicy;
    }

    private HttpUtil() {

    }

    public static HttpUtil getInstance() {
        if (mHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new HttpUtil();
                }
            }
        }
        return mHttpUtil;
    }

    /**
     * http请求
     *
     * @param url          http地址（后缀）
     * @param param        参数
     * @param httpCallBack 回调
     */
    public <T> void post(String url, final Map<String, String> param, final HttpCallBack<T>
            httpCallBack) {
        HttpVolley.getInstance().post(HttpPolicy.POST,url,param,httpCallBack);
    }

    /**
     * http请求
     *
     * @param url          http地址（后缀）
     * @param param        参数
     * @param httpCallBack 回调
     */
    public <T> void get(String url, final Map<String, String> param, final HttpCallBack<T>
            httpCallBack) {
        StringBuilder sb=new StringBuilder();
        sb.append(url).append("?");
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        HttpVolley.getInstance().get(HttpPolicy.GET,sb.deleteCharAt(sb.length() -1).toString(),null,
                httpCallBack);
    }


}