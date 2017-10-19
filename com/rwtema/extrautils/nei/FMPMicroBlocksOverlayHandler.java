/*   1:    */ package com.rwtema.extrautils.nei;
/*   2:    */ 
/*   3:    */ import codechicken.lib.inventory.InventoryUtils;
/*   4:    */ import codechicken.microblock.Saw;
/*   5:    */ import codechicken.nei.FastTransferManager;
/*   6:    */ import codechicken.nei.PositionedStack;
/*   7:    */ import codechicken.nei.api.IOverlayHandler;
/*   8:    */ import codechicken.nei.recipe.IRecipeHandler;
/*   9:    */ import cpw.mods.fml.relauncher.Side;
/*  10:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.HashSet;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.LinkedList;
/*  16:    */ import java.util.List;
/*  17:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  18:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  19:    */ import net.minecraft.inventory.Container;
/*  20:    */ import net.minecraft.inventory.Slot;
/*  21:    */ import net.minecraft.item.ItemStack;
/*  22:    */ 
/*  23:    */ @SideOnly(Side.CLIENT)
/*  24:    */ public class FMPMicroBlocksOverlayHandler
/*  25:    */   implements IOverlayHandler
/*  26:    */ {
/*  27:    */   int offsetx;
/*  28:    */   int offsety;
/*  29:    */   
/*  30:    */   public static class DistributedIngred
/*  31:    */   {
/*  32:    */     public boolean isSaw;
/*  33:    */     public ItemStack stack;
/*  34:    */     public int invAmount;
/*  35:    */     public int distributed;
/*  36:    */     public int numSlots;
/*  37:    */     public int recipeAmount;
/*  38:    */     
/*  39:    */     public DistributedIngred(ItemStack item)
/*  40:    */     {
/*  41: 22 */       this.stack = InventoryUtils.copyStack(item, 1);
/*  42: 23 */       this.isSaw = FMPMicroBlocksOverlayHandler.isSaw(item);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static class IngredientDistribution
/*  47:    */   {
/*  48:    */     public FMPMicroBlocksOverlayHandler.DistributedIngred distrib;
/*  49:    */     public ItemStack permutation;
/*  50:    */     public Slot[] slots;
/*  51:    */     
/*  52:    */     public IngredientDistribution(FMPMicroBlocksOverlayHandler.DistributedIngred distrib, ItemStack permutation)
/*  53:    */     {
/*  54: 37 */       this.distrib = distrib;
/*  55: 38 */       this.permutation = permutation;
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public FMPMicroBlocksOverlayHandler(int x, int y)
/*  60:    */   {
/*  61: 47 */     this.offsetx = x;
/*  62: 48 */     this.offsety = y;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public FMPMicroBlocksOverlayHandler()
/*  66:    */   {
/*  67: 52 */     this(5, 11);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void overlayRecipe(GuiContainer gui, IRecipeHandler recipe, int recipeIndex, boolean shift)
/*  71:    */   {
/*  72: 60 */     List<PositionedStack> ingredients = recipe.getIngredientStacks(recipeIndex);
/*  73: 61 */     List<DistributedIngred> ingredStacks = getPermutationIngredients(ingredients);
/*  74: 63 */     if (!clearIngredients(gui, ingredients)) {
/*  75: 64 */       return;
/*  76:    */     }
/*  77: 66 */     findInventoryQuantities(gui, ingredStacks);
/*  78:    */     
/*  79: 68 */     List<IngredientDistribution> assignedIngredients = assignIngredients(ingredients, ingredStacks);
/*  80: 69 */     if (assignedIngredients == null) {
/*  81: 70 */       return;
/*  82:    */     }
/*  83: 72 */     assignIngredSlots(gui, ingredients, assignedIngredients);
/*  84: 73 */     int quantity = calculateRecipeQuantity(assignedIngredients);
/*  85: 75 */     if (quantity != 0) {
/*  86: 76 */       moveIngredients(gui, assignedIngredients, quantity);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   private boolean clearIngredients(GuiContainer gui, List<PositionedStack> ingreds)
/*  91:    */   {
/*  92: 81 */     for (Iterator i$ = ingreds.iterator(); i$.hasNext();)
/*  93:    */     {
/*  94: 81 */       pstack = (PositionedStack)i$.next();
/*  95: 82 */       for (Slot slot : gui.field_147002_h.inventorySlots) {
/*  96: 83 */         if ((slot.xDisplayPosition == pstack.relx + this.offsetx) && (slot.yDisplayPosition == pstack.rely + this.offsety)) {
/*  97: 84 */           if (slot.getHasStack())
/*  98:    */           {
/*  99: 87 */             FastTransferManager.clickSlot(gui, slot.slotNumber, 0, 1);
/* 100: 88 */             if (slot.getHasStack()) {
/* 101: 89 */               return false;
/* 102:    */             }
/* 103:    */           }
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:    */     PositionedStack pstack;
/* 108: 92 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void moveIngredients(GuiContainer gui, List<IngredientDistribution> assignedIngredients, int quantity)
/* 112:    */   {
/* 113: 97 */     for (Iterator i$ = assignedIngredients.iterator(); i$.hasNext();)
/* 114:    */     {
/* 115: 97 */       distrib = (IngredientDistribution)i$.next();
/* 116: 98 */       pstack = distrib.permutation;
/* 117:    */       
/* 118:100 */       transferCap = quantity * pstack.stackSize;
/* 119:101 */       transferred = 0;
/* 120:103 */       if (distrib.distrib.isSaw) {
/* 121:104 */         transferCap = 1;
/* 122:    */       }
/* 123:106 */       destSlotIndex = 0;
/* 124:107 */       dest = distrib.slots[0];
/* 125:108 */       slotTransferred = 0;
/* 126:109 */       slotTransferCap = pstack.getMaxStackSize();
/* 127:111 */       for (Slot slot : gui.field_147002_h.inventorySlots) {
/* 128:112 */         if ((slot.getHasStack()) && ((slot.inventory instanceof InventoryPlayer)))
/* 129:    */         {
/* 130:115 */           ItemStack stack = slot.getStack();
/* 131:117 */           if (distrib.distrib.isSaw ? 
/* 132:118 */             sameItemStack(stack, pstack) : 
/* 133:    */             
/* 134:120 */             InventoryUtils.canStack(stack, pstack))
/* 135:    */           {
/* 136:124 */             FastTransferManager.clickSlot(gui, slot.slotNumber);
/* 137:125 */             int amount = Math.min(transferCap - transferred, stack.stackSize);
/* 138:126 */             for (int c = 0; c < amount; c++)
/* 139:    */             {
/* 140:127 */               FastTransferManager.clickSlot(gui, dest.slotNumber, 1);
/* 141:128 */               transferred++;
/* 142:129 */               slotTransferred++;
/* 143:130 */               if (slotTransferred >= slotTransferCap)
/* 144:    */               {
/* 145:131 */                 destSlotIndex++;
/* 146:132 */                 if (destSlotIndex == distrib.slots.length)
/* 147:    */                 {
/* 148:133 */                   dest = null;
/* 149:134 */                   break;
/* 150:    */                 }
/* 151:136 */                 dest = distrib.slots[destSlotIndex];
/* 152:137 */                 slotTransferred = 0;
/* 153:    */               }
/* 154:    */             }
/* 155:140 */             FastTransferManager.clickSlot(gui, slot.slotNumber);
/* 156:141 */             if ((transferred >= transferCap) || (dest == null)) {
/* 157:    */               break;
/* 158:    */             }
/* 159:    */           }
/* 160:    */         }
/* 161:    */       }
/* 162:    */     }
/* 163:    */     IngredientDistribution distrib;
/* 164:    */     ItemStack pstack;
/* 165:    */     int transferCap;
/* 166:    */     int transferred;
/* 167:    */     int destSlotIndex;
/* 168:    */     Slot dest;
/* 169:    */     int slotTransferred;
/* 170:    */     int slotTransferCap;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static boolean sameItemStack(ItemStack stack1, ItemStack stack2)
/* 174:    */   {
/* 175:148 */     return (stack1 == null) || (stack2 == null) || ((stack1.getItem() == stack2.getItem()) && ((!stack2.getHasSubtypes()) || (stack2.getItemDamage() == stack1.getItemDamage())) && (ItemStack.areItemStackTagsEqual(stack2, stack1)));
/* 176:    */   }
/* 177:    */   
/* 178:    */   private int calculateRecipeQuantity(List<IngredientDistribution> assignedIngredients)
/* 179:    */   {
/* 180:155 */     int quantity = 2147483647;
/* 181:156 */     for (IngredientDistribution distrib : assignedIngredients)
/* 182:    */     {
/* 183:157 */       DistributedIngred istack = distrib.distrib;
/* 184:158 */       if (!distrib.distrib.isSaw)
/* 185:    */       {
/* 186:161 */         if (istack.numSlots == 0) {
/* 187:162 */           return 0;
/* 188:    */         }
/* 189:164 */         int allSlots = istack.invAmount;
/* 190:165 */         if (allSlots / istack.numSlots > istack.stack.getMaxStackSize()) {
/* 191:166 */           allSlots = istack.numSlots * istack.stack.getMaxStackSize();
/* 192:    */         }
/* 193:168 */         quantity = Math.min(quantity, allSlots / istack.distributed);
/* 194:    */       }
/* 195:    */     }
/* 196:171 */     return quantity;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private Slot[][] assignIngredSlots(GuiContainer gui, List<PositionedStack> ingredients, List<IngredientDistribution> assignedIngredients)
/* 200:    */   {
/* 201:175 */     Slot[][] recipeSlots = mapIngredSlots(gui, ingredients);
/* 202:    */     
/* 203:177 */     HashMap<Slot, Integer> distribution = new HashMap();
/* 204:178 */     for (int i = 0; i < recipeSlots.length; i++) {
/* 205:179 */       for (Slot slot : recipeSlots[i]) {
/* 206:180 */         if (!distribution.containsKey(slot)) {
/* 207:181 */           distribution.put(slot, Integer.valueOf(-1));
/* 208:    */         }
/* 209:    */       }
/* 210:    */     }
/* 211:183 */     HashSet<Slot> avaliableSlots = new HashSet(distribution.keySet());
/* 212:184 */     HashSet<Integer> remainingIngreds = new HashSet();
/* 213:185 */     ArrayList<LinkedList<Slot>> assignedSlots = new ArrayList();
/* 214:186 */     for (int i = 0; i < ingredients.size(); i++)
/* 215:    */     {
/* 216:187 */       remainingIngreds.add(Integer.valueOf(i));
/* 217:188 */       assignedSlots.add(new LinkedList());
/* 218:    */     }
/* 219:    */     Iterator<Integer> iterator;
/* 220:191 */     while ((avaliableSlots.size() > 0) && (remainingIngreds.size() > 0)) {
/* 221:192 */       for (iterator = remainingIngreds.iterator(); iterator.hasNext();)
/* 222:    */       {
/* 223:193 */         int i = ((Integer)iterator.next()).intValue();
/* 224:194 */         boolean assigned = false;
/* 225:195 */         DistributedIngred istack = ((IngredientDistribution)assignedIngredients.get(i)).distrib;
/* 226:197 */         for (Slot slot : recipeSlots[i]) {
/* 227:198 */           if (avaliableSlots.contains(slot))
/* 228:    */           {
/* 229:199 */             avaliableSlots.remove(slot);
/* 230:200 */             if (!slot.getHasStack())
/* 231:    */             {
/* 232:203 */               istack.numSlots += 1;
/* 233:204 */               ((LinkedList)assignedSlots.get(i)).add(slot);
/* 234:205 */               assigned = true;
/* 235:206 */               break;
/* 236:    */             }
/* 237:    */           }
/* 238:    */         }
/* 239:210 */         if ((!assigned) || (istack.numSlots * istack.stack.getMaxStackSize() >= istack.invAmount)) {
/* 240:211 */           iterator.remove();
/* 241:    */         }
/* 242:    */       }
/* 243:    */     }
/* 244:215 */     for (int i = 0; i < ingredients.size(); i++) {
/* 245:216 */       ((IngredientDistribution)assignedIngredients.get(i)).slots = ((Slot[])((LinkedList)assignedSlots.get(i)).toArray(new Slot[0]));
/* 246:    */     }
/* 247:217 */     return recipeSlots;
/* 248:    */   }
/* 249:    */   
/* 250:    */   private List<IngredientDistribution> assignIngredients(List<PositionedStack> ingredients, List<DistributedIngred> ingredStacks)
/* 251:    */   {
/* 252:221 */     ArrayList<IngredientDistribution> assignedIngredients = new ArrayList();
/* 253:222 */     for (PositionedStack posstack : ingredients)
/* 254:    */     {
/* 255:224 */       DistributedIngred biggestIngred = null;
/* 256:225 */       ItemStack permutation = null;
/* 257:226 */       int biggestSize = 0;
/* 258:227 */       for (ItemStack pstack : posstack.items)
/* 259:    */       {
/* 260:228 */         boolean isSaw = isSaw(pstack);
/* 261:229 */         for (int j = 0; j < ingredStacks.size(); j++)
/* 262:    */         {
/* 263:230 */           DistributedIngred istack = (DistributedIngred)ingredStacks.get(j);
/* 264:232 */           if (isSaw == istack.isSaw) {
/* 265:235 */             if (isSaw)
/* 266:    */             {
/* 267:236 */               if ((sameSaw(pstack, istack.stack)) && (istack.invAmount - istack.distributed >= pstack.stackSize))
/* 268:    */               {
/* 269:239 */                 biggestSize = 1;
/* 270:240 */                 biggestIngred = istack;
/* 271:241 */                 permutation = pstack;
/* 272:242 */                 break;
/* 273:    */               }
/* 274:    */             }
/* 275:244 */             else if ((InventoryUtils.canStack(pstack, istack.stack)) && (istack.invAmount - istack.distributed >= pstack.stackSize))
/* 276:    */             {
/* 277:247 */               int relsize = (istack.invAmount - istack.invAmount / istack.recipeAmount * istack.distributed) / pstack.stackSize;
/* 278:248 */               if (relsize > biggestSize)
/* 279:    */               {
/* 280:249 */                 biggestSize = relsize;
/* 281:250 */                 biggestIngred = istack;
/* 282:251 */                 permutation = pstack;
/* 283:252 */                 break;
/* 284:    */               }
/* 285:    */             }
/* 286:    */           }
/* 287:    */         }
/* 288:    */       }
/* 289:258 */       if (biggestIngred == null) {
/* 290:259 */         return null;
/* 291:    */       }
/* 292:261 */       biggestIngred.distributed += permutation.stackSize;
/* 293:262 */       assignedIngredients.add(new IngredientDistribution(biggestIngred, permutation));
/* 294:    */     }
/* 295:265 */     return assignedIngredients;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public static boolean isSaw(ItemStack a)
/* 299:    */   {
/* 300:269 */     return (!a.isStackable()) && ((a.getItem() instanceof Saw));
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean sameSaw(ItemStack a, ItemStack b)
/* 304:    */   {
/* 305:273 */     return (a.getItem() == b.getItem()) && ((a.getItem() instanceof Saw)) && (((Saw)a.getItem()).getCuttingStrength(a) == ((Saw)a.getItem()).getCuttingStrength(b));
/* 306:    */   }
/* 307:    */   
/* 308:    */   private void findInventoryQuantities(GuiContainer gui, List<DistributedIngred> ingredStacks)
/* 309:    */   {
/* 310:278 */     for (Slot slot : gui.field_147002_h.inventorySlots) {
/* 311:280 */       if ((slot.getHasStack()) && ((slot.inventory instanceof InventoryPlayer)))
/* 312:    */       {
/* 313:281 */         ItemStack pstack = slot.getStack();
/* 314:282 */         DistributedIngred istack = findIngred(ingredStacks, pstack);
/* 315:283 */         if (istack != null) {
/* 316:284 */           istack.invAmount += pstack.stackSize;
/* 317:    */         }
/* 318:    */       }
/* 319:    */     }
/* 320:    */   }
/* 321:    */   
/* 322:    */   private List<DistributedIngred> getPermutationIngredients(List<PositionedStack> ingredients)
/* 323:    */   {
/* 324:290 */     ArrayList<DistributedIngred> ingredStacks = new ArrayList();
/* 325:291 */     for (PositionedStack posstack : ingredients) {
/* 326:293 */       for (ItemStack pstack : posstack.items)
/* 327:    */       {
/* 328:294 */         DistributedIngred istack = findIngred(ingredStacks, pstack);
/* 329:295 */         if (istack == null) {
/* 330:296 */           ingredStacks.add(istack = new DistributedIngred(pstack));
/* 331:    */         }
/* 332:297 */         istack.recipeAmount += pstack.stackSize;
/* 333:    */       }
/* 334:    */     }
/* 335:300 */     return ingredStacks;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public Slot[][] mapIngredSlots(GuiContainer gui, List<PositionedStack> ingredients)
/* 339:    */   {
/* 340:305 */     Slot[][] recipeSlotList = new Slot[ingredients.size()][];
/* 341:306 */     for (int i = 0; i < ingredients.size(); i++)
/* 342:    */     {
/* 343:308 */       LinkedList<Slot> recipeSlots = new LinkedList();
/* 344:309 */       PositionedStack pstack = (PositionedStack)ingredients.get(i);
/* 345:310 */       for (Slot slot : gui.field_147002_h.inventorySlots) {
/* 346:311 */         if ((slot.xDisplayPosition == pstack.relx + this.offsetx) && (slot.yDisplayPosition == pstack.rely + this.offsety))
/* 347:    */         {
/* 348:312 */           recipeSlots.add(slot);
/* 349:313 */           break;
/* 350:    */         }
/* 351:    */       }
/* 352:316 */       recipeSlotList[i] = ((Slot[])recipeSlots.toArray(new Slot[0]));
/* 353:    */     }
/* 354:318 */     return recipeSlotList;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public DistributedIngred findIngred(List<DistributedIngred> ingredStacks, ItemStack pstack)
/* 358:    */   {
/* 359:322 */     for (DistributedIngred istack : ingredStacks) {
/* 360:323 */       if (istack.isSaw)
/* 361:    */       {
/* 362:324 */         if (sameSaw(pstack, istack.stack)) {
/* 363:325 */           return istack;
/* 364:    */         }
/* 365:    */       }
/* 366:326 */       else if (InventoryUtils.canStack(pstack, istack.stack)) {
/* 367:327 */         return istack;
/* 368:    */       }
/* 369:    */     }
/* 370:329 */     return null;
/* 371:    */   }
/* 372:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.FMPMicroBlocksOverlayHandler
 * JD-Core Version:    0.7.0.1
 */