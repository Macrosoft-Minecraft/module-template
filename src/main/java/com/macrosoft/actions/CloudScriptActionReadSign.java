package com.macrosoft.actions;
import com.bezouro.modules.CloudScript.Core.CloudScriptAction;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.parser.ScriptCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
//import net.minecraft.util.math.BlockPos;
import net.minecraft.util.BlockPos;

@APIVersion(ModuleInfo.API_VERSION)
public class CloudScriptActionReadSign extends CloudScriptAction {

	/**
     * Module members must have a noarg constructor
     */
    /*public CloudScriptActionReadSign() {
        // Context is the context for this action, action name must be lowercase
        super(ScriptContext.MAIN, "readsign");
    } */
    
    public CloudScriptActionReadSign() {
        super("readsign");
    }
    
    private TileEntitySign sign;
    private String[] signLines;
    private int signUpdateTicks;
    		
    /**
     * Execute the action
     * 
     * @see net.eq2online.macros.scripting.parser.ScriptAction#execute(
     *      net.eq2online.macros.scripting.api.IScriptActionProvider,
     *      net.eq2online.macros.scripting.api.IMacro,
     *      net.eq2online.macros.scripting.api.IMacroAction,
     *      java.lang.String, java.lang.String[])
     */
    @Override
    public IReturnValue executeAction(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
		
    	if (params.length > 2) {
    		
    		try {
    			
    			
    			String posXStr = ScriptCore.parseVars(provider, macro, params[0].toLowerCase(), false);
    			String posYStr = ScriptCore.parseVars(provider, macro, params[1].toLowerCase(), false);
    			String posZStr = ScriptCore.parseVars(provider, macro, params[2].toLowerCase(), false);
    			
    			int posX = Integer.parseInt(posXStr);
	    		int posY = Integer.parseInt(posYStr);
	    		int posZ = Integer.parseInt(posZStr);
	    		
	    		BlockPos blockPos = new BlockPos(posX, posY, posZ);
		        //IBlockState blockState = this.mc.world.getBlockState(blockPos);
		        IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
		        this.signLines = new String[]{"", "", "", ""};
		        Block block = blockState.getBlock();
		    	TileEntity teSign;
		    	if (block instanceof BlockSign && (teSign = Minecraft.getMinecraft().theWorld.getTileEntity(blockPos)) instanceof TileEntitySign) {
		    		if (teSign != this.sign || this.signUpdateTicks > 50) {
		                this.signUpdateTicks = 0;
		                this.sign = (TileEntitySign)((Object)teSign);
		                for (int pos = 0; pos < 4; ++pos) {
		                    this.signLines[pos] = CloudScriptActionReadSign.readSignLine(this.sign, pos);
		                }
		            }
		    	}
	    		if (params.length > 3) {
	    			//String arrayName = provider.expand(macro, params[3], false).toLowerCase();
	    			String arrayName = ScriptCore.parseVars(provider, macro, params[3].toLowerCase(), false);
	    	        provider.clearArray(macro, arrayName);
	    	        for (String line : this.signLines) {
	    	        	//System.out.println("[READSIGN] " + line);
	    	            provider.pushValueToArray(macro, arrayName, line);
	    	        }
	        	}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("[READSIGN exception] " + e.getMessage());
				// TODO: handle exception
			}
	    		
    	}
    	
        return null;
    }
    
    static String readSignLine(TileEntitySign sign, int pos) {
        return sign.signText[pos] != null ? sign.signText[pos].getFormattedText() : "";
    }
    
    /**
     * Called after this action is initialised, the action should register
     * with the script core.
     * 
     * @see net.eq2online.macros.scripting.parser.ScriptAction#onInit()
     */
    @Override
    public void onInit() {
        //this.context.getCore().registerScriptAction(this);
    	System.out.println("[READSIGN ACTION] Init");
        registerAction(this);
    }
	
}
