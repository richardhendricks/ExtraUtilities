/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.tileentity.TileEntityFilingCabinet;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  10:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  11:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  12:    */ import net.minecraft.inventory.Container;
/*  13:    */ import net.minecraft.inventory.IInventory;
/*  14:    */ import net.minecraft.inventory.Slot;
/*  15:    */ import net.minecraft.item.Item;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.util.MovementInput;
/*  18:    */ import net.minecraft.util.ResourceLocation;
/*  19:    */ import org.lwjgl.input.Mouse;
/*  20:    */ import org.lwjgl.opengl.GL11;
/*  21:    */ 
/*  22:    */ public class GuiFilingCabinet
/*  23:    */   extends GuiContainer
/*  24:    */ {
/*  25: 19 */   private static final ResourceLocation texture = new ResourceLocation("extrautils", "textures/guiFilingCabinet.png");
/*  26: 20 */   ItemSorter sorter = new ItemSorter();
/*  27: 21 */   private int numItems = 0;
/*  28: 22 */   private int currentScroll = 0;
/*  29: 23 */   private boolean isScrolling = false;
/*  30: 25 */   private int prevn = -1;
/*  31:    */   private TileEntityFilingCabinet cabinet;
/*  32:    */   
/*  33:    */   public GuiFilingCabinet(IInventory player, TileEntityFilingCabinet cabinet)
/*  34:    */   {
/*  35: 30 */     super(new ContainerFilingCabinet(player, cabinet, true));
/*  36: 31 */     this.field_146999_f = 176;
/*  37: 32 */     this.field_147000_g = 240;
/*  38: 33 */     this.prevn = cabinet.getMaxSlots();
/*  39: 34 */     this.cabinet = cabinet;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void sortItems()
/*  43:    */   {
/*  44: 38 */     List<Slot> items = new ArrayList();
/*  45: 39 */     items.clear();
/*  46: 40 */     this.numItems = 0;
/*  47: 42 */     for (int i = 0; i < this.cabinet.getMaxSlots(); i++)
/*  48:    */     {
/*  49: 43 */       if (((Slot)this.field_147002_h.inventorySlots.get(i)).getHasStack()) {
/*  50: 44 */         this.numItems += 1;
/*  51:    */       } else {
/*  52: 45 */         if (i > this.prevn) {
/*  53:    */           break;
/*  54:    */         }
/*  55:    */       }
/*  56: 49 */       items.add((Slot)this.field_147002_h.inventorySlots.get(i));
/*  57:    */     }
/*  58: 52 */     this.prevn = (this.numItems + 1);
/*  59: 53 */     Collections.sort(items, this.sorter);
/*  60: 54 */     int start = getStartSlot();
/*  61: 56 */     if (start > this.numItems - 54) {
/*  62: 57 */       start = this.numItems - 54;
/*  63:    */     }
/*  64: 60 */     if (start < 0) {
/*  65: 61 */       start = 0;
/*  66:    */     }
/*  67: 64 */     for (int i = 0; i < items.size(); i++) {
/*  68: 65 */       if ((i < start) || (i >= start + 54))
/*  69:    */       {
/*  70: 66 */         ((Slot)items.get(i)).xDisplayPosition = -2147483648;
/*  71: 67 */         ((Slot)items.get(i)).yDisplayPosition = -2147483648;
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75: 69 */         int x = (i - start) % 9;
/*  76: 70 */         int y = (i - start - x) / 9;
/*  77: 71 */         ((Slot)items.get(i)).xDisplayPosition = (8 + x * 18);
/*  78: 72 */         ((Slot)items.get(i)).yDisplayPosition = (18 + y * 18);
/*  79:    */       }
/*  80:    */     }
/*  81: 76 */     ContainerFilingCabinet.updated = false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getStartSlot()
/*  85:    */   {
/*  86: 80 */     float t = this.currentScroll;
/*  87: 81 */     t /= 144.0F;
/*  88: 82 */     return (int)Math.floor(t * Math.ceil((this.numItems - 54 + 1) / 1.0F));
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void func_146976_a(float f, int i, int j)
/*  92:    */   {
/*  93: 87 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  94: 88 */     this.mc.renderEngine.bindTexture(texture);
/*  95: 89 */     int k = (this.width - this.field_146999_f) / 2;
/*  96: 90 */     int l = (this.height - this.field_147000_g) / 2;
/*  97: 91 */     drawTexturedModalRect(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/*  98: 93 */     if (this.numItems <= 54) {
/*  99: 94 */       drawTexturedModalRect(k + ScrollX(), l + 128, 176, 9, 17, 8);
/* 100:    */     } else {
/* 101: 96 */       drawTexturedModalRect(k + ScrollX(), l + 128, 176, 0, 17, 8);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setScroll(int mX)
/* 106:    */   {
/* 107:101 */     int prevScroll = getStartSlot();
/* 108:103 */     if (this.numItems <= 54) {
/* 109:104 */       this.currentScroll = 0;
/* 110:    */     } else {
/* 111:106 */       this.currentScroll = mX;
/* 112:    */     }
/* 113:109 */     if (prevScroll != getStartSlot()) {
/* 114:110 */       sortItems();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int ScrollX()
/* 119:    */   {
/* 120:116 */     if (this.numItems <= 54) {
/* 121:117 */       return 8;
/* 122:    */     }
/* 123:120 */     if (this.currentScroll < 0) {
/* 124:121 */       return 8;
/* 125:    */     }
/* 126:124 */     if (this.currentScroll > 143) {
/* 127:125 */       return 151;
/* 128:    */     }
/* 129:128 */     return 8 + this.currentScroll;
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected void mouseMovedOrUp(int par1, int par2, int par3)
/* 133:    */   {
/* 134:133 */     if (par3 >= 0) {
/* 135:134 */       this.isScrolling = false;
/* 136:    */     }
/* 137:137 */     super.mouseMovedOrUp(par1, par2, par3);
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 141:    */   {
/* 142:142 */     if ((!this.isScrolling) && (par3 == 0) && 
/* 143:143 */       (func_146978_c(8, 128, 162, 8, par1, par2)))
/* 144:    */     {
/* 145:144 */       this.isScrolling = true;
/* 146:145 */       setScroll(par1 - this.field_147003_i - 8 - 9);
/* 147:    */     }
/* 148:148 */     super.mouseClicked(par1, par2, par3);
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected void mouseClickMove(int par1, int par2, int par3, long par4)
/* 152:    */   {
/* 153:153 */     if (this.isScrolling) {
/* 154:154 */       setScroll(par1 - this.field_147003_i - 8 - 9);
/* 155:    */     }
/* 156:157 */     super.mouseClickMove(par1, par2, par3, par4);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void handleMouseInput()
/* 160:    */   {
/* 161:162 */     super.handleMouseInput();
/* 162:163 */     int i = Mouse.getEventDWheel();
/* 163:165 */     if ((i != 0) && (this.numItems > 54))
/* 164:    */     {
/* 165:166 */       if (i > 0) {
/* 166:167 */         i = 1;
/* 167:    */       }
/* 168:170 */       if (i < 0) {
/* 169:171 */         i = -1;
/* 170:    */       }
/* 171:174 */       this.currentScroll -= i * 9;
/* 172:176 */       if (this.currentScroll < 0) {
/* 173:177 */         this.currentScroll = 0;
/* 174:    */       }
/* 175:180 */       if (this.currentScroll > 153) {
/* 176:181 */         this.currentScroll = 153;
/* 177:    */       }
/* 178:184 */       setScroll(this.currentScroll);
/* 179:185 */       sortItems();
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void drawScreen(int par1, int par2, float par3)
/* 184:    */   {
/* 185:199 */     if (ContainerFilingCabinet.updated) {
/* 186:200 */       sortItems();
/* 187:    */     }
/* 188:203 */     SlotFilingCabinet.drawing = true;
/* 189:204 */     super.drawScreen(par1, par2, par3);
/* 190:205 */     SlotFilingCabinet.drawing = false;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static class ItemSorter
/* 194:    */     implements Comparator<Slot>
/* 195:    */   {
/* 196:    */     public int compare(Slot arg0, Slot arg1)
/* 197:    */     {
/* 198:211 */       if (!arg0.getHasStack())
/* 199:    */       {
/* 200:212 */         if (!arg1.getHasStack()) {
/* 201:213 */           return 0;
/* 202:    */         }
/* 203:215 */         return 1;
/* 204:    */       }
/* 205:217 */       if (!arg1.getHasStack()) {
/* 206:218 */         return -1;
/* 207:    */       }
/* 208:221 */       EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
/* 209:222 */       boolean sneak = player.movementInput.sneak;
/* 210:223 */       player.movementInput.sneak = false;
/* 211:224 */       String i1 = concat(arg0.getStack().getTooltip(Minecraft.getMinecraft().thePlayer, true));
/* 212:225 */       String i2 = concat(arg1.getStack().getTooltip(Minecraft.getMinecraft().thePlayer, true));
/* 213:226 */       player.movementInput.sneak = sneak;
/* 214:227 */       int a = i1.compareTo(i2);
/* 215:229 */       if (a == 0)
/* 216:    */       {
/* 217:230 */         int b = arg0.getStack().getItem().getUnlocalizedName().compareTo(arg0.getStack().getItem().getUnlocalizedName());
/* 218:232 */         if (b != 0) {
/* 219:233 */           return b;
/* 220:    */         }
/* 221:236 */         int c = intCompare(arg0.getStack().getItemDamage(), arg0.getStack().getItemDamage());
/* 222:238 */         if (c != 0) {
/* 223:239 */           return c;
/* 224:    */         }
/* 225:242 */         return 0;
/* 226:    */       }
/* 227:244 */       return a;
/* 228:    */     }
/* 229:    */     
/* 230:    */     public int intCompare(int a, int b)
/* 231:    */     {
/* 232:249 */       if (a == b) {
/* 233:250 */         return 0;
/* 234:    */       }
/* 235:251 */       if (a > b) {
/* 236:252 */         return 1;
/* 237:    */       }
/* 238:254 */       return -1;
/* 239:    */     }
/* 240:    */     
/* 241:    */     public String concat(List list)
/* 242:    */     {
/* 243:259 */       String s = "";
/* 244:261 */       for (Object aList : list) {
/* 245:262 */         s = s + aList + "\n";
/* 246:    */       }
/* 247:265 */       return s;
/* 248:    */     }
/* 249:    */   }
/* 250:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.GuiFilingCabinet
 * JD-Core Version:    0.7.0.1
 */