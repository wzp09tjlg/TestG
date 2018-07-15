package com.wuzp.didi.testg.view.testP;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wuzp.didi.testg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里的包使用传统的权限申请，然后再使用小玲的权限申请框架
 * 1.定义需要申请的权限
 * 2.适当的时机申请权限
 * 3.针对没有授权的权限再次申请
 * 4.针对用户设置不在询问的权限提示，并引导用户去设置也开启权限
 * 5.权限都已经开启 则进行后续操作
 *
 * @author wuzhenpeng03 (wuzhenpeng03@didichuxing.com)
 */
public class PActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnP;
    Context mContext = this;

    final int resultCode = 0x123;

    private final String[] requiredPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_p);
        init();
        initData();
        requestPermission();
    }

    private void init() {
        btnP = findViewById(R.id.btn_p);
    }

    private void initData() {
        btnP.setOnClickListener(this);
    }

    private void requestPermission() {
        List<String> array = getNeedTipPermissions(requiredPermissions);
        ActivityCompat.requestPermissions(PActivity.this, requiredPermissions, resultCode);
        Log.e("wzp", "requestPermissions-------- : size?;" + array.size());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("wzp", "requestPermissions***************");
        switch (requestCode) {
            case resultCode:
             for(String str:permissions){
                 // Here, thisActivity is the current activity
                 if (ContextCompat.checkSelfPermission(PActivity.this,
                         str) != PackageManager.PERMISSION_GRANTED) {

                     // Should we show an explanation?
                     if (ActivityCompat.shouldShowRequestPermissionRationale(PActivity.this, str)) {
                         // Show an expanation to the user *asynchronously* -- don't block
                         // this thread waiting for the user's response! After the user
                         // sees the explanation, try again to request the permission.


                         AlertDialog dialog = new AlertDialog.Builder(this)
                                 .setTitle("title")
                                 .setMessage("you must granted the permission")
                                 .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         //  gotoHuaweiPermission();
                                     }
                                 })
                                 .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.dismiss();
                                         //finish();
                                     }
                                 })
                                 .setCancelable(false)
                                 .create();
                         dialog.show();
                     } else {

                         ActivityCompat.requestPermissions(PActivity.this,
                                 new String[]{str},
                                 resultCode);

                         // No explanation needed, we can request the permission.



                         // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                         // app-defined int constant. The callback method gets the
                         // result of the request.
                     }
                 }
             }
//                boolean isAllGranted = checkAllPersmissionOk(permissions, grantResults);
//                if (isAllGranted) {
//                    //do flow work
//                } else {
//                    String[] allDenys = getAllDenyPermissions(permissions, grantResults);
//                    List<String> list = getNeedTipPermissions(allDenys);
//
//                    if (list.size() == 0) {//表示没有   设置过禁止询问的权限
//                        //表示不需要提示 可再次申请权限
//                        Log.e("wzp", "可以再次弹框，进行申请权限");
//                        ActivityCompat.requestPermissions(PActivity.this, requiredPermissions, resultCode);
//                    } else { //有 设置过禁止询问的权限
//                        //表示需要提示
//                        ActivityCompat.requestPermissions(PActivity.this, requiredPermissions, resultCode);
//                        Log.e("wzp", "需要引导用户去设置页面进行设置");
//                        AlertDialog dialog = new AlertDialog.Builder(this)
//                                .setTitle("title")
//                                .setMessage("you must granted the permission")
//                                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        gotoHuaweiPermission();
//                                    }
//                                })
//                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        finish();
//                                    }
//                                })
//                                .setCancelable(false)
//                                .create();
//                       // dialog.show();
//                    }
//                    //需要判断权限是否已经设置禁止询问，如果设置禁止询问 那么就应该提示用户去设置页面进行设置，否者就继续提示弹框申请权限
//                }
//                Log.e("wzp", "onRequestPermissionsResult   " + toShow(permissions) + " : " + toShow(grantResults));
//

                break;
        }
    }

    private void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("dasdsadasda", "gotoHuaweiPermission: ------------------");
        }

    }

    private boolean checkAllPersmissionOk(@NonNull String[] permissions, @NonNull int[] grantResults) {
        int len = permissions.length;
        for (int i = 0; i < len; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                //只要有一个权限不授权 就需要处理 是授权 还是去提示
                return false;
            }
        }
        //所有权限都授权 那么接着做业务的需求
        return true;
    }

    private String[] getAllDenyPermissions(@NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> list = new ArrayList<>();
        int len = permissions.length;
        for (int i = 0; i < len; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                list.add(permissions[i]);
            }
        }
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    //传入的权限都是没有被授权的权限
    private List<String> getNeedTipPermissions(String[] permissions) {
        List<String> list = new ArrayList<>();

        for (String str : permissions) {
            boolean is = ActivityCompat.shouldShowRequestPermissionRationale(PActivity.this, str);
            if (is) {//需要提示用户该权限应去设置页面进行设置
                list.add(str);
            }
        }
        return list;
    }

    private String toShow(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        String result = "";
        for (String str : array) {
            result += ("  " + str);
        }
        return result;
    }

    private String toShow(int[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        String result = "";
        for (int str : array) {
            result += ("  " + str);
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_p:
                break;
        }
    }
}
