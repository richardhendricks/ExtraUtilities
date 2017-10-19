/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.modintegration.EE3Integration;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*  11:    */ import net.minecraft.inventory.InventoryCrafting;
/*  12:    */ import net.minecraft.item.Item;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.item.crafting.IRecipe;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ import net.minecraftforge.oredict.OreDictionary;
/*  17:    */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*  18:    */ 
/*  19:    */ public class RecipeEnchantCrafting
/*  20:    */   extends ShapedOreRecipe
/*  21:    */   implements IRecipe
/*  22:    */ {
/*  23:    */   private static final int MAX_CRAFT_GRID_WIDTH = 3;
/*  24:    */   private static final int MAX_CRAFT_GRID_HEIGHT = 3;
/*  25: 24 */   private ItemStack output = null;
/*  26: 25 */   private Object[] input = null;
/*  27: 26 */   private int width = 0;
/*  28: 27 */   private int height = 0;
/*  29: 28 */   private boolean mirrored = true;
/*  30:    */   
/*  31:    */   public RecipeEnchantCrafting(Block result, Object... recipe)
/*  32:    */   {
/*  33: 31 */     this(new ItemStack(result), recipe);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public RecipeEnchantCrafting(Item result, Object... recipe)
/*  37:    */   {
/*  38: 35 */     this(new ItemStack(result), recipe);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public RecipeEnchantCrafting(ItemStack result, Object... recipe)
/*  42:    */   {
/*  43: 39 */     super(result, recipe);
/*  44: 40 */     this.output = result.copy();
/*  45:    */     
/*  46: 42 */     String shape = "";
/*  47: 43 */     int idx = 0;
/*  48: 45 */     if ((recipe[idx] instanceof Boolean))
/*  49:    */     {
/*  50: 46 */       this.mirrored = ((Boolean)recipe[idx]).booleanValue();
/*  51: 47 */       if ((recipe[(idx + 1)] instanceof Object[])) {
/*  52: 48 */         recipe = (Object[])recipe[(idx + 1)];
/*  53:    */       } else {
/*  54: 50 */         idx = 1;
/*  55:    */       }
/*  56:    */     }
/*  57: 54 */     if ((recipe[idx] instanceof String[]))
/*  58:    */     {
/*  59: 55 */       String[] parts = (String[])recipe[(idx++)];
/*  60: 57 */       for (String s : parts)
/*  61:    */       {
/*  62: 58 */         this.width = s.length();
/*  63: 59 */         shape = shape + s;
/*  64:    */       }
/*  65: 62 */       this.height = parts.length;
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69: 64 */       while ((recipe[idx] instanceof String))
/*  70:    */       {
/*  71: 65 */         String s = (String)recipe[(idx++)];
/*  72: 66 */         shape = shape + s;
/*  73: 67 */         this.width = s.length();
/*  74: 68 */         this.height += 1;
/*  75:    */       }
/*  76:    */     }
/*  77: 72 */     if (this.width * this.height != shape.length())
/*  78:    */     {
/*  79: 73 */       String ret = "Invalid shaped ore recipe: ";
/*  80: 74 */       for (Object tmp : recipe) {
/*  81: 75 */         ret = ret + tmp + ", ";
/*  82:    */       }
/*  83: 77 */       ret = ret + this.output;
/*  84: 78 */       throw new RuntimeException(ret);
/*  85:    */     }
/*  86: 81 */     HashMap<Character, Object> itemMap = new HashMap();
/*  87: 83 */     for (; idx < recipe.length; idx += 2)
/*  88:    */     {
/*  89: 84 */       Character chr = (Character)recipe[idx];
/*  90: 85 */       Object in = recipe[(idx + 1)];
/*  91: 87 */       if ((in instanceof ItemStack))
/*  92:    */       {
/*  93: 88 */         itemMap.put(chr, ((ItemStack)in).copy());
/*  94:    */       }
/*  95: 89 */       else if ((in instanceof Item))
/*  96:    */       {
/*  97: 90 */         itemMap.put(chr, new ItemStack((Item)in));
/*  98:    */       }
/*  99: 91 */       else if ((in instanceof Block))
/* 100:    */       {
/* 101: 92 */         itemMap.put(chr, new ItemStack((Block)in, 1, 32767));
/* 102:    */       }
/* 103: 93 */       else if ((in instanceof String))
/* 104:    */       {
/* 105: 94 */         itemMap.put(chr, OreDictionary.getOres((String)in));
/* 106:    */       }
/* 107:    */       else
/* 108:    */       {
/* 109: 96 */         String ret = "Invalid shaped ore recipe: ";
/* 110: 97 */         for (Object tmp : recipe) {
/* 111: 98 */           ret = ret + tmp + ", ";
/* 112:    */         }
/* 113:100 */         ret = ret + this.output;
/* 114:101 */         throw new RuntimeException(ret);
/* 115:    */       }
/* 116:    */     }
/* 117:105 */     this.input = new Object[this.width * this.height];
/* 118:106 */     int x = 0;
/* 119:107 */     for (char chr : shape.toCharArray()) {
/* 120:108 */       this.input[(x++)] = itemMap.get(Character.valueOf(chr));
/* 121:    */     }
/* 122:112 */     Object[] copyInput = new Object[this.input.length];
/* 123:113 */     for (int i = 0; i < this.input.length; i++)
/* 124:    */     {
/* 125:114 */       copyInput[i] = this.input[i];
/* 126:115 */       if ((this.input[i] instanceof ItemStack))
/* 127:    */       {
/* 128:116 */         ItemStack itemStack = (ItemStack)this.input[i];
/* 129:117 */         if (itemStack.isItemEnchanted())
/* 130:    */         {
/* 131:118 */           itemStack = itemStack.copy();
/* 132:119 */           itemStack.setTagCompound(null);
/* 133:120 */           copyInput[i] = itemStack;
/* 134:    */         }
/* 135:    */       }
/* 136:    */     }
/* 137:124 */     EE3Integration.addRecipe(this.output, copyInput);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public ItemStack getCraftingResult(InventoryCrafting var1)
/* 141:    */   {
/* 142:132 */     return this.output.copy();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getRecipeSize()
/* 146:    */   {
/* 147:140 */     return this.input.length;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public ItemStack getRecipeOutput()
/* 151:    */   {
/* 152:145 */     return this.output;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean matches(InventoryCrafting inv, World world)
/* 156:    */   {
/* 157:153 */     for (int x = 0; x <= 3 - this.width; x++) {
/* 158:154 */       for (int y = 0; y <= 3 - this.height; y++)
/* 159:    */       {
/* 160:155 */         if (checkMatch(inv, x, y, false)) {
/* 161:156 */           return true;
/* 162:    */         }
/* 163:159 */         if ((this.mirrored) && (checkMatch(inv, x, y, true))) {
/* 164:160 */           return true;
/* 165:    */         }
/* 166:    */       }
/* 167:    */     }
/* 168:165 */     return false;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror)
/* 172:    */   {
/* 173:170 */     for (int x = 0; x < 3; x++) {
/* 174:171 */       for (int y = 0; y < 3; y++)
/* 175:    */       {
/* 176:172 */         int subX = x - startX;
/* 177:173 */         int subY = y - startY;
/* 178:174 */         Object target = null;
/* 179:176 */         if ((subX >= 0) && (subY >= 0) && (subX < this.width) && (subY < this.height)) {
/* 180:177 */           if (mirror) {
/* 181:178 */             target = this.input[(this.width - subX - 1 + subY * this.width)];
/* 182:    */           } else {
/* 183:180 */             target = this.input[(subX + subY * this.width)];
/* 184:    */           }
/* 185:    */         }
/* 186:184 */         ItemStack slot = inv.getStackInRowAndColumn(x, y);
/* 187:    */         Map<Integer, Integer> other;
/* 188:186 */         if ((target instanceof ItemStack))
/* 189:    */         {
/* 190:187 */           ItemStack stack = (ItemStack)target;
/* 191:189 */           if (!OreDictionary.itemMatches(stack, slot, false)) {
/* 192:190 */             return false;
/* 193:    */           }
/* 194:193 */           Map<Integer, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
/* 195:194 */           if (!enchants.isEmpty())
/* 196:    */           {
/* 197:195 */             other = EnchantmentHelper.getEnchantments(slot);
/* 198:196 */             for (Map.Entry<Integer, Integer> entry : enchants.entrySet())
/* 199:    */             {
/* 200:197 */               Integer t = (Integer)other.get(entry.getKey());
/* 201:198 */               if ((t == null) || (t.intValue() < ((Integer)entry.getValue()).intValue())) {
/* 202:199 */                 return false;
/* 203:    */               }
/* 204:    */             }
/* 205:    */           }
/* 206:    */         }
/* 207:203 */         else if ((target instanceof ArrayList))
/* 208:    */         {
/* 209:204 */           boolean matched = false;
/* 210:    */           
/* 211:206 */           Iterator<ItemStack> itr = ((ArrayList)target).iterator();
/* 212:207 */           while ((itr.hasNext()) && (!matched)) {
/* 213:208 */             matched = OreDictionary.itemMatches((ItemStack)itr.next(), slot, false);
/* 214:    */           }
/* 215:211 */           if (!matched) {
/* 216:212 */             return false;
/* 217:    */           }
/* 218:    */         }
/* 219:214 */         else if ((target == null) && (slot != null))
/* 220:    */         {
/* 221:215 */           return false;
/* 222:    */         }
/* 223:    */       }
/* 224:    */     }
/* 225:220 */     return true;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public RecipeEnchantCrafting setMirrored(boolean mirror)
/* 229:    */   {
/* 230:224 */     this.mirrored = mirror;
/* 231:225 */     return this;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public Object[] getInput()
/* 235:    */   {
/* 236:235 */     return this.input;
/* 237:    */   }
/* 238:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeEnchantCrafting
 * JD-Core Version:    0.7.0.1
 */