package com.github.ebjerke04.myrpg.scripting;

import java.util.logging.Level;

import com.github.ebjerke04.myrpg.scripting.objects.ScriptPlayer;
import com.github.ebjerke04.myrpg.util.Logging;

public class FunctionContainer {

    public void logToServer(ScriptPlayer player) {
        Logging.sendConsole(Level.WARNING, "Position of " + player.playerName + ": " 
            + player.position.x + ", " + player.position.y + ", " + player.position.z);
    }
    
}
