/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.inventory.InventoryCrafting;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.item.crafting.IRecipe;
/*  11:    */ import net.minecraft.item.crafting.ShapedRecipes;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class RecipeUnstableIngotCrafting
/*  16:    */   extends ShapedRecipes
/*  17:    */ {
/*  18:    */   public RecipeUnstableIngotCrafting(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack)
/*  19:    */   {
/*  20: 18 */     super(par1, par2, par3ArrayOfItemStack, par4ItemStack);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static IRecipe addRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
/*  24:    */   {
/*  25: 22 */     String s = "";
/*  26: 23 */     int i = 0;
/*  27: 24 */     int j = 0;
/*  28: 25 */     int k = 0;
/*  29: 27 */     if ((par2ArrayOfObj[i] instanceof String[]))
/*  30:    */     {
/*  31: 28 */       String[] astring = (String[])par2ArrayOfObj[(i++)];
/*  32: 30 */       for (String s1 : astring)
/*  33:    */       {
/*  34: 31 */         k++;
/*  35: 32 */         j = s1.length();
/*  36: 33 */         s = s + s1;
/*  37:    */       }
/*  38:    */     }
/*  39:    */     else
/*  40:    */     {
/*  41: 36 */       while ((par2ArrayOfObj[i] instanceof String))
/*  42:    */       {
/*  43: 37 */         String s2 = (String)par2ArrayOfObj[(i++)];
/*  44: 38 */         k++;
/*  45: 39 */         j = s2.length();
/*  46: 40 */         s = s + s2;
/*  47:    */       }
/*  48:    */     }
/*  49: 46 */     for (HashMap hashmap = new HashMap(); i < par2ArrayOfObj.length; i += 2)
/*  50:    */     {
/*  51: 47 */       Character character = (Character)par2ArrayOfObj[i];
/*  52: 48 */       ItemStack itemstack1 = null;
/*  53: 50 */       if ((par2ArrayOfObj[(i + 1)] instanceof Item)) {
/*  54: 51 */         itemstack1 = new ItemStack((Item)par2ArrayOfObj[(i + 1)]);
/*  55: 52 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof Block)) {
/*  56: 53 */         itemstack1 = new ItemStack((Block)par2ArrayOfObj[(i + 1)], 1, 32767);
/*  57: 54 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof ItemStack)) {
/*  58: 55 */         itemstack1 = (ItemStack)par2ArrayOfObj[(i + 1)];
/*  59:    */       }
/*  60: 58 */       hashmap.put(character, itemstack1);
/*  61:    */     }
/*  62: 61 */     ItemStack[] aitemstack = new ItemStack[j * k];
/*  63: 63 */     for (int i1 = 0; i1 < j * k; i1++)
/*  64:    */     {
/*  65: 64 */       char c0 = s.charAt(i1);
/*  66: 66 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/*  67: 67 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
/*  68:    */       } else {
/*  69: 69 */         aitemstack[i1] = null;
/*  70:    */       }
/*  71:    */     }
/*  72: 73 */     NBTTagCompound tags = new NBTTagCompound();
/*  73: 74 */     tags.setBoolean("Bug", true);
/*  74: 75 */     par1ItemStack.setTagCompound(tags);
/*  75: 76 */     IRecipe shapedrecipes = new RecipeUnstableIngotCrafting(j, k, aitemstack, par1ItemStack);
/*  76: 77 */     return shapedrecipes;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean matches(InventoryCrafting inventorycrafting, World world)
/*  80:    */   {
/*  81: 82 */     return getCraftingResult(inventorycrafting) != null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
/*  85:    */   {
/*  86: 87 */     if (inventorycrafting.getSizeInventory() != 9) {
/*  87: 88 */       return null;
/*  88:    */     }
/*  89: 91 */     int curRow = -1;
/*  90: 93 */     for (int x = 0; x < 3; x++) {
/*  91: 94 */       for (int y = 0; y < 3; y++) {
/*  92: 95 */         if (inventorycrafting.getStackInRowAndColumn(x, y) != null)
/*  93:    */         {
/*  94: 96 */           if (curRow == -1)
/*  95:    */           {
/*  96: 97 */             if (y == 0) {
/*  97: 98 */               curRow = x;
/*  98:    */             } else {
/*  99:100 */               return null;
/* 100:    */             }
/* 101:    */           }
/* 102:102 */           else if (x != curRow) {
/* 103:103 */             return null;
/* 104:    */           }
/* 105:    */         }
/* 106:105 */         else if (curRow == x) {
/* 107:106 */           return null;
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:111 */     if (curRow == -1) {
/* 112:112 */       return null;
/* 113:    */     }
/* 114:115 */     int x = curRow;
/* 115:116 */     ItemStack n = inventorycrafting.getStackInRowAndColumn(x, 0);
/* 116:117 */     ItemStack s = inventorycrafting.getStackInRowAndColumn(x, 1);
/* 117:118 */     ItemStack d = inventorycrafting.getStackInRowAndColumn(x, 2);
/* 118:120 */     if (s.getItem() != ExtraUtils.divisionSigil) {
/* 119:121 */       return null;
/* 120:    */     }
/* 121:124 */     if ((!s.hasTagCompound()) || ((!s.getTagCompound().hasKey("damage")) && (!s.getTagCompound().hasKey("stable")))) {
/* 122:125 */       return null;
/* 123:    */     }
/* 124:128 */     return makeResult(n, d, s);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public ItemStack makeResult(ItemStack n, ItemStack d, ItemStack s)
/* 128:    */   {
/* 129:132 */     if ((n.getItem() == Items.iron_ingot) && (d.getItem() == Items.diamond))
/* 130:    */     {
/* 131:133 */       if (!s.getTagCompound().hasKey("stable"))
/* 132:    */       {
/* 133:134 */         StackTraceElement[] stackTrace = new Throwable().getStackTrace();
/* 134:136 */         if ((stackTrace.length < 4) || ((!"net.minecraft.inventory.ContainerWorkbench".equals(stackTrace[4].getClassName())) && (!"net.minecraft.inventory.ContainerWorkbench".equals(stackTrace[3].getClassName())))) {
/* 135:138 */           return null;
/* 136:    */         }
/* 137:    */       }
/* 138:143 */       ItemStack item = new ItemStack(ExtraUtils.unstableIngot, 1);
/* 139:145 */       if (s.getTagCompound().hasKey("stable"))
/* 140:    */       {
/* 141:146 */         item.setItemDamage(2);
/* 142:    */       }
/* 143:    */       else
/* 144:    */       {
/* 145:148 */         NBTTagCompound tags = new NBTTagCompound();
/* 146:149 */         tags.setBoolean("crafting", true);
/* 147:150 */         item.setTagCompound(tags);
/* 148:    */       }
/* 149:153 */       return item;
/* 150:    */     }
/* 151:156 */     return null;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getRecipeSize()
/* 155:    */   {
/* 156:161 */     return 3;
/* 157:    */   }
/* 158:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeUnstableIngotCrafting
 * JD-Core Version:    0.7.0.1
 */