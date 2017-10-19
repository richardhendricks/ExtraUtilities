/*  1:   */ package com.rwtema.extrautils.nei;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemBlock;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ 
/* 10:   */ public class InfoData
/* 11:   */ {
/* 12:12 */   public static List<InfoData> data = new ArrayList();
/* 13:   */   public ItemStack item;
/* 14:   */   public String[] info;
/* 15:   */   public String name;
/* 16:   */   public String url;
/* 17:24 */   public boolean isBlock = false;
/* 18:25 */   public boolean precise = false;
/* 19:   */   
/* 20:   */   public InfoData(ItemStack item, String[] info, String name, String url, boolean precise)
/* 21:   */   {
/* 22:28 */     this.item = item;
/* 23:29 */     this.info = info;
/* 24:30 */     this.name = name;
/* 25:32 */     if ((url != null) && (!url.endsWith(".png"))) {
/* 26:33 */       throw new RuntimeException(name + " is missing .png from url : " + url);
/* 27:   */     }
/* 28:35 */     this.url = url;
/* 29:36 */     this.precise = precise;
/* 30:37 */     this.isBlock = (item.getItem() instanceof ItemBlock);
/* 31:   */   }
/* 32:   */   
/* 33:   */   @Deprecated
/* 34:   */   public static InfoData add(Object item, String name, String url, String... info)
/* 35:   */   {
/* 36:42 */     InfoData newData = null;
/* 37:44 */     if ((item instanceof ItemStack)) {
/* 38:45 */       newData = new InfoData((ItemStack)item, info, name, url, true);
/* 39:46 */     } else if ((item instanceof Item)) {
/* 40:47 */       newData = new InfoData(new ItemStack((Item)item), info, name, url, false);
/* 41:48 */     } else if ((item instanceof Block)) {
/* 42:49 */       newData = new InfoData(new ItemStack((Block)item), info, name, url, false);
/* 43:   */     }
/* 44:52 */     data.add(newData);
/* 45:53 */     return newData;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean matches(ItemStack item)
/* 49:   */   {
/* 50:57 */     if (item == null) {
/* 51:58 */       return false;
/* 52:   */     }
/* 53:61 */     if (this.precise) {
/* 54:62 */       return ItemStack.areItemStacksEqual(item, this.item);
/* 55:   */     }
/* 56:64 */     return item == this.item;
/* 57:   */   }
/* 58:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.InfoData
 * JD-Core Version:    0.7.0.1
 */