本项目基于AspectJ封装了一些常用的AOP工具，目前已封装功能如下：

**1.防抖动**

**2.权限请求**



**本库会长期维护更新**

### 使用方式：

##### 插件引用

1. 在module的build.gradle添加如下gradle依赖

   ```java
   implementation 'com.github.Don-Lee:AopDemo:1.0'
   ```

2. 因为本库基于沪江的AspectJx开发，因此要配合[aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx)使用

   - 在项目根目录的build.gradle里依赖**AspectJX**



     ```java
     dependencies {
             classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.8'
     }
     ```



   - 在app项目的build.gradle里应用插件

     ```java
     apply plugin: 'android-aspectjx'
     //或者这样也可以
     apply plugin: 'com.hujiang.android-aspectjx'
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