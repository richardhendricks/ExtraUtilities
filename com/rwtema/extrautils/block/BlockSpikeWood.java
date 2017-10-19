/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  7:   */ import net.minecraft.entity.Entity;
/*  8:   */ import net.minecraft.entity.EntityLivingBase;
/*  9:   */ import net.minecraft.init.Items;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.util.DamageSource;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ import net.minecraft.world.WorldServer;
/* 14:   */ 
/* 15:   */ public class BlockSpikeWood
/* 16:   */   extends BlockSpike
/* 17:   */ {
/* 18:   */   public BlockSpikeWood()
/* 19:   */   {
/* 20:17 */     super(Material.wood, new ItemStack(Items.wooden_sword));
/* 21:18 */     setBlockName("extrautils:spike_base_wood");
/* 22:19 */     setBlockTextureName("extrautils:spike_base_wood");
/* 23:20 */     setHardness(2.0F);
/* 24:   */   }
/* 25:   */   
/* 26:   */   @SideOnly(Side.CLIENT)
/* 27:   */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 28:   */   {
/* 29:31 */     super.registerBlockIcons(par1IIconRegister);
/* 30:32 */     this.ironIcon = par1IIconRegister.registerIcon("extrautils:spike_side_wood");
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity target)
/* 34:   */   {
/* 35:37 */     if ((world.isClient) || (!(world instanceof WorldServer))) {
/* 36:38 */       return;
/* 37:   */     }
/* 38:40 */     if ((target instanceof EntityLivingBase))
/* 39:   */     {
/* 40:41 */       float h = ((EntityLivingBase)target).getHealth();
/* 41:   */       
/* 42:43 */       float damage = getDamageMultipliers(1.0F, world.getTileEntity(x, y, z), (EntityLivingBase)target);
/* 43:45 */       if ((h > damage) && 
/* 44:46 */         (target.attackEntityFrom(DamageSource.cactus, damage))) {
/* 45:47 */         doPostAttack(world, target, world.getTileEntity(x, y, z), x, y, z);
/* 46:   */       }
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockSpikeWood
 * JD-Core Version:    0.7.0.1
 */