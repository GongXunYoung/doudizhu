package com.itjob.doudizhu.http;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itjob.doudizhu.app.MainApplication;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HttpVolley implements HttpPolicy {
    private Gson mGson;
    //volley请求队列
    private static RequestQueue mRequestQueue;
    //连接超时时间
    private static final int REQUEST_TIMEOUT_TIME = 6 * 1000;
    private HashMap<String,String> headers = new HashMap<>();


    private HttpVolley() {
        mGson = new Gson();
        //这里使用Application创建全局的请求队列
        mRequestQueue = Volley.newRequestQueue(MainApplication.getInstance());
    }

    private static class VolleyHolder {
        private static HttpVolley httpVolley = new HttpVolley();
    }

    public static HttpVolley getInstance() {
        return VolleyHolder.httpVolley;
    }



    @Override
    public <T> void get(String url, Map<String, String> param, HttpCallBack<T> httpCallBack) {

    }


    @Override
    public <T> void post(String url, final Map<String, String> param,final HttpCallBack<T> httpCallBack) {
        mRequestQueue.cancelAll(url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,  url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (httpCallBack == null) {
                            return;
                        }

                        Type type = getTType(httpCallBack.getClass());

                        HttpResult httpResult = mGson.fromJson(response, HttpResult.class);
                        if (httpResult != null) {
                            if (httpResult.getStatus() != 200) {//失败
                                httpCallBack.onFail(httpResult.getMessage());
                            } else {//成功
                                //获取data对应的json字符串
                                String json = mGson.toJson(httpResult.getData());
                                if (type == String.class) {//泛型是String，返回结果json字符串
                                    httpCallBack.onSuccess((T) json);
                                } else {//泛型是实体或者List<>
                                    T t = mGson.fromJson(json, type);
                                    httpCallBack.onSuccess(t);
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpCallBack == null) {
                    return;
                }
                String msg = error.getMessage();
                httpCallBack.onFail(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //请求参数
                if (param == null) return super.getParams();
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (param == null) return super.getHeaders();
                return headers;
            }
        };
        //设置请求超时和重试
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        //加入到请求队列
        if (mRequestQueue != null)
            mRequestQueue.add(stringRequest.setTag(url));
    }





    @Override
    public void cancel(Object obj) {
        mRequestQueue.cancelAll(obj);
    }

    @Override
    public void cancelAll() {

    }
    @Override
    public  <T> void uploadFile(String url,Map<String, Object> param,FileEntity fileEntity,
                               final HttpCallBack<T> httpCallBack) {

        File directory = Environment.getExternalStorageDirectory();
// 参数
//        Map<String, Object> params = new HashMap<>();
//        params.put("name", "volley_single_file_name");
//        params.put("value", "volley_single_file_value");

//        FileEntity fileEntity = new FileEntity();
//        fileEntity.mName = "filename";
//        fileEntity.mFileName = "other.png";
//        fileEntity.mFile = new File(directory, "Movies/other.png");

        MultipartRequest multipartRequest = new MultipartRequest(url,param, fileEntity, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Succeed Result", response);
                if (httpCallBack == null) {
                    return;
                }

                Type type = getTType(httpCallBack.getClass());

                HttpResult httpResult = mGson.fromJson(response, HttpResult.class);
                if (httpResult != null) {
                    if (httpResult.getStatus() != 200) {//失败
                        httpCallBack.onFail(httpResult.getMessage());
                    } else {//成功
                        //获取data对应的json字符串
                        String json = mGson.toJson(httpResult.getData());
                        if (type == String.class) {//泛型是String，返回结果json字符串
                            httpCallBack.onSuccess((T) json);
                        } else {//泛型是实体或者List<>
                            T t = mGson.fromJson(json, type);
                            httpCallBack.onSuccess(t);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpCallBack == null) {
                    return;
                }
                String msg = error.getMessage();
                httpCallBack.onFail(msg);
            }
        });

       mRequestQueue.add(multipartRequest.setTag(url));
    }

    private Type getTType(Class<?> clazz) {
        Type mySuperClassType = clazz.getGenericSuperclass();
        Type[] types = ((ParameterizedType) mySuperClassType).getActualTypeArguments();
        if (types != null && types.length > 0) {
            //T
            return types[0];
        }
        return null;
    }
}
