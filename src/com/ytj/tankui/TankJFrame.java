package com.ytj.tankui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TankJFrame extends JFrame {
	TankJPanel tankJPanel = null;

	public TankJFrame() {
		tankJPanel = new TankJPanel();
		this.setSize(1120,770);
		//设置标题
		this.setTitle("Tank大战");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//获取系统分辨率大小
		Dimension dim = toolkit.getScreenSize();
		int dimWid = (int) dim.getWidth();
		int dimHei = (int) dim.getHeight();
		//将窗体居中
		this.setLocation((dimWid-1120)/2, (dimHei-770)/2);
		//退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗体不可变化
		this.setResizable(false);
		
		//将面板添加到窗体中
		this.setContentPane(tankJPanel);
		//设置面板可见
		this.setVisible(true);
	}
}
