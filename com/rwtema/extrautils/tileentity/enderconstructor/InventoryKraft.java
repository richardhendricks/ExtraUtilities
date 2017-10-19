/*   1:    */ package com.rwtema.extrautils.tileentity.enderconstructor;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.EnergyStorage;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.inventory.ISidedInventory;
/*   6:    */ import net.minecraft.inventory.InventoryBasic;
/*   7:    */ import net.minecraft.inventory.InventoryCrafting;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class InventoryKraft
/*  13:    */   implements ISidedInventory
/*  14:    */ {
/*  15: 12 */   public CraftMatrix matrix = new CraftMatrix();
/*  16:    */   public CraftResult result;
/*  17:    */   EnergyStorage energy;
/*  18:    */   TileEnderConstructor tile;
/*  19:    */   ItemStack bufferItem;
/*  20:    */   
/*  21:    */   public InventoryKraft(TileEnderConstructor tile)
/*  22:    */   {
/*  23: 19 */     this.energy = tile.energy;
/*  24: 20 */     this.tile = tile;
/*  25: 21 */     this.result = new CraftResult(tile, this.matrix);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void readFromNBT(NBTTagCompound tags)
/*  29:    */   {
/*  30: 25 */     CraftResult.crafting = true;
/*  31: 26 */     for (int i = 0; i < 9; i++) {
/*  32: 27 */       if (tags.hasKey("items_" + i)) {
/*  33: 28 */         this.matrix.inv.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(tags.getCompoundTag("items_" + i)));
/*  34:    */       } else {
/*  35: 30 */         this.matrix.inv.setInventorySlotContents(i, null);
/*  36:    */       }
/*  37:    */     }
/*  38: 33 */     CraftResult.crafting = false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void writeToNBT(NBTTagCompound tags)
/*  42:    */   {
/*  43: 37 */     for (int i = 0; i < 9; i++) {
/*  44: 38 */       if (this.matrix.inv.getStackInSlot(i) != null)
/*  45:    */       {
/*  46: 39 */         NBTTagCompound t = new NBTTagCompound();
/*  47: 40 */         this.matrix.inv.getStackInSlot(i).writeToNBT(t);
/*  48: 41 */         tags.setTag("items_" + i, t);
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getSizeInventory()
/*  54:    */   {
/*  55: 48 */     return 10;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ItemStack getStackInSlot(int i)
/*  59:    */   {
/*  60: 53 */     if (i == 9) {
/*  61: 54 */       return this.result.getStackInSlot(0);
/*  62:    */     }
/*  63: 56 */     return this.matrix.getStackInSlot(i);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ItemStack decrStackSize(int i, int j)
/*  67:    */   {
/*  68: 62 */     if (i == 9)
/*  69:    */     {
/*  70: 63 */       if (this.tile.getBlockMetadata() == 4)
/*  71:    */       {
/*  72: 64 */         ItemStack item = this.result.decrStackSize(0, j);
/*  73: 66 */         if (item != null)
/*  74:    */         {
/*  75: 67 */           if (!this.tile.getWorldObj().isClient)
/*  76:    */           {
/*  77: 68 */             this.energy.setEnergyStored(0);
/*  78: 69 */             this.tile.getWorldObj().setBlockMetadataWithNotify(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord, 0, 2);
/*  79:    */           }
/*  80:    */           else
/*  81:    */           {
/*  82: 71 */             this.tile.getWorldObj().setBlockMetadataWithNotify(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord, 0, 0);
/*  83:    */           }
/*  84: 74 */           return item;
/*  85:    */         }
/*  86:    */       }
/*  87: 78 */       return null;
/*  88:    */     }
/*  89: 80 */     return this.matrix.decrStackSize(i, j);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public ItemStack getStackInSlotOnClosing(int i)
/*  93:    */   {
/*  94: 86 */     if (i == 9) {
/*  95: 87 */       return this.result.getStackInSlotOnClosing(0);
/*  96:    */     }
/*  97: 89 */     return this.matrix.getStackInSlotOnClosing(i);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setInventorySlotContents(int i, ItemStack itemstack)
/* 101:    */   {
/* 102: 95 */     if (i != 9) {
/* 103: 96 */       this.matrix.setInventorySlotContents(i, itemstack);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getInventoryName()
/* 108:    */   {
/* 109:102 */     return "Ender Crafting";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isInventoryNameLocalized()
/* 113:    */   {
/* 114:107 */     return true;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getInventoryStackLimit()
/* 118:    */   {
/* 119:112 */     return 64;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void onInventoryChanged()
/* 123:    */   {
/* 124:117 */     this.matrix.onInventoryChanged();
/* 125:118 */     this.result.onInventoryChanged();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/* 129:    */   {
/* 130:123 */     return true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void openInventory() {}
/* 134:    */   
/* 135:    */   public void closeInventory() {}
/* 136:    */   
/* 137:    */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/* 138:    */   {
/* 139:136 */     return i != 9;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int[] getAccessibleSlotsFromSide(int var1)
/* 143:    */   {
/* 144:141 */     return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean canInsertItem(int i, ItemStack itemstack, int j)
/* 148:    */   {
/* 149:146 */     return i < 9;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean canExtractItem(int i, ItemStack itemstack, int j)
/* 153:    */   {
/* 154:151 */     return (i == 9) && (isEnabled());
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isEnabled()
/* 158:    */   {
/* 159:155 */     return this.tile.energy.getEnergyStored() == this.tile.energy.getMaxEnergyStored();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static class CraftMatrix
/* 163:    */     extends InventoryCrafting
/* 164:    */   {
/* 165:159 */     InventoryBasic inv = new InventoryBasic("Craft Matrix", false, 9);
/* 166:    */     InventoryKraft.CraftResult result;
/* 167:    */     
/* 168:    */     public CraftMatrix()
/* 169:    */     {
/* 170:163 */       super(3, 3);
/* 171:    */     }
/* 172:    */     
/* 173:    */     public ItemStack getStackInRowAndColumn(int par1, int par2)
/* 174:    */     {
/* 175:168 */       if ((par1 >= 0) && (par1 < 3))
/* 176:    */       {
/* 177:169 */         int k = par1 + par2 * 3;
/* 178:170 */         return this.inv.getStackInSlot(k);
/* 179:    */       }
/* 180:172 */       return null;
/* 181:    */     }
/* 182:    */     
/* 183:    */     public int getSizeInventory()
/* 184:    */     {
/* 185:178 */       return this.inv.getSizeInventory();
/* 186:    */     }
/* 187:    */     
/* 188:    */     public ItemStack getStackInSlot(int i)
/* 189:    */     {
/* 190:183 */       return this.inv.getStackInSlot(i);
/* 191:    */     }
/* 192:    */     
/* 193:    */     public ItemStack decrStackSize(int i, int j)
/* 194:    */     {
/* 195:188 */       return this.inv.decrStackSize(i, j);
/* 196:    */     }
/* 197:    */     
/* 198:    */     public ItemStack getStackInSlotOnClosing(int i)
/* 199:    */     {
/* 200:193 */       return this.inv.getStackInSlotOnClosing(i);
/* 201:    */     }
/* 202:    */     
/* 203:    */     public void setInventorySlotContents(int i, ItemStack itemstack)
/* 204:    */     {
/* 205:198 */       this.inv.setInventorySlotContents(i, itemstack);
/* 206:    */     }
/* 207:    */     
/* 208:    */     public String getInventoryName()
/* 209:    */     {
/* 210:203 */       return this.inv.getInventoryName();
/* 211:    */     }
/* 212:    */     
/* 213:    */     public boolean isInventoryNameLocalized()
/* 214:    */     {
/* 215:208 */       return this.inv.isInventoryNameLocalized();
/* 216:    */     }
/* 217:    */     
/* 218:    */     public int getInventoryStackLimit()
/* 219:    */     {
/* 220:213 */       return this.inv.getInventoryStackLimit();
/* 221:    */     }
/* 222:    */     
/* 223:    */     public void setResult(InventoryKraft.CraftResult result)
/* 224:    */     {
/* 225:217 */       this.result = result;
/* 226:    */     }
/* 227:    */     
/* 228:    */     public void onInventoryChanged()
/* 229:    */     {
/* 230:222 */       this.result.markDirty(this);
/* 231:    */     }
/* 232:    */     
/* 233:    */     public boolean isUseableByPlayer(EntityPlayer entityplayer)
/* 234:    */     {
/* 235:227 */       return false;
/* 236:    */     }
/* 237:    */     
/* 238:    */     public void openInventory()
/* 239:    */     {
/* 240:232 */       this.inv.openInventory();
/* 241:    */     }
/* 242:    */     
/* 243:    */     public void closeInventory()
/* 244:    */     {
/* 245:237 */       this.inv.closeInventory();
/* 246:    */     }
/* 247:    */     
/* 248:    */     public boolean isItemValidForSlot(int i, ItemStack itemstack)
/* 249:    */     {
/* 250:242 */       return this.inv.isItemValidForSlot(i, itemstack);
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   public static class CraftResult
/* 255:    */     extends InventoryBasic
/* 256:    */   {
/* 257:247 */     public static boolean crafting = false;
/* 258:    */     public InventoryKraft.CraftMatrix matrix;
/* 259:    */     TileEnderConstructor tile;
/* 260:    */     
/* 261:    */     public CraftResult(TileEnderConstructor tile, InventoryKraft.CraftMatrix matrix)
/* 262:    */     {
/* 263:254 */       super(false, 1);
/* 264:255 */       this.tile = tile;
/* 265:256 */       this.matrix = matrix;
/* 266:257 */       matrix.setResult(this);
/* 267:    */     }
/* 268:    */     
/* 269:    */     public ItemStack decrStackSize(int par1, int par2)
/* 270:    */     {
/* 271:262 */       return super.decrStackSize(par1, par2);
/* 272:    */     }
/* 273:    */     
/* 274:    */     public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 275:    */     {
/* 276:267 */       return false;
/* 277:    */     }
/* 278:    */     
/* 279:    */     public void markDirty(InventoryKraft.CraftMatrix craftMatrix)
/* 280:    */     {
/* 281:271 */       if (crafting) {
/* 282:272 */         return;
/* 283:    */       }
/* 284:274 */       ItemStack item = EnderConstructorRecipesHandler.findMatchingRecipe(craftMatrix, this.tile.getWorldObj());
/* 285:    */       
/* 286:276 */       setInventorySlotContents(0, null);
/* 287:278 */       if (item != null) {
/* 288:279 */         setInventorySlotContents(0, item);
/* 289:    */       }
/* 290:    */     }
/* 291:    */   }
/* 292:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.InventoryKraft
 * JD-Core Version:    0.7.0.1
 */