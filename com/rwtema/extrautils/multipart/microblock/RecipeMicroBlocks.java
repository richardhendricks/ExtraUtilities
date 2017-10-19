/*   1:    */ package com.rwtema.extrautils.multipart.microblock;
/*   2:    */ 
/*   3:    */ import codechicken.microblock.ItemMicroPart;
/*   4:    */ import codechicken.microblock.MicroRecipe;
/*   5:    */ import com.rwtema.extrautils.multipart.FMPBase;
/*   6:    */ import net.minecraft.inventory.InventoryCrafting;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.item.crafting.IRecipe;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class RecipeMicroBlocks
/*  14:    */   implements IRecipe
/*  15:    */ {
/*  16: 14 */   public static Item microID = null;
/*  17:    */   public final int recipeWidth;
/*  18:    */   public final int recipeHeight;
/*  19:    */   public final Object[] recipeItems;
/*  20:    */   public final Item recipeOutputItemID;
/*  21:    */   private ItemStack recipeOutput;
/*  22:    */   private boolean field_92101_f;
/*  23:    */   
/*  24:    */   public RecipeMicroBlocks(int par1, int par2, Object[] par3ArrayOfItemStack, ItemStack par4ItemStack)
/*  25:    */   {
/*  26: 41 */     this.recipeOutputItemID = par4ItemStack.getItem();
/*  27: 42 */     this.recipeWidth = par1;
/*  28: 43 */     this.recipeHeight = par2;
/*  29: 44 */     this.recipeItems = par3ArrayOfItemStack;
/*  30: 45 */     this.recipeOutput = par4ItemStack;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ItemStack getRecipeOutput()
/*  34:    */   {
/*  35: 50 */     return this.recipeOutput;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  39:    */   {
/*  40: 58 */     for (int i = 0; i <= 3 - this.recipeWidth; i++) {
/*  41: 59 */       for (int j = 0; j <= 3 - this.recipeHeight; j++)
/*  42:    */       {
/*  43: 60 */         if (checkMatch(par1InventoryCrafting, i, j, true)) {
/*  44: 61 */           return true;
/*  45:    */         }
/*  46: 64 */         if (checkMatch(par1InventoryCrafting, i, j, false)) {
/*  47: 65 */           return true;
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51: 70 */     return false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ItemStack getRecipeItem(int a)
/*  55:    */   {
/*  56: 75 */     if ((this.recipeItems[a] instanceof ItemStack)) {
/*  57: 76 */       return (ItemStack)this.recipeItems[a];
/*  58:    */     }
/*  59: 77 */     if ((this.recipeItems[a] instanceof Integer))
/*  60:    */     {
/*  61: 78 */       int damage = ((Integer)this.recipeItems[a]).intValue();
/*  62: 80 */       if (getMicroID() != null) {
/*  63: 81 */         return new ItemStack(getMicroID(), 1, damage);
/*  64:    */       }
/*  65:    */     }
/*  66: 85 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private boolean checkMatch(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4)
/*  70:    */   {
/*  71: 92 */     if (getMicroID() == null) {
/*  72: 93 */       return false;
/*  73:    */     }
/*  74: 96 */     int curMat = 0;
/*  75: 98 */     for (int k = 0; k < 3; k++) {
/*  76: 99 */       for (int l = 0; l < 3; l++)
/*  77:    */       {
/*  78:100 */         int i1 = k - par2;
/*  79:101 */         int j1 = l - par3;
/*  80:102 */         ItemStack itemstack = null;
/*  81:    */         
/*  82:104 */         int i = -1;
/*  83:106 */         if ((i1 >= 0) && (j1 >= 0) && (i1 < this.recipeWidth) && (j1 < this.recipeHeight)) {
/*  84:107 */           if (par4) {
/*  85:108 */             i = this.recipeWidth - i1 - 1 + j1 * this.recipeWidth;
/*  86:    */           } else {
/*  87:110 */             i = i1 + j1 * this.recipeWidth;
/*  88:    */           }
/*  89:    */         }
/*  90:114 */         ItemStack itemstack1 = par1InventoryCrafting.getStackInRowAndColumn(k, l);
/*  91:116 */         if ((this.recipeItems[i] == null) || ((this.recipeItems[i] instanceof ItemStack)))
/*  92:    */         {
/*  93:117 */           itemstack = this.recipeItems[i] != null ? (ItemStack)this.recipeItems[i] : null;
/*  94:119 */           if ((itemstack1 != null) || (itemstack != null))
/*  95:    */           {
/*  96:120 */             if (((itemstack1 == null) && (itemstack != null)) || ((itemstack1 != null) && (itemstack == null))) {
/*  97:121 */               return false;
/*  98:    */             }
/*  99:124 */             if (itemstack.getItem() != itemstack1.getItem()) {
/* 100:125 */               return false;
/* 101:    */             }
/* 102:128 */             if ((itemstack.getItemDamage() != 32767) && (itemstack.getItemDamage() != itemstack1.getItemDamage())) {
/* 103:129 */               return false;
/* 104:    */             }
/* 105:    */           }
/* 106:    */         }
/* 107:132 */         else if ((this.recipeItems[i] instanceof Integer))
/* 108:    */         {
/* 109:133 */           int damage = ((Integer)this.recipeItems[i]).intValue();
/* 110:134 */           if (itemstack1 == null) {
/* 111:135 */             return false;
/* 112:    */           }
/* 113:137 */           if (damage == 0)
/* 114:    */           {
/* 115:138 */             if ((itemstack1.getItem() == null) || (itemstack1.getItem() == getMicroID())) {
/* 116:139 */               return false;
/* 117:    */             }
/* 118:141 */             int mat = MicroRecipe.findMaterial(itemstack1);
/* 119:142 */             if (mat <= 0) {
/* 120:143 */               return false;
/* 121:    */             }
/* 122:145 */             if (curMat == 0) {
/* 123:146 */               curMat = mat;
/* 124:147 */             } else if (curMat != mat) {
/* 125:148 */               return false;
/* 126:    */             }
/* 127:    */           }
/* 128:    */           else
/* 129:    */           {
/* 130:152 */             if (itemstack1.getItem() != getMicroID()) {
/* 131:153 */               return false;
/* 132:    */             }
/* 133:155 */             if (!itemstack1.hasTagCompound()) {
/* 134:156 */               return false;
/* 135:    */             }
/* 136:158 */             int s = ItemMicroPart.getMaterialID(itemstack1);
/* 137:160 */             if (s == 0) {
/* 138:161 */               return false;
/* 139:    */             }
/* 140:164 */             if (curMat == 0) {
/* 141:165 */               curMat = s;
/* 142:166 */             } else if (curMat != s) {
/* 143:167 */               return false;
/* 144:    */             }
/* 145:    */           }
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:175 */     return true;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/* 153:    */   {
/* 154:183 */     ItemStack itemstack = getRecipeOutput().copy();
/* 155:185 */     for (int i = 0; i < par1InventoryCrafting.getSizeInventory(); i++)
/* 156:    */     {
/* 157:186 */       ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(i);
/* 158:188 */       if ((itemstack1 != null) && (itemstack1.getItem() == getMicroID()) && (itemstack1.hasTagCompound()))
/* 159:    */       {
/* 160:189 */         NBTTagCompound tag = itemstack.getTagCompound();
/* 161:191 */         if (tag == null) {
/* 162:192 */           tag = new NBTTagCompound();
/* 163:    */         }
/* 164:195 */         tag.setString("mat", itemstack1.getTagCompound().getString("mat"));
/* 165:196 */         itemstack.setTagCompound((NBTTagCompound)itemstack1.stackTagCompound.copy());
/* 166:197 */         return itemstack;
/* 167:    */       }
/* 168:    */     }
/* 169:201 */     return itemstack;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private Item getMicroID()
/* 173:    */   {
/* 174:205 */     if (microID == null) {
/* 175:206 */       microID = FMPBase.getMicroBlockItemId();
/* 176:    */     }
/* 177:209 */     return microID;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getRecipeSize()
/* 181:    */   {
/* 182:217 */     return this.recipeWidth * this.recipeHeight;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public ItemStack[] getRecipeItems()
/* 186:    */   {
/* 187:221 */     ItemStack[] t = new ItemStack[this.recipeItems.length];
/* 188:223 */     for (int i = 0; i < this.recipeItems.length; i++) {
/* 189:224 */       t[i] = getRecipeItem(i);
/* 190:    */     }
/* 191:227 */     return t;
/* 192:    */   }
/* 193:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.RecipeMicroBlocks
 * JD-Core Version:    0.7.0.1
 */