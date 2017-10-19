/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import net.minecraft.inventory.InventoryBasic;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ import net.minecraft.nbt.NBTTagCompound;
/*  6:   */ 
/*  7:   */ public class InventoryGeneric
/*  8:   */   extends InventoryBasic
/*  9:   */ {
/* 10:   */   public InventoryGeneric(String par1Str, boolean par2, int par3)
/* 11:   */   {
/* 12: 9 */     super(par1Str, par2, par3);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void writeToNBT(NBTTagCompound nbt)
/* 16:   */   {
/* 17:13 */     NBTTagCompound tag = new NBTTagCompound();
/* 18:15 */     for (int i = 0; i < getSizeInventory(); i++)
/* 19:   */     {
/* 20:16 */       ItemStack item = getStackInSlot(i);
/* 21:18 */       if (item != null)
/* 22:   */       {
/* 23:19 */         NBTTagCompound itemtag = new NBTTagCompound();
/* 24:20 */         item.writeToNBT(itemtag);
/* 25:21 */         tag.setTag("item_" + i, itemtag);
/* 26:   */       }
/* 27:   */     }
/* 28:25 */     nbt.setTag("items", tag);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void readFromNBT(NBTTagCompound nbt)
/* 32:   */   {
/* 33:29 */     if (!nbt.hasKey("items"))
/* 34:   */     {
/* 35:30 */       for (int i = 0; i < getSizeInventory(); i++) {
/* 36:31 */         setInventorySlotContents(i, null);
/* 37:   */       }
/* 38:   */     }
/* 39:   */     else
/* 40:   */     {
/* 41:34 */       NBTTagCompound tag = nbt.getCompoundTag("items");
/* 42:36 */       for (int i = 0; i < getSizeInventory(); i++) {
/* 43:37 */         if (tag.hasKey("item_" + i)) {
/* 44:38 */           setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item_" + i)));
/* 45:   */         } else {
/* 46:40 */           setInventorySlotContents(i, null);
/* 47:   */         }
/* 48:   */       }
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.InventoryGeneric
 * JD-Core Version:    0.7.0.1
 */