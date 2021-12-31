package com.zfsw.plugin.package_manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.JSONUtil;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** PackageManagerPlugin */
public class PackageManagerPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "package_manager");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "getApps":
        result.success(getApps());
        break;
      case "getApp":
        result.success(getApp(call.argument("package_name")));
        break;
      default:
        result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private List<Map<String, String>> getApps() {
    List<ApplicationInfo> allApps = context.getPackageManager().getInstalledApplications(0);
    List<Map<String, String>> data = new ArrayList<>();
    for (ApplicationInfo app : allApps) {
      Map<String, String> appInfo = new HashMap<>();
      appInfo.put("name", app.loadLabel(context.getPackageManager()).toString());
      appInfo.put("package_name", app.packageName);
      data.add(appInfo);
    }
    return data;
  }

  private boolean getApp(String packageName) {
    List<ApplicationInfo> allApps = context.getPackageManager().getInstalledApplications(0);
    for (ApplicationInfo app : allApps) {
      if (app.packageName.equals(packageName)) {
        return true;
      }
    }
    return false;
  }

}
