package com.wsgh.androidutils.mp3lame;

/**
 * File Description: 对接libmp3lame库的JNI类
 *
 * Note: 参考自com.pocketdigi.utils.FLameUtils类，由于找不到源码以及对应编译的so库源码，但是需要编译64位库，
 * 故重新编译了Android平台的so库，并新建了JNI类。
 *
 * <p>
 * Created by sandy with Email: sandy1108@163.com at Date: 2020/2/18.
 */
public class MP3LameUtils {

    private int numChannels = 1;
    private int sampleRate = 16000;
    private int bitRate = 96;

    static {
        System.loadLibrary("mp3lame");
    }

    private native void initEncoder(int var1, int var2, int var3, int var4, int var5);

    private native void destroyEncoder();

    private native int encodeFile(String var1, String var2);

    public int getNumChannels() {
        return this.numChannels;
    }

    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getBitRate() {
        return this.bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public MP3LameUtils() {
    }

    public MP3LameUtils(int numChannels, int sampleRate, int bitRate) {
        this.numChannels = numChannels;
        this.sampleRate = sampleRate;
        this.bitRate = bitRate;
    }

    public boolean raw2mp3(String source, String destination) {
        this.initEncoder(this.numChannels, this.sampleRate, this.bitRate, 1, 2);
        int result = this.encodeFile(source, destination);
        this.destroyEncoder();
        return result == 0;
    }
}
