# ComposeWall

[English version](README.md)
ComposeWall 是一款由 [Jetpack Compose](https://developer.android.com/jetpack/compose) 打造的应用。

### 下载

<a href="https://play.google.com/store/apps/details?id=com.v2ray.ang">
<img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="165" height="64" />
</a>
[Releases](https://github.com/DanteAndroid/ComposeWall/releases/)

### 截图

<img src="Screenshots/0.png" width="200"> <img src="Screenshots/1.png" width="200"> <img src="Screenshots/2.png" width="200">

### 谁适合学习此项目？

Android 初学者和资深开发都可以学习此项目。

### 为什么要选择这个项目？

你还可以看看 [Compose samples](https://github.com/android/compose-samples)，
但我觉得会比较难一些。如有任何疑问，请提issue，我会尽可能地解答~

### 项目的架构？

- `data` 负责模型类和用来解析网页图源的解析类
- `net` 负责API接口和网络请求服务
- `component` 里面是Compose UI组件的封装
- `theme` 里面包括颜色、字体样式和主题
- `AppMenu` 定义App的主菜单
- `ComposeNavGraph` 定义App的导航 (类似于 Jetpack navigation)
- `ComposeDestinations` 负责不同状态下的导航方法

