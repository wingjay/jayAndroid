在android中添加jni。

- 在 app/src/main目录里面创建jni目录

- 在jni目录里创建 hello-jni.c，内部方法的名字是 Java_com_wingjay_jayandroid_ndkdev_NdkUtil_stringFromJNI，即`Java_包名_方法名`
```c
#include <string.h>
#include <jni.h>

jstring Java_com_wingjay_jayandroid_ndkdev_NdkUtil_stringFromJNI( JNIEnv* env,
                                                  jobject thiz )
{
    return (*env)->NewStringUTF(env, "Hello from JNI ! ");
}
```
- 在jni目录里创建 `Android.mk`
```c
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := hello-jni
LOCAL_SRC_FILES := hello-jni.c
include $(BUILD_SHARED_LIBRARY)
```
- 在jni目录里创建 `Application.mk`
```c
APP_ABI := armeabi armeabi-v7a
```
- 在jni目录下执行 `ndk-build`。下载自 `https://developer.android.com/ndk/downloads/index.html`

- 生成的 libhello-jni.so 文件在 main/libs下面，copy到 main/jnilibs 对应cpu架构的目录里。

- 回到java中。在刚刚 hello-jni.c 方法对应的包下面 load 对应的库 添加 native 方法
```java
class NdkUtil {
	static {
		System.loadLibrary("hello-jni");
	}	
	public static native String stringFromJNI();
}
```
