/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.BlockCompressed;
/*  6:   */ import net.minecraft.block.material.Material;
/*  7:   */ import net.minecraft.entity.Entity;
/*  8:   */ import net.minecraft.entity.EntityLivingBase;
/*  9:   */ import net.minecraft.item.ItemBlock;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.potion.Potion;
/* 12:   */ import net.minecraft.potion.PotionEffect;
/* 13:   */ import net.minecraft.world.Explosion;
/* 14:   */ import net.minecraft.world.IBlockAccess;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class BlockBedrockium
/* 18:   */   extends BlockCompressed
/* 19:   */ {
/* 20:   */   public BlockBedrockium()
/* 21:   */   {
/* 22:19 */     super(Material.rock.getMaterialMapColor());
/* 23:20 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 24:21 */     setBlockName("extrautils:block_bedrockium");
/* 25:22 */     setBlockTextureName("extrautils:bedrockiumBlock");
/* 26:23 */     setHardness(1000.0F);
/* 27:24 */     setResistance(6000000.0F);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean canDropFromExplosion(Explosion par1Explosion)
/* 31:   */   {
/* 32:29 */     return false;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {}
/* 36:   */   
/* 37:   */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
/* 38:   */   {
/* 39:39 */     return false;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static class ItemBedrockium
/* 43:   */     extends ItemBlock
/* 44:   */   {
/* 45:   */     public ItemBedrockium(Block p_i45328_1_)
/* 46:   */     {
/* 47:44 */       super();
/* 48:   */     }
/* 49:   */     
/* 50:   */     public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean b)
/* 51:   */     {
/* 52:49 */       super.onUpdate(itemStack, world, entity, i, b);
/* 53:50 */       if ((entity instanceof EntityLivingBase)) {
/* 54:51 */         ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10, 3));
/* 55:   */       }
/* 56:   */     }
/* 57:   */     
/* 58:   */     public int getEntityLifespan(ItemStack itemStack, World world)
/* 59:   */     {
/* 60:58 */       return 2147473647;
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockBedrockium
 * JD-Core Version:    0.7.0.1
 */