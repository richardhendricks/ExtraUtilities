/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Blocks;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.inventory.InventoryCrafting;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*  9:   */ 
/* 10:   */ public class RecipeGlove
/* 11:   */   extends ShapedOreRecipe
/* 12:   */ {
/* 13:   */   public RecipeGlove(Item glove)
/* 14:   */   {
/* 15:13 */     super(glove, new Object[] { "sW", "Ws", Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 32767), Character.valueOf('s'), Items.string });
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ItemStack getCraftingResult(InventoryCrafting var1)
/* 19:   */   {
/* 20:18 */     int a = -1;int b = -1;
/* 21:19 */     for (int i = 0; i < var1.getSizeInventory(); i++)
/* 22:   */     {
/* 23:20 */       ItemStack stackInSlot = var1.getStackInSlot(i);
/* 24:21 */       if ((stackInSlot != null) && (stackInSlot.getItem() == Item.getItemFromBlock(Blocks.wool))) {
/* 25:22 */         if (a != -1) {
/* 26:23 */           b = stackInSlot.getItemDamage();
/* 27:   */         } else {
/* 28:25 */           a = stackInSlot.getItemDamage();
/* 29:   */         }
/* 30:   */       }
/* 31:   */     }
/* 32:29 */     if ((a < 0) || (b < 0) || (b >= 16) || (a >= 16)) {
/* 33:30 */       return super.getCraftingResult(var1);
/* 34:   */     }
/* 35:32 */     ItemStack craftingResult = super.getCraftingResult(var1);
/* 36:33 */     craftingResult.setItemDamage(a << 4 | b);
/* 37:   */     
/* 38:35 */     return craftingResult;
/* 39:   */   }
/* 40:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeGlove
 * JD-Core Version:    0.7.0.1
 */