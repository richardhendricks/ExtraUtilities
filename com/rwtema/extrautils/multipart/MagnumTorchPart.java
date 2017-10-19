/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Cuboid6;
/*  4:   */ import codechicken.multipart.IRandomDisplayTick;
/*  5:   */ import codechicken.multipart.TileMultipart;
/*  6:   */ import codechicken.multipart.minecraft.McMetaPart;
/*  7:   */ import com.rwtema.extrautils.EventHandlerServer;
/*  8:   */ import com.rwtema.extrautils.ExtraUtils;
/*  9:   */ import com.rwtema.extrautils.tileentity.IAntiMobTorch;
/* 10:   */ import com.rwtema.extrautils.tileentity.TileEntityAntiMobTorch;
/* 11:   */ import cpw.mods.fml.relauncher.Side;
/* 12:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 13:   */ import java.util.ArrayList;
/* 14:   */ import java.util.List;
/* 15:   */ import java.util.Random;
/* 16:   */ import net.minecraft.block.Block;
/* 17:   */ import net.minecraft.world.World;
/* 18:   */ import net.minecraft.world.WorldProvider;
/* 19:   */ 
/* 20:   */ public class MagnumTorchPart
/* 21:   */   extends McMetaPart
/* 22:   */   implements IRandomDisplayTick, IAntiMobTorch
/* 23:   */ {
/* 24:   */   public Cuboid6 getBounds()
/* 25:   */   {
/* 26:20 */     return new Cuboid6(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Block getBlock()
/* 30:   */   {
/* 31:25 */     return ExtraUtils.magnumTorch;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Iterable<Cuboid6> getCollisionBoxes()
/* 35:   */   {
/* 36:30 */     ArrayList t = new ArrayList();
/* 37:31 */     t.add(getBounds());
/* 38:32 */     return t;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getType()
/* 42:   */   {
/* 43:37 */     return "XU|MagnumTorch";
/* 44:   */   }
/* 45:   */   
/* 46:   */   @SideOnly(Side.CLIENT)
/* 47:   */   public void randomDisplayTick(Random random)
/* 48:   */   {
/* 49:43 */     ExtraUtils.magnumTorch.randomDisplayTick(tile().getWorldObj(), x(), y(), z(), random);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void onWorldJoin()
/* 53:   */   {
/* 54:48 */     int[] myCoord = TileEntityAntiMobTorch.getCoord(tile());
/* 55:51 */     for (int i = 0; i < EventHandlerServer.magnumTorchRegistry.size(); i++)
/* 56:   */     {
/* 57:52 */       int[] coord = (int[])EventHandlerServer.magnumTorchRegistry.get(i);
/* 58:54 */       if ((myCoord[0] == coord[0]) && (myCoord[1] == coord[1]) && (myCoord[2] == coord[2]) && (myCoord[3] == coord[3])) {
/* 59:55 */         return;
/* 60:   */       }
/* 61:   */     }
/* 62:59 */     EventHandlerServer.magnumTorchRegistry.add(myCoord);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void onWorldSeparate()
/* 66:   */   {
/* 67:64 */     int[] myCoord = { getWorld().provider.dimensionId, x(), y(), z() };
/* 68:65 */     EventHandlerServer.magnumTorchRegistry.remove(myCoord);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public float getHorizontalTorchRangeSquared()
/* 72:   */   {
/* 73:70 */     return 16384.0F;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public float getVerticalTorchRangeSquared()
/* 77:   */   {
/* 78:75 */     return 1024.0F;
/* 79:   */   }
/* 80:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.MagnumTorchPart
 * JD-Core Version:    0.7.0.1
 */