/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.util.Random;
/*  6:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  7:   */ import net.minecraft.entity.Entity;
/*  8:   */ import net.minecraft.entity.EntityLivingBase;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.init.Items;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.tileentity.TileEntity;
/* 13:   */ import net.minecraft.util.DamageSource;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ import net.minecraft.world.WorldServer;
/* 16:   */ import net.minecraftforge.common.util.FakePlayer;
/* 17:   */ import net.minecraftforge.common.util.FakePlayerFactory;
/* 18:   */ 
/* 19:   */ public class BlockSpikeGold
/* 20:   */   extends BlockSpike
/* 21:   */ {
/* 22:   */   public BlockSpikeGold()
/* 23:   */   {
/* 24:20 */     super(new ItemStack(Items.golden_sword));
/* 25:21 */     setBlockName("extrautils:spike_base_gold");
/* 26:22 */     setBlockTextureName("extrautils:spike_base_gold");
/* 27:   */   }
/* 28:   */   
/* 29:   */   @SideOnly(Side.CLIENT)
/* 30:   */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 31:   */   {
/* 32:32 */     super.registerBlockIcons(par1IIconRegister);
/* 33:33 */     this.ironIcon = par1IIconRegister.registerIcon("extrautils:spike_side_gold");
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity target)
/* 37:   */   {
/* 38:38 */     if ((world.isClient) || (!(world instanceof WorldServer))) {
/* 39:39 */       return;
/* 40:   */     }
/* 41:42 */     FakePlayer fakeplayer = FakePlayerFactory.getMinecraft((WorldServer)world);
/* 42:44 */     if ((target instanceof EntityLivingBase))
/* 43:   */     {
/* 44:45 */       TileEntity tile = world.getTileEntity(x, y, z);
/* 45:46 */       float damage = getDamageMultipliers(4.0F, tile, (EntityLivingBase)target);
/* 46:   */       
/* 47:48 */       float h = ((EntityLivingBase)target).getHealth();
/* 48:   */       
/* 49:50 */       boolean flag = false;
/* 50:52 */       if ((h > damage) || ((target instanceof EntityPlayer))) {
/* 51:53 */         flag = target.attackEntityFrom(DamageSource.cactus, damage);
/* 52:55 */       } else if (h > 0.5F) {
/* 53:56 */         flag = target.attackEntityFrom(DamageSource.generic, h - 0.001F);
/* 54:59 */       } else if (world.rand.nextInt(3) == 0) {
/* 55:60 */         flag = doPlayerLastHit(world, target, tile);
/* 56:   */       } else {
/* 57:62 */         flag = target.attackEntityFrom(DamageSource.cactus, 40.0F);
/* 58:   */       }
/* 59:67 */       if (flag) {
/* 60:68 */         doPostAttack(world, target, tile, x, y, z);
/* 61:   */       }
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockSpikeGold
 * JD-Core Version:    0.7.0.1
 */