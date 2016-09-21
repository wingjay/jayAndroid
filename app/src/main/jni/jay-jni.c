#include <jni.h>

jstring Java_com_wingjay_jayandroid_ndkdev_NdkUtil_stringFromJayJni(JNIEnv* env, jobject thiz)
{
    return (*env)->NewStringUTF(env, "Hello from Jay jni real");
}