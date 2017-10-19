/*  1:   */ package com.rwtema.extrautils.tileentity;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.tileentity.TileEntity;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ import net.minecraft.world.WorldProvider;
/*  9:   */ 
/* 10:   */ public class TileEntityRainMuffler
/* 11:   */   extends TileEntity
/* 12:   */ {
/* 13:   */   public static final int range = 8;
/* 14:   */   public static final int rain_range = 4096;
/* 15: 9 */   public static boolean playerNeedsMuffler = true;
/* 16:10 */   public static boolean playerNeedsMufflerInstantCheck = false;
/* 17:11 */   public static int curDimension = -100;
/* 18:12 */   public static int curX = -1;
/* 19:12 */   public static int curY = -1;
/* 20:12 */   public static int curZ = -1;
/* 21:   */   
/* 22:   */   public TileEntityRainMuffler()
/* 23:   */   {
/* 24:15 */     if ((this.worldObj == null) || (!this.worldObj.isClient)) {
/* 25:16 */       return;
/* 26:   */     }
/* 27:20 */     if (((curDimension != this.worldObj.provider.dimensionId) || (playerNeedsMuffler)) && 
/* 28:21 */       (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 1) && 
/* 29:22 */       (getDistanceFrom(ExtraUtilsMod.proxy.getClientPlayer().posX, ExtraUtilsMod.proxy.getClientPlayer().posY, ExtraUtilsMod.proxy.getClientPlayer().posZ) < 4096.0D))
/* 30:   */     {
/* 31:23 */       curX = this.xCoord;
/* 32:24 */       curY = this.yCoord;
/* 33:25 */       curZ = this.zCoord;
/* 34:26 */       curDimension = this.worldObj.provider.dimensionId;
/* 35:27 */       playerNeedsMuffler = false;
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void updateEntity()
/* 40:   */   {
/* 41:35 */     if ((this.worldObj == null) || (!this.worldObj.isClient)) {
/* 42:36 */       return;
/* 43:   */     }
/* 44:39 */     if ((!playerNeedsMufflerInstantCheck) && (this.worldObj.getWorldTime() % 100L != 0L)) {
/* 45:40 */       return;
/* 46:   */     }
/* 47:43 */     if ((playerNeedsMuffler) && (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 1)) {
/* 48:45 */       if (getDistanceFrom(ExtraUtilsMod.proxy.getClientPlayer().posX, ExtraUtilsMod.proxy.getClientPlayer().posY, ExtraUtilsMod.proxy.getClientPlayer().posZ) < 4096.0D)
/* 49:   */       {
/* 50:46 */         curX = this.xCoord;
/* 51:47 */         curY = this.yCoord;
/* 52:48 */         curZ = this.zCoord;
/* 53:49 */         curDimension = this.worldObj.provider.dimensionId;
/* 54:50 */         playerNeedsMuffler = false;
/* 55:   */       }
/* 56:   */     }
/* 57:   */   }
/* 58:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityRainMuffler
 * JD-Core Version:    0.7.0.1
 */