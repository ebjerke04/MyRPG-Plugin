package com.github.ebjerke04.myrpg.entities;

import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.util.Logging;

public class ScriptComponent {

    private Context context;
    private Scriptable scope;
    private File scriptFolder;
    private boolean isInitialized = false;

    public ScriptComponent(String scriptName) {
        try {
            context = Context.enter();
            // Create standard scope with JavaScript objects (Array, Object, etc.)
            scope = context.initStandardObjects();
            isInitialized = true;
            Logging.sendConsole(Level.INFO, "JavaScript engine initialized successfully");

            FunctionContainer container = new FunctionContainer();
            ScriptableObject.putProperty(scope, "myrpgLib", container);

            // Setup script folder
            scriptFolder = new File(Plugin.get().getDataFolder(), "scripts");
            if (!scriptFolder.exists()) {
                scriptFolder.mkdirs();
            }

            loadScript(scriptName);
        } catch (Exception e) {
            Logging.sendConsole(Level.SEVERE, "Failed to initialize JavaScript engine: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadScript(String scriptName) {
        if (!isInitialized) {
            Logging.sendConsole(Level.SEVERE, "Cannot load script - engine not initialized");
            return;
        }

        File scriptFile = new File(scriptFolder, scriptName);

        if (!scriptFile.exists()) {
            Logging.sendConsole(Level.SEVERE, "Script file not found: " + scriptName);
            return;
        }

        try (FileReader reader = new FileReader(scriptFile)) {
            // Evaluate the script in the context
            context.evaluateReader(scope, reader, scriptName, 1, null);
            Logging.sendConsole(Level.INFO, "Successfully loaded script: " + scriptName);
        } catch (Exception e) {
            Logging.sendConsole(Level.SEVERE, "Error loading script: " + scriptName);
            e.printStackTrace();
        }
    }

    public Object invokeFunction(String functionName, Object... parameters) {
        if (!isInitialized) {
            Logging.sendConsole(Level.SEVERE, "Cannot test function - engine not initialized");
            return null;
        }

        try {
            Object fObj = scope.get(functionName, scope);
            if (!(fObj instanceof Function)) {
                Logging.sendConsole(Level.SEVERE, functionName + " is not a function");
                return null;
            }
            
            Function f = (Function) fObj;
            Object result = f.call(context, scope, scope, parameters);
            Logging.sendConsole(Level.INFO, "Function executed successfully: " + result);
            return Context.toString(result); // Convert result to string
        } catch (Exception e) {
            Logging.sendConsole(Level.SEVERE, "Error executing script function: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
    
    public void cleanup() {
        if (isInitialized) {
            try {
                Context.exit();
                context = null;
                scope = null;
                isInitialized = false;
                Logging.sendConsole(Level.INFO, "JavaScript engine cleaned up successfully");
            } catch (Exception e) {
                Logging.sendConsole(Level.SEVERE, "Error during cleanup: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void cleanupAll() {
        try {
            while (Context.getCurrentContext() != null) {
                Context.exit();
            }
        } catch (Exception e) {
            Logging.sendConsole(Level.SEVERE, "Error during final cleanup: " + e.getMessage());
        }
    }
}
