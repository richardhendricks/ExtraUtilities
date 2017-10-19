/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.inventory.Container;
/*  5:   */ import net.minecraft.inventory.InventoryCrafting;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.nbt.NBTTagCompound;
/*  9:   */ 
/* 10:   */ public class InvCrafting
/* 11:   */   extends InventoryCrafting
/* 12:   */ {
/* 13:   */   public InvCrafting(int par2, int par3)
/* 14:   */   {
/* 15:11 */     super(fakeContainer.instance, par2, par3);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int hashCode()
/* 19:   */   {
/* 20:16 */     int hash = 0;
/* 21:17 */     int n = 1;
/* 22:19 */     for (int i = 0; i < getSizeInventory(); i++)
/* 23:   */     {
/* 24:20 */       n *= 31;
/* 25:21 */       hash += n * hashItemStack(getStackInSlot(i));
/* 26:   */     }
/* 27:24 */     return hash;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int hashItemStack(ItemStack item)
/* 31:   */   {
/* 32:28 */     if (item == null) {
/* 33:29 */       return 0;
/* 34:   */     }
/* 35:31 */     int k = Item.getIdFromItem(item.getItem());
/* 36:32 */     k = k * 31 + item.getItemDamage();
/* 37:33 */     k *= 31;
/* 38:35 */     if (item.hasTagCompound()) {
/* 39:36 */       k += item.getTagCompound().hashCode();
/* 40:   */     }
/* 41:39 */     return k;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static class fakeContainer
/* 45:   */     extends Container
/* 46:   */   {
/* 47:43 */     public static fakeContainer instance = new fakeContainer();
/* 48:   */     
/* 49:   */     public boolean canInteractWith(EntityPlayer var1)
/* 50:   */     {
/* 51:47 */       return false;
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.InvCrafting
 * JD-Core Version:    0.7.0.1
 */