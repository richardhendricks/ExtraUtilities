/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.nbt.NBTTagCompound;
/*  14:    */ import net.minecraft.util.IIcon;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ import net.minecraftforge.oredict.OreDictionary;
/*  17:    */ 
/*  18:    */ public class ItemPaintbrush
/*  19:    */   extends Item
/*  20:    */ {
/*  21:    */   private IIcon[] icons;
/*  22:    */   
/*  23:    */   public ItemPaintbrush()
/*  24:    */   {
/*  25: 24 */     setMaxStackSize(1);
/*  26: 25 */     setMaxDamage(0);
/*  27: 26 */     setHasSubtypes(true);
/*  28: 27 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  29: 28 */     setContainerItem(this);
/*  30: 29 */     setUnlocalizedName("extrautils:paintbrush");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static ItemStack setColor(ItemStack par1ItemStack, int color, int damage)
/*  34:    */   {
/*  35: 33 */     return setColor(par1ItemStack, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, damage);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static ItemStack setColor(ItemStack par1ItemStack, int r, int g, int b, int damage)
/*  39:    */   {
/*  40: 37 */     if (par1ItemStack.getTagCompound() == null) {
/*  41: 38 */       par1ItemStack.setTagCompound(new NBTTagCompound());
/*  42:    */     }
/*  43: 41 */     par1ItemStack.getTagCompound().setInteger("r", r & 0xFF);
/*  44: 42 */     par1ItemStack.getTagCompound().setInteger("g", g & 0xFF);
/*  45: 43 */     par1ItemStack.getTagCompound().setInteger("b", b & 0xFF);
/*  46: 45 */     if (damage >= 0) {
/*  47: 46 */       par1ItemStack.getTagCompound().setInteger("damage", damage);
/*  48:    */     }
/*  49: 49 */     return par1ItemStack;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static int getColor(ItemStack par1ItemStack)
/*  53:    */   {
/*  54: 53 */     int r = 255;int g = 255;int b = 255;
/*  55: 55 */     if (par1ItemStack.getTagCompound() != null)
/*  56:    */     {
/*  57: 56 */       if (par1ItemStack.getTagCompound().hasKey("r")) {
/*  58: 57 */         r = par1ItemStack.getTagCompound().getInteger("r");
/*  59:    */       }
/*  60: 60 */       if (par1ItemStack.getTagCompound().hasKey("g")) {
/*  61: 61 */         g = par1ItemStack.getTagCompound().getInteger("g");
/*  62:    */       }
/*  63: 64 */       if (par1ItemStack.getTagCompound().hasKey("b")) {
/*  64: 65 */         b = par1ItemStack.getTagCompound().getInteger("b");
/*  65:    */       }
/*  66:    */     }
/*  67: 69 */     return r << 16 | g << 8 | b;
/*  68:    */   }
/*  69:    */   
/*  70:    */   @SideOnly(Side.CLIENT)
/*  71:    */   public void registerIcons(IIconRegister par1IIconRegister)
/*  72:    */   {
/*  73: 75 */     this.icons = new IIcon[2];
/*  74: 76 */     this.icons[0] = par1IIconRegister.registerIcon("extrautils:paintbrush_base");
/*  75: 77 */     this.icons[1] = par1IIconRegister.registerIcon("extrautils:paintbrush_brush");
/*  76:    */   }
/*  77:    */   
/*  78:    */   @SideOnly(Side.CLIENT)
/*  79:    */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/*  80:    */   {
/*  81: 83 */     if (par2 == 1) {
/*  82: 84 */       return getColor(par1ItemStack);
/*  83:    */     }
/*  84: 86 */     return 16777215;
/*  85:    */   }
/*  86:    */   
/*  87:    */   @SideOnly(Side.CLIENT)
/*  88:    */   public boolean requiresMultipleRenderPasses()
/*  89:    */   {
/*  90: 93 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public IIcon getIcon(ItemStack stack, int pass)
/*  94:    */   {
/*  95: 98 */     return this.icons[pass];
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
/*  99:    */   {
/* 100:103 */     return true;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
/* 104:    */   {
/* 105:113 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean onItemUse(ItemStack item, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
/* 109:    */   {
/* 110:119 */     if (world.isClient) {
/* 111:120 */       return true;
/* 112:    */     }
/* 113:    */     ArrayList<ItemStack> items;
/* 114:123 */     if ((item.getTagCompound() != null) && (item.getTagCompound().hasKey("damage")))
/* 115:    */     {
/* 116:124 */       if ((ExtraUtils.colorBlockBrick != null) && (world.getBlock(x, y, z) == Blocks.stonebrick) && (world.getBlockMetadata(x, y, z) == 0))
/* 117:    */       {
/* 118:125 */         world.setBlock(x, y, z, ExtraUtils.colorBlockBrick, item.getTagCompound().getInteger("damage") & 0xF, 3);
/* 119:    */         
/* 120:    */ 
/* 121:    */ 
/* 122:129 */         return true;
/* 123:    */       }
/* 124:132 */       if (ExtraUtils.coloredWood != null)
/* 125:    */       {
/* 126:133 */         Block id = world.getBlock(x, y, z);
/* 127:135 */         if (!world.isAirBlock(x, y, z))
/* 128:    */         {
/* 129:136 */           items = id.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
/* 130:138 */           if ((items != null) && (items.size() == 1) && (((ItemStack)items.get(0)).stackSize == 1) && (((ItemStack)items.get(0)).getItem() == Item.getItemFromBlock(world.getBlock(x, y, z)))) {
/* 131:139 */             for (ItemStack target : OreDictionary.getOres("plankWood")) {
/* 132:140 */               if (OreDictionary.itemMatches(target, (ItemStack)items.get(0), false))
/* 133:    */               {
/* 134:141 */                 world.setBlock(x, y, z, ExtraUtils.coloredWood, item.getTagCompound().getInteger("damage") & 0xF, 3);
/* 135:142 */                 return true;
/* 136:    */               }
/* 137:    */             }
/* 138:    */           }
/* 139:    */         }
/* 140:    */       }
/* 141:    */     }
/* 142:148 */     return false;
/* 143:    */   }
/* 144:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemPaintbrush
 * JD-Core Version:    0.7.0.1
 */