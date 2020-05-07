## 引入第三方库需要注意的点
### BaseRecyclerViewAdapterHelper
[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki) 需要添加
```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

## 组件化ARouter
在gradle.properties中有组件化开关

不能多Application共存, 查询解决办法

## module/application介绍
### module-android-api
android控件和api使用demo
- ViewPager2实现的引导页
- TabLayout
- RecyclerView各种用法: Search Filter/Swipe delete
- Handler线程传递消息

