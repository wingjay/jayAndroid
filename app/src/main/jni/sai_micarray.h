/*********************************************************************
* Copyright (C) 2016-2017, SoundAI, Inc.
**********************************************************************/
/*
 *===================================================================
 *  SAI Microphone Array Assemble Processing Module - Little Endian
 *===================================================================
 */

#ifndef SAI_MICARRAY_H
#define SAI_MICARRAY_H

#if defined(__cplusplus)
extern "C"
{
#endif

/****************************************************************************
 * SaiMicaInit(...)
 *
 *
 * Input:
 *     - path          : 配置文件路径
 *
 * Return:
 *       模块函数句柄, NULL IS ERROR
 *
 */
void *SaiMicaInit(char *path);

/****************************************************************************
 * SaiMicaStart(...)
 *
 *
 * Input:
 *     - handle            :模块函数句柄
 *
 * Return:
 *        NULL
 *
 */
void SaiMicaStart(void *handle);

/****************************************************************************
 * SaiMicaStop(...)
 *
 *
 * Input:
 *     - handle            :模块函数句柄
 *
 * Return:
 *        NULL
 *
 */
void SaiMicaStop(void *handle);

/****************************************************************************
 * SaiMicaSetAngle(...)
 *
 *
 * Input:
 *     - handle         : 模块函数句柄
 *     - value          : 配置角度，训练用
 *
 * Return:
 *       模块函数句柄, NULL
 *
 */
void SaiMicaSetAngle(void *handle,float value);

/****************************************************************************
 * SaiMicaGetAngle(...)
 *
 *
 * Input:
 *     - handle             :模块函数句柄
 *     - timelenms          :计算数据长度（毫秒）
 *     - delayms            :延时时间估计（毫秒）
 *
 * Return:
 *        实际唤醒角度
 */
float SaiMicaGetAngle(void *handle,float timelenms,float delayms);

/****************************************************************************
 * SaiMicaProcess(...)
 *
 *
 * Input:
 *     - handle             :模块函数句柄
 *     - wake_up            :0未唤醒，1已唤醒
 *     - out_asr            :输出ASR数据缓冲
 *     - out_vad            :输出VAD数据缓冲
 *     - outlen             :输出数据长度  1ch 16bit 16k
 *
 * Return:
 *        实际样本数量
 */
int SaiMicaProcess(void *handle,int wake_up, short** out_asr, short** out_vad, int* outlen);

/****************************************************************************
 * SaiGetVersion(...)
 *
 *
 * Input:
 *     - NULL
 *
 * Return:
 *        String
 *
 */
void *SaiGetVersion();

/****************************************************************************
 * SaiMicaReset(...)
 *
 *
 * Input:
 *     - handle            :模块函数句柄
 *
 * Return:
 *        NULL
 *
 */
void SaiMicaReset(void *handle);

/****************************************************************************
 * SaiMicaRelease(...)
 *
 *
 * Input:
 *     - handle            :模块函数句柄
 *
 * Return:
 *        0 Success -1 Fail
 *
 */
int SaiMicaRelease(void *handle);

#if defined(__cplusplus)
}
#endif


#endif
