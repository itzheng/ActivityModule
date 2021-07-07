# ActivityModule
```
 此为平时使用的 SuperActivity 的封装，包含状态栏设置，权限申请，Toast 弹窗，加载进度，软键盘等常用功能。
 本着最少原则，能不用的尽量不要。
 ```
# 使用说明：
## 第一步：
### 在 Project 的 build.gradle 文件中 添加仓库支持
```groovy
allprojects {
    repositories {
        
        maven { url 'https://jitpack.io' }
    }
} 
```
## 第二步：
### 在需要引用的项目的 build.gradle 添加依赖
[see javadoc](https://javadoc.jitpack.io/com/github/itzheng/ActivityModule/latest/javadoc/index.html)
[![](https://jitpack.io/v/itzheng/ActivityModule.svg)](https://jitpack.io/#itzheng/ActivityModule)
```groovy
dependencies {
        
       implementation 'com.github.itzheng:ActivityModule:Tag'
}
```


