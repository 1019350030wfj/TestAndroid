LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := hello
LOCAL_SRC_FILES := com_jayden_testandroid_ndk_HelloNDKActivity.c
include $(BUILD_SHARED_LIBRARY)