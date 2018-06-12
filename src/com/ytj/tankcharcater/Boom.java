package com.ytj.tankcharcater;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.ytj.tanktools.TankSetting;

public class Boom extends Character implements Runnable {

	public Boom(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.isLive = true;
		this.imgIndex = 0;
		new Thread(this).start();
	}

	@Override
	public void draw(Graphics g, Image img, Image gress, JPanel jp) {
		// TODO Auto-generated method stub
		g.drawImage(img, x * TankSetting.cell, y * TankSetting.cell, (x + 1) * TankSetting.cell,
				(y + 1) * TankSetting.cell, imgIndex * 192, 0, (imgIndex + 1) * 192, 192, jp);

	}

	@Override
	public void crossGrass(Graphics g, Image img, Image gress, JPanel jp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isLive) {
			if (imgIndex < 9) {
				imgIndex++;
			} else {
				isLive = false;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
