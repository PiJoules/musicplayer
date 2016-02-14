#!/usr/bin/env sh

ant debug
if [ $? != 0 ]
then
    exit 1
fi
adb install bin/somthing-debug.apk && adb logcat -s "TAG"
