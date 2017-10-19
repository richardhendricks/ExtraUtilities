/*   1:    */ package com.rwtema.extrautils.nei;
/*   2:    */ 
/*   3:    */ import codechicken.microblock.MicroMaterialRegistry;
/*   4:    */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*   5:    */ import codechicken.microblock.MicroRecipe;
/*   6:    */ import codechicken.microblock.Saw;
/*   7:    */ import codechicken.nei.ItemList;
/*   8:    */ import codechicken.nei.NEIServerUtils;
/*   9:    */ import codechicken.nei.PositionedStack;
/*  10:    */ import codechicken.nei.api.IOverlayHandler;
/*  11:    */ import codechicken.nei.recipe.RecipeInfo;
/*  12:    */ import codechicken.nei.recipe.ShapedRecipeHandler;
/*  13:    */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*  14:    */ import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
/*  15:    */ import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
/*  16:    */ import com.google.common.collect.ListMultimap;
/*  17:    */ import com.rwtema.extrautils.multipart.FMPBase;
/*  18:    */ import cpw.mods.fml.relauncher.Side;
/*  19:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  20:    */ import java.awt.Rectangle;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.HashSet;
/*  23:    */ import java.util.LinkedList;
/*  24:    */ import java.util.List;
/*  25:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  26:    */ import net.minecraft.inventory.Container;
/*  27:    */ import net.minecraft.item.Item;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.item.crafting.ShapedRecipes;
/*  30:    */ import net.minecraft.nbt.NBTTagCompound;
/*  31:    */ import scala.Tuple2;
/*  32:    */ 
/*  33:    */ @SideOnly(Side.CLIENT)
/*  34:    */ public class FMPMicroBlocksHandler
/*  35:    */   extends ShapedRecipeHandler
/*  36:    */ {
/*  37: 29 */   public static String[] currentMaterials = null;
/*  38: 30 */   public static ItemStack[] currentBlocks = null;
/*  39: 32 */   public static ArrayList<ShapedRecipes> recipes = null;
/*  40:    */   public String currentMaterial;
/*  41:    */   public ItemStack currentBlock;
/*  42:    */   public boolean scroll;
/*  43:    */   
/*  44:    */   public FMPMicroBlocksHandler()
/*  45:    */   {
/*  46: 34 */     this.currentMaterial = "";
/*  47: 35 */     this.currentBlock = null;
/*  48: 36 */     this.scroll = true;
/*  49:    */   }
/*  50:    */   
/*  51: 38 */   public static HashSet<ItemStack> sawList = null;
/*  52:    */   public static final String identifier = "microblocks";
/*  53:    */   
/*  54:    */   public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
/*  55:    */   {
/*  56: 44 */     if ((RecipeInfo.hasDefaultOverlay(gui, "microblocks")) || (RecipeInfo.hasOverlayHandler(gui, "microblocks"))) {
/*  57: 45 */       return true;
/*  58:    */     }
/*  59: 46 */     if ((isRecipe2x2(recipe)) && (RecipeInfo.hasDefaultOverlay(gui, "microblocks2x2"))) {
/*  60: 47 */       return true;
/*  61:    */     }
/*  62: 48 */     return super.hasOverlay(gui, container, recipe);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe)
/*  66:    */   {
/*  67: 53 */     IOverlayHandler handler = RecipeInfo.getOverlayHandler(gui, "microblocks");
/*  68: 54 */     if (handler != null) {
/*  69: 55 */       return handler;
/*  70:    */     }
/*  71: 57 */     if (isRecipe2x2(recipe))
/*  72:    */     {
/*  73: 58 */       handler = RecipeInfo.getOverlayHandler(gui, "microblocks2x2");
/*  74: 59 */       if (handler != null) {
/*  75: 60 */         return handler;
/*  76:    */       }
/*  77:    */     }
/*  78: 63 */     return super.getOverlayHandler(gui, recipe);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static HashSet<ItemStack> getSawList()
/*  82:    */   {
/*  83: 67 */     if (sawList == null)
/*  84:    */     {
/*  85: 68 */       sawList = new HashSet();
/*  86: 70 */       synchronized (ItemList.class)
/*  87:    */       {
/*  88: 71 */         for (Item item : ItemList.itemMap.keySet()) {
/*  89: 72 */           if ((item instanceof Saw)) {
/*  90: 73 */             for (ItemStack stack : ItemList.itemMap.get(item)) {
/*  91: 74 */               sawList.add(stack);
/*  92:    */             }
/*  93:    */           }
/*  94:    */         }
/*  95:    */       }
/*  96:    */     }
/*  97: 80 */     return sawList;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ItemStack[] getSaws()
/* 101:    */   {
/* 102: 84 */     if ((this.scroll) || ("".equals(this.currentMaterial)) || (this.currentBlock == null)) {
/* 103: 85 */       return (ItemStack[])getSawList().toArray(new ItemStack[0]);
/* 104:    */     }
/* 105: 88 */     int p = MicroMaterialRegistry.getMaterial(this.currentMaterial).getCutterStrength();
/* 106: 89 */     HashSet<ItemStack> s = new HashSet();
/* 107: 90 */     for (ItemStack saw : getSawList())
/* 108:    */     {
/* 109: 91 */       int sawStrength = ((Saw)saw.getItem()).getCuttingStrength(saw);
/* 110: 92 */       if ((sawStrength >= p) || (sawStrength == MicroMaterialRegistry.getMaxCuttingStrength())) {
/* 111: 93 */         s.add(saw);
/* 112:    */       }
/* 113:    */     }
/* 114: 96 */     return (ItemStack[])s.toArray(new ItemStack[s.size()]);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static ArrayList<ShapedRecipes> getCraftingRecipes()
/* 118:    */   {
/* 119:100 */     if ((recipes == null) || (recipes.size() == 0)) {
/* 120:101 */       recipes = FMPMicroBlockRecipeCreator.loadRecipes();
/* 121:    */     }
/* 122:104 */     return recipes;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void loadTransferRects()
/* 126:    */   {
/* 127:110 */     this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84, 23, 24, 18), "microblocks", new Object[0]));
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void loadCraftingRecipes(String outputId, Object... results)
/* 131:    */   {
/* 132:115 */     if (outputId.equals("microblocks"))
/* 133:    */     {
/* 134:116 */       for (ShapedRecipes irecipe : getCraftingRecipes())
/* 135:    */       {
/* 136:117 */         MicroblockCachedRecipe recipe = new MicroblockCachedRecipe(irecipe);
/* 137:118 */         recipe.computeVisuals();
/* 138:119 */         this.arecipes.add(recipe);
/* 139:    */       }
/* 140:122 */       this.scroll = true;
/* 141:123 */       this.currentMaterial = "";
/* 142:124 */       this.currentBlock = null;
/* 143:    */     }
/* 144:    */     else
/* 145:    */     {
/* 146:126 */       super.loadCraftingRecipes(outputId, results);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void loadCraftingRecipes(ItemStack result)
/* 151:    */   {
/* 152:131 */     if ((!result.hasTagCompound()) || ("".equals(result.getTagCompound().getString("mat")))) {
/* 153:132 */       return;
/* 154:    */     }
/* 155:134 */     MicroMaterialRegistry.IMicroMaterial m = MicroMaterialRegistry.getMaterial(result.getTagCompound().getString("mat"));
/* 156:136 */     if (m == null) {
/* 157:137 */       return;
/* 158:    */     }
/* 159:139 */     this.scroll = false;
/* 160:140 */     this.currentMaterial = result.getTagCompound().getString("mat");
/* 161:141 */     this.currentBlock = m.getItem().copy();
/* 162:143 */     for (ShapedRecipes irecipe : getCraftingRecipes()) {
/* 163:144 */       if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result))
/* 164:    */       {
/* 165:145 */         MicroblockCachedRecipe recipe = new MicroblockCachedRecipe(irecipe);
/* 166:146 */         recipe.computeVisuals();
/* 167:147 */         this.arecipes.add(recipe);
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void loadUsageRecipes(ItemStack ingredient)
/* 173:    */   {
/* 174:156 */     if ((ingredient.getItem() instanceof Saw))
/* 175:    */     {
/* 176:157 */       this.scroll = true;
/* 177:158 */       this.currentMaterial = "";
/* 178:159 */       this.currentBlock = null;
/* 179:    */     }
/* 180:162 */     else if ((!ingredient.hasTagCompound()) || ("".equals(ingredient.getTagCompound().getString("mat"))))
/* 181:    */     {
/* 182:163 */       int id = MicroRecipe.findMaterial(ingredient);
/* 183:164 */       if (id < 0) {
/* 184:165 */         return;
/* 185:    */       }
/* 186:166 */       MicroMaterialRegistry.IMicroMaterial m = MicroMaterialRegistry.getMaterial(id);
/* 187:168 */       if (m == null) {
/* 188:169 */         return;
/* 189:    */       }
/* 190:171 */       this.scroll = false;
/* 191:172 */       this.currentMaterial = MicroMaterialRegistry.materialName(id);
/* 192:173 */       this.currentBlock = m.getItem().copy();
/* 193:    */     }
/* 194:    */     else
/* 195:    */     {
/* 196:175 */       MicroMaterialRegistry.IMicroMaterial m = MicroMaterialRegistry.getMaterial(ingredient.getTagCompound().getString("mat"));
/* 197:177 */       if (m == null) {
/* 198:178 */         return;
/* 199:    */       }
/* 200:180 */       this.scroll = false;
/* 201:181 */       this.currentMaterial = ingredient.getTagCompound().getString("mat");
/* 202:182 */       this.currentBlock = m.getItem().copy();
/* 203:    */     }
/* 204:186 */     for (ShapedRecipes irecipe : getCraftingRecipes())
/* 205:    */     {
/* 206:187 */       MicroblockCachedRecipe recipe = new MicroblockCachedRecipe(irecipe);
/* 207:    */       
/* 208:189 */       recipe.computeVisuals();
/* 209:191 */       if (recipe.contains(recipe.ingredients, ingredient))
/* 210:    */       {
/* 211:192 */         recipe.setIngredientPermutation(recipe.ingredients, ingredient);
/* 212:193 */         this.arecipes.add(recipe);
/* 213:    */       }
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getGuiTexture()
/* 218:    */   {
/* 219:201 */     return "textures/gui/container/crafting_table.png";
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String getRecipeName()
/* 223:    */   {
/* 224:206 */     return "Microblock Crafting";
/* 225:    */   }
/* 226:    */   
/* 227:    */   public class MicroblockCachedRecipe
/* 228:    */     extends TemplateRecipeHandler.CachedRecipe
/* 229:    */   {
/* 230:    */     public ArrayList<PositionedStack> ingredients;
/* 231:    */     public MicroblockPositionedStack result;
/* 232:    */     
/* 233:    */     public MicroblockCachedRecipe(int width, int height, Object[] items, ItemStack out)
/* 234:    */     {
/* 235:213 */       super();
/* 236:214 */       this.result = new MicroblockPositionedStack(out, 119, 24);
/* 237:    */       
/* 238:216 */       this.ingredients = new ArrayList();
/* 239:217 */       setIngredients(width, height, items);
/* 240:    */     }
/* 241:    */     
/* 242:    */     public MicroblockCachedRecipe(ShapedRecipes irecipe)
/* 243:    */     {
/* 244:221 */       this(irecipe.recipeWidth, irecipe.recipeHeight, irecipe.recipeItems, irecipe.getRecipeOutput());
/* 245:    */     }
/* 246:    */     
/* 247:    */     public void setIngredients(int width, int height, Object[] items)
/* 248:    */     {
/* 249:225 */       for (int x = 0; x < width; x++) {
/* 250:226 */         for (int y = 0; y < height; y++) {
/* 251:227 */           if ((y * width + x <= items.length) && (items[(y * width + x)] != null))
/* 252:    */           {
/* 253:232 */             ItemStack item = (ItemStack)items[(y * width + x)];
/* 254:    */             PositionedStack stack;
/* 255:    */             PositionedStack stack;
/* 256:234 */             if (item.getItem() == FMPMicroBlockRecipeCreator.stone.getItem())
/* 257:    */             {
/* 258:235 */               stack = new MicroblockPositionedStack(item, 25 + x * 18, 6 + y * 18);
/* 259:    */             }
/* 260:    */             else
/* 261:    */             {
/* 262:    */               PositionedStack stack;
/* 263:236 */               if (item == FMPMicroBlockRecipeCreator.saw)
/* 264:    */               {
/* 265:237 */                 stack = new PositionedStack(FMPMicroBlocksHandler.this.getSaws(), 25 + x * 18, 6 + y * 18, false);
/* 266:    */               }
/* 267:    */               else
/* 268:    */               {
/* 269:    */                 PositionedStack stack;
/* 270:238 */                 if (item.getItem() == FMPBase.getMicroBlockItemId()) {
/* 271:239 */                   stack = new MicroblockPositionedStack((ItemStack)items[(y * width + x)], 25 + x * 18, 6 + y * 18);
/* 272:    */                 } else {
/* 273:241 */                   stack = new PositionedStack(items[(y * width + x)], 25 + x * 18, 6 + y * 18);
/* 274:    */                 }
/* 275:    */               }
/* 276:    */             }
/* 277:244 */             stack.setMaxSize(1);
/* 278:245 */             this.ingredients.add(stack);
/* 279:    */           }
/* 280:    */         }
/* 281:    */       }
/* 282:    */     }
/* 283:    */     
/* 284:    */     public void permMaterial()
/* 285:    */     {
/* 286:251 */       if (!FMPMicroBlocksHandler.this.scroll) {
/* 287:252 */         return;
/* 288:    */       }
/* 289:255 */       if (FMPMicroBlocksHandler.currentMaterials == null)
/* 290:    */       {
/* 291:256 */         FMPMicroBlocksHandler.currentMaterials = new String[MicroMaterialRegistry.getIdMap().length];
/* 292:258 */         for (int i = 0; i < MicroMaterialRegistry.getIdMap().length; i++) {
/* 293:259 */           FMPMicroBlocksHandler.currentMaterials[i] = ((String)MicroMaterialRegistry.getIdMap()[i]._1());
/* 294:    */         }
/* 295:    */       }
/* 296:262 */       if (FMPMicroBlocksHandler.currentBlocks == null)
/* 297:    */       {
/* 298:263 */         FMPMicroBlocksHandler.currentBlocks = new ItemStack[MicroMaterialRegistry.getIdMap().length];
/* 299:265 */         for (int i = 0; i < MicroMaterialRegistry.getIdMap().length; i++) {
/* 300:266 */           FMPMicroBlocksHandler.currentBlocks[i] = ((MicroMaterialRegistry.IMicroMaterial)MicroMaterialRegistry.getIdMap()[i]._2()).getItem().copy();
/* 301:    */         }
/* 302:    */       }
/* 303:270 */       FMPMicroBlocksHandler.this.currentMaterial = FMPMicroBlocksHandler.currentMaterials[(FMPMicroBlocksHandler.this.cycleticks / 20 % FMPMicroBlocksHandler.currentMaterials.length)];
/* 304:271 */       FMPMicroBlocksHandler.this.currentBlock = FMPMicroBlocksHandler.currentBlocks[(FMPMicroBlocksHandler.this.cycleticks / 20 % FMPMicroBlocksHandler.currentMaterials.length)];
/* 305:    */     }
/* 306:    */     
/* 307:    */     public List<PositionedStack> getIngredients()
/* 308:    */     {
/* 309:276 */       return getCycledIngredients(FMPMicroBlocksHandler.this.cycleticks / 20, this.ingredients);
/* 310:    */     }
/* 311:    */     
/* 312:    */     public void randomRenderPermutation(PositionedStack stack, long cycle)
/* 313:    */     {
/* 314:281 */       super.randomRenderPermutation(stack, cycle);
/* 315:    */     }
/* 316:    */     
/* 317:    */     public PositionedStack getResult()
/* 318:    */     {
/* 319:286 */       this.result.setPermutationToRender(0);
/* 320:287 */       return this.result;
/* 321:    */     }
/* 322:    */     
/* 323:    */     public void computeVisuals()
/* 324:    */     {
/* 325:292 */       for (PositionedStack p : this.ingredients) {
/* 326:293 */         p.generatePermutations();
/* 327:    */       }
/* 328:296 */       this.result.generatePermutations();
/* 329:    */     }
/* 330:    */     
/* 331:    */     public class MicroblockPositionedStack
/* 332:    */       extends PositionedStack
/* 333:    */     {
/* 334:301 */       boolean materialTag = false;
/* 335:    */       int stacksize;
/* 336:    */       
/* 337:    */       public MicroblockPositionedStack(ItemStack object, int x, int y)
/* 338:    */       {
/* 339:305 */         super(x, y, true);
/* 340:306 */         this.stacksize = object.stackSize;
/* 341:307 */         this.item = this.items[0].copy();
/* 342:308 */         this.materialTag = ((this.item.getItem() == FMPBase.getMicroBlockItemId()) && (this.item.getItemDamage() != 0) && (this.item.getItemDamage() != 8));
/* 343:309 */         setPermutationToRender(0);
/* 344:    */       }
/* 345:    */       
/* 346:    */       public void generatePermutations() {}
/* 347:    */       
/* 348:    */       public void setItem(ItemStack item)
/* 349:    */       {
/* 350:318 */         if (item != null)
/* 351:    */         {
/* 352:319 */           this.item = item.copy();
/* 353:320 */           this.items[0] = item.copy();
/* 354:    */         }
/* 355:    */         else
/* 356:    */         {
/* 357:322 */           this.item = null;
/* 358:    */         }
/* 359:    */       }
/* 360:    */       
/* 361:    */       public void setPermutationToRender(int index)
/* 362:    */       {
/* 363:328 */         if (this.item == null) {
/* 364:329 */           return;
/* 365:    */         }
/* 366:331 */         FMPMicroBlocksHandler.MicroblockCachedRecipe.this.permMaterial();
/* 367:333 */         if (this.materialTag)
/* 368:    */         {
/* 369:334 */           addMaterial();
/* 370:    */         }
/* 371:    */         else
/* 372:    */         {
/* 373:336 */           this.items[0] = FMPMicroBlocksHandler.this.currentBlock.copy();
/* 374:337 */           this.items[0].stackSize = this.stacksize;
/* 375:338 */           this.item = this.items[0];
/* 376:    */         }
/* 377:341 */         super.setPermutationToRender(0);
/* 378:    */       }
/* 379:    */       
/* 380:    */       public void addMaterial()
/* 381:    */       {
/* 382:345 */         NBTTagCompound tag = this.items[0].getTagCompound();
/* 383:347 */         if (tag == null) {
/* 384:348 */           tag = new NBTTagCompound();
/* 385:    */         }
/* 386:351 */         tag.setString("mat", FMPMicroBlocksHandler.this.currentMaterial);
/* 387:352 */         this.items[0].setTagCompound(tag);
/* 388:    */       }
/* 389:    */     }
/* 390:    */   }
/* 391:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.FMPMicroBlocksHandler
 * JD-Core Version:    0.7.0.1
 */