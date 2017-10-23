package husky.note.huskynote.utils;

import husky.note.huskynote.HuskyApp;

/**
 * Created by Administrator on 2017/10/20.
 */

public class DispalyUtils
{
    public static int dp2px(float dp)
    {
        // 获取显示器的密度 —— 一个尺寸因子
        final float scale = HuskyApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
