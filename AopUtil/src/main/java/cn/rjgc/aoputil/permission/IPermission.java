package cn.rjgc.aoputil.permission;

public interface IPermission {
    //同意权限申请
    void onPermissionGranted();

    //拒绝权限并且选中不再提示
    void onPermissionDenied(int requestCode);

    //取消(拒绝)权限申请
    void onPermissionCancel(int requestCode);

}
