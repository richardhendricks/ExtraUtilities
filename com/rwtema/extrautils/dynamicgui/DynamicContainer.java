/*   1:    */ package com.rwtema.extrautils.dynamicgui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.ISidedFunction;
/*   6:    */ import com.rwtema.extrautils.gui.InventoryTweaksHelper;
/*   7:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   8:    */ import com.rwtema.extrautils.network.packets.PacketGUIWidget;
/*   9:    */ import cpw.mods.fml.relauncher.Side;
/*  10:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  11:    */ import invtweaks.api.container.ContainerSection;
/*  12:    */ import invtweaks.api.container.ContainerSectionCallback;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.LinkedList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import net.minecraft.client.Minecraft;
/*  18:    */ import net.minecraft.client.gui.FontRenderer;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  21:    */ import net.minecraft.inventory.Container;
/*  22:    */ import net.minecraft.inventory.ICrafting;
/*  23:    */ import net.minecraft.inventory.IInventory;
/*  24:    */ import net.minecraft.inventory.Slot;
/*  25:    */ import net.minecraft.item.ItemStack;
/*  26:    */ import net.minecraft.nbt.NBTBase;
/*  27:    */ import net.minecraft.nbt.NBTTagCompound;
/*  28:    */ import net.minecraft.util.StatCollector;
/*  29:    */ 
/*  30:    */ public abstract class DynamicContainer
/*  31:    */   extends Container
/*  32:    */ {
/*  33:    */   public static final int playerInvWidth = 162;
/*  34:    */   public static final int playerInvHeight = 95;
/*  35: 31 */   public static final ISidedFunction<String, Integer> stringWidth = new ISidedFunction()
/*  36:    */   {
/*  37:    */     @SideOnly(Side.SERVER)
/*  38:    */     public Integer applyServer(String input)
/*  39:    */     {
/*  40: 35 */       return Integer.valueOf(input == null ? 0 : input.length());
/*  41:    */     }
/*  42:    */     
/*  43:    */     @SideOnly(Side.CLIENT)
/*  44:    */     public Integer applyClient(String input)
/*  45:    */     {
/*  46: 41 */       return Integer.valueOf(input == null ? 0 : Minecraft.getMinecraft().fontRenderer.getStringWidth(input));
/*  47:    */     }
/*  48:    */   };
/*  49: 45 */   public List<IWidget> widgets = new ArrayList();
/*  50: 46 */   public int playerSlotsStart = -1;
/*  51: 47 */   public LinkedList<EntityPlayerMP> playerCrafters = new LinkedList();
/*  52: 48 */   public int width = 176;
/*  53: 48 */   public int height = 166;
/*  54: 49 */   public boolean changesOnly = false;
/*  55:    */   
/*  56:    */   public void addSlot(WidgetSlot slot)
/*  57:    */   {
/*  58: 52 */     addSlotToContainer(slot);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void addCraftingToCrafters(ICrafting par1ICrafting)
/*  62:    */   {
/*  63: 57 */     if ((par1ICrafting instanceof EntityPlayerMP)) {
/*  64: 58 */       this.playerCrafters.add((EntityPlayerMP)par1ICrafting);
/*  65:    */     }
/*  66: 61 */     this.changesOnly = false;
/*  67: 62 */     super.addCraftingToCrafters(par1ICrafting);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void recieveDescriptionPacket(NBTTagCompound tag)
/*  71:    */   {
/*  72: 66 */     int n = this.widgets.size();
/*  73: 68 */     for (int j = 0; j < n; j++) {
/*  74: 69 */       if (tag.hasKey(Integer.toString(j)))
/*  75:    */       {
/*  76: 70 */         NBTBase nbtobj = tag.getTag(Integer.toString(j));
/*  77: 71 */         if ((nbtobj instanceof NBTTagCompound))
/*  78:    */         {
/*  79: 72 */           NBTTagCompound desc = (NBTTagCompound)nbtobj;
/*  80: 74 */           if (desc.hasKey("name"))
/*  81:    */           {
/*  82: 77 */             int i = desc.getInteger("name");
/*  83: 79 */             if ((i >= 0) && (i < this.widgets.size())) {
/*  84: 80 */               ((IWidget)this.widgets.get(i)).handleDescriptionPacket(desc);
/*  85:    */             }
/*  86:    */           }
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void detectAndSendChanges()
/*  93:    */   {
/*  94: 90 */     NBTTagCompound tag = null;
/*  95: 93 */     for (int i = 0; i < this.widgets.size(); i++)
/*  96:    */     {
/*  97: 94 */       NBTTagCompound t = ((IWidget)this.widgets.get(i)).getDescriptionPacket(this.changesOnly);
/*  98: 96 */       if (t != null)
/*  99:    */       {
/* 100: 97 */         if (tag == null) {
/* 101: 98 */           tag = new NBTTagCompound();
/* 102:    */         }
/* 103:101 */         t.setInteger("name", i);
/* 104:    */         
/* 105:103 */         tag.setTag(Integer.toString(i), t);
/* 106:    */       }
/* 107:    */     }
/* 108:107 */     this.changesOnly = true;
/* 109:109 */     if (tag != null) {
/* 110:110 */       for (EntityPlayerMP player : this.playerCrafters) {
/* 111:111 */         NetworkHandler.sendPacketToPlayer(new PacketGUIWidget(this.windowId, tag), player);
/* 112:    */       }
/* 113:    */     }
/* 114:115 */     super.detectAndSendChanges();
/* 115:    */   }
/* 116:    */   
/* 117:    */   @SideOnly(Side.CLIENT)
/* 118:    */   public void removeCraftingFromCrafters(ICrafting par1ICrafting)
/* 119:    */   {
/* 120:124 */     if ((par1ICrafting instanceof EntityPlayerMP)) {
/* 121:125 */       this.playerCrafters.remove(par1ICrafting);
/* 122:    */     }
/* 123:128 */     super.removeCraftingFromCrafters(par1ICrafting);
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected void validate()
/* 127:    */   {
/* 128:132 */     for (IWidget widget : this.widgets) {
/* 129:133 */       widget.addToContainer(this);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void addPlayerSlotsToBottom(IInventory inventory)
/* 134:    */   {
/* 135:138 */     addPlayerSlots(inventory, (this.width - 162) / 2, this.height - 95);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void crop(int border)
/* 139:    */   {
/* 140:142 */     int maxx = 18;
/* 141:143 */     int maxy = 18;
/* 142:145 */     for (IWidget widget : this.widgets)
/* 143:    */     {
/* 144:146 */       maxx = Math.max(maxx, widget.getX() + widget.getW());
/* 145:147 */       maxy = Math.max(maxy, widget.getY() + widget.getH());
/* 146:    */     }
/* 147:150 */     this.width = (maxx + border);
/* 148:151 */     this.height = (maxy + border);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void cropAndAddPlayerSlots(IInventory inventory)
/* 152:    */   {
/* 153:155 */     crop(4);
/* 154:156 */     this.height += 95;
/* 155:158 */     if (this.width < 170) {
/* 156:159 */       this.width = 170;
/* 157:    */     }
/* 158:162 */     addPlayerSlotsToBottom(inventory);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void addPlayerSlots(IInventory inventory, int x, int y)
/* 162:    */   {
/* 163:166 */     this.playerSlotsStart = 0;
/* 164:168 */     for (IWidget w : this.widgets) {
/* 165:169 */       if ((w instanceof Slot)) {
/* 166:170 */         this.playerSlotsStart += 1;
/* 167:    */       }
/* 168:    */     }
/* 169:174 */     this.widgets.add(new WidgetTextTranslate(x, y, inventory.getInventoryName(), 162));
/* 170:176 */     for (int j = 0; j < 3; j++) {
/* 171:177 */       for (int k = 0; k < 9; k++)
/* 172:    */       {
/* 173:178 */         WidgetSlot w = new WidgetSlot(inventory, k + j * 9 + 9, x + k * 18, y + 14 + j * 18);
/* 174:179 */         addWidget(w);
/* 175:    */       }
/* 176:    */     }
/* 177:183 */     for (int j = 0; j < 9; j++)
/* 178:    */     {
/* 179:184 */       WidgetSlot w = new WidgetSlot(inventory, j, x + j * 18, y + 14 + 58);
/* 180:185 */       addWidget(w);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void addWidget(IWidget w)
/* 185:    */   {
/* 186:190 */     this.widgets.add(w);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 190:    */   {
/* 191:200 */     ItemStack itemstack = null;
/* 192:201 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/* 193:203 */     if ((this.playerSlotsStart > 0) && (slot != null) && (slot.getHasStack()))
/* 194:    */     {
/* 195:204 */       ItemStack itemstack1 = slot.getStack();
/* 196:205 */       itemstack = itemstack1.copy();
/* 197:207 */       if (par2 < this.playerSlotsStart)
/* 198:    */       {
/* 199:208 */         if (!mergeItemStack(itemstack1, this.playerSlotsStart, this.inventorySlots.size(), true)) {
/* 200:209 */           return null;
/* 201:    */         }
/* 202:    */       }
/* 203:211 */       else if (!mergeItemStack(itemstack1, 0, this.playerSlotsStart, false)) {
/* 204:212 */         return null;
/* 205:    */       }
/* 206:215 */       if (itemstack1.stackSize == 0) {
/* 207:216 */         slot.putStack(null);
/* 208:    */       } else {
/* 209:218 */         slot.onSlotChanged();
/* 210:    */       }
/* 211:    */     }
/* 212:222 */     return itemstack;
/* 213:    */   }
/* 214:    */   
/* 215:    */   protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
/* 216:    */   {
/* 217:227 */     boolean flag1 = false;
/* 218:228 */     int k = p_75135_2_;
/* 219:230 */     if (p_75135_4_) {
/* 220:231 */       k = p_75135_3_ - 1;
/* 221:    */     }
/* 222:237 */     if (p_75135_1_.isStackable()) {
/* 223:238 */       while ((p_75135_1_.stackSize > 0) && (((!p_75135_4_) && (k < p_75135_3_)) || ((p_75135_4_) && (k >= p_75135_2_))))
/* 224:    */       {
/* 225:239 */         Slot slot = (Slot)this.inventorySlots.get(k);
/* 226:240 */         ItemStack itemstack1 = slot.getStack();
/* 227:242 */         if ((slot.isItemValid(p_75135_1_)) && (itemstack1 != null) && (itemstack1.getItem() == p_75135_1_.getItem()) && ((!p_75135_1_.getHasSubtypes()) || (p_75135_1_.getItemDamage() == itemstack1.getItemDamage())) && (ItemStack.areItemStackTagsEqual(p_75135_1_, itemstack1)))
/* 228:    */         {
/* 229:244 */           int l = itemstack1.stackSize + p_75135_1_.stackSize;
/* 230:246 */           if (l <= p_75135_1_.getMaxStackSize())
/* 231:    */           {
/* 232:247 */             p_75135_1_.stackSize = 0;
/* 233:248 */             itemstack1.stackSize = l;
/* 234:249 */             slot.onSlotChanged();
/* 235:250 */             flag1 = true;
/* 236:    */           }
/* 237:251 */           else if (itemstack1.stackSize < p_75135_1_.getMaxStackSize())
/* 238:    */           {
/* 239:252 */             p_75135_1_.stackSize -= p_75135_1_.getMaxStackSize() - itemstack1.stackSize;
/* 240:253 */             itemstack1.stackSize = p_75135_1_.getMaxStackSize();
/* 241:254 */             slot.onSlotChanged();
/* 242:255 */             flag1 = true;
/* 243:    */           }
/* 244:    */         }
/* 245:259 */         if (p_75135_4_) {
/* 246:260 */           k--;
/* 247:    */         } else {
/* 248:262 */           k++;
/* 249:    */         }
/* 250:    */       }
/* 251:    */     }
/* 252:267 */     if (p_75135_1_.stackSize > 0)
/* 253:    */     {
/* 254:268 */       if (p_75135_4_) {
/* 255:269 */         k = p_75135_3_ - 1;
/* 256:    */       } else {
/* 257:271 */         k = p_75135_2_;
/* 258:    */       }
/* 259:274 */       while (((!p_75135_4_) && (k < p_75135_3_)) || ((p_75135_4_) && (k >= p_75135_2_)))
/* 260:    */       {
/* 261:275 */         Slot slot = (Slot)this.inventorySlots.get(k);
/* 262:276 */         ItemStack itemstack1 = slot.getStack();
/* 263:278 */         if ((itemstack1 == null) && (slot.isItemValid(p_75135_1_)))
/* 264:    */         {
/* 265:280 */           slot.putStack(p_75135_1_.copy());
/* 266:281 */           slot.onSlotChanged();
/* 267:282 */           p_75135_1_.stackSize = 0;
/* 268:283 */           flag1 = true;
/* 269:284 */           break;
/* 270:    */         }
/* 271:287 */         if (p_75135_4_) {
/* 272:288 */           k--;
/* 273:    */         } else {
/* 274:290 */           k++;
/* 275:    */         }
/* 276:    */       }
/* 277:    */     }
/* 278:295 */     return flag1;
/* 279:    */   }
/* 280:    */   
/* 281:    */   @ContainerSectionCallback
/* 282:    */   public Map<ContainerSection, List<Slot>> getSlots()
/* 283:    */   {
/* 284:301 */     return InventoryTweaksHelper.getSlots(this, false);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public int getStringWidth(String text)
/* 288:    */   {
/* 289:305 */     return ((Integer)ExtraUtilsMod.proxy.apply(stringWidth, text)).intValue();
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void addTitle(String name, boolean translate)
/* 293:    */   {
/* 294:    */     WidgetText e;
/* 295:    */     WidgetText e;
/* 296:310 */     if (translate) {
/* 297:311 */       e = new WidgetTextTranslate(5, 5, name, ((Integer)ExtraUtilsMod.proxy.apply(stringWidth, StatCollector.translateToLocal(name))).intValue());
/* 298:    */     } else {
/* 299:313 */       e = new WidgetText(5, 5, name, ((Integer)ExtraUtilsMod.proxy.apply(stringWidth, name)).intValue());
/* 300:    */     }
/* 301:315 */     this.widgets.add(e);
/* 302:    */   }
/* 303:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.DynamicContainer
 * JD-Core Version:    0.7.0.1
 */