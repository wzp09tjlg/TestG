package com.wuzp.didi.testg.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

/**
 * 权限申请辅助类
 * 用于展示弹框样式的控制的类
 *
 * @author wuzhenpeng03 (wuzhenpeng03@didichuxing.com)
 */
public class LocalPermissionHelper {

    //申请权限
    public static void doRequestPermissions(String[] permissions) {

    }

    //判断权限是否需要引导提示(针对用户选择了禁止询问的选择框和禁止该权限)
    private static boolean isPermissionNeedTip(Activity activity, String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return true;
        }

        boolean result = false;
        for (String str : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, str)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private AlertDialog buildDialogStyle(Activity activity, String title, String contentMsg,
                                         String positiveTxt, String negativeTxt, final CallBackListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(contentMsg)
                .setPositiveButton(positiveTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startMainActivity();//去设置页面进行设置
                        if (listener != null) {
                            listener.onGranted();
                        }
                    }
                })
                .setNegativeButton(negativeTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onDeny();
                        }
                    }
                })
                .setCancelable(false)
                .create();

        return dialog;
    }

    //针对各大手机厂商打开设置页面的适配
    //华为手机
    public void openHuaweiSetting() {
    }

    //小米手机
    public void openXiaomiSetting() {
    }

    //魅族手机
    public void openMeizuSetting() {
    }

    //其他手机
    public void openOtherSetting() {
    }


    interface CallBackListener {
        void onGranted();

        void onDeny();
    }
}
