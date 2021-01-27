package com.macrosoft.actions;

import com.bezouro.modules.CloudScript.Core.CloudScriptAction;
import com.bezouro.modules.CloudScript.Core.CloudScriptCore;
import com.macrosoft.NET;

import net.eq2online.macros.scripting.api.*;

import java.util.Map;


@APIVersion(ModuleInfo.API_VERSION)
public class CloudScriptActionHTTPRequest extends CloudScriptAction {
	
    public CloudScriptActionHTTPRequest() {
        super("httprequest");
    }

    public boolean isThreadSafe() {
        return false;
    }

    public IReturnValue executeAction(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
    	
    	ReturnValue ret = null;
    	    	
    	if (params.length > 1) {//Method & URL
    		
    		String method=params[0], url=params[1], body="", headers="", timeout="5000";
        	String response, statuscode;
    		
    		if (params.length > 2) {//+body
    			
    			body = params[2];
    			
    			if (params.length > 3) {//+headers
        			
    				headers = params[3];
    				
    				if (params.length > 4) {//+timeout
            			
    					timeout = params[4];
    					
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
    			CloudScriptCore.setVariable(provider, macro, params[5].toLowerCase(), response);
				
                if (params.length > 6) {//+#status code

                	//String stat = provider.expand(macro, params[6], false).toLowerCase();
                    //provider.setVariable(macro, stat, statuscode);
                	CloudScriptCore.setVariable(provider, macro, params[6].toLowerCase(), statuscode);
                	
        		} 
		
    		} 
    		
    	}
    	
    	//provider.actionAddChatMessage("requested");
    	
        return ret;

    }

    @Override
    public void onInit() {
        //context.getCore().registerScriptAction(this);
    	registerAction(this);
    }
}