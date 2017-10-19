/*   1:    */ package com.rwtema.extrautils.item.filters;
/*   2:    */ 
/*   3:    */ import gnu.trove.map.hash.TIntByteHashMap;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Locale;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.util.StatCollector;
/*  10:    */ import net.minecraftforge.fluids.FluidStack;
/*  11:    */ import net.minecraftforge.oredict.OreDictionary;
/*  12:    */ 
/*  13:    */ public class Matcher
/*  14:    */ {
/*  15:    */   public Type type;
/*  16:    */   public final String name;
/*  17:    */   public final String unlocalizedName;
/*  18:    */   int index;
/*  19:    */   private boolean addToNEI;
/*  20:    */   
/*  21:    */   public static String getUnlocalizedName(String name)
/*  22:    */   {
/*  23: 22 */     name = name.replaceAll("[^{A-Za-z0-9}]", "").toLowerCase(Locale.ENGLISH);
/*  24: 23 */     return "item.extrautils:nodeUpgrade.10.program." + name;
/*  25:    */   }
/*  26:    */   
/*  27:    */   private static String titleCase(String prefix)
/*  28:    */   {
/*  29: 27 */     return prefix.substring(0, 1).toUpperCase(Locale.ENGLISH) + prefix.substring(1, prefix.length());
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getLocalizedName()
/*  33:    */   {
/*  34: 31 */     if (!StatCollector.canTranslate(this.unlocalizedName)) {
/*  35: 32 */       return this.name + ".exe";
/*  36:    */     }
/*  37: 33 */     return StatCollector.translateToLocal(this.unlocalizedName);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isSelectable()
/*  41:    */   {
/*  42: 37 */     return true;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Matcher(String name)
/*  46:    */   {
/*  47: 41 */     this(name, true);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Matcher(String name, boolean addToNEI)
/*  51:    */   {
/*  52: 45 */     this.name = name;
/*  53: 46 */     this.addToNEI = addToNEI;
/*  54: 47 */     this.unlocalizedName = getUnlocalizedName(name);
/*  55: 48 */     boolean hasFluid = methodExists("matchFluid", getClass(), new Class[] { FluidStack.class });
/*  56: 49 */     boolean hasItem = methodExists("matchItem", getClass(), new Class[] { ItemStack.class });
/*  57:    */     
/*  58: 51 */     int t = (hasFluid ? 1 : 0) + (hasItem ? 2 : 0);
/*  59:    */     
/*  60: 53 */     Type type = null;
/*  61: 55 */     switch (t)
/*  62:    */     {
/*  63:    */     case 0: 
/*  64:    */     default: 
/*  65: 58 */       throw new RuntimeException("No overrided methods");
/*  66:    */     case 1: 
/*  67: 60 */       type = Type.FLUID;
/*  68: 61 */       break;
/*  69:    */     case 2: 
/*  70: 63 */       type = Type.ITEM;
/*  71: 64 */       break;
/*  72:    */     case 3: 
/*  73: 66 */       type = Type.BOTH;
/*  74:    */     }
/*  75: 69 */     this.type = type;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean methodExists(String method, Class<?> clazz, Class... classes)
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82: 74 */       clazz.getDeclaredMethod(method, classes);
/*  83: 75 */       return true;
/*  84:    */     }
/*  85:    */     catch (NoSuchMethodException e)
/*  86:    */     {
/*  87: 77 */       clazz = clazz.getSuperclass();
/*  88: 78 */       if ((clazz != Matcher.class) && (methodExists(method, clazz, classes))) {
/*  89: 78 */         tmpTernaryOp = true;
/*  90:    */       }
/*  91:    */     }
/*  92: 78 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean matchFluid(FluidStack fluid)
/*  96:    */   {
/*  97: 83 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean matchItem(ItemStack item)
/* 101:    */   {
/* 102: 87 */     return false;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean shouldAddToNEI()
/* 106:    */   {
/* 107: 91 */     return this.addToNEI;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static enum Type
/* 111:    */   {
/* 112: 95 */     FLUID,  ITEM,  BOTH;
/* 113:    */     
/* 114:    */     private Type() {}
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static class MatcherTool
/* 118:    */     extends Matcher
/* 119:    */   {
/* 120:    */     private final String tool;
/* 121:    */     
/* 122:    */     public MatcherTool(String tool)
/* 123:    */     {
/* 124:106 */       super();
/* 125:107 */       this.tool = tool;
/* 126:    */     }
/* 127:    */     
/* 128:    */     public boolean matchItem(ItemStack item)
/* 129:    */     {
/* 130:112 */       for (String s : item.getItem().getToolClasses(item)) {
/* 131:113 */         if (this.tool.equals(s)) {
/* 132:114 */           return true;
/* 133:    */         }
/* 134:    */       }
/* 135:116 */       return false;
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static class MatcherOreDic
/* 140:    */     extends Matcher
/* 141:    */   {
/* 142:    */     private final String prefix;
/* 143:123 */     private final TIntByteHashMap map = new TIntByteHashMap();
/* 144:    */     
/* 145:    */     public MatcherOreDic(String prefix)
/* 146:    */     {
/* 147:126 */       super();
/* 148:127 */       this.prefix = prefix;
/* 149:    */     }
/* 150:    */     
/* 151:    */     public boolean matchItem(ItemStack item)
/* 152:    */     {
/* 153:132 */       for (int i : OreDictionary.getOreIDs(item)) {
/* 154:133 */         if (this.map.containsKey(i))
/* 155:    */         {
/* 156:134 */           if (this.map.get(i) != 0) {
/* 157:135 */             return true;
/* 158:    */           }
/* 159:    */         }
/* 160:    */         else {
/* 161:138 */           this.map.put(i, (byte)(OreDictionary.getOreName(i).startsWith(this.prefix) ? 1 : 0));
/* 162:    */         }
/* 163:    */       }
/* 164:142 */       return false;
/* 165:    */     }
/* 166:    */     
/* 167:    */     public boolean isSelectable()
/* 168:    */     {
/* 169:147 */       for (String s : ) {
/* 170:148 */         if (s.startsWith(this.prefix)) {
/* 171:149 */           return true;
/* 172:    */         }
/* 173:    */       }
/* 174:151 */       return false;
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static abstract class MatcherItem
/* 179:    */     extends Matcher
/* 180:    */   {
/* 181:    */     HashSet<Item> entries;
/* 182:    */     
/* 183:    */     public MatcherItem(String name)
/* 184:    */     {
/* 185:159 */       super();
/* 186:    */     }
/* 187:    */     
/* 188:    */     public void buildMap()
/* 189:    */     {
/* 190:163 */       this.entries = new HashSet();
/* 191:164 */       for (Object anItemRegistry : Item.itemRegistry)
/* 192:    */       {
/* 193:165 */         Item item = (Item)anItemRegistry;
/* 194:166 */         if (matchItem(item)) {
/* 195:167 */           this.entries.add(item);
/* 196:    */         }
/* 197:    */       }
/* 198:    */     }
/* 199:    */     
/* 200:    */     public boolean matchItem(ItemStack item)
/* 201:    */     {
/* 202:174 */       if (this.entries == null) {
/* 203:174 */         buildMap();
/* 204:    */       }
/* 205:175 */       return this.entries.contains(item.getItem());
/* 206:    */     }
/* 207:    */     
/* 208:    */     public boolean isSelectable()
/* 209:    */     {
/* 210:180 */       if (this.entries == null) {
/* 211:180 */         buildMap();
/* 212:    */       }
/* 213:181 */       return !this.entries.isEmpty();
/* 214:    */     }
/* 215:    */     
/* 216:    */     protected abstract boolean matchItem(Item paramItem);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static class MatcherOreDicPair
/* 220:    */     extends Matcher
/* 221:    */   {
/* 222:    */     private final String prefix;
/* 223:    */     private final String prefix2;
/* 224:190 */     private final TIntByteHashMap map = new TIntByteHashMap();
/* 225:    */     
/* 226:    */     public MatcherOreDicPair(String prefix, String prefix2)
/* 227:    */     {
/* 228:193 */       super();
/* 229:194 */       this.prefix = prefix;
/* 230:195 */       this.prefix2 = prefix2;
/* 231:    */     }
/* 232:    */     
/* 233:    */     public boolean matchItem(ItemStack item)
/* 234:    */     {
/* 235:200 */       for (int i : OreDictionary.getOreIDs(item))
/* 236:    */       {
/* 237:206 */         String oreName = OreDictionary.getOreName(i);
/* 238:207 */         boolean isOre = oreName.startsWith(this.prefix);
/* 239:208 */         isOre = (isOre) && (oreExists(oreName.replaceFirst(this.prefix, this.prefix2)));
/* 240:209 */         this.map.put(i, (byte)(isOre ? 1 : 0));
/* 241:212 */         if (isOre) {
/* 242:212 */           return true;
/* 243:    */         }
/* 244:    */       }
/* 245:214 */       return false;
/* 246:    */     }
/* 247:    */     
/* 248:    */     public boolean oreExists(String k)
/* 249:    */     {
/* 250:218 */       for (String s : ) {
/* 251:219 */         if ((k.equals(s)) && 
/* 252:220 */           (!OreDictionary.getOres(k, false).isEmpty())) {
/* 253:221 */           return true;
/* 254:    */         }
/* 255:    */       }
/* 256:224 */       return false;
/* 257:    */     }
/* 258:    */     
/* 259:    */     public boolean isSelectable()
/* 260:    */     {
/* 261:229 */       for (String s : ) {
/* 262:230 */         if ((s.startsWith(this.prefix)) && (oreExists(s.replace(this.prefix, this.prefix2)))) {
/* 263:231 */           return true;
/* 264:    */         }
/* 265:    */       }
/* 266:233 */       return false;
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public static class InverseMatch
/* 271:    */     extends Matcher
/* 272:    */   {
/* 273:    */     private final Matcher matcher;
/* 274:    */     
/* 275:    */     public InverseMatch(String item, Matcher matcher)
/* 276:    */     {
/* 277:241 */       super();
/* 278:242 */       this.matcher = matcher;
/* 279:243 */       this.type = matcher.type;
/* 280:    */     }
/* 281:    */     
/* 282:    */     public boolean matchItem(ItemStack item)
/* 283:    */     {
/* 284:248 */       return !this.matcher.matchItem(item);
/* 285:    */     }
/* 286:    */     
/* 287:    */     public boolean matchFluid(FluidStack fluid)
/* 288:    */     {
/* 289:253 */       return !this.matcher.matchFluid(fluid);
/* 290:    */     }
/* 291:    */   }
/* 292:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.filters.Matcher
 * JD-Core Version:    0.7.0.1
 */