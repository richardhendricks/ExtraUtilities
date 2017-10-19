/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Items;
/*  4:   */ import net.minecraft.item.ItemFood;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraft.nbt.NBTTagCompound;
/*  7:   */ 
/*  8:   */ public class TileEntityGeneratorFood
/*  9:   */   extends TileEntityGeneratorFurnace
/* 10:   */ {
/* 11: 9 */   double curLevel = 0.0D;
/* 12:   */   
/* 13:   */   public int transferLimit()
/* 14:   */   {
/* 15:13 */     return 160;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public double genLevel()
/* 19:   */   {
/* 20:18 */     return this.curLevel;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public double getGenLevelForStack(ItemStack item)
/* 24:   */   {
/* 25:23 */     if (item != null)
/* 26:   */     {
/* 27:24 */       if ((item.getItem() instanceof ItemFood)) {
/* 28:25 */         return scale(((ItemFood)item.getItem()).func_150905_g(item), 8.0D) * 4.0D;
/* 29:   */       }
/* 30:26 */       if (item.getItem() == Items.cake) {
/* 31:27 */         return 64.0D;
/* 32:   */       }
/* 33:   */     }
/* 34:31 */     return 0.0D;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public double scale(double a, double h)
/* 38:   */   {
/* 39:35 */     if (a < h) {
/* 40:36 */       return a;
/* 41:   */     }
/* 42:38 */     double b = 0.0D;
/* 43:39 */     double s = 1.0D;
/* 44:40 */     for (double i = 0.0D; i <= a; i += h)
/* 45:   */     {
/* 46:41 */       double da = Math.min(h, a - i);
/* 47:42 */       b += da * s;
/* 48:43 */       s *= 0.75D;
/* 49:   */     }
/* 50:45 */     return b;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void adjustGenLevel(ItemStack item)
/* 54:   */   {
/* 55:50 */     this.curLevel = getGenLevelForStack(item);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public double getFuelBurn(ItemStack item)
/* 59:   */   {
/* 60:55 */     if (item != null)
/* 61:   */     {
/* 62:56 */       if ((item.getItem() instanceof ItemFood))
/* 63:   */       {
/* 64:57 */         if (((ItemFood)item.getItem()).func_150905_g(item) > 0) {
/* 65:58 */           return Math.ceil(scale(((ItemFood)item.getItem()).func_150906_h(item), 0.8D) * 1800.0D);
/* 66:   */         }
/* 67:60 */         return 0.0D;
/* 68:   */       }
/* 69:61 */       if (item.getItem() == Items.cake) {
/* 70:62 */         return 1500.0D;
/* 71:   */       }
/* 72:   */     }
/* 73:66 */     return 0.0D;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public void readFromNBT(NBTTagCompound nbt)
/* 77:   */   {
/* 78:71 */     super.readFromNBT(nbt);
/* 79:72 */     this.curLevel = nbt.getDouble("curLevel");
/* 80:   */   }
/* 81:   */   
/* 82:   */   public void writeToNBT(NBTTagCompound nbt)
/* 83:   */   {
/* 84:77 */     super.writeToNBT(nbt);
/* 85:78 */     nbt.setDouble("curLevel", this.curLevel);
/* 86:   */   }
/* 87:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorFood
 * JD-Core Version:    0.7.0.1
 */