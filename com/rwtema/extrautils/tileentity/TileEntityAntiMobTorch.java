/*  1:   */ package com.rwtema.extrautils.tileentity;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.EventHandlerServer;
/*  4:   */ import com.rwtema.extrautils.block.BlockChandelier;
/*  5:   */ import com.rwtema.extrautils.block.BlockMagnumTorch;
/*  6:   */ import java.util.List;
/*  7:   */ import net.minecraft.tileentity.TileEntity;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ import net.minecraft.world.WorldProvider;
/* 10:   */ 
/* 11:   */ public class TileEntityAntiMobTorch
/* 12:   */   extends TileEntity
/* 13:   */   implements IAntiMobTorch
/* 14:   */ {
/* 15:   */   public static int[] getCoord(TileEntity tile)
/* 16:   */   {
/* 17:10 */     return new int[] { tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord };
/* 18:   */   }
/* 19:   */   
/* 20:   */   public float getHorizontalTorchRangeSquared()
/* 21:   */   {
/* 22:15 */     if ((getBlockType() instanceof BlockMagnumTorch)) {
/* 23:16 */       return 16384.0F;
/* 24:   */     }
/* 25:17 */     if ((getBlockType() instanceof BlockChandelier)) {
/* 26:18 */       return 256.0F;
/* 27:   */     }
/* 28:20 */     return -1.0F;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public float getVerticalTorchRangeSquared()
/* 32:   */   {
/* 33:26 */     if ((getBlockType() instanceof BlockMagnumTorch)) {
/* 34:27 */       return 1024.0F;
/* 35:   */     }
/* 36:28 */     if ((getBlockType() instanceof BlockChandelier)) {
/* 37:29 */       return 256.0F;
/* 38:   */     }
/* 39:31 */     return -1.0F;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void invalidate()
/* 43:   */   {
/* 44:40 */     EventHandlerServer.magnumTorchRegistry.remove(getCoord());
/* 45:41 */     super.invalidate();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void onChunkUnload()
/* 49:   */   {
/* 50:46 */     super.onChunkUnload();
/* 51:47 */     EventHandlerServer.magnumTorchRegistry.remove(getCoord());
/* 52:   */   }
/* 53:   */   
/* 54:   */   public int[] getCoord()
/* 55:   */   {
/* 56:51 */     return getCoord(this);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void validate()
/* 60:   */   {
/* 61:57 */     int[] myCoord = getCoord();
/* 62:59 */     for (int i = 0; i < EventHandlerServer.magnumTorchRegistry.size(); i++)
/* 63:   */     {
/* 64:60 */       int[] coord = (int[])EventHandlerServer.magnumTorchRegistry.get(i);
/* 65:62 */       if ((myCoord[0] == coord[0]) && (myCoord[1] == coord[1]) && (myCoord[2] == coord[2]) && (myCoord[3] == coord[3])) {
/* 66:63 */         return;
/* 67:   */       }
/* 68:   */     }
/* 69:67 */     EventHandlerServer.magnumTorchRegistry.add(myCoord);
/* 70:   */   }
/* 71:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityAntiMobTorch
 * JD-Core Version:    0.7.0.1
 */