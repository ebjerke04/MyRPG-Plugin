package com.github.ebjerke04.myrpg.players;

import com.github.ebjerke04.myrpg.classes.RpgClass;

public class RpgPlayer {

	private RpgClass activeClass = null;

	public RpgPlayer() {
		
	}

	public void setActiveClass(RpgClass activeClass) {
		this.activeClass = activeClass;

		// TODO: Player active class set. Figure out what is next.
		// Send player to last location they left off at.
		// Load their saved inventory.
	}

	public RpgClass getActiveClass() {
		return activeClass;
	}
	
}
