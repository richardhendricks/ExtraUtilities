/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.block.BlockColor;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.util.StatCollector;
/* 10:   */ 
/* 11:   */ public class ItemBlockColor
/* 12:   */   extends ItemBlockMetadata
/* 13:   */ {
/* 14:   */   public ItemBlockColor(Block par1)
/* 15:   */   {
/* 16:13 */     super(par1);
/* 17:   */   }
/* 18:   */   
/* 19:   */   @SideOnly(Side.CLIENT)
/* 20:   */   public int getColorFromItemStack(ItemStack item, int p_82790_2_)
/* 21:   */   {
/* 22:19 */     float[] col = BlockColor.initColor[(item.getItemDamage() & 0xF)];
/* 23:20 */     return (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getItemStackDisplayName(ItemStack p_77653_1_)
/* 27:   */   {
/* 28:26 */     Block bc = ((BlockColor)this.field_150939_a).baseBlock;
/* 29:   */     
/* 30:   */ 
/* 31:   */ 
/* 32:30 */     Item i = Item.getItemFromBlock(bc);
/* 33:   */     String name;
/* 34:31 */     if (i == null)
/* 35:   */     {
/* 36:32 */       String name = bc.getUnlocalizedName();
/* 37:33 */       if (name != null)
/* 38:   */       {
/* 39:34 */         name = StatCollector.translateToLocal(name);
/* 40:35 */         name = ("" + StatCollector.translateToLocal(new StringBuilder().append(name).append(".name").toString())).trim();
/* 41:   */       }
/* 42:   */       else
/* 43:   */       {
/* 44:37 */         name = "";
/* 45:   */       }
/* 46:   */     }
/* 47:   */     else
/* 48:   */     {
/* 49:40 */       name = new ItemStack(i, 1, 0).getDisplayName();
/* 50:   */     }
/* 51:43 */     return StatCollector.translateToLocal("tile.extrautils:colorBlock." + p_77653_1_.getItemDamage() + ".name").replaceAll("BLOCKNAME", name);
/* 52:   */   }
/* 53:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockColor
 * JD-Core Version:    0.7.0.1
 */