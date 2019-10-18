# ActivityModule
# 使用说明：
## 第一步：
### 在 Project 的 build.gradle 文件中 添加仓库支持
allprojects {
    repositories {
        ...
        maven { url "https://dl.bintray.com/itzheng/andMaven/" }
        ...     
    }
} 
## 第二步：
### 在需要引用的项目的 build.gradle 添加依赖
dependencies {
        ...
        compile 'org.itzheng.android:activity:0.0.5'
        ...
}

