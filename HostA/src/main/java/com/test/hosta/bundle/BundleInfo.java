package com.test.hosta.bundle;

import java.io.Serializable;

/**
 * 每个bundle的信息
 * Created by Jayden on 2016/6/17.
 * Email : 1570713698@qq.com
 */
public class BundleInfo implements Serializable {
    private static final long serialVersionUID = 1;

    private String packageName;//包名

    private String apkPath;//apk路径

    private int packageId; //包ID

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
}
