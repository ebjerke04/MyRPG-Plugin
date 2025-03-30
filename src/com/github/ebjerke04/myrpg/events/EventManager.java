package com.github.ebjerke04.myrpg.events;

import com.github.ebjerke04.myrpg.weapons.TestWeapon;

public class EventManager {
	
	public static void registerEvents() {
		new PlayerConnectEvent();
		new PlayerDisconnectEvent();
		new PlayerMessageEvent();
		new PlayerRightClickEntityEvent();
		new PlayerRightClickEvent();
		new PlayerClickItemEvent();
		new PlayerMovingEvent();
		new PlayerDamageEntityEvent();
		
		new EntityDeathEvent();
		
		new TestWeapon();
	}

}
