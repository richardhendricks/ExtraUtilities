/*   1:    */ package com.rwtema.extrautils.nei;
/*   2:    */ 
/*   3:    */ import codechicken.microblock.MicroMaterialRegistry;
/*   4:    */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*   5:    */ import codechicken.nei.NEIServerUtils;
/*   6:    */ import codechicken.nei.PositionedStack;
/*   7:    */ import codechicken.nei.recipe.ShapedRecipeHandler;
/*   8:    */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*   9:    */ import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
/*  10:    */ import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
/*  11:    */ import com.rwtema.extrautils.multipart.FMPBase;
/*  12:    */ import com.rwtema.extrautils.multipart.microblock.RecipeMicroBlocks;
/*  13:    */ import java.awt.Rectangle;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashSet;
/*  16:    */ import java.util.LinkedList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Set;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.item.crafting.CraftingManager;
/*  21:    */ import net.minecraft.item.crafting.IRecipe;
/*  22:    */ import net.minecraft.nbt.NBTTagCompound;
/*  23:    */ import scala.Tuple2;
/*  24:    */ 
/*  25:    */ public class MicroBlocksHandler
/*  26:    */   extends ShapedRecipeHandler
/*  27:    */ {
/*  28: 22 */   public static String[] currentMaterials = null;
/*  29: 23 */   public static ItemStack[] currentBlocks = null;
/*  30: 24 */   public static Set<RecipeMicroBlocks> recipes = null;
/*  31:    */   public String currentMaterial;
/*  32:    */   public ItemStack currentBlock;
/*  33:    */   public boolean scroll;
/*  34:    */   
/*  35:    */   public MicroBlocksHandler()
/*  36:    */   {
/*  37: 25 */     this.currentMaterial = "";
/*  38: 26 */     this.currentBlock = null;
/*  39: 27 */     this.scroll = true;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static Set<RecipeMicroBlocks> getCraftingRecipes()
/*  43:    */   {
/*  44: 30 */     if (recipes == null)
/*  45:    */     {
/*  46: 31 */       recipes = new HashSet();
/*  47: 32 */       List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
/*  48: 34 */       for (IRecipe irecipe : allrecipes) {
/*  49: 35 */         if ((irecipe instanceof RecipeMicroBlocks)) {
/*  50: 36 */           recipes.add((RecipeMicroBlocks)irecipe);
/*  51:    */         }
/*  52:    */       }
/*  53:    */     }
/*  54: 41 */     return recipes;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void loadTransferRects()
/*  58:    */   {
/*  59: 46 */     this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84, 23, 24, 18), "xu_microblocks_crafting", new Object[0]));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void loadCraftingRecipes(String outputId, Object... results)
/*  63:    */   {
/*  64: 51 */     if (outputId.equals("xu_microblocks_crafting"))
/*  65:    */     {
/*  66: 52 */       for (RecipeMicroBlocks irecipe : getCraftingRecipes())
/*  67:    */       {
/*  68: 53 */         MicroblockCachedRecipe recipe = new MicroblockCachedRecipe(irecipe);
/*  69: 54 */         recipe.computeVisuals();
/*  70: 55 */         this.arecipes.add(recipe);
/*  71:    */       }
/*  72: 58 */       this.scroll = true;
/*  73: 59 */       this.currentMaterial = "";
/*  74: 60 */       this.currentBlock = null;
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78: 62 */       super.loadCraftingRecipes(outputId, results);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void loadCraftingRecipes(ItemStack result)
/*  83:    */   {
/*  84: 67 */     if ((!result.hasTagCompound()) || ("".equals(result.getTagCompound().getString("mat")))) {
/*  85: 68 */       return;
/*  86:    */     }
/*  87: 70 */     MicroMaterialRegistry.IMicroMaterial m = MicroMaterialRegistry.getMaterial(result.getTagCompound().getString("mat"));
/*  88: 72 */     if (m == null) {
/*  89: 73 */       return;
/*  90:    */     }
/*  91: 75 */     this.scroll = false;
/*  92: 76 */     this.currentMaterial = result.getTagCompound().getString("mat");
/*  93: 77 */     this.currentBlock = m.getItem().copy();
/*  94: 79 */     for (RecipeMicroBlocks irecipe : getCraftingRecipes()) {
/*  95: 80 */       if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result))
/*  96:    */       {
/*  97: 81 */         MicroblockCachedRecipe recipe = new MicroblockCachedRecipe(irecipe);
/*  98: 82 */         recipe.computeVisuals();
/*  99: 83 */         this.arecipes.add(recipe);
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void loadUsageRecipes(ItemStack ingredient)
/* 105:    */   {
/* 106: 92 */     if ((!ingredient.hasTagCompound()) || ("".equals(ingredient.getTagCompound().getString("mat")))) {
/* 107: 93 */       return;
/* 108:    */     }
/* 109: 95 */     MicroMaterialRegistry.IMicroMaterial m = MicroMaterialRegistry.getMaterial(ingredient.getTagCompound().getString("mat"));
/* 110: 97 */     if (m == null) {
/* 111: 98 */       return;
/* 112:    */     }
/* 113:100 */     this.scroll = false;
/* 114:101 */     this.currentMaterial = ingredient.getTagCompound().getString("mat");
/* 115:102 */     this.currentBlock = m.getItem().copy();
/* 116:104 */     for (RecipeMicroBlocks irecipe : getCraftingRecipes())
/* 117:    */     {
/* 118:105 */       MicroblockCachedRecipe recipe = new MicroblockCachedRecipe(irecipe);
/* 119:    */       
/* 120:107 */       recipe.computeVisuals();
/* 121:109 */       if (recipe.contains(recipe.ingredients, ingredient))
/* 122:    */       {
/* 123:110 */         recipe.setIngredientPermutation(recipe.ingredients, ingredient);
/* 124:111 */         this.arecipes.add(recipe);
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getGuiTexture()
/* 130:    */   {
/* 131:118 */     return "textures/gui/container/crafting_table.png";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getRecipeName()
/* 135:    */   {
/* 136:123 */     return "Extra Utilities: Microblocks";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public class MicroblockCachedRecipe
/* 140:    */     extends TemplateRecipeHandler.CachedRecipe
/* 141:    */   {
/* 142:    */     public ArrayList<PositionedStack> ingredients;
/* 143:    */     public MicroblockPositionedStack result;
/* 144:    */     
/* 145:    */     public MicroblockCachedRecipe(int width, int height, Object[] items, ItemStack out)
/* 146:    */     {
/* 147:130 */       super();
/* 148:131 */       this.result = new MicroblockPositionedStack(out, 119, 24);
/* 149:132 */       this.ingredients = new ArrayList();
/* 150:133 */       setIngredients(width, height, items);
/* 151:    */     }
/* 152:    */     
/* 153:    */     public MicroblockCachedRecipe(RecipeMicroBlocks irecipe)
/* 154:    */     {
/* 155:137 */       this(irecipe.recipeWidth, irecipe.recipeHeight, irecipe.getRecipeItems(), irecipe.getRecipeOutput());
/* 156:    */     }
/* 157:    */     
/* 158:    */     public void setIngredients(int width, int height, Object[] items)
/* 159:    */     {
/* 160:141 */       for (int x = 0; x < width; x++) {
/* 161:142 */         for (int y = 0; y < height; y++) {
/* 162:143 */           if (items[(y * width + x)] != null)
/* 163:    */           {
/* 164:    */             PositionedStack stack;
/* 165:    */             PositionedStack stack;
/* 166:148 */             if (((items[(y * width + x)] instanceof ItemStack)) && (((ItemStack)items[(y * width + x)]).getItem() == FMPBase.getMicroBlockItemId())) {
/* 167:151 */               stack = new MicroblockPositionedStack((ItemStack)items[(y * width + x)], 25 + x * 18, 6 + y * 18);
/* 168:    */             } else {
/* 169:153 */               stack = new PositionedStack(items[(y * width + x)], 25 + x * 18, 6 + y * 18, false);
/* 170:    */             }
/* 171:156 */             stack.setMaxSize(1);
/* 172:157 */             this.ingredients.add(stack);
/* 173:    */           }
/* 174:    */         }
/* 175:    */       }
/* 176:    */     }
/* 177:    */     
/* 178:    */     public void permMaterial()
/* 179:    */     {
/* 180:163 */       if (!MicroBlocksHandler.this.scroll) {
/* 181:164 */         return;
/* 182:    */       }
/* 183:167 */       if (MicroBlocksHandler.currentMaterials == null)
/* 184:    */       {
/* 185:168 */         MicroBlocksHandler.currentMaterials = new String[MicroMaterialRegistry.getIdMap().length];
/* 186:170 */         for (int i = 0; i < MicroMaterialRegistry.getIdMap().length; i++) {
/* 187:171 */           MicroBlocksHandler.currentMaterials[i] = ((String)MicroMaterialRegistry.getIdMap()[i]._1());
/* 188:    */         }
/* 189:    */       }
/* 190:174 */       if (MicroBlocksHandler.currentBlocks == null)
/* 191:    */       {
/* 192:175 */         MicroBlocksHandler.currentBlocks = new ItemStack[MicroMaterialRegistry.getIdMap().length];
/* 193:177 */         for (int i = 0; i < MicroMaterialRegistry.getIdMap().length; i++) {
/* 194:178 */           MicroBlocksHandler.currentBlocks[i] = ((MicroMaterialRegistry.IMicroMaterial)MicroMaterialRegistry.getIdMap()[i]._2()).getItem().copy();
/* 195:    */         }
/* 196:    */       }
/* 197:182 */       MicroBlocksHandler.this.currentMaterial = MicroBlocksHandler.currentMaterials[(MicroBlocksHandler.this.cycleticks / 20 % MicroBlocksHandler.currentMaterials.length)];
/* 198:183 */       MicroBlocksHandler.this.currentBlock = MicroBlocksHandler.currentBlocks[(MicroBlocksHandler.this.cycleticks / 20 % MicroBlocksHandler.currentMaterials.length)];
/* 199:    */     }
/* 200:    */     
/* 201:    */     public List<PositionedStack> getIngredients()
/* 202:    */     {
/* 203:189 */       return getCycledIngredients(MicroBlocksHandler.this.cycleticks / 20, this.ingredients);
/* 204:    */     }
/* 205:    */     
/* 206:    */     public List<PositionedStack> getCycledIngredients(int cycle, List<PositionedStack> ingredients)
/* 207:    */     {
/* 208:197 */       return super.getCycledIngredients(cycle, ingredients);
/* 209:    */     }
/* 210:    */     
/* 211:    */     public void randomRenderPermutation(PositionedStack stack, long cycle)
/* 212:    */     {
/* 213:202 */       stack.setPermutationToRender(0);
/* 214:    */     }
/* 215:    */     
/* 216:    */     public PositionedStack getResult()
/* 217:    */     {
/* 218:207 */       this.result.setPermutationToRender(0);
/* 219:208 */       return this.result;
/* 220:    */     }
/* 221:    */     
/* 222:    */     public void computeVisuals()
/* 223:    */     {
/* 224:212 */       for (PositionedStack p : this.ingredients) {
/* 225:213 */         p.generatePermutations();
/* 226:    */       }
/* 227:216 */       this.result.generatePermutations();
/* 228:    */     }
/* 229:    */     
/* 230:    */     public class MicroblockPositionedStack
/* 231:    */       extends PositionedStack
/* 232:    */     {
/* 233:221 */       boolean materialTag = false;
/* 234:    */       
/* 235:    */       public MicroblockPositionedStack(ItemStack object, int x, int y)
/* 236:    */       {
/* 237:224 */         super(x, y, false);
/* 238:225 */         this.item = this.items[0].copy();
/* 239:226 */         this.materialTag = ((this.item.getItem() != FMPBase.getMicroBlockItemId()) || (this.item.getItemDamage() != 0));
/* 240:    */       }
/* 241:    */       
/* 242:    */       public void setItem(ItemStack item)
/* 243:    */       {
/* 244:230 */         if (item != null)
/* 245:    */         {
/* 246:231 */           this.item = item.copy();
/* 247:232 */           this.items[0] = item.copy();
/* 248:    */         }
/* 249:    */         else
/* 250:    */         {
/* 251:234 */           this.item = null;
/* 252:    */         }
/* 253:    */       }
/* 254:    */       
/* 255:    */       public void setPermutationToRender(int index)
/* 256:    */       {
/* 257:241 */         if (this.item == null) {
/* 258:242 */           return;
/* 259:    */         }
/* 260:244 */         MicroBlocksHandler.MicroblockCachedRecipe.this.permMaterial();
/* 261:246 */         if (this.materialTag) {
/* 262:247 */           addMaterial();
/* 263:    */         } else {
/* 264:249 */           this.items[0] = MicroBlocksHandler.this.currentBlock.copy();
/* 265:    */         }
/* 266:252 */         super.setPermutationToRender(0);
/* 267:    */       }
/* 268:    */       
/* 269:    */       public void addMaterial()
/* 270:    */       {
/* 271:256 */         NBTTagCompound tag = this.items[0].getTagCompound();
/* 272:258 */         if (tag == null) {
/* 273:259 */           tag = new NBTTagCompound();
/* 274:    */         }
/* 275:262 */         tag.setString("mat", MicroBlocksHandler.this.currentMaterial);
/* 276:263 */         this.items[0].setTagCompound(tag);
/* 277:    */       }
/* 278:    */     }
/* 279:    */   }
/* 280:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.MicroBlocksHandler
 * JD-Core Version:    0.7.0.1
 */