package com.macrosoft.actions;

import com.bezouro.modules.CloudScript.Core.CloudScriptAction;
import com.macrosoft.EntityManager;

import net.eq2online.macros.scripting.api.*;
import net.eq2online.macros.scripting.parser.ScriptCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@APIVersion(ModuleInfo.API_VERSION)
public class CloudScriptActionGetEntities extends CloudScriptAction {
	
    public CloudScriptActionGetEntities() {
        super("getentities");
    }

    public boolean isThreadSafe() {
        return false;
    }
    
    //private String NAME = "entities";
	private String DEF = "([^:)]*):?([^)]*)";

    public IReturnValue executeAction(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
    	
    	ArrayList<EntityManager> es = new ArrayList<EntityManager>();
    	
    	if (params.length > 1) {
    		
    		System.out.println("[ENTITY ACTION] INIT NORMAL");
	    	String type = null;
	    	String rangeStr = null;
	    	int range = 0;
	    	String criteria = params[0];
	    	System.out.println("[ENTITY ACTION] Criteria " + criteria);
	    	
	    	
	        Pattern r = Pattern.compile(DEF);
	        Matcher m = r.matcher(criteria);
	        int groupCount = m.groupCount();
	        
	        if(m.find()) {
	        	type = m.group(1);
	        	rangeStr = m.group(2);
	        	try {
	            	range = Integer.parseInt(rangeStr.trim());
	    		} catch(Exception e) {
	    			System.out.println("[ENTITY ACTION] Range invalid " + rangeStr);
	    		}
	        }
	        
	        System.out.println("[ENTITY ITER] Args (type)" + type + " (range) "+range+ " --> "+groupCount);
	        
	        EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
	        
	        for (Object e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
	        	Entity entity = (Entity) e;
	        	if(entity != thePlayer) {
	        		EntityManager subject = new EntityManager(thePlayer, entity);
		        	if((type == null) || (EntityManager.matchType(type.split("~"), entity))) {
		        		if((range == 0) || (subject.getDistance() <= range)) {
		        			es.add(subject);
		        		}
		        	}
	        	}
	        }
	        
	        ArrayList<String> dictList = getFederalDICTList(es);
	        
	        //String arrayName = provider.expand(macro, params[3], false).toLowerCase();
			String arrayName = ScriptCore.parseVars(provider, macro, params[1].toLowerCase(), false);
	        provider.clearArray(macro, arrayName);
	        provider.unsetVariable(macro, arrayName);
	        for (String dict : dictList) {
	            provider.pushValueToArray(macro, arrayName, dict);
	        }
    		
    	}

        return null;
	        
    }
    
    private ArrayList<String> getFederalDICTList(ArrayList<EntityManager> es) {
    	
    	Collections.sort(es, new Comparator<EntityManager>() {
        	@Override
        	public int compare(EntityManager e1, EntityManager e2) {
        		// TODO Auto-generated method stub
        		return Integer.valueOf(e1.getDistance()).compareTo(e2.getDistance());
        	}
		});
    	
    	ArrayList<String> vars = new ArrayList<String>();
    		
    	for (EntityManager subject : es) {

    		vars.add(buildFederalDICTElem("ENTITY", subject.getCategory()));
    		vars.add(buildFederalDICTElem("ENTITYNAME", subject.getName()));
    		vars.add(buildFederalDICTElem("ENTITYID", String.valueOf(subject.getId())));
    		
    		vars.add(buildFederalDICTElem("ENTITYPITCH", String.valueOf(subject.getPitch())));
    		vars.add(buildFederalDICTElem("ENTITYYAW", String.valueOf(subject.getYaw())));
    		vars.add(buildFederalDICTElem("ENTITYXPOS", String.valueOf(subject.getPosX())));
    		vars.add(buildFederalDICTElem("ENTITYYPOS", String.valueOf(subject.getPosY())));
    		vars.add(buildFederalDICTElem("ENTITYZPOS", String.valueOf(subject.getPosZ())));
    		vars.add(buildFederalDICTElem("ENTITYXPOSF", String.valueOf(subject.getFposX())));
    		vars.add(buildFederalDICTElem("ENTITYYPOSF", String.valueOf(subject.getFposY())));
    		vars.add(buildFederalDICTElem("ENTITYZPOSF", String.valueOf(subject.getFposZ())));
    		
    		vars.add(buildFederalDICTElem("ENTITYDISTANCE", String.valueOf(subject.getDistance())));
    		vars.add(buildFederalDICTElem("CARDINALYAW", String.valueOf(subject.getRealYaw())));
    		vars.add(buildFederalDICTElem("ENTITYUUID", String.valueOf(subject.getUuid())));
    		vars.add(buildFederalDICTElem("ENTITYDIRECTION", String.valueOf(subject.getDirection())));
    		vars.add(buildFederalDICTElem("ENTITYYAWTO", String.valueOf(subject.getYawOffset())));
    		vars.add(buildFederalDICTElem("ENTITYPITCHTO", String.valueOf(subject.getPitchOffset())));
    		vars.add(buildFederalDICTElem("ENTITYHEALTH", String.valueOf(subject.getHealth())));

        }
    	
        return vars;

    }
    
    private String buildFederalDICTElem(String key, String value) {
    	return "⦃" + key + "⦄→⦃" + value + "⦄";
    }

    @Override
    public void onInit() {
        //context.getCore().registerScriptAction(this);
    	System.out.println("[ENTITY ACTION] Init");
    	registerAction(this);
    }
}