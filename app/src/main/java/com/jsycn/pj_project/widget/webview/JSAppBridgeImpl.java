package com.jsycn.pj_project.widget.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;


import com.blankj.utilcode.util.LogUtils;
import com.jsycn.pj_project.BuildConfig;
import com.jsycn.pj_project.entity.WebVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.TreeMap;


//import com.jindashi.yingstock.common.utils.DBUtils;


//import com.jindashi.yingstock.business.mine.LoginActivity;


public final class JSAppBridgeImpl implements JSAppBridge, Serializable {
    private static final String TAG = "JSAppBridgeImpl";
    private String mUserAgent;
    /**
     * 是否拦截返回键
     */
    boolean interceptOnBack;
    private transient Activity mContext;

    private transient WebView mWebView;

    private String mParams;

    private Activity activity;
    private static final String NATIVE_CACHE_PREFIX = "native_cache_prefix"; //用于h5调用源生的存储方法key中

    public   JSAppBridgeImpl setWebView(WebView webView) {
        mWebView = webView;
        return this;
    }

    public JSAppBridgeImpl() {
        String version = BuildConfig.VERSION_NAME;
        //String device = DeviceUtils.getDeviceIMEI(AppContext.getInstance().getContext());
        String appType = "1";
        //mUserAgent = String.format(" android superstock(%s,%s,%s) ", version, device, appType);
        mUserAgent = String.format(" android superstock(%s,%s) ", version, appType);
    }

    /**
     * js 判断应用是否有开启通知
     *
     * @param jsFun
     */
    @JavascriptInterface
    public void checkJurisdiction(String jsFun) {
//        invokeJs(jsFun, String.valueOf(FSystemSettingHelper.isHasOpenNotification()));
    }

    /**
     * H5跳转到源生的通知设置页面
     */
    @JavascriptInterface
    public void goPushSetting() {
//        if (PreventFastClickHelper.isFastClick()) {
//            return;
//        }
//        FSystemSettingHelper.openNotificationSetting(mContext);
    }

    /**
     * 获取APP 的一些基础信息
     * getAppInfo
     * {
     * channel:''//渠道
     * os:'' 0安卓 1ios
     * version:''版本号
     * }
     *
     * @param jsFun
     */
    @JavascriptInterface
    public void getAppInfo(String jsFun) {
        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("channel", HttpCommonParamsProxy.getInstance().getAppChannel());
//            jsonObject.put("os", "0");
//            jsonObject.put("version", HttpCommonParamsProxy.getInstance().getAppVersion());
//            jsonObject.put("imei", HttpCommonParamsProxy.getInstance().getAppImei());
//            jsonObject.put("androidid", HttpCommonParamsProxy.getInstance().getAppAndroidId());
//            jsonObject.put("oaid", HttpCommonParamsProxy.getInstance().getAppOAId());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        invokeJs(jsFun, jsonObject);
    }

    /**
     * 2.17.0版本后 统一使用这个方法进行页面的跳转
     * 同推送逻辑保持一致
     */
    @JavascriptInterface
    public void commonJSCallBack(String params) {
        if (TextUtils.isEmpty(params)) {
            return;
        }
        try {
            JSONObject object = new JSONObject(params);
            if (object == null) {
                return;
            }
            String gotoPage = object.optString("goto_page");
            if (TextUtils.isEmpty(gotoPage)) {
                return;
            }
            /*JumpHelper.msgCenterToJump(mContext, gotoPage, params);*/
//            mWebView.post(() -> JumpHelper.msgCenterToJump(mContext, gotoPage, params));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //为h5提供的存储方法
    @JavascriptInterface
    public void storeNativeData(String params) {
        if (TextUtils.isEmpty(params)) {
            return;
        }
        try {
            JSONObject object = new JSONObject(params);
            if (object != null && object.has("key") && object.has("value")) {
                String key = object.getString("key");
                String value = object.getString("value");
                if (!TextUtils.isEmpty(key)) {
//                    PreferenceManager.putString(NATIVE_CACHE_PREFIX + key, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //h5获取存储的值
    @JavascriptInterface
    public void getNativeData(String params) {
//        if (TextUtils.isEmpty(params)) {
//            invokeJs(JsMethodName.getNativeDataCallback, "");
//            return;
//        }
//
//        try {
//            if (mContext instanceof OnWebSettingCallBack) {
//                ((OnWebSettingCallBack) mContext).showFontSettingBtn(true);
//            }
//            JSONObject object = new JSONObject(params);
//            if (object != null && object.has("key")) {
//                String key = object.getString("key");
//                if (!TextUtils.isEmpty(key)) {
//                    invokeJs(JsMethodName.getNativeDataCallback, PreferenceManager.getString(NATIVE_CACHE_PREFIX + key, ""));
//                    return;
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        invokeJs(JsMethodName.getNativeDataCallback, "");
    }

    @JavascriptInterface
    public void openVideoList(String params) {
//        try {
//            JSONObject object = new JSONObject(params);
//            if (object != null && object.has("master_id") && object.has("category_id")) {
//                int masterId = object.getInt("master_id");
//                int categoryId = object.getInt("category_id");
//                MasterVideoListActivity.launch(mContext, masterId, categoryId, "H5跳转原生页面");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @JavascriptInterface
    public void openUserAccout() { //跳转到个人中心
//        if (UserManager.getInstance().isLogin()) {
//            UserCenterActivity.launch(mContext);
//        } else {
//            JumpUtil.jumpToOneKeyLoginActivity(mContext, (isLogin) -> {
//                if (isLogin) {
//                    UserCenterActivity.launch(mContext);
//                }
//            });
//        }
    }

    @JavascriptInterface
    public void openMasterIndex(String params) { //跳转到大咖页
//        if (TextUtils.isEmpty(params)) {
//            return;
//        }
//        try {
//            JSONObject object = new JSONObject(params);
//            if (object != null && object.has("master_id")) {
//                int masterId = object.getInt("master_id");
//                //MasterDetailActivity.launch(mContext, masterId, "H5跳转原生页面");
//                LaunchHelper.launchToMasterDetailActivity(mContext, masterId, "H5跳转原生页面");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    @JavascriptInterface
    public void openGroupChat(String params) {
//        if (TextUtils.isEmpty(params)) {
//            return;
//        }
//        try {
//            if (UserManager.getInstance().isLogin()) {
//                JsonObject object = new JsonParser().parse(params).getAsJsonObject();
//                if (object != null) {
//                    String group_id = object.get("group_id").getAsString();
//                    //先加群 在跳转
//                    JoinGroupHelper.onJoinGroup(mContext, group_id);
//                }
//            } else {
//                JumpUtil.jumpToLogin(mContext);
//            }
//        } catch (Exception e) {
//
//        }

    }

    @Override
    @JavascriptInterface
    public void openPrivacyProtocal() { //跳转到隐私政策详情页
//        WebVo webVo = new WebVo(H5URL.getHDWebUrl(H5URL.WEB_PRIVACY), new   JSAppBridgeImpl(), true);
//        webVo.setTitle("隐私政策");
//        Intent intent = new Intent(mContext, WebActivity.class);
//        intent.putExtra(WebVo.WEB_VO, webVo);
//        mContext.startActivity(intent);
    }

    @Override
    @JavascriptInterface
    public void openImage(String[] allUrls, String currUrl) {
//        if (allUrls == null || allUrls.length == 0 ||
//                TextUtils.isEmpty(currUrl) || mContext == null) {
//            Log.e("JSAppBridgeImpl", "抓取Web页图片失败");
//        } else {
//            Intent intent = new Intent();
//            intent.putExtra(ALL_IMAGE_URLS, allUrls);
//            intent.putExtra(CURR_IMAGE_URL, currUrl);
//            intent.setClass(mContext, WebPreviewActivity.class);
//            mContext.startActivity(intent);
//            for (int i = 0; i < allUrls.length; i++) {
//                Log.e("图片地址" + i, allUrls[i]);
//            }
//        }
    }

    @Override
    @JavascriptInterface
    public void onBack(String init) {
        boolean bool = false;
        if (!TextUtils.isEmpty(init)) {
            try {
                JSONObject jsonObject = new JSONObject(init);
                bool = jsonObject.optBoolean("isInit");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bool) {
            interceptOnBack = bool;
        } else if (interceptOnBack) {
            mContext.finish();
        }
    }

    @Override
    public boolean canClosePage() {
        return !interceptOnBack;
    }

    @Override
    public void invokeJsOnBack() {
//        invokeJs(JsMethodName.onBack, "");
    }

    @Override
    @JavascriptInterface
    public String init() {
        LogUtils.d(TAG, "js init...");
//        return result(0, "succ", null);
        return "";
    }

    @Override
    @JavascriptInterface
    public String shareImageTextLink(String paramStr) {
        LogUtils.d(TAG, "i want share : " + paramStr);
//        return result(0, "succ", null);
        return "";
    }

    @Override
    @JavascriptInterface
    public void getUkey(String jsFun) {
//        UserManager userManager = UserManager.getInstance();
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("uid", userManager.isLogin() ? userManager.getUserVo().getId() : "");
//            jsonObject.put("ukey", userManager.isLogin() ? userManager.getToken() : "");
//            invokeJs(jsFun, jsonObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    @JavascriptInterface
    public String openWeb(String paramStr) {
        /*JsonObject jo = null;
        String ret = null;
        try {
            jo = new JsonParser().parse(paramStr).getAsJsonObject();
            String newUrl = jo.get("url").getAsString();
            String title = jo.get("title").getAsString();
            openWebView(title, newUrl);
            ret = result(0, "succ", null);
        } catch (Exception e) {
            e.printStackTrace();
            ret = result(-1, e.getMessage(), null);
        }*/

//        String ret = "";
//        try {
//            if (!TextUtils.isEmpty(paramStr)) {
//                JSONObject jsonObject = new JSONObject(paramStr);
//                if (jsonObject != null) {
//                    String newUrl = jsonObject.optString("url");
//                    String title = jsonObject.optString("title");
//                    openWebView(title, newUrl);
//                    ret = result(0, "succ", null);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            ret = result(-1, e.getMessage(), null);
//        }
//        return ret;
        return "";
    }

    @Override
    @JavascriptInterface
    public String openLogin() {
//        // TODO: 2020/1/14
//        /*Intent intent = new Intent(mContext, LoginActivity.class);
//        mContext.startActivityForResult(intent, REQUEST_CODE_TOLOGIN);*/
//        JumpUtil.jumpToOneKeyLoginActivity(mContext, new OnLoginCallBack() {
//            @Override
//            public void onCallBack(boolean isSuccess) {
//                if (isSuccess) {
//                    onLoginRequest();
//                }
//            }
//        });
//        return result(0, "succ", null);
        return "";
    }

    @Override
    @JavascriptInterface
    public String openStockSearch() {
//        Intent intent = new Intent(mContext, CommonActivity.class);
//        intent.putExtra(CommonActivity.PAGE_TYPE, CommonActivity.PAGE_SEARCH_STOCK);
//        mContext.startActivity(intent);
//        return result(0, "succ", null);
        return "";
    }

    @Override
    public void onLoginRequest() {
//        if (mContext instanceof WebActivity) {
//            UserManager userManager = UserManager.getInstance();
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("uid", userManager.getUserVo().getId());
//                jsonObject.put("isLogin", userManager.isLogin());
//                jsonObject.put("ukey", userManager.getToken());
//                jsonObject.put("environment", BuildConfig.DEBUG ? "test" : "xigua");
//                invokeJs(JsMethodName.loginsuccess, jsonObject);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    @JavascriptInterface
    public void share(String params) {
//        if (mContext != null && !TextUtils.isEmpty(params)) {
//            try {
//                JSONObject jsonObject = new JSONObject(params);
//                SocialShareProvider socialShareProvider = new SocialShareProvider(mContext);
//                UMWeb umWeb = new UMWeb(jsonObject.getString("url"));
//                umWeb.setTitle(jsonObject.getString("title"));
//                umWeb.setDescription(jsonObject.getString("content"));
//                umWeb.setThumb(new UMImage(mContext, jsonObject.getString("imgSrc")));
//               /* socialShareProvider.share(SocialShareProvider.MEDIA_TYPE_WEB, umWeb, new SocialShareProvider.CustomShareListener(mContext) {
//                    @Override
//                    public void onStart(SHARE_MEDIA platform) {
//                        super.onStart(platform);
//                    }
//
//                    @Override
//                    public void onSelect(SHARE_MEDIA platform) {
//                        super.onSelect(platform);
//                    }
//                });*/
//                mWebView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        socialShareProvider.share(SocialShareProvider.MEDIA_TYPE_WEB, umWeb, new SocialShareProvider.CustomShareListener(mContext) {
//                            @Override
//                            public void onStart(SHARE_MEDIA platform) {
//                                super.onStart(platform);
//                            }
//
//                            @Override
//                            public void onSelect(SHARE_MEDIA platform) {
//                                super.onSelect(platform);
//                            }
//                        });
//                    }
//                });
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    @JavascriptInterface
    public void shareWXMiniProgram(String params, byte[] bytes) {
//        try {
//            JSONObject jsonObject = new JSONObject(params);
//            String url = jsonObject.getString("url");
//            WXAppHelper.shareWXMiniApp(mContext, url, bytes);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    @JavascriptInterface
    public void openWeChatMiniProgram(String params) {
//        LogUtils.d(this, "小程序跳转传参：" + params);
//        if (TextUtils.isEmpty(params))
//            return;
//        try {
//            JSONObject jsonObject = new JSONObject(params);
//            String value = jsonObject.getString("miniProgramNum");
//            WXAppHelper.x(mContext, value);
////            if (value.contains(":")) {
////                String[] arry = value.split(":");
////                if (arry[1].contains("?")){
////                    arry[1] += ("&uid=" + UserManager.getInstance().getUid());
////                }else {
////                    arry[1] += ("?uid=" + UserManager.getInstance().getUid());
////                }
////                WXAppHelper.openWXMiniApp(mContext, arry[0], arry[1]);
////            } else {
////                String path = "?uid=" + UserManager.getInstance().getUid();
////                WXAppHelper.openWXMiniApp(mContext, params, path);
////            }
//            //启动小程序并发送头条信息
//            /*boolean first_register = PreferenceManager.getBoolean(HomePresenter.FIRST_REGISTER_WX, true);
//            if (first_register) {
//                getToutiaoMessage(mContext, HomePresenter.TOUTIAO_ACTIVE_REGISTER_WX);
//                PreferenceManager.putBoolean(HomePresenter.FIRST_REGISTER_WX, false);
//            }*/
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    @JavascriptInterface
    public void shareData(String params) {
//        LogUtils.d(TAG, "shareData = " + params);
//        this.mParams = params;
//        if (mContext instanceof WebActivity && !TextUtils.isEmpty(mParams)) {
//            ((WebActivity) mContext).showShareVisible();
//        }
    }

    @Override
    @JavascriptInterface
    public String openVideoLive() {
//        LogUtils.d(TAG, "openVideoLive");
////        Intent intent = new Intent(mContext, MainActivity.class);
////        intent.putExtra(MainActivity.PUSH_PAGE, PUSH_LIVE_FRAGMENT);
////        mContext.startActivity(intent);
//        if (UserManager.getInstance().isLogin()) {
//            LiveEvent liveEvent = new LiveEvent(LiveEvent.EVENT_WEB_TO_LIVE);
//            RxBus.getInstance().post(liveEvent);
//        } else {
//            Intent intent = new Intent(mContext, MainActivity.class);
//            intent.putExtra(PushConfig.PUSH_PAGE, PushConfig.PUSH_LIVE_FRAGMENT);
//            mContext.startActivity(intent);
//        }
//        return result(0, "succ", null);
        return "";
    }

    @Override
    @JavascriptInterface
    public void openStockDetail(String params) {
//        if (!TextUtils.isEmpty(params)) {
//            try {
//                FPushStockMsgEntity.StockParams stockParams = GsonUtils.fromJson(params, FPushStockMsgEntity.StockParams.class);
//                if (stockParams != null) {
//                    ContractVo contractVo = stockParams.getSelectedStockContractVoMethod();
//                    if (contractVo != null) {
//                        String market = contractVo.getMarket();
//                        if (market.equals("SH") || market.equals("SZ") /*|| market.equals("SHETF") || market.equals("SZETF")*/) {
//                            market = market.toLowerCase();
//                        } else if (market.equals("sge") || market.equals("gold") || market.equals("forex")) {
//                            market = market.toUpperCase();
//                        }
//                        contractVo.setMarket(market);
//                        LaunchHelper.launchToQuoteDetailPage(mContext, contractVo, stockParams.getSelectedTypeMethod(), stockParams.getSelectedKLineTypeMethod());
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            /*try {
//                JSONObject paramsResult = new JSONObject(params);
//                int selectindex = paramsResult.getInt("selectindex");
//                JSONArray stocklist = paramsResult.getJSONArray("stocklist");
//                for (int i = 0; i < stocklist.length(); i++) {
//                    if (i == selectindex) {
//                        ContractVo contractVo = new ContractVo();
//                        contractVo.setCode(stocklist.getJSONObject(i).getString("code"));
//                        contractVo.setMarket(stocklist.getJSONObject(i).getString("market"));
//                        if (mContext != null) {
//                            JumpUtil.jumpToStockDetail(mContext, contractVo);
//                        }
//                        break;
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }*/
//        }
    }

    @Override
    @JavascriptInterface
    public void refreshSelfStock(String params) {
//        LogUtils.d("refreshSelfStock");
//        if (!TextUtils.isEmpty(params)) {
//            try {
//                JSONObject jsonObject = new JSONObject(params);
//                ContractVo contractVo = new ContractVo();
//                contractVo.setCode(jsonObject.optString("code"));
//                contractVo.setMarket(jsonObject.optString("market"));
//                //新增
//                contractVo.setCodeType(jsonObject.optInt("codeType")); // 合约一级类型
//                contractVo.setCodeSecondType(jsonObject.optInt("codeSecondType")); // 合约二级类型
//
//                String type = jsonObject.optString("type");
//                if (TextUtils.equals("add", type)) {
//                    // TODO: 2020/3/10 更换自选存储
//                    //DBUtils.getInstance().put(contractVo);
//                    SelfStockManager.getInstance().putSelf(contractVo);
//                } else {
//                    //DBUtils.getInstance().remove(contractVo);
//                    SelfStockManager.getInstance().removeSelf(contractVo);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        RxBus.getInstance().post(new StockEvent(StockEvent.EVENT_H5_REFRESH_SELF));
    }

    @Override
    @JavascriptInterface
    public void openStockPool(String params) {
//        LogUtils.d("openStockPool：" + params);
//        if (TextUtils.isEmpty(params))
//            return;
//        try {
//            JSONObject paramsResult = new JSONObject(params);
//            int masterId = paramsResult.getInt("master_id");
//            //String articleUrl = paramsResult.getString("article_url");
//            //JumpUtil.jumpToStockPool(mContext, String.valueOf(masterId));
//            LaunchHelper.launchToMasterDetailActivity(mContext, masterId, ILauncher.MasterDetailTabEnum.STOCKPOOL, "H5页面");
//
//            /*JSONObject jsonObject = new JSONObject();
//            jsonObject.put(SensorConst.APP_CLICK_NAME, masterId + "");
//            jsonObject.put("url", articleUrl);
//            SensorsTracker.getInstance().track("h5_article_bigshot", jsonObject);*/
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    @JavascriptInterface
    public void openMoreHotNews(String params) {
//        LogUtils.d("openMoreHotNews：" + params);
//        if (TextUtils.isEmpty(params)) {
//            ArticlePageActivity.launch(mContext);
//            return;
//        }
//        try {
//            JSONObject jsonObject = new JSONObject(params);
//            String categoryId = jsonObject.getString("category_id");
//            ArticlePageActivity.launch(mContext, Integer.valueOf(categoryId));
//        } catch (JSONException e) {
//            e.printStackTrace();
//            ArticlePageActivity.launch(mContext);
//        }
    }

    @Override
    @JavascriptInterface
    public void openPdf(String params) {
//        LogUtils.d("openPDF：" + params);
//        if (TextUtils.isEmpty(params))
//            return;
//        try {
//            JSONObject paramsResult = new JSONObject(params);
//            String pdfUrl = paramsResult.getString("url");
//            JumpUtil.jumpToCommonPDF(mContext, pdfUrl);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * {
     * isFullscreen: Boolean // 是否全屏显示页面
     * }
     *
     * @param jsonParams
     */
    @Override
    @JavascriptInterface
    public void setFullscreen(String jsonParams) {
//        try {
//            if (TextUtils.isEmpty(jsonParams)) {
//                return;
//            }
//            JSONObject jsonObject = new JSONObject(jsonParams);
//            boolean isFullScreen = jsonObject.optBoolean("isFullscreen");
//            if (mContext instanceof OnWebSettingCallBack && mWebView != null) {
//                mWebView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        ((OnWebSettingCallBack) mContext).setFullscreen(isFullScreen);
//                    }
//                });
//            }
//            ((OnWebSettingCallBack) mContext).setFullscreen(isFullScreen);
//        } catch (Exception e) {
//
//        }
    }

    /**
     * {
     * title: '', // 右上角显示的文字
     * <p>
     * url: '' // 点击右上角文字跳转的地址
     * <p>
     * }
     *
     * @param jsonParams
     */
    @Override
    @JavascriptInterface
    public void setRightCorner(String jsonParams) {
//        try {
//            if (TextUtils.isEmpty(jsonParams)) {
//                return;
//            }
//            JSONObject jsonObject = new JSONObject(jsonParams);
//            String title = jsonObject.optString("title");
//            String url = jsonObject.optString("url");
//            if (mContext instanceof OnWebSettingCallBack && mWebView != null) {
//                mWebView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        ((OnWebSettingCallBack) mContext).setRightCorner(title, url);
//                    }
//                });
//            }
//        } catch (Exception e) {
//
//        }
    }

    /**
     * 打开一个新的页面
     * 只供本地使用 不支持 H5使用
     *
     * @param title
     * @param url
     */
    @Override
    @JavascriptInterface
    public void onOpenWebActivity(String title, String url) {
        openWebView(title, url);
    }

    /**
     * h5打开个股详情页信号弹窗
     *
     * @return
     */
    @Override
    @JavascriptInterface
    public void h5OpenSignDialog() {
//        RxBus.getInstance().post(new StockEvent(StockEvent.EVENT_H5_OPEN_SIGN_DIALOG));
    }

    @Override
    public void onNotificationRefresh() { //通知h5刷新的方法
        noticeMessageCallBack();
    }

    @Override
    public String getParams() {
        return mParams;
    }

    @Override
    public void showH5FontSettingView() {
//        invokeJs(JsMethodName.showSetFontSize, "");
    }


//    public String result(int code, String msg, JsonObject result) {
//        JsonObject jo = new JsonObject();
//        jo.addProperty("code", code);
//        jo.addProperty("message", msg);
//        jo.add("result", result);
//        return jo.toString();
//        return "";
//    }

    public   JSAppBridgeImpl setContext(Activity mContext) {
        this.mContext = mContext;
        return this;
    }

    private void openWebView(String title, String url) {
//        WebVo webVo = new WebVo(url, this, true);
//        webVo.setTitle(title);
//        Intent intent = new Intent(mContext, WebActivity.class);
//        intent.putExtra(WebVo.WEB_VO, webVo);
//        mContext.startActivity(intent);
    }

    public String getUserAgent() {
        return mUserAgent;
    }

    public void invokeJs(String jsFunction, JSONObject params) {
//        if (mWebView == null) {
//            LogUtils.e(TAG, "have no webview instance");
//            return;
//        }
//        String js = jsFunction + "(" + params + ");";
//        LogUtils.d(TAG, "invokeJs=" + js);
//        mWebView.post(new Runnable() {
//            @Override
//            public void run() {
//                //获取行情数据
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    mWebView.evaluateJavascript(js, new ValueCallback<String>() {
//                        @Override
//                        public void onReceiveValue(String value) {
//                            LogUtils.e(TAG, "[WebView] evaluateJavascript:value=" + value);
//                        }
//                    });
//                } else {
//                    mWebView.loadUrl("javascript:" + js);
//                }
//            }
//        });
    }

    public void invokeJs(String jsFunction, String params) {
//        if (mWebView == null) {
//            LogUtils.e(TAG, "have no webview instance");
//            return;
//        }
//        String js = jsFunction + "(" + params + ");";
//        LogUtils.d(TAG, "invokeJs=" + js);
//        mWebView.post(new Runnable() {
//            @Override
//            public void run() {
//                //获取行情数据
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    mWebView.evaluateJavascript(js, new ValueCallback<String>() {
//                        @Override
//                        public void onReceiveValue(String value) {
//                            LogUtils.e(TAG, "[WebView] evaluateJavascript:value=" + value);
//                        }
//                    });
//                } else {
//                    mWebView.loadUrl("javascript:" + js);
//                }
//            }
//        });
    }

    public void getToutiaoMessage(Context context, String type) {
//        TreeMap<String, String> paramMap = new TreeMap<>();
//        paramMap.put("mac", MacUtils.getMac(context));
//        paramMap.put("os", "0");
//        paramMap.put("imei", DeviceUtils.getIMEI(context));
//        paramMap.put("androidid", DeviceUtils.getAndroidId(context));
//        paramMap.put("event_type", type);
//        paramMap.put("uid", UserManager.getInstance().getUid());
//        paramMap.put("uuid", DeviceUtils.getUUID());
//        paramMap.put("brand", DeviceUtils.getMobileInfo());
//        paramMap.put("oaid", !TextUtils.isEmpty(DevicesUtil.getOaid()) ? DevicesUtil.getOaid() : "");
//        new RetrofitProxy<>().create(CmsApi.class, HttpURL.getCmsBaseURL())
//                .getToutiaoMessage(paramMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response.string());
//                            LogUtils.d(this, "ToutiaoMessage：" + jsonObject.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
    }


    private void noticeMessageCallBack() {//设置通知后 通知h5刷新页面
//        if (FSystemSettingHelper.isIsHasLaunchToNotificationSetting()) {
//            FSystemSettingHelper.setIsHasLaunchToNotificationSetting(false);
//            invokeJs(JsMethodName.noticeMessageCallBack, "");
//        }
    }
}
