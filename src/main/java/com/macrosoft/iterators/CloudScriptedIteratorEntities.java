package com.macrosoft.iterators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.macrosoft.EntityManager;
import com.macrosoft.actions.ModuleInfo;

import net.eq2online.macros.scripting.ScriptedIterator;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
//import net.eq2online.macros.scripting.api.IScriptedIterator;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

@APIVersion(ModuleInfo.API_VERSION)
public class CloudScriptedIteratorEntities extends ScriptedIterator {
//public class CloudScriptedIteratorEntities extends ScriptedIterator implements IScriptedIterator {
		
	private String NAME = "entities";
	private String DEF = "\\(([^:)]*):?([^)]*)\\)";
	
	public CloudScriptedIteratorEntities() {
		super(null, null);
	}
	
	public CloudScriptedIteratorEntities(IScriptActionProvider provider, IMacro macro, String iteratorDef) {
    	super(provider, macro);
    	String type = null;
    	String rangeStr = null;
    	int range = 0;
    	String criteria = iteratorDef.replaceFirst(NAME, "");
    	System.out.println("[FILTER ITER] Criteria " + criteria);
    	
    	
        Pattern r = Pattern.compile(DEF);
        Matcher m = r.matcher(criteria);
        int groupCount = m.groupCount();
        
        if(m.find()) {
        	type = m.group(1);
        	rangeStr = m.group(2);
        	try {
            	range = Integer.parseInt(rangeStr.trim());
    		} catch(Exception e) {
    			System.out.println("[ENTITY ITER] Range invalid " + rangeStr);
    		}
        }
        
        System.out.println("[ENTITY ITER] Args (type)" + type + " (range) "+range+ " --> "+groupCount);
        
        EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        ArrayList<EntityManager> es = new ArrayList<EntityManager>();
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
        
        appendVars(es);
	        
    }
    
    private void appendVars(ArrayList<EntityManager> es) {
    	
    	Collections.sort(es, new Comparator<EntityManager>() {
        	@Override
        	public int compare(EntityManager e1, EntityManager e2) {
        		// TODO Auto-generated method stub
        		return Integer.valueOf(e1.getDistance()).compareTo(e2.getDistance());
        	}
		});
    	
    	for (EntityManager subject : es) {
        	//this.begin();
        	HashMap<String, Object> vars = new HashMap<String, Object>();
            vars.put("ENTITY", subject.getCategory());
            vars.put("ENTITYNAME", subject.getName());
            vars.put("ENTITYID", String.valueOf(subject.getId()));
            vars.put("ENTITYPITCH", String.valueOf(subject.getPitch()));
            vars.put("ENTITYYAW", String.valueOf(subject.getYaw()));
	        vars.put("ENTITYXPOS", String.valueOf(subject.getPosX()));
	        vars.put("ENTITYYPOS", String.valueOf(subject.getPosY()));
	        vars.put("ENTITYZPOS", String.valueOf(subject.getPosZ()));
	        vars.put("ENTITYXPOSF", subject.getFposX());
	        vars.put("ENTITYYPOSF", subject.getFposY());
	        vars.put("ENTITYZPOSF", subject.getFposZ());
	        vars.put("ENTITYDISTANCE", String.valueOf(subject.getDistance()));
	        vars.put("CARDINALYAW", String.valueOf(subject.getYaw()));
	        vars.put("ENTITYUUID", subject.getUuid());
	        vars.put("ENTITYDIRECTION", subject.getDirection());
	        vars.put("ENTITYYAWTO", String.valueOf(subject.getYawOffset()));
	        vars.put("ENTITYPITCHTO", String.valueOf(subject.getPitchOffset()));
	        vars.put("ENTITYHEALTH", String.valueOf(subject.getHealth()));
	        this.items.add(vars);
	     
        }
    }
    
    @Override
    public void onInit() {
        //ScriptContext.MAIN.getCore().registerIterator(NAME, this.getClass());
    	System.out.println("[ENTITIES ITER] Init");
    	ScriptContext.MAIN.getCore().registerIterator(NAME, this.getClass());
    }
}