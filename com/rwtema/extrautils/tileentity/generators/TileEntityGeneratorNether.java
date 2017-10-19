/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.util.Random;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  9:   */ import net.minecraft.init.Blocks;
/* 10:   */ import net.minecraft.init.Items;
/* 11:   */ import net.minecraft.item.Item;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import net.minecraft.potion.Potion;
/* 14:   */ import net.minecraft.potion.PotionEffect;
/* 15:   */ import net.minecraft.util.AxisAlignedBB;
/* 16:   */ import net.minecraft.util.DamageSource;
/* 17:   */ import net.minecraft.world.World;
/* 18:   */ 
/* 19:   */ public class TileEntityGeneratorNether
/* 20:   */   extends TileEntityGeneratorFurnace
/* 21:   */ {
/* 22:   */   public double genLevel()
/* 23:   */   {
/* 24:21 */     return 40000.0D;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int transferLimit()
/* 28:   */   {
/* 29:26 */     return 100000;
/* 30:   */   }
/* 31:   */   
/* 32:   */   @SideOnly(Side.CLIENT)
/* 33:   */   public void doRandomDisplayTickR(Random random)
/* 34:   */   {
/* 35:32 */     super.doRandomDisplayTickR(random);
/* 36:34 */     if (shouldSoundPlay())
/* 37:   */     {
/* 38:35 */       int col = Potion.wither.getLiquidColor();
/* 39:36 */       double d0 = (col >> 16 & 0xFF) / 255.0D;
/* 40:37 */       double d1 = (col >> 8 & 0xFF) / 255.0D;
/* 41:38 */       double d2 = (col >> 0 & 0xFF) / 255.0D;
/* 42:   */       
/* 43:40 */       this.worldObj.spawnParticle("mobSpell", x() + random.nextFloat(), y() + 0.9D, z() + random.nextFloat(), d0, d1, d2);
/* 44:42 */       for (int i = 0; i < 50; i++)
/* 45:   */       {
/* 46:43 */         double dx = x() + 0.5D - 10.0D + random.nextInt(22);
/* 47:44 */         double dy = y() + 0.5D - 10.0D + random.nextInt(22);
/* 48:45 */         double dz = z() + 0.5D - 10.0D + random.nextInt(22);
/* 49:47 */         if (getDistanceFrom(dx, dy, dz) < 100.0D) {
/* 50:49 */           this.worldObj.spawnParticle("mobSpell", dx, dy, dz, d0, d1, d2);
/* 51:   */         }
/* 52:   */       }
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:56 */   private final int range = 10;
/* 57:   */   
/* 58:   */   public void doSpecial()
/* 59:   */   {
/* 60:60 */     if (this.coolDown > 0.0D) {
/* 61:62 */       for (Object ent : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x(), y(), z(), x() + 1, y() + 1, z() + 1).expand(10.0D, 10.0D, 10.0D)))
/* 62:   */       {
/* 63:64 */         double d = 10.0D - ((EntityLivingBase)ent).getDistance(x() + 0.5D, y() + 0.5D, z() + 0.5D);
/* 64:66 */         if (d > 0.0D) {
/* 65:68 */           if ((!(ent instanceof EntityPlayer)) || (!((EntityPlayer)ent).capabilities.isCreativeMode))
/* 66:   */           {
/* 67:69 */             ((EntityLivingBase)ent).addPotionEffect(new PotionEffect(Potion.wither.getId(), 200, 2));
/* 68:70 */             if (!((EntityLivingBase)ent).isEntityUndead()) {
/* 69:71 */               ((EntityLivingBase)ent).attackEntityFrom(DamageSource.setExplosionSource(null), (float)d);
/* 70:   */             }
/* 71:   */           }
/* 72:   */         }
/* 73:   */       }
/* 74:   */     }
/* 75:   */   }
/* 76:   */   
/* 77:   */   public double getFuelBurn(ItemStack item)
/* 78:   */   {
/* 79:80 */     if ((item != null) && ((item.getItem() == Items.nether_star) || (item.getItem() == Item.getItemFromBlock(Blocks.dragon_egg)))) {
/* 80:81 */       return 2400.0D;
/* 81:   */     }
/* 82:84 */     return 0.0D;
/* 83:   */   }
/* 84:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorNether
 * JD-Core Version:    0.7.0.1
 */