/*   1:    */ package com.rwtema.extrautils.tileentity;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.inventory.IInventory;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import net.minecraft.tileentity.TileEntity;
/*  11:    */ import net.minecraftforge.oredict.OreDictionary;
/*  12:    */ 
/*  13:    */ public class TileEntityFilingCabinet
/*  14:    */   extends TileEntity
/*  15:    */   implements IInventory
/*  16:    */ {
/*  17: 15 */   public List<ItemStack> itemSlots = new ArrayList();
/*  18: 16 */   public List<ItemStack> inputSlots = new ArrayList();
/*  19: 17 */   private boolean needsUpdate = false;
/*  20:    */   
/*  21:    */   public static boolean areCloseEnoughForBasic(ItemStack a, ItemStack b)
/*  22:    */   {
/*  23: 22 */     if ((a == null) || (b == null)) {
/*  24: 23 */       return false;
/*  25:    */     }
/*  26: 26 */     int[] da = OreDictionary.getOreIDs(a);int[] db = OreDictionary.getOreIDs(b);
/*  27:    */     
/*  28: 28 */     return a.getItem() == b.getItem() ? true : (da.length > 0) || (db.length > 0) ? arrayContain(da, db) : false;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static boolean arrayContain(int[] a, int[] b)
/*  32:    */   {
/*  33: 32 */     if ((a.length == 0) || (b.length == 0)) {
/*  34: 33 */       return false;
/*  35:    */     }
/*  36: 35 */     for (int i = 0; i < a.length; i++) {
/*  37: 36 */       for (int j = 0; j < b.length; j++) {
/*  38: 37 */         if (a[i] == a[j]) {
/*  39: 38 */           return true;
/*  40:    */         }
/*  41:    */       }
/*  42:    */     }
/*  43: 42 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getMaxSlots()
/*  47:    */   {
/*  48: 47 */     if (getBlockMetadata() < 6) {
/*  49: 48 */       return 1728;
/*  50:    */     }
/*  51: 50 */     return 1728;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void updateEntity()
/*  55:    */   {
/*  56: 56 */     handleInput();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void handleInput()
/*  60:    */   {
/*  61: 60 */     if (this.needsUpdate)
/*  62:    */     {
/*  63: 61 */       for (int i = 0; i < this.itemSlots.size(); i++) {
/*  64: 62 */         if (this.itemSlots.get(i) == null)
/*  65:    */         {
/*  66: 63 */           this.itemSlots.remove(i);
/*  67: 64 */           i--;
/*  68:    */         }
/*  69:    */       }
/*  70: 68 */       while (this.inputSlots.size() > 0)
/*  71:    */       {
/*  72: 69 */         boolean added = false;
/*  73: 71 */         for (ItemStack itemSlot : this.itemSlots) {
/*  74: 72 */           if (XUHelper.canItemsStack(itemSlot, (ItemStack)this.inputSlots.get(0), false, true))
/*  75:    */           {
/*  76: 73 */             itemSlot.stackSize += ((ItemStack)this.inputSlots.get(0)).stackSize;
/*  77: 74 */             added = true;
/*  78: 75 */             break;
/*  79:    */           }
/*  80:    */         }
/*  81: 79 */         if (!added) {
/*  82: 80 */           this.itemSlots.add(this.inputSlots.get(0));
/*  83:    */         }
/*  84: 83 */         this.inputSlots.remove(0);
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void onInventoryChanged()
/*  90:    */   {
/*  91: 90 */     this.needsUpdate = true;
/*  92: 91 */     super.onInventoryChanged();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getSizeInventory()
/*  96:    */   {
/*  97: 96 */     return this.itemSlots.size() + this.inputSlots.size() + 1;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ItemStack getStackInSlot(int i)
/* 101:    */   {
/* 102:101 */     if (i < this.itemSlots.size()) {
/* 103:102 */       return (ItemStack)this.itemSlots.get(i);
/* 104:    */     }
/* 105:105 */     if (i - this.itemSlots.size() < this.inputSlots.size()) {
/* 106:106 */       return (ItemStack)this.inputSlots.get(i - this.itemSlots.size());
/* 107:    */     }
/* 108:108 */     return null;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ItemStack decrStackSize(int par1, int par2)
/* 112:    */   {
/* 113:114 */     if ((par1 < this.itemSlots.size()) && (this.itemSlots.get(par1) != null))
/* 114:    */     {
/* 115:117 */       if (par2 > ((ItemStack)this.itemSlots.get(par1)).getMaxStackSize()) {
/* 116:118 */         par2 = ((ItemStack)this.itemSlots.get(par1)).getMaxStackSize();
/* 117:    */       }
/* 118:121 */       if (((ItemStack)this.itemSlots.get(par1)).stackSize <= par2)
/* 119:    */       {
/* 120:122 */         ItemStack itemstack = (ItemStack)this.itemSlots.get(par1);
/* 121:123 */         this.itemSlots.set(par1, null);
/* 122:124 */         onInventoryChanged();
/* 123:125 */         return itemstack;
/* 124:    */       }
/* 125:127 */       ItemStack itemstack = ((ItemStack)this.itemSlots.get(par1)).splitStack(par2);
/* 126:129 */       if (((ItemStack)this.itemSlots.get(par1)).stackSize == 0) {
/* 127:130 */         this.itemSlots.set(par1, null);
/* 128:    */       }
/* 129:133 */       onInventoryChanged();
/* 130:134 */       return itemstack;
/* 131:    */     }
/* 132:137 */     return null;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public ItemStack getStackInSlotOnClosing(int i)
/* 136:    */   {
/* 137:143 */     if (i < this.itemSlots.size()) {
/* 138:144 */       return (ItemStack)this.itemSlots.get(i);
/* 139:    */     }
/* 140:146 */     return null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setInventorySlotContents(int i, ItemStack itemstack)
/* 144:    */   {
/* 145:152 */     if (i < this.itemSlots.size()) {
/* 146:153 */       this.itemSlots.set(i, itemstack);
/* 147:154 */     } else if (i - this.itemSlots.size() < this.inputSlots.size()) {
/* 148:155 */       this.inputSlots.set(i - this.itemSlots.size(), itemstack);
/* 149:156 */     } else if ((i == this.itemSlots.size() + this.inputSlots.size()) && (itemstack != null)) {
/* 150:157 */       this.inputSlots.add(itemstack);
/* 151:    */     }
/* 152:160 */     this.needsUpdate = true;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getInventoryName()
/* 156:    */   {
/* 157:165 */     return "extrautils:filing.cabinet";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isInventoryNameLocalized()
/* 161:    */   {
/* 162:170 */     return false;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getInventoryStackLimit()
/* 166:    */   {
/* 167:175 */     if (getBlockMetadata() >= 6) {
/* 168:176 */       return 1;
/* 169:    */     }
/* 170:179 */     int n = 0;
/* 171:181 */     for (int j = 0; (j < this.itemSlots.size()) && (n <= getMaxSlots()); j++) {
/* 172:182 */       if (this.itemSlots.get(j) != null) {
/* 173:183 */         n += ((ItemStack)this.itemSlots.get(j)).stackSize;
/* 174:    */       }
/* 175:    */     }
/* 176:187 */     for (ItemStack inputSlot : this.inputSlots) {
/* 177:188 */       if (inputSlot != null) {
/* 178:189 */         n += inputSlot.stackSize;
/* 179:    */       }
/* 180:    */     }
/* 181:192 */     return Math.max(1, getMaxSlots() - n);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/* 185:    */   {
/* 186:197 */     return !isInvalid();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/* 190:    */   {
/* 191:202 */     if (itemstack == null) {
/* 192:203 */       return false;
/* 193:    */     }
/* 194:206 */     if (i == this.itemSlots.size() + this.inputSlots.size())
/* 195:    */     {
/* 196:207 */       boolean basic = getBlockMetadata() < 6;
/* 197:209 */       if ((!basic) && (itemstack.getMaxStackSize() != 1)) {
/* 198:210 */         return false;
/* 199:    */       }
/* 200:213 */       int n = 0;
/* 201:215 */       for (int j = 0; (j < this.itemSlots.size()) && (n < getMaxSlots()); j++) {
/* 202:216 */         if (this.itemSlots.get(j) != null)
/* 203:    */         {
/* 204:217 */           if ((basic) && (!areCloseEnoughForBasic((ItemStack)this.itemSlots.get(j), itemstack))) {
/* 205:218 */             return false;
/* 206:    */           }
/* 207:221 */           n += ((ItemStack)this.itemSlots.get(j)).stackSize;
/* 208:    */         }
/* 209:    */       }
/* 210:225 */       for (int j = 0; (j < this.inputSlots.size()) && (n < getMaxSlots()); j++) {
/* 211:226 */         if (this.inputSlots.get(j) != null) {
/* 212:227 */           n += ((ItemStack)this.inputSlots.get(j)).stackSize;
/* 213:    */         }
/* 214:    */       }
/* 215:230 */       return n < getMaxSlots();
/* 216:    */     }
/* 217:234 */     return false;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void readInvFromTags(NBTTagCompound tags)
/* 221:    */   {
/* 222:238 */     int n = 0;
/* 223:240 */     if (tags.hasKey("item_no")) {
/* 224:241 */       n = tags.getInteger("item_no");
/* 225:    */     }
/* 226:244 */     this.itemSlots.clear();
/* 227:245 */     this.inputSlots.clear();
/* 228:247 */     for (int i = 0; i < n; i++)
/* 229:    */     {
/* 230:248 */       ItemStack item = ItemStack.loadItemStackFromNBT(tags.getCompoundTag("item_" + i));
/* 231:250 */       if (item != null)
/* 232:    */       {
/* 233:251 */         item.stackSize = tags.getCompoundTag("item_" + i).getInteger("Size");
/* 234:253 */         if (item.stackSize > 0) {
/* 235:254 */           this.itemSlots.add(item);
/* 236:    */         }
/* 237:    */       }
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void writeInvToTags(NBTTagCompound tags)
/* 242:    */   {
/* 243:261 */     handleInput();
/* 244:263 */     if (this.itemSlots.size() > 0)
/* 245:    */     {
/* 246:264 */       tags.setInteger("item_no", this.itemSlots.size());
/* 247:266 */       for (int i = 0; i < this.itemSlots.size(); i++)
/* 248:    */       {
/* 249:267 */         NBTTagCompound t = new NBTTagCompound();
/* 250:268 */         ((ItemStack)this.itemSlots.get(i)).writeToNBT(t);
/* 251:269 */         t.setInteger("Size", ((ItemStack)this.itemSlots.get(i)).stackSize);
/* 252:270 */         tags.setTag("item_" + i, t);
/* 253:    */       }
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void readFromNBT(NBTTagCompound tags)
/* 258:    */   {
/* 259:277 */     super.readFromNBT(tags);
/* 260:278 */     readInvFromTags(tags);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void writeToNBT(NBTTagCompound tags)
/* 264:    */   {
/* 265:283 */     super.writeToNBT(tags);
/* 266:284 */     writeInvToTags(tags);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void openInventory() {}
/* 270:    */   
/* 271:    */   public void closeInventory() {}
/* 272:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityFilingCabinet
 * JD-Core Version:    0.7.0.1
 */