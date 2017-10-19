/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   6:    */ import com.rwtema.extrautils.item.ItemPaintbrush;
/*   7:    */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*   8:    */ import com.rwtema.extrautils.texture.TextureColorBlockBase;
/*   9:    */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*  10:    */ import cpw.mods.fml.relauncher.Side;
/*  11:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  12:    */ import java.util.List;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  15:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  16:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  17:    */ import net.minecraft.creativetab.CreativeTabs;
/*  18:    */ import net.minecraft.entity.Entity;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.init.Items;
/*  21:    */ import net.minecraft.item.Item;
/*  22:    */ import net.minecraft.item.ItemArmor;
/*  23:    */ import net.minecraft.item.ItemPotion;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.nbt.NBTTagCompound;
/*  26:    */ import net.minecraft.util.AxisAlignedBB;
/*  27:    */ import net.minecraft.util.IIcon;
/*  28:    */ import net.minecraft.world.IBlockAccess;
/*  29:    */ import net.minecraft.world.World;
/*  30:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  31:    */ 
/*  32:    */ public class BlockColor
/*  33:    */   extends Block
/*  34:    */ {
/*  35: 35 */   public static float[][] initColor = new float[16][3];
/*  36:    */   
/*  37:    */   static
/*  38:    */   {
/*  39: 38 */     float saturation = 0.85F;
/*  40: 39 */     for (int i = 0; i < 16; i++)
/*  41:    */     {
/*  42: 40 */       float r = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[i][0];
/*  43: 41 */       float g = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[i][1];
/*  44: 42 */       float b = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[i][2];
/*  45: 43 */       float m = (r + g + b) / 3.0F * (1.0F - saturation);
/*  46: 44 */       initColor[i][0] = (r * saturation + m);
/*  47: 45 */       initColor[i][1] = (g * saturation + m);
/*  48: 46 */       initColor[i][2] = (b * saturation + m);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52: 51 */   public int curMetadata = 0;
/*  53:    */   public boolean specialTexture;
/*  54:    */   public String oreName;
/*  55:    */   public Block baseBlock;
/*  56:    */   
/*  57:    */   public BlockColor(Block b, String orename)
/*  58:    */   {
/*  59: 58 */     this(b, orename, (String)ReflectionHelper.getPrivateValue(Block.class, b, new String[] { "textureName", "textureName" }));
/*  60:    */   }
/*  61:    */   
/*  62: 61 */   public Object[] customRecipe = null;
/*  63: 62 */   public int customRecipeNo = 0;
/*  64:    */   
/*  65:    */   @SideOnly(Side.CLIENT)
/*  66:    */   public int getBlockColor()
/*  67:    */   {
/*  68: 67 */     return super.getBlockColor();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public BlockColor(Block b, String orename, String texture)
/*  72:    */   {
/*  73: 71 */     super(b.getMaterial());
/*  74: 72 */     setHardness(((Float)ReflectionHelper.getPrivateValue(Block.class, b, new String[] { "blockHardness", "blockHardness" })).floatValue());
/*  75: 73 */     setResistance(((Float)ReflectionHelper.getPrivateValue(Block.class, b, new String[] { "blockResistance", "blockResistance" })).floatValue());
/*  76: 74 */     setStepSound(b.stepSound);
/*  77: 75 */     setBlockTextureName(texture);
/*  78: 76 */     setBlockName("extrautils:color_" + b.getUnlocalizedName().substring(5));
/*  79: 77 */     setLightLevel(b.getLightValue() / 15.0F);
/*  80: 78 */     setLightOpacity(b.getLightOpacity());
/*  81: 79 */     this.oreName = orename;
/*  82: 80 */     this.baseBlock = b;
/*  83: 81 */     ExtraUtils.colorblocks.add(this);
/*  84:    */   }
/*  85:    */   
/*  86:    */   @SideOnly(Side.CLIENT)
/*  87:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  88:    */   {
/*  89: 88 */     if (!(par1IIconRegister instanceof TextureMap)) {
/*  90: 89 */       return;
/*  91:    */     }
/*  92: 92 */     String t = getTextureName();
/*  93: 93 */     this.blockIcon = ((TextureMap)par1IIconRegister).getTextureExtry("extrautils:bw_(" + t + ")");
/*  94: 95 */     if (this.blockIcon == null)
/*  95:    */     {
/*  96: 96 */       TextureAtlasSprite t2 = new TextureColorBlockBase(t);
/*  97: 97 */       this.blockIcon = t2;
/*  98: 98 */       ((TextureMap)par1IIconRegister).setTextureEntry("extrautils:bw_(" + t + ")", t2);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
/* 103:    */   {
/* 104:108 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 105:    */   }
/* 106:    */   
/* 107:    */   @SideOnly(Side.CLIENT)
/* 108:    */   public IIcon getIcon(int par1, int par2)
/* 109:    */   {
/* 110:117 */     this.curMetadata = par2;
/* 111:118 */     return super.getIcon(par1, par2);
/* 112:    */   }
/* 113:    */   
/* 114:    */   @SideOnly(Side.CLIENT)
/* 115:    */   public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
/* 116:    */   {
/* 117:128 */     if (par1IBlockAccess.getBlock(par2, par3, par4) == this) {
/* 118:129 */       this.curMetadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
/* 119:    */     }
/* 120:132 */     float[] col = BlockColorData.getColorData(par1IBlockAccess, par2, par3, par4, this.curMetadata);
/* 121:    */     
/* 122:134 */     return (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F);
/* 123:    */   }
/* 124:    */   
/* 125:    */   @SideOnly(Side.CLIENT)
/* 126:    */   public int getRenderColor(int p_149741_1_)
/* 127:    */   {
/* 128:139 */     float[] col = initColor[p_149741_1_];
/* 129:140 */     return (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int getRenderType()
/* 133:    */   {
/* 134:148 */     return ExtraUtilsProxy.colorBlockID;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int damageDropped(int par1)
/* 138:    */   {
/* 139:157 */     return par1;
/* 140:    */   }
/* 141:    */   
/* 142:    */   @SideOnly(Side.CLIENT)
/* 143:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 144:    */   {
/* 145:166 */     for (int j = 0; j < 16; j++) {
/* 146:167 */       par3List.add(new ItemStack(par1, 1, j));
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
/* 151:    */   {
/* 152:176 */     if ((par5EntityPlayer != null) && (par5EntityPlayer.getCurrentEquippedItem() != null))
/* 153:    */     {
/* 154:177 */       ItemStack itemstack = par5EntityPlayer.getCurrentEquippedItem();
/* 155:178 */       int metadata = par1World.getBlockMetadata(par2, par3, par4);
/* 156:180 */       if ((itemstack.getItem() instanceof ItemArmor))
/* 157:    */       {
/* 158:181 */         ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/* 159:183 */         if (itemarmor.hasColor(itemstack))
/* 160:    */         {
/* 161:184 */           int l = itemarmor.getColor(itemstack);
/* 162:185 */           float r = (l >> 16 & 0xFF) / 255.0F;
/* 163:186 */           float g = (l >> 8 & 0xFF) / 255.0F;
/* 164:187 */           float b = (l & 0xFF) / 255.0F;
/* 165:189 */           if (BlockColorData.changeColorData(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), r, g, b)) {
/* 166:190 */             return true;
/* 167:    */           }
/* 168:192 */           PacketTempChat.sendChat(par5EntityPlayer, "Unable to change color at this location");
/* 169:    */         }
/* 170:    */       }
/* 171:195 */       else if (XUHelper.getDyeFromItemStack(itemstack) >= 0)
/* 172:    */       {
/* 173:196 */         float p = 0.9F;
/* 174:197 */         float[] col1 = BlockColorData.getColorData(par1World, par2, par3, par4);
/* 175:198 */         float[] col2 = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[net.minecraft.block.BlockColored.func_150032_b(XUHelper.getDyeFromItemStack(itemstack))];
/* 176:199 */         float r = (col1[0] + col2[0]) / 2.0F;float g = (col1[1] + col2[1]) / 2.0F;float b = (col1[2] + col2[2]) / 2.0F;
/* 177:200 */         float f = (Math.max(Math.max(col1[0], col1[1]), col1[2]) + Math.max(Math.max(col2[0], col2[1]), col2[2])) / 2.0F;
/* 178:201 */         float f1 = Math.max(r, Math.max(g, b));
/* 179:202 */         r = r * f / f1;
/* 180:203 */         g = g * f / f1;
/* 181:204 */         b = b * f / f1;
/* 182:205 */         r = col1[0] * p + col2[0] * (1.0F - p);
/* 183:206 */         g = col1[1] * p + col2[1] * (1.0F - p);
/* 184:207 */         b = col1[2] * p + col2[2] * (1.0F - p);
/* 185:209 */         if (BlockColorData.changeColorData(par1World, par2, par3, par4, metadata, r, g, b)) {
/* 186:210 */           return true;
/* 187:    */         }
/* 188:212 */         PacketTempChat.sendChat(par5EntityPlayer, "Unable to change color at this location");
/* 189:    */       }
/* 190:214 */       else if (((itemstack.getItem() == Items.potionitem ? 1 : 0) & ((Items.potionitem.getEffects(itemstack) == null) || (Items.potionitem.getEffects(itemstack).isEmpty()) ? 1 : 0)) != 0)
/* 191:    */       {
/* 192:215 */         float r = initColor[metadata][0];
/* 193:216 */         float g = initColor[metadata][1];
/* 194:217 */         float b = initColor[metadata][2];
/* 195:219 */         if (BlockColorData.changeColorData(par1World, par2, par3, par4, metadata, r, g, b)) {
/* 196:220 */           return true;
/* 197:    */         }
/* 198:222 */         PacketTempChat.sendChat(par5EntityPlayer, "Unable to change color at this location");
/* 199:    */       }
/* 200:224 */       else if (itemstack.getItem() == ExtraUtils.paintBrush)
/* 201:    */       {
/* 202:225 */         if (!par5EntityPlayer.isSneaking())
/* 203:    */         {
/* 204:226 */           float r = 1.0F;float g = 1.0F;float b = 1.0F;
/* 205:227 */           NBTTagCompound tag = itemstack.getTagCompound();
/* 206:229 */           if (tag != null)
/* 207:    */           {
/* 208:230 */             if (tag.hasKey("r")) {
/* 209:231 */               r = tag.getInteger("r") / 255.0F;
/* 210:    */             }
/* 211:234 */             if (tag.hasKey("g")) {
/* 212:235 */               g = tag.getInteger("g") / 255.0F;
/* 213:    */             }
/* 214:238 */             if (tag.hasKey("b")) {
/* 215:239 */               b = tag.getInteger("b") / 255.0F;
/* 216:    */             }
/* 217:    */           }
/* 218:243 */           if (BlockColorData.changeColorData(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), r, g, b)) {
/* 219:244 */             return true;
/* 220:    */           }
/* 221:246 */           PacketTempChat.sendChat(par5EntityPlayer, "Unable to change color at this location");
/* 222:    */         }
/* 223:    */         else
/* 224:    */         {
/* 225:249 */           float[] col = BlockColorData.getColorData(par1World, par2, par3, par4);
/* 226:250 */           ItemPaintbrush.setColor(itemstack, (int)(col[0] * 255.0F), (int)(col[1] * 255.0F), (int)(col[2] * 255.0F), metadata);
/* 227:    */         }
/* 228:    */       }
/* 229:    */     }
/* 230:255 */     return false;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour)
/* 234:    */   {
/* 235:271 */     int metadata = world.getBlockMetadata(x, y, z);
/* 236:272 */     float p = 0.9F;
/* 237:273 */     float[] col1 = BlockColorData.getColorData(world, x, y, z);
/* 238:274 */     float[] col2 = initColor[colour];
/* 239:    */     
/* 240:276 */     float r = col1[0] * p + col2[0] * (1.0F - p);
/* 241:277 */     float g = col1[1] * p + col2[1] * (1.0F - p);
/* 242:278 */     float b = col1[2] * p + col2[2] * (1.0F - p);
/* 243:    */     
/* 244:280 */     return BlockColorData.changeColorData(world, x, y, z, metadata, r, g, b);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public BlockColor setCustomRecipe(int customRecipeNo, Object... customRecipe)
/* 248:    */   {
/* 249:285 */     this.customRecipe = customRecipe;
/* 250:286 */     this.customRecipeNo = customRecipeNo;
/* 251:287 */     return this;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
/* 255:    */   {
/* 256:292 */     return this.baseBlock.getCollisionBoundingBoxFromPool(world, x, y, z);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 260:    */   {
/* 261:297 */     this.baseBlock.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public boolean canProvidePower()
/* 265:    */   {
/* 266:302 */     return this.baseBlock.canProvidePower();
/* 267:    */   }
/* 268:    */   
/* 269:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/* 270:    */   {
/* 271:307 */     return this.baseBlock.isProvidingWeakPower(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, p_149709_5_);
/* 272:    */   }
/* 273:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockColor
 * JD-Core Version:    0.7.0.1
 */