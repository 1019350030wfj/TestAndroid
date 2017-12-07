package com.jayden.versionopo.multimedia.audio.wav;

import android.util.Log;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 读wav文件
 * 打开文件、读文件头、读文件数据、关闭
 * Created by Administrator on 2017/12/7.
 */

public class WavFileReader {

    private static final String TAG = WavFileReader.class.getSimpleName();
    private DataInputStream mDataInputStream;
    private WavFileHeader mWavFileHeader;

    public boolean openFile(String filePath) throws IOException {
        if (mDataInputStream != null){
            closeFile();
        }
        mDataInputStream = new DataInputStream(new FileInputStream(filePath));
        return readHeader();
    }

    private boolean readHeader() {
        if (mDataInputStream == null) {
            return false;
        }

        WavFileHeader header = new WavFileHeader();

        byte[] intValue = new byte[4];
        byte[] shortValue = new byte[2];

        try {
            //4
            header.mChunkID = "" + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte();
            Log.d(TAG, "Read file chunkID:" + header.mChunkID);

            //4
            mDataInputStream.read(intValue);
            header.mChunkSize = byteArrayToInt(intValue);
            Log.d(TAG, "Read file chunkSize:" + header.mChunkSize);

            //4
            header.mFormat = "" + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte();
            Log.d(TAG, "Read file format:" + header.mFormat);

            //4
            header.mSubChunk1ID = "" + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte();
            Log.d(TAG, "Read fmt chunkID:" + header.mSubChunk1ID);

            //4
            mDataInputStream.read(intValue);
            header.mSubChunk1Size = byteArrayToInt(intValue);
            Log.d(TAG, "Read fmt chunkSize:" + header.mSubChunk1Size);

            //2
            mDataInputStream.read(shortValue);
            header.mAudioFormat = byteArrayToShort(shortValue);
            Log.d(TAG, "Read audioFormat:" + header.mAudioFormat);

            //2
            mDataInputStream.read(shortValue);
            header.mNumChannel = byteArrayToShort(shortValue);
            Log.d(TAG, "Read channel number:" + header.mNumChannel);

            //4
            mDataInputStream.read(intValue);
            header.mSampleRate = byteArrayToInt(intValue);
            Log.d(TAG, "Read samplerate:" + header.mSampleRate);

            //4
            mDataInputStream.read(intValue);
            header.mByteRate = byteArrayToInt(intValue);
            Log.d(TAG, "Read byterate:" + header.mByteRate);

            //2
            mDataInputStream.read(shortValue);
            header.mBlockAlign = byteArrayToShort(shortValue);
            Log.d(TAG, "Read blockalign:" + header.mBlockAlign);

            //2
            mDataInputStream.read(shortValue);
            header.mBitsPerSample = byteArrayToShort(shortValue);
            Log.d(TAG, "Read bitspersample:" + header.mBitsPerSample);

            //4
            header.mSubChunk2ID = "" + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte() + (char) mDataInputStream.readByte();
            Log.d(TAG, "Read data chunkID:" + header.mSubChunk2ID);

            //4
            mDataInputStream.read(intValue);
            header.mSubChunk2Size = byteArrayToInt(intValue);
            Log.d(TAG, "Read data chunkSize:" + header.mSubChunk2Size);

            Log.d(TAG, "Read wav file success !");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        mWavFileHeader = header;

        return true;
    }

    public int readData(byte[] buffer, int offset, int count){
        if (mDataInputStream == null || mWavFileHeader == null){
            return -1;
        }
        try {
            int nbytes = mDataInputStream.read(buffer, offset, count);
            if (nbytes == -1){
                return 0;
            }
            return nbytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public void closeFile() throws IOException {
        if (mDataInputStream != null){
            mDataInputStream.close();
            mDataInputStream = null;
        }
    }


    private static short byteArrayToShort(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    private static int byteArrayToInt(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
}
