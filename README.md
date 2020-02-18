# MP3LameAndroidTest

## 说明

由于项目中原有使用的libmp3lame.so缺少64位的，而这个库的源码相比于原版的lame库是有区别的，还增加了几个自定义的封装方法提供给JNI层。

既然如此，想要64位库，只能自己重新编译，并且重新定义JNI类了。

## aar库的编译和使用

mp3lame这个module的输出产物是aar，其中包含了so库和JNI类。
app这个module中MainActivity里展示了使用方法。

## 工程整理过程介绍

### lame源码

https://sourceforge.net/projects/lame/

### 源码处理

1. 准备一个AndroidStudio的Android工程，用于编译。（其实也可以不用AndroidStudio，ndk-build命令也可）。

2. 将源码包解压缩，将其中的libmp3lame目录拷贝到我们jni目录中（src/main/jni），进行如下操作：

- 注释或删除 fft.c 文件的 47 行的 include “vector/lame_intrin.h”
- 修改 set_get.h 文件的 24 行的 #include “lame.h” （原始的是#include <lame.h>）
- 将 util.h 文件的 570 行的”extern ieee754_float32_t fast_log2(ieee754_float32_t x);” 替换为 “extern float fast_log2(float x);”
- 移除i386目录，以及libmp3lame目录中除了.c和.h之外的其他无用文件。

### 创建JNI类

我定义了JNI类为com.wsgh.androidutils.mp3lame.MP3LameUtils，其中定义了三个native方法名。然后利用javah生成对应的头文件com_wsgh_androidutils_mp3lame_MP3LameUtils.h。

### 引入和修改自定义的wrapper.c

1. 此文件并不是必须的，仅仅是因为lame库中的原生接口比较多，为了使用方便，在c层面先做了方法的封装，合并了很多方法，这样就只需要提供合并后的三个方法给JNI接口就可以了，调用起来更加方便。所以才有了这个c文件。

2. 这个wrapper.c文件，我看网上流传的都是三星的一个版本。我将其改名为jni_wrapper.c，并修改了其中的方法名，以适配上面创建的JNI类（方法名对应上，这就是上面头文件中方法的实现）。

### 编写Android.mk

主要是增加了如下的一个赋值，解决一个编译问题：

```
LOCAL_CFLAGS = -DSTDC_HEADERS
```

