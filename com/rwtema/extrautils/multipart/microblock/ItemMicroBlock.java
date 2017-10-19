/*   1:    */ package com.rwtema.extrautils.multipart.microblock;
/*   2:    */ 
/*   3:    */ import codechicken.lib.vec.BlockCoord;
/*   4:    */ import codechicken.lib.vec.Vector3;
/*   5:    */ import codechicken.microblock.MicroMaterialRegistry;
/*   6:    */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*   7:    */ import codechicken.multipart.TMultiPart;
/*   8:    */ import codechicken.multipart.TileMultipart;
/*   9:    */ import com.rwtema.extrautils.ExtraUtils;
/*  10:    */ import com.rwtema.extrautils.ICreativeTabSorting;
/*  11:    */ import cpw.mods.fml.relauncher.Side;
/*  12:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import net.minecraft.creativetab.CreativeTabs;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  18:    */ import net.minecraft.item.Item;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.nbt.NBTTagCompound;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ import scala.Tuple2;
/*  23:    */ 
/*  24:    */ public class ItemMicroBlock
/*  25:    */   extends Item
/*  26:    */   implements ICreativeTabSorting
/*  27:    */ {
/*  28:    */   public static ItemMicroBlock instance;
/*  29:    */   
/*  30:    */   public ItemMicroBlock()
/*  31:    */   {
/*  32: 30 */     instance = this;
/*  33: 31 */     if (ExtraUtils.showMultiblocksTab) {
/*  34: 32 */       setCreativeTab(CreativeTabMicroBlocks.instance);
/*  35:    */     }
/*  36: 33 */     setUnlocalizedName("extrautils:microblocks");
/*  37: 34 */     setHasSubtypes(true);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static ItemStack getStack(ItemStack item, String material)
/*  41:    */   {
/*  42: 38 */     item = item.copy();
/*  43: 39 */     NBTTagCompound tag = new NBTTagCompound();
/*  44: 40 */     tag.setString("mat", material);
/*  45: 41 */     item.setTagCompound(tag);
/*  46: 42 */     return item;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getItemStackDisplayName(ItemStack stack)
/*  50:    */   {
/*  51: 47 */     if (!stack.hasTagCompound()) {
/*  52: 48 */       return "ERR";
/*  53:    */     }
/*  54: 51 */     String material = stack.getTagCompound().getString("mat");
/*  55: 53 */     if (MicroMaterialRegistry.getMaterial(material) == null) {
/*  56: 54 */       return "ERR";
/*  57:    */     }
/*  58: 58 */     return MicroMaterialRegistry.getMaterial(material).getLocalizedName() + " " + super.getItemStackDisplayName(stack);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*  62:    */   {
/*  63: 63 */     return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
/*  64:    */   }
/*  65:    */   
/*  66:    */   @SideOnly(Side.CLIENT)
/*  67:    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*  68:    */   {
/*  69: 70 */     for (IMicroBlock microBlock : RegisterMicroBlocks.mParts.values()) {
/*  70: 71 */       if (!microBlock.hideCreativeTab())
/*  71:    */       {
/*  72: 72 */         int meta = microBlock.getMetadata();
/*  73: 73 */         ItemStack item = new ItemStack(par1, 1, meta);
/*  74: 75 */         for (Tuple2<String, MicroMaterialRegistry.IMicroMaterial> t : MicroMaterialRegistry.getIdMap()) {
/*  75: 76 */           par3List.add(getStack(item, (String)t._1()));
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public double getHitDepth(Vector3 vhit, int side)
/*  82:    */   {
/*  83: 83 */     return vhit.copy().scalarProject(codechicken.lib.vec.Rotation.axes[side]) + (side % 2 ^ 0x1);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*  87:    */   {
/*  88: 88 */     BlockCoord pos = new BlockCoord(x, y, z);
/*  89: 89 */     Vector3 vhit = new Vector3(hitX, hitY, hitZ);
/*  90: 90 */     double d = getHitDepth(vhit, side);
/*  91: 92 */     if ((d < 1.0D) && (place(item, player, world, pos, side, vhit))) {
/*  92: 93 */       return true;
/*  93:    */     }
/*  94: 96 */     pos.offset(side);
/*  95: 97 */     return place(item, player, world, pos, side, vhit);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean place(ItemStack stack, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 arg5)
/*  99:    */   {
/* 100:101 */     TMultiPart part = newPart(stack, player, world, pos, side, arg5);
/* 101:103 */     if ((part == null) || (!TileMultipart.canPlacePart(world, pos, part))) {
/* 102:104 */       return false;
/* 103:    */     }
/* 104:107 */     if (!world.isClient) {
/* 105:108 */       TileMultipart.addPart(world, pos, part);
/* 106:    */     }
/* 107:111 */     if (!player.capabilities.isCreativeMode) {
/* 108:112 */       stack.stackSize -= 1;
/* 109:    */     }
/* 110:115 */     return true;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public TMultiPart newPart(ItemStack stack, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 arg5)
/* 114:    */   {
/* 115:119 */     if (!stack.hasTagCompound()) {
/* 116:120 */       return null;
/* 117:    */     }
/* 118:123 */     String material = stack.getTagCompound().getString("mat");
/* 119:125 */     if ((material.equals("")) || (MicroMaterialRegistry.getMaterial(material) == null)) {
/* 120:126 */       return null;
/* 121:    */     }
/* 122:129 */     IMicroBlock part = (IMicroBlock)RegisterMicroBlocks.mParts.get(Integer.valueOf(stack.getItemDamage()));
/* 123:131 */     if (part != null) {
/* 124:132 */       return part.placePart(stack, player, world, pos, side, arg5, MicroMaterialRegistry.materialID(material));
/* 125:    */     }
/* 126:135 */     return null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getSortingName(ItemStack par1ItemStack)
/* 130:    */   {
/* 131:140 */     return par1ItemStack.getUnlocalizedName() + "_" + par1ItemStack.getItemDamage() + "_" + par1ItemStack.getDisplayName();
/* 132:    */   }
/* 133:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.ItemMicroBlock
 * JD-Core Version:    0.7.0.1
 */