package com.ytj.tanktools;

import java.applet.AudioClip;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Music {
	public static final String  BGM = "music/111.wav";//背景
	public static final String  fight = "music/4666.wav";
	public static final String  move = "music/move.wav";//移动
	
	
	
	public static void playSound(String path){
		//定义文件输入流
		try {
			FileInputStream fis = new FileInputStream(path);
			//定义音频流
			AudioStream as = new AudioStream(fis);
			//播放声音
			AudioPlayer.player.start(as);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
