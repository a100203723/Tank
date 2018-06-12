package com.ytj.tankcharcater;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.ytj.tanktools.TankSetting;

public abstract class Character {
	public int x;// 玩家初始化坐标X
	public int y;// 玩家初始化坐标y
	public boolean isLive;// 是否存活
	/***
	 * 0 向上 1 向右 2 向下 3 向左
	 */
	public int direct; // 运动方向
	public int imgIndex;// 玩家图标下方

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public abstract void draw(Graphics g, Image img, Image gress, JPanel jp);

	public void crossGrass(Graphics g, Image img, Image gress, JPanel jp) {
		if (TankSetting.MAP[y][x] == TankSetting.GRASS) {
			g.drawImage(img, x * TankSetting.cell + 8, y * TankSetting.cell, (x + 1) * TankSetting.cell + 8,
					(y + 1) * TankSetting.cell, 0, 0, 87, 83, jp);
		}
	}

	public boolean crash(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
