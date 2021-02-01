package com.macrosoft.iterators;

import java.util.ArrayList;
import java.util.List;

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
//public class CloudScriptedIteratorEntities extends ScriptedIterator {
public class CloudScriptedIteratorPlayerEntities extends AbstractEntitiesIterator {
	
	protected String typeEntity = "player";
	
	public CloudScriptedIteratorPlayerEntities() {
		super(null, null);
	}
	
	public CloudScriptedIteratorPlayerEntities(IScriptActionProvider provider, IMacro macro) {
		super(provider, macro);
        EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        ArrayList<EntityManager> es = new ArrayList<EntityManager>();
        List<Entity> el = Minecraft.getMinecraft().theWorld.loadedEntityList;
        for (Entity entity : el) {
        	if(entity != thePlayer) {
        		EntityManager subject = new EntityManager(thePlayer, entity);
	        	if(entity instanceof EntityPlayer) {
	        		es.add(subject);
	        	}
        	}
        }
        
        this.appendVars(es);
	}
	
	@Override
    public void onInit() {
        ScriptContext.MAIN.getCore().registerIterator("playerentities", this.getClass());
    }
}