/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*   4:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IFilterPipe;
/*   5:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*   6:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic;
/*   7:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.StdPipes;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import net.minecraft.inventory.IInventory;
/*  10:    */ import net.minecraft.inventory.InventoryBasic;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.tileentity.TileEntity;
/*  14:    */ import net.minecraft.util.IIcon;
/*  15:    */ import net.minecraft.world.IBlockAccess;
/*  16:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  17:    */ 
/*  18:    */ public class TileEntityFilterPipe
/*  19:    */   extends TileEntity
/*  20:    */   implements IFilterPipe, IPipe, IPipeCosmetic
/*  21:    */ {
/*  22: 20 */   public InventoryBasic items = new InventoryBasic("Sorting Pipe", true, 6);
/*  23:    */   
/*  24:    */   public boolean canUpdate()
/*  25:    */   {
/*  26: 24 */     return false;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void readFromNBT(NBTTagCompound tags)
/*  30:    */   {
/*  31: 32 */     super.readFromNBT(tags);
/*  32: 34 */     if (tags.hasKey("items"))
/*  33:    */     {
/*  34: 35 */       NBTTagCompound item_tags = tags.getCompoundTag("items");
/*  35: 37 */       for (int i = 0; i < this.items.getSizeInventory(); i++) {
/*  36: 38 */         if (item_tags.hasKey("item_" + i)) {
/*  37: 39 */           this.items.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(item_tags.getCompoundTag("item_" + i)));
/*  38:    */         }
/*  39:    */       }
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/*  44:    */   {
/*  45: 50 */     super.writeToNBT(par1NBTTagCompound);
/*  46: 51 */     NBTTagCompound item_tags = new NBTTagCompound();
/*  47: 53 */     for (int i = 0; i < this.items.getSizeInventory(); i++) {
/*  48: 54 */       if (this.items.getStackInSlot(i) != null)
/*  49:    */       {
/*  50: 55 */         NBTTagCompound item = new NBTTagCompound();
/*  51: 56 */         this.items.getStackInSlot(i).writeToNBT(item);
/*  52: 57 */         item_tags.setTag("item_" + i, item);
/*  53:    */       }
/*  54:    */     }
/*  55: 61 */     par1NBTTagCompound.setTag("items", item_tags);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public IInventory getFilterInventory(IBlockAccess world, int x, int y, int z)
/*  59:    */   {
/*  60: 66 */     return this.items;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/*  64:    */   {
/*  65: 71 */     return StdPipes.getPipeType(9).getOutputDirections(world, x, y, z, dir, buffer);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/*  69:    */   {
/*  70: 76 */     return StdPipes.getPipeType(9).transferItems(world, x, y, z, dir, buffer);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/*  74:    */   {
/*  75: 81 */     return StdPipes.getPipeType(9).canInput(world, x, y, z, dir);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/*  79:    */   {
/*  80: 86 */     return StdPipes.getPipeType(9).canOutput(world, x, y, z, dir);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int limitTransfer(TileEntity dest, ForgeDirection side, INodeBuffer buffer)
/*  84:    */   {
/*  85: 91 */     return StdPipes.getPipeType(9).limitTransfer(dest, side, buffer);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/*  89:    */   {
/*  90: 96 */     return StdPipes.getPipeType(9).shouldConnectToTile(world, x, y, z, dir);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public IIcon baseTexture()
/*  94:    */   {
/*  95:101 */     return ((IPipeCosmetic)StdPipes.getPipeType(9)).baseTexture();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/*  99:    */   {
/* 100:106 */     return ((IPipeCosmetic)StdPipes.getPipeType(9)).pipeTexture(dir, blocked);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public IIcon invPipeTexture(ForgeDirection dir)
/* 104:    */   {
/* 105:111 */     return ((IPipeCosmetic)StdPipes.getPipeType(9)).invPipeTexture(dir);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public IIcon socketTexture(ForgeDirection dir)
/* 109:    */   {
/* 110:116 */     return ((IPipeCosmetic)StdPipes.getPipeType(9)).socketTexture(dir);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getPipeType()
/* 114:    */   {
/* 115:121 */     return StdPipes.getPipeType(9).getPipeType();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public float baseSize()
/* 119:    */   {
/* 120:126 */     return ((IPipeCosmetic)StdPipes.getPipeType(9)).baseSize();
/* 121:    */   }
/* 122:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityFilterPipe
 * JD-Core Version:    0.7.0.1
 */