LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := appm
LOCAL_SRC_FILES := Model.cpp PPMd.cpp

include $(BUILD_SHARED_LIBRARY)