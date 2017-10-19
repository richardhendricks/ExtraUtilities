/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   6:    */ import com.rwtema.extrautils.item.ItemBlockDrum;
/*   7:    */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*   8:    */ import com.rwtema.extrautils.tileentity.TileEntityDrum;
/*   9:    */ import cpw.mods.fml.relauncher.Side;
/*  10:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Locale;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Random;
/*  16:    */ import java.util.Set;
/*  17:    */ import net.minecraft.block.Block;
/*  18:    */ import net.minecraft.block.material.Material;
/*  19:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  20:    */ import net.minecraft.creativetab.CreativeTabs;
/*  21:    */ import net.minecraft.entity.EntityLivingBase;
/*  22:    */ import net.minecraft.entity.player.EntityPlayer;
/*  23:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  24:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  25:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  26:    */ import net.minecraft.init.Items;
/*  27:    */ import net.minecraft.item.Item;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.server.MinecraftServer;
/*  30:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  31:    */ import net.minecraft.tileentity.TileEntity;
/*  32:    */ import net.minecraft.util.IIcon;
/*  33:    */ import net.minecraft.util.MovingObjectPosition;
/*  34:    */ import net.minecraft.world.IBlockAccess;
/*  35:    */ import net.minecraft.world.World;
/*  36:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  37:    */ import net.minecraftforge.fluids.Fluid;
/*  38:    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*  39:    */ import net.minecraftforge.fluids.FluidRegistry;
/*  40:    */ import net.minecraftforge.fluids.FluidStack;
/*  41:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  42:    */ 
/*  43:    */ public class BlockDrum
/*  44:    */   extends Block
/*  45:    */ {
/*  46:    */   public static IIcon drum_side;
/*  47:    */   public static IIcon drum_top;
/*  48:    */   public static IIcon drum_side2;
/*  49:    */   private static IIcon drum_top2;
/*  50:    */   
/*  51:    */   public BlockDrum()
/*  52:    */   {
/*  53: 42 */     super(Material.rock);
/*  54: 43 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  55: 44 */     setHardness(1.5F);
/*  56: 45 */     setBlockName("extrautils:drum");
/*  57: 46 */     setBlockBounds(0.07499999F, 0.0F, 0.07499999F, 0.925F, 1.0F, 0.925F);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean hasComparatorInputOverride()
/*  61:    */   {
/*  62: 51 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getComparatorInputOverride(World world, int x, int y, int z, int side)
/*  66:    */   {
/*  67: 56 */     if ((world.getTileEntity(x, y, z) instanceof TileEntityDrum))
/*  68:    */     {
/*  69: 57 */       TileEntityDrum drum = (TileEntityDrum)world.getTileEntity(x, y, z);
/*  70:    */       
/*  71:    */ 
/*  72: 60 */       FluidTankInfo tank = drum.getTankInfo(ForgeDirection.UP)[0];
/*  73: 61 */       if ((tank == null) || (tank.fluid == null) || (tank.fluid.amount == 0)) {
/*  74: 62 */         return 0;
/*  75:    */       }
/*  76: 64 */       double t = tank.fluid.amount * 14.0D / tank.capacity;
/*  77: 65 */       if (t < 0.0D) {
/*  78: 65 */         t = 0.0D;
/*  79:    */       }
/*  80: 66 */       if (t > 15.0D) {
/*  81: 66 */         t = 14.0D;
/*  82:    */       }
/*  83: 68 */       return (int)Math.floor(t) + 1;
/*  84:    */     }
/*  85: 71 */     return 0;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
/*  89:    */   {
/*  90: 76 */     if (!par1World.isClient)
/*  91:    */     {
/*  92: 77 */       if ((par1World.getTileEntity(par2, par3, par4) instanceof TileEntityDrum))
/*  93:    */       {
/*  94: 78 */         TileEntityDrum drum = (TileEntityDrum)par1World.getTileEntity(par2, par3, par4);
/*  95: 79 */         FluidTankInfo tank = drum.getTankInfo(ForgeDirection.UP)[0];
/*  96: 80 */         ItemStack item = par5EntityPlayer.getCurrentEquippedItem();
/*  97: 82 */         if (item != null)
/*  98:    */         {
/*  99: 83 */           if ((item.getItem() == Items.stick) || ((par5EntityPlayer.isSneaking()) && (XUHelper.isWrench(item))))
/* 100:    */           {
/* 101: 84 */             dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
/* 102: 85 */             par1World.setBlockToAir(par2, par3, par4);
/* 103: 86 */             return true;
/* 104:    */           }
/* 105: 87 */           if (FluidContainerRegistry.isEmptyContainer(item))
/* 106:    */           {
/* 107: 88 */             ItemStack filled = FluidContainerRegistry.fillFluidContainer(tank.fluid, item);
/* 108: 90 */             if (filled != null)
/* 109:    */             {
/* 110: 91 */               int a = FluidContainerRegistry.getFluidForFilledItem(filled).amount;
/* 111: 93 */               if (par5EntityPlayer.capabilities.isCreativeMode)
/* 112:    */               {
/* 113: 94 */                 drum.drain(ForgeDirection.DOWN, a, true);
/* 114:    */               }
/* 115: 95 */               else if (item.stackSize == 1)
/* 116:    */               {
/* 117: 96 */                 par5EntityPlayer.setCurrentItemOrArmor(0, filled);
/* 118: 97 */                 drum.drain(ForgeDirection.DOWN, a, true);
/* 119:    */               }
/* 120: 99 */               else if (par5EntityPlayer.inventory.addItemStackToInventory(filled))
/* 121:    */               {
/* 122:100 */                 item.stackSize -= 1;
/* 123:101 */                 drum.drain(ForgeDirection.DOWN, a, true);
/* 124:103 */                 if ((par5EntityPlayer instanceof EntityPlayerMP)) {
/* 125:104 */                   ((EntityPlayerMP)par5EntityPlayer).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP)par5EntityPlayer);
/* 126:    */                 }
/* 127:    */               }
/* 128:109 */               if (drum.getTankInfo(ForgeDirection.DOWN)[0].fluid == null) {
/* 129:110 */                 par1World.markBlockForUpdate(par2, par3, par4);
/* 130:    */               }
/* 131:113 */               return true;
/* 132:    */             }
/* 133:    */           }
/* 134:115 */           else if (FluidContainerRegistry.isFilledContainer(item))
/* 135:    */           {
/* 136:116 */             FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
/* 137:118 */             if (drum.fill(ForgeDirection.UP, fluid, false) == fluid.amount)
/* 138:    */             {
/* 139:119 */               if (par5EntityPlayer.capabilities.isCreativeMode)
/* 140:    */               {
/* 141:120 */                 drum.fill(ForgeDirection.UP, fluid, true);
/* 142:    */               }
/* 143:    */               else
/* 144:    */               {
/* 145:122 */                 ItemStack c = null;
/* 146:124 */                 if (item.getItem().hasContainerItem(item)) {
/* 147:125 */                   c = item.getItem().getContainerItem(item);
/* 148:    */                 }
/* 149:128 */                 if ((c == null) || (item.stackSize == 1) || (par5EntityPlayer.inventory.addItemStackToInventory(c)))
/* 150:    */                 {
/* 151:129 */                   drum.fill(ForgeDirection.UP, fluid, true);
/* 152:131 */                   if (item.stackSize == 1) {
/* 153:132 */                     par5EntityPlayer.setCurrentItemOrArmor(0, c);
/* 154:133 */                   } else if (item.stackSize > 1) {
/* 155:134 */                     item.stackSize -= 1;
/* 156:    */                   }
/* 157:    */                 }
/* 158:    */               }
/* 159:139 */               return true;
/* 160:    */             }
/* 161:    */           }
/* 162:    */         }
/* 163:146 */         FluidStack fluid = tank.fluid;
/* 164:    */         String s;
/* 165:    */         String s;
/* 166:148 */         if (fluid != null) {
/* 167:149 */           s = XUHelper.getFluidName(fluid) + ": " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(fluid.amount) }) + " / " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(tank.capacity) });
/* 168:    */         } else {
/* 169:151 */           s = "Empty: 0 / " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(tank.capacity) });
/* 170:    */         }
/* 171:154 */         PacketTempChat.sendChat(par5EntityPlayer, s);
/* 172:155 */         return true;
/* 173:    */       }
/* 174:158 */       return false;
/* 175:    */     }
/* 176:160 */     return true;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean renderAsNormalBlock()
/* 180:    */   {
/* 181:166 */     return false;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean isOpaqueCube()
/* 185:    */   {
/* 186:171 */     return false;
/* 187:    */   }
/* 188:    */   
/* 189:    */   @SideOnly(Side.CLIENT)
/* 190:    */   public IIcon getIcon(int par1, int par2)
/* 191:    */   {
/* 192:177 */     return par2 == 1 ? drum_side2 : par1 <= 1 ? drum_top : par2 == 1 ? drum_top2 : drum_side;
/* 193:    */   }
/* 194:    */   
/* 195:    */   @SideOnly(Side.CLIENT)
/* 196:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 197:    */   {
/* 198:183 */     drum_side = par1IIconRegister.registerIcon("extrautils:drum_side");
/* 199:184 */     drum_side2 = par1IIconRegister.registerIcon("extrautils:drum_side2");
/* 200:185 */     drum_top = par1IIconRegister.registerIcon("extrautils:drum_top");
/* 201:186 */     drum_top2 = par1IIconRegister.registerIcon("extrautils:drum_top2");
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int getRenderType()
/* 205:    */   {
/* 206:191 */     return ExtraUtilsProxy.drumRendererID;
/* 207:    */   }
/* 208:    */   
/* 209:    */   @SideOnly(Side.CLIENT)
/* 210:    */   public int colorMultiplier(IBlockAccess world, int x, int y, int z)
/* 211:    */   {
/* 212:197 */     if ((world.getTileEntity(x, y, z) instanceof TileEntityDrum)) {
/* 213:198 */       return ((TileEntityDrum)world.getTileEntity(x, y, z)).getColor();
/* 214:    */     }
/* 215:201 */     return 16777215;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean hasTileEntity(int metadata)
/* 219:    */   {
/* 220:206 */     return true;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public TileEntity createTileEntity(World world, int metadata)
/* 224:    */   {
/* 225:211 */     return new TileEntityDrum(metadata);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
/* 229:    */   {
/* 230:217 */     if ((!player.capabilities.isCreativeMode) && (canHarvestBlock(player, world.getBlockMetadata(x, y, z))))
/* 231:    */     {
/* 232:218 */       ArrayList<ItemStack> items = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
/* 233:220 */       if (world.setBlockToAir(x, y, z))
/* 234:    */       {
/* 235:221 */         if (!world.isClient) {
/* 236:222 */           for (ItemStack item : items) {
/* 237:224 */             dropBlockAsItem_do(world, x, y, z, item);
/* 238:    */           }
/* 239:    */         }
/* 240:228 */         return true;
/* 241:    */       }
/* 242:230 */       return false;
/* 243:    */     }
/* 244:233 */     return super.removedByPlayer(world, player, x, y, z, willHarvest);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
/* 248:    */   {
/* 249:239 */     par2EntityPlayer.addStat(net.minecraft.stats.StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
/* 250:240 */     par2EntityPlayer.addExhaustion(0.025F);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
/* 254:    */   {
/* 255:245 */     if ((par6ItemStack.hasTagCompound()) && 
/* 256:246 */       ((par6ItemStack.getItem() instanceof ItemBlockDrum)))
/* 257:    */     {
/* 258:247 */       TileEntity drum = par1World.getTileEntity(par2, par3, par4);
/* 259:249 */       if ((drum != null) && ((drum instanceof TileEntityDrum)))
/* 260:    */       {
/* 261:250 */         FluidStack fluid = ((ItemBlockDrum)par6ItemStack.getItem()).drain(par6ItemStack, 2147483647, false);
/* 262:    */         
/* 263:252 */         ((TileEntityDrum)drum).setCapacityFromMetadata(par6ItemStack.getItemDamage());
/* 264:254 */         if (fluid != null) {
/* 265:255 */           ((TileEntityDrum)drum).fill(ForgeDirection.UP, fluid, true);
/* 266:    */         }
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   @SideOnly(Side.CLIENT)
/* 272:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 273:    */   {
/* 274:270 */     par3List.add(new ItemStack(par1, 1, 0));
/* 275:271 */     par3List.add(new ItemStack(par1, 1, 1));
/* 276:273 */     if (par2CreativeTabs != null) {
/* 277:274 */       return;
/* 278:    */     }
/* 279:276 */     FluidRegistry.getRegisteredFluidIDs().keySet().iterator();
/* 280:278 */     for (Fluid fluid1 : FluidRegistry.getRegisteredFluids().values())
/* 281:    */     {
/* 282:279 */       ItemStack drum = new ItemStack(par1, 1, 0);ItemStack drum2 = new ItemStack(par1, 1, 1);
/* 283:280 */       FluidStack fluid = FluidRegistry.getFluidStack(fluid1.getName(), TileEntityDrum.getCapacityFromMetadata(1));
/* 284:282 */       if (fluid != null)
/* 285:    */       {
/* 286:283 */         ((ItemBlockDrum)par1).fill(drum, fluid, true);
/* 287:284 */         par3List.add(drum);
/* 288:    */         
/* 289:286 */         ((ItemBlockDrum)par1).fill(drum2, fluid, true);
/* 290:287 */         par3List.add(drum2);
/* 291:    */       }
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
/* 296:    */   {
/* 297:295 */     return (ItemStack)getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0).get(0);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public int damageDropped(int meta)
/* 301:    */   {
/* 302:300 */     return meta;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
/* 306:    */   {
/* 307:305 */     ArrayList<ItemStack> ret = new ArrayList();
/* 308:306 */     ItemStack item = new ItemStack(this, 1, damageDropped(metadata));
/* 309:308 */     if (((world.getTileEntity(x, y, z) instanceof TileEntityDrum)) && ((item.getItem() instanceof ItemBlockDrum)))
/* 310:    */     {
/* 311:309 */       FluidStack fluid = ((TileEntityDrum)world.getTileEntity(x, y, z)).getTankInfo(ForgeDirection.UP)[0].fluid;
/* 312:311 */       if (fluid != null) {
/* 313:312 */         ((ItemBlockDrum)item.getItem()).fill(item, fluid, true);
/* 314:    */       }
/* 315:    */     }
/* 316:316 */     ret.add(item);
/* 317:317 */     return ret;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
/* 321:    */   {
/* 322:325 */     if (!par1World.isClient)
/* 323:    */     {
/* 324:326 */       TileEntity drum = par1World.getTileEntity(par2, par3, par4);
/* 325:328 */       if ((drum instanceof TileEntityDrum)) {
/* 326:329 */         ((TileEntityDrum)drum).ticked();
/* 327:    */       }
/* 328:    */     }
/* 329:    */   }
/* 330:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockDrum
 * JD-Core Version:    0.7.0.1
 */