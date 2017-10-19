/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.asm.FluidIDGetter;
/*   4:    */ import com.rwtema.extrautils.asm.FluidIDGetter.IFluidLegacy;
/*   5:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
/*   6:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeEnergy;
/*   7:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeInventory;
/*   8:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeLiquid;
/*   9:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeUpgradeInventory;
/*  10:    */ import cpw.mods.fml.relauncher.Side;
/*  11:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  12:    */ import invtweaks.api.container.ContainerSection;
/*  13:    */ import invtweaks.api.container.ContainerSectionCallback;
/*  14:    */ import invtweaks.api.container.InventoryContainer;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.inventory.Container;
/*  19:    */ import net.minecraft.inventory.ICrafting;
/*  20:    */ import net.minecraft.inventory.IInventory;
/*  21:    */ import net.minecraft.inventory.Slot;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraftforge.fluids.FluidStack;
/*  24:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  25:    */ 
/*  26:    */ @InventoryContainer
/*  27:    */ public class ContainerTransferNode
/*  28:    */   extends Container
/*  29:    */ {
/*  30: 26 */   public int lastenergy = 0;
/*  31: 27 */   public int lastenergycount = 0;
/*  32: 30 */   public int liquid_type = -1;
/*  33: 30 */   public int liquid_amount = -1;
/*  34:    */   private TileEntityTransferNode node;
/*  35:    */   private IInventory player;
/*  36: 33 */   private int lastpipe_x = 0;
/*  37: 33 */   private int lastpipe_y = 0;
/*  38: 33 */   private int lastpipe_z = 0;
/*  39:    */   
/*  40:    */   public ContainerTransferNode(IInventory player, TileEntityTransferNode node)
/*  41:    */   {
/*  42: 36 */     this.node = node;
/*  43: 38 */     if ((node instanceof IInventory)) {
/*  44: 39 */       addSlotToContainer(new Slot((IInventory)node, 0, 80, 83));
/*  45:    */     }
/*  46: 42 */     for (int i = 0; i < node.upgrades.getSizeInventory(); i++) {
/*  47: 43 */       addSlotToContainer(new SlotChecksValidity(node.upgrades, i, 35 + i * 18, 121));
/*  48:    */     }
/*  49: 46 */     for (int iy = 0; iy < 3; iy++) {
/*  50: 47 */       for (int ix = 0; ix < 9; ix++) {
/*  51: 48 */         addSlotToContainer(new Slot(player, ix + iy * 9 + 9, 8 + ix * 18, 143 + iy * 18));
/*  52:    */       }
/*  53:    */     }
/*  54: 52 */     for (int ix = 0; ix < 9; ix++) {
/*  55: 53 */       addSlotToContainer(new Slot(player, ix, 8 + ix * 18, 201));
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void addCraftingToCrafters(ICrafting icrafting)
/*  60:    */   {
/*  61: 59 */     super.addCraftingToCrafters(icrafting);
/*  62: 60 */     icrafting.sendProgressBarUpdate(this, 0, this.lastpipe_x = this.node.pipe_x);
/*  63: 61 */     icrafting.sendProgressBarUpdate(this, 1, this.lastpipe_y = this.node.pipe_y);
/*  64: 62 */     icrafting.sendProgressBarUpdate(this, 2, this.lastpipe_z = this.node.pipe_z);
/*  65: 64 */     if ((this.node instanceof TileEntityTransferNodeEnergy))
/*  66:    */     {
/*  67: 66 */       icrafting.sendProgressBarUpdate(this, 3, this.lastenergycount = ((TileEntityTransferNodeEnergy)this.node).numMachines());
/*  68: 67 */       this.lastenergy = ((TileEntityTransferNodeEnergy)this.node).getEnergyStored(null);
/*  69: 68 */       for (int i = 0; i < 3; i++) {
/*  70: 69 */         icrafting.sendProgressBarUpdate(this, 6 + i, convToShort(this.lastenergy, i));
/*  71:    */       }
/*  72:    */     }
/*  73: 72 */     int newliquid_type = -1;
/*  74: 73 */     int newliquid_amount = -1;
/*  75: 75 */     if ((this.node instanceof TileEntityTransferNodeLiquid))
/*  76:    */     {
/*  77: 76 */       FluidStack t = ((TileEntityTransferNodeLiquid)this.node).getTankInfo(null)[0].fluid;
/*  78: 78 */       if ((t != null) && (t.amount > 0))
/*  79:    */       {
/*  80: 79 */         newliquid_type = FluidIDGetter.fluidLegacy.getID(t);
/*  81: 80 */         newliquid_amount = t.amount;
/*  82: 81 */         icrafting.sendProgressBarUpdate(this, 4, newliquid_type);
/*  83: 82 */         icrafting.sendProgressBarUpdate(this, 5, newliquid_amount);
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  89:    */   {
/*  90: 93 */     ItemStack itemstack = null;
/*  91: 94 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/*  92:    */     
/*  93: 96 */     int start = 0;int end = this.node.upgrades.getSizeInventory();
/*  94: 97 */     if ((this.node instanceof TileEntityTransferNodeInventory))
/*  95:    */     {
/*  96: 98 */       start++;
/*  97: 99 */       end++;
/*  98:    */     }
/*  99:103 */     if ((slot != null) && (slot.getHasStack()))
/* 100:    */     {
/* 101:104 */       ItemStack itemstack1 = slot.getStack();
/* 102:105 */       itemstack = itemstack1.copy();
/* 103:107 */       if (par2 < end)
/* 104:    */       {
/* 105:108 */         if (!mergeItemStack(itemstack1, end, this.inventorySlots.size(), true)) {
/* 106:109 */           return null;
/* 107:    */         }
/* 108:    */       }
/* 109:111 */       else if (((!this.node.upgrades.isItemValidForSlot(0, itemstack1)) || (!mergeItemStack(itemstack1, start, end, false))) && (
/* 110:112 */         (start == 0) || (!mergeItemStack(itemstack1, 0, start, false)))) {
/* 111:113 */         return null;
/* 112:    */       }
/* 113:116 */       if (itemstack1.stackSize == 0) {
/* 114:117 */         slot.putStack(null);
/* 115:    */       } else {
/* 116:119 */         slot.onSlotChanged();
/* 117:    */       }
/* 118:    */     }
/* 119:123 */     return itemstack;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean canInteractWith(EntityPlayer entityplayer)
/* 123:    */   {
/* 124:128 */     return this.node.isUseableByPlayer(entityplayer);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void detectAndSendChanges()
/* 128:    */   {
/* 129:136 */     super.detectAndSendChanges();
/* 130:137 */     int newenergy = -1;
/* 131:138 */     int newenergycount = -1;
/* 132:140 */     if ((this.node instanceof TileEntityTransferNodeEnergy))
/* 133:    */     {
/* 134:141 */       newenergy = ((TileEntityTransferNodeEnergy)this.node).getEnergyStored(null);
/* 135:142 */       newenergycount = ((TileEntityTransferNodeEnergy)this.node).numMachines();
/* 136:    */     }
/* 137:145 */     int newliquid_type = -1;
/* 138:146 */     int newliquid_type_metadata = -1;
/* 139:147 */     int newliquid_amount = -1;
/* 140:149 */     if ((this.node instanceof TileEntityTransferNodeLiquid))
/* 141:    */     {
/* 142:150 */       FluidStack t = ((TileEntityTransferNodeLiquid)this.node).getTankInfo(null)[0].fluid;
/* 143:152 */       if ((t != null) && (t.amount > 0))
/* 144:    */       {
/* 145:153 */         newliquid_type = FluidIDGetter.fluidLegacy.getID(t);
/* 146:154 */         newliquid_amount = t.amount;
/* 147:    */       }
/* 148:    */     }
/* 149:158 */     for (Object crafter : this.crafters)
/* 150:    */     {
/* 151:159 */       ICrafting icrafting = (ICrafting)crafter;
/* 152:161 */       if ((this.lastpipe_x != this.node.pipe_x) || (this.lastpipe_y != this.node.pipe_y) || (this.lastpipe_z != this.node.pipe_z))
/* 153:    */       {
/* 154:162 */         icrafting.sendProgressBarUpdate(this, 0, this.node.pipe_x);
/* 155:163 */         icrafting.sendProgressBarUpdate(this, 1, this.node.pipe_y);
/* 156:164 */         icrafting.sendProgressBarUpdate(this, 2, this.node.pipe_z);
/* 157:    */       }
/* 158:167 */       if (newenergycount != this.lastenergycount) {
/* 159:168 */         icrafting.sendProgressBarUpdate(this, 3, newenergycount);
/* 160:    */       }
/* 161:171 */       if ((this.liquid_type != newliquid_type) || (this.liquid_amount != newliquid_amount))
/* 162:    */       {
/* 163:172 */         icrafting.sendProgressBarUpdate(this, 4, newliquid_type);
/* 164:173 */         icrafting.sendProgressBarUpdate(this, 5, newliquid_amount);
/* 165:    */       }
/* 166:176 */       if (convToShort(newenergy, 2) != convToShort(this.lastenergy, 0)) {
/* 167:177 */         icrafting.sendProgressBarUpdate(this, 6, convToShort(this.lastenergy, 0));
/* 168:    */       }
/* 169:180 */       if (convToShort(newenergy, 1) != convToShort(this.lastenergy, 1)) {
/* 170:181 */         icrafting.sendProgressBarUpdate(this, 7, convToShort(this.lastenergy, 1));
/* 171:    */       }
/* 172:184 */       if (convToShort(newenergy, 2) != convToShort(this.lastenergy, 2)) {
/* 173:185 */         icrafting.sendProgressBarUpdate(this, 8, convToShort(this.lastenergy, 2));
/* 174:    */       }
/* 175:    */     }
/* 176:190 */     this.lastpipe_x = this.node.pipe_x;
/* 177:191 */     this.lastpipe_y = this.node.pipe_y;
/* 178:192 */     this.lastpipe_z = this.node.pipe_z;
/* 179:193 */     this.lastenergy = newenergy;
/* 180:194 */     this.lastenergycount = newenergycount;
/* 181:195 */     this.liquid_type = newliquid_type;
/* 182:196 */     this.liquid_amount = newliquid_amount;
/* 183:    */   }
/* 184:    */   
/* 185:    */   @SideOnly(Side.CLIENT)
/* 186:    */   public void updateProgressBar(int par1, int par2)
/* 187:    */   {
/* 188:202 */     switch (par1)
/* 189:    */     {
/* 190:    */     case 0: 
/* 191:204 */       this.node.pipe_x = par2;
/* 192:    */     case 1: 
/* 193:207 */       this.node.pipe_y = par2;
/* 194:    */     case 2: 
/* 195:210 */       this.node.pipe_z = par2;
/* 196:211 */       break;
/* 197:    */     case 3: 
/* 198:214 */       this.lastenergycount = par2;
/* 199:215 */       break;
/* 200:    */     case 4: 
/* 201:218 */       this.liquid_type = par2;
/* 202:219 */       break;
/* 203:    */     case 5: 
/* 204:222 */       this.liquid_amount = par2;
/* 205:223 */       break;
/* 206:    */     case 6: 
/* 207:    */     case 7: 
/* 208:    */     case 8: 
/* 209:228 */       this.lastenergy = ((int)changeShort(this.lastenergy, (short)par2, par1 - 6));
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static short convToShort(double t, int level)
/* 214:    */   {
/* 215:234 */     switch (level)
/* 216:    */     {
/* 217:    */     case 0: 
/* 218:236 */       return (short)(int)Math.floor((t - Math.floor(t)) * 32768.0D);
/* 219:    */     case 1: 
/* 220:238 */       return (short)(int)(Math.floor(t) % 32768.0D);
/* 221:    */     case 2: 
/* 222:240 */       return (short)(int)Math.floor(t / 32768.0D);
/* 223:    */     }
/* 224:242 */     return 0;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static float changeShort(float t, short k, int level)
/* 228:    */   {
/* 229:247 */     short[] v = new short[3];
/* 230:249 */     for (int i = 0; i < 3; i++) {
/* 231:250 */       if (i == level) {
/* 232:251 */         v[i] = k;
/* 233:    */       } else {
/* 234:253 */         v[i] = convToShort(t, i);
/* 235:    */       }
/* 236:    */     }
/* 237:255 */     return v[2] * 32768 + v[1] + v[0] / 32768.0F;
/* 238:    */   }
/* 239:    */   
/* 240:    */   @ContainerSectionCallback
/* 241:    */   public Map<ContainerSection, List<Slot>> getSlots()
/* 242:    */   {
/* 243:261 */     return InventoryTweaksHelper.getSlots(this, false);
/* 244:    */   }
/* 245:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.ContainerTransferNode
 * JD-Core Version:    0.7.0.1
 */