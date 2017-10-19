/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.helper.XURandom;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Random;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.block.material.MapColor;
/*  8:   */ import net.minecraft.block.material.Material;
/*  9:   */ import net.minecraft.entity.Entity;
/* 10:   */ import net.minecraft.entity.passive.EntityAnimal;
/* 11:   */ import net.minecraft.util.AxisAlignedBB;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class BlockPureLove
/* 15:   */   extends Block
/* 16:   */ {
/* 17:   */   public BlockPureLove()
/* 18:   */   {
/* 19:17 */     super(Material.iron);
/* 20:18 */     setBlockName("extrautils:pureLove");
/* 21:19 */     setBlockTextureName("extrautils:heart");
/* 22:20 */     setHardness(1.0F);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 26:   */   {
/* 27:24 */     float f = 0.125F;
/* 28:25 */     return AxisAlignedBB.getBoundingBox(p_149668_2_, p_149668_3_, p_149668_4_, p_149668_2_ + 1, p_149668_3_ + 1 - f, p_149668_4_ + 1);
/* 29:   */   }
/* 30:   */   
/* 31:28 */   Random rand = XURandom.getInstance();
/* 32:   */   
/* 33:   */   public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
/* 34:   */   {
/* 35:31 */     if (this.rand.nextInt(5) > 0) {
/* 36:32 */       return;
/* 37:   */     }
/* 38:34 */     if ((entity instanceof EntityAnimal))
/* 39:   */     {
/* 40:35 */       EntityAnimal animal = (EntityAnimal)entity;
/* 41:36 */       if (animal.getGrowingAge() < 0)
/* 42:   */       {
/* 43:37 */         animal.addGrowth(this.rand.nextInt(40));
/* 44:   */       }
/* 45:38 */       else if (animal.getGrowingAge() > 0)
/* 46:   */       {
/* 47:39 */         int j = animal.getGrowingAge();
/* 48:40 */         j -= this.rand.nextInt(40);
/* 49:41 */         if (j < 0) {
/* 50:42 */           j = 0;
/* 51:   */         }
/* 52:43 */         animal.setGrowingAge(j);
/* 53:   */       }
/* 54:44 */       else if (!animal.isInLove())
/* 55:   */       {
/* 56:45 */         if (world.getEntitiesWithinAABB(entity.getClass(), entity.boundingBox.expand(8.0D, 8.0D, 8.0D)).size() > 32) {
/* 57:46 */           return;
/* 58:   */         }
/* 59:48 */         animal.func_146082_f(null);
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */   
/* 64:   */   public MapColor getMapColor(int p_149728_1_)
/* 65:   */   {
/* 66:57 */     return MapColor.field_151671_v;
/* 67:   */   }
/* 68:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockPureLove
 * JD-Core Version:    0.7.0.1
 */