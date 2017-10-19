/*   1:    */ package com.rwtema.extrautils.tileentity.enderconstructor;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.LogHelper;
/*   5:    */ import com.rwtema.extrautils.helper.ThaumcraftHelper;
/*   6:    */ import cpw.mods.fml.common.Loader;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Set;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.inventory.InventoryCrafting;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.item.crafting.CraftingManager;
/*  17:    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*  18:    */ import net.minecraft.item.crafting.IRecipe;
/*  19:    */ import net.minecraft.item.crafting.ShapedRecipes;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ import net.minecraftforge.oredict.OreDictionary;
/*  22:    */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*  23:    */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*  24:    */ 
/*  25:    */ public class EnderConstructorRecipesHandler
/*  26:    */ {
/*  27: 26 */   public static ArrayList<IRecipe> recipes = new ArrayList();
/*  28:    */   
/*  29:    */   public static void addRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
/*  30:    */   {
/*  31: 29 */     registerRecipe(newRecipe(par1ItemStack, par2ArrayOfObj));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static void registerRecipe(IRecipe recipe)
/*  35:    */   {
/*  36: 33 */     recipes.add(recipe);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void postInit()
/*  40:    */   {
/*  41: 38 */     if (ExtraUtils.qed == null) {
/*  42: 39 */       return;
/*  43:    */     }
/*  44: 41 */     if (ExtraUtils.qedList.isEmpty()) {
/*  45: 42 */       return;
/*  46:    */     }
/*  47: 44 */     ArrayList<ItemStack> items = new ArrayList();
/*  48: 46 */     for (Object o : CraftingManager.getInstance().getRecipeList())
/*  49:    */     {
/*  50: 47 */       IRecipe recipe = (IRecipe)o;
/*  51: 48 */       if (recipe != null)
/*  52:    */       {
/*  53:    */         ItemStack item;
/*  54:    */         try
/*  55:    */         {
/*  56: 53 */           item = recipe.getRecipeOutput();
/*  57:    */         }
/*  58:    */         catch (Exception e)
/*  59:    */         {
/*  60: 55 */           LogHelper.info("Caught error from Recipe", new Object[0]);
/*  61: 56 */           e.printStackTrace();
/*  62:    */         }
/*  63: 57 */         continue;
/*  64: 60 */         if (item != null) {
/*  65: 63 */           if (item.getItem() == null)
/*  66:    */           {
/*  67: 64 */             new RuntimeException("ItemStack with null item found in " + recipe.getClass().getSimpleName() + " getRecipeOutput()").printStackTrace();
/*  68:    */           }
/*  69:    */           else
/*  70:    */           {
/*  71:    */             String s;
/*  72:    */             try
/*  73:    */             {
/*  74: 69 */               s = item.getUnlocalizedName();
/*  75:    */             }
/*  76:    */             catch (Exception e)
/*  77:    */             {
/*  78: 71 */               new RuntimeException("Caught error from ItemStack in getRecipeOutput()", e).printStackTrace();
/*  79:    */             }
/*  80: 72 */             continue;
/*  81: 75 */             if (ExtraUtils.qedList.contains(s))
/*  82:    */             {
/*  83: 76 */               items.add(item);
/*  84: 77 */               recipes.add(recipe);
/*  85:    */             }
/*  86:    */           }
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90: 82 */     if (Loader.isModLoaded("Thaumcraft")) {
/*  91: 83 */       ThaumcraftHelper.handleQEDRecipes(items);
/*  92:    */     }
/*  93: 85 */     CraftingManager.getInstance().getRecipeList().removeAll(recipes);
/*  94: 87 */     if (!ExtraUtils.disableQEDIngotSmeltRecipes) {
/*  95: 88 */       for (String oreName : OreDictionary.getOreNames()) {
/*  96: 89 */         if (oreName.startsWith("ore")) {
/*  97: 92 */           for (ItemStack ore : OreDictionary.getOres(oreName))
/*  98:    */           {
/*  99: 93 */             ItemStack smeltingResult = FurnaceRecipes.smelting().func_151395_a(ore);
/* 100: 94 */             if (smeltingResult != null) {
/* 101: 97 */               for (int i : OreDictionary.getOreIDs(smeltingResult)) {
/* 102: 98 */                 if (OreDictionary.getOreName(i).startsWith("ingot"))
/* 103:    */                 {
/* 104: 99 */                   ItemStack result = smeltingResult.copy();
/* 105:100 */                   result.stackSize *= 3;
/* 106:101 */                   if (result.stackSize > result.getMaxStackSize()) {
/* 107:102 */                     result.stackSize = result.getMaxStackSize();
/* 108:    */                   }
/* 109:103 */                   recipes.add(new ShapedOreRecipe(result, new Object[] { "Oc", Character.valueOf('O'), ore, Character.valueOf('c'), Items.coal }));
/* 110:104 */                   recipes.add(new ShapelessOreRecipe(result, new Object[] { ore, Items.coal }));
/* 111:105 */                   break;
/* 112:    */                 }
/* 113:    */               }
/* 114:    */             }
/* 115:    */           }
/* 116:    */         }
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World)
/* 122:    */   {
/* 123:116 */     for (IRecipe irecipe : recipes) {
/* 124:117 */       if (irecipe.matches(par1InventoryCrafting, par2World)) {
/* 125:118 */         return irecipe.getCraftingResult(par1InventoryCrafting);
/* 126:    */       }
/* 127:    */     }
/* 128:122 */     return null;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static ShapedRecipes newRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
/* 132:    */   {
/* 133:126 */     String s = "";
/* 134:127 */     int i = 0;
/* 135:128 */     int j = 0;
/* 136:129 */     int k = 0;
/* 137:131 */     if ((par2ArrayOfObj[i] instanceof String[]))
/* 138:    */     {
/* 139:132 */       String[] astring = (String[])par2ArrayOfObj[(i++)];
/* 140:134 */       for (String s1 : astring)
/* 141:    */       {
/* 142:135 */         k++;
/* 143:136 */         j = s1.length();
/* 144:137 */         s = s + s1;
/* 145:    */       }
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:140 */       while ((par2ArrayOfObj[i] instanceof String))
/* 150:    */       {
/* 151:141 */         String s2 = (String)par2ArrayOfObj[(i++)];
/* 152:142 */         k++;
/* 153:143 */         j = s2.length();
/* 154:144 */         s = s + s2;
/* 155:    */       }
/* 156:    */     }
/* 157:150 */     for (HashMap hashmap = new HashMap(); i < par2ArrayOfObj.length; i += 2)
/* 158:    */     {
/* 159:151 */       Character character = (Character)par2ArrayOfObj[i];
/* 160:152 */       ItemStack itemstack1 = null;
/* 161:154 */       if ((par2ArrayOfObj[(i + 1)] instanceof Item)) {
/* 162:155 */         itemstack1 = new ItemStack((Item)par2ArrayOfObj[(i + 1)]);
/* 163:156 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof Block)) {
/* 164:157 */         itemstack1 = new ItemStack((Block)par2ArrayOfObj[(i + 1)], 1, 32767);
/* 165:158 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof ItemStack)) {
/* 166:159 */         itemstack1 = (ItemStack)par2ArrayOfObj[(i + 1)];
/* 167:    */       }
/* 168:162 */       hashmap.put(character, itemstack1);
/* 169:    */     }
/* 170:165 */     ItemStack[] aitemstack = new ItemStack[j * k];
/* 171:167 */     for (int i1 = 0; i1 < j * k; i1++)
/* 172:    */     {
/* 173:168 */       char c0 = s.charAt(i1);
/* 174:170 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/* 175:171 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
/* 176:    */       } else {
/* 177:173 */         aitemstack[i1] = null;
/* 178:    */       }
/* 179:    */     }
/* 180:177 */     ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, par1ItemStack);
/* 181:178 */     return shapedrecipes;
/* 182:    */   }
/* 183:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.EnderConstructorRecipesHandler
 * JD-Core Version:    0.7.0.1
 */