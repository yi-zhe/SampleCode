//
// Created by Liuyang on 2016/1/29.
//

#include "com_liuyang_code_controllers_NDK.h"

jstring
Java_com_liuyang_code_controllers_NDK_stringFromC(JNIEnv *env, jobject obj) {
    return (*(*env)).NewStringUTF(env, "hello world");
}
