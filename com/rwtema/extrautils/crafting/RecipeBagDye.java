/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import com.rwtema.extrautils.item.ItemGoldenBag;
/*   5:    */ import net.minecraft.inventory.InventoryCrafting;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.item.crafting.IRecipe;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class RecipeBagDye
/*  11:    */   implements IRecipe
/*  12:    */ {
/*  13:    */   public boolean matches(InventoryCrafting inv, World p_77569_2_)
/*  14:    */   {
/*  15: 15 */     boolean foundBag = false;
/*  16: 16 */     boolean foundDye = false;
/*  17: 18 */     for (int i = 0; i < inv.getSizeInventory(); i++)
/*  18:    */     {
/*  19: 19 */       ItemStack item = inv.getStackInSlot(i);
/*  20: 21 */       if (item != null) {
/*  21: 22 */         if ((item.getItem() instanceof ItemGoldenBag))
/*  22:    */         {
/*  23: 23 */           if (foundBag) {
/*  24: 24 */             return false;
/*  25:    */           }
/*  26: 26 */           foundBag = true;
/*  27:    */         }
/*  28:    */         else
/*  29:    */         {
/*  30: 28 */           if (XUHelper.getDyeFromItemStack(item) == -1) {
/*  31: 29 */             return false;
/*  32:    */           }
/*  33: 31 */           foundDye = true;
/*  34:    */         }
/*  35:    */       }
/*  36:    */     }
/*  37: 36 */     return (foundBag) && (foundDye);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ItemStack getCraftingResult(InventoryCrafting p_77572_1_)
/*  41:    */   {
/*  42: 43 */     ItemStack result = null;
/*  43: 44 */     int[] color = new int[3];
/*  44: 45 */     int intensity = 0;
/*  45: 46 */     int numDyes = 0;
/*  46: 47 */     ItemGoldenBag itemBag = null;
/*  47: 50 */     for (int k = 0; k < p_77572_1_.getSizeInventory(); k++)
/*  48:    */     {
/*  49: 51 */       ItemStack itemstack = p_77572_1_.getStackInSlot(k);
/*  50: 53 */       if (itemstack != null) {
/*  51: 54 */         if ((itemstack.getItem() instanceof ItemGoldenBag))
/*  52:    */         {
/*  53: 55 */           itemBag = (ItemGoldenBag)itemstack.getItem();
/*  54: 57 */           if (result != null) {
/*  55: 58 */             return null;
/*  56:    */           }
/*  57: 61 */           result = itemstack.copy();
/*  58: 62 */           result.stackSize = 1;
/*  59: 64 */           if (itemBag.hasColor(itemstack))
/*  60:    */           {
/*  61: 65 */             int col = itemBag.getColor(result);
/*  62: 66 */             float r = (col >> 16 & 0xFF) / 255.0F;
/*  63: 67 */             float g = (col >> 8 & 0xFF) / 255.0F;
/*  64: 68 */             float b = (col & 0xFF) / 255.0F;
/*  65: 69 */             intensity = (int)(intensity + Math.max(r, Math.max(g, b)) * 255.0F);
/*  66: 70 */             color[0] = ((int)(color[0] + r * 255.0F));
/*  67: 71 */             color[1] = ((int)(color[1] + g * 255.0F));
/*  68: 72 */             color[2] = ((int)(color[2] + b * 255.0F));
/*  69: 73 */             numDyes++;
/*  70:    */           }
/*  71:    */         }
/*  72:    */         else
/*  73:    */         {
/*  74: 77 */           int c = XUHelper.getDyeFromItemStack(itemstack);
/*  75: 78 */           if (c == -1) {
/*  76: 79 */             return null;
/*  77:    */           }
/*  78: 82 */           float[] afloat = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[net.minecraft.block.BlockColored.func_150032_b(c)];
/*  79: 83 */           int r = (int)(afloat[0] * 255.0F);
/*  80: 84 */           int g = (int)(afloat[1] * 255.0F);
/*  81: 85 */           int b = (int)(afloat[2] * 255.0F);
/*  82: 86 */           intensity += Math.max(r, Math.max(g, b));
/*  83: 87 */           color[0] += r;
/*  84: 88 */           color[1] += g;
/*  85: 89 */           color[2] += b;
/*  86: 90 */           numDyes++;
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90: 95 */     if (itemBag == null) {
/*  91: 96 */       return null;
/*  92:    */     }
/*  93: 98 */     int r = color[0] / numDyes;
/*  94: 99 */     int g = color[1] / numDyes;
/*  95:100 */     int b = color[2] / numDyes;
/*  96:101 */     float i = intensity / numDyes;
/*  97:102 */     float max = Math.max(r, Math.max(g, b));
/*  98:103 */     r = (int)(r * i / max);
/*  99:104 */     g = (int)(g * i / max);
/* 100:105 */     b = (int)(b * i / max);
/* 101:106 */     int col = ((r << 8) + g << 8) + b;
/* 102:107 */     itemBag.setColor(result, col);
/* 103:108 */     return result;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getRecipeSize()
/* 107:    */   {
/* 108:116 */     return 10;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ItemStack getRecipeOutput()
/* 112:    */   {
/* 113:120 */     return null;
/* 114:    */   }
/* 115:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeBagDye
 * JD-Core Version:    0.7.0.1
 */