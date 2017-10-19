/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   5:    */ import com.rwtema.extrautils.item.IBlockLocalization;
/*   6:    */ import com.rwtema.extrautils.particle.ParticleHelperClient;
/*   7:    */ import com.rwtema.extrautils.particle.ParticlePortal;
/*   8:    */ import com.rwtema.extrautils.tileentity.TileEntityPortal;
/*   9:    */ import com.rwtema.extrautils.worldgen.Underdark.TeleporterUnderdark;
/*  10:    */ import com.rwtema.extrautils.worldgen.endoftime.TeleporterEndOfTime;
/*  11:    */ import cpw.mods.fml.relauncher.Side;
/*  12:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Random;
/*  15:    */ import net.minecraft.block.Block;
/*  16:    */ import net.minecraft.block.material.Material;
/*  17:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  18:    */ import net.minecraft.creativetab.CreativeTabs;
/*  19:    */ import net.minecraft.entity.Entity;
/*  20:    */ import net.minecraft.entity.player.EntityPlayer;
/*  21:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  22:    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*  23:    */ import net.minecraft.init.Blocks;
/*  24:    */ import net.minecraft.item.Item;
/*  25:    */ import net.minecraft.item.ItemStack;
/*  26:    */ import net.minecraft.server.MinecraftServer;
/*  27:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  28:    */ import net.minecraft.tileentity.TileEntity;
/*  29:    */ import net.minecraft.util.IIcon;
/*  30:    */ import net.minecraft.util.MathHelper;
/*  31:    */ import net.minecraft.world.Explosion;
/*  32:    */ import net.minecraft.world.IBlockAccess;
/*  33:    */ import net.minecraft.world.World;
/*  34:    */ 
/*  35:    */ public class BlockPortal
/*  36:    */   extends Block
/*  37:    */   implements IBlockLocalization
/*  38:    */ {
/*  39:    */   public static IIcon particle;
/*  40:    */   private IIcon lightPortal;
/*  41:    */   public static ItemStack darkPortalItemStack;
/*  42:    */   public static ItemStack lightPortalItemStack;
/*  43:    */   
/*  44:    */   public BlockPortal()
/*  45:    */   {
/*  46: 41 */     super(Material.rock);
/*  47: 42 */     setBlockTextureName("extrautils:dark_portal");
/*  48: 43 */     setBlockName("extrautils:dark_portal");
/*  49: 44 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  50: 45 */     setHardness(2.0F);
/*  51: 46 */     darkPortalItemStack = new ItemStack(this, 1, 0);
/*  52: 47 */     lightPortalItemStack = new ItemStack(this, 1, 2);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  56:    */   {
/*  57: 53 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/*  58: 54 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
/*  59:    */   }
/*  60:    */   
/*  61:    */   @SideOnly(Side.CLIENT)
/*  62:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  63:    */   {
/*  64: 60 */     if (p_149691_2_ >> 1 == 1) {
/*  65: 61 */       return this.lightPortal;
/*  66:    */     }
/*  67: 62 */     return super.getIcon(p_149691_1_, p_149691_2_);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getLightValue(IBlockAccess world, int x, int y, int z)
/*  71:    */   {
/*  72: 67 */     return world.getBlockMetadata(x, y, z) >> 1 == 0 ? 15 : 0;
/*  73:    */   }
/*  74:    */   
/*  75:    */   @SideOnly(Side.CLIENT)
/*  76:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  77:    */   {
/*  78: 73 */     super.registerBlockIcons(par1IIconRegister);
/*  79: 74 */     this.lightPortal = par1IIconRegister.registerIcon("extrautils:light_portal");
/*  80: 75 */     particle = par1IIconRegister.registerIcon("extrautils:particle_blue");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
/*  84:    */   {
/*  85: 80 */     par1World.setBlock(par2, par3 + 1, par4, Blocks.torch);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public float getBlockHardness(World world, int x, int y, int z)
/*  89:    */   {
/*  90: 85 */     return (world.getBlockMetadata(x, y, z) & 0x1) == 1 ? -1.0F : 2.0F;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
/*  94:    */   {
/*  95: 90 */     return (world.getBlockMetadata(x, y, z) & 0x1) != 1;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
/*  99:    */   {
/* 100: 95 */     return (world.getBlockMetadata(x, y, z) & 0x1) == 1 ? 1.0E+020F : super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean canDropFromExplosion(Explosion par1Explosion)
/* 104:    */   {
/* 105:100 */     return (par1Explosion == null) || (!(par1Explosion.exploder instanceof EntityWitherSkull));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
/* 109:    */   {
/* 110:105 */     if ((!world.isClient) && (canDropFromExplosion(explosion)) && ((world.getBlockMetadata(x, y, z) & 0x1) == 0)) {
/* 111:106 */       super.onBlockExploded(world, x, y, z, explosion);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entity, int par6, float par7, float par8, float par9)
/* 116:    */   {
/* 117:112 */     return (world.isClient) || (transferPlayer(world, x, y, z, entity));
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean transferPlayer(World world, int x, int y, int z, Entity entity)
/* 121:    */   {
/* 122:116 */     if ((entity.ridingEntity == null) && (entity.riddenByEntity == null) && ((entity instanceof EntityPlayerMP)))
/* 123:    */     {
/* 124:117 */       EntityPlayerMP thePlayer = (EntityPlayerMP)entity;
/* 125:119 */       if (XUHelper.isPlayerFake(thePlayer)) {
/* 126:120 */         return false;
/* 127:    */       }
/* 128:122 */       int type = world.getBlockMetadata(x, y, z) >> 1;
/* 129:124 */       if (type == 0)
/* 130:    */       {
/* 131:125 */         if (ExtraUtils.underdarkDimID == 0) {
/* 132:125 */           return false;
/* 133:    */         }
/* 134:127 */         if (thePlayer.dimension != ExtraUtils.underdarkDimID)
/* 135:    */         {
/* 136:128 */           thePlayer.setLocationAndAngles(x + 0.5D, thePlayer.posY, z + 0.5D, thePlayer.rotationYaw, thePlayer.rotationPitch);
/* 137:129 */           thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, ExtraUtils.underdarkDimID, new TeleporterUnderdark(thePlayer.mcServer.worldServerForDimension(ExtraUtils.underdarkDimID)));
/* 138:    */         }
/* 139:    */         else
/* 140:    */         {
/* 141:132 */           thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, 0, new TeleporterUnderdark(thePlayer.mcServer.worldServerForDimension(0)));
/* 142:    */         }
/* 143:135 */         return true;
/* 144:    */       }
/* 145:136 */       if (type == 1)
/* 146:    */       {
/* 147:137 */         if (ExtraUtils.endoftimeDimID == 0) {
/* 148:137 */           return false;
/* 149:    */         }
/* 150:139 */         if (thePlayer.dimension != ExtraUtils.endoftimeDimID)
/* 151:    */         {
/* 152:140 */           thePlayer.setLocationAndAngles(x + 0.5D, thePlayer.posY, z + 0.5D, thePlayer.rotationYaw, thePlayer.rotationPitch);
/* 153:141 */           thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, ExtraUtils.endoftimeDimID, new TeleporterEndOfTime(thePlayer.mcServer.worldServerForDimension(ExtraUtils.endoftimeDimID)));
/* 154:    */         }
/* 155:    */         else
/* 156:    */         {
/* 157:143 */           thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, 0, new TeleporterEndOfTime(thePlayer.mcServer.worldServerForDimension(0)));
/* 158:    */         }
/* 159:146 */         return true;
/* 160:    */       }
/* 161:    */     }
/* 162:150 */     return false;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean hasTileEntity(int metadata)
/* 166:    */   {
/* 167:155 */     return true;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public TileEntity createTileEntity(World world, int metadata)
/* 171:    */   {
/* 172:160 */     return new TileEntityPortal();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 176:    */   {
/* 177:165 */     int type = par1ItemStack.getItemDamage() >> 1;
/* 178:166 */     return getUnlocalizedName() + (type == 0 ? "" : new StringBuilder().append(".").append(type).toString());
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int damageDropped(int meta)
/* 182:    */   {
/* 183:171 */     return meta & 0xE;
/* 184:    */   }
/* 185:    */   
/* 186:    */   @SideOnly(Side.CLIENT)
/* 187:    */   public void randomDisplayTick(World world, int x, int y, int z, Random r)
/* 188:    */   {
/* 189:177 */     if (world.getBlockMetadata(x, y, z) >> 1 == 0) {
/* 190:178 */       return;
/* 191:    */     }
/* 192:180 */     double dx = MathHelper.clamp_double(0.5D + 0.2D * r.nextGaussian(), 0.0D, 1.0D);
/* 193:181 */     double dz = MathHelper.clamp_double(0.5D + 0.2D * r.nextGaussian(), 0.0D, 1.0D);
/* 194:182 */     ParticleHelperClient.addParticle(new ParticlePortal(world, x + dx, y + 1, z + dz, 1.0F, 1.0F, 1.0F));
/* 195:    */   }
/* 196:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockPortal
 * JD-Core Version:    0.7.0.1
 */