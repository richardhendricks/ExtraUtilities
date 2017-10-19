/*  1:   */ package com.rwtema.extrautils;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.Collections;
/*  7:   */ import java.util.Comparator;
/*  8:   */ import java.util.List;
/*  9:   */ import net.minecraft.block.Block;
/* 10:   */ import net.minecraft.creativetab.CreativeTabs;
/* 11:   */ import net.minecraft.item.Item;
/* 12:   */ import net.minecraft.item.ItemBlock;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class CreativeTabExtraUtils
/* 16:   */   extends CreativeTabs
/* 17:   */ {
/* 18:17 */   private itemSorter alphabeticalSorter = new itemSorter(null);
/* 19:   */   
/* 20:   */   public CreativeTabExtraUtils(String label)
/* 21:   */   {
/* 22:20 */     super(label);
/* 23:   */   }
/* 24:   */   
/* 25:   */   @SideOnly(Side.CLIENT)
/* 26:   */   public ItemStack getIconItemStack()
/* 27:   */   {
/* 28:29 */     if (ExtraUtils.angelBlock != null) {
/* 29:30 */       return new ItemStack(ExtraUtils.angelBlock, 1, 0);
/* 30:   */     }
/* 31:33 */     if (ExtraUtils.creativeTabIcon != null) {
/* 32:34 */       return new ItemStack(ExtraUtils.creativeTabIcon, 1, 0);
/* 33:   */     }
/* 34:37 */     return null;
/* 35:   */   }
/* 36:   */   
/* 37:   */   @SideOnly(Side.CLIENT)
/* 38:   */   public void displayAllReleventItems(List par1List)
/* 39:   */   {
/* 40:46 */     List newList = new ArrayList();
/* 41:47 */     super.displayAllReleventItems(newList);
/* 42:48 */     Collections.sort(newList, this.alphabeticalSorter);
/* 43:49 */     par1List.addAll(newList);
/* 44:51 */     for (ItemStack item : (ArrayList)newList) {
/* 45:52 */       if (item.getDisplayName().endsWith(".name")) {
/* 46:53 */         LogHelper.debug("Missing localization data for " + item.getDisplayName(), new Object[0]);
/* 47:   */       }
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   @SideOnly(Side.CLIENT)
/* 52:   */   public Item getTabIconItem()
/* 53:   */   {
/* 54:61 */     return null;
/* 55:   */   }
/* 56:   */   
/* 57:   */   private static class itemSorter
/* 58:   */     implements Comparator
/* 59:   */   {
/* 60:   */     public int compare(Object arg0, Object arg1)
/* 61:   */     {
/* 62:67 */       ItemStack i0 = (ItemStack)arg0;
/* 63:68 */       ItemStack i1 = (ItemStack)arg1;
/* 64:70 */       if (((i0.getItem() instanceof ItemBlock)) && (!(i1.getItem() instanceof ItemBlock))) {
/* 65:71 */         return -1;
/* 66:   */       }
/* 67:74 */       if (((i1.getItem() instanceof ItemBlock)) && (!(i0.getItem() instanceof ItemBlock))) {
/* 68:75 */         return 1;
/* 69:   */       }
/* 70:78 */       String a = getString(i0);
/* 71:79 */       String b = getString(i1);
/* 72:80 */       return (int)Math.signum(a.compareToIgnoreCase(b));
/* 73:   */     }
/* 74:   */     
/* 75:   */     public String getString(ItemStack item)
/* 76:   */     {
/* 77:84 */       if ((item.getItem() instanceof ICreativeTabSorting)) {
/* 78:85 */         return ((ICreativeTabSorting)item.getItem()).getSortingName(item);
/* 79:   */       }
/* 80:86 */       if ((item.getItem() instanceof ItemBlock))
/* 81:   */       {
/* 82:87 */         Block block_id = ((ItemBlock)item.getItem()).field_150939_a;
/* 83:89 */         if ((block_id instanceof ICreativeTabSorting)) {
/* 84:90 */           ((ICreativeTabSorting)block_id).getSortingName(item);
/* 85:   */         }
/* 86:   */       }
/* 87:94 */       return item.getDisplayName();
/* 88:   */     }
/* 89:   */   }
/* 90:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.CreativeTabExtraUtils
 * JD-Core Version:    0.7.0.1
 */