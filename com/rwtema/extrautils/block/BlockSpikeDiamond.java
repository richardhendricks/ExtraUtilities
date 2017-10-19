/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  6:   */ import net.minecraft.entity.Entity;
/*  7:   */ import net.minecraft.entity.EntityLivingBase;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ import net.minecraft.init.Items;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.tileentity.TileEntity;
/* 12:   */ import net.minecraft.util.DamageSource;
/* 13:   */ import net.minecraft.world.World;
/* 14:   */ import net.minecraft.world.WorldServer;
/* 15:   */ 
/* 16:   */ public class BlockSpikeDiamond
/* 17:   */   extends BlockSpike
/* 18:   */ {
/* 19:17 */   public ItemStack lootah = newLootah();
/* 20:   */   
/* 21:   */   public static ItemStack newLootah()
/* 22:   */   {
/* 23:20 */     ItemStack lootah = new ItemStack(Items.diamond_sword);
/* 24:21 */     lootah.setItemDamage(lootah.getMaxDamage());
/* 25:22 */     return lootah;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public BlockSpikeDiamond()
/* 29:   */   {
/* 30:26 */     super(new ItemStack(Items.diamond_sword));
/* 31:27 */     setBlockName("extrautils:spike_base_diamond");
/* 32:28 */     setBlockTextureName("extrautils:spike_base_diamond");
/* 33:   */   }
/* 34:   */   
/* 35:   */   @SideOnly(Side.CLIENT)
/* 36:   */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 37:   */   {
/* 38:38 */     super.registerBlockIcons(par1IIconRegister);
/* 39:39 */     this.ironIcon = par1IIconRegister.registerIcon("extrautils:spike_side_diamond");
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity target)
/* 43:   */   {
/* 44:44 */     if ((world.isClient) || (!(world instanceof WorldServer))) {
/* 45:45 */       return;
/* 46:   */     }
/* 47:48 */     boolean flag = false;
/* 48:50 */     if ((target instanceof EntityLivingBase))
/* 49:   */     {
/* 50:51 */       TileEntity tile = world.getTileEntity(x, y, z);
/* 51:52 */       float damage = getDamageMultipliers(10.0F, tile, (EntityLivingBase)target);
/* 52:   */       
/* 53:54 */       float h = ((EntityLivingBase)target).getHealth();
/* 54:56 */       if ((h > damage) || ((target instanceof EntityPlayer))) {
/* 55:57 */         flag = target.attackEntityFrom(DamageSource.cactus, damage - 0.01F);
/* 56:59 */       } else if (h > 0.5F) {
/* 57:60 */         flag = target.attackEntityFrom(DamageSource.generic, h - 0.001F);
/* 58:   */       } else {
/* 59:62 */         flag = doPlayerLastHit((WorldServer)world, target, tile);
/* 60:   */       }
/* 61:66 */       if (flag) {
/* 62:67 */         doPostAttack(world, target, tile, x, y, z);
/* 63:   */       }
/* 64:   */     }
/* 65:   */   }
/* 66:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockSpikeDiamond
 * JD-Core Version:    0.7.0.1
 */