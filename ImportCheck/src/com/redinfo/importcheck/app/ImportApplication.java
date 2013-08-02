package com.redinfo.importcheck.app;

import com.redinfo.importcheck.datamodel.DataTable;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.telephony.TelephonyManager;

public class ImportApplication extends Application {
    private String UserID = "";
    private String LoginName = "";
    private String TrueName = "";
    private TelephonyManager phonyManager = null;

    public String getDeviceId() {
        return this.getTelephonyManager().getDeviceId();
    }

    public String getSimSerialNumber() {
        return this.getTelephonyManager().getSimSerialNumber();
    }

    public String getSubscriberId() {
        return this.getTelephonyManager().getSubscriberId();
    }

    private TelephonyManager getTelephonyManager() {
        if (this.phonyManager == null) {
            this.phonyManager = (TelephonyManager) this
                    .getSystemService(Context.TELEPHONY_SERVICE);
        }
        return this.phonyManager;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserId(String uid) {
        this.UserID = uid;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String name) {
        this.LoginName = name;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String name) {
        this.TrueName = name;
    }

    private DataTable[] Result = null;

    public DataTable[] getResult() {
        return Result;
    }

    private String SysOrder = null;

    public String getSysOrder() {


        return SysOrder;
    }

    public void setResults(String OrderCode, DataTable[] ResultStr) {
        this.SysOrder = OrderCode;
        this.Result = ResultStr;
    }

    public void setResult(DataTable[] Result) {
        this.Result = Result;
    }

    private boolean isAutoFocus = false;

    public boolean getIsAutoFocus() {
        return this.isAutoFocus;
    }

    private Boolean IsAutoFocus() {
        String[] parameters = this.GetCameraParameters();
        for (String parameter : parameters) {
            if (parameter.equalsIgnoreCase("focus-mode=auto"))
                return true;
        }
        return false;
    }

    ;

    private String[] GetCameraParameters() {
        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        String[] params = parameters.flatten().split(";");

        if (camera != null) {
            camera.release();
            camera = null;
        }
        return params;
    }

    @Override
    public void onCreate() {
        this.getTelephonyManager();
        // this.initMapManager();
        this.isAutoFocus = this.IsAutoFocus();
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        /*
		 * if (this.mapManager != null) { this.mapManager.destroy();
		 * this.mapManager = null; }
		 */
        super.onTerminate();
    }
}
