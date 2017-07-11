LOCAL_PATH := $(call my-dir)

# prepare libX
include $(CLEAR_VARS)
TARGET_ARCH_ABI := armeabi-v7a
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_MODULE    := avcodec
LOCAL_SRC_FILES := libavcodec.a
include $(PREBUILT_STATIC_LIBRARY)

# prepare libX
include $(CLEAR_VARS)
TARGET_ARCH_ABI := armeabi-v7a
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_MODULE    := avdevice
LOCAL_SRC_FILES := libavdevice.a
include $(PREBUILT_STATIC_LIBRARY)

# prepare libX
include $(CLEAR_VARS)
TARGET_ARCH_ABI := armeabi-v7a
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_MODULE    := avfilter
LOCAL_SRC_FILES := libavfilter.a
include $(PREBUILT_STATIC_LIBRARY)

# prepare libX
include $(CLEAR_VARS)
TARGET_ARCH_ABI := armeabi-v7a
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_MODULE    := avformat
LOCAL_SRC_FILES := libavformat.a
include $(PREBUILT_STATIC_LIBRARY)

# prepare libX
include $(CLEAR_VARS)
TARGET_ARCH_ABI := armeabi-v7a
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_MODULE    := avutil
LOCAL_SRC_FILES := libavutil.a
include $(PREBUILT_STATIC_LIBRARY)

# prepare libX
include $(CLEAR_VARS)
TARGET_ARCH_ABI := armeabi-v7a
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_MODULE    := swresample
LOCAL_SRC_FILES := libswresample.a
include $(PREBUILT_STATIC_LIBRARY)

# prepare libX
include $(CLEAR_VARS)
TARGET_ARCH_ABI := armeabi-v7a
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_MODULE    := swscale
LOCAL_SRC_FILES := libswscale.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)

TARGET_ARCH_ABI := armeabi-v7a

LOCAL_MODULE     := live_jni
LOCAL_SRC_FILES  := live_jni.c
LOCAL_C_INCLUDES += $(LOCAL_PATH)/include
LOCAL_CFLAGS     := -D__STDC_CONSTANT_MACROS -Wno-sign-compare -Wno-switch -Wno-pointer-sign -DHAVE_NEON=1 -mfpu=neon -mfloat-abi=softfp -fPIC -DANDROID

LOCAL_STATIC_LIBRARIES := avdevice avfilter avformat avcodec swresample swscale avutil
LOCAL_LDLIBS     := -L$(NDK_ROOT)/platforms/$(APP_PLATFORM)/arch-arm/usr/lib -L$(LOCAL_PATH) -llog -ljnigraphics -lz -ldl


include $(BUILD_SHARED_LIBRARY)