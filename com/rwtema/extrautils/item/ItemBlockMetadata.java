/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.util.IIcon;
/*  8:   */ 
/*  9:   */ public class ItemBlockMetadata
/* 10:   */   extends ItemBlockTooltip
/* 11:   */ {
/* 12:10 */   protected boolean hasBlockMetadata = true;
/* 13:   */   private int blockId;
/* 14:   */   IBlockLocalization blockLocalizationMetadata;
/* 15:   */   
/* 16:   */   public ItemBlockMetadata(Block par1)
/* 17:   */   {
/* 18:15 */     super(par1);
/* 19:   */     
/* 20:17 */     setMaxDamage(0);
/* 21:18 */     setHasSubtypes(true);
/* 22:19 */     if ((par1 instanceof IBlockLocalization)) {
/* 23:20 */       this.blockLocalizationMetadata = ((IBlockLocalization)par1);
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   @SideOnly(Side.CLIENT)
/* 28:   */   public IIcon getIconFromDamage(int par1)
/* 29:   */   {
/* 30:30 */     if (!this.hasBlockMetadata) {
/* 31:31 */       return super.getIconFromDamage(par1);
/* 32:   */     }
/* 33:34 */     return this.field_150939_a.getIcon(2, par1);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int getMetadata(int par1)
/* 37:   */   {
/* 38:42 */     if (!this.hasBlockMetadata) {
/* 39:43 */       return super.getMetadata(par1);
/* 40:   */     }
/* 41:46 */     return par1;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 45:   */   {
/* 46:51 */     if (this.blockLocalizationMetadata != null) {
/* 47:52 */       return this.blockLocalizationMetadata.getUnlocalizedName(par1ItemStack);
/* 48:   */     }
/* 49:54 */     if (!this.hasBlockMetadata) {
/* 50:55 */       return super.getUnlocalizedName(par1ItemStack);
/* 51:   */     }
/* 52:58 */     return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
/* 53:   */   }
/* 54:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockMetadata
 * JD-Core Version:    0.7.0.1
 */