/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.block.Box;
/*   5:    */ import com.rwtema.extrautils.block.BoxModel;
/*   6:    */ import com.rwtema.extrautils.item.ItemNodeUpgrade;
/*   7:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.FluidBuffer;
/*   8:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*   9:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeLiquid;
/*  10:    */ import java.util.Random;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  16:    */ import net.minecraftforge.fluids.Fluid;
/*  17:    */ import net.minecraftforge.fluids.FluidRegistry;
/*  18:    */ import net.minecraftforge.fluids.FluidStack;
/*  19:    */ import net.minecraftforge.fluids.FluidTank;
/*  20:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  21:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  22:    */ 
/*  23:    */ public class TileEntityTransferNodeLiquid
/*  24:    */   extends TileEntityTransferNode
/*  25:    */   implements INodeLiquid
/*  26:    */ {
/*  27: 17 */   public boolean nearSource = false;
/*  28: 18 */   public long checkTimer = 0L;
/*  29:    */   
/*  30:    */   public TileEntityTransferNodeLiquid()
/*  31:    */   {
/*  32: 28 */     super("Liquid", new FluidBuffer());this.pr = 0.001F;this.pg = 0.0F;this.pb = 1.0F;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public TileEntityTransferNodeLiquid(String s, INodeBuffer buffer)
/*  36:    */   {
/*  37: 32 */     super(s, buffer);this.pr = 0.001F;this.pg = 0.0F;this.pb = 1.0F;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void processBuffer()
/*  41:    */   {
/*  42: 37 */     if ((this.worldObj != null) && (!this.worldObj.isClient))
/*  43:    */     {
/*  44: 38 */       if (this.coolDown > 0) {
/*  45: 39 */         this.coolDown -= this.stepCoolDown;
/*  46:    */       }
/*  47: 42 */       if (checkRedstone()) {
/*  48: 43 */         return;
/*  49:    */       }
/*  50: 46 */       while (this.coolDown <= 0)
/*  51:    */       {
/*  52: 47 */         this.coolDown += baseMaxCoolDown;
/*  53: 49 */         if (handleInventories()) {
/*  54: 50 */           advPipeSearch();
/*  55:    */         }
/*  56: 53 */         loadTank();
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void loadTank()
/*  62:    */   {
/*  63: 59 */     int dir = getBlockMetadata() % 6;
/*  64: 62 */     if ((this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir]) instanceof IFluidHandler))
/*  65:    */     {
/*  66: 63 */       IFluidHandler source = (IFluidHandler)this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir]);
/*  67:    */       
/*  68: 65 */       FluidStack liquid = source.drain(ForgeDirection.getOrientation(dir).getOpposite(), upgradeNo(3) == 0 ? 200 : ((FluidTank)this.buffer.getBuffer()).getCapacity(), false);
/*  69: 66 */       int k = fill(getNodeDir(), liquid, false);
/*  70: 68 */       if (k > 0) {
/*  71: 69 */         fill(getNodeDir(), source.drain(ForgeDirection.getOrientation(dir).getOpposite(), k, true), true);
/*  72:    */       }
/*  73:    */     }
/*  74: 71 */     else if ((!ExtraUtils.disableInfiniteWater) && (upgradeNo(2) > 0))
/*  75:    */     {
/*  76: 72 */       if (this.worldObj.getTotalWorldTime() - this.checkTimer > 20L)
/*  77:    */       {
/*  78: 73 */         this.checkTimer = this.worldObj.getTotalWorldTime();
/*  79: 74 */         this.nearSource = false;
/*  80: 76 */         if (isWaterSource(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir]))
/*  81:    */         {
/*  82: 77 */           int n = 0;
/*  83: 79 */           for (int i = 2; i < 6; i++) {
/*  84: 80 */             if (isWaterSource(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir] + net.minecraft.util.Facing.offsetsXForSide[i], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir] + net.minecraft.util.Facing.offsetsZForSide[i])) {
/*  85: 82 */               n++;
/*  86:    */             }
/*  87:    */           }
/*  88: 86 */           if (n >= 2) {
/*  89: 87 */             this.nearSource = true;
/*  90:    */           }
/*  91:    */         }
/*  92:    */       }
/*  93: 92 */       if (this.nearSource)
/*  94:    */       {
/*  95: 93 */         long t = this.worldObj.getTotalWorldTime() / TileEntityTransferNode.baseMaxCoolDown * TileEntityTransferNode.baseMaxCoolDown;
/*  96: 94 */         int a = 1000 * TileEntityTransferNode.baseMaxCoolDown / (20 * this.stepCoolDown);
/*  97: 95 */         float b = 1000.0F * TileEntityTransferNode.baseMaxCoolDown / (20 * this.stepCoolDown);
/*  98: 97 */         if ((a != b) && (b - a > this.worldObj.rand.nextFloat())) {
/*  99: 98 */           a++;
/* 100:    */         }
/* 101:101 */         if (a > 0) {
/* 102:102 */           fill(getNodeDir(), new FluidStack(FluidRegistry.WATER, a * (1 + upgradeNo(2))), true);
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isWaterSource(int x, int y, int z)
/* 109:    */   {
/* 110:109 */     Block id = this.worldObj.getBlock(x, y, z);
/* 111:110 */     return ((id == Blocks.water) || (id == Blocks.flowing_water)) && (this.worldObj.getBlockMetadata(x, y, z) == 0);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
/* 115:    */   {
/* 116:116 */     if (from == getNodeDir())
/* 117:    */     {
/* 118:117 */       if (resource == null) {
/* 119:118 */         return 0;
/* 120:    */       }
/* 121:121 */       for (int j = 0; j < this.upgrades.getSizeInventory(); j++) {
/* 122:122 */         if ((this.upgrades.getStackInSlot(j) != null) && (this.upgrades.getStackInSlot(j).getItemDamage() == 1) && (this.upgrades.getStackInSlot(j).getTagCompound() != null) && 
/* 123:123 */           (!ItemNodeUpgrade.matchesFilterLiquid(resource, this.upgrades.getStackInSlot(j)))) {
/* 124:124 */           return 0;
/* 125:    */         }
/* 126:    */       }
/* 127:129 */       return ((FluidTank)this.buffer.getBuffer()).fill(resource, doFill);
/* 128:    */     }
/* 129:131 */     return 0;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
/* 133:    */   {
/* 134:137 */     return null;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
/* 138:    */   {
/* 139:142 */     return null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean canFill(ForgeDirection from, Fluid fluid)
/* 143:    */   {
/* 144:147 */     return from.ordinal() == getBlockMetadata() % 6;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean canDrain(ForgeDirection from, Fluid fluid)
/* 148:    */   {
/* 149:152 */     return false;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public FluidTankInfo[] getTankInfo(ForgeDirection from)
/* 153:    */   {
/* 154:157 */     return new FluidTankInfo[] { ((FluidTank)this.buffer.getBuffer()).getInfo() };
/* 155:    */   }
/* 156:    */   
/* 157:    */   public TileEntityTransferNodeLiquid getNode()
/* 158:    */   {
/* 159:162 */     return this;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public BoxModel getModel(ForgeDirection dir)
/* 163:    */   {
/* 164:167 */     BoxModel boxes = new BoxModel();
/* 165:168 */     float w = 0.125F;
/* 166:169 */     boxes.add(new Box(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F).rotateToSide(dir).setTextureSides(new Object[] { Integer.valueOf(dir.ordinal()), BlockTransferNode.nodeBase }));
/* 167:170 */     boxes.add(new Box(0.1875F, 0.0625F, 0.1875F, 0.8125F, 0.25F, 0.8125F).rotateToSide(dir));
/* 168:171 */     boxes.add(new Box(0.3125F, 0.25F, 0.3125F, 0.6875F, 0.375F, 0.6875F).rotateToSide(dir));
/* 169:172 */     boxes.add(new Box(0.375F, 0.25F, 0.375F, 0.625F, 0.375F, 0.625F).rotateToSide(dir).setTexture(BlockTransferNode.nodeBase).setAllSideInvisible().setSideInvisible(new Object[] { Integer.valueOf(dir.getOpposite().ordinal()), Boolean.valueOf(false) }));
/* 170:    */     
/* 171:174 */     return boxes;
/* 172:    */   }
/* 173:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeLiquid
 * JD-Core Version:    0.7.0.1
 */