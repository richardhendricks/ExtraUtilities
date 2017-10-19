/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   6:    */ import com.rwtema.extrautils.texture.TextureColorBlockBase;
/*   7:    */ import cpw.mods.fml.relauncher.Side;
/*   8:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   9:    */ import java.util.List;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ import net.minecraft.util.IIcon;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ 
/*  20:    */ public class ItemGoldenBag
/*  21:    */   extends Item
/*  22:    */ {
/*  23:    */   public IIcon bwIcon;
/*  24:    */   
/*  25:    */   public ItemGoldenBag()
/*  26:    */   {
/*  27: 24 */     setUnlocalizedName("extrautils:golden_bag");
/*  28: 25 */     setTextureName("extrautils:golden_bag");
/*  29: 26 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  30: 27 */     setMaxStackSize(1);
/*  31:    */   }
/*  32:    */   
/*  33:    */   @SideOnly(Side.CLIENT)
/*  34:    */   public void registerIcons(IIconRegister par1IIconRegister)
/*  35:    */   {
/*  36: 35 */     super.registerIcons(par1IIconRegister);
/*  37:    */     
/*  38: 37 */     String t = getIconString();
/*  39: 38 */     this.bwIcon = ((TextureMap)par1IIconRegister).getTextureExtry("extrautils:bw_(" + t + ")");
/*  40: 40 */     if (this.bwIcon == null)
/*  41:    */     {
/*  42: 41 */       TextureColorBlockBase t2 = new TextureColorBlockBase(t, "items");
/*  43: 42 */       t2.scale = 20.0F;
/*  44: 43 */       this.bwIcon = t2;
/*  45: 44 */       ((TextureMap)par1IIconRegister).setTextureEntry("extrautils:bw_(" + t + ")", t2);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static boolean isMagic(ItemStack item)
/*  50:    */   {
/*  51: 50 */     return (item.hasTagCompound()) && (item.getTagCompound().hasKey("enchanted"));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void setMagic(ItemStack item)
/*  55:    */   {
/*  56: 54 */     NBTTagCompound tag = item.getTagCompound();
/*  57: 56 */     if (tag == null) {
/*  58: 57 */       tag = new NBTTagCompound();
/*  59:    */     }
/*  60: 60 */     tag.setBoolean("enchanted", true);
/*  61: 61 */     item.setTagCompound(tag);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static ItemStack clearMagic(ItemStack item)
/*  65:    */   {
/*  66: 65 */     if (item == null) {
/*  67: 66 */       return null;
/*  68:    */     }
/*  69: 69 */     NBTTagCompound tag = item.getTagCompound();
/*  70: 71 */     if (tag == null) {
/*  71: 72 */       tag = new NBTTagCompound();
/*  72:    */     }
/*  73: 75 */     if (tag.hasKey("enchanted"))
/*  74:    */     {
/*  75: 76 */       tag.removeTag("enchanted");
/*  76: 78 */       if (tag.hasNoTags()) {
/*  77: 79 */         tag = null;
/*  78:    */       }
/*  79: 82 */       item.setTagCompound(tag);
/*  80:    */     }
/*  81: 85 */     return item;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  85:    */   {
/*  86: 90 */     if ((!par2World.isClient) && (!XUHelper.isPlayerFake(par3EntityPlayer))) {
/*  87: 91 */       par3EntityPlayer.openGui(ExtraUtilsMod.instance, 1, par2World, par3EntityPlayer.inventory.currentItem, 0, 0);
/*  88:    */     }
/*  89: 94 */     return par1ItemStack;
/*  90:    */   }
/*  91:    */   
/*  92:    */   @SideOnly(Side.CLIENT)
/*  93:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/*  94:    */   {
/*  95:100 */     return isMagic(par1ItemStack);
/*  96:    */   }
/*  97:    */   
/*  98:    */   @SideOnly(Side.CLIENT)
/*  99:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 100:    */   {
/* 101:106 */     if (isMagic(par1ItemStack)) {
/* 102:107 */       par3List.add("Reincarnating I");
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_)
/* 107:    */   {
/* 108:113 */     return getColor(p_82790_1_);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
/* 112:    */   {
/* 113:118 */     return getIconIndex(stack);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public IIcon getIconIndex(ItemStack item)
/* 117:    */   {
/* 118:123 */     return hasColor(item) ? this.bwIcon : super.getIconIndex(item);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean hasColor(ItemStack item)
/* 122:    */   {
/* 123:127 */     return (item.hasTagCompound()) && (item.getTagCompound().hasKey("Color"));
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getColor(ItemStack item)
/* 127:    */   {
/* 128:131 */     return hasColor(item) ? item.getTagCompound().getInteger("Color") : 16777215;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public ItemStack setColor(ItemStack item, int color)
/* 132:    */   {
/* 133:135 */     NBTTagCompound tag = item.getTagCompound();
/* 134:136 */     if (tag == null) {
/* 135:136 */       tag = new NBTTagCompound();
/* 136:    */     }
/* 137:137 */     tag.setInteger("Color", color);
/* 138:138 */     item.setTagCompound(tag);
/* 139:139 */     return item;
/* 140:    */   }
/* 141:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemGoldenBag
 * JD-Core Version:    0.7.0.1
 */