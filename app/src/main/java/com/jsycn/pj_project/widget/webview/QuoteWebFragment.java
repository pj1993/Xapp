package com.jsycn.pj_project.widget.webview;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.Lifecycle;

import com.blankj.utilcode.util.LogUtils;
import com.jsycn.pj_project.BuildConfig;
import com.jsycn.pj_project.R;
import com.jsycn.pj_project.entity.WebVo;
import com.jsycn.pj_project.utils.AppExistUtil;

import java.io.File;



public class QuoteWebFragment extends BaseRxFragment<BasePresenter> implements BaseView {


    NestedScrollWebView mWebView;
    ProgressBar mProgressBar;
    LinearLayout mErrorView;


    private WebVo mWebVo;
    // 加载失败标识
//    private boolean mLoadError;

    // H5拍照/图片上传
    private static final int REQUEST_CODE_GRANT_PERMIT = 0x9527;
    private static final int REQUEST_CODE_UPLOAD_IMAGE = 0x9528;
    public static final int REQUEST_CODE_TOLOGIN = 0x9529;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageList;
    private Uri mCameraUri;


    @Override
    protected int initLayout() {
        return R.layout.fragment_quote_web;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = view.findViewById(R.id.web_view);
        mProgressBar = view.findViewById(R.id.web_progress_bar);
        mErrorView = view.findViewById(R.id.web_error_view);
    }

    @Override
    protected void initPresenter() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(WebVo.WEB_VO)) {
            mWebVo = (WebVo) args.getSerializable(WebVo.WEB_VO);
        }
    }

    @Override
    protected void initView(Bundle bundle) {
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
//        StatusBarUtils.setDarkStatusBar(mContext, mStatusBar, com.libs.core.R.color.white, Color.TRANSPARENT);



        initTitleView();
        initWebView();

        if (mWebVo != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mWebVo.getJsAppBridge() != null) {
                mWebVo.getJsAppBridge().setContext(mContext).setWebView(mWebView);
                mWebView.addJavascriptInterface(mWebVo.getJsAppBridge(), JSAppBridge.JS_OBJ_NAME);
            } else {
                mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
            }

            if (mWebVo.getJsAppBridge() != null) {
                String preUA = mWebView.getSettings().getUserAgentString();
                mWebView.getSettings().setUserAgentString(preUA + mWebVo.getJsAppBridge().getUserAgent());
            }

            mWebView.loadUrl(mWebVo.getUrl());
        }

        register();
    }

    /**
     * 初始化TitleView
     */
    private void initTitleView() {
//        if (mWebVo != null) {
//            if (mWebVo.isHasTitle()) {
//                mTitleLay.setVisibility(View.VISIBLE);
//                mTitleView.setText(mWebVo.getTitle());
//                if (mWebVo.isHasShare()) {
//                    mShareView.setVisibility(View.VISIBLE);
//                } else {
//                    mShareView.setVisibility(View.GONE);
//                }
//                if (!TextUtils.isEmpty(mWebVo.getMarker_code())) {
//                    mStockDetail.setVisibility(View.VISIBLE);
//                } else {
//                    mStockDetail.setVisibility(View.GONE);
//                }
//
//            } else {
//                mTitleLay.setVisibility(View.GONE);
//            }
//        }
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);

        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        // 内容调试支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        // 跨域安全支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccess(false);
            webSettings.setAllowFileAccessFromFileURLs(false);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        // 混合加载支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 媒体自动播放支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        mWebView.setWebViewClient(new MWebViewClient());
        mWebView.setWebChromeClient(new MWebChromeClient());
        mWebView.setDownloadListener(new MDownloadListener());
    }

    private void register() {
//        RxBus.getInstance().register(BaseEvent.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .as(this.<BaseEvent>bindLifecycle(Lifecycle.Event.ON_DESTROY))
//                .subscribe(new Consumer<BaseEvent>() {
//                    @Override
//                    public void accept(BaseEvent event) throws Exception {
//                        switch (event.getEventId()) {
//                            case UserEvent.EVENT_USER_LOGIN:
//                            case UserEvent.EVENT_USER_LOGOUT:
//                                LogUtils.d(this, "接收登录/登出刷新事件");
//                                mWebView.reload();
//                                break;
//                        }
//                    }
//                });
    }

    public void showShareVisible() {
//        mContext.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mShareView != null) {
//                    mShareView.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }


//    @OnClick({ R.id.stock_tv})
//    void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.share_tv) {
//            if (mWebVo != null && mWebVo.getJsAppBridge() != null) {
//                if (!TextUtils.isEmpty(mWebVo.getJsAppBridge().getParams())) {
//                    mWebVo.getJsAppBridge().share(mWebVo.getJsAppBridge().getParams());
//                }
//            }
//        } else
//            if (id == R.id.stock_tv) {
//            SensorsUtils.trackCommon("diagnostic_stock_quote", "查看行情");
//            UserEvent userEvent = new UserEvent(UserEvent.EVENT_STOCK_DETAIL);
//            userEvent.putExtra(UserEvent.MARKET_CODE, mWebVo.getMarker_code());
//            RxBus.getInstance().post(userEvent);
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
            mWebView.resumeTimers();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
            mWebView.pauseTimers();
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (mWebView != null) {
                mWebView.clearCache(false);
                mWebView.removeAllViews();
                mWebView.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    /**
     * 网页下载
     */
    class MDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                    String mimeType, long contentLength) {
//            LogUtils.e(this, "url=" + url + ",userAgent=" + userAgent + ",contentDisposition=" +
//                    contentDisposition + ",mimeType=" + mimeType + ",contentLength=" + contentLength);
            if (TextUtils.isEmpty(url))
                return;
            Uri uri = Uri.parse(url.trim());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    /**
     * WebViewClient
     */
    class MWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtils.d(this, "url:" + url + "-----onPageStarted");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.d(this, "url:" + url + "-----onPageFinished");
//            injectImageClickListener(view, "onPageFinished");
            // 重置标题视图
            setWebTitle(view.getTitle());
        }


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            LogUtils.e(this, "onReceivedError(111)--" + errorCode);
            // 断网或者网络连接超时
            if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT) {
                //showErrorPage();
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            LogUtils.e(this, "onReceivedError(222)" + error.getErrorCode());
            // 断网或者网络连接超时
            if (error.getErrorCode() == ERROR_HOST_LOOKUP || error.getErrorCode() == ERROR_CONNECT || error.getErrorCode() == ERROR_TIMEOUT) {
                //showErrorPage();
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            LogUtils.e(this, "onReceivedHttpError" + errorResponse.getStatusCode());
            int statusCode = errorResponse.getStatusCode();
            if (404 == statusCode || 500 == statusCode) {
                //showErrorPage();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.d(this, "shouldOverrideUrlLoading(111)-->url:" + url);
            injectImageClickListener(view, "shouldOverrideUrlLoading");
            return loadViewByUrl(url, super.shouldOverrideUrlLoading(view, url));
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            LogUtils.d(this, "shouldOverrideUrlLoading(222)-->url:" + request.getUrl().toString());
            injectImageClickListener(view, "shouldOverrideUrlLoading");
            return loadViewByUrl(request.getUrl().toString(), false);
        }

        private boolean loadViewByUrl(String url, boolean isOverride) {
            try {
                if (url != null && url.startsWith("weixin://")) {
                    if (AppExistUtil.isWeixinAvilible(mContext)) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivity(intent);
                    } else {
                        showToast("检查到您手机没有安装微信，请安装后使用该功能");
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("微信打开失败");
            }
            return isOverride;
        }

        /**
         * 注入JS代码实现图片预览
         */
        private void injectImageClickListener(WebView webView, String tag) {
            webView.loadUrl("javascript:(function(){ "
                    + "var images = document.getElementsByTagName('img'); "
                    + "var urls=[]; "
                    + "for(var i=0;i<images.length;i++) { "
                    + "  urls[i] = images[i].src; "
                    + "} "
                    + "for(var i=0;i<images.length;i++) { "
                    + "  images[i].onclick = function() { "
                    + "    window.jindashiapp.openImage(urls, this.src); "
                    + "  } "
                    + "} "
                    + "})()");
            LogUtils.d(this, "页面已注入JS脚本" + "----" + tag);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    /**
     * WebChromeClient
     */
    class MWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            // 此处用fragment会出现闪退，改用dialog
            //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
//            final SimpleDialog mMaterialDialog = new SimpleDialog(mContext);
//            mMaterialDialog.setTitle("西瓜智选股提示您")
//                    .setMessage(message)
//                    .setPositiveButton("确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mMaterialDialog.dismiss();
//                        }
//                    })
//                    .setCanceledOnTouchOutside(true)
//                    .show();
//            result.confirm();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//            final SimpleDialog mMaterialDialog = new SimpleDialog(mContext);
//            mMaterialDialog.setTitle("西瓜智选股提示您")
//                    .setMessage(message)
//                    .setPositiveButton("确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            result.confirm();
//                            mMaterialDialog.dismiss();
//                        }
//                    })
//                    .setNegativeButton("取消", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            result.cancel();
//                            mMaterialDialog.dismiss();
//                        }
//                    })
//                    .setCanceledOnTouchOutside(true)
//                    .show();
            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String message = consoleMessage.message();
            int lineNumber = consoleMessage.lineNumber();
            String sourceID = consoleMessage.sourceId();
            String messageLevel = consoleMessage.message();

            LogUtils.d("[WebView]", String.format("[%s] sourceID: %s lineNumber: %n message: %s",
                    messageLevel, sourceID, lineNumber, message));
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public void onProgressChanged(WebView view, int progress) {
            super.onProgressChanged(view, progress);
            // 设置进度值
            if (mProgressBar != null) {
                mProgressBar.setProgress(progress);
                if (progress != 100) {
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setWebTitle(title);
            // android 6.0 以下通过title获取
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                if (title.contains("404") || title.contains("500") || title.contains("Error")) {
                    //showErrorPage();
                }
            }
        }

        /**
         * 文件上传
         */
        // Android 3.0--
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            LogUtils.d(this, "Android 3.0--");
            mUploadMessage = uploadMsg;
            showUploadImageDialog();
        }

        // Android 3.0++
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            LogUtils.d(this, "Android 3.0++");
            openFileChooser(uploadMsg);
        }


        // Android 4.1++
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            LogUtils.d(this, "Android 4.1++");
            openFileChooser(uploadMsg);
        }


        // Android 5.0++
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            LogUtils.d(this, "Android 5.0++");
            mUploadMessageList = filePathCallback;
            showUploadImageDialog();
            return true;
        }
    }

    /**
     * 设置标题视图
     */
    private void setWebTitle(String title) {
        // 返回按钮
//        if (mWebView != null && mBackView != null) {
//            if (mWebView.canGoBack()) {
//                mBackView.setText("返回");
//            } else {
//                mBackView.setText("关闭");
//            }
//        }
//        // 标题名称
//        if (mWebVo != null && mTitleView != null) {
//            //&& TextUtils.isEmpty(mTitleView.getText())
//            if (mWebVo.isHasTitle()) {
//                if (!TextUtils.isEmpty(title)) {
//                    // 控制标题最多显示15个字符
//                    if (title.length() <= 15) {
//                        mTitleView.setText(title);
//                    } else {
//                        // 9 + 3 + 3
//                        String newTitle = title.substring(0, 9) + "..." + title.substring(title.length() - 3);
//                        mTitleView.setText(newTitle);
//                    }
//                }
//            }
//        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_GRANT_PERMIT) {
            if (permissions.length == 0 || grantResults.length == 0) {
                // 显示设置权限提示对话框
                showSystemSettingsDialog();
                return;
            }
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    openImageByPhotos();
                }
                if (permissions[0].equals(Manifest.permission.CAMERA)) {
                    openImageByCamera();
                }
            } else {
                // 是否勾选不再询问（true-未勾选 false-已勾选）
                if (!ActivityCompat.shouldShowRequestPermissionRationale(mContext, permissions[0])) {
                    // 显示设置权限提示对话框
                    showSystemSettingsDialog();
                }
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.d(this, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPLOAD_IMAGE) {
            // 5.0++
            if (mUploadMessageList != null) {
                LogUtils.d(this, "onActivityResult---<5.0++>");
                Uri[] results = null;
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        results = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
                    } else {
                        if (mCameraUri != null) {
                            results = new Uri[]{mCameraUri};
                        }
                    }
                }
                mUploadMessageList.onReceiveValue(results);
                mUploadMessageList = null;
            }
            // 5.0--
            if (mUploadMessage != null) {
                LogUtils.d(this, "onActivityResult---<5.0-->");
                Uri result = (data == null || resultCode != Activity.RESULT_OK) ? null : data.getData();
                if (result == null && data == null && resultCode == Activity.RESULT_OK) {
                    result = mCameraUri;
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else if (requestCode == REQUEST_CODE_TOLOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                if (mWebVo != null && mWebVo.getJsAppBridge() != null) {
                    mWebVo.getJsAppBridge().onLoginRequest();
                }
            }
        }
    }

    /**
     * 手动授权对话框
     */
    private void showSystemSettingsDialog() {
        String message = "拍照相关权限已关闭，请开启后再进行操作。";
//        final SimpleDialog aDialog = new SimpleDialog(mContext);
//        aDialog.setTitle("友情提示")
//                .setMessage(message)
//                .setPositiveButton("确定", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        aDialog.dismiss();
//                        // 打开系统设置
//                        Uri packageURI = Uri.parse("package:" + mContext.getPackageName());
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                        startActivity(intent);
//                        mContext.finish();
//                    }
//                })
//                .setCanceledOnTouchOutside(false)
//                .show();
    }

    /**
     * 图片上传对话框
     */
    private void showUploadImageDialog() {
//        final SimpleDialog aDialog = new SimpleDialog(mContext);
//        aDialog.setTitle("图片上传")
//                .setMessage("请选择获取图片方式")
//                .setPositiveButton("相册", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        aDialog.dismiss();
//                        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
//                    }
//                })
//                .setNegativeButton("拍照", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        aDialog.dismiss();
//                        checkPermission(Manifest.permission.CAMERA);
//                    }
//                })
//                .setCanceledOnTouchOutside(false)
//                .show();
    }

    /**
     * 检查权限
     */
    private void checkPermission(String permission) {
        if (PermissionChecker.checkSelfPermission(mContext, permission) == PermissionChecker.PERMISSION_GRANTED) {
            if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // 打开相册
                openImageByPhotos();
            } else if (permission.equals(Manifest.permission.CAMERA)) {
                // 打开拍照
                openImageByCamera();
            }
        } else {
            ActivityCompat.requestPermissions(mContext, new String[]{permission}, REQUEST_CODE_GRANT_PERMIT);
        }
    }

    /**
     * 打开系统相册
     */
    private void openImageByPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE_UPLOAD_IMAGE);
    }

    /**
     * 打开系统拍照
     */
    private void openImageByCamera() {
//        String imgSavePath = StorageUtils.getImageCacheDir(mContext) + File.separator + System.currentTimeMillis() + ".jpg";
//        LogUtils.d(this, "拍照图片路径：" + imgSavePath);
//        mCameraUri = IntentUtils.getUriForFile(mContext, new File(imgSavePath));
//        IntentUtils.startCameraAction(mContext, mCameraUri, REQUEST_CODE_UPLOAD_IMAGE);
    }

    //------------------------------------以上是图片上传相关----------------------------------------

    /**
     * 显示错误页
     */
    /*private void showErrorPage() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.VISIBLE);
            mErrorView.findViewById(com.libs.core.R.id.reload_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLoadError = false;
                    mWebView.reload();
                }
            });
        }
        mLoadError = true;
    }*/

    /**
     * 隐藏错误页
     */
   /* private void hideErrorPage() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
        mLoadError = false;
    }
*/
    //------------------------------------以上是错误页面相关----------------------------------------
}
