/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.potion.Potion;
/*  9:   */ import net.minecraft.potion.PotionEffect;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class ItemBedrockiumIngot
/* 13:   */   extends Item
/* 14:   */ {
/* 15:   */   public ItemBedrockiumIngot()
/* 16:   */   {
/* 17:15 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 18:16 */     setTextureName("extrautils:bedrockiumIngot");
/* 19:17 */     setUnlocalizedName("extrautils:bedrockiumIngot");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean b)
/* 23:   */   {
/* 24:22 */     super.onUpdate(itemStack, world, entity, i, b);
/* 25:23 */     if ((entity instanceof EntityLivingBase)) {
/* 26:24 */       ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10, 3));
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getEntityLifespan(ItemStack itemStack, World world)
/* 31:   */   {
/* 32:31 */     return 2147473647;
/* 33:   */   }
/* 34:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBedrockiumIngot
 * JD-Core Version:    0.7.0.1
 */