package com.jsycn.pj_project.core.http;
/*
 * Created by zhangxiaowei on 2018/9/11 0011.
 */

import android.annotation.SuppressLint;


import com.blankj.utilcode.util.LogUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

public class LogInterceptor implements Interceptor {
    static final String TAG = "LogInterceptor";

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Chain chain) throws IOException {
        /*if (!HttpCommonParamsProxy.getInstance().isDebug()) {
            return chain.proceed(chain.request());
        }*/
        //===============
        Request request = chain.request();
        String requestMethod = request.method();
        HttpUrl httpUrl = request.url();
        RequestBody requestBody = request.body();
        Response response = null;
        StringBuilder requestBuilder = null;
        StringBuilder responseBuilder = null;
        try {
            //String startTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS").format(new Date());
            requestBuilder = new StringBuilder();
            requestBuilder.append("发送请求 : ");
            /*Headers headers = request.headers();
            if (headers != null) {
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        requestBuilder.append(name);
                        requestBuilder.append(" : ");
                        requestBuilder.append(headers.value(i));
                        requestBuilder.append("\n");
                    }
                }
            }*/
            requestBuilder.append("\nurl : ");
            requestBuilder.append(request.url().toString());
            requestBuilder.append("\nmethod : ");
            requestBuilder.append(requestMethod);
            requestBuilder.append("\nparams : \n {\n");
            if ("GET".equals(requestMethod)) {
                Set<String> paramNames = httpUrl.queryParameterNames();
                if (paramNames != null && !paramNames.isEmpty()) {
                    for (String key : paramNames) { //追加已有参数
                        requestBuilder.append(key);
                        requestBuilder.append(" : ");
                        requestBuilder.append(httpUrl.queryParameter(key));
                        requestBuilder.append("\n");
                    }
                }
            } else if ("POST".equals(requestMethod)) {
                if (requestBody != null) {
                    Charset charset = StandardCharsets.UTF_8;
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(StandardCharsets.UTF_8);
                    }
                    /*requestBuilder.append("\ncontent-Type: ");
                    requestBuilder.append(requestBody.contentType());
                    requestBuilder.append("\n");*/
                    if (requestBody instanceof FormBody) {
                        for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                            requestBuilder.append(((FormBody) requestBody).name(i));
                            requestBuilder.append(" : ");
                            requestBuilder.append(((FormBody) requestBody).value(i));
//                            requestBuilder.append(URLDecoder.decode(((FormBody) requestBody).value(i),"utf-8"));
                            requestBuilder.append("\n");
                        }
                    } else if (requestBody instanceof MultipartBody) {
                        /*MultipartBody body = (MultipartBody) requestBody;
                     *//*   Buffer buffer1 = new Buffer();
                        body.writeTo(buffer1);
                        String postParams = buffer1.readUtf8();
                         String[] split = postParams.split("\n");
                        List<String> names = new ArrayList<>();
                        for (String s : split) {
                            if (s.contains("Content-Disposition")) {
                                names.add(s.replace("Content-Disposition: form-data; name=", "").replace("\"", ""));
                            }
                        }*//*

                        List<MultipartBody.Part> parts = body.parts();
                        if (parts != null && parts.size() > 0) {
                            for (int i = 0; i < parts.size(); i++) {
                                MultipartBody.Part part = parts.get(i);
                                RequestBody body1 = part.body();
                                if (body1.contentLength() < 100) {
                                    Buffer buffer = new Buffer();
                                    body1.writeTo(buffer);
                                    String value = buffer.readUtf8();
                                    //打印 name和value
                                    if (names.size() > i) {
                                        Log.e(tag, "params-->" + names.get(i) + " = " + value);
                                    }
                                } else {
                                    if (names.size() > i) {
                                        Log.e(tag, "params-->" + names.get(i));
                                    }
                                }
                            }
                        }*/

                    } else {
                        Buffer buffer = new Buffer();
                        String json = "null";
                        try {
                            requestBody.writeTo(buffer);
                            json = buffer.readString(charset);

                            JSONObject jsonObject = new JSONObject(json);
                            Iterator<String> iterator = jsonObject.keys();
                            while (iterator.hasNext()) {
                                String key = iterator.next();
                                String params = jsonObject.optString(key);
                                requestBuilder.append(key);
                                requestBuilder.append(" : ");
                                requestBuilder.append(params);
                                requestBuilder.append("\n");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            requestBuilder.append("非json格式");
                            requestBuilder.append(" : ");
                            requestBuilder.append(json);
                            requestBuilder.append("\n");
                        } finally {
                            buffer.close();
                        }
                    }
                }
            }
            requestBuilder.append("\n}");

            //============response ==================
            long startNs = System.nanoTime();
            response = chain.proceed(request);
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            responseBuilder = new StringBuilder("请求结果 : ");
            responseBuilder.append("\n");
            responseBuilder.append("请求耗时 : ");
            responseBuilder.append(tookMs);
            responseBuilder.append(" ms \n");
            if (response != null) {
                responseBuilder.append("response method : ");
                responseBuilder.append(response.request().method());
                responseBuilder.append("\n");
                responseBuilder.append("response url : ");
                responseBuilder.append(response.request().url());
                responseBuilder.append("\n");
                responseBuilder.append("response code : ");
                responseBuilder.append(response.code());
                responseBuilder.append("\n");
                responseBuilder.append("response message : ");
                responseBuilder.append(response.message());
                responseBuilder.append("\n");

                /*Headers resHeaders = response.headers();
                for (int i = 0, count = resHeaders.size(); i < count; i++) {
                    responseBuilder.append(resHeaders.name(i));
                    responseBuilder.append(" : ");
                    responseBuilder.append(resHeaders.value(i));
                    responseBuilder.append("\n");
                }*/

                if (HttpHeaders.hasBody(response)) {
                    try {
                        ResponseBody responseBody = response.body();
                        BufferedSource source = responseBody.source();
                        source.request(Long.MAX_VALUE); // Buffer the entire body.
                        Buffer buffer = source.buffer();
                        Charset resCharset = StandardCharsets.UTF_8;
                        MediaType resContentType = responseBody.contentType();
                        if (resContentType != null) {
                            resCharset = resContentType.charset(StandardCharsets.UTF_8);
                        }
                        String bodyStr = buffer.clone().readString(resCharset);
                        responseBuilder.append("response result : ");
                        responseBuilder.append("\n");
                        responseBuilder.append(formatJson(bodyStr));
                        responseBuilder.append("\n");
                    } catch (Exception e) {
                        LogUtils.e(TAG, "response exception: " + e.getMessage());
                    }
                }
            }
            //String endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS").format(new Date());

        } catch (Exception e) {
            LogUtils.e(TAG, "request exception: " + e.getMessage());
            return chain.proceed(chain.request());
        } finally {
            if (requestBuilder != null) {
                LogUtils.d(TAG, requestBuilder.toString());
            }
            if (responseBuilder != null) {
                LogUtils.d(TAG, responseBuilder.toString());
            }
        }
        return response;
        //=================

    }

    /**
     * 格式化json
     *
     * @param content
     * @return
     */
    public static String formatJson(String content) {

        StringBuffer sb = new StringBuffer();
        int index = 0;
        int count = 0;
        while (index < content.length()) {
            if (sb.length()>65536){
                break;
            }
            char ch = content.charAt(index);
            if (ch == '{' || ch == '[') {
                sb.append(ch);
                sb.append('\n');
                count++;
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
            } else if (ch == '}' || ch == ']') {
                sb.append('\n');
                count--;
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
                sb.append(ch);
            } else if (ch == ',') {
                sb.append(ch);
                sb.append('\n');
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
            } else {
                sb.append(ch);
            }
            index++;
        }
        return sb.toString();
    }


 /*   result = result.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
    result = result.replaceAll("\\+", "%2B");
    result = URLDecoder.decode(result, "utf-8");*/
}
