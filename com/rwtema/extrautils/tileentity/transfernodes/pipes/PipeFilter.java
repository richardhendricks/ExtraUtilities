/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.item.ItemNodeUpgrade;
/*   4:    */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*   5:    */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*   6:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import net.minecraft.inventory.IInventory;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ import net.minecraft.world.IBlockAccess;
/*  12:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  13:    */ 
/*  14:    */ public class PipeFilter
/*  15:    */   extends PipeBase
/*  16:    */ {
/*  17:    */   public PipeFilter()
/*  18:    */   {
/*  19: 17 */     super("Filter");
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/*  23:    */   {
/*  24: 22 */     ArrayList<ForgeDirection> dirs = new ArrayList();
/*  25: 23 */     if (TNHelper.getPipe(world, x, y, z) != null)
/*  26:    */     {
/*  27: 24 */       IInventory inv = TNHelper.getPipe(world, x, y, z).getFilterInventory(world, x, y, z);
/*  28: 26 */       if (inv == null) {
/*  29: 27 */         return super.getOutputDirections(world, x, y, z, dir, buffer);
/*  30:    */       }
/*  31: 30 */       for (ForgeDirection d : TNHelper.randomDirections()) {
/*  32: 31 */         if ((d != dir.getOpposite()) && (TNHelper.canOutput(world, x, y, z, d)) && 
/*  33: 32 */           (TNHelper.canInput(world, x + d.offsetX, y + d.offsetY, z + d.offsetZ, d.getOpposite())))
/*  34:    */         {
/*  35: 33 */           ItemStack filter = inv.getStackInSlot(d.ordinal());
/*  36: 35 */           if ((filter != null) && 
/*  37: 36 */             (ItemNodeUpgrade.matchesFilterBuffer(buffer, filter))) {
/*  38: 37 */             dirs.add(d);
/*  39:    */           }
/*  40:    */         }
/*  41:    */       }
/*  42: 42 */       for (ForgeDirection d : TNHelper.randomDirections()) {
/*  43: 43 */         if ((d != dir.getOpposite()) && (TNHelper.canOutput(world, x, y, z, d)) && 
/*  44: 44 */           (TNHelper.canInput(world, x + d.offsetX, y + d.offsetY, z + d.offsetZ, d.getOpposite())) && 
/*  45: 45 */           (inv.getStackInSlot(d.ordinal()) == null)) {
/*  46: 46 */           dirs.add(d);
/*  47:    */         }
/*  48:    */       }
/*  49:    */     }
/*  50: 51 */     return dirs;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/*  54:    */   {
/*  55: 56 */     boolean advance = true;
/*  56: 57 */     IPipe pipe = TNHelper.getPipe(world, x, y, z);
/*  57: 59 */     if (pipe == null) {
/*  58: 60 */       return true;
/*  59:    */     }
/*  60: 63 */     IInventory inv = pipe.getFilterInventory(world, x, y, z);
/*  61: 65 */     if (inv == null) {
/*  62: 66 */       return super.transferItems(world, x, y, z, dir, buffer);
/*  63:    */     }
/*  64: 69 */     for (ForgeDirection d : TNHelper.randomDirections()) {
/*  65: 70 */       if (d.getOpposite() != dir)
/*  66:    */       {
/*  67: 71 */         ItemStack filter = inv.getStackInSlot(d.ordinal());
/*  68: 73 */         if ((filter != null) && 
/*  69: 74 */           (pipe.shouldConnectToTile(world, x, y, z, d)) && 
/*  70: 75 */           (ItemNodeUpgrade.matchesFilterBuffer(buffer, filter)) && 
/*  71: 76 */           (!buffer.transfer(world.getTileEntity(x + d.offsetX, y + d.offsetY, z + d.offsetZ), d.getOpposite(), pipe, x, y, z, dir))) {
/*  72: 77 */           advance = false;
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76: 83 */     for (ForgeDirection d : TNHelper.randomDirections()) {
/*  77: 84 */       if (d.getOpposite() != dir)
/*  78:    */       {
/*  79: 85 */         ItemStack filter = inv.getStackInSlot(d.ordinal());
/*  80: 87 */         if ((filter == null) && 
/*  81: 88 */           (pipe.shouldConnectToTile(world, x, y, z, d)) && 
/*  82: 89 */           (!buffer.transfer(world.getTileEntity(x + d.offsetX, y + d.offsetY, z + d.offsetZ), d.getOpposite(), pipe, x, y, z, dir))) {
/*  83: 90 */           advance = false;
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87: 96 */     return advance;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/*  91:    */   {
/*  92:101 */     if (dir.ordinal() == 6) {
/*  93:102 */       return BlockTransferPipe.pipes;
/*  94:    */     }
/*  95:105 */     return BlockTransferPipe.pipes_diamond[dir.ordinal()];
/*  96:    */   }
/*  97:    */   
/*  98:    */   public IIcon invPipeTexture(ForgeDirection dir)
/*  99:    */   {
/* 100:110 */     return pipeTexture(dir, false);
/* 101:    */   }
/* 102:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeFilter
 * JD-Core Version:    0.7.0.1
 */