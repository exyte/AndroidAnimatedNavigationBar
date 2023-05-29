![header](https://user-images.githubusercontent.com/9447630/217482844-e5f420cf-7aa2-4684-8238-54064bbb23ba.png)
![demo](https://user-images.githubusercontent.com/57913130/232720350-c929208f-81f2-4f95-8133-d5e8ee2102b4.gif)


<p><h1 align="left">Animated Navigation Bar</h1></p>
<p><h4>AnimatedNavigationBar is a navigation bar with a number of preset animations written in Jetpack Compose</h4></p>
 <a href="https://exyte.com/blog/animated-navigation-bar">Read Article »</a>

___

<p> We are a development agency building
 <a href="https://clutch.co/profile/exyte#review-731233?utm_medium=referral&utm_source=github.com&utm_campaign=phenomenal_to_clutch">phenomenal</a> apps.</p>

</br>

<a href="https://exyte.com/contacts"><img src="https://i.imgur.com/vGjsQPt.png" width="134" height="34"></a> <a href="https://twitter.com/exyteHQ"><img src="https://i.imgur.com/DngwSn1.png" width="165" height="34"></a>
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

