package com.ytj.tankskill;

import com.ytj.tankcharcater.Boom;
import com.ytj.tanktools.TankSetting;

public class TankSkills {
	public int x = 0;
	public int y = 0;
	public int count = 0;

	public void ClearAll() {
		for (int i = 0; i < TankSetting.npcs.size(); i++) {
			x = TankSetting.npcs.get(i).x;
			y = TankSetting.npcs.get(i).y;
			TankSetting.npcs.get(i).isLive = false;
			TankSetting.boom.add(new Boom(x, y));
			TankSetting.boom.get(i).isLive = false;
//			TankSetting.npcbv.get(i-1).isLive = false;
			count++;
		}
		for (int i = 0; i < TankSetting.npcbv.size(); i++) {
			x = TankSetting.npcs.get(i).x;
			y = TankSetting.npcs.get(i).y;
			TankSetting.npcbv.get(i).isLive = false;
			TankSetting.npcbv.remove(i);
		}
		System.out.println(count);
		TankSetting.NpcPlayerNum -= count;

	}

}
