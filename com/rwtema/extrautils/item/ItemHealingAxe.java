/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.util.Random;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   9:    */ import net.minecraft.entity.DataWatcher;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.entity.EntityLiving;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.monster.EntityZombie;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.item.Item.ToolMaterial;
/*  16:    */ import net.minecraft.item.ItemAxe;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.potion.Potion;
/*  19:    */ import net.minecraft.util.DamageSource;
/*  20:    */ import net.minecraft.util.FoodStats;
/*  21:    */ import net.minecraft.util.IIcon;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ 
/*  24:    */ public class ItemHealingAxe
/*  25:    */   extends ItemAxe
/*  26:    */   implements IItemMultiTransparency
/*  27:    */ {
/*  28:    */   private IIcon[] icons;
/*  29:    */   
/*  30:    */   public ItemHealingAxe()
/*  31:    */   {
/*  32: 26 */     super(Item.ToolMaterial.EMERALD);
/*  33: 27 */     this.maxStackSize = 1;
/*  34: 28 */     setMaxDamage(1561);
/*  35: 29 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  36: 30 */     setUnlocalizedName("extrautils:defoliageAxe");
/*  37:    */   }
/*  38:    */   
/*  39:    */   @SideOnly(Side.CLIENT)
/*  40:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/*  41:    */   {
/*  42: 36 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
/*  46:    */   {
/*  47: 41 */     return true;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
/*  51:    */   {
/*  52: 57 */     float k = 2.0F;
/*  53:    */     
/*  54: 59 */     player.addExhaustion(10.0F);
/*  55: 66 */     if ((entity instanceof EntityLiving))
/*  56:    */     {
/*  57: 67 */       if (entity.worldObj.isClient) {
/*  58: 68 */         for (int i = 0; i < 5; i++)
/*  59:    */         {
/*  60: 69 */           int j = Potion.heal.getLiquidColor();
/*  61: 70 */           float r = (j >> 16 & 0xFF) / 255.0F;
/*  62: 71 */           float g = (j >> 8 & 0xFF) / 255.0F;
/*  63: 72 */           float b = (j & 0xFF) / 255.0F;
/*  64: 73 */           entity.worldObj.spawnParticle("mobSpell", entity.posX + (entity.worldObj.rand.nextDouble() - 0.5D) * entity.width, entity.posY + entity.worldObj.rand.nextDouble() * entity.height - entity.yOffset, entity.posZ + (entity.worldObj.rand.nextDouble() - 0.5D) * entity.width, r, g, b);
/*  65:    */         }
/*  66:    */       }
/*  67: 78 */       if (k > 0.0F)
/*  68:    */       {
/*  69: 79 */         EntityLiving entLivin = (EntityLiving)entity;
/*  70: 80 */         k *= 2.0F;
/*  71: 82 */         if ((((EntityLiving)entity).isEntityUndead()) && (entity.isEntityAlive()))
/*  72:    */         {
/*  73: 83 */           if (((entity instanceof EntityZombie)) && 
/*  74: 84 */             (((EntityZombie)entity).isVillager()))
/*  75:    */           {
/*  76: 85 */             if (!entity.worldObj.isClient)
/*  77:    */             {
/*  78: 86 */               entity.getDataWatcher().updateObject(14, Byte.valueOf((byte)1));
/*  79: 87 */               entity.worldObj.setEntityState(entity, (byte)16);
/*  80:    */             }
/*  81: 90 */             return true;
/*  82:    */           }
/*  83: 93 */           entLivin.attackEntityFrom(DamageSource.causePlayerDamage(player), k * 4.0F);
/*  84: 94 */           return true;
/*  85:    */         }
/*  86: 95 */         if ((entity.isEntityAlive()) && (((EntityLiving)entity).getHealth() > 0.0F) && 
/*  87: 96 */           (entLivin.getHealth() < entLivin.getHealth()))
/*  88:    */         {
/*  89: 97 */           if (!entLivin.worldObj.isClient) {
/*  90: 98 */             ((EntityLiving)entity).heal(k);
/*  91:    */           }
/*  92:101 */           return true;
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:107 */     return true;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getDamageVsEntity(Entity par1Entity)
/* 100:    */   {
/* 101:114 */     return 0;
/* 102:    */   }
/* 103:    */   
/* 104:    */   @SideOnly(Side.CLIENT)
/* 105:    */   public boolean isFull3D()
/* 106:    */   {
/* 107:123 */     return true;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
/* 111:    */   {
/* 112:132 */     if ((par5) && 
/* 113:133 */       ((par3Entity instanceof EntityPlayer)) && 
/* 114:134 */       (par2World.getTotalWorldTime() % 40L == 0L)) {
/* 115:135 */       ((EntityPlayer)par3Entity).getFoodStats().addStats(1, 0.2F);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   @SideOnly(Side.CLIENT)
/* 120:    */   public void registerIcons(IIconRegister par1IIconRegister)
/* 121:    */   {
/* 122:143 */     this.icons = new IIcon[2];
/* 123:144 */     this.itemIcon = (this.icons[0] =  = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5)));
/* 124:145 */     this.icons[1] = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5) + "1");
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int numIcons(ItemStack item)
/* 128:    */   {
/* 129:150 */     return 2;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public IIcon getIconForTransparentRender(ItemStack item, int pass)
/* 133:    */   {
/* 134:155 */     return this.icons[pass];
/* 135:    */   }
/* 136:    */   
/* 137:    */   public float getIconTransparency(ItemStack item, int pass)
/* 138:    */   {
/* 139:160 */     if (pass == 1) {
/* 140:161 */       return 0.5F;
/* 141:    */     }
/* 142:163 */     return 1.0F;
/* 143:    */   }
/* 144:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemHealingAxe
 * JD-Core Version:    0.7.0.1
 */