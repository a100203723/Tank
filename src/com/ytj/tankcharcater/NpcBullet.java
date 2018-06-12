package com.ytj.tankcharcater;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import com.ytj.tanktools.TankSetting;

public class NpcBullet extends Character implements Runnable {

	public NpcBullet(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.isLive = true;
		this.imgIndex = 0;
		new Thread(this).start();
	}

	// 画出敌方子彈
	/***
	 * 
	 * @param g
	 * @param img
	 * @param grass
	 * @param jp
	 *            img指定要绘制的图像。如果img为空，则此方法不起作用。
	 *            dx1目标矩形第一个角的x坐标。dy1目标矩形第一个角的y坐标。dx2目标矩形第二个角的x坐标。
	 *            dy2目标矩形第二个角的y坐标。
	 *            sx1源矩形第一个角的x坐标。sy1源矩形第一个角的y坐标。sx2源矩形第二个角的x坐标。sy2源矩形第二个角的y坐标。
	 *            当更多的图像被缩放和转换时，将通知观察者对象。
	 */
	public void drawNpcBullet(Graphics g, Image img, Image grass, JPanel jp) {

		g.drawImage(img, x * TankSetting.cell, y * TankSetting.cell, (x + 1) * TankSetting.cell,
				(y + 1) * TankSetting.cell, 0, 0, 32, 32, jp);
		crossGrass(g, grass, jp);
	}

	// 进入草后隐藏敌方子彈
	public void crossGrass(Graphics g, Image img, JPanel jp) {

		if (TankSetting.MAP[y][x] == TankSetting.GRASS) {
			g.drawImage(img, x * TankSetting.cell, y * TankSetting.cell, (x + 1) * TankSetting.cell,
					(y + 1) * TankSetting.cell, 0, 0, 87, 83, jp);
		}
	}

	// 子弹移动
	public void move() {
		if (direct == 0 && crash(x, y - 1)) {
			y--;
		} else if (direct == 2 && crash(x, y + 1)) {
			y++;
		} else if (direct == 3 && crash(x - 1, y)) {
			x--;
		} else if (direct == 1 && crash(x + 1, y)) {
			x++;
		}
	}

	// 单位碰撞
	@Override
	/***
	 * 敌方子弹可以穿草，穿过水流，在默认区域移动 打到专上就移除砖块，添加爆炸效果，并设置子弹生命周期为false
	 * 
	 */
	public boolean crash(int x, int y) {
		if (TankSetting.MAP[y][x] == TankSetting.GRASS || TankSetting.MAP[y][x] == TankSetting.DEFAULT
				|| TankSetting.MAP[y][x] == TankSetting.WATER) {
			this.isLive = true;
			return true;
		} else if (TankSetting.MAP[y][x] == TankSetting.BRICK) {
			TankSetting.MAP[y][x] = TankSetting.DEFAULT;
			TankSetting.boom.add(new Boom(x, y));
			this.isLive = false;
			return false;
		} else if (TankSetting.MAP[y][x] == TankSetting.BOSS) {//子弹击中BOSS时，玩家的血量血条全部归0
			TankSetting.MAP[y][x] = TankSetting.DEFAULT;
			TankSetting.HP = 0;
			TankSetting.hpitem = 0;
			TankSetting.boom.add(new Boom(x, y));
			this.isLive = false;
			return false;
		} else {
			this.isLive = false;
			TankSetting.boom.add(new Boom(x, y));
			return false;
		}
	}

	@Override
	// 重写run方法
	public void run() {
		int count = 0;
		while (isLive) {
			move();
			try {
				Thread.sleep(100);// 每隔100毫秒发射1颗子弹
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
		}

	}

	@Override
	public void draw(Graphics g, Image img, Image gress, JPanel jp) {
		// TODO Auto-generated method stub

	}

}
