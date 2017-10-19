/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.item.ItemBlock;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.potion.Potion;
/* 10:   */ import net.minecraft.potion.PotionEffect;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ 
/* 13:   */ public class ItemBlockBedrockium
/* 14:   */   extends ItemBlock
/* 15:   */ {
/* 16:   */   public ItemBlockBedrockium(Block p_i45328_1_)
/* 17:   */   {
/* 18:15 */     super(p_i45328_1_);
/* 19:16 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean b)
/* 23:   */   {
/* 24:22 */     super.onUpdate(itemStack, world, entity, i, b);
/* 25:23 */     if ((entity instanceof EntityLivingBase)) {
/* 26:24 */       ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10, 6));
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getEntityLifespan(ItemStack itemStack, World world)
/* 31:   */   {
/* 32:31 */     return 2147473647;
/* 33:   */   }
/* 34:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockBedrockium
 * JD-Core Version:    0.7.0.1
 */