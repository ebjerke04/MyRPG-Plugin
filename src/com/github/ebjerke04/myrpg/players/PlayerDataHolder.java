package com.github.ebjerke04.myrpg.players;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.github.ebjerke04.myrpg.classes.RpgClass;

public class PlayerDataHolder {

	private RpgClass activeClass;

	public int level;
	
	public PlayerDataHolder() {
		this.level = 999;
	}

	public void setActiveClass(RpgClass activeClass) {
		this.activeClass = activeClass;

		Bukkit.getLogger().log(Level.SEVERE, "TEST WORKS");
	}

	public RpgClass getActiveClass() {
		return activeClass;
	}
	
}
