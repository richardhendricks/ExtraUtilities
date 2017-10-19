/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.helper.XURandom;
/*  4:   */ import cpw.mods.fml.common.FMLCommonHandler;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import java.util.Random;
/*  7:   */ import net.minecraft.block.Block;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ 
/* 10:   */ public class ItemBlockQED
/* 11:   */   extends ItemBlockMetadata
/* 12:   */ {
/* 13:12 */   public static Random rand = ;
/* 14:13 */   public static long prevTime = -2147483648L;
/* 15:14 */   public static int curRand = -1;
/* 16:   */   
/* 17:   */   public ItemBlockQED(Block p_i45328_1_)
/* 18:   */   {
/* 19:17 */     super(p_i45328_1_);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 23:   */   {
/* 24:23 */     if ((par1ItemStack.getItemDamage() == 0) && (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT))
/* 25:   */     {
/* 26:24 */       StackTraceElement[] stackTrace = new Throwable().getStackTrace();
/* 27:26 */       if ("net.minecraft.item.Item".equals(stackTrace[1].getClassName()))
/* 28:   */       {
/* 29:27 */         long curTime = System.currentTimeMillis();
/* 30:29 */         if ((curTime - prevTime > 1000L) || (curRand == -1)) {
/* 31:30 */           curRand = rand.nextInt(17);
/* 32:   */         }
/* 33:32 */         prevTime = curTime;
/* 34:   */         
/* 35:34 */         return "tile.extrautils:qed.rand." + curRand;
/* 36:   */       }
/* 37:   */     }
/* 38:38 */     return super.getUnlocalizedName(par1ItemStack);
/* 39:   */   }
/* 40:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockQED
 * JD-Core Version:    0.7.0.1
 */