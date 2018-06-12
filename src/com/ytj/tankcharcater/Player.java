package com.ytj.tankcharcater;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.ytj.tankskill.TankSkills;
import com.ytj.tanktools.Music;
import com.ytj.tanktools.TankSetting;

public class Player extends Character implements Runnable, KeyListener {
	public int hp = 100;
	public TankSkills skills;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		isLive = true;
		direct = 0;
		imgIndex = 0;
		skills = new TankSkills();
		TankSetting.play.add(this);
	}

	// 画出我方坦克
	public void draw(Graphics g, Image img, Image grass, JPanel jp) {
		g.drawImage(img, x * TankSetting.cell, y * TankSetting.cell, (x + 1) * TankSetting.cell,
				(y + 1) * TankSetting.cell, imgIndex * 32, direct * 32, (imgIndex + 1) * 32, (direct + 1) * 32, jp);
		crossGrass(g, grass, jp);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (isLive) {
			if (imgIndex < 3) {
				imgIndex++;
			} else {
				imgIndex = 0;
			}
			if (TankSetting.NpcPlayerNum > 0 || (TankSetting.HP != 0 && TankSetting.hpitem != 0)) {
				collision(x, y);
				try {
					Thread.sleep(100);// 暂停，注意要与地方子弹发射的时间一致
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				isLive = false;
			}

		}

	}

	// 敌方子弹打到我方坦克时,进行生命值的扣除
	private void collision(int x, int y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < TankSetting.npcbv.size(); i++) {
			if (x == TankSetting.npcbv.get(i).x && y == TankSetting.npcbv.get(i).y) {
				System.out.println("我方被打中了");
				TankSetting.HP -= 20;
				if (TankSetting.hpitem > 0 && TankSetting.HP < 20) {
					TankSetting.hpitem--;
					TankSetting.HP = 100;
				} else if (TankSetting.hpitem <= 0 && TankSetting.HP < 20) {
					TankSetting.HP = 0;
					TankSetting.hpitem = 0;
				}
				System.out.println("我方tank生命值还剩" + TankSetting.HP);
				TankSetting.npcbv.get(i).isLive = false;
				TankSetting.boom.add(new Boom(x, y));
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP) {// 上键
			Music.playSound(Music.move);
			if (direct == 2) {
				move('y', -1);
			} else {
				direct = 2;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {// 下键
			Music.playSound(Music.move);
			if (direct == 0) {
				move('y', 1);
			} else {
				direct = 0;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {// 左键
			Music.playSound(Music.move);

			if (direct == 3) {
				move('x', -1);
			} else {
				direct = 3;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {// 右键
			Music.playSound(Music.move);
			if (direct == 1) {
				move('x', 1);
			} else {
				direct = 1;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {// 空格
			TankSetting.bv.add(new Bullet(x, y, direct));
			Music.playSound(Music.fight);
		} else if (e.getKeyCode() == KeyEvent.VK_J) {// j建
			skills.ClearAll();
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

		// System.out.println("我方玩家的位置x:" + x + "y:" + y + ":" +
		// TankSetting.MAP[y][x]);
		// TODO Auto-generated method stub
		if (c == 'y' && crash(x, y + num)) {
			y += num;
		} else if (c == 'x' && crash(x + num, y)) {
			x += num;
		}

	}

	/***
	 * 碰撞方法 在草，空白区域，玩家初始位置移动时 判断是否有地方坦克，如果有则不能穿过
	 * 
	 */
	public boolean crash(int x, int y) {
		System.out.println("碰撞方法");
		if (TankSetting.MAP[y][x] == TankSetting.GRASS || TankSetting.MAP[y][x] == TankSetting.DEFAULT
				|| TankSetting.MAP[y][x] == TankSetting.PLAYERS) {
			for (int i = 0; i < TankSetting.npcs.size(); i++) {
				System.out.println("敌方坦克数量" + TankSetting.npcs.size());
				if (x == TankSetting.npcs.get(i).x && y == TankSetting.npcs.get(i).y) {
					System.out.println("我方碰撞了");
					return false;
				} else {
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public void crossGrass(Graphics g, Image img, Image gress, JPanel jp) {
		// TODO Auto-generated method stub

	}

}
