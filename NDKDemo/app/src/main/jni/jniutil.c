//
// Created by MurphySL on 2017/9/18.
//
#include "cn_edu_nuc_androidlab_ndkdemo_JNIUtils.h"

JNIEXPORT jint JNICALL Java_cn_edu_nuc_androidlab_ndkdemo_JNIUtils_add(JNIEnv * env, jclass object, jint i, jint j){
    return i + j;
}
