package com.wuzp.didi.testg;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.didichuxing.sofa.permission.PermissionHelper;
import com.didichuxing.sofa.permission.PermissionRequest;
import com.didichuxing.sofa.permission.annotation.OnPermissionDenied;
import com.didichuxing.sofa.permission.annotation.OnPermissionGranted;
import com.didichuxing.sofa.permission.annotation.OnShowPermissionExplanation;
import com.didichuxing.sofa.permission.annotation.RequestPermissions;
import com.wuzp.didi.testg.view.testP.other.OtherActivity;

@RequestPermissions
public class MainActivity extends AppCompatActivity {


    private final  int pCode = 100;

    private String sP = Manifest.permission.CALL_PHONE;

    private final String[] requiredPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    boolean first = true;

    @Override
    protected void onStart() {
        super.onStart();
        //startMainActivity();
        Log.e("wzp", "onStart");
        if (first) {
            first = false;
            dorequestPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void dorequestPermission() {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (PermissionHelper.isPermissionsGranted(MainActivity.this, requiredPermissions)) {
                            //手机已经授权
                        } else {
                            //手机没有授权
                            PermissionHelper.requestPermission(MainActivity.this, requiredPermissions);//对拒绝的权限再做一次申请
                        }
                    }
                }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case pCode:
                if (PermissionHelper.isPermissionsGranted(MainActivity.this, requiredPermissions)){
                    startMainActivity();
                }else{
                    finish();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("wzp", "onRequestPermissionsResult permission: " + toShow(permissions) + "    grantR: " + toShow(grantResults));
        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnPermissionGranted
    public void onPermissionGranted() {
        startMainActivity();
        Log.e("wzp", "onPermissionGranted " + System.currentTimeMillis());
    }

    //shouldShowRequestPermissionRationale
    // 返回false，存在二意性，
    // 第一次请求是返回 false,  （这时给用户的提示应该是继续申请权限）
    // 之后用户拒绝授权(没有选择禁止询问) 会返回true，
    // 当用户选择了不再询问，也会返回false。（这是给用户的提示应该是提醒用户去设置页进行设置权限）
    //这个方法好奇怪，
    @OnShowPermissionExplanation(true)
    public void onPermissionExplain(final PermissionRequest permissionRequest) {
        Log.e("wzp", "onPermissionExplain  " + System.currentTimeMillis() + "     " + permissionRequest.toString());
        //permissionRequest.proceed();
    }

    //方法中必须要带参数 不然就编译不过
    @OnPermissionDenied
    public void requestPermissionFailed(final @NonNull String[] deniedPermissions) {
        Log.e("wzp", "onPermissionDenied  " + System.currentTimeMillis() + "     " + "" + toShow(deniedPermissions));
        Toast.makeText(this, "requestPermissionFailed----------", Toast.LENGTH_SHORT).show();
        doPermission(deniedPermissions);
    }

    private void doPermission(final String[] deniedPermissions) {
        boolean needSetting = checkPermissionNeedSetting(deniedPermissions);
        if (needSetting) {
            //need tip to user go setting page
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("title")
                    .setMessage("you must granted the permission")
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //startMainActivity();//去设置页面进行设置
                            gotoHuaweiPermission();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .create();
            dialog.show();
        } else {
            //可以继续申请权限
            //否则就继续弹提示，不断的循环
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("title")
                    .setMessage("you must granted the permission")
                    .setPositiveButton("申请", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PermissionHelper.requestPermission(MainActivity.this, deniedPermissions);//对拒绝的权限再做一次申请
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .create();
            dialog.show();
        }
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

    //是否需要引导到设置页面
    private boolean checkPermissionNeedSetting(String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return true;
        }

        boolean result = false;
        for (String str : permissions) {
            //shouldShowRequestPermissionRationale
            // 方法的说明
            // 返回true 表示之前用户已经拒绝过这个权限的申请，可以再次申请。
            // 返回false 第一次询问 和 用户选择了禁止询问 需要引导用户去设置页面开启权限（存在二意性，因为引导用户做不通的操作）
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, str)) {
                //需要提示
                result = true;
                break;
            }
        }
        return result;
    }

    private void startMainActivity() {
        Log.e("wzp", "nextPage");
        Intent intent = new Intent(this, OtherActivity.class);
        if (getIntent() != null && getIntent().getExtras() != null) {
            intent.putExtras(getIntent().getExtras());
        }
        startActivity(intent);
        finish();
    }

    private void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivityForResult(intent,pCode);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "gotoHuaweiPermission: ------------------");
        }

    }
}
