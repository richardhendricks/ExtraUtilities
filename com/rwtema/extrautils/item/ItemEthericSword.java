/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Multimap;
/*   4:    */ import com.rwtema.extrautils.ExtraUtils;
/*   5:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  13:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  14:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.init.Blocks;
/*  17:    */ import net.minecraft.item.EnumAction;
/*  18:    */ import net.minecraft.item.Item.ToolMaterial;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.item.ItemSword;
/*  21:    */ import net.minecraft.util.IIcon;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ 
/*  24:    */ public class ItemEthericSword
/*  25:    */   extends ItemSword
/*  26:    */   implements IItemMultiTransparency
/*  27:    */ {
/*  28:    */   private double weaponDamage;
/*  29:    */   private IIcon[] icons;
/*  30:    */   
/*  31:    */   public ItemEthericSword()
/*  32:    */   {
/*  33: 36 */     super(Item.ToolMaterial.IRON);
/*  34: 37 */     this.maxStackSize = 1;
/*  35: 38 */     setMaxDamage(Item.ToolMaterial.EMERALD.getMaxUses());
/*  36: 39 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  37: 40 */     setUnlocalizedName("extrautils:ethericsword");
/*  38: 41 */     this.weaponDamage = 8.0D;
/*  39:    */   }
/*  40:    */   
/*  41:    */   @SideOnly(Side.CLIENT)
/*  42:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/*  43:    */   {
/*  44: 47 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
/*  48:    */   {
/*  49: 56 */     if (par2Block == Blocks.web) {
/*  50: 57 */       return 15.0F;
/*  51:    */     }
/*  52: 59 */     Material var3 = par2Block.getMaterial();
/*  53: 60 */     return (var3 != Material.plants) && (var3 != Material.vine) && (var3 != Material.coral) && (var3 != Material.leaves) && (var3 != Material.plants) ? 1.0F : 1.5F;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving)
/*  57:    */   {
/*  58: 66 */     return true;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLiving)
/*  62:    */   {
/*  63: 72 */     if (par3.getBlockHardness(par2World, par4, par5, par6) != 0.0D) {
/*  64: 73 */       par1ItemStack.damageItem(1, par7EntityLiving);
/*  65:    */     }
/*  66: 76 */     return true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   @SideOnly(Side.CLIENT)
/*  70:    */   public boolean isFull3D()
/*  71:    */   {
/*  72: 85 */     return true;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/*  76:    */   {
/*  77: 94 */     return EnumAction.block;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/*  81:    */   {
/*  82:102 */     return 72000;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
/*  86:    */   {
/*  87:111 */     if ((ExtraUtils.lawSwordEnabled) && (XUHelper.isThisPlayerACheatyBastardOfCheatBastardness(player))) {
/*  88:112 */       return ItemLawSword.newSword();
/*  89:    */     }
/*  90:114 */     player.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
/*  91:115 */     return par1ItemStack;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean canHarvestBlock(Block par1Block, ItemStack item)
/*  95:    */   {
/*  96:123 */     return par1Block == Blocks.web;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/* 100:    */   {
/* 101:131 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   @SideOnly(Side.CLIENT)
/* 105:    */   public void registerIcons(IIconRegister par1IIconRegister)
/* 106:    */   {
/* 107:137 */     this.icons = new IIcon[2];
/* 108:138 */     this.itemIcon = (this.icons[0] =  = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5)));
/* 109:139 */     this.icons[1] = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5) + "1");
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int numIcons(ItemStack item)
/* 113:    */   {
/* 114:144 */     return 2;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public IIcon getIconForTransparentRender(ItemStack item, int pass)
/* 118:    */   {
/* 119:149 */     return this.icons[pass];
/* 120:    */   }
/* 121:    */   
/* 122:    */   public float getIconTransparency(ItemStack item, int pass)
/* 123:    */   {
/* 124:154 */     if (pass == 1) {
/* 125:155 */       return 0.5F;
/* 126:    */     }
/* 127:157 */     return 1.0F;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Multimap getItemAttributeModifiers()
/* 131:    */   {
/* 132:163 */     Multimap multimap = super.getItemAttributeModifiers();
/* 133:164 */     multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.weaponDamage, 0));
/* 134:165 */     return multimap;
/* 135:    */   }
/* 136:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemEthericSword
 * JD-Core Version:    0.7.0.1
 */