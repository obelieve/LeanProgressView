# ProgressView
- LeanProgressView 斜度进度条

![image](https://github.com/obelieve/ProgressView/blob/master/screenshots/screenshot.png)


### Step 1. Add the JitPack repository to your build file
```
...
allprojects {
    repositories {
        ...
        maven(){url 'https://jitpack.io'}
    }
}
...
```
### Step 2. Add the dependency
```
	dependencies {
	        implementation 'com.github.obelieve:ProgressView:1.0.0'
	}
```

### Step 3. Use
```xml
    <com.obelieve.progressview.LeanProgressView
        android:id="@+id/lpv_content"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
### 具体参数
```xml
app:leftProgress="50" //左边进度
app:rightProgress="50"//右边进度
app:leftRightGap="10dp"//中间空白间距
app:leftProgressColor="#FF5252" //左边进度条颜色
app:rightProgressColor="#308BFE"//右边进度条颜色
```
