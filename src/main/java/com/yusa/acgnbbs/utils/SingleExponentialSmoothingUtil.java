package com.yusa.acgnbbs.utils;

import com.yusa.acgnbbs.domain.SingleSmoothingEntity;

import java.text.DecimalFormat;
import java.util.*;

public class SingleExponentialSmoothingUtil {
    public static Double singleExponentialSmoothingMethod( SingleSmoothingEntity singleSmoothingEntity) {
        /** 一次指数平滑公式：
         * F(t+1)=a*Xt+(1-a)*Ft
         */
        // （1）获取实际观察值列表和最后一次的预测值
        List<Double> realParamList = singleSmoothingEntity.getRealDataList();
        System.out.println(realParamList);
        Double lastPredictParam = singleSmoothingEntity.getLastPredictParam();
        Double result = singleSmoothingEntity.getLastPredictParam();
        int count=0;

        System.out.println(lastPredictParam);
        // 定义结果集合类
        Map<Double, Double> resultMap = new HashMap<>();
        List<Map> resultList = new ArrayList<>();
        List<Double> gapList = new ArrayList<>();
        Map<Double, Double> gapMap = new HashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        Boolean flag=false;
        // （2）平滑值区间 [1~10]
        for (double a = 2; a < 10; a++) {
            for (Double realData : realParamList) {
                double smoothParam = a / 10;
//                System.out.println("平滑指数：" + smoothParam + ",实际值是："
//                        + realData + ",上次预测值是："
//                        + lastPredictParam + "，误差为："
//                        + decimalFormat.format(Math.abs(realData - lastPredictParam)));
                // 将误差值装进list方便统计平均误差
                gapList.add(Math.abs(realData - lastPredictParam));
                lastPredictParam = smoothParam * realData + (1 - smoothParam) * lastPredictParam;
                // 计算下次的预测值，保留一位小数
                lastPredictParam = Double.valueOf(decimalFormat.format(lastPredictParam));
                System.out.println("预测下次为：" + lastPredictParam);
                Random random=new Random();
                if(random.nextInt()%2==0){
                    flag=false;
                }else {
                    flag=true;
                }
                if(flag==false) {
                    result += lastPredictParam;
                    count++;
                }
            }
            // 计算误差的平均值
            double totalGap = 0.0;
            for (Double gap : gapList) {
                totalGap = totalGap + gap;
            }
            System.out.println(gapList.size() + "；" + totalGap);
            gapMap.put(a, totalGap / (double) gapList.size());
            // 每更换一个平滑值，预估值都要复位
            lastPredictParam = singleSmoothingEntity.getLastPredictParam();
            // 清空当前list装的误差值
            gapList.clear();
        }
        return result/count;
    }
}
