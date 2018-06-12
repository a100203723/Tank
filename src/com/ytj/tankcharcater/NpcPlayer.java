package com.ytj.tankcharcater;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.ytj.tanktools.TankSetting;

public class NpcPlayer extends Character implements Runnable {
	public int countx1 = 0;// x轴正 移动次数
	public int countx2 = 0;// x轴负 移动次数
	public int county1 = 0;// y轴正 移动次数
	public int county2 = 0;// y轴负 移动次数

	public NpcPlayer(int id, int x, int y) {
		this.x = x;
		this.y = y;
		isLive = true;
		direct = 0;
		imgIndex = 0;
		
		new Thread(this).start();
	}

	public void draw(Graphics g, Image img, Image grass, JPanel jp) {
		g.drawImage(img, x * TankSetting.cell, y * TankSetting.cell, (x + 1) * TankSetting.cell,
				(y + 1) * TankSetting.cell, imgIndex * 28, direct * 28, (imgIndex + 1) * 28, (direct + 1) * 28, jp);

		crossGrass(g, grass, jp);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (isLive) {
			if (TankSetting.NpcPlayerNum <= 0) {
				isLive = false;

			} else {
				if (imgIndex < 1) {
					imgIndex++;
				} else {
					imgIndex = 0;
				}
				if (direct == 0) {
					county1++;
					move('y', -1);

				} else if (direct == 1) {
					countx1++;
					move('x', 1);

				} else if (direct == 2) {
					county2++;
					move('y', 1);

				} else if (direct == 3) {
					countx2++;
					move('x', -1);
				}
				collision(x, y);
			}
			try {
				Thread.sleep(1000);// 暂停
				TankSetting.npcbv.add(new NpcBullet(x, y, direct));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch blocks
				e.printStackTrace();
			}

		}

	}

	// 我方坦克不能穿透敌方坦克
	private void collision(int x, int y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < TankSetting.npcs.size(); i++) {
			// if (x == TankSetting.npcbv.get(i).x && y ==
			// TankSetting.npcbv.get(i).y) {
			//
			// }
		}
	}

	// 穿过草时，对玩家进行覆盖
	public void crossGrass(Graphics g, Image img, JPanel jp) {
		if (TankSetting.MAP[y][x] == TankSetting.GRASS) {
			g.drawImage(img, x * TankSetting.cell, y * TankSetting.cell, (x + 1) * TankSetting.cell,
					(y + 1) * TankSetting.cell, 0, 0, 87, 83, jp);
		}
	}

	// 玩家移动 c-- x 或者y轴上的移动，num 每次移动
	public void move(char c, int num) {
		// System.out.println("敌方玩家的位置x:" + x + "y:" + y + ":" +
		// TankSetting.MAP[y][x]);
		// TODO Auto-generated method stub
		if (c == 'y' && crash(x, y + num)) {
			y += num;
		} else if (c == 'x' && crash(x + num, y)) {
			x += num;
		}

	}

	/**
	 * 碰撞方法 在草中，空白区域，以及玩家的初始的位置是可以穿过的，并且在这些敌方移动超过4歩，就会随机转变方向 遇到边界，水，砖 ，金属砖
	 * 都会改变方向
	 */
	public synchronized boolean crash(int x, int y) {
		System.out.println("敌方坦克位置x:" + x);
		if (countx1 >= 2) {
			direct = (int) (Math.random() * 3 + 1);
			countx1 = 0;
		} else if (countx2 >= 2) {
			direct = (int) (Math.random() * 3 + 1);
			countx2 = 0;
		} else if (county1 >= 2) {
			direct = (int) (Math.random() * 3 + 1);
			county1 = 0;
		} else if (county2 >= 2) {
			direct = (int) (Math.random() * 3 + 1);
			county2 = 0;
		} 
		if (TankSetting.MAP[y][x] == TankSetting.GRASS || TankSetting.MAP[y][x] == TankSetting.DEFAULT
				|| TankSetting.MAP[y][x] == TankSetting.PLAYERS) {
			for (int i = 0; i < TankSetting.play.size(); i++) {
				System.out.println("我方坦克位置x:" + TankSetting.play.get(i).x);
				if (x == TankSetting.play.get(i).x && y == TankSetting.play.get(i).y) {
					System.out.println("碰撞了");
					direct = (int) (Math.random() * 3 + 1);
					return false;
				} else {
					return true;
				}
			}

			return true;
		} else if (TankSetting.MAP[y][x] == TankSetting.BRICK || TankSetting.MAP[y][x] == TankSetting.WATER
				|| TankSetting.MAP[y][x] == TankSetting.STEELS || TankSetting.MAP[y][x] == TankSetting.BORDER) {
			direct = (int) (Math.random() * 3 + 1);
			return false;
		} else {

			return false;
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void crossGrass(Graphics g, Image img, Image gress, JPanel jp) {
		// TODO Auto-generated method stub

	}

}
