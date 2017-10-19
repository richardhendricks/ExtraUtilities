/*   1:    */ package com.rwtema.extrautils.helper;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.block.BlockColor;
/*   5:    */ import com.rwtema.extrautils.block.BlockSpike;
/*   6:    */ import com.rwtema.extrautils.block.BlockSpikeWood;
/*   7:    */ import cpw.mods.fml.common.Loader;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Comparator;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraftforge.oredict.OreDictionary;
/*  17:    */ import thaumcraft.api.ThaumcraftApi;
/*  18:    */ import thaumcraft.api.ThaumcraftApiHelper;
/*  19:    */ import thaumcraft.api.aspects.Aspect;
/*  20:    */ import thaumcraft.api.aspects.AspectList;
/*  21:    */ 
/*  22:    */ public class ThaumcraftHelper
/*  23:    */ {
/*  24: 23 */   public static final int[] pi = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8, 4, 6, 2, 6, 4, 3, 3, 8, 3, 2, 7, 9, 5 };
/*  25:    */   
/*  26:    */   public static void registerItems()
/*  27:    */   {
/*  28: 27 */     if (Loader.isModLoaded("Thaumcraft")) {
/*  29: 28 */       registerItems_do();
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   private static void registerItems_do()
/*  34:    */   {
/*  35: 33 */     addAspectsDivSigil();
/*  36:    */     
/*  37: 35 */     addAspectRecipe(ExtraUtils.unstableIngot, new Object[] { Aspect.METAL, Integer.valueOf(4), Aspect.ELDRITCH, Integer.valueOf(4), Aspect.ENERGY, Integer.valueOf(16) });
/*  38: 36 */     addAspectRecipe(ExtraUtils.cursedEarth, new Object[] { Aspect.EARTH, Integer.valueOf(1), Aspect.DARKNESS, Integer.valueOf(1), Aspect.UNDEAD, Integer.valueOf(4), Aspect.ELDRITCH, Integer.valueOf(1), Aspect.EXCHANGE, Integer.valueOf(1) });
/*  39: 37 */     addAspectRecipe(ExtraUtils.enderLily, new Object[] { Aspect.DARKNESS, Integer.valueOf(1), Items.wheat_seeds, Items.ender_pearl, Aspect.ELDRITCH, Integer.valueOf(16) });
/*  40: 38 */     addAspectRecipe(ExtraUtils.transferPipe, new Object[] { { 0, 1, 2, 3, 4, 5, 6, 7 }, Aspect.TRAVEL, Integer.valueOf(1), Aspect.ORDER, Integer.valueOf(1), Aspect.EARTH, Integer.valueOf(1) });
/*  41: 39 */     addAspectRecipe(ExtraUtils.buildersWand, new Object[] { Aspect.TOOL, Integer.valueOf(4), Blocks.obsidian, Aspect.ELDRITCH, Integer.valueOf(4) });
/*  42: 40 */     addAspectRecipe(ExtraUtils.buildersWand, new Object[] { Integer.valueOf(32767), Aspect.TOOL, Integer.valueOf(4), Blocks.obsidian, Aspect.ELDRITCH, Integer.valueOf(4), Items.nether_star });
/*  43: 41 */     addAspectRecipe(ExtraUtils.trashCan, new Object[] { Aspect.VOID, Integer.valueOf(8), Blocks.cobblestone, Aspect.ENTROPY, Integer.valueOf(4) });
/*  44: 43 */     if (ExtraUtils.spike != null) {
/*  45: 44 */       addAspectRecipe(ExtraUtils.spike.itemSpike, new Object[] { Integer.valueOf(-1), null, Aspect.WEAPON, Integer.valueOf(18), Aspect.METAL, Integer.valueOf(14) });
/*  46:    */     }
/*  47: 45 */     if (ExtraUtils.spikeDiamond != null) {
/*  48: 46 */       addAspectRecipe(ExtraUtils.spikeDiamond.itemSpike, new Object[] { Integer.valueOf(-1), null, Aspect.WEAPON, Integer.valueOf(18), Aspect.METAL, Integer.valueOf(14) });
/*  49:    */     }
/*  50: 47 */     if (ExtraUtils.spikeGold != null) {
/*  51: 48 */       addAspectRecipe(ExtraUtils.spikeGold.itemSpike, new Object[] { Integer.valueOf(-1), null, Aspect.WEAPON, Integer.valueOf(18), Aspect.METAL, Integer.valueOf(14) });
/*  52:    */     }
/*  53: 49 */     if (ExtraUtils.spikeWood != null) {
/*  54: 50 */       addAspectRecipe(ExtraUtils.spikeWood.itemSpike, new Object[] { Integer.valueOf(-1), null, Aspect.WEAPON, Integer.valueOf(18), Aspect.METAL, Integer.valueOf(14) });
/*  55:    */     }
/*  56: 51 */     addAspectRecipe(ExtraUtils.wateringCan, new Object[] { Integer.valueOf(-1), new ItemStack(ExtraUtils.wateringCan, 1, 1), Aspect.WATER, Integer.valueOf(1), Aspect.LIFE, Integer.valueOf(1), Aspect.EARTH, Integer.valueOf(2) });
/*  57: 52 */     addAspectRecipe(ExtraUtils.conveyor, new Object[] { Integer.valueOf(-1), null, Blocks.rail, Aspect.TRAVEL, Integer.valueOf(4) });
/*  58: 54 */     if (ExtraUtils.decorative1 != null)
/*  59:    */     {
/*  60: 55 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 4), new Object[] { Blocks.stonebrick });
/*  61: 56 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 7), new Object[] { Blocks.stonebrick });
/*  62: 57 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 0), new Object[] { Blocks.stonebrick });
/*  63: 58 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 1), new Object[] { Blocks.obsidian, Items.ender_pearl });
/*  64: 59 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 2), new Object[] { Blocks.quartz_block, Aspect.FIRE, Integer.valueOf(4) });
/*  65: 60 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 3), new Object[] { Blocks.stone, Blocks.ice });
/*  66: 61 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 6), new Object[] { Blocks.gravel });
/*  67: 62 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 10), new Object[] { Blocks.gravel, Blocks.gravel, Aspect.TRAVEL, Integer.valueOf(1) });
/*  68: 63 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 9), new Object[] { Blocks.sand, Blocks.glass });
/*  69: 64 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 13), new Object[] { Blocks.sand, Blocks.end_stone });
/*  70: 65 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 14), new Object[] { Blocks.stonebrick, Aspect.SENSES, Integer.valueOf(2), Aspect.ELDRITCH, Integer.valueOf(2) });
/*  71: 66 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 8), new Object[] { null, Aspect.MAGIC, Integer.valueOf(16), Aspect.METAL, Integer.valueOf(4), Aspect.GREED, Integer.valueOf(4), Aspect.MIND, Integer.valueOf(8), Aspect.TREE, Integer.valueOf(8) });
/*  72: 67 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative1, 1, 11), new Object[] { null, Aspect.ELDRITCH, Integer.valueOf(16) });
/*  73:    */     }
/*  74: 70 */     if (ExtraUtils.decorative2 != null)
/*  75:    */     {
/*  76: 71 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative2, 1, 10), new Object[] { Aspect.CRYSTAL, Integer.valueOf(2), Aspect.DARKNESS, Integer.valueOf(4) });
/*  77: 72 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative2, 1, 0), new Object[] { Aspect.CRYSTAL, Integer.valueOf(2) });
/*  78: 73 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative2, 1, 3), new Object[] { Aspect.CRYSTAL, Integer.valueOf(2), Aspect.ENTROPY, Integer.valueOf(1) });
/*  79: 74 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative2, 1, 3), new Object[] { Aspect.CRYSTAL, Integer.valueOf(2), Aspect.ENTROPY, Integer.valueOf(1) });
/*  80: 75 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative2, 1, 4), new Object[] { Aspect.CRYSTAL, Integer.valueOf(2), Aspect.GREED, Integer.valueOf(1) });
/*  81: 76 */       addAspectRecipe(new ItemStack(ExtraUtils.decorative2, 1, 8), new Object[] { Aspect.CRYSTAL, Integer.valueOf(2), Aspect.LIFE, Integer.valueOf(1), Aspect.HEAL, Integer.valueOf(1) });
/*  82:    */     }
/*  83: 79 */     for (BlockColor colorblock : ExtraUtils.colorblocks)
/*  84:    */     {
/*  85: 80 */       AspectList aspectList = new AspectList(new ItemStack(colorblock.baseBlock, 1));
/*  86: 81 */       if (aspectList.visSize() > 0) {
/*  87: 82 */         for (int i = 0; i < 16; i++) {
/*  88: 83 */           addAspectRecipe(new ItemStack(colorblock, 1, i), new Object[] { aspectList, Aspect.SENSES, Integer.valueOf(1) });
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92: 88 */     addAspectRecipe(ExtraUtils.lawSword, new Object[] { Aspect.WEAPON, Integer.valueOf(64), Aspect.LIFE, Integer.valueOf(32), Aspect.MAGIC, Integer.valueOf(16) });
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static void handleQEDRecipes(ArrayList<ItemStack> items)
/*  96:    */   {
/*  97: 94 */     handleQEDRecipes_do(items);
/*  98:    */   }
/*  99:    */   
/* 100:    */   private static void handleQEDRecipes_do(ArrayList<ItemStack> items)
/* 101:    */   {
/* 102: 98 */     for (ItemStack item : items) {
/* 103: 99 */       ThaumcraftApiHelper.getObjectAspects(item);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   private static void addAspectsDivSigil()
/* 108:    */   {
/* 109:104 */     if (ExtraUtils.divisionSigil == null) {
/* 110:105 */       return;
/* 111:    */     }
/* 112:107 */     ArrayList<Aspect> a = new ArrayList();
/* 113:108 */     a.add(Aspect.AURA);
/* 114:109 */     a.add(Aspect.EXCHANGE);
/* 115:110 */     a.add(Aspect.TOOL);
/* 116:111 */     a.add(Aspect.CRAFT);
/* 117:112 */     a.add(Aspect.ELDRITCH);
/* 118:113 */     a.add(Aspect.SOUL);
/* 119:    */     
/* 120:115 */     Collections.sort(a, new Comparator()
/* 121:    */     {
/* 122:    */       public int compare(Aspect o1, Aspect o2)
/* 123:    */       {
/* 124:118 */         return o1.getTag().compareTo(o2.getTag());
/* 125:    */       }
/* 126:121 */     });
/* 127:122 */     AspectList b = new AspectList();
/* 128:124 */     for (int i = 0; i < a.size(); i++) {
/* 129:125 */       b.add((Aspect)a.get(i), pi[i]);
/* 130:    */     }
/* 131:128 */     ThaumcraftApi.registerObjectTag(new ItemStack(ExtraUtils.divisionSigil, 1, 32767), b);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private static void addAspectRecipe(Block block, Object... ingredients)
/* 135:    */   {
/* 136:132 */     if (block != null) {
/* 137:133 */       addAspectRecipe(new ItemStack(block), ingredients);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private static void addAspectRecipe(Item item, Object... ingredients)
/* 142:    */   {
/* 143:137 */     if (item != null) {
/* 144:138 */       addAspectRecipe(new ItemStack(item), ingredients);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   private static void addAspectRecipe(ItemStack result, Object... ingredients)
/* 149:    */   {
/* 150:142 */     if ((result == null) || (result.getItem() == null)) {
/* 151:143 */       return;
/* 152:    */     }
/* 153:145 */     int[] meta = null;
/* 154:    */     
/* 155:147 */     AspectList al = new AspectList(result);
/* 156:148 */     for (int i = 0; i < ingredients.length; i++)
/* 157:    */     {
/* 158:149 */       Object o = ingredients[i];
/* 159:151 */       if (o == null)
/* 160:    */       {
/* 161:152 */         al.add(new AspectList(result));
/* 162:    */       }
/* 163:153 */       else if ((i == 0) && ((o instanceof Integer)))
/* 164:    */       {
/* 165:154 */         int newmeta = ((Integer)o).intValue();
/* 166:155 */         if (newmeta == -1) {
/* 167:155 */           newmeta = 32767;
/* 168:    */         }
/* 169:156 */         result.setItemDamage(newmeta);
/* 170:    */       }
/* 171:157 */       else if ((o instanceof int[]))
/* 172:    */       {
/* 173:158 */         meta = (int[])o;
/* 174:    */       }
/* 175:159 */       else if ((o instanceof AspectList))
/* 176:    */       {
/* 177:160 */         al.add((AspectList)o);
/* 178:    */       }
/* 179:161 */       else if ((o instanceof Aspect))
/* 180:    */       {
/* 181:162 */         Aspect a = (Aspect)o;
/* 182:163 */         int a_num = 1;
/* 183:164 */         if ((i + 1 < ingredients.length) && ((ingredients[(i + 1)] instanceof Integer)))
/* 184:    */         {
/* 185:165 */           a_num = ((Integer)ingredients[(i + 1)]).intValue();
/* 186:166 */           i++;
/* 187:    */         }
/* 188:168 */         al.add(a, a_num);
/* 189:    */       }
/* 190:169 */       else if ((o instanceof ItemStack))
/* 191:    */       {
/* 192:170 */         AspectList aspectList = new AspectList((ItemStack)o);
/* 193:171 */         al.add(aspectList);
/* 194:    */       }
/* 195:172 */       else if ((o instanceof Item))
/* 196:    */       {
/* 197:173 */         AspectList aspectList = new AspectList(new ItemStack((Item)o));
/* 198:174 */         al.add(aspectList);
/* 199:    */       }
/* 200:175 */       else if ((o instanceof Block))
/* 201:    */       {
/* 202:176 */         AspectList aspectList = new AspectList(new ItemStack((Block)o));
/* 203:177 */         al.add(aspectList);
/* 204:    */       }
/* 205:178 */       else if ((o instanceof String))
/* 206:    */       {
/* 207:179 */         AspectList aspectList = new AspectList();
/* 208:180 */         for (ItemStack itemStack : OreDictionary.getOres((String)o)) {
/* 209:181 */           aspectList.merge(new AspectList(itemStack));
/* 210:    */         }
/* 211:183 */         al.add(aspectList);
/* 212:    */       }
/* 213:    */     }
/* 214:188 */     if (meta != null) {
/* 215:189 */       ThaumcraftApi.registerObjectTag(result, meta, al);
/* 216:    */     } else {
/* 217:191 */       ThaumcraftApi.registerObjectTag(result, al);
/* 218:    */     }
/* 219:    */   }
/* 220:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.ThaumcraftHelper
 * JD-Core Version:    0.7.0.1
 */