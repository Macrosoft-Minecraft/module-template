package com.macrosoft.variables;

import com.macrosoft.actions.ModuleInfo;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.variable.VariableCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;

/**
 * This is an example variable provider, by extending {@link VariableCache} we
 * can update variables as often or as rarely as we like, since the values are
 * stored in the underlying caches.
 * 
 * <p>The {@link APIVersion} annotation must match the target API version for
 * this module, we provide a central location to update the version by storing
 * it in the {@link ModuleInfo} class.</p>
 */
@APIVersion(ModuleInfo.API_VERSION)
public class CloudVariableProviderLang extends VariableCache {

    /**
     * Called every frame, allows us to update variables in the current scope
     * when necessary
     * 
     * @see net.eq2online.macros.scripting.api.IVariableProvider#updateVariables(boolean)
     */
    @Override
    public void updateVariables(boolean clock) {
        if (!clock) {
            return;
        }
        Language lang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
        this.storeVariable("LANG", lang.getLanguageCode());
    }

    @Override
    public Object getVariable(String variableName) {
        return this.getCachedValue(variableName);
    }

    @Override
    public void onInit() {
    	System.out.println("[LANG VAR] Init");
        ScriptContext.MAIN.getCore().registerVariableProvider(this);
    }

}
