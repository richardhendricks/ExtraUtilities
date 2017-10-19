/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Multimap;
/*   4:    */ import com.rwtema.extrautils.ExtraUtils;
/*   5:    */ import com.rwtema.extrautils.crafting.LawSwordCraftHandler;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.List;
/*  10:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*  11:    */ import net.minecraft.entity.Entity;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  14:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  15:    */ import net.minecraft.entity.ai.attributes.BaseAttribute;
/*  16:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  17:    */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*  18:    */ import net.minecraft.entity.player.EntityPlayer;
/*  19:    */ import net.minecraft.item.Item.ToolMaterial;
/*  20:    */ import net.minecraft.item.ItemStack;
/*  21:    */ import net.minecraft.item.ItemSword;
/*  22:    */ import net.minecraft.potion.Potion;
/*  23:    */ import net.minecraft.util.DamageSource;
/*  24:    */ import net.minecraft.util.EntityDamageSource;
/*  25:    */ import net.minecraft.util.StatCollector;
/*  26:    */ import net.minecraft.world.World;
/*  27:    */ import net.minecraftforge.common.util.EnumHelper;
/*  28:    */ 
/*  29:    */ public class ItemLawSword
/*  30:    */   extends ItemSword
/*  31:    */ {
/*  32: 29 */   public static Item.ToolMaterial material = EnumHelper.addToolMaterial("OpeOpeNoMi", 3, 4096, 8.0F, 3.0F, 10);
/*  33:    */   
/*  34:    */   static
/*  35:    */   {
/*  36: 32 */     LawSwordCraftHandler.init();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ItemLawSword()
/*  40:    */   {
/*  41: 36 */     super(material);
/*  42: 37 */     this.maxStackSize = 1;
/*  43: 38 */     setUnlocalizedName("extrautils:lawSword");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
/*  47:    */   {
/*  48: 43 */     p_77659_3_.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
/*  49: 44 */     return p_77659_1_;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving)
/*  53:    */   {
/*  54: 49 */     return true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
/*  58:    */   {
/*  59: 54 */     if (entity.canAttackWithItem())
/*  60:    */     {
/*  61: 55 */       Multimap<String, AttributeModifier> multimap = stack.getAttributeModifiers();
/*  62:    */       
/*  63:    */ 
/*  64: 58 */       Collection<AttributeModifier> gsd = multimap.get(godSlayingDamage.getAttributeUnlocalizedName());
/*  65: 60 */       if (gsd != null) {
/*  66: 61 */         for (AttributeModifier t : gsd) {
/*  67: 62 */           attackEntity(player, entity, t.getAmount(), new DamageSourceEvil(player, true));
/*  68:    */         }
/*  69:    */       }
/*  70: 64 */       gsd = multimap.get(armorPiercingDamage.getAttributeUnlocalizedName());
/*  71: 65 */       if (gsd != null) {
/*  72: 66 */         for (AttributeModifier gs : gsd) {
/*  73: 67 */           attackEntity(player, entity, gs.getAmount(), new DamageSourceEvil(player, false));
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77: 71 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void attackEntity(EntityPlayer player, Entity entity, double f, DamageSource damageSource)
/*  81:    */   {
/*  82: 75 */     float f1 = 0.0F;
/*  83: 77 */     if ((entity instanceof EntityLivingBase)) {
/*  84: 78 */       f1 = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLivingBase)entity);
/*  85:    */     }
/*  86: 81 */     if ((f > 0.0D) || (f1 > 0.0F))
/*  87:    */     {
/*  88: 82 */       boolean flag = (player.fallDistance > 0.0F) && (!player.onGround) && (!player.isOnLadder()) && (!player.isInWater()) && (!player.isPotionActive(Potion.blindness)) && (player.ridingEntity == null) && ((entity instanceof EntityLivingBase));
/*  89: 84 */       if ((flag) && (f > 0.0D)) {
/*  90: 85 */         f *= 1.5D;
/*  91:    */       }
/*  92: 88 */       f += f1;
/*  93:    */       
/*  94:    */ 
/*  95: 91 */       boolean flag2 = entity.attackEntityFrom(damageSource, (float)f);
/*  96: 93 */       if (flag2)
/*  97:    */       {
/*  98: 94 */         if (flag) {
/*  99: 95 */           player.onCriticalHit(entity);
/* 100:    */         }
/* 101: 98 */         if (f1 > 0.0F) {
/* 102: 99 */           player.onEnchantmentCritical(entity);
/* 103:    */         }
/* 104:102 */         player.setLastAttacker(entity);
/* 105:104 */         if ((entity instanceof EntityLivingBase)) {
/* 106:105 */           EnchantmentHelper.func_151384_a((EntityLivingBase)entity, player);
/* 107:    */         }
/* 108:108 */         EnchantmentHelper.func_151385_b(player, entity);
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Multimap getAttributeModifiers(ItemStack stack)
/* 114:    */   {
/* 115:115 */     Multimap multimap = super.getItemAttributeModifiers();
/* 116:116 */     multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 4.0D, 0));
/* 117:117 */     multimap.put(godSlayingDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 2.0D, 0));
/* 118:118 */     multimap.put(armorPiercingDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 4.0D, 0));
/* 119:119 */     return multimap;
/* 120:    */   }
/* 121:    */   
/* 122:122 */   public static BaseAttribute godSlayingDamage = new RangedAttribute("extrautils.godSlayingAttackDamage", 2.0D, 0.0D, 1.7976931348623157E+308D);
/* 123:123 */   public static BaseAttribute armorPiercingDamage = new RangedAttribute("extrautils.armorPiercingAttackDamage", 2.0D, 0.0D, 1.7976931348623157E+308D);
/* 124:    */   
/* 125:    */   public static class DamageSourceEvil
/* 126:    */     extends EntityDamageSource
/* 127:    */   {
/* 128:    */     public DamageSourceEvil(EntityPlayer player, boolean creative)
/* 129:    */     {
/* 130:128 */       super(player);
/* 131:129 */       setDamageBypassesArmor();
/* 132:130 */       setDamageIsAbsolute();
/* 133:131 */       if (creative) {
/* 134:132 */         setDamageAllowedInCreativeMode();
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   @SideOnly(Side.CLIENT)
/* 140:    */   public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_)
/* 141:    */   {
/* 142:140 */     super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
/* 143:141 */     p_77624_3_.add(("" + StatCollector.translateToLocal(new StringBuilder().append(getUnlocalizedNameInefficiently(p_77624_1_)).append(".tooltip").toString())).trim());
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static ItemStack newSword()
/* 147:    */   {
/* 148:145 */     return new ItemStack(ExtraUtils.lawSword);
/* 149:    */   }
/* 150:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemLawSword
 * JD-Core Version:    0.7.0.1
 */