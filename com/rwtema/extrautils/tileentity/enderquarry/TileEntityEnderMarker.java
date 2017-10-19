/*   1:    */ package com.rwtema.extrautils.tileentity.enderquarry;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.tileentity.TileEntity;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ import net.minecraft.world.WorldProvider;
/*   9:    */ 
/*  10:    */ public class TileEntityEnderMarker
/*  11:    */   extends TileEntity
/*  12:    */   implements IChunkLoad
/*  13:    */ {
/*  14: 11 */   public static List<int[]> markers = new ArrayList();
/*  15:    */   
/*  16:    */   public static int[] getCoord(TileEntity tile)
/*  17:    */   {
/*  18: 14 */     return new int[] { tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord };
/*  19:    */   }
/*  20:    */   
/*  21: 17 */   public boolean init = false;
/*  22:    */   
/*  23:    */   public boolean canUpdate()
/*  24:    */   {
/*  25: 20 */     return true;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean shouldRefresh(Block oldID, Block newID, int oldMeta, int newMeta, World world, int x, int y, int z)
/*  29:    */   {
/*  30: 26 */     return oldID != newID;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void updateEntity()
/*  34:    */   {
/*  35: 31 */     if (!this.init) {
/*  36: 32 */       onChunkLoad();
/*  37:    */     }
/*  38: 33 */     super.updateEntity();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void invalidate()
/*  42:    */   {
/*  43: 38 */     super.invalidate();
/*  44: 40 */     if (this.worldObj.isClient) {
/*  45: 41 */       return;
/*  46:    */     }
/*  47: 43 */     int[] myCoord = getCoord();
/*  48:    */     
/*  49: 45 */     List<int[]> toUpdate = new ArrayList();
/*  50: 47 */     for (int i = 0; i < markers.size(); i++)
/*  51:    */     {
/*  52: 48 */       int[] coord = (int[])markers.get(i);
/*  53: 50 */       if ((myCoord[0] == coord[0]) && (myCoord[2] == coord[2])) {
/*  54: 51 */         if ((myCoord[3] == coord[3]) && (myCoord[1] == coord[1]))
/*  55:    */         {
/*  56: 52 */           markers.remove(i);
/*  57: 53 */           i--;
/*  58:    */         }
/*  59: 54 */         else if ((myCoord[3] == coord[3]) || (myCoord[1] == coord[1]))
/*  60:    */         {
/*  61: 55 */           toUpdate.add(coord);
/*  62:    */         }
/*  63:    */       }
/*  64:    */     }
/*  65: 60 */     for (int[] coord : toUpdate)
/*  66:    */     {
/*  67: 61 */       TileEntity tile = this.worldObj.getTileEntity(coord[1], coord[2], coord[3]);
/*  68: 63 */       if ((tile instanceof TileEntityEnderMarker)) {
/*  69: 64 */         ((TileEntityEnderMarker)tile).recheck();
/*  70:    */       }
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int[] getCoord()
/*  75:    */   {
/*  76: 70 */     return getCoord(this);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void recheck()
/*  80:    */   {
/*  81: 74 */     int[] myCoord = getCoord();
/*  82: 75 */     int flag = 0;
/*  83: 77 */     for (int[] coord : markers) {
/*  84: 78 */       if ((myCoord[0] == coord[0]) && (myCoord[2] == coord[2]) && (
/*  85: 79 */         (myCoord[1] != coord[1]) || (myCoord[3] != coord[3]))) {
/*  86: 81 */         if (myCoord[1] == coord[1]) {
/*  87: 82 */           flag |= (myCoord[3] < coord[3] ? 1 : 2);
/*  88: 83 */         } else if (myCoord[3] == coord[3]) {
/*  89: 84 */           flag |= (myCoord[1] < coord[1] ? 4 : 8);
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93: 89 */     this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, flag, 2);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void onChunkLoad()
/*  97:    */   {
/*  98: 94 */     if (this.init) {
/*  99: 95 */       return;
/* 100:    */     }
/* 101: 96 */     this.init = true;
/* 102: 98 */     if ((this.worldObj == null) || (this.worldObj.isClient)) {
/* 103: 99 */       return;
/* 104:    */     }
/* 105:101 */     int[] myCoord = getCoord();
/* 106:102 */     List<int[]> toUpdate = new ArrayList();
/* 107:104 */     for (int[] coord : markers) {
/* 108:105 */       if ((myCoord[0] == coord[0]) && (myCoord[2] == coord[2]))
/* 109:    */       {
/* 110:106 */         if ((myCoord[3] == coord[3]) && (myCoord[1] == coord[1])) {
/* 111:107 */           return;
/* 112:    */         }
/* 113:108 */         if ((myCoord[3] == coord[3]) || (myCoord[1] == coord[1])) {
/* 114:109 */           toUpdate.add(coord);
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:114 */     markers.add(myCoord);
/* 119:115 */     recheck();
/* 120:117 */     for (int[] coord : toUpdate)
/* 121:    */     {
/* 122:118 */       TileEntity tile = this.worldObj.getTileEntity(coord[1], coord[2], coord[3]);
/* 123:120 */       if ((tile instanceof TileEntityEnderMarker)) {
/* 124:121 */         ((TileEntityEnderMarker)tile).recheck();
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderquarry.TileEntityEnderMarker
 * JD-Core Version:    0.7.0.1
 */