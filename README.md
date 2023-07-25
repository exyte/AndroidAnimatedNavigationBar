<a href="https://exyte.com/"><picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/exyte/media/master/common/header-dark.png"><img src="https://raw.githubusercontent.com/exyte/media/master/common/header-light.png"></picture></a>

<a href="https://exyte.com/"><picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/exyte/media/master/common/our-site-dark.png" width="80" height="16"><img src="https://raw.githubusercontent.com/exyte/media/master/common/our-site-light.png" width="80" height="16"></picture></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://twitter.com/exyteHQ"><picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/exyte/media/master/common/twitter-dark.png" width="74" height="16"><img src="https://raw.githubusercontent.com/exyte/media/master/common/twitter-light.png" width="74" height="16">
</picture></a> <a href="https://exyte.com/contacts"><picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/exyte/media/master/common/get-in-touch-dark.png" width="128" height="24" align="right"><img src="https://raw.githubusercontent.com/exyte/media/master/common/get-in-touch-light.png" width="128" height="24" align="right"></picture></a>

![demo](https://user-images.githubusercontent.com/57913130/232720350-c929208f-81f2-4f95-8133-d5e8ee2102b4.gif)


<p><h1 align="left">Animated Navigation Bar</h1></p>
<p><h4>AnimatedNavigationBar is a navigation bar with a number of preset animations written in Jetpack Compose</h4></p>
 <a href="https://exyte.com/blog/animated-navigation-bar">Read Article »</a>
</br></br>

[![Licence](https://img.shields.io/github/license/exyte/AndroidAnimatedNavigationBar)](https://github.com/exyte/AndroidAnimatedNavigationBar/blob/master/LICENSE)
[![API](https://img.shields.io/badge/API%20-21%2B-brightgreen)](https://github.com/exyte/AndroidAnimatedNavigationBar)
[![Maven-Central](https://img.shields.io/maven-central/v/com.exyte/animated-navigation-bar)](https://central.sonatype.com/artifact/com.exyte/animated-navigation-bar/1.0.0/overview)

## Usage
1. Remember Int to store the current selection
```kotlin
var selectedIndex by remember { mutableStateOf(0) }
```
2. Pass your buttons to the AnimatedNavigationBar
```kotlin

AnimatedNavigationBar(selectedIndex = selectedIndex) {
   Button1()
   Button2()
   Button3()
}
```


### Required parameters
`selectedIndex` - binding to the current index    
`content` - buttons to display in the tabbar 

### Additional parameters

`barColor` - Color of the navigation bar itself   
`ballColor` - Ball indicator color   
`cornerRadius` - The corner radius to all corners applied to the navigation bar         
`ballAnimation` - Ball animation with the animation curve, default value `Parabolic(tween(300))`
- `Parabolic`  - Jump to the selected button following a parabolic arc    
- `Teleport` - Disappear and quickly re-appear above selected tab
- `Straight` - Slide to the selected tab   
You can build your own animation, just implement the `BallAnimation` interface.

`indentAnimation` - Indent animation with the animation curve, default value is `Height(tween(300))`    
- `Height`  -  Disappear by decreasing in height and quickly re-appear by increasing in height above the selected tab 
- `StraightIndent` - Slide to the selected tab   
You can also build your own animation, just implement the `IndentAnimation` interface.

### Built-in animatable tab buttons
This library has two built-in button types you can use out-of-the-box:
`DropletButton` and `WiggleButton`, and a super custom `ColorButton` type in the Example project. Please feel free to use them in your projects or build your own buttons.

## Download

Add the dependency in `build.gradle` file:
```gradle
dependencies {
    implementation("com.exyte:animated-navigation-bar:1.0.0")
}
```

## Acknowledgements

Many thanks to [Yeasin Arafat](https://dribbble.com/shots/14883627-Tab-Bar-Animation) for their beautiful original work that we recreated with JetpackCompose.

