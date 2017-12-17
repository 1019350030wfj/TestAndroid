package com.jayden.versionopo.multimedia.audio.wav;

/**
 * 读写wav文件
 * Created by Administrator on 2017/12/1.
 */

public class TestWavFile {
    /**
     * 无论是文字，图像还是声音 都必须以一定的格式组织和存储起来，这样播放器才知道以怎样的方式
     * 去解析这一段数据。 对于原始的图像数据，常见格式有YUV 、 Bitmap， 而对于音频来说，最简单常见的格式就是wav格式了
     *
     * wav格式，与bitmap一样都是微软开发的一种文件格式规范(http://soundfile.sapp.org/doc/WaveFormat/)。 它们有一个相似之处，就是整个文件分为两部分：
     * 第一部分是“文件头”，记录重要的参数信息，对于音频而言，就包括： 采样率、通道数、位宽等等。对于图像而言，就包括：图像宽高、色彩位数等等；
     * 第二部分是“数据块”，即一帧一帧的二进制数据，对于音频而言，就是原始的PCM数据；对于图像而言，就是RGB数据
     *
     * wav文件 实际就是 “文件头” + “音频数据” 因此：
     * 写wav文件 就是先写入wav文件头，然后再继续写入音频二进制数据
     * 读wav文件 其实就是先读wav文件头， 然后再继续读音频二进制数据
     *
     * wav文件头用来记录音频数据总长度的Subchunk2Size    是需要等到你写完所有音频数据才知道到底有多大。
     * 因此可以用RandomAccessFile 类，将文件指针跳到“Subchunk2Size ”字段，改写一下默认值即可
     */
}
