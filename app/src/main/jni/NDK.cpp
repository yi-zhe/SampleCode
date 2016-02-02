//
// Created by Liuyang on 2016/1/29.
//
#include <android/log.h>
#include "com_liuyang_code_controllers_NDK.h"

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "TAG", __VA_ARGS__)

jstring
Java_com_liuyang_code_controllers_NDK_stringFromC(JNIEnv *env, jobject obj) {
    LOGE("Hello Log");
    return env->NewStringUTF("hello world");
}
