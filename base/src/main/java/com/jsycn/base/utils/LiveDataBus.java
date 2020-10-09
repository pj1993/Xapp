//package com.jsycn.base.utils;
//
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Observer;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by pj on 2019/3/22.
// * 刺.刺.刺激
// * 事件总线  100行轻松实现eventbus 并感知生命周期
// *
// * 1注册订阅
//    LiveDataBus.get()
//    .with("key_test", String.class)
//    .observe(this, new Observer<String>() {
//        @Override
//        public void onChanged(@Nullable String s) {
//            //主线程更新ui等等
//        }
//    });
// *2发送消息：
//    LiveDataBus.get().with("key_test").setValue(s);
//
// *博客地址
// https://www.cnblogs.com/meituantech/p/9376449.html
// 还得完善粘性事件 ，本是自带是粘性事件， 但是作者将observe方法hook掉，让其不带粘性。
// */
//public final class LiveDataBus {
//
//    private final Map<String, BusMutableLiveData<Object>> bus;
//
//    private LiveDataBus() {
//        bus = new HashMap<>();
//    }
//
//    private static class SingletonHolder {
//        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
//    }
//
//    public static LiveDataBus get() {
//        return SingletonHolder.DEFAULT_BUS;
//    }
//
//    public <T> MutableLiveData<T> with(String key, Class<T> type) {
//        if (!bus.containsKey(key)) {
//            bus.put(key, new BusMutableLiveData<>());
//        }
//        return (MutableLiveData<T>) bus.get(key);
//    }
//
//    public MutableLiveData<Object> with(String key) {
//        return with(key, Object.class);
//    }
//
//    private static class ObserverWrapper<T> implements Observer<T> {
//
//        private Observer<T> observer;
//
//        public ObserverWrapper(Observer<T> observer) {
//            this.observer = observer;
//        }
//
//        @Override
//        public void onChanged(@Nullable T t) {
//            if (observer != null) {
//                if (isCallOnObserve()) {
//                    return;
//                }
//                observer.onChanged(t);
//            }
//        }
//
//        private boolean isCallOnObserve() {
//            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//            if (stackTrace != null && stackTrace.length > 0) {
//                for (StackTraceElement element : stackTrace) {
//                    if ("android.arch.lifecycle.LiveData".equals(element.getClassName()) &&
//                            "observeForever".equals(element.getMethodName())) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }
//
//    private static class BusMutableLiveData<T> extends MutableLiveData<T> {
//
//        private Map<Observer, Observer> observerMap = new HashMap<>();
//
//        @Override
//        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
//            super.observe(owner, observer);
//            try {
//                hook(observer);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void observeForever(@NonNull Observer<T> observer) {
//            if (!observerMap.containsKey(observer)) {
//                observerMap.put(observer, new ObserverWrapper(observer));
//            }
//            super.observeForever(observerMap.get(observer));
//        }
//
//        @Override
//        public void removeObserver(@NonNull Observer<T> observer) {
//            Observer realObserver = null;
//            if (observerMap.containsKey(observer)) {
//                realObserver = observerMap.remove(observer);
//            } else {
//                realObserver = observer;
//            }
//            super.removeObserver(realObserver);
//        }
//
//        private void hook(@NonNull Observer<T> observer) throws Exception {
//            //get wrapper's version
//            Class<LiveData> classLiveData = LiveData.class;
//            Field fieldObservers = classLiveData.getDeclaredField("mObservers");
//            fieldObservers.setAccessible(true);
//            Object objectObservers = fieldObservers.get(this);
//            Class<?> classObservers = objectObservers.getClass();
//            Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
//            methodGet.setAccessible(true);
//            Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
//            Object objectWrapper = null;
//            if (objectWrapperEntry instanceof Map.Entry) {
//                objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();
//            }
//            if (objectWrapper == null) {
//                throw new NullPointerException("Wrapper can not be bull!");
//            }
//            Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
//            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
//            fieldLastVersion.setAccessible(true);
//            //get livedata's version
//            Field fieldVersion = classLiveData.getDeclaredField("mVersion");
//            fieldVersion.setAccessible(true);
//            Object objectVersion = fieldVersion.get(this);
//            //set wrapper's version
//            fieldLastVersion.set(objectWrapper, objectVersion);
//        }
//    }
//}
