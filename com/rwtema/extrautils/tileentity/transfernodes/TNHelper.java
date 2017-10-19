/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.IEnergyConnection;
/*   4:    */ import com.rwtema.extrautils.helper.XURandom;
/*   5:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*   6:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeBlock;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Random;
/*  12:    */ import java.util.Set;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.block.BlockChest;
/*  15:    */ import net.minecraft.inventory.IInventory;
/*  16:    */ import net.minecraft.inventory.ISidedInventory;
/*  17:    */ import net.minecraft.inventory.InventoryLargeChest;
/*  18:    */ import net.minecraft.tileentity.TileEntity;
/*  19:    */ import net.minecraft.tileentity.TileEntityChest;
/*  20:    */ import net.minecraft.world.IBlockAccess;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  23:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  24:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  25:    */ 
/*  26:    */ public class TNHelper
/*  27:    */ {
/*  28: 22 */   public static Random rand = ;
/*  29: 23 */   public static Set<Block> pipeBlocks = new HashSet();
/*  30: 24 */   public static List<ForgeDirection> directions = new ArrayList();
/*  31:    */   
/*  32:    */   static
/*  33:    */   {
/*  34: 27 */     for (int i = 0; i < 6; i++) {
/*  35: 28 */       directions.add(ForgeDirection.getOrientation(i));
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static IInventory getInventory(TileEntity tile)
/*  40:    */   {
/*  41: 33 */     if ((tile instanceof IInventory))
/*  42:    */     {
/*  43: 34 */       if ((tile instanceof TileEntityChest))
/*  44:    */       {
/*  45: 35 */         int x = tile.xCoord;int y = tile.yCoord;int z = tile.zCoord;
/*  46: 36 */         Block blockID = tile.getWorldObj().getBlock(x, y, z);
/*  47: 38 */         if ((!tile.getWorldObj().isAirBlock(x, y, z)) && ((blockID instanceof BlockChest)))
/*  48:    */         {
/*  49: 39 */           if (tile.getWorldObj().getBlock(x - 1, y, z) == blockID) {
/*  50: 40 */             return new InventoryLargeChest("container.chestDouble", (TileEntityChest)tile.getWorldObj().getTileEntity(x - 1, y, z), (IInventory)tile);
/*  51:    */           }
/*  52: 43 */           if (tile.getWorldObj().getBlock(x + 1, y, z) == blockID) {
/*  53: 44 */             return new InventoryLargeChest("container.chestDouble", (IInventory)tile, (TileEntityChest)tile.getWorldObj().getTileEntity(x + 1, y, z));
/*  54:    */           }
/*  55: 47 */           if (tile.getWorldObj().getBlock(x, y, z - 1) == blockID) {
/*  56: 48 */             return new InventoryLargeChest("container.chestDouble", (TileEntityChest)tile.getWorldObj().getTileEntity(x, y, z - 1), (IInventory)tile);
/*  57:    */           }
/*  58: 51 */           if (tile.getWorldObj().getBlock(x, y, z + 1) == blockID) {
/*  59: 52 */             return new InventoryLargeChest("container.chestDouble", (IInventory)tile, (TileEntityChest)tile.getWorldObj().getTileEntity(x, y, z + 1));
/*  60:    */           }
/*  61:    */         }
/*  62:    */       }
/*  63: 57 */       return (IInventory)tile;
/*  64:    */     }
/*  65: 60 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static boolean isValidTileEntity(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/*  69:    */   {
/*  70: 64 */     return (getPipe(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == null) && (isValidTileEntity(world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ), dir.getOpposite().ordinal()));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static boolean isValidTileEntity(TileEntity inv, int side)
/*  74:    */   {
/*  75: 69 */     if (inv == null) {
/*  76: 70 */       return false;
/*  77:    */     }
/*  78: 73 */     ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
/*  79:    */     
/*  80: 75 */     String classname = inv.getClass().toString();
/*  81: 77 */     if ((classname.contains("thermalexpansion")) && (classname.contains("conduit"))) {
/*  82: 78 */       return false;
/*  83:    */     }
/*  84: 81 */     if ((inv instanceof IFluidHandler))
/*  85:    */     {
/*  86: 82 */       FluidTankInfo[] t = ((IFluidHandler)inv).getTankInfo(forgeSide);
/*  87: 84 */       if ((t != null) && (t.length != 0)) {
/*  88: 85 */         return true;
/*  89:    */       }
/*  90:    */     }
/*  91: 89 */     if (((inv instanceof IInventory)) && 
/*  92: 90 */       (((IInventory)inv).getSizeInventory() > 0)) {
/*  93: 91 */       if ((inv instanceof ISidedInventory))
/*  94:    */       {
/*  95: 92 */         int[] t = ((ISidedInventory)inv).getAccessibleSlotsFromSide(side);
/*  96: 94 */         if ((t != null) && (t.length != 0)) {
/*  97: 95 */           return true;
/*  98:    */         }
/*  99:    */       }
/* 100:    */       else
/* 101:    */       {
/* 102: 98 */         return true;
/* 103:    */       }
/* 104:    */     }
/* 105:103 */     return isRFEnergy(inv, forgeSide);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static boolean isRFEnergy(TileEntity inv, ForgeDirection forgeSide)
/* 109:    */   {
/* 110:107 */     return ((inv instanceof IEnergyConnection)) && (((IEnergyConnection)inv).canConnectEnergy(forgeSide));
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static boolean isEnergy(TileEntity inv, ForgeDirection forgeSide)
/* 114:    */   {
/* 115:111 */     return isRFEnergy(inv, forgeSide);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static void initBlocks()
/* 119:    */   {
/* 120:116 */     for (Object aBlockRegistry : Block.blockRegistry)
/* 121:    */     {
/* 122:117 */       Block i = (Block)aBlockRegistry;
/* 123:118 */       if (((i instanceof IPipe)) || ((i instanceof IPipeBlock))) {
/* 124:119 */         pipeBlocks.add(i);
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static IPipe getPipe(IBlockAccess world, int x, int y, int z)
/* 130:    */   {
/* 131:125 */     if (world == null) {
/* 132:126 */       return null;
/* 133:    */     }
/* 134:129 */     if ((y < 0) || (y >= 256)) {
/* 135:130 */       return null;
/* 136:    */     }
/* 137:133 */     TileEntity tile = world.getTileEntity(x, y, z);
/* 138:135 */     if (tile != null)
/* 139:    */     {
/* 140:136 */       if ((tile instanceof IPipe)) {
/* 141:137 */         return (IPipe)tile;
/* 142:    */       }
/* 143:140 */       if ((tile instanceof IPipeBlock)) {
/* 144:141 */         return ((IPipeBlock)tile).getPipe(world.getBlockMetadata(x, y, z));
/* 145:    */       }
/* 146:    */     }
/* 147:145 */     Block id = world.getBlock(x, y, z);
/* 148:147 */     if ((!id.isAir(world, x, y, z)) && (pipeBlocks.contains(id)))
/* 149:    */     {
/* 150:148 */       if ((id instanceof IPipe)) {
/* 151:149 */         return (IPipe)id;
/* 152:    */       }
/* 153:152 */       if ((id instanceof IPipeBlock)) {
/* 154:153 */         return ((IPipeBlock)id).getPipe(world.getBlockMetadata(x, y, z));
/* 155:    */       }
/* 156:    */     }
/* 157:157 */     return null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 161:    */   {
/* 162:161 */     IPipe pipe = getPipe(world, x, y, z);
/* 163:162 */     return (pipe != null) && (pipe.canInput(world, x, y, z, dir));
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 167:    */   {
/* 168:166 */     IPipe pipe = getPipe(world, x, y, z);
/* 169:167 */     return (pipe != null) && (pipe.canOutput(world, x, y, z, dir));
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static boolean doesPipeConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 173:    */   {
/* 174:171 */     return ((canOutput(world, x, y, z, dir)) && (canInput(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite()))) || ((canInput(world, x, y, z, dir)) && (canOutput(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite())));
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static ForgeDirection[] randomDirections()
/* 178:    */   {
/* 179:176 */     Collections.shuffle(directions, rand);
/* 180:177 */     return (ForgeDirection[])directions.toArray(new ForgeDirection[directions.size()]);
/* 181:    */   }
/* 182:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TNHelper
 * JD-Core Version:    0.7.0.1
 */