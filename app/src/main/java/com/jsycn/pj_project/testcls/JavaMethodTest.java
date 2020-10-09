package com.jsycn.pj_project.testcls;

import java.util.Arrays;

/**
 * Created by pj on 2019/3/27.
 * 刺.刺.刺激
 * 各种排序算法
 */
public class JavaMethodTest {
    public static void  main(String []args){
//        String s0="kvill";
//        String s1="kvill";
//        String s2="kv" + "ill";
//        System.out.println( s0==s1 );
//        System.out.println( s0==s2 );
        int[] nums={6,3,17,7,4,12,3,10,1,8,5};

//        System.out.println(Arrays.toString(maoPao(nums)));
//        System.out.println(Arrays.toString(xuanZe(nums)));
//        System.out.println(Arrays.toString(charu(nums)));
//        System.out.println(Arrays.toString(kuaisu(nums,0,nums.length-1)));
//        System.out.println(Arrays.toString(insertSortDichotomy(nums)));
        System.out.println(Arrays.toString(xiEr(nums)));
    }

    //冒泡排序
    public static int[] maoPao(int[] nums){
        for (int i=0;i<nums.length;i++){
            //每次循环剩下的所有，比较相邻的元素，找出最大的，然后放在最右边
            for (int j=0;j<nums.length-1-i;j++){
                if(nums[j]>nums[j+1]){//每次比较相邻两个，左边大于右边，则交换
                    int temp=nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;
                }
            }
        }
        return nums;
    }

    //选择排序
    public static int[] xuanZe(int[] nums){
        for (int i=0;i<nums.length;i++){
            int index=i;//记录最小值的下标
            int vlue=nums[i];//记录最小值
            for (int j=i+1;j<nums.length;j++){
                if(nums[j]<vlue){
                    index=j;
                    vlue=nums[j];
                }
            }
            nums[index]=nums[i];
            nums[i]=vlue;
        }
        return nums;
    }
    //插入排序
    public static int[] charu(int [] nums){
        for (int i=1;i<nums.length;i++){
            int j=i-1;
            int value=nums[i];
            while(j>=0&&value<nums[j]){
                //前移当前
                nums[j+1]=nums[j];
                j--;
            }
            //说明找到了插入位子
            nums[j+1]=value;
        }
        return nums;
    }

    //二分插入排序
    public static int[] insertSortDichotomy(int[] nums){
        //前面和插入排序思想类似，首先左边的是排好的，后面的只要找到对应的左边的位置，插进去就好了
        for (int i=1;i<nums.length;i++){
            int value=nums[i];
            int left=0;
            int right=i-1;
            //最后leftright必然会相等，但是想等的这个地方不是我们的插入点，必须再判断一次，找出插入点
            while (left<=right){//这里必须是等于，应为等于的时候，再进行一次循环  可以知道 插入的位置是在当前位置的左边还是右边  左边则left不变  右边则left还得加1
                    int mid=(left+right)/2;
                    if(nums[mid]>value){
                        right=mid-1;
                    }else {
                        left=mid+1;
                    }
            }
            //将前面排好序的 偏移，给value流出位置插入
            for(int j=i-1;j>left-1;j--){
                nums[j+1]=nums[j];
            }
            //赋值给插入的位置
            nums[left]=value;
        }
        return nums;
    }

    //希尔排序（其实就是分组插入法）
    public static int[] xiEr(int[] nums){
        int group=nums.length/2;
        while (group>0){
            for (int i=group;i<nums.length;i++){
                int j=i-group;//
                int value=nums[i];//要插入的数，然后在组中找到插入的下标
                while (j>=0&&nums[j]>value){
                    nums[j+group]=nums[j];
                    j=j-group;
                }
                //插装进去
                nums[j+group]=value;
                System.out.print(Arrays.toString(nums)+"\n");
            }
            group=group/2;
        }
        return nums;
    }


    //归并排序

    //堆排序

    //快速排序
//    int[] nums={6,3,17,7,4,12,3,10,1,8,5};
    public static int[] kuaisu(int[] nums,int low,int hi){
        //以low为基准值， 比我大的放右边 比我小的放左边
        if(low>=hi){
            return nums;
        }
        int i=low,j=hi;
        int temp=nums[low];
        while (i<j){
            //从左到右，找到一个大于基准temp的数
            while (nums[i]<=temp&&i<j){
                i++;
            }
            //从右到左找到一个小于基准temp的数   前提是i《j
            while (nums[j]>=temp&&i<j){
                j--;
            }
            if(i<j){
                //交换
                int value=nums[i];
                nums[i]=nums[j];
                nums[j]=value;
            }
        }
        //将基准和最后一个  右边的大于基准的数 下标减一  调换
        nums[low]=nums[j-1];
        nums[j-1]=temp;
        kuaisu(nums,low,j-1);
        kuaisu(nums,j,hi);
        return nums;
    }


}
