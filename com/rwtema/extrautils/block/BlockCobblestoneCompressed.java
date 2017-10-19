/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ICreativeTabSorting;
/*   5:    */ import com.rwtema.extrautils.texture.TextureComprBlock;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Locale;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.block.material.Material;
/*  12:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  13:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  14:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  15:    */ import net.minecraft.creativetab.CreativeTabs;
/*  16:    */ import net.minecraft.entity.Entity;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*  19:    */ import net.minecraft.init.Blocks;
/*  20:    */ import net.minecraft.item.Item;
/*  21:    */ import net.minecraft.item.ItemStack;
/*  22:    */ import net.minecraft.util.IIcon;
/*  23:    */ import net.minecraft.world.Explosion;
/*  24:    */ import net.minecraft.world.IBlockAccess;
/*  25:    */ import net.minecraft.world.World;
/*  26:    */ import net.minecraftforge.common.ForgeHooks;
/*  27:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  28:    */ 
/*  29:    */ public class BlockCobblestoneCompressed
/*  30:    */   extends Block
/*  31:    */   implements IBlockTooltip, ICreativeTabSorting
/*  32:    */ {
/*  33: 31 */   private IIcon[] icons = new IIcon[16];
/*  34:    */   
/*  35:    */   public BlockCobblestoneCompressed(Material par2Material)
/*  36:    */   {
/*  37: 34 */     super(par2Material);
/*  38: 35 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  39: 36 */     setHardness(2.0F);
/*  40: 37 */     setStepSound(soundTypeStone);
/*  41: 38 */     setResistance(10.0F);
/*  42: 39 */     setBlockName("extrautils:cobblestone_compressed");
/*  43: 40 */     setBlockTextureName("extrautils:cobblestone_compressed");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static String getOreDictName(int metadata)
/*  47:    */   {
/*  48: 44 */     if (metadata < 8) {
/*  49: 45 */       return "Cobblestone";
/*  50:    */     }
/*  51: 46 */     if (metadata < 12) {
/*  52: 47 */       return "Dirt";
/*  53:    */     }
/*  54: 48 */     if (metadata < 14) {
/*  55: 49 */       return "Gravel";
/*  56:    */     }
/*  57: 51 */     return "Sand";
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static Block getBlock(int metadata)
/*  61:    */   {
/*  62: 56 */     if (metadata < 8) {
/*  63: 57 */       return Blocks.cobblestone;
/*  64:    */     }
/*  65: 58 */     if (metadata < 12) {
/*  66: 59 */       return Blocks.dirt;
/*  67:    */     }
/*  68: 60 */     if (metadata < 14) {
/*  69: 61 */       return Blocks.gravel;
/*  70:    */     }
/*  71: 63 */     return Blocks.sand;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static boolean isBaseBlock(int metadata)
/*  75:    */   {
/*  76: 68 */     return (metadata == 0) || (metadata == 8) || (metadata == 12) || (metadata == 14);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static int getCompr(int metadata)
/*  80:    */   {
/*  81: 72 */     if (metadata < 8) {
/*  82: 73 */       return metadata;
/*  83:    */     }
/*  84: 74 */     if (metadata < 12) {
/*  85: 75 */       return metadata - 8;
/*  86:    */     }
/*  87: 76 */     if (metadata < 14) {
/*  88: 77 */       return metadata - 12;
/*  89:    */     }
/*  90: 79 */     return metadata - 14;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean canDropFromExplosion(Explosion par1Explosion)
/*  94:    */   {
/*  95: 85 */     return (par1Explosion == null) || (!(par1Explosion.exploder instanceof EntityWitherSkull));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
/*  99:    */   {
/* 100: 91 */     if ((!world.isClient) && (canDropFromExplosion(explosion))) {
/* 101: 92 */       super.onBlockExploded(world, x, y, z, explosion);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5)
/* 106:    */   {
/* 107: 97 */     return ForgeHooks.blockStrength(getBlock(par2World.getBlockMetadata(par3, par4, par5)), par1EntityPlayer, par2World, par3, par4, par5);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean canHarvestBlock(EntityPlayer player, int meta)
/* 111:    */   {
/* 112:102 */     return ForgeHooks.canHarvestBlock(getBlock(meta), player, meta);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
/* 116:    */   {
/* 117:108 */     return getBlock(world.getBlockMetadata(x, y, z)).isFireSource(world, x, y, z, side);
/* 118:    */   }
/* 119:    */   
/* 120:    */   @SideOnly(Side.CLIENT)
/* 121:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 122:    */   {
/* 123:114 */     if (!(par1IIconRegister instanceof TextureMap)) {
/* 124:115 */       return;
/* 125:    */     }
/* 126:118 */     for (int i = 0; i < 16; i++)
/* 127:    */     {
/* 128:119 */       if (getBlock(i).getIcon(0, 0) == null) {
/* 129:120 */         getBlock(i).registerBlockIcons(par1IIconRegister);
/* 130:    */       }
/* 131:123 */       String icon_name = getBlock(i).getIcon(0, 0).getIconName();
/* 132:124 */       int c = getCompr(i);
/* 133:125 */       String t = "extrautils:" + icon_name + "_compressed_" + (c + 1);
/* 134:126 */       this.icons[i] = ((TextureMap)par1IIconRegister).getTextureExtry(t);
/* 135:128 */       if (this.icons[i] == null)
/* 136:    */       {
/* 137:129 */         TextureAtlasSprite t2 = new TextureComprBlock(t, icon_name, 1 + c);
/* 138:130 */         this.icons[i] = t2;
/* 139:131 */         ((TextureMap)par1IIconRegister).setTextureEntry(t, t2);
/* 140:    */       }
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   @SideOnly(Side.CLIENT)
/* 145:    */   public IIcon getIcon(int par1, int par2)
/* 146:    */   {
/* 147:143 */     return this.icons[par2];
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int damageDropped(int par1)
/* 151:    */   {
/* 152:152 */     return par1;
/* 153:    */   }
/* 154:    */   
/* 155:    */   @SideOnly(Side.CLIENT)
/* 156:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 157:    */   {
/* 158:161 */     for (int var4 = 0; var4 < 16; var4++) {
/* 159:162 */       par3List.add(new ItemStack(par1, 1, var4));
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 164:    */   {
/* 165:168 */     int i = par1ItemStack.getItemDamage();
/* 166:169 */     par3List.add(String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf((int)Math.pow(9.0D, getCompr(i) + 1)) }) + " " + getBlock(i).getLocalizedName());
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getSortingName(ItemStack item)
/* 170:    */   {
/* 171:174 */     ItemStack i = item.copy();
/* 172:175 */     i.setItemDamage(i.getItemDamage() - getCompr(i.getItemDamage()));
/* 173:176 */     return i.getDisplayName();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
/* 177:    */   {
/* 178:181 */     return getCompr(world.getBlockMetadata(x, y, z)) < 6;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
/* 182:    */   {
/* 183:186 */     int metadata = world.getBlockMetadata(x, y, z);
/* 184:187 */     return getBlock(metadata).getExplosionResistance(par1Entity) * (int)Math.pow(1.5D, 1 + getCompr(metadata));
/* 185:    */   }
/* 186:    */   
/* 187:    */   public float getBlockHardness(World world, int x, int y, int z)
/* 188:    */   {
/* 189:192 */     int metadata = world.getBlockMetadata(x, y, z);
/* 190:193 */     return (int)(getBlock(metadata).getBlockHardness(world, x, y, z) * Math.pow(2.5D, 1 + getCompr(metadata)));
/* 191:    */   }
/* 192:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockCobblestoneCompressed
 * JD-Core Version:    0.7.0.1
 */