cmake_minimum_required(VERSION 4.1.0)

project("oboe_engine")

add_library(
        oboe_engine
        SHARED
        kissfft/kiss_fft.c
        kissfft/kiss_fftr.c
        AudioSpectrumAnalyzer.cpp
        OboeEngine.cpp
        jni-bridge.cpp
)

find_library(
        log-lib
        log)

find_package(oboe REQUIRED CONFIG)

target_link_libraries(
        oboe_engine
        oboe::oboe
        ${log-lib}
        android
)

# 16kb对齐
target_link_options(${CMAKE_PROJECT_NAME} PRIVATE
        "-Wl,-z,max-page-size=16384"
)