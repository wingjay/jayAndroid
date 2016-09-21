//
// Created by baoqiang on 2016/7/15.
//
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <jni.h>
#include <stdlib.h>
#include <Android/log.h>
#include "sai_micarray.h"
#include "license.h"
#include "com_soundai_pk_maso_Sai_MA_JniMethods.h"

#define TAG "Findme!" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

void *handle = NULL;
int current_volume = 0;

jstring Java_com_wingjay_jayandroid_ndkdev_NdkUtil_stringFromJayJni(JNIEnv* env, jobject thiz)
{
    return (*env)->NewStringUTF(env, "Hello from Jay Sai-Ma");
}


/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaInit
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaInit(JNIEnv *env, jobject obj,jstring filePath)
{
    if(!verifyLicenseDate(2017,1,1))
         return NULL;
    LOGI("初始化之前");
    const char* file_path = (*env)->GetStringUTFChars(env,filePath, 0 );
    handle = SaiMicaInit(file_path);
    LOGI("初始化之后");
    (*env)->ReleaseStringUTFChars(env,filePath, file_path );
    //(*env)->ReleaseStringUTFChars(env,fileName, file_name );
    if(handle == NULL)
    {
        LOGI("初始化失败（JNI）");
        return -1;
    }else{
        SaiMicaStart(handle);
        LOGI("初始化成功66");
        return 1;
    }
}

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaProcess
 * Signature: (I[B[BI)I
 */
JNIEXPORT jintArray JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaProcess
(JNIEnv *env, jobject obj, jint wakeup_Status, jbyteArray out_asr, jbyteArray out_vad, jboolean debug, jbyteArray debug_data)
{
    if( !verifyLicenseDate(2017,1,1) ){
        return NULL;
    }
    jintArray jiArray = (*env) -> NewIntArray(env,2);
    int * ji = (*env) -> GetIntArrayElements(env,jiArray,NULL);
    if(handle == NULL)
    {
        ji[0] = 0;
        ji[1] = 0;
    }else{
        char *os=(*env)->GetByteArrayElements(env,out_asr, NULL);
        char *ov=(*env)->GetByteArrayElements(env,out_vad, NULL);
        short *asr;
        short *vad;
        int len;
        //SaiMicaStart(handle);
        LOGI("saimicaprocess方法一");
        LOGI("saimicaprocess方法: %d", wakeup_Status);
        SaiMicaProcess(handle, wakeup_Status, &asr, &vad, &len);
        LOGI("saimicaprocess方法er");
        ji[0] = len;
        if(debug)
        {
            char *db=(*env)->GetByteArrayElements(env,debug_data, NULL);
            short *debug_d;
            int debug_l;

            SaiMicaDebugOut(handle, &debug_d, &debug_l);

            ji[1] = debug_l;
            memcpy(db,(char *)debug_d,debug_l * sizeof(short));
            (*env)->ReleaseByteArrayElements(env,debug_data,db, 0);
        }else
        {
            ji[1] = 0;
        }
        current_volume = getPcmVolume(asr,ji[0]);
        if(ji[0] > 1)
        {
            memcpy(os,(char *)asr,len * sizeof(short));
            memcpy(ov,(char *)vad,len * sizeof(short));
        }else
        {
            SaiMicaReset(handle);
        }
        asr = NULL;
        vad = NULL;
        //LOGI("读取返回的长度 i = %d",retnsample);
        if(out_asr != NULL && os != NULL)
        {
            (*env)->ReleaseByteArrayElements(env,out_asr,os, 0);
        }
        if(out_vad != NULL && ov != NULL)
        {
            (*env)->ReleaseByteArrayElements(env,out_vad,ov, 0);
        }
    }
    (*env) -> ReleaseIntArrayElements(env,jiArray,ji,0);

    return jiArray;
}

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaGetAngle
 * Signature: (I)I
 */
JNIEXPORT jfloat JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaGetAngle(JNIEnv *env, jobject obj, jfloat wake_len, jfloat delayms)
{
    if( !verifyLicenseDate(2017,1,1) )
        return 0.0f;
    jfloat angle = 0.0f;
    if(handle == NULL)
    {
        angle = -1.0f;
    }else
    {
        angle = SaiMicaGetAngle(handle,wake_len,delayms);
    }
    return angle;
}

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaReset
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaReset(JNIEnv *env, jobject obj)
{
    if( !verifyLicenseDate(2017,1,1) )
        return NULL;
    if(handle == NULL)
    {

    }else
    {
        SaiMicaReset(handle);
       // SaiMicaStart(handle);
    }
}

/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiMicaRelease
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiMicaRelease(JNIEnv *env, jobject obj)
{
    if( !verifyLicenseDate(2017,1,1) )
        return NULL;
    int error = 0;
    if(handle == NULL)
    {
        error = 9527;
    }else
    {
        error = SaiMicaRelease(handle);
        SaiMicaStop(handle);
    }
    return error;

}
/*
 * Class:     com_soundai_pk_maso_Sai_MA_JniMethods
 * Method:    SaiGetVersion
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_SaiGetVersion(JNIEnv * env, jobject obj)
{
    if( !verifyLicenseDate(2017,1,1) )
        return NULL;
    return (int)SaiGetVersion();
}

JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_getDevicePermission(JNIEnv * env, jobject obj, jstring deviceName)
{
    int ret = system("su -c \"chmod -R 777 /dev/bus/usb/\"");
    int res = system("su -c \"setenforce 0\"");
    LOGI("res == %d",res);
    if(ret != 0)
    {
        LOGI("jni 修改设备权限失败");
    }else
    {
        LOGI("jni 修改设备权限成功");
    }
   // LOGI("##### ret = %d",ret);
    return ret;
}

JNIEXPORT jint JNICALL Java_com_soundai_pk_1maso_Sai_1MA_1JniMethods_getVoiceLevel(JNIEnv *env, jobject obj)
{
   // LOGI("返回声音大小");
    return current_volume;
}

int getPcmVolume(const short* ptr, size_t size)
{
    LOGI("开始得到声音");
    int ndb = 0;

        short int value;

        int i;
        long double v = 0;
        for(i=0; i<size; i++)
        {
            memcpy(&value, ptr+i, sizeof(short));
            v += value * value;
        }

        //v = v/(size/2);
        v = v/size;
       LOGI("走了v=%d",(int)v);
        if(v != 0) {
            ndb = (int)sqrt(v);
            LOGI("走了if！=");
        }
        else {
        LOGI("走了if=");
            ndb = -96;
        }
        return ndb;
}

