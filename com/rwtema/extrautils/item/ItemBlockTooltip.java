/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ICreativeTabSorting;
/*  4:   */ import com.rwtema.extrautils.block.IBlockTooltip;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  7:   */ import java.util.List;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.item.ItemBlock;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ItemBlockTooltip
/* 14:   */   extends ItemBlock
/* 15:   */   implements ICreativeTabSorting
/* 16:   */ {
/* 17:   */   public ItemBlockTooltip(Block par1)
/* 18:   */   {
/* 19:16 */     super(par1);
/* 20:   */   }
/* 21:   */   
/* 22:   */   @SideOnly(Side.CLIENT)
/* 23:   */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 24:   */   {
/* 25:25 */     if ((this.field_150939_a instanceof IBlockTooltip)) {
/* 26:26 */       ((IBlockTooltip)this.field_150939_a).addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getSortingName(ItemStack par1ItemStack)
/* 31:   */   {
/* 32:33 */     if ((this.field_150939_a instanceof ICreativeTabSorting)) {
/* 33:34 */       return ((ICreativeTabSorting)this.field_150939_a).getSortingName(par1ItemStack);
/* 34:   */     }
/* 35:36 */     return par1ItemStack.getDisplayName();
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockTooltip
 * JD-Core Version:    0.7.0.1
 */