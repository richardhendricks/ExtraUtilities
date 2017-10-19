/*  1:   */ package com.rwtema.extrautils.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ import net.minecraft.nbt.NBTTagList;
/*  5:   */ import net.minecraft.tileentity.TileEntity;
/*  6:   */ 
/*  7:   */ public class TileEntityEnchantedSpike
/*  8:   */   extends TileEntity
/*  9:   */ {
/* 10:   */   NBTTagList enchants;
/* 11:   */   
/* 12:   */   public boolean canUpdate()
/* 13:   */   {
/* 14:12 */     return false;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setEnchantmentTagList(NBTTagList enchants)
/* 18:   */   {
/* 19:16 */     this.enchants = enchants;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void readFromNBT(NBTTagCompound tags)
/* 23:   */   {
/* 24:21 */     super.readFromNBT(tags);
/* 25:22 */     this.enchants = tags.getTagList("ench", 10);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void writeToNBT(NBTTagCompound tags)
/* 29:   */   {
/* 30:27 */     super.writeToNBT(tags);
/* 31:28 */     if ((this.enchants != null) && (this.enchants.tagCount() > 0)) {
/* 32:29 */       tags.setTag("ench", this.enchants);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public NBTTagCompound getEnchantmentTagList()
/* 37:   */   {
/* 38:33 */     if ((this.enchants == null) || (this.enchants.tagCount() == 0)) {
/* 39:34 */       return null;
/* 40:   */     }
/* 41:36 */     NBTTagCompound tagCompound = new NBTTagCompound();
/* 42:37 */     tagCompound.setTag("ench", this.enchants.copy());
/* 43:38 */     return tagCompound;
/* 44:   */   }
/* 45:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityEnchantedSpike
 * JD-Core Version:    0.7.0.1
 */