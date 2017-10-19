/*   1:    */ package com.rwtema.extrautils.tileentity.generators;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.LogHelper;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   5:    */ import com.rwtema.extrautils.helper.XURandom;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Random;
/*  11:    */ import net.minecraft.entity.passive.EntityAnimal;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.item.crafting.CraftingManager;
/*  17:    */ import net.minecraft.item.crafting.IRecipe;
/*  18:    */ import net.minecraft.item.crafting.ShapedRecipes;
/*  19:    */ import net.minecraft.item.crafting.ShapelessRecipes;
/*  20:    */ import net.minecraft.util.AxisAlignedBB;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ import net.minecraftforge.oredict.OreDictionary;
/*  23:    */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*  24:    */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*  25:    */ 
/*  26:    */ public class TileEntityGeneratorPink
/*  27:    */   extends TileEntityGeneratorFurnace
/*  28:    */ {
/*  29:    */   public static HashSet<ItemStack> pink;
/*  30:    */   
/*  31:    */   public static void genPink()
/*  32:    */   {
/*  33: 30 */     String[] dyes = { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
/*  34:    */     
/*  35:    */ 
/*  36: 33 */     HashSet<Integer> dyeids = new HashSet();
/*  37: 35 */     for (String dye : dyes) {
/*  38: 36 */       dyeids.add(Integer.valueOf(OreDictionary.getOreID(dye)));
/*  39:    */     }
/*  40: 39 */     int pinkid = OreDictionary.getOreID("dyePink");
/*  41: 40 */     pink = new HashSet();
/*  42: 41 */     pink.add(new ItemStack(Items.dye, 1, 9));
/*  43: 42 */     pink.add(new ItemStack(Blocks.wool, 1, 6));
/*  44: 44 */     for (Object recipe : CraftingManager.getInstance().getRecipeList()) {
/*  45: 45 */       if (((IRecipe)recipe).getRecipeOutput() != null)
/*  46:    */       {
/*  47: 49 */         boolean flag = false;
/*  48: 50 */         for (int oreid : OreDictionary.getOreIDs(((IRecipe)recipe).getRecipeOutput())) {
/*  49: 51 */           if (!dyeids.contains(Integer.valueOf(oreid))) {
/*  50: 53 */             if (pinkid == oreid)
/*  51:    */             {
/*  52: 54 */               flag = true;
/*  53: 55 */               break;
/*  54:    */             }
/*  55:    */           }
/*  56:    */         }
/*  57: 59 */         if ((flag) || (
/*  58: 60 */           (!isPinkItem(((IRecipe)recipe).getRecipeOutput())) && 
/*  59:    */           
/*  60: 62 */           ((recipe instanceof ShapelessOreRecipe) ? 
/*  61: 63 */           isPinkRecipe(((ShapelessOreRecipe)recipe).getInput()) : 
/*  62:    */           
/*  63:    */ 
/*  64: 66 */           (recipe instanceof ShapedOreRecipe) ? 
/*  65: 67 */           isPinkRecipe(((ShapedOreRecipe)recipe).getInput()) : 
/*  66:    */           
/*  67:    */ 
/*  68: 70 */           (recipe instanceof ShapelessRecipes) ? 
/*  69: 71 */           isPinkRecipe(((ShapelessRecipes)recipe).recipeItems) : 
/*  70:    */           
/*  71:    */ 
/*  72: 74 */           ((recipe instanceof ShapedRecipes)) && 
/*  73: 75 */           (isPinkRecipe(((ShapedRecipes)recipe).recipeItems))))) {
/*  74: 83 */           pink.add(((IRecipe)recipe).getRecipeOutput().copy());
/*  75:    */         }
/*  76:    */       }
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static boolean isPinkRecipe(List recipe)
/*  81:    */   {
/*  82: 88 */     for (Object item : recipe) {
/*  83: 89 */       if (isPinkItem(item)) {
/*  84: 90 */         return true;
/*  85:    */       }
/*  86:    */     }
/*  87: 94 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static boolean isPinkRecipe(Object[] recipe)
/*  91:    */   {
/*  92: 98 */     for (Object item : recipe) {
/*  93: 99 */       if (isPinkItem(item)) {
/*  94:100 */         return true;
/*  95:    */       }
/*  96:    */     }
/*  97:104 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static boolean isPinkItem(Object item)
/* 101:    */   {
/* 102:108 */     if ((item instanceof ItemStack))
/* 103:    */     {
/* 104:109 */       ItemStack p = (ItemStack)item;
/* 105:111 */       if ((p.getItem() == Items.dye) && (p.getItemDamage() == 9)) {
/* 106:112 */         return true;
/* 107:    */       }
/* 108:113 */       if ((p.getItem() == Item.getItemFromBlock(Blocks.wool)) && (p.getItemDamage() == 6)) {
/* 109:114 */         return true;
/* 110:    */       }
/* 111:    */     }
/* 112:116 */     else if ((item instanceof ItemStack[]))
/* 113:    */     {
/* 114:117 */       for (ItemStack i : (ItemStack[])item) {
/* 115:118 */         if (isPinkItem(i)) {
/* 116:119 */           return true;
/* 117:    */         }
/* 118:    */       }
/* 119:    */     }
/* 120:121 */     else if ((item instanceof List))
/* 121:    */     {
/* 122:122 */       for (Object i : (List)item) {
/* 123:123 */         if (isPinkItem(i)) {
/* 124:124 */           return true;
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:128 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int transferLimit()
/* 132:    */   {
/* 133:133 */     return 160;
/* 134:    */   }
/* 135:    */   
/* 136:136 */   public Random rand = XURandom.getInstance();
/* 137:    */   
/* 138:    */   @SideOnly(Side.CLIENT)
/* 139:    */   public void doRandomDisplayTickR(Random random)
/* 140:    */   {
/* 141:141 */     if (this.rand.nextInt(2) != 0) {
/* 142:142 */       return;
/* 143:    */     }
/* 144:144 */     if (!shouldSoundPlay()) {
/* 145:145 */       return;
/* 146:    */     }
/* 147:147 */     double d0 = this.rand.nextGaussian() * 0.5D;
/* 148:148 */     double d1 = this.rand.nextGaussian() * 0.5D;
/* 149:149 */     double d2 = this.rand.nextGaussian() * 0.5D;
/* 150:    */     
/* 151:151 */     this.worldObj.spawnParticle("heart", x() + 0.2D + 0.6D * this.rand.nextFloat(), y() + 0.95D, z() + 0.2D + 0.6D * this.rand.nextFloat(), d0, d1, d2);
/* 152:    */     
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:156 */     super.doRandomDisplayTickR(random);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void doSpecial()
/* 160:    */   {
/* 161:162 */     if ((this.coolDown > 0.0D) && (this.rand.nextInt(40) == 0)) {
/* 162:163 */       for (Object entity : this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(x(), y(), z(), x() + 1, y() + 1, z() + 1).expand(10.0D, 10.0D, 10.0D)))
/* 163:    */       {
/* 164:165 */         EntityAnimal animal = (EntityAnimal)entity;
/* 165:166 */         if (animal.getGrowingAge() < 0)
/* 166:    */         {
/* 167:167 */           animal.addGrowth(this.rand.nextInt(40));
/* 168:    */         }
/* 169:168 */         else if (animal.getGrowingAge() > 0)
/* 170:    */         {
/* 171:169 */           int j = animal.getGrowingAge();
/* 172:170 */           j -= this.rand.nextInt(40);
/* 173:171 */           if (j < 0) {
/* 174:172 */             j = 0;
/* 175:    */           }
/* 176:173 */           animal.setGrowingAge(j);
/* 177:    */         }
/* 178:174 */         else if ((!animal.isInLove()) && (this.rand.nextInt(40) == 0))
/* 179:    */         {
/* 180:175 */           if (animal.worldObj.getEntitiesWithinAABB(entity.getClass(), animal.boundingBox.expand(8.0D, 8.0D, 8.0D)).size() > 32) {
/* 181:176 */             return;
/* 182:    */           }
/* 183:178 */           animal.func_146082_f(null);
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean isPink(ItemStack item)
/* 190:    */   {
/* 191:185 */     if (item == null) {
/* 192:186 */       return false;
/* 193:    */     }
/* 194:189 */     if ((item.getUnlocalizedName() != null) && (item.getUnlocalizedName().contains("pink"))) {
/* 195:190 */       return true;
/* 196:    */     }
/* 197:193 */     if (pink == null)
/* 198:    */     {
/* 199:194 */       long t = System.nanoTime();
/* 200:195 */       genPink();
/* 201:196 */       LogHelper.info("Pink recipes gened in " + (System.nanoTime() - t) / 1000000.0D, new Object[0]);
/* 202:    */     }
/* 203:199 */     for (ItemStack pinkItem : pink) {
/* 204:200 */       if (XUHelper.canItemsStack(item, pinkItem, false, true)) {
/* 205:201 */         return true;
/* 206:    */       }
/* 207:    */     }
/* 208:205 */     return false;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public double genLevel()
/* 212:    */   {
/* 213:210 */     return 1.0D;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public double getFuelBurn(ItemStack item)
/* 217:    */   {
/* 218:215 */     if (isPink(item)) {
/* 219:216 */       return 600.0D;
/* 220:    */     }
/* 221:218 */     return 0.0D;
/* 222:    */   }
/* 223:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorPink
 * JD-Core Version:    0.7.0.1
 */