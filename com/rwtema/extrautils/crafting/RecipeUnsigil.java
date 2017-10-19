/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.helper.XURandom;
/*  5:   */ import java.util.Random;
/*  6:   */ import net.minecraft.init.Items;
/*  7:   */ import net.minecraft.inventory.InventoryCrafting;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.item.crafting.IRecipe;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class RecipeUnsigil
/* 13:   */   implements IRecipe
/* 14:   */ {
/* 15:14 */   Random rand = XURandom.getInstance();
/* 16:   */   
/* 17:   */   public boolean matches(InventoryCrafting inv, World world)
/* 18:   */   {
/* 19:18 */     boolean hasSigil = false;
/* 20:20 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 21:21 */       if ((inv.getStackInSlot(i) != null) && (inv.getStackInSlot(i).getItem() == ExtraUtils.divisionSigil))
/* 22:   */       {
/* 23:22 */         if (inv.getStackInSlot(i).hasTagCompound()) {
/* 24:23 */           return false;
/* 25:   */         }
/* 26:25 */         hasSigil = true;
/* 27:   */       }
/* 28:   */     }
/* 29:29 */     return hasSigil;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
/* 33:   */   {
/* 34:34 */     return new ItemStack(Items.iron_ingot, 1 + this.rand.nextInt(1 + this.rand.nextInt(1 + this.rand.nextInt(1 + this.rand.nextInt(1 + this.rand.nextInt(63))))));
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getRecipeSize()
/* 38:   */   {
/* 39:39 */     return 1;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public ItemStack getRecipeOutput()
/* 43:   */   {
/* 44:44 */     return new ItemStack(Items.iron_ingot, 1);
/* 45:   */   }
/* 46:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeUnsigil
 * JD-Core Version:    0.7.0.1
 */