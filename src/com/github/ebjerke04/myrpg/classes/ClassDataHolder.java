package com.github.ebjerke04.myrpg.classes;

import java.util.ArrayList;
import java.util.List;

public class ClassDataHolder {
    
    public ClassType type;
    public int level;
    public int exp;

    public List<String> questsCompleted = new ArrayList<>();
    
    public ClassDataHolder() {
        this.level = 1;
        this.exp = 0;
    }

}
