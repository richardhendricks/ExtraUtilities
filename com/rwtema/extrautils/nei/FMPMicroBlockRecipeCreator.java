/*   1:    */ package com.rwtema.extrautils.nei;
/*   2:    */ 
/*   3:    */ import codechicken.microblock.MicroRecipe;
/*   4:    */ import codechicken.microblock.handler.MicroblockProxy;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.inventory.InventoryBasic;
/*   9:    */ import net.minecraft.inventory.InventoryCrafting;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.item.crafting.ShapedRecipes;
/*  12:    */ 
/*  13:    */ public class FMPMicroBlockRecipeCreator
/*  14:    */   extends InventoryCrafting
/*  15:    */ {
/*  16: 15 */   public static final int[] validClasses = { 0, 1, 2, 3 };
/*  17: 16 */   public static final int[] validSizes = { 4, 2, 1 };
/*  18: 17 */   public static final int[] validSizes2 = { 8, 4, 2, 1 };
/*  19: 18 */   public static ItemStack stone = new ItemStack(Blocks.stone);
/*  20: 20 */   public static FMPMicroBlockRecipeCreator craft = new FMPMicroBlockRecipeCreator();
/*  21:    */   public static int mat;
/*  22:    */   public static ItemStack saw;
/*  23:    */   
/*  24:    */   public FMPMicroBlockRecipeCreator()
/*  25:    */   {
/*  26: 25 */     super(null, 3, 3);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static ArrayList<ShapedRecipes> loadRecipes()
/*  30:    */   {
/*  31: 30 */     ArrayList<ShapedRecipes> set = new ArrayList();
/*  32: 31 */     mat = MicroRecipe.findMaterial(stone);
/*  33: 32 */     if (mat == -1) {
/*  34: 33 */       return set;
/*  35:    */     }
/*  36: 35 */     saw = new ItemStack(MicroblockProxy.sawDiamond());
/*  37: 36 */     if (saw == null) {
/*  38: 37 */       return set;
/*  39:    */     }
/*  40: 40 */     loadThinningRecipes(set);
/*  41: 41 */     loadSplittingRecipes(set);
/*  42: 42 */     loadHollowRecipes(set);
/*  43: 43 */     loadHollowFillingRecipes(set);
/*  44: 44 */     loadGluingRecipes(set);
/*  45: 45 */     return set;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void loadThinningRecipes(ArrayList<ShapedRecipes> recipes)
/*  49:    */   {
/*  50: 50 */     craft.clear();
/*  51: 51 */     craft.setInventorySlotContents(0, 0, saw);
/*  52: 53 */     for (int mclass : validClasses) {
/*  53: 54 */       for (int msize : validSizes2) {
/*  54: 55 */         if ((msize != 8) || (mclass == 0))
/*  55:    */         {
/*  56: 56 */           ItemStack a = MicroRecipe.create(1, mclass, msize, mat);
/*  57: 57 */           craft.setInventorySlotContents(0, 1, a);
/*  58: 58 */           ItemStack b = MicroRecipe.getCraftingResult(craft);
/*  59: 59 */           if (b != null) {
/*  60: 59 */             recipes.add(new ShapedRecipes(1, 2, new ItemStack[] { saw, a }, b));
/*  61:    */           }
/*  62:    */         }
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void loadSplittingRecipes(ArrayList<ShapedRecipes> recipes)
/*  68:    */   {
/*  69: 64 */     craft.clear();
/*  70: 65 */     craft.setInventorySlotContents(0, 0, saw);
/*  71: 68 */     for (int mclass : validClasses) {
/*  72: 69 */       for (int msize : validSizes2) {
/*  73: 70 */         if ((msize != 8) || (mclass == 0))
/*  74:    */         {
/*  75: 71 */           ItemStack a = MicroRecipe.create(1, mclass, msize, mat);
/*  76: 72 */           craft.setInventorySlotContents(1, 0, a);
/*  77: 73 */           ItemStack b = MicroRecipe.getCraftingResult(craft);
/*  78: 74 */           if (b != null) {
/*  79: 75 */             recipes.add(new ShapedRecipes(2, 1, new ItemStack[] { saw, a }, b));
/*  80:    */           }
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static void loadHollowRecipes(ArrayList<ShapedRecipes> recipes)
/*  87:    */   {
/*  88: 80 */     craft.clear();
/*  89: 83 */     for (int msize : validSizes)
/*  90:    */     {
/*  91: 84 */       ItemStack a = MicroRecipe.create(1, 0, msize, mat);
/*  92: 86 */       for (int i = 0; i < 9; i++) {
/*  93: 87 */         if (i != 4) {
/*  94: 88 */           craft.setInventorySlotContents(i, a);
/*  95:    */         }
/*  96:    */       }
/*  97: 90 */       ItemStack b = MicroRecipe.getCraftingResult(craft);
/*  98: 91 */       if (b != null) {
/*  99: 91 */         recipes.add(new ShapedRecipes(3, 3, new ItemStack[] { a, a, a, a, null, a, a, a, a }, b));
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void loadHollowFillingRecipes(ArrayList<ShapedRecipes> recipes)
/* 105:    */   {
/* 106: 97 */     craft.clear();
/* 107:101 */     for (int msize : validSizes)
/* 108:    */     {
/* 109:102 */       ItemStack a = MicroRecipe.create(1, 1, msize, mat);
/* 110:103 */       craft.setInventorySlotContents(0, a);
/* 111:104 */       ItemStack b = MicroRecipe.getCraftingResult(craft);
/* 112:105 */       if (b != null) {
/* 113:105 */         recipes.add(new ShapedRecipes(1, 1, new ItemStack[] { a }, b));
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static void loadGluingRecipes(ArrayList<ShapedRecipes> recipes)
/* 119:    */   {
/* 120:111 */     craft.clear();
/* 121:115 */     for (int mClass : validClasses) {
/* 122:116 */       for (int msize : validSizes)
/* 123:    */       {
/* 124:117 */         craft.clear();
/* 125:118 */         ItemStack a = MicroRecipe.create(1, mClass, msize, mat);
/* 126:    */         
/* 127:120 */         craft.setInventorySlotContents(0, a);
/* 128:121 */         craft.setInventorySlotContents(1, a);
/* 129:122 */         ItemStack b = MicroRecipe.getCraftingResult(craft);
/* 130:123 */         if (b != null) {
/* 131:123 */           recipes.add(new ShapedRecipes(2, 1, new ItemStack[] { a, a }, b));
/* 132:    */         }
/* 133:126 */         craft.setInventorySlotContents(2, a);
/* 134:127 */         craft.setInventorySlotContents(3, a);
/* 135:128 */         b = MicroRecipe.getCraftingResult(craft);
/* 136:129 */         if (b != null) {
/* 137:129 */           recipes.add(new ShapedRecipes(2, 2, new ItemStack[] { a, a, a, a }, b));
/* 138:    */         }
/* 139:131 */         craft.setInventorySlotContents(4, a);
/* 140:132 */         craft.setInventorySlotContents(5, a);
/* 141:133 */         b = MicroRecipe.getCraftingResult(craft);
/* 142:134 */         if (b != null) {
/* 143:134 */           recipes.add(new ShapedRecipes(2, 3, new ItemStack[] { a, a, a, a, a, a }, b));
/* 144:    */         }
/* 145:136 */         craft.setInventorySlotContents(6, a);
/* 146:137 */         craft.setInventorySlotContents(7, a);
/* 147:138 */         b = MicroRecipe.getCraftingResult(craft);
/* 148:139 */         if (b != null) {
/* 149:139 */           recipes.add(new ShapedRecipes(3, 3, new ItemStack[] { a, a, a, a, a, a, a, a, null }, b));
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void clear()
/* 156:    */   {
/* 157:146 */     for (int i = 0; i < 9; i++) {
/* 158:147 */       this.inv.setInventorySlotContents(i, null);
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:151 */   InventoryBasic inv = new InventoryBasic("Craft Matrix", false, 9);
/* 163:    */   
/* 164:    */   public ItemStack getStackInRowAndColumn(int par1, int par2)
/* 165:    */   {
/* 166:155 */     if ((par1 >= 0) && (par1 < 3))
/* 167:    */     {
/* 168:156 */       int k = par1 + par2 * 3;
/* 169:157 */       return this.inv.getStackInSlot(k);
/* 170:    */     }
/* 171:159 */     return null;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setInventorySlotContents(int par1, int par2, ItemStack itemstack)
/* 175:    */   {
/* 176:164 */     this.inv.setInventorySlotContents(par1 + par2 * 3, itemstack);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int getSizeInventory()
/* 180:    */   {
/* 181:169 */     return this.inv.getSizeInventory();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public ItemStack getStackInSlot(int i)
/* 185:    */   {
/* 186:174 */     return this.inv.getStackInSlot(i);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public ItemStack decrStackSize(int i, int j)
/* 190:    */   {
/* 191:179 */     return this.inv.decrStackSize(i, j);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public ItemStack getStackInSlotOnClosing(int i)
/* 195:    */   {
/* 196:184 */     return this.inv.getStackInSlotOnClosing(i);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setInventorySlotContents(int i, ItemStack itemstack)
/* 200:    */   {
/* 201:189 */     this.inv.setInventorySlotContents(i, itemstack);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String getInventoryName()
/* 205:    */   {
/* 206:194 */     return this.inv.getInventoryName();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isInventoryNameLocalized()
/* 210:    */   {
/* 211:199 */     return this.inv.isInventoryNameLocalized();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public int getInventoryStackLimit()
/* 215:    */   {
/* 216:204 */     return this.inv.getInventoryStackLimit();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/* 220:    */   {
/* 221:210 */     return false;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void openInventory()
/* 225:    */   {
/* 226:215 */     this.inv.openInventory();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void closeInventory()
/* 230:    */   {
/* 231:220 */     this.inv.closeInventory();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/* 235:    */   {
/* 236:225 */     return this.inv.isItemValidForSlot(i, itemstack);
/* 237:    */   }
/* 238:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.FMPMicroBlockRecipeCreator
 * JD-Core Version:    0.7.0.1
 */