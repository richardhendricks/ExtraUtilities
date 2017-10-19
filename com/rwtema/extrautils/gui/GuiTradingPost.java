/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   4:    */ import com.rwtema.extrautils.network.packets.PacketVillagerReturn;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Comparator;
/*   8:    */ import java.util.List;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.gui.FontRenderer;
/*  11:    */ import net.minecraft.client.gui.GuiButton;
/*  12:    */ import net.minecraft.client.gui.GuiScreen;
/*  13:    */ import net.minecraft.client.renderer.RenderHelper;
/*  14:    */ import net.minecraft.client.renderer.Tessellator;
/*  15:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  16:    */ import net.minecraft.client.settings.GameSettings;
/*  17:    */ import net.minecraft.entity.Entity;
/*  18:    */ import net.minecraft.entity.passive.EntityVillager;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.item.EnumRarity;
/*  21:    */ import net.minecraft.item.Item;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.nbt.NBTTagCompound;
/*  24:    */ import net.minecraft.tileentity.TileEntity;
/*  25:    */ import net.minecraft.util.EnumChatFormatting;
/*  26:    */ import net.minecraft.village.MerchantRecipe;
/*  27:    */ import net.minecraft.village.MerchantRecipeList;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ import net.minecraft.world.WorldProvider;
/*  30:    */ import org.lwjgl.opengl.GL11;
/*  31:    */ 
/*  32:    */ public class GuiTradingPost
/*  33:    */   extends GuiScreen
/*  34:    */ {
/*  35: 28 */   protected static RenderItem itemRenderer = new RenderItem();
/*  36: 29 */   private static Comparator c = new VillagerSorter();
/*  37: 30 */   protected int guiLeft = 0;
/*  38: 31 */   protected int guiTop = 0;
/*  39: 35 */   protected String screenTitle = "Trading Post";
/*  40: 36 */   List<MerchantRecipe> merchant_recipes = new ArrayList();
/*  41: 37 */   List<Integer> merchant_id = new ArrayList();
/*  42: 38 */   int item_size = 18;
/*  43: 39 */   int space_between_items = 10;
/*  44: 40 */   int button_width = this.item_size * 3 + this.space_between_items * 4;
/*  45: 41 */   int button_height = 20;
/*  46: 42 */   int w = this.button_width + this.space_between_items;
/*  47: 42 */   int h = this.button_height + 5;
/*  48: 43 */   int grid_w = 4;
/*  49: 43 */   int grid_h = 5;
/*  50: 44 */   int grid_no = this.grid_w * this.grid_h;
/*  51: 45 */   int x0 = 0;
/*  52: 45 */   int y0 = 20;
/*  53: 46 */   int curPage = 0;
/*  54:    */   int maxPages;
/*  55:    */   TileEntity tradingPost;
/*  56:    */   EntityPlayer player;
/*  57:    */   private GuiButton[] buttons;
/*  58:    */   private GuiButton leftButton;
/*  59:    */   private GuiButton rightButton;
/*  60:    */   
/*  61:    */   public GuiTradingPost(EntityPlayer _player, int[] p1ids, MerchantRecipeList[] p2recipes, TileEntity _post)
/*  62:    */   {
/*  63: 55 */     this.tradingPost = _post;
/*  64: 56 */     this.player = _player;
/*  65: 57 */     List<Object[]> t = new ArrayList();
/*  66: 59 */     for (int i = 0; i < p2recipes.length; i++)
/*  67:    */     {
/*  68: 60 */       Entity e = _player.worldObj.getEntityByID(p1ids[i]);
/*  69: 62 */       if (e != null) {
/*  70: 63 */         for (int j = 0; j < p2recipes[i].size(); j++) {
/*  71: 64 */           t.add(new Object[] { e, p2recipes[i].get(j) });
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75: 67 */     Collections.sort(t, c);
/*  76: 68 */     int hash = -1;
/*  77: 69 */     int j = 0;
/*  78: 71 */     for (int i = 0; i < p2recipes.length; i++)
/*  79:    */     {
/*  80: 72 */       MerchantRecipe m = (MerchantRecipe)((Object[])t.get(i))[1];
/*  81: 73 */       int h = m.writeToTags().hashCode();
/*  82: 76 */       if ((h != hash) || (i == 0))
/*  83:    */       {
/*  84: 77 */         hash = h;
/*  85: 78 */         this.merchant_recipes.add(j, m);
/*  86: 79 */         this.merchant_id.add(j, Integer.valueOf(((Entity)((Object[])t.get(i))[0]).getEntityId()));
/*  87: 80 */         j++;
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static int getProf(Object a)
/*  93:    */   {
/*  94:100 */     if ((a instanceof EntityVillager)) {
/*  95:101 */       return ((EntityVillager)a).getProfession();
/*  96:    */     }
/*  97:103 */     return -1;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPage(int page)
/* 101:    */   {
/* 102:108 */     if (page < 0) {
/* 103:109 */       page = 0;
/* 104:    */     }
/* 105:112 */     if (page >= this.maxPages) {
/* 106:113 */       page = this.maxPages - 1;
/* 107:    */     }
/* 108:116 */     this.curPage = page;
/* 109:118 */     for (int i = 0; i < this.buttons.length; i++) {
/* 110:119 */       this.buttons[i].enabled = ((getRecipeForButton(i) < this.merchant_id.size()) && (!((MerchantRecipe)this.merchant_recipes.get(i)).isRecipeDisabled()));
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getRecipeForButton(int i)
/* 115:    */   {
/* 116:124 */     return this.curPage * this.grid_no + i;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void initGui()
/* 120:    */   {
/* 121:132 */     this.buttonList.clear();
/* 122:133 */     int h2 = (int)Math.ceil(this.width / 1.5D);
/* 123:134 */     this.grid_w = Math.min(this.width / this.w, 4);
/* 124:135 */     this.grid_h = Math.min((h2 - 40) / this.h, 5);
/* 125:137 */     if (this.grid_h < 0) {
/* 126:138 */       this.grid_h = 1;
/* 127:    */     }
/* 128:141 */     this.grid_no = (this.grid_w * this.grid_h);
/* 129:142 */     this.buttons = new GuiButton[this.grid_no];
/* 130:143 */     this.maxPages = ((int)Math.ceil(this.merchant_recipes.size() / this.grid_no));
/* 131:144 */     this.x0 = ((this.width - this.grid_w * this.w) / 2);
/* 132:    */     
/* 133:146 */     this.y0 = 80;
/* 134:147 */     this.buttonList.add(this.leftButton = new GuiButton(0, this.width / 2 - 100, 15, 20, 20, "<"));
/* 135:148 */     this.buttonList.add(this.rightButton = new GuiButton(1, this.width / 2 + 80, 15, 20, 20, ">"));
/* 136:149 */     this.leftButton.enabled = (this.maxPages > 1);
/* 137:150 */     this.rightButton.enabled = (this.maxPages > 1);
/* 138:151 */     String text = "";
/* 139:153 */     for (int i = 0; i < this.grid_no; i++)
/* 140:    */     {
/* 141:154 */       this.buttons[i] = new GuiButton(2 + i, this.x0 + i / this.grid_h * this.w, this.y0 + i % this.grid_h * this.h, this.button_width, this.button_height, text);
/* 142:156 */       if (i >= this.merchant_id.size()) {
/* 143:157 */         this.buttons[i].enabled = false;
/* 144:    */       }
/* 145:160 */       this.buttonList.add(this.buttons[i]);
/* 146:    */     }
/* 147:163 */     setPage(0);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean doesGuiPauseGame()
/* 151:    */   {
/* 152:172 */     return false;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void updateScreen()
/* 156:    */   {
/* 157:180 */     boolean flag = false;
/* 158:182 */     if (this.tradingPost == null) {
/* 159:183 */       flag = true;
/* 160:184 */     } else if (this.tradingPost.getWorldObj() == null) {
/* 161:185 */       flag = true;
/* 162:186 */     } else if (this.tradingPost.getWorldObj().getTileEntity(this.tradingPost.xCoord, this.tradingPost.yCoord, this.tradingPost.zCoord) != this.tradingPost) {
/* 163:187 */       flag = true;
/* 164:    */     }
/* 165:190 */     if (flag) {
/* 166:191 */       this.mc.displayGuiScreen(null);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected void actionPerformed(GuiButton par1GuiButton)
/* 171:    */   {
/* 172:201 */     if (par1GuiButton.enabled) {
/* 173:202 */       if (par1GuiButton.id == 0)
/* 174:    */       {
/* 175:203 */         setPage(this.curPage - 1);
/* 176:    */       }
/* 177:204 */       else if (par1GuiButton.id == 1)
/* 178:    */       {
/* 179:205 */         setPage(this.curPage + 1);
/* 180:    */       }
/* 181:    */       else
/* 182:    */       {
/* 183:207 */         int i = getRecipeForButton(par1GuiButton.id - 2);
/* 184:    */         
/* 185:209 */         NetworkHandler.sendPacketToServer(new PacketVillagerReturn(((Integer)this.merchant_id.get(i)).intValue(), this.tradingPost.getWorldObj().provider.dimensionId, this.tradingPost.xCoord, this.tradingPost.yCoord, this.tradingPost.zCoord));
/* 186:    */         
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:215 */         this.mc.displayGuiScreen(null);
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void drawScreen(int par1, int par2, float par3)
/* 197:    */   {
/* 198:225 */     drawDefaultBackground();
/* 199:226 */     drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);
/* 200:227 */     super.drawScreen(par1, par2, par3);
/* 201:228 */     GL11.glPushMatrix();
/* 202:229 */     RenderHelper.enableGUIStandardItemLighting();
/* 203:230 */     GL11.glDisable(2896);
/* 204:231 */     GL11.glEnable(32826);
/* 205:232 */     GL11.glEnable(2903);
/* 206:233 */     GL11.glEnable(2896);
/* 207:234 */     itemRenderer.zLevel = 100.0F;
/* 208:236 */     for (int i = 0; i < this.buttons.length; i++)
/* 209:    */     {
/* 210:237 */       int j = getRecipeForButton(i);
/* 211:239 */       if (j < this.merchant_recipes.size())
/* 212:    */       {
/* 213:240 */         int x = this.buttons[i].field_146128_h;int y = this.buttons[i].field_146129_i;
/* 214:242 */         if (((MerchantRecipe)this.merchant_recipes.get(j)).getSecondItemToBuy() != null)
/* 215:    */         {
/* 216:245 */           itemRenderer.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getItemToBuy(), x + this.space_between_items, y + 2);
/* 217:246 */           itemRenderer.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getItemToBuy(), x + this.space_between_items, y + 2);
/* 218:247 */           itemRenderer.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getSecondItemToBuy(), x + this.space_between_items * 2 + this.item_size, y + 2);
/* 219:248 */           itemRenderer.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getSecondItemToBuy(), x + this.space_between_items * 2 + this.item_size, y + 2);
/* 220:249 */           itemRenderer.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getItemToSell(), x + 3 * this.space_between_items + 2 * this.item_size, y + 2);
/* 221:250 */           itemRenderer.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getItemToSell(), x + 3 * this.space_between_items + 2 * this.item_size, y + 2);
/* 222:    */         }
/* 223:    */         else
/* 224:    */         {
/* 225:252 */           itemRenderer.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getItemToBuy(), x + (this.item_size + this.space_between_items) / 2 + this.space_between_items, y + 2);
/* 226:    */           
/* 227:254 */           itemRenderer.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getItemToBuy(), x + (this.item_size + this.space_between_items) / 2 + this.space_between_items, y + 2);
/* 228:    */           
/* 229:256 */           itemRenderer.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getItemToSell(), x + (this.item_size + this.space_between_items) / 2 + 2 * this.space_between_items + this.item_size, y + 2);
/* 230:    */           
/* 231:258 */           itemRenderer.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, ((MerchantRecipe)this.merchant_recipes.get(j)).getItemToSell(), x + (this.item_size + this.space_between_items) / 2 + 2 * this.space_between_items + this.item_size, y + 2);
/* 232:    */         }
/* 233:    */       }
/* 234:    */     }
/* 235:264 */     itemRenderer.zLevel = 0.0F;
/* 236:265 */     GL11.glDisable(2896);
/* 237:267 */     for (int i = 0; i < this.buttons.length; i++)
/* 238:    */     {
/* 239:268 */       int j = getRecipeForButton(i);
/* 240:270 */       if (j < this.merchant_recipes.size())
/* 241:    */       {
/* 242:271 */         int x = this.buttons[i].field_146128_h;int y = this.buttons[i].field_146129_i;
/* 243:273 */         if (((MerchantRecipe)this.merchant_recipes.get(j)).getSecondItemToBuy() != null)
/* 244:    */         {
/* 245:274 */           drawCenteredString(this.fontRendererObj, "+", x + this.space_between_items * 3 / 2 + this.item_size, y + 7, 16777215);
/* 246:275 */           drawCenteredString(this.fontRendererObj, "=", x + this.space_between_items * 5 / 2 + 2 * this.item_size, y + 7, 16777215);
/* 247:    */         }
/* 248:    */         else
/* 249:    */         {
/* 250:277 */           drawCenteredString(this.fontRendererObj, "=", x + this.button_width / 2, y + 7, 16777215);
/* 251:    */         }
/* 252:    */       }
/* 253:    */     }
/* 254:282 */     for (int i = 0; i < this.buttons.length; i++)
/* 255:    */     {
/* 256:283 */       int j = getRecipeForButton(i);
/* 257:285 */       if (j < this.merchant_recipes.size())
/* 258:    */       {
/* 259:286 */         int x = this.buttons[i].field_146128_h;int y = this.buttons[i].field_146129_i;
/* 260:288 */         if (((MerchantRecipe)this.merchant_recipes.get(j)).getSecondItemToBuy() != null)
/* 261:    */         {
/* 262:289 */           if (func_146978_c(x + this.space_between_items, y, this.item_size, this.item_size, par1, par2)) {
/* 263:290 */             drawItemStackTooltip(((MerchantRecipe)this.merchant_recipes.get(j)).getItemToBuy(), par1, par2);
/* 264:    */           }
/* 265:293 */           if (func_146978_c(x + this.space_between_items * 2 + this.item_size, y, this.item_size, this.item_size, par1, par2)) {
/* 266:294 */             drawItemStackTooltip(((MerchantRecipe)this.merchant_recipes.get(j)).getSecondItemToBuy(), par1, par2);
/* 267:    */           }
/* 268:297 */           if (func_146978_c(x + 3 * this.space_between_items + 2 * this.item_size, y, this.item_size, this.item_size, par1, par2)) {
/* 269:298 */             drawItemStackTooltip(((MerchantRecipe)this.merchant_recipes.get(j)).getItemToSell(), par1, par2);
/* 270:    */           }
/* 271:    */         }
/* 272:    */         else
/* 273:    */         {
/* 274:301 */           if (func_146978_c(x + (this.item_size + this.space_between_items) / 2 + this.space_between_items, y, this.item_size, this.item_size, par1, par2)) {
/* 275:302 */             drawItemStackTooltip(((MerchantRecipe)this.merchant_recipes.get(j)).getItemToBuy(), par1, par2);
/* 276:    */           }
/* 277:305 */           if (func_146978_c(x + (this.item_size + this.space_between_items) / 2 + 2 * this.space_between_items + this.item_size, y, this.item_size, this.item_size, par1, par2)) {
/* 278:306 */             drawItemStackTooltip(((MerchantRecipe)this.merchant_recipes.get(j)).getItemToSell(), par1, par2);
/* 279:    */           }
/* 280:    */         }
/* 281:    */       }
/* 282:    */     }
/* 283:312 */     GL11.glPopMatrix();
/* 284:313 */     GL11.glEnable(2896);
/* 285:314 */     GL11.glEnable(2929);
/* 286:315 */     RenderHelper.enableStandardItemLighting();
/* 287:    */   }
/* 288:    */   
/* 289:    */   protected void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3)
/* 290:    */   {
/* 291:319 */     GL11.glDisable(32826);
/* 292:320 */     RenderHelper.disableStandardItemLighting();
/* 293:321 */     GL11.glDisable(2896);
/* 294:322 */     GL11.glDisable(2929);
/* 295:323 */     List list = par1ItemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/* 296:325 */     if (!list.isEmpty())
/* 297:    */     {
/* 298:326 */       int k = 0;
/* 299:330 */       for (int l = 0; l < list.size(); l++)
/* 300:    */       {
/* 301:331 */         int i1 = this.fontRendererObj.getStringWidth((String)list.get(l));
/* 302:333 */         if (i1 > k) {
/* 303:334 */           k = i1;
/* 304:    */         }
/* 305:    */       }
/* 306:338 */       l = par2 + 12;
/* 307:339 */       int i1 = par3 - 12;
/* 308:340 */       int j1 = 8;
/* 309:342 */       if (list.size() > 1) {
/* 310:343 */         j1 += 2 + (list.size() - 1) * 10;
/* 311:    */       }
/* 312:349 */       this.zLevel = 300.0F;
/* 313:350 */       itemRenderer.zLevel = 300.0F;
/* 314:351 */       int k1 = -267386864;
/* 315:352 */       drawGradientRect(l - 3, i1 - 4, l + k + 3, i1 - 3, k1, k1);
/* 316:353 */       drawGradientRect(l - 3, i1 + j1 + 3, l + k + 3, i1 + j1 + 4, k1, k1);
/* 317:354 */       drawGradientRect(l - 3, i1 - 3, l + k + 3, i1 + j1 + 3, k1, k1);
/* 318:355 */       drawGradientRect(l - 4, i1 - 3, l - 3, i1 + j1 + 3, k1, k1);
/* 319:356 */       drawGradientRect(l + k + 3, i1 - 3, l + k + 4, i1 + j1 + 3, k1, k1);
/* 320:357 */       int l1 = 1347420415;
/* 321:358 */       int i2 = (l1 & 0xFEFEFE) >> 1 | l1 & 0xFF000000;
/* 322:359 */       drawGradientRect(l - 3, i1 - 3 + 1, l - 3 + 1, i1 + j1 + 3 - 1, l1, i2);
/* 323:360 */       drawGradientRect(l + k + 2, i1 - 3 + 1, l + k + 3, i1 + j1 + 3 - 1, l1, i2);
/* 324:361 */       drawGradientRect(l - 3, i1 - 3, l + k + 3, i1 - 3 + 1, l1, l1);
/* 325:362 */       drawGradientRect(l - 3, i1 + j1 + 2, l + k + 3, i1 + j1 + 3, i2, i2);
/* 326:364 */       for (int j2 = 0; j2 < list.size(); j2++)
/* 327:    */       {
/* 328:365 */         String s = (String)list.get(j2);
/* 329:367 */         if (j2 == 0) {
/* 330:368 */           s = par1ItemStack.getRarity().rarityColor + s;
/* 331:    */         } else {
/* 332:370 */           s = EnumChatFormatting.GRAY + s;
/* 333:    */         }
/* 334:373 */         this.fontRendererObj.drawStringWithShadow(s, l, i1, -1);
/* 335:375 */         if (j2 == 0) {
/* 336:376 */           i1 += 2;
/* 337:    */         }
/* 338:379 */         i1 += 10;
/* 339:    */       }
/* 340:382 */       this.zLevel = 0.0F;
/* 341:383 */       itemRenderer.zLevel = 0.0F;
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   private void drawLine(double x1, double x2, double y1, double y2, int col)
/* 346:    */   {
/* 347:388 */     GL11.glDisable(3553);
/* 348:389 */     GL11.glEnable(3042);
/* 349:390 */     GL11.glDisable(3008);
/* 350:391 */     GL11.glBlendFunc(770, 771);
/* 351:392 */     GL11.glShadeModel(7425);
/* 352:393 */     Tessellator tessellator = Tessellator.instance;
/* 353:394 */     tessellator.startDrawing(1);
/* 354:395 */     tessellator.setColorOpaque_I(col);
/* 355:396 */     tessellator.addVertex(x1, y1, this.zLevel);
/* 356:397 */     tessellator.addVertex(x2, y2, this.zLevel);
/* 357:398 */     tessellator.draw();
/* 358:399 */     GL11.glShadeModel(7424);
/* 359:400 */     GL11.glDisable(3042);
/* 360:401 */     GL11.glEnable(3008);
/* 361:402 */     GL11.glEnable(3553);
/* 362:    */   }
/* 363:    */   
/* 364:    */   protected boolean func_146978_c(int par1, int par2, int par3, int par4, int par5, int par6)
/* 365:    */   {
/* 366:410 */     int k1 = this.guiTop;
/* 367:411 */     int l1 = this.guiLeft;
/* 368:412 */     par5 -= k1;
/* 369:413 */     par6 -= l1;
/* 370:414 */     return (par5 >= par1 - 1) && (par5 < par1 + par3 + 1) && (par6 >= par2 - 1) && (par6 < par2 + par4 + 1);
/* 371:    */   }
/* 372:    */   
/* 373:    */   public static class VillagerSorter
/* 374:    */     implements Comparator
/* 375:    */   {
/* 376:    */     public int intCompare(int a, int b)
/* 377:    */     {
/* 378:419 */       if (a == b) {
/* 379:420 */         return 0;
/* 380:    */       }
/* 381:421 */       if (a > b) {
/* 382:422 */         return 1;
/* 383:    */       }
/* 384:424 */       return -1;
/* 385:    */     }
/* 386:    */     
/* 387:    */     public int itemCompare(Item a, Item b)
/* 388:    */     {
/* 389:429 */       return a.getUnlocalizedName().compareTo(b.getUnlocalizedName());
/* 390:    */     }
/* 391:    */     
/* 392:    */     public int compare(Object arg0, Object arg1)
/* 393:    */     {
/* 394:434 */       int i = intCompare(GuiTradingPost.getProf(((Object[])(Object[])arg0)[0]), GuiTradingPost.getProf(((Object[])(Object[])arg0)[0]));
/* 395:436 */       if (i == 0)
/* 396:    */       {
/* 397:437 */         MerchantRecipe m1 = (MerchantRecipe)((Object[])(Object[])arg0)[1];
/* 398:438 */         MerchantRecipe m2 = (MerchantRecipe)((Object[])(Object[])arg1)[1];
/* 399:439 */         int i2 = itemCompare(m1.getItemToBuy().getItem(), m2.getItemToBuy().getItem());
/* 400:441 */         if (i2 == 0)
/* 401:    */         {
/* 402:442 */           int i3 = intCompare(m1.getItemToBuy().stackSize, m2.getItemToBuy().stackSize);
/* 403:444 */           if (i3 == 0)
/* 404:    */           {
/* 405:445 */             int i4 = itemCompare(m1.getItemToSell().getItem(), m2.getItemToSell().getItem());
/* 406:447 */             if (i4 == 0)
/* 407:    */             {
/* 408:448 */               int i5 = intCompare(m1.getItemToSell().stackSize, m2.getItemToSell().stackSize);
/* 409:450 */               if (i5 == 0) {
/* 410:451 */                 return intCompare(m1.writeToTags().hashCode(), m2.writeToTags().hashCode());
/* 411:    */               }
/* 412:453 */               return i5;
/* 413:    */             }
/* 414:456 */             return i4;
/* 415:    */           }
/* 416:459 */           return i3;
/* 417:    */         }
/* 418:462 */         return i2;
/* 419:    */       }
/* 420:465 */       return i;
/* 421:    */     }
/* 422:    */   }
/* 423:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.GuiTradingPost
 * JD-Core Version:    0.7.0.1
 */