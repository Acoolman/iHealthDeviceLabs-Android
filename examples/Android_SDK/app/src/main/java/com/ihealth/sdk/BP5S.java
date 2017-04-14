package com.ihealth.sdk;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ihealth.communication.control.Bp5sControl;
import com.ihealth.communication.control.BpProfile;
import com.ihealth.communication.manager.iHealthDevicesCallback;
import com.ihealth.communication.manager.iHealthDevicesManager;
import com.ihealth.communication.manager.iHealthDevicesUpgradeManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class BP5S extends AppCompatActivity implements View.OnClickListener {


    Bp5sControl bp5sControl;
    private static final String TAG = "BP5S";
    private String deviceMac;
    private int clientCallbackId;
    private TextView tv_return;
    private int service = 404;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp5s);

        Intent intent = getIntent();
        deviceMac = intent.getStringExtra("mac");

        findViewById(R.id.btn_getBattery_bp5s).setOnClickListener(this);
        findViewById(R.id.btn_getFunctionInfo_bp5s).setOnClickListener(this);
        findViewById(R.id.btn_getMeasureStatus_bp5s).setOnClickListener(this);
        findViewById(R.id.btn_getOfflineDataNum_bp5s).setOnClickListener(this);
        findViewById(R.id.btn_getOfflineData_bp5s).setOnClickListener(this);
        findViewById(R.id.btn_setRepeatedlyMeasureParameter_bp5s).setOnClickListener(this);
        findViewById(R.id.btn_deleteRepeatedlyMeasureSetting_bp5s).setOnClickListener(this);
        findViewById(R.id.btn_disconnect_bp5s).setOnClickListener(this);

        editText = (EditText) findViewById(R.id.Service_Bp5s);
        findViewById(R.id.changeService_Bp5s).setOnClickListener(this);
        findViewById(R.id.checkInfoFormCloudAndDevice_Bp5s).setOnClickListener(this);
        findViewById(R.id.startUpgrade_Bp5s).setOnClickListener(this);
        findViewById(R.id.stopUpgrade_Bp5s).setOnClickListener(this);
        findViewById(R.id.checkIsUpgrade_Bp5s).setOnClickListener(this);

        tv_return = (TextView) findViewById(R.id.tv_returnMessage);

        clientCallbackId = iHealthDevicesManager.getInstance().registerClientCallback(miHealthDevicesCallback);
        /* Limited wants to receive notification specified device */
        iHealthDevicesManager.getInstance().addCallbackFilterForDeviceType(clientCallbackId, iHealthDevicesManager.TYPE_BP5S);
        /* Get bp5S controller */
        bp5sControl = iHealthDevicesManager.getInstance().getBp5sControl(deviceMac);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_getBattery_bp5s:

                if (bp5sControl != null) {
                    bp5sControl.getBattery();
                } else
                    Toast.makeText(BP5S.this, "bp5sControl == null", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_getFunctionInfo_bp5s:
                if (bp5sControl != null) {
                    bp5sControl.getFunctionInfo();
                } else
                    Toast.makeText(BP5S.this, "bp5sControl == null", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_getMeasureStatus_bp5s:
                if (bp5sControl != null) {
                    bp5sControl.getMeasureStatus();
                } else
                    Toast.makeText(BP5S.this, "bp5sControl == null", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_getOfflineDataNum_bp5s:

                if (bp5sControl != null) {
                    bp5sControl.getOfflineDataNum();
                } else
                    Toast.makeText(BP5S.this, "bp5sControl == null", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_getOfflineData_bp5s:

                if (bp5sControl != null) {
                    bp5sControl.getOfflineData();
                } else
                    Toast.makeText(BP5S.this, "bp5sControl == null", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_setRepeatedlyMeasureParameter_bp5s:

                if (bp5sControl != null) {
                    bp5sControl.setRepeatedlyMeasureParameter(1, 0, 0, 12, 0, 10, 10, 0);
                } else
                    Toast.makeText(BP5S.this, "bp5sControl == null", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_deleteRepeatedlyMeasureSetting_bp5s:

                if (bp5sControl != null) {
                    bp5sControl.deleteRepeatedlyMeasureSetting(1);
                } else
                    Toast.makeText(BP5S.this, "bp5sControl == null", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_disconnect_bp5s:

                if (bp5sControl != null) {
                    bp5sControl.disconnect();
                } else
                    Toast.makeText(BP5S.this, "bp5sControl == null", Toast.LENGTH_LONG).show();
                break;

            case R.id.changeService_Bp5s:
                if (TextUtils.isEmpty(editText.getText())) {
                    service = 404;
                } else {
                    service = Integer.parseInt(editText.getText().toString());
                }
                iHealthDevicesUpgradeManager.getInstance().setType(service);
                break;

            case R.id.checkInfoFormCloudAndDevice_Bp5s:

                iHealthDevicesUpgradeManager.getInstance().queryUpgradeInfoFromDeviceAndCloud(deviceMac, iHealthDevicesManager.TYPE_BP5S);
                break;

            case R.id.startUpgrade_Bp5s:
                iHealthDevicesUpgradeManager.getInstance().startUpGrade(deviceMac, iHealthDevicesManager.TYPE_BP5S);
                break;

            case R.id.stopUpgrade_Bp5s:
                iHealthDevicesUpgradeManager.getInstance().stopUpgrade(deviceMac, iHealthDevicesManager.TYPE_BP5S);
                break;

            case R.id.checkIsUpgrade_Bp5s:
                boolean a = iHealthDevicesUpgradeManager.getInstance().isUpgradeState(deviceMac, iHealthDevicesManager.TYPE_BP5S);
                Log.e(TAG, "a = " + a);
                break;


        }
    }

    iHealthDevicesCallback miHealthDevicesCallback = new iHealthDevicesCallback() {
        @Override
        public void onScanDevice(String mac, String deviceType, int rssi) {
            super.onScanDevice(mac, deviceType, rssi);
        }

        @Override
        public void onScanDevice(String mac, String deviceType, int rssi, Map manufactorData) {
            super.onScanDevice(mac, deviceType, rssi, manufactorData);
        }

        @Override
        public void onScanFinish() {
            super.onScanFinish();
        }

        @Override
        public void onDeviceConnectionStateChange(String mac, String deviceType, int status, int errorID) {
            super.onDeviceConnectionStateChange(mac, deviceType, status, errorID);
        }

        @Override
        public void onDeviceConnectionStateChange(String mac, String deviceType, int status, int errorID, Map manufactorData) {
            super.onDeviceConnectionStateChange(mac, deviceType, status, errorID, manufactorData);
        }


        @Override
        public void onDeviceNotify(String mac, String deviceType, String action, String message) {
            super.onDeviceNotify(mac, deviceType, action, message);

            Log.i(TAG, "mac: " + mac);
            Log.i(TAG, "deviceType: " + deviceType);
            Log.i(TAG, "action: " + action);
            Log.i(TAG, "message: " + message);

            if (action.equals(BpProfile.ACTION_BATTERY_BP5S)) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    int battery = jsonObject.getInt(BpProfile.BATTERY_BP5S);
                    Message msg = new Message();
                    msg.what = HANDLER_MESSAGE;
                    msg.obj = "Battery = " + battery;
                    myHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (action.equals(BpProfile.ACTION_FUNCTION_INFORMATION_BP5S)) {
                try {
                    JSONObject info = new JSONObject(message);

                    int lastOperationStatus = info.getInt(BpProfile.FUNCTION_OPERATING_STATE_BP5S);  //最后一次操作状态
                    boolean upAirMeasureFlg = info.getBoolean(BpProfile.FUNCTION_IS_UPAIR_MEASURE_BP5S);    //上气、下气测量标志位
                    boolean armMeasureFlg = info.getBoolean(BpProfile.FUNCTION_IS_ARM_MEASURE_BP5S);  //腕式、臂式
                    boolean haveAngleSensorFlg = info.getBoolean(BpProfile.FUNCTION_HAVE_ANGLE_SENSOR_BP5S); //是否带角度
                    boolean haveRepeatedlyMeasureFlg = info.getBoolean(BpProfile.FUNCTION_HAVE_REPEATEDLY_MEASURE_BP5S); //是否带多次测量功能
                    boolean haveOfflineFlg = info.getBoolean(BpProfile.FUNCTION_HAVE_OFFLINE_BP5S); //是否带离线测量功能
                    boolean haveHSDFlg = info.getBoolean(BpProfile.FUNCTION_HAVE_HSD_BP5S); //是否有HSD
                    int memoryGroup = info.getInt(BpProfile.FUNCTION_MEMORY_GROUP_BP5S);
                    int maxMemoryCapacity = info.getInt(BpProfile.FUNCTION_MAX_MEMORY_CAPACITY_BP5S);
                    boolean haveAngleSetFlg = info.getBoolean(BpProfile.FUNCTION_HAVE_ANGLE_SETTING_BP5S);    //是否带手腕角度设置
                    boolean mutableUploadFlg = info.getBoolean(BpProfile.FUNCTION_IS_MULTI_UPLOAD_BP5S);   //一次是否上传多组记忆
                    boolean selfUpdateFlg = info.getBoolean(BpProfile.FUNCTION_IS_SUPPORT_UPDATE_BP5S);  //是否支持自升级

                    String result = "lastOperationStatus = " + lastOperationStatus + "\n" +
                            "upAirMeasureFlg = " + upAirMeasureFlg + "\n" +
                            "armMeasureFlg = " + armMeasureFlg + "\n" +
                            "haveAngleSensorFlg = " + haveAngleSensorFlg + "\n" +
                            "haveRepeatedlyMeasureFlg = " + haveRepeatedlyMeasureFlg + "\n" +
                            "haveOfflineFlg = " + haveOfflineFlg + "\n" +
                            "haveHSDFlg = " + haveHSDFlg + "\n" +
                            "memoryGroup = " + memoryGroup + "\n" +
                            "maxMemoryCapacity = " + maxMemoryCapacity + "\n" +
                            "haveAngleSetFlg = " + haveAngleSetFlg + "\n" +
                            "mutableUploadFlg = " + mutableUploadFlg + "\n" +
                            "selfUpdateFlg = " + selfUpdateFlg + "\n";
                    Log.i(TAG, "functionInfo = " + result);

                    Message msg = new Message();
                    msg.what = HANDLER_MESSAGE;
                    msg.obj = "functionInfo = " + result;
                    myHandler.sendMessage(msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (action.equals(BpProfile.ACTION_MEASURE_STATUS_BP5S)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = message;
                myHandler.sendMessage(msg);
            } else if (action.equals(BpProfile.ACTION_ONLINE_RESULT_BP5S)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = message;
                myHandler.sendMessage(msg);
            } else if (action.equals(BpProfile.ACTION_ERROR_BP5S)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = message;
                myHandler.sendMessage(msg);
            } else if (action.equals(BpProfile.ACTION_HISTORICAL_NUM_BP5S)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = message;
                myHandler.sendMessage(msg);
            } else if (action.equals(BpProfile.ACTION_HISTORICAL_DATA_BP5S)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = message;
                myHandler.sendMessage(msg);
            } else if (action.equals(BpProfile.ACTION_SET_REPEATEDLY_MEASURE_SETTING_SUCCESS_BP5S)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = "set repeatedly measure setting successfully!";
                myHandler.sendMessage(msg);
            } else if (action.equals(BpProfile.ACTION_DELETE_REPEATEDLY_MEASURE_SETTING_SUCCESS_BP5S)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = "delete repeatedly measure setting successfully!";
                myHandler.sendMessage(msg);
            } else {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = message;
                myHandler.sendMessage(msg);
            }
        }
    };


    private static final int HANDLER_MESSAGE = 101;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_MESSAGE:
                    tv_return.setText((String) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iHealthDevicesManager.getInstance().unRegisterClientCallback(clientCallbackId);
        bp5sControl.destroy();
    }


}
