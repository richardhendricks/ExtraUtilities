/*   1:    */ package com.rwtema.extrautils.modintegration;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Throwables;
/*   4:    */ import com.rwtema.extrautils.ExtraUtils;
/*   5:    */ import com.rwtema.extrautils.block.BlockColor;
/*   6:    */ import cpw.mods.fml.common.Loader;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraftforge.fluids.FluidRegistry;
/*  14:    */ import net.minecraftforge.fluids.FluidStack;
/*  15:    */ 
/*  16:    */ public class EE3Integration
/*  17:    */ {
/*  18:    */   static final boolean EE3Present;
/*  19:    */   public static Method addRecipe;
/*  20:    */   public static Method setAsNotLearnable;
/*  21:    */   public static Method setAsNotRecoverable;
/*  22:    */   public static Method addPreAssignedEnergyValue;
/*  23:    */   
/*  24:    */   static
/*  25:    */   {
/*  26: 26 */     boolean found = false;
/*  27: 27 */     if (Loader.isModLoaded("EE3")) {
/*  28:    */       try
/*  29:    */       {
/*  30: 29 */         Class<?> aClass = Class.forName("com.pahimar.ee3.api.exchange.RecipeRegistryProxy");
/*  31: 30 */         addRecipe = aClass.getDeclaredMethod("addRecipe", new Class[] { Object.class, List.class });
/*  32:    */         
/*  33: 32 */         aClass = Class.forName("com.pahimar.ee3.api.knowledge.AbilityRegistryProxy");
/*  34: 33 */         setAsNotLearnable = aClass.getDeclaredMethod("setAsNotLearnable", new Class[] { Object.class });
/*  35: 34 */         setAsNotRecoverable = aClass.getDeclaredMethod("setAsNotRecoverable", new Class[] { Object.class });
/*  36:    */         
/*  37: 36 */         aClass = Class.forName("com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy");
/*  38: 37 */         addPreAssignedEnergyValue = aClass.getDeclaredMethod("addPreAssignedEnergyValue", new Class[] { Object.class, Float.TYPE });
/*  39:    */         
/*  40: 39 */         found = true;
/*  41:    */       }
/*  42:    */       catch (Exception e)
/*  43:    */       {
/*  44: 41 */         found = false;
/*  45:    */       }
/*  46:    */     }
/*  47: 44 */     EE3Present = found;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void addPreAssignedEnergyValue(Object object, float val)
/*  51:    */   {
/*  52: 48 */     if (!EE3Present) {
/*  53: 48 */       return;
/*  54:    */     }
/*  55:    */     try
/*  56:    */     {
/*  57: 50 */       addPreAssignedEnergyValue.invoke(null, new Object[] { object, Float.valueOf(val) });
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61: 52 */       throw Throwables.propagate(e);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static void setAsNotLearnable(Object o)
/*  66:    */   {
/*  67: 57 */     if (!EE3Present) {
/*  68: 57 */       return;
/*  69:    */     }
/*  70:    */     try
/*  71:    */     {
/*  72: 59 */       setAsNotLearnable.invoke(null, new Object[] { o });
/*  73:    */     }
/*  74:    */     catch (Exception e)
/*  75:    */     {
/*  76: 61 */       throw Throwables.propagate(e);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void setAsNotRecoverable(Object o)
/*  81:    */   {
/*  82: 66 */     if (!EE3Present) {
/*  83: 66 */       return;
/*  84:    */     }
/*  85:    */     try
/*  86:    */     {
/*  87: 68 */       setAsNotRecoverable.invoke(null, new Object[] { o });
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91: 70 */       throw Throwables.propagate(e);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static void addEMCRecipes()
/*  96:    */   {
/*  97: 76 */     if (!EE3Present) {
/*  98: 76 */       return;
/*  99:    */     }
/* 100: 78 */     if (ExtraUtils.cursedEarthEnabled) {
/* 101: 79 */       addRecipe(new ItemStack(ExtraUtils.cursedEarth), new Object[] { Blocks.dirt, Items.rotten_flesh });
/* 102:    */     }
/* 103: 82 */     if (ExtraUtils.enderLilyEnabled) {
/* 104: 83 */       addRecipe(new ItemStack(ExtraUtils.enderLily), new Object[] { new ItemStack(Items.ender_pearl, 64) });
/* 105:    */     }
/* 106: 86 */     if (ExtraUtils.divisionSigilEnabled) {
/* 107: 87 */       addRecipe(new ItemStack(ExtraUtils.divisionSigil, 2), new Object[] { new ItemStack(Items.ender_pearl, 4), Items.nether_star });
/* 108:    */     }
/* 109: 90 */     if (ExtraUtils.wateringCanEnabled) {
/* 110: 91 */       addRecipe(new ItemStack(ExtraUtils.wateringCan, 1, 0), new Object[] { new ItemStack(ExtraUtils.wateringCan, 1, 1), new FluidStack(FluidRegistry.WATER, 1000) });
/* 111:    */     }
/* 112: 94 */     if (ExtraUtils.decorative1 != null)
/* 113:    */     {
/* 114: 95 */       addRecipe(new ItemStack(ExtraUtils.decorative1, 1, 8), new Object[] { Blocks.bookshelf, new ItemStack(Items.gold_ingot, 4), new ItemStack(Items.ender_pearl, 4) });
/* 115:    */       
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:101 */       addRecipe(new ItemStack(ExtraUtils.decorative1, 1, 2), new Object[] { Blocks.quartz_block });
/* 121:    */     }
/* 122:105 */     for (BlockColor colorblock : ExtraUtils.colorblocks) {
/* 123:106 */       for (int i = 0; i < 16; i++) {
/* 124:107 */         addRecipe(new ItemStack(colorblock, 1, i), new Object[] { colorblock.baseBlock });
/* 125:    */       }
/* 126:    */     }
/* 127:111 */     if (ExtraUtils.decorative2 != null) {
/* 128:112 */       addRecipe(new ItemStack(ExtraUtils.decorative2, 1, 0), new Object[] { Blocks.glass });
/* 129:    */     }
/* 130:118 */     if (ExtraUtils.unstableIngot != null)
/* 131:    */     {
/* 132:119 */       setAsNotLearnable(new ItemStack(ExtraUtils.unstableIngot, 1, 32767));
/* 133:120 */       addRecipe(new ItemStack(ExtraUtils.unstableIngot, 1, 0), new Object[] { ExtraUtils.unstableIngot, Integer.valueOf(9), Integer.valueOf(2) });
/* 134:    */     }
/* 135:124 */     if (ExtraUtils.soul != null)
/* 136:    */     {
/* 137:125 */       addPreAssignedEnergyValue(new ItemStack(ExtraUtils.soul), 1515413.0F);
/* 138:126 */       setAsNotLearnable(new ItemStack(ExtraUtils.soul, 1, 32767));
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static void addRecipe(ItemStack itemStack, Object... inputs)
/* 143:    */   {
/* 144:134 */     if (!EE3Present) {
/* 145:134 */       return;
/* 146:    */     }
/* 147:136 */     ArrayList<Object> items = new ArrayList(inputs.length);
/* 148:138 */     for (Object a : inputs) {
/* 149:139 */       if (a != null)
/* 150:    */       {
/* 151:    */         ItemStack input;
/* 152:142 */         if ((a instanceof ItemStack))
/* 153:    */         {
/* 154:143 */           input = (ItemStack)a;
/* 155:    */         }
/* 156:    */         else
/* 157:    */         {
/* 158:145 */           items.add(a);
/* 159:146 */           continue;
/* 160:    */         }
/* 161:    */         ItemStack input;
/* 162:149 */         if (input.stackSize != 0) {
/* 163:153 */           if (input.stackSize == 1)
/* 164:    */           {
/* 165:154 */             items.add(input.copy());
/* 166:    */           }
/* 167:    */           else
/* 168:    */           {
/* 169:156 */             int k = input.stackSize;
/* 170:157 */             input = input.copy();
/* 171:158 */             input.stackSize = 1;
/* 172:159 */             for (int i = 0; i < k; i++) {
/* 173:160 */               items.add(input.copy());
/* 174:    */             }
/* 175:    */           }
/* 176:    */         }
/* 177:    */       }
/* 178:    */     }
/* 179:164 */     if (items.isEmpty()) {
/* 180:164 */       return;
/* 181:    */     }
/* 182:166 */     recipes.add(new EE3Recipe(itemStack, items));
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static void finalRegister()
/* 186:    */   {
/* 187:172 */     if (!EE3Present) {
/* 188:172 */       return;
/* 189:    */     }
/* 190:    */     try
/* 191:    */     {
/* 192:174 */       for (EE3Recipe recipe : recipes) {
/* 193:175 */         addRecipe.invoke(null, new Object[] { recipe.output, recipe.inputs });
/* 194:    */       }
/* 195:    */     }
/* 196:    */     catch (Exception e)
/* 197:    */     {
/* 198:178 */       throw Throwables.propagate(e);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:182 */   static List<EE3Recipe> recipes = new ArrayList();
/* 203:    */   
/* 204:    */   public static class EE3Recipe
/* 205:    */   {
/* 206:    */     ItemStack output;
/* 207:    */     List<?> inputs;
/* 208:    */     
/* 209:    */     public EE3Recipe(ItemStack itemStack, List object)
/* 210:    */     {
/* 211:189 */       this.output = itemStack;
/* 212:190 */       this.inputs = object;
/* 213:    */     }
/* 214:    */   }
/* 215:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.EE3Integration
 * JD-Core Version:    0.7.0.1
 */