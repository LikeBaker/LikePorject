package com.like26th.likeproject.fileprovide;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.like26th.likeproject.BuildConfig;
import com.like26th.likeproject.R;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/29.
 */

public class InitApkActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileprovider);
        ButterKnife.bind(this);

        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23 && (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED))){
            requestPermissions(permissions, 1);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_init)
    public void onViewClicked() {
        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "yvjian.apk";
        Log.d("IHomePresenterCompl", downloadPath);
        initApk(new File(downloadPath));
    }

    private void initApk(File file) {
        if (!file.exists()){
            Log.d("IHomePresenterCompl", "文件不存在");
            return;
        } else {
            Log.d("IHomePresenterCompl", "文件存在");
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + file.toString());
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //在服务中开启activity必须设置flag,后面解释
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, "com.like26th.likeproject.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }


//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try{
            this.startActivity(intent);
            Log.d("IHomePresenterCompl", "开始安装");
        } catch (Exception e) {
            Log.d("IHomePresenterCompl", e.toString());
            Toast.makeText(this, "存在相同签名的应用", Toast.LENGTH_SHORT).show();
        }
    }
}
