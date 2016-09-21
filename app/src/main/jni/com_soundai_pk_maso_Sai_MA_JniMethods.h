/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_soundai_pk_maso_Sai_MA_JniMethods */

#ifndef _Included_com_soundai_pk_maso_Sai_MA_JniMethods
#define _Included_com_soundai_pk_maso_Sai_MA_JniMethods
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaInit
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaInit
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaProcess
 * Signature: (I[B[BZ[BI)[I
 */
JNIEXPORT jintArray JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaProcess
  (JNIEnv *, jobject, jint, jbyteArray, jbyteArray, jboolean, jbyteArray);

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaGetAngle
 * Signature: (FF)F
 */
JNIEXPORT jfloat JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaGetAngle
  (JNIEnv *, jobject, jfloat, jfloat);

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaReset
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaReset
  (JNIEnv *, jobject);

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaRelease
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaRelease
  (JNIEnv *, jobject);

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiGetVersion
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiGetVersion
  (JNIEnv *, jobject);

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    getDevicePermission
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_getDevicePermission
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    getVoiceLevel
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_getVoiceLevel
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
