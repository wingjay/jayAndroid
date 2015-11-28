#include "com_wingjay_jayandroid_fastblur_FastBlurJni.h"

/*
 * Class:     com_wingjay_jayandroid_fastblur_FastBlurJni
 * Method:    add
 * Signature: (II)I
 */
JNIEXPORT jstring JNICALL Java_com_wingjay_jayandroid_fastblur_FastBlurJni_test
  (JNIEnv *env, jclass clz) {
  	return (*env)->NewStringUTF(env,"hahaha This just a test for Android Studio NDK JNI developer!");
  }

/*
 * Class:     com_wingjay_jayandroid_fastblur_FastBlurJni
 * Method:    add
 * Signature: (II)I
 */
JNIEXPORT jstring JNICALL Java_com_wingjay_jayandroid_fastblur_FastBlurJni_test__I
        (JNIEnv *env, jclass clz, jint i) {
    return (*env)->NewStringUTF(env,"hahaha This just a test for Android Studio NDK JNI developer!");
}