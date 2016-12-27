package com.delta.smt.ui.main.utils;

import java.math.BigDecimal;

/**
 * Created by Shufeng.Wu on 2016/12/27.
 */

public class StringUtils {
    //apk文件大小换算+单位
    //B、KB、MB转换
    public static String getChangedContentLength(int progress) {
        String res = null;
        if (progress < 1024) {
            res = progress + " B";
            //1024 * 1024
        } else if (progress < 1048576) {
            res = new BigDecimal((double) progress / 1024).setScale(2, BigDecimal.ROUND_DOWN) + " KB";
        } else {
            res = new BigDecimal((double) progress / 1024 / 1024).setScale(2, BigDecimal.ROUND_DOWN) + " MB";
        }
        return res;
    }

    //获取百分比
    public static int getPercentProgress(int progress,int maxProgress) {
        int res = 0;
        String tmp = new BigDecimal((double) progress / maxProgress * 100).setScale(0, BigDecimal.ROUND_DOWN) + "";
        res = (int) Double.parseDouble(tmp);
        return res;
    }
}
