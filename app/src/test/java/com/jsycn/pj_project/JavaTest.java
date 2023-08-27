package com.jsycn.pj_project;

import android.util.LruCache;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Description:
 * @Author: jsync
 * @CreateDate: 2021/5/8 10:37
 */
public class JavaTest {

    protected String s2 = "ss";
    String s3 = "sss";
    class TestScope{
        public void tt(){
            String sss = s3;
            String s = s2;//这个class可以访问s2，说明protect比默认的范围大
        }
    }

    @Test
    public synchronized void testBoolean() throws InterruptedException {
        Boolean a = null;
        System.out.println(a==null?"true":"false");
        short s1 = 1;
        s1 +=1;
        System.out.println(a==null?"true":"false");
        ScheduledExecutorService poll2 = Executors.newScheduledThreadPool(9);
        //poll2.schedule()
        //ArrayBlockingQueue
        //LinkedBlockingQueue

        //LruCache
        //LinkedHashMap
        //HashMap

        wait();
        synchronized (this){
            wait();
        }
        notifyAll();

        ExecutorService poll = Executors.newFixedThreadPool(7);
        Callable c = (Callable<String>) () -> "s3";
        FutureTask<String> task = new FutureTask<String>(c);//自己是一个runable和future
        //poll.execute(task);
        //String s = task.get() //获取返回值
    }

    class Fu {
        String a = "fu";
        protected String getMsg(){
            return "fuf";
        }
    }
    class Zi extends Fu{
        String a = "zi";

        @Override
        protected String getMsg() {
            return "ziz";
        }
    }
    @Test
    public void testE(){
        Fu f = new Zi();
        String x = f.a;//调用的父类的成员变量
        String x2 = f.getMsg();
        System.out.println("false");
    }


    @Test
    public void testCompletableFuture(){
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "11111111111111111";
            }
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "222222222222";
            }
        });

        CompletableFuture.allOf(cf1,cf2).whenComplete(new BiConsumer<Void, Throwable>() {
            @Override
            public void accept(Void unused, Throwable throwable) {

            }
        }).join();

    }

    interface Asss{

    }

}
