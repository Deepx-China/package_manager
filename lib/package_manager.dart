
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:package_manager/app_info.dart';

class PackageManager {
  static const MethodChannel _channel = MethodChannel('package_manager');

  static Future<List<AppInfo>> getApps() async {
    List<dynamic> apps = await _channel.invokeMethod('getApps');
    List<AppInfo> appInfoList = apps.map((app) => AppInfo.create(app)).toList();
    return appInfoList;
  }

  static Future<bool> getApp(String packageName) async {
    return await _channel.invokeMethod(
        'getApp',
      {"package_name": packageName}
    );
  }
}
