 **云顶S10强音争霸混音器** 

使用 Kotlin Multiplatform 开发的 Android 项目，后期会考虑编译桌面端。

使用 Jetpack Compose 组件库，界面为 Material 3 设计语言。

使用 Google Oboe 高性能 Android 音频方案，多音轨播放真正达到零延迟，并使用 kissfft 计算频谱数据，实现音频可视化效果。

开发语言：
- 界面：Kotlin
- 播放器逻辑封装：Java
- 播放器底层实现：C++

![输入图片说明](image/Screenshot_20250915_191509.png)

运行代码：
1. 拉取仓库
2. 下载LFS
3. 导入 Android Studio
4. Gradle sync
5. 运行！

注：Android Studio 需要安装 NDK 与 CMake 4.1.0

非常简单且普通的 Android 项目
