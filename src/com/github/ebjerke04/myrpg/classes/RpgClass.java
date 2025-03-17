package com.github.ebjerke04.myrpg.classes;

public class RpgClass {

    private ClassType classType;

    protected ClassDataHolder classData;

    public RpgClass(ClassType classType) {
        this.classType = classType;
        this.classData = null;
    }

    public void setUserData(ClassDataHolder classData) {
        this.classData = classData;
    }

    public ClassType getType() {
        return classType;
    }
    
}
