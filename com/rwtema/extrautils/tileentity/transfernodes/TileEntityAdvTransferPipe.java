/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*  5:   */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import net.minecraft.inventory.IInventory;
/*  8:   */ import net.minecraft.tileentity.TileEntity;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ import net.minecraft.world.IBlockAccess;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 13:   */ 
/* 14:   */ public class TileEntityAdvTransferPipe
/* 15:   */   extends TileEntity
/* 16:   */   implements IPipe, IPipeCosmetic
/* 17:   */ {
/* 18:16 */   int basePipeType = 0;
/* 19:17 */   int outputsMask = 0;
/* 20:   */   
/* 21:   */   public static boolean isVPipe(World world, int x, int y, int z)
/* 22:   */   {
/* 23:21 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static int getType(World world, int x, int y, int z)
/* 27:   */   {
/* 28:25 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static boolean setBlockType()
/* 32:   */   {
/* 33:29 */     return false;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 37:   */   {
/* 38:34 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 42:   */   {
/* 43:39 */     return false;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 47:   */   {
/* 48:44 */     return false;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 52:   */   {
/* 53:49 */     return false;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int limitTransfer(TileEntity dest, ForgeDirection side, INodeBuffer buffer)
/* 57:   */   {
/* 58:54 */     return 0;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public IInventory getFilterInventory(IBlockAccess world, int x, int y, int z)
/* 62:   */   {
/* 63:59 */     return null;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 67:   */   {
/* 68:64 */     return false;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public String getPipeType()
/* 72:   */   {
/* 73:69 */     return null;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public IIcon baseTexture()
/* 77:   */   {
/* 78:74 */     return null;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 82:   */   {
/* 83:79 */     return null;
/* 84:   */   }
/* 85:   */   
/* 86:   */   public IIcon invPipeTexture(ForgeDirection dir)
/* 87:   */   {
/* 88:84 */     return null;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public IIcon socketTexture(ForgeDirection dir)
/* 92:   */   {
/* 93:89 */     return null;
/* 94:   */   }
/* 95:   */   
/* 96:   */   public float baseSize()
/* 97:   */   {
/* 98:94 */     return 0.0F;
/* 99:   */   }
/* :0:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityAdvTransferPipe
 * JD-Core Version:    0.7.0.1
 */