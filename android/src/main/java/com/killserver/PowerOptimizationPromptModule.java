
package com.killserver;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class PowerOptimizationPromptModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public PowerOptimizationPromptModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "PowerOptimizationPrompt";
    }

    private static final Intent[] POWERMANAGER_INTENTS = {
            new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")),
            new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager")),
            new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")),
            new Intent().setComponent(new ComponentName("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity")),
            new Intent().setComponent(new ComponentName("com.htc.pitroad", "com.htc.pitroad.landingpage.activity.LandingPageActivity")),
            new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.MainActivity"))
    };

    @ReactMethod
    public void alertIfCompatibleDevice(String title, String message, String dontShowAgainText, String positiveText, String negativeText) {

        boolean noRelevantPackages = true;
        final String skipDialogPreference = "skipDialog";

        SharedPreferences settings = getCurrentActivity().getSharedPreferences("ProtectedApps", Context.MODE_PRIVATE);
        boolean skipMessage = settings.getBoolean(skipDialogPreference, false);

        if (!skipMessage) {
            final SharedPreferences.Editor editor = settings.edit();
            for (final Intent intent : POWERMANAGER_INTENTS) {
                if (getCurrentActivity().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    //This device is relevant and a dialog should be shown to the user
                    noRelevantPackages = false;

                    final CheckBox dontShowAgain = new CheckBox(getCurrentActivity());
                    dontShowAgain.setText(dontShowAgainText);
                    dontShowAgain.setLeft(20);
                    dontShowAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            editor.putBoolean(skipDialogPreference, isChecked);
                            editor.apply();
                        }
                    });

                    final RelativeLayout layout = new RelativeLayout(getCurrentActivity());
                    layout.setPadding(50,50,0,0);
                    layout.addView(dontShowAgain);


                    new AlertDialog.Builder(getCurrentActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(title)
                            .setMessage(message)
                            .setView(layout)
                            .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    openPowerOptimizationActivity(intent);
                                }
                            })
                            .setNegativeButton(negativeText, null)
                            .show();
                }
            }
            if(noRelevantPackages) {
                // Save "do not show again" flag automatically for irrelevant devices to prevent unnecessary checks
                editor.putBoolean(skipDialogPreference, true);
                editor.apply();
            }
        }
    }

    private void openPowerOptimizationActivity(Intent intent){
        if (getCurrentActivity().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            getCurrentActivity().startActivity(intent);
        }
    }
}