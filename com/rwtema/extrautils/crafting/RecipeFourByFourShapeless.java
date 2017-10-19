/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.inventory.InventoryCrafting;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraft.item.crafting.IRecipe;
/*  7:   */ import net.minecraft.item.crafting.ShapelessRecipes;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class RecipeFourByFourShapeless
/* 11:   */   extends ShapelessRecipes
/* 12:   */   implements IRecipe
/* 13:   */ {
/* 14:   */   public RecipeFourByFourShapeless(ItemStack par1ItemStack, List par2List)
/* 15:   */   {
/* 16:13 */     super(par1ItemStack, par2List);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/* 20:   */   {
/* 21:21 */     return (par1InventoryCrafting.getSizeInventory() == 4) && (super.matches(par1InventoryCrafting, par2World));
/* 22:   */   }
/* 23:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeFourByFourShapeless
 * JD-Core Version:    0.7.0.1
 */