package com.github.ebjerke04.myrpg.entities.ai;

import java.util.Stack;

import org.bukkit.Location;

public class MovePath {

    private Stack<Location> path;

    public MovePath() {
        this.path = new Stack<Location>();
    }
    
    public void addNode(Location node) {
        path.insertElementAt(node, 0);
    }

    public void setPath(Stack<Location> path) {
        this.path = path;
    }

    public Stack<Location> getNodes() {
        Stack<Location> nodes = new Stack<>();
        nodes.addAll(path);
        
        return nodes;
    }
    
}
