/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.inventory.InventoryCrafting;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.item.crafting.IRecipe;
/*  8:   */ import net.minecraft.nbt.NBTTagCompound;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class RecipeHorseTransmutation
/* 12:   */   implements IRecipe
/* 13:   */ {
/* 14:   */   public boolean matches(InventoryCrafting inventorycrafting, World world)
/* 15:   */   {
/* 16:13 */     return getCraftingResult(inventorycrafting) != null;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
/* 20:   */   {
/* 21:18 */     if (inventorycrafting.getSizeInventory() != 9) {
/* 22:19 */       return null;
/* 23:   */     }
/* 24:22 */     int curRow = -1;
/* 25:24 */     for (int x = 0; x < 3; x++) {
/* 26:25 */       for (int y = 0; y < 3; y++) {
/* 27:26 */         if (inventorycrafting.getStackInRowAndColumn(x, y) != null)
/* 28:   */         {
/* 29:27 */           if (curRow == -1)
/* 30:   */           {
/* 31:28 */             if (y == 0) {
/* 32:29 */               curRow = x;
/* 33:   */             } else {
/* 34:31 */               return null;
/* 35:   */             }
/* 36:   */           }
/* 37:33 */           else if (x != curRow) {
/* 38:34 */             return null;
/* 39:   */           }
/* 40:   */         }
/* 41:36 */         else if (curRow == x) {
/* 42:37 */           return null;
/* 43:   */         }
/* 44:   */       }
/* 45:   */     }
/* 46:42 */     if (curRow == -1) {
/* 47:43 */       return null;
/* 48:   */     }
/* 49:46 */     int x = curRow;
/* 50:47 */     ItemStack n = inventorycrafting.getStackInRowAndColumn(x, 0);
/* 51:48 */     ItemStack s = inventorycrafting.getStackInRowAndColumn(x, 1);
/* 52:49 */     ItemStack d = inventorycrafting.getStackInRowAndColumn(x, 2);
/* 53:51 */     if ((ExtraUtils.divisionSigil == null) || (s.getItem() != ExtraUtils.divisionSigil)) {
/* 54:52 */       return null;
/* 55:   */     }
/* 56:55 */     if ((!s.hasTagCompound()) || (!s.getTagCompound().hasKey("damage"))) {
/* 57:56 */       return null;
/* 58:   */     }
/* 59:59 */     if ((n.getItem() == Items.iron_ingot) || (d.getItem() == Items.diamond)) {
/* 60:60 */       return null;
/* 61:   */     }
/* 62:63 */     if ((n.getItem() != ExtraUtils.goldenLasso) || (!n.hasTagCompound())) {
/* 63:64 */       return null;
/* 64:   */     }
/* 65:67 */     if ((!n.getTagCompound().hasKey("id")) || (!n.getTagCompound().getString("id").equals("EntityHorse"))) {
/* 66:68 */       return null;
/* 67:   */     }
/* 68:71 */     if (!n.getTagCompound().hasKey("Type")) {
/* 69:72 */       return null;
/* 70:   */     }
/* 71:75 */     int type = n.getTagCompound().getInteger("Type");
/* 72:76 */     ItemStack r = n.copy();
/* 73:78 */     if ((d.getItem() == Items.rotten_flesh) && (type != 3)) {
/* 74:79 */       r.getTagCompound().setInteger("Type", 3);
/* 75:   */     }
/* 76:82 */     if ((d.getItem() == Items.bone) && (type != 4)) {
/* 77:83 */       r.getTagCompound().setInteger("Type", 4);
/* 78:   */     }
/* 79:86 */     return r;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public int getRecipeSize()
/* 83:   */   {
/* 84:91 */     return 4;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public ItemStack getRecipeOutput()
/* 88:   */   {
/* 89:96 */     return null;
/* 90:   */   }
/* 91:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeHorseTransmutation
 * JD-Core Version:    0.7.0.1
 */