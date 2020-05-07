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

### 遇到的问题
- 多application替换
- DexArchiveMergerException问题
- 主题冲突
- 在子module中未找到资源

### 组件化使用参考
- [一步步用ARouter实现组件化](https://www.codetd.com/article/11007754)
- [组件化构想以及ARouter的使用分析](https://www.jianshu.com/p/5b318df8b6f6)
- [接口+映射解决多application问题](https://www.jianshu.com/p/b9766b1a9c31)
- [AndroidStudio进行Build时出现DexArchiveMergerException异常的解决办法](https://blog.csdn.net/zgd826237710/article/details/79456202)
- [组件化过程中application的替换规则](https://blog.csdn.net/baidu_31093133/article/details/94633316)
- [组件化实现的多媒体应用](https://github.com/guofudong/KotlinAndroid)

## module/application介绍
### module-android-api
android控件和api使用demo
- ViewPager2实现的引导页
- TabLayout
- RecyclerView各种用法: Search Filter/Swipe delete
- Handler线程传递消息

