/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.material.Material;
/*   9:    */ import net.minecraft.client.renderer.IconFlipped;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.entity.Entity;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.EnumCreatureType;
/*  14:    */ import net.minecraft.entity.item.EntityItem;
/*  15:    */ import net.minecraft.init.Blocks;
/*  16:    */ import net.minecraft.init.Items;
/*  17:    */ import net.minecraft.inventory.IInventory;
/*  18:    */ import net.minecraft.item.Item;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.util.AxisAlignedBB;
/*  21:    */ import net.minecraft.util.IIcon;
/*  22:    */ import net.minecraft.util.MathHelper;
/*  23:    */ import net.minecraft.world.IBlockAccess;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ 
/*  26:    */ public class BlockConveyor
/*  27:    */   extends Block
/*  28:    */   implements IMultiBoxBlock
/*  29:    */ {
/*  30: 26 */   ItemStack potionEmptyStack = new ItemStack(Items.glass_bottle);
/*  31:    */   private IIcon[] icons;
/*  32:    */   
/*  33:    */   public BlockConveyor()
/*  34:    */   {
/*  35: 30 */     super(Material.iron);
/*  36:    */     
/*  37: 32 */     setBlockName("extrautils:conveyor");
/*  38: 33 */     setBlockTextureName("extrautils:conveyor");
/*  39: 34 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  40: 35 */     setHardness(5.0F);
/*  41: 36 */     setStepSound(soundTypeStone);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getRenderType()
/*  45:    */   {
/*  46: 44 */     return ExtraUtilsProxy.multiBlockID;
/*  47:    */   }
/*  48:    */   
/*  49:    */   @SideOnly(Side.CLIENT)
/*  50:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  51:    */   {
/*  52: 50 */     this.icons = new IIcon[3];
/*  53: 51 */     this.icons[0] = par1IIconRegister.registerIcon("extrautils:conveyor_top");
/*  54: 52 */     this.icons[1] = par1IIconRegister.registerIcon("extrautils:conveyor_side");
/*  55: 53 */     this.icons[2] = new IconFlipped(this.icons[1], true, false);
/*  56:    */   }
/*  57:    */   
/*  58:    */   @SideOnly(Side.CLIENT)
/*  59:    */   public IIcon getIcon(int par1, int par2)
/*  60:    */   {
/*  61: 59 */     IIcon t = prevIcon(par1, par2);
/*  62: 60 */     if (par2 % 2 == 1) {
/*  63: 61 */       if (t == this.icons[1]) {
/*  64: 62 */         t = this.icons[2];
/*  65: 63 */       } else if (t == this.icons[2]) {
/*  66: 64 */         t = this.icons[1];
/*  67:    */       }
/*  68:    */     }
/*  69: 67 */     if (shouldFlip(par1)) {
/*  70: 68 */       if (t == this.icons[1]) {
/*  71: 69 */         t = this.icons[2];
/*  72: 70 */       } else if (t == this.icons[2]) {
/*  73: 71 */         t = this.icons[1];
/*  74:    */       }
/*  75:    */     }
/*  76: 73 */     return t;
/*  77:    */   }
/*  78:    */   
/*  79:    */   @SideOnly(Side.CLIENT)
/*  80:    */   public IIcon prevIcon(int par1, int par2)
/*  81:    */   {
/*  82: 78 */     if (par1 <= 1) {
/*  83: 79 */       return this.icons[0];
/*  84:    */     }
/*  85: 82 */     if (par2 % 2 == 0)
/*  86:    */     {
/*  87: 83 */       if (par1 <= 3) {
/*  88: 84 */         return this.icons[0];
/*  89:    */       }
/*  90: 86 */       if ((par1 == 4) && (par2 == 0)) {
/*  91: 87 */         return this.icons[2];
/*  92:    */       }
/*  93: 88 */       if ((par1 == 5) && (par2 == 2)) {
/*  94: 89 */         return this.icons[2];
/*  95:    */       }
/*  96:    */     }
/*  97: 93 */     if (par2 % 2 == 1)
/*  98:    */     {
/*  99: 94 */       if (par1 > 3) {
/* 100: 95 */         return this.icons[0];
/* 101:    */       }
/* 102: 97 */       if ((par1 == 2) && (par2 == 1)) {
/* 103: 98 */         return this.icons[2];
/* 104:    */       }
/* 105: 99 */       if ((par1 == 3) && (par2 == 3)) {
/* 106:100 */         return this.icons[2];
/* 107:    */       }
/* 108:    */     }
/* 109:104 */     return this.icons[1];
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean shouldFlip(int side)
/* 113:    */   {
/* 114:108 */     return (side == 3) || (side == 2);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
/* 118:    */   {
/* 119:113 */     int var6 = ((MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
/* 120:114 */     par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void onEntityCollidedWithBlock(World par1World, int x, int y, int z, Entity par5Entity)
/* 124:    */   {
/* 125:119 */     double m_speed = 0.05D;
/* 126:120 */     int a = par1World.getBlockMetadata(x, y, z);
/* 127:121 */     int[] ax = { 0, 1, 0, -1 };
/* 128:122 */     int[] az = { -1, 0, 1, 0 };
/* 129:124 */     if (a > 3) {
/* 130:125 */       return;
/* 131:    */     }
/* 132:127 */     if ((par5Entity != null) && (par5Entity.posY > y + 0.5D) && (!par5Entity.isSneaking()))
/* 133:    */     {
/* 134:128 */       if ((par5Entity instanceof EntityItem))
/* 135:    */       {
/* 136:130 */         ItemStack my_item = null;
/* 137:    */         
/* 138:132 */         my_item = ((EntityItem)par5Entity).getEntityItem();
/* 139:134 */         if (my_item != null) {
/* 140:135 */           for (int j = 0; j < 4; j++) {
/* 141:136 */             if ((a % 2 != j % 2) && 
/* 142:137 */               ((par1World.getTileEntity(x + ax[(j % 4)], y - 1, z + az[(j % 4)]) instanceof IInventory)))
/* 143:    */             {
/* 144:138 */               IInventory chest = (IInventory)par1World.getTileEntity(x + ax[(j % 4)], y - 1, z + az[(j % 4)]);
/* 145:139 */               boolean hasItem = false;
/* 146:140 */               boolean hasSpace = false;
/* 147:142 */               for (int i = 0; i < chest.getSizeInventory(); i++)
/* 148:    */               {
/* 149:143 */                 ItemStack ch_item = chest.getStackInSlot(i);
/* 150:145 */                 if (ch_item != null)
/* 151:    */                 {
/* 152:146 */                   if ((ch_item.getItem() == my_item.getItem()) && (
/* 153:147 */                     (ch_item.getItem().isDamageable()) || (ch_item.getItemDamage() == my_item.getItemDamage())))
/* 154:    */                   {
/* 155:148 */                     hasItem = true;
/* 156:150 */                     if ((ch_item.stackSize < ch_item.getItem().getItemStackLimit(ch_item)) && (ch_item.stackSize < chest.getInventoryStackLimit())) {
/* 157:151 */                       hasSpace = true;
/* 158:    */                     }
/* 159:    */                   }
/* 160:    */                 }
/* 161:    */                 else {
/* 162:156 */                   hasSpace = true;
/* 163:    */                 }
/* 164:159 */                 if ((hasItem) && (hasSpace))
/* 165:    */                 {
/* 166:160 */                   a = j % 4;
/* 167:161 */                   par5Entity.isAirBorne = true;
/* 168:162 */                   break;
/* 169:    */                 }
/* 170:    */               }
/* 171:    */             }
/* 172:    */           }
/* 173:    */         }
/* 174:    */       }
/* 175:171 */       if ((ax[a] == 0) && (Math.abs(x + 0.5D - par5Entity.posX) < 0.5D) && (Math.abs(x + 0.5D - par5Entity.posX) > 0.1D)) {
/* 176:172 */         par5Entity.motionX += Math.signum(x + 0.5D - par5Entity.posX) * Math.min(m_speed, Math.abs(x + 0.5D - par5Entity.posX)) / 1.2D;
/* 177:    */       }
/* 178:175 */       if ((az[a] == 0) && (Math.abs(z + 0.5D - par5Entity.posZ) < 0.5D) && (Math.abs(z + 0.5D - par5Entity.posZ) > 0.1D)) {
/* 179:176 */         par5Entity.motionZ += Math.signum(z + 0.5D - par5Entity.posZ) * Math.min(m_speed, Math.abs(z + 0.5D - par5Entity.posZ)) / 1.2D;
/* 180:    */       }
/* 181:180 */       if ((par5Entity instanceof EntityItem))
/* 182:    */       {
/* 183:181 */         double jump_vel = 0.19D;
/* 184:182 */         double jump_point = 0.0D;
/* 185:183 */         boolean jump = (par1World.isAirBlock(x, y + 2, z)) && ((par1World.getBlock(x + ax[a], y + 1, z + az[a]) == this) || (par1World.getBlock(x + ax[a], y + 1, z + az[a]) == Blocks.hopper));
/* 186:186 */         if ((!jump) && 
/* 187:187 */           (!par1World.isAirBlock(x + ax[a], y, z + az[a])) && (par1World.getBlock(x + ax[a], y, z + az[a]) != this) && (!par1World.isAirBlock(x + ax[a], y + 1, z)))
/* 188:    */         {
/* 189:188 */           jump = true;
/* 190:189 */           jump_vel = 0.07000000000000001D;
/* 191:190 */           jump_point = 0.3D;
/* 192:    */         }
/* 193:193 */         if (jump)
/* 194:    */         {
/* 195:194 */           double progress = (par5Entity.posX - x - 0.5D) * ax[a] + (par5Entity.posZ - z - 0.5D) * az[a];
/* 196:195 */           double prog_speed = par5Entity.motionX * ax[a] + par5Entity.motionZ * az[a];
/* 197:196 */           double prog_counterspeed = Math.abs(par5Entity.motionX * az[a] + par5Entity.motionZ * ax[a]);
/* 198:198 */           if ((progress > jump_point) || ((progress > jump_point - 0.2D) && (prog_speed < 0.0D)))
/* 199:    */           {
/* 200:199 */             a = (a + 2) % 4;
/* 201:    */           }
/* 202:200 */           else if ((progress + 1.5D * prog_speed > jump_point) && (prog_speed >= m_speed) && (prog_counterspeed < 0.2D))
/* 203:    */           {
/* 204:201 */             par5Entity.onGround = false;
/* 205:202 */             par5Entity.isAirBorne = true;
/* 206:204 */             if (ax[a] == 0) {
/* 207:205 */               par5Entity.motionX = 0.0D;
/* 208:    */             }
/* 209:208 */             if (az[a] == 0) {
/* 210:209 */               par5Entity.motionZ = 0.0D;
/* 211:    */             }
/* 212:215 */             par5Entity.addVelocity(0.0D, jump_vel * 2.0D, 0.0D);
/* 213:216 */             return;
/* 214:    */           }
/* 215:    */         }
/* 216:    */       }
/* 217:223 */       par5Entity.motionX += ax[a] * m_speed;
/* 218:    */       
/* 219:    */ 
/* 220:226 */       par5Entity.motionZ += az[a] * m_speed;
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
/* 225:    */   {
/* 226:232 */     float var5 = 0.0625F;
/* 227:233 */     return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1 - var5, par4 + 1);
/* 228:    */   }
/* 229:    */   
/* 230:    */   @SideOnly(Side.CLIENT)
/* 231:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
/* 232:    */   {
/* 233:239 */     return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1, par4 + 1);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
/* 237:    */   {
/* 238:244 */     return true;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void prepareForRender(String label) {}
/* 242:    */   
/* 243:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/* 244:    */   {
/* 245:258 */     return getModel(world.getBlockMetadata(x, y, z));
/* 246:    */   }
/* 247:    */   
/* 248:    */   public BoxModel getInventoryModel(int metadata)
/* 249:    */   {
/* 250:263 */     return getModel(1);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public BoxModel getModel(int metadata)
/* 254:    */   {
/* 255:267 */     Box main = new Box(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 256:268 */     main.renderAsNormalBlock = true;
/* 257:269 */     main.uvRotateBottom = (main.uvRotateTop = metadata % 2);
/* 258:270 */     return new BoxModel(main);
/* 259:    */   }
/* 260:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockConveyor
 * JD-Core Version:    0.7.0.1
 */