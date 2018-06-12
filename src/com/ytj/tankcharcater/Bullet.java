package com.ytj.tankcharcater;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.ytj.tanktools.Music;
import com.ytj.tanktools.TankSetting;

public class Bullet extends Character implements Runnable {

	public Bullet(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.isLive = true;
		this.imgIndex = 0;
		new Thread(this).start();
	}

	// 画出我方子彈
	public void drawMyBullet(Graphics g, Image img, Image grass, JPanel jp) {// img指定要绘制的图像。如果img为空，则此方法不起作用。
		// dx1目标矩形第一个角的x坐标。dy1目标矩形第一个角的y坐标。dx2目标矩形第二个角的x坐标。dy2目标矩形第二个角的y坐标。
		// sx1源矩形第一个角的x坐标。sy1源矩形第一个角的y坐标。sx2源矩形第二个角的x坐标。sy2源矩形第二个角的y坐标。
		// 当更多的图像被缩放和转换时，将通知观察者对象。
		g.drawImage(img, x * TankSetting.cell, y * TankSetting.cell, (x + 1) * TankSetting.cell,
				(y + 1) * TankSetting.cell, 0, 0, 32, 32, jp);
		crossGrass(g, grass, jp);
	}

	// 进入草后隐藏我方子彈
	public void crossGrass(Graphics g, Image img, JPanel jp) {
		if (TankSetting.MAP[y][x] == TankSetting.GRASS) {
			g.drawImage(img, x * TankSetting.cell, y * TankSetting.cell, (x + 1) * TankSetting.cell,
					(y + 1) * TankSetting.cell, 0, 0, 87, 83, jp);
		}
	}

	// 子弹移动
	public void move() {
		System.out.println("我方玩家的子彈位置x:" + x + "y:" + y + ":" + TankSetting.MAP[y][x]);
		System.out.println("敌方NPC x：" + TankSetting.npcs.get(0).x + "y：" + TankSetting.npcs.get(0).y);
		if (direct == 2 && crash(x, y - 1)) {
			y--;
		} else if (direct == 0 && crash(x, y + 1)) {
			y++;
		} else if (direct == 3 && crash(x - 1, y)) {
			x--;
		} else if (direct == 1 && crash(x + 1, y)) {
			x++;
		}
	}

	// 单位碰撞
	@Override
	public boolean crash(int x, int y) {
		if (TankSetting.MAP[y][x] == TankSetting.GRASS || TankSetting.MAP[y][x] == TankSetting.DEFAULT
				|| TankSetting.MAP[y][x] == TankSetting.WATER) {
			this.isLive = true;
			return true;
		} else if (TankSetting.MAP[y][x] == TankSetting.BRICK) {
			TankSetting.MAP[y][x] = TankSetting.DEFAULT;
			Music.playSound(Music.fight);
			TankSetting.boom.add(new Boom(x, y));
			this.isLive = false;
			return false;
		} else {
			this.isLive = false;
			Music.playSound(Music.fight);
			TankSetting.boom.add(new Boom(x, y));
			return true;
		}
	}

	@Override
	// 重写run方法
	public void run() {
		while (isLive) {
			if (TankSetting.NpcPlayerNum > 0) {
				move();
				cashNpcPlay(x, y);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				isLive = false;
			}

		}

	}

	/***
	 * 
	 * @param x
	 * @param y
	 *            当前我方坦克的子弹打到地方坦克的时候 添加爆炸效果
	 */
	private void cashNpcPlay(int x, int y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < TankSetting.npcs.size(); i++) {
			if (x == TankSetting.npcs.get(i).x && y == TankSetting.npcs.get(i).y) {
				System.out.println("碰撞了碰撞了碰撞了碰撞了碰撞了碰撞了碰撞了碰撞了");
				if (TankSetting.NpcPlayerNum <= 0) {
					TankSetting.NpcPlayerNum = 0;
				} else {
					TankSetting.NpcPlayerNum--;
				}
				TankSetting.npcs.get(i).isLive = false;
				TankSetting.boom.add(new Boom(x, y));
				this.isLive = false;
				TankSetting.npcs.remove(i);

			}

		}
		for (int j = 0; j < TankSetting.npcbv.size(); j++) {
			if (x == TankSetting.npcbv.get(j).x && y == TankSetting.npcbv.get(j).y) {
				this.isLive = false;
				TankSetting.npcbv.get(j).isLive = false;
				TankSetting.npcbv.remove(j);

			}
		}

	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	@Override
	public void draw(Graphics g, Image img, Image gress, JPanel jp) {
		// TODO Auto-generated method stub

	}

}
