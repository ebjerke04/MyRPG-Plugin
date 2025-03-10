package com.github.ebjerke04.myrpg.data.leveling;

public class PlayerLevel {

	private int experienceToNext;
	
	public PlayerLevel(int experienceToNext) {
		this.experienceToNext = experienceToNext;
	}
	
	public int getExperienceToNext() {
		return experienceToNext;
	}
	
	public String toString() {
		return Integer.toString(experienceToNext);
	}
	
}
