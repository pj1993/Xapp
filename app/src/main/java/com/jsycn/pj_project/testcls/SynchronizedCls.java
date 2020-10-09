package com.jsycn.pj_project.testcls;

/**
 * create by pj on 2019/10/30
 * 刺.刺.刺激...
 */
public class SynchronizedCls {
    public static int i=0;
    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int c=0;c<100_100;c++){
                    i++;
                }
                System.out.println("A的结果是:"+i);
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int c=0;c<100_100;c++){
                    i++;
                }
                System.out.println("B的结果是:"+i);
            }
        }.start();
    }

}
