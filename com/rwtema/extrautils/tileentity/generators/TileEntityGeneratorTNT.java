/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.helper.XURandom;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.init.Items;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class TileEntityGeneratorTNT
/* 12:   */   extends TileEntityGeneratorFurnace
/* 13:   */ {
/* 14:12 */   private static Random rand = ;
/* 15:   */   
/* 16:   */   public int transferLimit()
/* 17:   */   {
/* 18:16 */     return 400;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public double getFuelBurn(ItemStack item)
/* 22:   */   {
/* 23:21 */     if (item == null) {
/* 24:22 */       return 0.0D;
/* 25:   */     }
/* 26:25 */     if (item.getItem() == Items.gunpowder) {
/* 27:26 */       return 400.0D;
/* 28:   */     }
/* 29:29 */     if (item.getItem() == Item.getItemFromBlock(Blocks.tnt)) {
/* 30:30 */       return 6000.0D;
/* 31:   */     }
/* 32:33 */     return 0.0D;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public double genLevel()
/* 36:   */   {
/* 37:38 */     return 80.0D;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void doSpecial()
/* 41:   */   {
/* 42:43 */     if ((this.coolDown > 0.0D) && (rand.nextInt(80) == 0)) {
/* 43:44 */       this.worldObj.createExplosion(null, this.xCoord + rand.nextDouble() * 2.0D - 0.5D, this.yCoord + rand.nextDouble() * 2.0D - 0.5D, this.zCoord + rand.nextDouble() * 2.0D - 0.5D, 1.0F, false);
/* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   public boolean processInput()
/* 48:   */   {
/* 49:50 */     if (super.processInput())
/* 50:   */     {
/* 51:51 */       this.worldObj.createExplosion(null, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 5.0F, false);
/* 52:52 */       return true;
/* 53:   */     }
/* 54:54 */     return false;
/* 55:   */   }
/* 56:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorTNT
 * JD-Core Version:    0.7.0.1
 */