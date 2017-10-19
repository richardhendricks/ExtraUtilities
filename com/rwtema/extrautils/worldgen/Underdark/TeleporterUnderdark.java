/*   1:    */ package com.rwtema.extrautils.worldgen.Underdark;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.tileentity.TileEntityPortal;
/*   5:    */ import java.util.Map;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.tileentity.TileEntity;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.Teleporter;
/*  11:    */ import net.minecraft.world.WorldProvider;
/*  12:    */ import net.minecraft.world.WorldServer;
/*  13:    */ import net.minecraft.world.chunk.Chunk;
/*  14:    */ 
/*  15:    */ public class TeleporterUnderdark
/*  16:    */   extends Teleporter
/*  17:    */ {
/*  18:    */   private final WorldServer worldServerInstance;
/*  19:    */   
/*  20:    */   public TeleporterUnderdark(WorldServer par1WorldServer)
/*  21:    */   {
/*  22: 17 */     super(par1WorldServer);
/*  23: 18 */     this.worldServerInstance = par1WorldServer;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void placeInPortal(Entity entity, double x, double y, double z, float r)
/*  27:    */   {
/*  28: 26 */     if (!placeInExistingPortal(entity, x, y, z, r)) {
/*  29: 27 */       if (this.worldServerInstance.provider.dimensionId != ExtraUtils.underdarkDimID)
/*  30:    */       {
/*  31: 28 */         y = this.worldServerInstance.getTopSolidOrLiquidBlock((int)x, (int)z);
/*  32: 29 */         entity.setLocationAndAngles(x, y, z, entity.rotationYaw, 0.0F);
/*  33:    */       }
/*  34:    */       else
/*  35:    */       {
/*  36: 31 */         makePortal(entity);
/*  37:    */       }
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public TileEntity findPortalInChunk(double x, double z)
/*  42:    */   {
/*  43: 37 */     Chunk chunk = this.worldServerInstance.getChunkFromBlockCoords((int)x, (int)z);
/*  44: 39 */     for (Object tile : chunk.chunkTileEntityMap.values()) {
/*  45: 40 */       if ((tile instanceof TileEntityPortal)) {
/*  46: 41 */         return (TileEntity)tile;
/*  47:    */       }
/*  48:    */     }
/*  49: 45 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float r)
/*  53:    */   {
/*  54: 53 */     TileEntity destPortal = null;
/*  55: 55 */     for (int s = 0; (s <= 5) && (destPortal == null); s++) {
/*  56: 56 */       for (int dx = -s; dx <= s; dx++) {
/*  57: 57 */         for (int dz = -s; dz <= s; dz++) {
/*  58: 58 */           if (destPortal == null) {
/*  59: 59 */             destPortal = findPortalInChunk(x + dx * 16, z + dz * 16);
/*  60:    */           }
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64: 65 */     if (destPortal != null)
/*  65:    */     {
/*  66: 66 */       entity.setLocationAndAngles(destPortal.xCoord + 0.5D, destPortal.yCoord + 1, destPortal.zCoord + 0.5D, entity.rotationYaw, entity.rotationPitch);
/*  67: 67 */       entity.motionX = (entity.motionY = entity.motionZ = 0.0D);
/*  68: 68 */       return true;
/*  69:    */     }
/*  70: 70 */     return false;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean makePortal(Entity entity)
/*  74:    */   {
/*  75: 76 */     int ex = MathHelper.floor_double(entity.posX);
/*  76: 77 */     int ey = MathHelper.floor_double(entity.posY) - 1;
/*  77: 78 */     int ez = MathHelper.floor_double(entity.posZ);
/*  78:    */     
/*  79:    */ 
/*  80: 81 */     ey /= 5;
/*  81: 82 */     ey += 139;
/*  82: 84 */     if (ey > 247) {
/*  83: 85 */       ey = 247;
/*  84:    */     }
/*  85: 88 */     for (int x = -3; x <= 3; x++) {
/*  86: 89 */       for (int z = -3; z <= 3; z++) {
/*  87: 90 */         for (int y = -2; y <= 4; y++) {
/*  88: 91 */           if ((x == 0) && (y == -1) && (z == 0))
/*  89:    */           {
/*  90: 92 */             this.worldServerInstance.setBlock(ex + x, ey + y, ez + z, ExtraUtils.portal, 1, 2);
/*  91: 93 */             this.worldServerInstance.scheduleBlockUpdate(ex + x, ey + y, ez + z, ExtraUtils.portal, 1);
/*  92:    */           }
/*  93: 94 */           else if ((x == -3) || (x == 3) || (y <= -1) || (y == 4) || (z == -3) || (z == 3))
/*  94:    */           {
/*  95: 95 */             this.worldServerInstance.setBlock(ex + x, ey + y, ez + z, Blocks.cobblestone);
/*  96:    */           }
/*  97: 97 */           else if ((y == 0) && ((x == 2) || (x == -2) || (z == 2) || (z == -2)))
/*  98:    */           {
/*  99: 98 */             this.worldServerInstance.setBlock(ex + x, ey + y, ez + z, Blocks.torch, 5, 3);
/* 100:    */           }
/* 101:    */           else
/* 102:    */           {
/* 103:100 */             this.worldServerInstance.setBlock(ex + x, ey + y, ez + z, Blocks.air);
/* 104:    */           }
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:105 */     entity.setLocationAndAngles(ex + 0.5D, ey, ez + 0.5D, entity.rotationYaw, 0.0F);
/* 109:106 */     entity.motionX = (entity.motionY = entity.motionZ = 0.0D);
/* 110:    */     
/* 111:108 */     return true;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void removeStalePortalLocations(long par1) {}
/* 115:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.Underdark.TeleporterUnderdark
 * JD-Core Version:    0.7.0.1
 */