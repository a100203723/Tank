package com.ytj.tankui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Position;

import com.ytj.tankcharcater.Bullet;
import com.ytj.tankcharcater.NpcBullet;
import com.ytj.tankcharcater.NpcPlayer;

import com.ytj.tankcharcater.Player;
import com.ytj.tanktools.Music;
import com.ytj.tanktools.TankSetting;

public class TankJPanel extends JPanel implements Runnable {
	boolean isTrue = true;
	Image grass = null;
	Image border = null;
	Image water = null;
	Image steels = null;
	Image brick = null;
	Image boss = null;
	Image blank = null;
	Image mtank = null;
	Image mbullet = null;
	Image npcbullet = null;
	Image boom = null;
	Image playerImg = null;
	Image npcplayererImg = null;
	Image gameover = null;
	int imgIndex = 0;
	JLabel jlable = new JLabel();// 显示坦克信息
	JLabel jlable1 = null;
	JLabel jlable2 = null;
	Player player = new Player(14, 21);// 设置初始位置
	NpcPlayer npcplayer = null;
	JPanel jp = new JPanel();
	Icon icon = null;
	Icon icon1 = null;

	public TankJPanel() {
		// 背景颜色
		this.setBackground(Color.BLACK);
		// 设置背景可见
		this.setVisible(true);
		// 获取草1
		try {
			grass = ImageIO.read(new File("img/grass.png"));
			// 获取边界0
			border = ImageIO.read(new File("img/gray.png"));
			// 获取水2
			water = ImageIO.read(new File("img/water.png"));
			// 获取普通砖3
			brick = ImageIO.read(new File("img/brick.png"));
			// 获取老王6
			boss = ImageIO.read(new File("img/boss.gif"));
			// 获取金砖4
			steels = ImageIO.read(new File("img/steels.png"));
			// 获取道路
			// blank=ImageIO.read(new File("img/ice.png"));
			// 获取我方坦克
			mtank = ImageIO.read(new File("img/player.png"));
			// 获取我方子弹
			mbullet = ImageIO.read(new File("img/bullet2.png"));
			// 获取爆炸效果
			boom = ImageIO.read(new File("img/boom.png"));
			// 玩家坦克
			playerImg = ImageIO.read(new File("img/player.png"));
			// 敌方坦克
			npcplayererImg = ImageIO.read(new File("img/npc.gif"));
			// 敌方坦克子弹
			npcbullet = ImageIO.read(new File("img/bullet1.png"));
			// 游戏结束
			gameover = ImageIO.read(new File("img/gameOver1.jpg"));
			icon = new ImageIcon("img/gameOver1.jpg");
			icon1 = new ImageIcon("img/youWin2.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 播放背景音乐
		Music.playSound(Music.BGM);

		// 开启地图线程
		jlable1 = new JLabel();
		jlable1.setIcon(icon);
		jlable1.setBounds(50, 50, icon.getIconWidth(), icon.getIconHeight());
		jlable1.setLocation(200, 200);
		jlable2 = new JLabel();
		jlable2.setIcon(icon1);
		jlable2.setBounds(50, 50, icon1.getIconWidth(), icon1.getIconHeight());
		jlable2.setLocation(200, 200);
		new Thread(this).start();
		// 开启我方坦克线程
		new Thread(player).start();
		// 添加聚焦到该程序
		this.setFocusable(true);
		this.add(jlable, RIGHT_ALIGNMENT);
		this.addKeyListener(player);
	}

	@Override
	protected void paintComponent(Graphics g) {// 重写paintComponent绘图方法
		// TODO Auto-generated method stub
		super.paintComponent(g);
		jlable.setFont(new java.awt.Font("Dialog", 0, 15));
		jlable.setForeground(Color.red);
		jlable.setText("<html><body>我方tank生命值还剩:" + TankSetting.HP + " 生命条数还剩：" + TankSetting.hpitem
				+ "条  <br />敌方tank数量还剩:" + TankSetting.NpcPlayerNum + " 辆</body></html>");
		drawGameOver(g);//绘制游戏过关或结束时的插图
		drawMAP(g);// 重绘老王
		createTank();//创建tank
		drawNpcTank(g);// 绘制敌方玩家
		player.draw(g, mtank, grass, this);// 绘制我方玩家
		drawBullet(g);// 绘制玩家子弹
		drawNpcBullet(g);// 绘制敌方子弹
		drawBoom(g);// 绘制爆炸

	}

	private void drawGameOver(Graphics g) {
		// TODO Auto-generated method stub
		if (TankSetting.HP == 0 && TankSetting.hpitem == 0) {
			this.setLayout(null);
			this.add(jlable1);
			this.setLocation(200, 200);
		} else if (TankSetting.NpcPlayerNum <= 0) {
			this.setLayout(null);
			this.add(jlable2);
			this.setLocation(300, 200);
		}
	}

	// 绘制敌方坦克
	public void drawNpcTank(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < TankSetting.npcs.size(); i++) {
			if (TankSetting.npcs.get(i).isLive) {
				TankSetting.npcs.get(i).draw(g, npcplayererImg, grass, this);
			}

		}
	}

	// 初始化敌方坦克数量
	public void createTank() {
		// TODO Auto-generated method stub
		for (int i = 0; i < TankSetting.NpcPlayerNum; i++) {
			if (TankSetting.npcs.size() < TankSetting.NpcPlayerNum) {
				npcplayer = new NpcPlayer(i, (int) (Math.random() * 32 + 1), 2);
				TankSetting.npcs.add(npcplayer);
			} else {
				break;
			}

		}

	}

	// 绘制敌方坦克子弹
	public void drawNpcBullet(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < TankSetting.npcbv.size(); i++) {
			NpcBullet mb = TankSetting.npcbv.get(i);
			if (mb.isLive()) {
				mb.drawNpcBullet(g, npcbullet, grass, this);
			} else {
				TankSetting.npcbv.remove(i);
			}
		}
	}

	public void drawBullet(Graphics g) {// 画子弹
		for (int i = 0; i < TankSetting.bv.size(); i++) {
			Bullet mb = TankSetting.bv.get(i);
			if (mb.isLive()) {
				mb.drawMyBullet(g, mbullet, grass, this);
			} else {
				TankSetting.bv.remove(i);
			}
		}
	}

	public void drawBoom(Graphics g) {// 画爆炸效果
		for (int i = 0; i < TankSetting.boom.size(); i++) {
			if (TankSetting.boom.get(i).isLive()) {
				TankSetting.boom.get(i).draw(g, boom, null, this);
			} else {
				TankSetting.boom.remove(i);
			}
		}
	}

	public void drawMAP(Graphics g) {// 绘制地图

		for (int x = 0; x < TankSetting.MAP.length; x++) {// 纵坐标
			for (int y = 0; y < TankSetting.MAP[x].length; y++) {// 横坐标
				if (TankSetting.MAP[x][y] == TankSetting.BORDER) {// 绘制边界
					g.drawImage(border, TankSetting.cell * y, TankSetting.cell * x, TankSetting.cell * (y + 1),
							TankSetting.cell * (x + 1), 0, 0, 32, 32, this);
				} else if (TankSetting.MAP[x][y] == TankSetting.GRASS) {// 绘制草
					g.drawImage(grass, TankSetting.cell * y, TankSetting.cell * x, TankSetting.cell * (y + 1),
							TankSetting.cell * (x + 1), 0, 0, 87, 83, this);
				} else if (TankSetting.MAP[x][y] == TankSetting.WATER) {// 绘制水流
					g.drawImage(water, TankSetting.cell * y, TankSetting.cell * x, TankSetting.cell * (y + 1),
							TankSetting.cell * (x + 1), 0, 0, 52, 65, this);
				} else if (TankSetting.MAP[x][y] == TankSetting.BRICK) {// 绘制普通砖
					g.drawImage(brick, TankSetting.cell * y, TankSetting.cell * x, TankSetting.cell * (y + 1),
							TankSetting.cell * (x + 1), 0, 0, 32, 32, this);
				} else if (TankSetting.MAP[x][y] == TankSetting.STEELS) {// 绘制金砖
					g.drawImage(steels, TankSetting.cell * y, TankSetting.cell * x, TankSetting.cell * (y + 1),
							TankSetting.cell * (x + 1), 0, 0, 32, 32, this);
				} else if (TankSetting.MAP[x][y] == TankSetting.BOSS) {// 绘制老王
					g.drawImage(boss, TankSetting.cell * y, TankSetting.cell * x, TankSetting.cell * (y + 1),
							TankSetting.cell * (x + 1), 0, imgIndex * 34, 41, (imgIndex + 1) * 34, this);
					// } else if (TankSetting.MAP[x][y] == TankSetting.PLAYERS)
					// {
					// g.drawImage(playerImg, TankSetting.cell * y,
					// TankSetting.cell * x, TankSetting.cell * (y + 1),
					// TankSetting.cell * (x + 1), 0, 0, 32, 32, this);
					// }
				}
			}
		}

	}

	@Override
	public void run() {

		// TODO Auto-generated method stub
		while (isTrue) {// 设置死循环来实现老王不停左右运动
			if (imgIndex < 11)// 11是指有11个小图片在其中
				imgIndex += 1;
			else {
				imgIndex = 0;
			}

			this.repaint();// 重写paintComponent方法
			try {
				Thread.sleep(100);// 线程休眠0.1秒
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (TankSetting.HP == 0 && TankSetting.hpitem == 0) {
				isTrue = false;

			}

		}

	}

}