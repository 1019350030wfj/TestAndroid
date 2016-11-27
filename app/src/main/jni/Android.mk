LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_LDLIBS    += -llog
LOCAL_MODULE := hello
LOCAL_SRC_FILES := com_jayden_testandroid_ndk_HelloNDKActivity.cpp
include $(BUILD_SHARED_LIBRARY)