# module/application介绍
## usage
```
git clone --recursive https://github.com/tainzhi/AndroidPlayground
```

## module-customview 自定义View
- 环形的进度条
- 图片指示器进度条,可动态更改图片指示器
- 验证码输入框
- 波浪形态的圆形进度条
- 水平或者竖直滑动选择器(String, Bitmap)等等

## module-android-api
android控件和api使用demo
- ViewPager2实现的引导页
- TabLayout
- RecyclerView各种用法: Item首尾居中, 缩放, notifyItemChanged(position, payload), Search Filter/Swipe delete
- Handler线程传递消息
- 触摸事件: Single Touch, multi touch

# 组件化使用
因为用了ARouter组件化, 所以开发时, 从`gradle.properties`中关闭组件化`isModule=false`


### 组件化ARouter
在gradle.properties中有组件化开关

不能多Application共存, 查询解决办法

#### 遇到的问题
- 多application替换
- DexArchiveMergerException问题
- 主题冲突
- 在子module中未找到资源

#### 资源冲突怎么解决
- 在`build.gradle`中添加
```groovy
android {
    resourcePrefix "module_common_"
}
```
AndroidStudio不会自动给该模块下的资源添加前缀, 而是会提示报错, 起着约束作用, 需要手动修改

### 组件化方案
##### [Quadrant](https://github.com/gaelmarhic/Quadrant): 原理是通过读取所有module的AndroidManifest.xml生成各个activity的const入口, 然后使用.
- 优点: 使用简单, 直接startActivity即可
- 缺点: 传参不方便

### 组件化使用参考
- [一步步用ARouter实现组件化](https://www.codetd.com/article/11007754)
- [组件化构想以及ARouter的使用分析](https://www.jianshu.com/p/5b318df8b6f6)
- [接口+映射解决多application问题](https://www.jianshu.com/p/b9766b1a9c31)
- [AndroidStudio进行Build时出现DexArchiveMergerException异常的解决办法](https://blog.csdn.net/zgd826237710/article/details/79456202)
- [组件化过程中application的替换规则](https://blog.csdn.net/baidu_31093133/article/details/94633316)
- [组件化实现的多媒体应用](https://github.com/guofudong/KotlinAndroid)
- [组件化实现的喜马拉雅app](https://github.com/TanZhiL/Zhumulangma)
- [杨充: 组件化思考笔记](https://juejin.im/post/5c46e6fb6fb9a049a5713bcc#heading-33)
- [Relax 基于Kotlin语言编写的一套组件化框架,不紧整体组件化、内部也高度组件化🎋你可配置MVP、MVVM的开发模式、也可以配置所需要的业务组件](https://github.com/UCodeUStory/Relax)
- [灵活的组件化路由框架.](https://github.com/chenenyu/Router)

