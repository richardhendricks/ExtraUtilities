/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Multimap;
/*  4:   */ import com.rwtema.extrautils.block.BlockSpike;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.enchantment.Enchantment;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.item.ItemBlock;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ 
/* 12:   */ public class ItemBlockSpike
/* 13:   */   extends ItemBlock
/* 14:   */ {
/* 15:   */   public final BlockSpike spike;
/* 16:16 */   public static final HashSet<Item> itemHashSet = new HashSet();
/* 17:   */   
/* 18:   */   public ItemBlockSpike(Block p_i45328_1_)
/* 19:   */   {
/* 20:19 */     super(p_i45328_1_);
/* 21:20 */     this.spike = ((BlockSpike)p_i45328_1_);
/* 22:21 */     this.spike.itemSpike = this;
/* 23:   */     
/* 24:23 */     itemHashSet.add(this);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getItemEnchantability()
/* 28:   */   {
/* 29:28 */     return this.spike.swordStack.getItem().getItemEnchantability(this.spike.swordStack);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int cofh_canEnchantApply(ItemStack stack, Enchantment ench)
/* 33:   */   {
/* 34:33 */     if (ench.canApply(this.spike.swordStack.copy())) {
/* 35:34 */       return 1;
/* 36:   */     }
/* 37:35 */     return -1;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean isItemTool(ItemStack p_77616_1_)
/* 41:   */   {
/* 42:40 */     return p_77616_1_.stackSize == 1;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Multimap getAttributeModifiers(ItemStack stack)
/* 46:   */   {
/* 47:45 */     return this.spike.swordStack.copy().getAttributeModifiers();
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean isBookEnchantable(ItemStack stack, ItemStack book)
/* 51:   */   {
/* 52:50 */     return stack.stackSize == 1;
/* 53:   */   }
/* 54:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockSpike
 * JD-Core Version:    0.7.0.1
 */