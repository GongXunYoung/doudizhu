package com.itjob.doudizhu.http;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public abstract class HttpCallBack<T> {


    public abstract void onSuccess(T data);

    public abstract void onFail(String msg);
}
