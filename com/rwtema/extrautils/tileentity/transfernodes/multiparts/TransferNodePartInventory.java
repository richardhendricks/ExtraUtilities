/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*   2:    */ 
/*   3:    */ import codechicken.lib.vec.Cuboid6;
/*   4:    */ import codechicken.multipart.TMultiPart;
/*   5:    */ import codechicken.multipart.TileMultipart;
/*   6:    */ import com.rwtema.extrautils.block.Box;
/*   7:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
/*   8:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeInventory;
/*   9:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  10:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeInventory;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import net.minecraft.inventory.IInventory;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class TransferNodePartInventory
/*  18:    */   extends TransferNodePart
/*  19:    */   implements INodeInventory
/*  20:    */ {
/*  21:    */   public TransferNodePartInventory()
/*  22:    */   {
/*  23: 17 */     super(new TileEntityTransferNodeInventory());
/*  24:    */   }
/*  25:    */   
/*  26:    */   public TransferNodePartInventory(int meta)
/*  27:    */   {
/*  28: 21 */     super(meta, new TileEntityTransferNodeInventory());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public TransferNodePartInventory(TileEntityTransferNode node)
/*  32:    */   {
/*  33: 25 */     super(node);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public TransferNodePartInventory(int meta, TileEntityTransferNodeInventory node)
/*  37:    */   {
/*  38: 29 */     super(meta, node);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void onRemoved()
/*  42:    */   {
/*  43: 34 */     if ((!getWorld().isClient) && 
/*  44: 35 */       (!this.node.buffer.isEmpty()))
/*  45:    */     {
/*  46: 36 */       List<ItemStack> drops = new ArrayList();
/*  47: 37 */       ItemStack item = (ItemStack)getNode().buffer.getBuffer();
/*  48: 38 */       drops.add(item);
/*  49: 39 */       tile().dropItems(drops);
/*  50:    */     }
/*  51: 43 */     super.onRemoved();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Iterable<Cuboid6> getOcclusionBoxes()
/*  55:    */   {
/*  56:142 */     Box t = new Box(0.125F, 0.0F, 0.125F, 0.875F, 0.375F, 0.875F);
/*  57:143 */     t.rotateToSide(getNodeDir());
/*  58:144 */     List<Cuboid6> s = new ArrayList();
/*  59:145 */     s.add(new Cuboid6(t.minX, t.minY, t.minZ, t.maxX, t.maxY, t.maxZ));
/*  60:146 */     return s;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public TileEntityTransferNodeInventory getNode()
/*  64:    */   {
/*  65:151 */     return (TileEntityTransferNodeInventory)this.node;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean occlusionTest(TMultiPart npart)
/*  69:    */   {
/*  70:156 */     return (!(npart instanceof IInventory)) && (super.occlusionTest(npart));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getType()
/*  74:    */   {
/*  75:162 */     return "extrautils:transfer_node_inv";
/*  76:    */   }
/*  77:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.TransferNodePartInventory
 * JD-Core Version:    0.7.0.1
 */