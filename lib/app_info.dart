class AppInfo {
  String? name;
  String? packageName;

  AppInfo(
      this.name,
      this.packageName,
      );

  factory AppInfo.create(dynamic data) {
    return AppInfo(
      data["name"],
      data["package_name"],
    );
  }
}