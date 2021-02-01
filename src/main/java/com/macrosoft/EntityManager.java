package com.macrosoft;

//import net.eq2online.macros.core.bridge.EntityUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vec3d;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityManager {

	private float prevRotationYaw;
	private Entity entity;
	private String category;
	private String name;
	private String uuid;
	private int id;
	private int pitch;
	private int pitchOffset;
	private int yaw;
	private int yawOffset;
	private int realYaw;
	private int posX;
	private int posY;
	private int posZ;
	private int health;
	private String fposX;
	private String fposY;
	private String fposZ;
	private int distance;
	private boolean isEnemy = false;
	private String facingDirection = "S";
	
	private final double DEG = 180.0D/Math.PI;
	
	public EntityManager(EntityPlayerSP thePlayer, Entity subject) {
	
		category = "TILE";
		float health = 0;
		boolean isEnemy = false;
		if(subject instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) subject;
            health = MathHelper.floor_double(entity.getHealth());
 	        category = "PLAYER";
 	    } else if(subject instanceof EntityLiving) {
 	    	EntityLiving entity = (EntityLiving) subject;
            health = MathHelper.floor_double(entity.getHealth());
            //isEnemy = (thePlayer == entity.getAITarget());
 	        category = "ENTITY";
 	    }

        int yaw = 0;
        int posX = MathHelper.floor_double(subject.posX);
        int posY = MathHelper.floor_double(subject.posY);
        int posZ = MathHelper.floor_double(subject.posZ);
        int distance = MathHelper.floor_double(subject.getDistance(thePlayer.posX, thePlayer.posY, thePlayer.posZ));
        String fposX = String.format("%.3f", Float.valueOf((float)subject.posX));
        String fposY = String.format("%.3f", Float.valueOf((float)subject.posY));
        String fposZ = String.format("%.3f", Float.valueOf((float)subject.posZ));
        
        
      //Kibado do Spthiel <3 Que kibou do MacroMod, Mumfrey é fera
        Vec3 thePlayerVec = thePlayer.getPositionVector();
		Vec3 subjectVec = subject.getPositionVector();
		
        double diffX = thePlayerVec.xCoord - subjectVec.xCoord;
		double diffY = thePlayerVec.yCoord - subjectVec.yCoord;
		double diffZ = thePlayerVec.zCoord - subjectVec.zCoord;
        double yawOffset = (Math.atan2(diffZ, diffX) * DEG - 90.0D);
		while (yawOffset < 0) {
			yawOffset += 360;
		}
		
		//Kibado do Spthiel <3 Ela é fera
		double dyFromEyes = diffY + thePlayer.getEyeHeight() - (subject.height / 2);
		double pitchOffset = (Math.atan2(dyFromEyes, Math.sqrt(diffX * diffX + diffZ * diffZ)) * DEG);
		while (pitchOffset < 0) {
			pitchOffset += 360;
		}
		
        int realYaw = yaw - 180;
        int pitch = (int)(subject.rotationPitch % 360.0f);
        for (yaw = (int)(subject.rotationYaw % 360.0f); yaw < 0; yaw += 360) {
        }
        while (realYaw < 0) {
            realYaw += 360;
        }
        while (pitch < 0) {
            pitch += 360;
        }
        if (this.prevRotationYaw != subject.rotationYaw) {
            this.prevRotationYaw = subject.rotationYaw;
            this.facingDirection = "S";
            if (yaw >= 45 && yaw < 135) {
                this.facingDirection = "W";
            }
            if (yaw >= 135 && yaw < 225) {
                this.facingDirection = "N";
            }
            if (yaw >= 225 && yaw < 315) {
                this.facingDirection = "E";
            }
        }
        
        
        //this.category = category;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.fposX = fposX;
        this.fposY = fposY;
        this.fposZ = fposZ;
        this.yaw = yaw;
        this.id = subject.getEntityId();
        this.uuid = subject.getUniqueID().toString();		
        this.name = subject.getCommandSenderName();
        this.pitch = pitch;
        this.realYaw = realYaw;
        this.distance = distance;
        this.yawOffset = MathHelper.floor_double(yawOffset);
        this.pitchOffset = MathHelper.floor_double(pitchOffset);
        this.health = MathHelper.floor_double(health);
        this.isEnemy = isEnemy;
        this.entity = subject;
        //this.facingDirection = facingDirection;
     
	}
	
	static public boolean matchType(String[] types, Entity entity) {
		for (String type : types) {
		    if (("PLAYER".equalsIgnoreCase(type.trim())) && (entity instanceof EntityPlayer)) {
		        return true;
		    } else if (("ENTITY".equalsIgnoreCase(type.trim())) && (entity instanceof EntityLiving)) {
		        return true;
		    } else if ("TILE".equalsIgnoreCase(type.trim())) {
		    	return true;
		    }
		}
    	return false;
    }
	
	static public boolean matchType(String type, Entity entity) {
		
		if (("PLAYER".equalsIgnoreCase(type.trim())) && (entity instanceof EntityPlayer)) {
	        return true;
	    } else if (("ENTITY".equalsIgnoreCase(type.trim())) && (entity instanceof EntityLiving)) {
	        return true;
	    } else if ("TILE".equalsIgnoreCase(type.trim())) {
	    	return true;
	    }   
		
    	return false;
    }
	
	public String getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getPitch() {
		return pitch;
	}

	public int getYaw() {
		return yaw;
	}
	
	public int getRealYaw() {
		return realYaw;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getPosZ() {
		return posZ;
	}

	public String getFposX() {
		return fposX;
	}

	public String getFposY() {
		return fposY;
	}

	public String getFposZ() {
		return fposZ;
	}

	public int getDistance() {
		return distance;
	}

	public String getDirection() {
		return facingDirection;
	}

	public String getUuid() {
		return uuid;
	}

	public int getPitchOffset() {
		return pitchOffset;
	}

	public int getYawOffset() {
		return yawOffset;
	}

	public int getHealth() {
		return health;
	}

	public boolean isEnemy() {
		return isEnemy;
	}

	public Entity getEntity() {
		return entity;
	}
	
}
