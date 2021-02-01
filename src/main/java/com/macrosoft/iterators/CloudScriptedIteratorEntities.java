package com.macrosoft.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.macrosoft.EntityManager;
import com.macrosoft.actions.ModuleInfo;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

@APIVersion(ModuleInfo.API_VERSION)
public class CloudScriptedIteratorEntities extends AbstractEntitiesIterator {
	
	public CloudScriptedIteratorEntities() {
		super(null, null);
	}
	
	public CloudScriptedIteratorEntities(IScriptActionProvider provider, IMacro macro) {

    	super(provider, macro);
        EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        ArrayList<EntityManager> es = new ArrayList<EntityManager>();
        List<Entity> el = Minecraft.getMinecraft().theWorld.loadedEntityList;
        for (Entity entity : el) {
        	if(entity != thePlayer) {
        		EntityManager subject = new EntityManager(thePlayer, entity);
	        	if(entity instanceof EntityLiving) {
	        		es.add(subject);
	        	}
        	}
        }
        
        this.appendVars(es);
	        
    }
    
    @Override
    public void onInit() {
        ScriptContext.MAIN.getCore().registerIterator("entities", this.getClass());
    }
}