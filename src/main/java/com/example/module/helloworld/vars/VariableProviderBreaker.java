package com.example.module.helloworld.vars;

import com.example.module.helloworld.ModuleInfo;
import net.eq2online.macros.scripting.ScriptCore;
import net.eq2online.macros.scripting.VariableCache;
import net.eq2online.macros.scripting.api.IVariableListener;
import net.eq2online.macros.scripting.api.IVariableProvider;
import net.eq2online.macros.scripting.api.APIVersion;

@APIVersion(ModuleInfo.API_VERSION)
public class VariableProviderBreaker extends VariableCache implements IVariableProvider {

    public void updateVariables(boolean clock) {
        this.setCachedVariable("HELLOWORLD", HelloWorld());
    }

    public static boolean HelloWorld(){
        return true;
    }

    public void provideVariables(IVariableListener variableListener) {
        this.provideCachedVariables(variableListener);
    }

    public Object getVariable(String variableName) {
        return this.getCachedValue(variableName);
    }

    public void onInit() {
        ScriptCore.registerVariableProvider(this);
    }

}
