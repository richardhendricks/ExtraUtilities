/*   1:    */ package com.rwtema.extrautils.nei;
/*   2:    */ 
/*   3:    */ import codechicken.lib.gui.GuiDraw;
/*   4:    */ import codechicken.nei.PositionedStack;
/*   5:    */ import codechicken.nei.api.IOverlayHandler;
/*   6:    */ import codechicken.nei.api.IRecipeOverlayRenderer;
/*   7:    */ import codechicken.nei.recipe.GuiCraftingRecipe;
/*   8:    */ import codechicken.nei.recipe.GuiRecipe;
/*   9:    */ import codechicken.nei.recipe.GuiUsageRecipe;
/*  10:    */ import codechicken.nei.recipe.ICraftingHandler;
/*  11:    */ import codechicken.nei.recipe.IUsageHandler;
/*  12:    */ import cpw.mods.fml.common.Loader;
/*  13:    */ import cpw.mods.fml.common.ModContainer;
/*  14:    */ import cpw.mods.fml.common.ModMetadata;
/*  15:    */ import cpw.mods.fml.relauncher.Side;
/*  16:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import net.minecraft.client.Minecraft;
/*  21:    */ import net.minecraft.client.gui.FontRenderer;
/*  22:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  23:    */ import net.minecraft.inventory.Container;
/*  24:    */ import net.minecraft.item.Item;
/*  25:    */ import net.minecraft.item.ItemStack;
/*  26:    */ import net.minecraft.util.RegistryNamespaced;
/*  27:    */ import net.minecraft.util.StatCollector;
/*  28:    */ 
/*  29:    */ @SideOnly(Side.CLIENT)
/*  30:    */ public class InfoHandler
/*  31:    */   implements IUsageHandler, ICraftingHandler
/*  32:    */ {
/*  33: 27 */   public static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
/*  34: 28 */   public static int color = -12566464;
/*  35:    */   ItemStack displayItem;
/*  36: 31 */   boolean precise = false;
/*  37:    */   String id;
/*  38:    */   String name;
/*  39:    */   String[] info;
/*  40:    */   
/*  41:    */   public InfoHandler()
/*  42:    */   {
/*  43: 37 */     this.displayItem = null;
/*  44:    */   }
/*  45:    */   
/*  46: 40 */   public boolean checkedOrder = false;
/*  47:    */   
/*  48:    */   public boolean checkOrder()
/*  49:    */   {
/*  50: 43 */     if (this.checkedOrder) {
/*  51: 44 */       return false;
/*  52:    */     }
/*  53: 45 */     this.checkedOrder = true;
/*  54: 46 */     return changeOrder(GuiUsageRecipe.usagehandlers) | changeOrder(GuiCraftingRecipe.craftinghandlers);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean changeOrder(ArrayList list)
/*  58:    */   {
/*  59: 50 */     int j = -1;
/*  60: 51 */     for (int i = 0; i < list.size() - 1; i++) {
/*  61: 52 */       if (list.get(i).getClass() == getClass())
/*  62:    */       {
/*  63: 53 */         j = i;
/*  64: 54 */         break;
/*  65:    */       }
/*  66:    */     }
/*  67: 57 */     if (j >= 0) {
/*  68: 58 */       list.add(list.remove(j));
/*  69:    */     }
/*  70: 60 */     return false;
/*  71:    */   }
/*  72:    */   
/*  73: 64 */   int noLinesPerPage = 12;
/*  74: 65 */   public final String suffix = ".documentation";
/*  75:    */   
/*  76:    */   public InfoHandler(ItemStack item)
/*  77:    */   {
/*  78: 68 */     if ((StatCollector.canTranslate(item.getUnlocalizedName() + ".documentation")) || (StatCollector.canTranslate(item.getUnlocalizedName() + ".documentation" + ".0")))
/*  79:    */     {
/*  80: 71 */       this.id = item.getUnlocalizedName();
/*  81: 72 */       this.name = StatCollector.translateToLocal(item.getUnlocalizedName());
/*  82: 73 */       this.precise = true;
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86: 75 */       this.id = item.getItem().getUnlocalizedName();
/*  87: 76 */       this.name = StatCollector.translateToLocal(item.getItem().getUnlocalizedName());
/*  88: 77 */       this.precise = false;
/*  89:    */     }
/*  90: 80 */     if (StatCollector.canTranslate(this.id + ".documentation"))
/*  91:    */     {
/*  92: 81 */       List<String> strings = splitString(StatCollector.translateToLocal(this.id + ".documentation"));
/*  93: 82 */       this.info = ((String[])strings.toArray(new String[strings.size()]));
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97: 84 */       ArrayList<String> list = new ArrayList();
/*  98: 85 */       int i = 0;
/*  99: 86 */       while (StatCollector.canTranslate(this.id + ".documentation" + "." + i))
/* 100:    */       {
/* 101: 87 */         String a = StatCollector.translateToLocal(this.id + ".documentation" + "." + i);
/* 102: 88 */         list.addAll(splitString(a));
/* 103: 89 */         i++;
/* 104:    */       }
/* 105: 92 */       this.info = ((String[])list.toArray(new String[list.size()]));
/* 106:    */     }
/* 107: 95 */     this.displayItem = item.copy();
/* 108: 96 */     this.displayItem.stackSize = 1;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<String> splitString(String a)
/* 112:    */   {
/* 113:100 */     ArrayList<String> list = new ArrayList();
/* 114:101 */     List b = fontRenderer.listFormattedStringToWidth(a, getWidth() - 8);
/* 115:102 */     if (b.size() < this.noLinesPerPage)
/* 116:    */     {
/* 117:103 */       list.add(a);
/* 118:    */     }
/* 119:    */     else
/* 120:    */     {
/* 121:105 */       String c = "";
/* 122:106 */       for (int j = 0; j < b.size(); j++)
/* 123:    */       {
/* 124:107 */         c = c + b.get(j) + " ";
/* 125:109 */         if ((j > 0) && (j % this.noLinesPerPage == 0))
/* 126:    */         {
/* 127:110 */           String d = c.trim();
/* 128:111 */           list.add(d);
/* 129:112 */           c = "";
/* 130:    */         }
/* 131:    */       }
/* 132:116 */       c = c.trim();
/* 133:117 */       if (!"".equals(c)) {
/* 134:118 */         list.add(c);
/* 135:    */       }
/* 136:    */     }
/* 137:121 */     return list;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getRecipeName()
/* 141:    */   {
/* 142:127 */     if (this.displayItem == null) {
/* 143:128 */       return "Documentation";
/* 144:    */     }
/* 145:130 */     String s = Item.itemRegistry.getNameForObject(this.displayItem.getItem());
/* 146:    */     
/* 147:132 */     String modid = s.split(":")[0];
/* 148:134 */     if ("minecraft".equals(modid)) {
/* 149:135 */       return "Minecraft";
/* 150:    */     }
/* 151:137 */     ModContainer selectedMod = (ModContainer)Loader.instance().getIndexedModList().get(modid);
/* 152:139 */     if (selectedMod == null) {
/* 153:140 */       return modid;
/* 154:    */     }
/* 155:142 */     if (!selectedMod.getMetadata().autogenerated) {
/* 156:143 */       return selectedMod.getMetadata().name;
/* 157:    */     }
/* 158:145 */     return selectedMod.getName();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int numRecipes()
/* 162:    */   {
/* 163:151 */     return (this.displayItem == null) || (this.info == null) ? 0 : this.info.length;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void drawBackground(int recipe) {}
/* 167:    */   
/* 168:    */   public int getWidth()
/* 169:    */   {
/* 170:159 */     return 166;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public PositionedStack getResultStack(int recipe)
/* 174:    */   {
/* 175:164 */     return new PositionedStack(this.displayItem, getWidth() / 2 - 9, 0, false);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void drawForeground(int recipe)
/* 179:    */   {
/* 180:169 */     List text = fontRenderer.listFormattedStringToWidth(this.info[recipe], getWidth() - 8);
/* 181:171 */     for (int i = 0; i < text.size(); i++)
/* 182:    */     {
/* 183:172 */       String t = (String)text.get(i);
/* 184:173 */       GuiDraw.drawString(t, getWidth() / 2 - GuiDraw.getStringWidth(t) / 2, 18 + i * 8, color, false);
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<PositionedStack> getIngredientStacks(int recipe)
/* 189:    */   {
/* 190:179 */     return new ArrayList();
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<PositionedStack> getOtherStacks(int recipetype)
/* 194:    */   {
/* 195:184 */     return new ArrayList();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void onUpdate() {}
/* 199:    */   
/* 200:    */   public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
/* 201:    */   {
/* 202:193 */     return false;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe)
/* 206:    */   {
/* 207:198 */     return null;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe)
/* 211:    */   {
/* 212:203 */     return null;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public int recipiesPerPage()
/* 216:    */   {
/* 217:208 */     return 1;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe)
/* 221:    */   {
/* 222:213 */     return currenttip;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe)
/* 226:    */   {
/* 227:218 */     return currenttip;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe)
/* 231:    */   {
/* 232:223 */     return false;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public boolean mouseClicked(GuiRecipe gui, int button, int recipe)
/* 236:    */   {
/* 237:228 */     return false;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean isValidItem(ItemStack item)
/* 241:    */   {
/* 242:232 */     return (StatCollector.canTranslate(item.getUnlocalizedName() + ".documentation")) || (StatCollector.canTranslate(item.getItem().getUnlocalizedName() + ".documentation")) || (StatCollector.canTranslate(item.getUnlocalizedName() + ".documentation" + ".0")) || (StatCollector.canTranslate(item.getItem().getUnlocalizedName() + ".documentation" + ".0"));
/* 243:    */   }
/* 244:    */   
/* 245:    */   public IUsageHandler getUsageHandler(String inputId, Object... ingredients)
/* 246:    */   {
/* 247:241 */     if (!inputId.equals("item")) {
/* 248:242 */       return this;
/* 249:    */     }
/* 250:245 */     for (Object ingredient : ingredients) {
/* 251:246 */       if (((ingredient instanceof ItemStack)) && 
/* 252:247 */         (isValidItem((ItemStack)ingredient))) {
/* 253:248 */         return new InfoHandler((ItemStack)ingredient);
/* 254:    */       }
/* 255:    */     }
/* 256:252 */     return this;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public ICraftingHandler getRecipeHandler(String outputId, Object... results)
/* 260:    */   {
/* 261:257 */     if (!outputId.equals("item")) {
/* 262:258 */       return this;
/* 263:    */     }
/* 264:262 */     for (Object result : results) {
/* 265:263 */       if (((result instanceof ItemStack)) && 
/* 266:264 */         (isValidItem((ItemStack)result))) {
/* 267:265 */         return new InfoHandler((ItemStack)result);
/* 268:    */       }
/* 269:    */     }
/* 270:268 */     return this;
/* 271:    */   }
/* 272:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.InfoHandler
 * JD-Core Version:    0.7.0.1
 */