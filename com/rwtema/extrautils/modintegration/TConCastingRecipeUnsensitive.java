/*  1:   */ package com.rwtema.extrautils.modintegration;
/*  2:   */ 
/*  3:   */ import net.minecraft.item.ItemStack;
/*  4:   */ import net.minecraftforge.fluids.FluidStack;
/*  5:   */ import tconstruct.library.client.FluidRenderProperties;
/*  6:   */ import tconstruct.library.crafting.CastingRecipe;
/*  7:   */ 
/*  8:   */ public class TConCastingRecipeUnsensitive
/*  9:   */   extends CastingRecipe
/* 10:   */ {
/* 11:   */   public TConCastingRecipeUnsensitive(ItemStack replacement, FluidStack metal, ItemStack cast, boolean consume, int delay, FluidRenderProperties props)
/* 12:   */   {
/* 13:10 */     super(replacement, metal, cast, consume, delay, props);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public TConCastingRecipeUnsensitive(CastingRecipe recipe)
/* 17:   */   {
/* 18:14 */     super(recipe.output.copy(), recipe.castingMetal.copy(), recipe.cast.copy(), recipe.consumeCast, recipe.coolTime, recipe.fluidRenderProperties);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean matches(FluidStack metal, ItemStack inputCast)
/* 22:   */   {
/* 23:18 */     return (this.castingMetal != null) && (this.castingMetal.isFluidEqual(metal)) && (inputCast != null) && (this.cast != null) && (inputCast.getItem() == this.cast.getItem()) && ((this.cast.getItemDamage() == 32767) || (this.cast.getItemDamage() == inputCast.getItemDamage()));
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.TConCastingRecipeUnsensitive
 * JD-Core Version:    0.7.0.1
 */