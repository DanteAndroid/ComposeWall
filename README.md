# ComposeWall

[中文版](README_CN.md)
Compose wall is a wallpaper app built fully
with [Jetpack Compose](https://developer.android.com/jetpack/compose).

### Download

<a href="https://play.google.com/store/apps/details?id=com.v2ray.ang">
<img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="165" height="64" />
</a>

[Releases](https://github.com/DanteAndroid/ComposeWall/releases/)

### Screenshots

<img src="Screenshots/0.png" width="200"> <img src="Screenshots/1.png" width="200"> <img src="Screenshots/2.png" width="200">

### Who could learn this repo?

Both Android beginners and experienced developers could learn this repo.

### Why choose this repo?

You should also checkout [Compose samples](https://github.com/android/compose-samples), but IMO they
are more difficult to learn than this repo. If you have any questions, please create an issue, I
will help to the best of my ability.

### Structure of this repo?

- `data` is for model class and parsers used to parse image source url from web pages.
- `net` is for net request apis and services.
- `component` is the encapsulation of Compose ui components.
- `theme` is for colors, font styles, and themes.
- `AppMenu` defines all menus of app.
- `ComposeNavGraph` defines destinations of app (like Jetpack navigation).
- `ComposeDestinations` is holding navigating methods for different status.

