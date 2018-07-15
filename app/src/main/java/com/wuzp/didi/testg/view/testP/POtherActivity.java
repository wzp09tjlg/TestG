package com.wuzp.didi.testg.view.testP;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.wuzp.didi.testg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuzhenpeng03 (wuzhenpeng03@didichuxing.com)
 */
public class POtherActivity extends AppCompatActivity {

    private final String[] requiredPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
    }

    private void onPermissionRequest() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //do flow work
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0x123);
            } else {
                //do flow work
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x123:
                if (isAllPermissionGranted(grantResults)) {
                    //do flow work
                } else {
                    String[] denyPermissions = getDenyPermissions(permissions, grantResults);
                    if (isNeedTipToUser(denyPermissions)) {
                       //向用户解释授权说明
                    } else {
                       //引导用户打开设置页面进行授权
                    }
                }
                break;
        }
    }

    /**
     * 申请的所有权限是否均已经授权
     *
     * @param grantResults
     * @return
     */
    private boolean isAllPermissionGranted(int[] grantResults) {
        int len = grantResults.length;
        for (int i = 0; i < len; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取没有授权的权限列表
     *
     * @param permissions
     * @param grantResults
     * @return
     */
    private String[] getDenyPermissions(@NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> tempPermissions = new ArrayList<>();
        int len = permissions.length;
        for (int i = 0; i < len; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                tempPermissions.add(permissions[i]);
            }
        }
        int denySize = tempPermissions.size();
        String[] denyPermissions = new String[denySize];
        for (int i = 0; i < denySize; i++) {
            denyPermissions[i] = tempPermissions.get(i);
        }
        return denyPermissions;
    }

    /**
     * 需要向用户解释权限申请说明
     *
     * @return
     */
    private boolean isNeedTipToUser(@NonNull String[] denyPermissions) {
        for (String permission : denyPermissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //需要向用户提示
            } else {
                //引导用户去设置页面进行授权
                return false;
            }
        }
        return true;
    }

}
