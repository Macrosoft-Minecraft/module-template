package com.example.module.helloworld.actions;

import com.example.module.helloworld.ModuleInfo;
import com.bezouro.modules.CloudScript.Core.CloudScriptAction;
import net.eq2online.macros.scripting.api.*;

@APIVersion(ModuleInfo.API_VERSION)
public class CloudScriptActionHelloWorld extends CloudScriptAction {

    public CloudScriptActionHelloWorld() {
        super("helloworld");
    }

    @Override
    public IReturnValue executeAction(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
        provider.actionAddChatMessage(HelloWorld());
        return null;
    }

    public static String HelloWorld(){
        return HelloWorlda();
    }
    public static String HelloWorlda(){
        return HelloWorldd();
    }
    public static String HelloWorldd(){
        return "Hello World";
    }
    
    @Override
    public void onInit() {
        registerAction(this);
    }
}
