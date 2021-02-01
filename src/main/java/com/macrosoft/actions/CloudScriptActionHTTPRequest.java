package com.macrosoft.actions;

import com.bezouro.modules.CloudScript.Core.CloudScriptAction;
import com.bezouro.modules.CloudScript.Core.CloudScriptCore;
import com.macrosoft.NET;

import net.eq2online.macros.scripting.api.*;
import net.eq2online.macros.scripting.parser.ScriptCore;

import java.util.Map;


@APIVersion(ModuleInfo.API_VERSION)
public class CloudScriptActionHTTPRequest extends CloudScriptAction {
	
    public CloudScriptActionHTTPRequest() {
        super("request");
    }

    public boolean isThreadSafe() {
        return false;
    }

    public IReturnValue executeAction(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
    	
    	ReturnValue ret = null;
    	String parsedValue;
    	
    	if (params.length > 1) {//Method & URL
    		
    		String method=ScriptCore.parseVars(provider, macro, params[0], false);
    		String url=ScriptCore.parseVars(provider, macro, params[1], false);
    				
    		String body="", headers="", timeout="5000";
        	String response, statuscode;
    		
    		if (params.length > 2) {//+body
    			
    			parsedValue = ScriptCore.parseVars(provider, macro, params[2], false);
    			body = parsedValue;
    			
    			if (params.length > 3) {//+headers
        			
    				parsedValue = ScriptCore.parseVars(provider, macro, params[3], false);
    				headers = parsedValue;
    				
    				if (params.length > 4) {//+timeout
            			
    					parsedValue = ScriptCore.parseVars(provider, macro, params[4], false);
    					timeout = parsedValue;
    					
    					try {
    						Integer.valueOf(timeout);
    					} catch (NumberFormatException e) {
    						timeout = "5000";
    					}
    					
            		} 
    				
        		} 
    			
    		} 
    		
    		Map<String, String> result;
    		result = NET.request(method, url, body, headers, Integer.valueOf(timeout));

    		ret = new ReturnValue(result.get("data"));
    		//ReturnValue statuscode = new ReturnValue(result.get("status_code"));
    		response = result.get("data");
    		statuscode = result.get("status_code");
    		
    		if (params.length > 5) {//+&response
    			
				//String res = provider.expand(macro, params[5], false).toLowerCase();
                //provider.setVariable(macro, res, response);
    			parsedValue = ScriptCore.parseVars(provider, macro, params[5], false);
    			CloudScriptCore.setVariable(provider, macro, parsedValue.toLowerCase(), response);
				
                if (params.length > 6) {//+#status code

                	//String stat = provider.expand(macro, params[6], false).toLowerCase();
                    //provider.setVariable(macro, stat, statuscode);
                	parsedValue = ScriptCore.parseVars(provider, macro, params[6], false);
                	CloudScriptCore.setVariable(provider, macro, parsedValue, statuscode);
                	
        		} 
		
    		} 
    		
    	}
    	
    	//provider.actionAddChatMessage("requested");
    	
        return ret;

    }

    @Override
    public void onInit() {
        //context.getCore().registerScriptAction(this);
    	System.out.println("[HTTPREQUEST ACTION] Init");
    	registerAction(this);
    }
}