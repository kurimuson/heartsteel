#include <jni.h>
#include <android/asset_manager_jni.h>
#include "OboeEngine.h"
#include "logging_macros.h"

using namespace oboe_engine;

extern "C" JNIEXPORT jlong JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeCreate(
        JNIEnv *env, jobject, jobject assetManagerObj, jstring filePathObj,
        jint sampleRate, jint channels, jint bitDepth, jobject errorCallbackObj) {

    AAssetManager *assetManager = AAssetManager_fromJava(env, assetManagerObj);
    const char *filePath = env->GetStringUTFChars(filePathObj, nullptr);
    if (!assetManager || !filePath) {
        if (filePathObj) env->ReleaseStringUTFChars(filePathObj, filePath);
        return 0;
    }
    // 获取JVM指针
    JavaVM *javaVM;
    env->GetJavaVM(&javaVM);
    // 创建回调的全局引用（避免局部引用失效）
    jobject errorCallbackGlobal = nullptr;
    if (errorCallbackObj != nullptr) {
        errorCallbackGlobal = env->NewGlobalRef(errorCallbackObj);
    }
    // 创建OboeEngine实例，传递JVM和回调引用
    OboeEngine *engine = new OboeEngine(assetManager, filePath,
                                        static_cast<int32_t>(sampleRate),
                                        static_cast<int32_t>(channels),
                                        static_cast<int32_t>(bitDepth),
                                        javaVM, errorCallbackGlobal);

    env->ReleaseStringUTFChars(filePathObj, filePath);
    return reinterpret_cast<jlong>(engine);
}

extern "C" JNIEXPORT void JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativePlay(
        JNIEnv *, jobject,
        jlong engineHandle
) {
    if (engineHandle == 0) return;
    reinterpret_cast<OboeEngine *>(engineHandle)->start();
}

extern "C" JNIEXPORT void JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativePause(
        JNIEnv *, jobject,
        jlong engineHandle
) {
    if (engineHandle == 0) return;
    reinterpret_cast<OboeEngine *>(engineHandle)->pause();
}

extern "C" JNIEXPORT void JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeStop(
        JNIEnv *, jobject,
        jlong engineHandle
) {
    if (engineHandle == 0) return;
    reinterpret_cast<OboeEngine *>(engineHandle)->stop();
}

extern "C" JNIEXPORT void JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeTurnTo(
        JNIEnv *, jobject,
        jlong engineHandle, jlong ms
) {
    if (engineHandle == 0) return;
    reinterpret_cast<OboeEngine *>(engineHandle)->setPositionMs(static_cast<int64_t>(ms));
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeGetCurrentPosition(
        JNIEnv *, jobject,
        jlong engineHandle
) {
    if (engineHandle == 0) return 0;
    return static_cast<jlong>(reinterpret_cast<OboeEngine *>(engineHandle)->getCurrentPositionMs());
}

extern "C" JNIEXPORT void JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeSetVolume(
        JNIEnv *, jobject,
        jlong engineHandle,
        jfloat volume) {
    if (engineHandle == 0) return;
    reinterpret_cast<OboeEngine *>(engineHandle)->setVolume(volume);
}

extern "C" JNIEXPORT void JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeOpenVoiceHighAnalyse(
        JNIEnv *, jobject,
        jlong engineHandle
) {
    if (engineHandle == 0) return;
    reinterpret_cast<OboeEngine *>(engineHandle)->openVoiceHighAnalyse();
}

extern "C" JNIEXPORT void JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeCloseVoiceHighAnalyse(
        JNIEnv *, jobject,
        jlong engineHandle
) {
    if (engineHandle == 0) return;
    reinterpret_cast<OboeEngine *>(engineHandle)->closeVoiceHighAnalyse();
}

// 获取频谱数据（返回byte数组，对应C++的uint16_t向量）
extern "C" JNIEXPORT jshortArray JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeGetCurrentVoiceHigh(
        JNIEnv *env, jobject,
        jlong engineHandle
) {
    if (engineHandle == 0) return nullptr;

    // 获取C++层的频谱数据（uint16_t向量）
    std::vector<uint16_t> voiceHigh =
            reinterpret_cast<OboeEngine *>(engineHandle)->getCurrentVoiceHigh();

    // 转换为Java的short数组（jshort对应Java的short，都是16位）
    jshortArray result = env->NewShortArray(static_cast<jsize>(voiceHigh.size()));
    if (result == nullptr) return nullptr; // 内存分配失败

    // 复制数据到Java数组：uint16_t -> jshort（二进制兼容，直接转换指针）
    env->SetShortArrayRegion(
            result,
            0,
            static_cast<jsize>(voiceHigh.size()),
            reinterpret_cast<const jshort*>(voiceHigh.data())
    );

    return result;
}

extern "C" JNIEXPORT void JNICALL
Java_com_crimson_app_heartsteel_player_core_OboeEngine_nativeDestroy(
        JNIEnv *, jobject,
        jlong engineHandle
) {
    if (engineHandle == 0) return;
    delete reinterpret_cast<OboeEngine *>(engineHandle);
}
