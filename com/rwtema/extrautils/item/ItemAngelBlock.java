/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.item.ItemBlock;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.util.AxisAlignedBB;
/*  8:   */ import net.minecraft.util.Vec3;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class ItemAngelBlock
/* 12:   */   extends ItemBlock
/* 13:   */ {
/* 14:   */   public ItemAngelBlock(Block par1)
/* 15:   */   {
/* 16:12 */     super(par1);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
/* 20:   */   {
/* 21:21 */     if (world.isClient) {
/* 22:22 */       return item;
/* 23:   */     }
/* 24:25 */     int x = (int)Math.floor(player.posX);
/* 25:26 */     int y = (int)Math.floor(player.posY) + 1;
/* 26:27 */     int z = (int)Math.floor(player.posZ);
/* 27:28 */     Vec3 look = player.getLookVec();
/* 28:   */     
/* 29:30 */     double max = Math.max(Math.max(Math.abs(look.xCoord), Math.abs(look.yCoord)), Math.abs(look.zCoord));
/* 30:32 */     if (look.yCoord == max) {
/* 31:33 */       y = (int)(Math.ceil(player.boundingBox.maxY) + 1.0D);
/* 32:34 */     } else if (-look.yCoord == max) {
/* 33:35 */       y = (int)(Math.floor(player.boundingBox.minY) - 1.0D);
/* 34:36 */     } else if (look.xCoord == max) {
/* 35:37 */       x = (int)(Math.floor(player.boundingBox.maxX) + 1.0D);
/* 36:38 */     } else if (-look.xCoord == max) {
/* 37:39 */       x = (int)(Math.floor(player.boundingBox.minX) - 1.0D);
/* 38:40 */     } else if (look.zCoord == max) {
/* 39:41 */       z = (int)(Math.floor(player.boundingBox.maxZ) + 1.0D);
/* 40:42 */     } else if (-look.zCoord == max) {
/* 41:43 */       z = (int)(Math.floor(player.boundingBox.minZ) - 1.0D);
/* 42:   */     }
/* 43:46 */     if (world.canPlaceEntityOnSide(this.field_150939_a, x, y, z, false, 0, player, item)) {
/* 44:47 */       item.tryPlaceItemIntoWorld(player, world, x, y, z, 0, 0.0F, 0.0F, 0.0F);
/* 45:   */     }
/* 46:50 */     return item;
/* 47:   */   }
/* 48:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemAngelBlock
 * JD-Core Version:    0.7.0.1
 */