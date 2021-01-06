本项目基于AspectJ封装了一些常用的AOP工具，目前已封装功能如下：

**1.防抖动**

**2.权限请求**

本权限请求框架支持在Activity、Fragment以外其他的地方申请权限，比如Service、普通类



**本库会长期维护更新**

### 使用方式：

##### 插件引用

1. 在module的build.gradle添加如下gradle依赖

   ```java
   implementation 'com.github.Don-Lee:AopDemo:1.0.2'
   ```

2. 因为本库基于沪江的AspectJx开发，因此要配合[aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx)使用

   - 在项目根目录的build.gradle里依赖**AspectJX**

      ```groovy
      dependencies {
              classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10'
	      classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'
      }
      ```
      
   - 在项目根目录的build.gradle里添加jitpack依赖库  
   
      ```groovy
      allprojects {
		   repositories {
			   ...
			   maven { url 'https://jitpack.io' }
		   }
	   }
      ```

   - 在app项目的build.gradle里应用插件

     ```groovy
     apply plugin: 'android-aspectjx'
     //或者这样也可以
     apply plugin: 'com.hujiang.android-aspectjx'
     ```

3. 因为权限申请部分用到了反射，因此如果你项目中使用了代码混淆，需要在混淆配置文件中添加如下代码

   ```java
   -keepclasseswithmembers class * {
       @cn.rjgc.aoputil.permission.annotation.DeniedPermission <methods>;
   }
   -keepclasseswithmembers class * {
       @cn.rjgc.aoputil.permission.annotation.CancelPermission <methods>;
   }
   ```

   

##### 示例

1. 防抖动

   直接使用@AntiShake注解标识方法即可

   ```java
   	//防抖动测试
       @AntiShake
       public void antiShake(View view) {
           Log.e(TAG, "按钮被点击了" );
       }
   ```



2. 权限请求

   @NeedPermission修饰的方法是表示用户权限请求成功后所执行的策略；

   value代表需要申请的权限，是一个String[]数组；requestCode是请求码，是为了区别开同一个Activity中有多个不同的权限请求，默认是0;

   @CancelPermission修饰的方法表示用户拒绝权限所执行的策略

   @DeniedPermission修饰的方法表示用户拒绝权限并勾选了不在提示按钮所执行的策略

   ```java
   //权限申请
       @NeedPermission(value = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, requestCode = 1)
       public void requestPermission(View view) {
           Log.e(TAG, "requestPermission: ");
       }
   
   //取消权限
       @CancelPermission
       public void permissionCancel(int requestCode) {
           Log.e(TAG, "permissionCancel: " + requestCode);
       }
   //拒绝权限
       @DeniedPermission
       public void permissionDenied(int requestCode) {
           showMissingPermissionDialog();//跳转到设置界面让用户手动授权
       }
   ```

