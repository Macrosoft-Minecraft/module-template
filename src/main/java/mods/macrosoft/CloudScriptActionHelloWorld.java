package mods.macrosoft;

import com.bezouro.modules.CloudScript.Core.CloudScriptAction;
import com.bezouro.modules.CloudScript.Info.ModuleInfo;
import net.eq2online.macros.scripting.*;
import net.eq2online.macros.scripting.api.*;
import net.eq2online.macros.scripting.parser.*;

import java.util.ArrayList;


@APIVersion(ModuleInfo.API_VERSION)
public class CloudScriptActionHelloWorld extends CloudScriptAction {

	private static final String MESSAGE = "I'm the first Federal's module";
	
    public CloudScriptActionHelloWorld() {
        super("helloworld");
    }

    public boolean isThreadSafe() {
        return false;
    }

    public IReturnValue executeAction(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {

    	provider.actionAddChatMessage(CloudScriptActionHelloWorld.MESSAGE);

        return null;

    }

    @Override
    public void onInit() {
        //context.getCore().registerScriptAction(this);
    	registerAction(this);
    }
}