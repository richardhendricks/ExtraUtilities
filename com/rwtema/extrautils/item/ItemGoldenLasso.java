/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityCreature;
/*  10:    */ import net.minecraft.entity.EntityList;
/*  11:    */ import net.minecraft.entity.EntityLiving;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.monster.EntityMob;
/*  14:    */ import net.minecraft.entity.passive.EntityAmbientCreature;
/*  15:    */ import net.minecraft.entity.passive.EntityVillager;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  18:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  19:    */ import net.minecraft.item.Item;
/*  20:    */ import net.minecraft.item.ItemStack;
/*  21:    */ import net.minecraft.nbt.NBTTagCompound;
/*  22:    */ import net.minecraft.nbt.NBTTagDouble;
/*  23:    */ import net.minecraft.nbt.NBTTagList;
/*  24:    */ import net.minecraft.util.EnumChatFormatting;
/*  25:    */ import net.minecraft.util.StatCollector;
/*  26:    */ import net.minecraft.world.World;
/*  27:    */ import net.minecraft.world.WorldProvider;
/*  28:    */ 
/*  29:    */ public class ItemGoldenLasso
/*  30:    */   extends Item
/*  31:    */ {
/*  32:    */   public ItemGoldenLasso()
/*  33:    */   {
/*  34: 27 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  35: 28 */     this.maxStackSize = 1;
/*  36: 29 */     setHasSubtypes(true);
/*  37: 30 */     setUnlocalizedName("extrautils:golden_lasso");
/*  38: 31 */     setTextureName("extrautils:golden_lasso");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par2EntityLiving)
/*  42:    */   {
/*  43: 39 */     if (par1ItemStack.hasTagCompound()) {
/*  44: 40 */       if (par1ItemStack.getItemDamage() == 0) {
/*  45: 41 */         par1ItemStack.setTagCompound(null);
/*  46:    */       } else {
/*  47: 43 */         return false;
/*  48:    */       }
/*  49:    */     }
/*  50: 47 */     if ((!(par2EntityLiving instanceof EntityCreature)) && (!(par2EntityLiving instanceof EntityAmbientCreature))) {
/*  51: 48 */       return false;
/*  52:    */     }
/*  53: 51 */     if ((par2EntityLiving instanceof EntityMob)) {
/*  54: 52 */       return false;
/*  55:    */     }
/*  56: 55 */     if (((EntityLiving)par2EntityLiving).getAttackTarget() != null) {
/*  57: 56 */       return false;
/*  58:    */     }
/*  59: 61 */     NBTTagCompound entityTags = new NBTTagCompound();
/*  60: 62 */     entityTags.setBoolean("com.rwtema.extrautils.goldenlasso", true);
/*  61: 64 */     if (!par2EntityLiving.writeMountToNBT(entityTags)) {
/*  62: 65 */       return false;
/*  63:    */     }
/*  64: 68 */     if (((!entityTags.hasKey("com.rwtema.extrautils.goldenlasso") ? 1 : 0) | (!entityTags.getBoolean("com.rwtema.extrautils.goldenlasso") ? 1 : 0)) != 0) {
/*  65: 69 */       return false;
/*  66:    */     }
/*  67: 72 */     String name = "";
/*  68: 74 */     if (((EntityLiving)par2EntityLiving).hasCustomNameTag()) {
/*  69: 75 */       name = ((EntityLiving)par2EntityLiving).getCustomNameTag();
/*  70:    */     }
/*  71: 78 */     if (!par2EntityLiving.worldObj.isClient) {
/*  72: 79 */       par2EntityLiving.setDead();
/*  73:    */     }
/*  74: 82 */     par1ItemStack.setTagCompound(entityTags);
/*  75: 84 */     if (name.equals(""))
/*  76:    */     {
/*  77: 85 */       if ((par2EntityLiving instanceof EntityVillager)) {
/*  78: 86 */         par1ItemStack.setItemDamage(2);
/*  79:    */       } else {
/*  80: 88 */         par1ItemStack.setItemDamage(1);
/*  81:    */       }
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85: 91 */       par1ItemStack.setStackDisplayName(name);
/*  86: 92 */       par1ItemStack.setItemDamage(2);
/*  87:    */     }
/*  88: 95 */     return true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   @SideOnly(Side.CLIENT)
/*  92:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/*  93:    */   {
/*  94:101 */     return par1ItemStack.getItemDamage() != 0;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  98:    */   {
/*  99:111 */     if (((par1ItemStack.getItemDamage() == 0 ? 1 : 0) | (!par1ItemStack.hasTagCompound() ? 1 : 0)) != 0)
/* 100:    */     {
/* 101:112 */       par1ItemStack.setItemDamage(0);
/* 102:113 */       return false;
/* 103:    */     }
/* 104:116 */     if (!par1ItemStack.getTagCompound().hasKey("id"))
/* 105:    */     {
/* 106:117 */       par1ItemStack.setItemDamage(0);
/* 107:118 */       return false;
/* 108:    */     }
/* 109:121 */     if (par3World.isClient) {
/* 110:122 */       return true;
/* 111:    */     }
/* 112:124 */     Block i1 = par3World.getBlock(par4, par5, par6);
/* 113:125 */     par4 += net.minecraft.util.Facing.offsetsXForSide[par7];
/* 114:126 */     par5 += net.minecraft.util.Facing.offsetsYForSide[par7];
/* 115:127 */     par6 += net.minecraft.util.Facing.offsetsZForSide[par7];
/* 116:128 */     double d0 = 0.0D;
/* 117:130 */     if ((par7 == 1) && (i1 != null) && (i1.getRenderType() == 11)) {
/* 118:131 */       d0 = 0.5D;
/* 119:    */     }
/* 120:137 */     NBTTagCompound tags = par1ItemStack.getTagCompound();
/* 121:138 */     tags.setTag("Pos", newDoubleNBTList(new double[] { par4 + 0.5D, par5 + d0, par6 + 0.5D }));
/* 122:139 */     tags.setTag("Motion", newDoubleNBTList(new double[] { 0.0D, 0.0D, 0.0D }));
/* 123:    */     
/* 124:    */ 
/* 125:142 */     tags.setFloat("FallDistance", 0.0F);
/* 126:143 */     tags.setInteger("Dimension", par3World.provider.dimensionId);
/* 127:144 */     Entity entity = EntityList.createEntityFromNBT(tags, par3World);
/* 128:146 */     if ((entity != null) && 
/* 129:147 */       ((entity instanceof EntityLiving)) && (par1ItemStack.hasDisplayName())) {
/* 130:148 */       ((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
/* 131:    */     }
/* 132:152 */     par3World.spawnEntityInWorld(entity);
/* 133:153 */     par1ItemStack.setTagCompound(null);
/* 134:154 */     par1ItemStack.setItemDamage(0);
/* 135:156 */     if (par2EntityPlayer.capabilities.isCreativeMode) {
/* 136:157 */       par2EntityPlayer.setCurrentItemOrArmor(0, new ItemStack(ExtraUtils.goldenLasso, 1, 0));
/* 137:    */     }
/* 138:160 */     par2EntityPlayer.inventory.onInventoryChanged();
/* 139:161 */     return true;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected NBTTagList newDoubleNBTList(double... par1ArrayOfDouble)
/* 143:    */   {
/* 144:169 */     NBTTagList nbttaglist = new NBTTagList();
/* 145:170 */     double[] adouble = par1ArrayOfDouble;
/* 146:171 */     int i = par1ArrayOfDouble.length;
/* 147:173 */     for (int j = 0; j < i; j++)
/* 148:    */     {
/* 149:174 */       double d1 = adouble[j];
/* 150:175 */       nbttaglist.appendTag(new NBTTagDouble(d1));
/* 151:    */     }
/* 152:178 */     return nbttaglist;
/* 153:    */   }
/* 154:    */   
/* 155:    */   @SideOnly(Side.CLIENT)
/* 156:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 157:    */   {
/* 158:187 */     if ((par1ItemStack.hasTagCompound()) && 
/* 159:188 */       (par1ItemStack.getTagCompound().hasKey("id")))
/* 160:    */     {
/* 161:189 */       par3List.set(0, ((String)par3List.get(0)).replaceFirst(EnumChatFormatting.ITALIC + par1ItemStack.getDisplayName() + EnumChatFormatting.RESET, getItemStackDisplayName(par1ItemStack)));
/* 162:    */       
/* 163:191 */       String animal_name = StatCollector.translateToLocal("entity." + par1ItemStack.getTagCompound().getString("id") + ".name");
/* 164:192 */       par3List.add(animal_name);
/* 165:194 */       if (par1ItemStack.hasDisplayName()) {
/* 166:195 */         if (par1ItemStack.getTagCompound().hasKey("spoiler")) {
/* 167:196 */           par3List.add("*this " + animal_name.toLowerCase() + " has chosen a new name*");
/* 168:    */         } else {
/* 169:198 */           par3List.add(par1ItemStack.getDisplayName());
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemGoldenLasso
 * JD-Core Version:    0.7.0.1
 */