/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.multipart.TileMultipart;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  5:   */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IFilterPipe;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ import net.minecraft.inventory.IInventory;
/* 10:   */ import net.minecraft.inventory.InventoryBasic;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.nbt.NBTTagCompound;
/* 13:   */ import net.minecraft.util.MovingObjectPosition;
/* 14:   */ import net.minecraft.world.IBlockAccess;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class FilterPipePart
/* 18:   */   extends PipePart
/* 19:   */   implements IFilterPipe
/* 20:   */ {
/* 21:18 */   public InventoryBasic items = new InventoryBasic("Sorting Pipe", true, 6);
/* 22:   */   
/* 23:   */   public FilterPipePart(int meta)
/* 24:   */   {
/* 25:21 */     super(9);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public FilterPipePart(InventoryBasic items)
/* 29:   */   {
/* 30:25 */     super(9);
/* 31:26 */     this.items = items;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public FilterPipePart()
/* 35:   */   {
/* 36:30 */     super(9);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void onRemoved()
/* 40:   */   {
/* 41:35 */     if (!getWorld().isClient)
/* 42:   */     {
/* 43:36 */       List<ItemStack> drops = new ArrayList();
/* 44:38 */       for (int i = 0; i < this.items.getSizeInventory(); i++) {
/* 45:39 */         if (this.items.getStackInSlot(i) != null) {
/* 46:40 */           drops.add(this.items.getStackInSlot(i));
/* 47:   */         }
/* 48:   */       }
/* 49:43 */       tile().dropItems(drops);
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String getType()
/* 54:   */   {
/* 55:49 */     return "extrautils:transfer_pipe_filter";
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean activate(EntityPlayer player, MovingObjectPosition part, ItemStack item)
/* 59:   */   {
/* 60:54 */     player.openGui(ExtraUtilsMod.instance, 0, getWorld(), x(), y(), z());
/* 61:55 */     return true;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void load(NBTTagCompound tags)
/* 65:   */   {
/* 66:63 */     super.load(tags);
/* 67:65 */     if (tags.hasKey("items"))
/* 68:   */     {
/* 69:66 */       NBTTagCompound item_tags = tags.getCompoundTag("items");
/* 70:68 */       for (int i = 0; i < this.items.getSizeInventory(); i++) {
/* 71:69 */         if (item_tags.hasKey("item_" + i)) {
/* 72:70 */           this.items.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(item_tags.getCompoundTag("item_" + i)));
/* 73:   */         }
/* 74:   */       }
/* 75:   */     }
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void save(NBTTagCompound par1NBTTagCompound)
/* 79:   */   {
/* 80:81 */     super.save(par1NBTTagCompound);
/* 81:82 */     NBTTagCompound item_tags = new NBTTagCompound();
/* 82:84 */     for (int i = 0; i < this.items.getSizeInventory(); i++) {
/* 83:85 */       if (this.items.getStackInSlot(i) != null)
/* 84:   */       {
/* 85:86 */         NBTTagCompound item = new NBTTagCompound();
/* 86:87 */         this.items.getStackInSlot(i).writeToNBT(item);
/* 87:88 */         item_tags.setTag("item_" + i, item);
/* 88:   */       }
/* 89:   */     }
/* 90:92 */     par1NBTTagCompound.setTag("items", item_tags);
/* 91:   */   }
/* 92:   */   
/* 93:   */   public IInventory getFilterInventory(IBlockAccess world, int x, int y, int z)
/* 94:   */   {
/* 95:97 */     return this.items;
/* 96:   */   }
/* 97:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.FilterPipePart
 * JD-Core Version:    0.7.0.1
 */