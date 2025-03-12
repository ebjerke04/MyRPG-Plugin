package com.github.ebjerke04.myrpg.players;

import com.github.ebjerke04.myrpg.classes.Class;

public class PlayerDataHolder {

	private Class activeClass;

	public int level;
	
	public PlayerDataHolder() {
		this.level = 999;
	}

	public void setActiveClass(Class activeClass) {
		this.activeClass = activeClass;
	}

	public Class getActiveClass() {
		return activeClass;
	}
	
}
