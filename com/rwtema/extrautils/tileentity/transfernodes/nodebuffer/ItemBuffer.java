/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes.nodebuffer;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import com.rwtema.extrautils.tileentity.transfernodes.InvHelper;
/*   5:    */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*   6:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*   7:    */ import net.minecraft.inventory.IInventory;
/*   8:    */ import net.minecraft.inventory.ISidedInventory;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.tileentity.TileEntity;
/*  12:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  13:    */ 
/*  14:    */ public class ItemBuffer
/*  15:    */   implements INodeBuffer
/*  16:    */ {
/*  17:    */   public INode node;
/*  18: 17 */   public ItemStack item = null;
/*  19:    */   
/*  20:    */   public boolean transfer(TileEntity tile, ForgeDirection side, IPipe insertingPipe, int x, int y, int z, ForgeDirection travelDir)
/*  21:    */   {
/*  22: 21 */     if ((this.item != null) && ((tile instanceof IInventory)) && (side != ForgeDirection.UNKNOWN))
/*  23:    */     {
/*  24: 22 */       boolean nonSided = !(tile instanceof ISidedInventory);
/*  25: 23 */       IInventory inv = TNHelper.getInventory(tile);
/*  26: 24 */       int empty = -1;
/*  27: 25 */       int filter = -1;
/*  28: 26 */       int maxStack = Math.min(this.item.getMaxStackSize(), inv.getInventoryStackLimit());
/*  29: 28 */       if (insertingPipe != null) {
/*  30: 29 */         filter = insertingPipe.limitTransfer(tile, side, this);
/*  31:    */       }
/*  32: 32 */       if (filter < 0) {
/*  33: 33 */         filter = maxStack;
/*  34: 34 */       } else if (filter == 0) {
/*  35: 35 */         return true;
/*  36:    */       }
/*  37: 38 */       boolean flag = true;
/*  38: 40 */       for (int i : InvHelper.getSlots(inv, side.ordinal())) {
/*  39: 41 */         if (inv.getStackInSlot(i) == null)
/*  40:    */         {
/*  41: 42 */           if ((empty == -1) && (inv.isItemValidForSlot(i, this.item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(i, this.item, side.ordinal())))) {
/*  42: 43 */             empty = i;
/*  43:    */           }
/*  44:    */         }
/*  45: 46 */         else if ((InvHelper.canStack(this.item, inv.getStackInSlot(i))) && (inv.isItemValidForSlot(i, this.item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(i, this.item, side.ordinal()))))
/*  46:    */         {
/*  47: 47 */           ItemStack dest = inv.getStackInSlot(i);
/*  48: 49 */           if ((maxStack - dest.stackSize > 0) && (filter > 0))
/*  49:    */           {
/*  50: 50 */             int l = Math.min(Math.min(this.item.stackSize, maxStack - dest.stackSize), filter);
/*  51: 52 */             if (l > 0)
/*  52:    */             {
/*  53: 53 */               dest.stackSize += l;
/*  54: 54 */               this.item.stackSize -= l;
/*  55: 55 */               filter -= l;
/*  56: 56 */               flag = true;
/*  57: 58 */               if (this.item.stackSize <= 0) {
/*  58: 59 */                 this.item = null;
/*  59:    */               } else {
/*  60: 63 */                 if (filter <= 0) {
/*  61:    */                   break;
/*  62:    */                 }
/*  63:    */               }
/*  64:    */             }
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68: 72 */       if ((filter > 0) && (this.item != null) && (empty != -1) && (inv.isItemValidForSlot(empty, this.item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(empty, this.item, side.ordinal()))))
/*  69:    */       {
/*  70: 73 */         if (filter < this.item.stackSize)
/*  71:    */         {
/*  72: 74 */           inv.setInventorySlotContents(empty, this.item.splitStack(filter));
/*  73:    */         }
/*  74:    */         else
/*  75:    */         {
/*  76: 76 */           inv.setInventorySlotContents(empty, this.item);
/*  77: 77 */           this.item = null;
/*  78:    */         }
/*  79: 80 */         flag = true;
/*  80:    */       }
/*  81: 83 */       if (flag) {
/*  82: 84 */         inv.onInventoryChanged();
/*  83:    */       }
/*  84:    */     }
/*  85: 88 */     return true;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ItemStack getBuffer()
/*  89:    */   {
/*  90: 93 */     return this.item;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getBufferType()
/*  94:    */   {
/*  95: 98 */     return "items";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setBuffer(Object buffer)
/*  99:    */   {
/* 100:103 */     if ((buffer == null) || ((buffer instanceof ItemStack))) {
/* 101:104 */       this.item = ((ItemStack)buffer);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void readFromNBT(NBTTagCompound tags)
/* 106:    */   {
/* 107:110 */     if (tags.hasKey("bufferItem")) {
/* 108:111 */       this.item = ItemStack.loadItemStackFromNBT(tags.getCompoundTag("bufferItem"));
/* 109:    */     } else {
/* 110:113 */       this.item = null;
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void writeToNBT(NBTTagCompound tags)
/* 115:    */   {
/* 116:119 */     if (this.item != null)
/* 117:    */     {
/* 118:120 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 119:121 */       this.item.writeToNBT(nbttagcompound1);
/* 120:122 */       tags.setTag("bufferItem", nbttagcompound1);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isEmpty()
/* 125:    */   {
/* 126:128 */     if (this.item == null) {
/* 127:129 */       return true;
/* 128:    */     }
/* 129:130 */     if (this.item.stackSize == 0)
/* 130:    */     {
/* 131:131 */       this.item = null;
/* 132:132 */       return true;
/* 133:    */     }
/* 134:134 */     return false;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setNode(INode node)
/* 138:    */   {
/* 139:140 */     this.node = node;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public INode getNode()
/* 143:    */   {
/* 144:145 */     return this.node;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean transferTo(INodeBuffer receptor, int no)
/* 148:    */   {
/* 149:151 */     if ((this.item == null) || (this.item.stackSize == 0) || (!getBufferType().equals(receptor.getBufferType()))) {
/* 150:152 */       return false;
/* 151:    */     }
/* 152:154 */     ItemStack buffer = (ItemStack)receptor.getBuffer();
/* 153:    */     ItemStack newbuffer;
/* 154:157 */     if (buffer == null)
/* 155:    */     {
/* 156:158 */       ItemStack newbuffer = this.item.copy();
/* 157:159 */       newbuffer.stackSize = 0;
/* 158:    */     }
/* 159:    */     else
/* 160:    */     {
/* 161:161 */       newbuffer = buffer.copy();
/* 162:    */     }
/* 163:163 */     if (((receptor.getNode() instanceof IInventory)) && 
/* 164:164 */       (((IInventory)receptor.getNode()).isItemValidForSlot(0, this.item))) {
/* 165:165 */       return false;
/* 166:    */     }
/* 167:167 */     if (XUHelper.canItemsStack(this.item, newbuffer))
/* 168:    */     {
/* 169:168 */       int m = newbuffer.getMaxStackSize() - newbuffer.stackSize;
/* 170:169 */       if (no < m) {
/* 171:169 */         m = no;
/* 172:    */       }
/* 173:170 */       if (this.item.stackSize < m) {
/* 174:170 */         m = this.item.stackSize;
/* 175:    */       }
/* 176:172 */       if (m <= 0) {
/* 177:173 */         return false;
/* 178:    */       }
/* 179:175 */       newbuffer.stackSize += m;
/* 180:176 */       receptor.setBuffer(newbuffer);
/* 181:177 */       receptor.markDirty();
/* 182:    */       
/* 183:179 */       this.item.stackSize -= m;
/* 184:180 */       if (this.item.stackSize == 0) {
/* 185:181 */         this.item = null;
/* 186:    */       }
/* 187:    */     }
/* 188:183 */     return true;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Object recieve(Object a)
/* 192:    */   {
/* 193:189 */     if (!(a instanceof ItemStack)) {
/* 194:190 */       return a;
/* 195:    */     }
/* 196:192 */     ItemStack i = (ItemStack)a;
/* 197:193 */     if (this.item == null)
/* 198:    */     {
/* 199:194 */       this.item = i;
/* 200:195 */       return null;
/* 201:    */     }
/* 202:    */     int m;
/* 203:196 */     if (XUHelper.canItemsStack(i, this.item)) {
/* 204:197 */       m = this.item.getMaxStackSize() - this.item.stackSize;
/* 205:    */     }
/* 206:201 */     return i;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void markDirty()
/* 210:    */   {
/* 211:206 */     this.node.bufferChanged();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean shouldSearch()
/* 215:    */   {
/* 216:211 */     return !isEmpty();
/* 217:    */   }
/* 218:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.ItemBuffer
 * JD-Core Version:    0.7.0.1
 */