/*   1:    */ package com.rwtema.extrautils.item.filters;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.IEnergyContainerItem;
/*   4:    */ import com.rwtema.extrautils.LogHelper;
/*   5:    */ import com.rwtema.extrautils.asm.RemoteCallFactory;
/*   6:    */ import com.rwtema.extrautils.asm.RemoteCallFactory.IObjectEvaluate;
/*   7:    */ import cpw.mods.fml.common.Loader;
/*   8:    */ import cpw.mods.fml.common.ModAPIManager;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import net.minecraft.block.BlockDispenser;
/*  12:    */ import net.minecraft.item.Item;
/*  13:    */ import net.minecraft.item.ItemBlock;
/*  14:    */ import net.minecraft.item.ItemFood;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*  17:    */ import net.minecraft.nbt.NBTTagCompound;
/*  18:    */ import net.minecraft.util.RegistrySimple;
/*  19:    */ import net.minecraftforge.common.IPlantable;
/*  20:    */ import net.minecraftforge.fluids.FluidStack;
/*  21:    */ 
/*  22:    */ public class AdvancedNodeUpgrades
/*  23:    */ {
/*  24: 22 */   public static HashMap<String, Matcher> matcherMap = new HashMap();
/*  25: 23 */   public static ArrayList<Matcher> entryList = new ArrayList();
/*  26:    */   public static Matcher nullMatcher;
/*  27: 27 */   public static boolean problemCodePresent = (Loader.isModLoaded("gregtech")) || (Loader.isModLoaded("gregapi")) || (ModAPIManager.INSTANCE.hasAPI("gregapi"));
/*  28:    */   
/*  29:    */   static
/*  30:    */   {
/*  31: 31 */     nullMatcher = new Matcher("Default", false)
/*  32:    */     {
/*  33:    */       public boolean matchFluid(FluidStack fluid)
/*  34:    */       {
/*  35: 34 */         return true;
/*  36:    */       }
/*  37:    */       
/*  38:    */       public boolean matchItem(ItemStack fluid)
/*  39:    */       {
/*  40: 39 */         return true;
/*  41:    */       }
/*  42: 41 */     };
/*  43: 42 */     addEntry(nullMatcher);
/*  44:    */     Matcher m;
/*  45: 44 */     addEntry(m = new Matcher.MatcherItem("Block")
/*  46:    */     {
/*  47:    */       protected boolean matchItem(Item item)
/*  48:    */       {
/*  49: 48 */         return item instanceof ItemBlock;
/*  50:    */       }
/*  51: 51 */     });
/*  52: 52 */     addEntry(new Matcher.InverseMatch("Item", m));
/*  53:    */     
/*  54: 54 */     addEntry(new Matcher.MatcherItem("HasSubTypes")
/*  55:    */     {
/*  56:    */       protected boolean matchItem(Item item)
/*  57:    */       {
/*  58: 58 */         return item.getHasSubtypes();
/*  59:    */       }
/*  60: 61 */     });
/*  61: 62 */     addEntry(new Matcher("StackSize1")
/*  62:    */     {
/*  63:    */       public boolean matchItem(ItemStack item)
/*  64:    */       {
/*  65: 65 */         return item.getMaxStackSize() == 1;
/*  66:    */       }
/*  67: 68 */     });
/*  68: 69 */     addEntry(new Matcher("StackSize64")
/*  69:    */     {
/*  70:    */       public boolean matchItem(ItemStack item)
/*  71:    */       {
/*  72: 72 */         return item.getMaxStackSize() == 64;
/*  73:    */       }
/*  74: 75 */     });
/*  75: 76 */     addEntry(new Matcher.MatcherOreDic("ore"));
/*  76: 77 */     addEntry(new Matcher.MatcherOreDic("ingot"));
/*  77: 78 */     addEntry(new Matcher.MatcherOreDic("nugget"));
/*  78: 79 */     addEntry(new Matcher.MatcherOreDic("block"));
/*  79: 80 */     addEntry(new Matcher.MatcherOreDic("gem"));
/*  80: 81 */     addEntry(new Matcher.MatcherOreDic("dust"));
/*  81:    */     
/*  82: 83 */     addEntry(new Matcher.MatcherItem("EnergyItem")
/*  83:    */     {
/*  84:    */       public boolean matchItem(ItemStack item)
/*  85:    */       {
/*  86: 86 */         return (super.matchItem(item)) && (((IEnergyContainerItem)item.getItem()).getMaxEnergyStored(item) != 0);
/*  87:    */       }
/*  88:    */       
/*  89:    */       protected boolean matchItem(Item item)
/*  90:    */       {
/*  91: 91 */         return item instanceof IEnergyContainerItem;
/*  92:    */       }
/*  93: 94 */     });
/*  94: 95 */     addEntry(new Matcher.MatcherItem("EnergyItemEmpty")
/*  95:    */     {
/*  96:    */       public boolean matchItem(ItemStack item)
/*  97:    */       {
/*  98: 98 */         return (super.matchItem(item)) && (((IEnergyContainerItem)item.getItem()).getMaxEnergyStored(item) > 0) && (((IEnergyContainerItem)item.getItem()).getEnergyStored(item) == 0);
/*  99:    */       }
/* 100:    */       
/* 101:    */       protected boolean matchItem(Item item)
/* 102:    */       {
/* 103:103 */         return item instanceof IEnergyContainerItem;
/* 104:    */       }
/* 105:106 */     });
/* 106:107 */     addEntry(new Matcher.MatcherItem("EnergyItem<50")
/* 107:    */     {
/* 108:    */       public boolean matchItem(ItemStack item)
/* 109:    */       {
/* 110:110 */         if (!super.matchItem(item)) {
/* 111:110 */           return false;
/* 112:    */         }
/* 113:111 */         IEnergyContainerItem energyContainerItem = (IEnergyContainerItem)item.getItem();
/* 114:112 */         int maxEnergyStored = energyContainerItem.getMaxEnergyStored(item);
/* 115:113 */         return (maxEnergyStored > 0) && (energyContainerItem.getEnergyStored(item) <= maxEnergyStored >> 1);
/* 116:    */       }
/* 117:    */       
/* 118:    */       protected boolean matchItem(Item item)
/* 119:    */       {
/* 120:119 */         return item instanceof IEnergyContainerItem;
/* 121:    */       }
/* 122:123 */     });
/* 123:124 */     addEntry(new Matcher.MatcherItem("EnergyItemFull")
/* 124:    */     {
/* 125:    */       public boolean matchItem(ItemStack item)
/* 126:    */       {
/* 127:127 */         if (!super.matchItem(item)) {
/* 128:127 */           return false;
/* 129:    */         }
/* 130:128 */         IEnergyContainerItem energyContainerItem = (IEnergyContainerItem)item.getItem();
/* 131:129 */         int maxEnergyStored = energyContainerItem.getMaxEnergyStored(item);
/* 132:130 */         return (maxEnergyStored != 0) && (energyContainerItem.getEnergyStored(item) == maxEnergyStored);
/* 133:    */       }
/* 134:    */       
/* 135:    */       protected boolean matchItem(Item item)
/* 136:    */       {
/* 137:135 */         return item instanceof IEnergyContainerItem;
/* 138:    */       }
/* 139:139 */     });
/* 140:140 */     addEntry(new Matcher.MatcherItem("Food")
/* 141:    */     {
/* 142:    */       protected boolean matchItem(Item item)
/* 143:    */       {
/* 144:143 */         return item instanceof ItemFood;
/* 145:    */       }
/* 146:147 */     });
/* 147:148 */     addEntry(new Matcher("Smeltable")
/* 148:    */     {
/* 149:    */       public boolean matchItem(ItemStack item)
/* 150:    */       {
/* 151:151 */         return FurnaceRecipes.smelting().func_151395_a(item) != null;
/* 152:    */       }
/* 153:    */     });
/* 154:155 */     if (RemoteCallFactory.pulverizer != null) {
/* 155:156 */       addEntry(new Matcher("Pulverizer")
/* 156:    */       {
/* 157:    */         public boolean matchItem(ItemStack item)
/* 158:    */         {
/* 159:159 */           return RemoteCallFactory.pulverizer.evaluate(item);
/* 160:    */         }
/* 161:    */       });
/* 162:    */     }
/* 163:164 */     addEntry(new Matcher("Enchanted")
/* 164:    */     {
/* 165:    */       public boolean matchItem(ItemStack item)
/* 166:    */       {
/* 167:168 */         return item.isItemEnchanted();
/* 168:    */       }
/* 169:171 */     });
/* 170:172 */     addEntry(new Matcher("Enchantable")
/* 171:    */     {
/* 172:    */       public boolean matchItem(ItemStack item)
/* 173:    */       {
/* 174:175 */         return item.isItemEnchantable();
/* 175:    */       }
/* 176:    */     });
/* 177:180 */     if (!problemCodePresent) {
/* 178:181 */       addEntry(new Matcher("HasContainerItem")
/* 179:    */       {
/* 180:    */         public boolean matchItem(ItemStack item)
/* 181:    */         {
/* 182:185 */           return item.getItem().hasContainerItem(item);
/* 183:    */         }
/* 184:    */       });
/* 185:    */     }
/* 186:190 */     addEntry(new Matcher("DurabilityBarShown")
/* 187:    */     {
/* 188:    */       public boolean matchItem(ItemStack item)
/* 189:    */       {
/* 190:193 */         return item.getItem().showDurabilityBar(item);
/* 191:    */       }
/* 192:196 */     });
/* 193:197 */     addEntry(new Matcher("DurabilityBarFull")
/* 194:    */     {
/* 195:    */       public boolean matchItem(ItemStack stack)
/* 196:    */       {
/* 197:200 */         Item item = stack.getItem();
/* 198:201 */         return (item.showDurabilityBar(stack)) && (item.getDurabilityForDisplay(stack) <= 0.0D);
/* 199:    */       }
/* 200:204 */     });
/* 201:205 */     addEntry(new Matcher("DurabilityBar<90", false)
/* 202:    */     {
/* 203:    */       public boolean matchItem(ItemStack stack)
/* 204:    */       {
/* 205:208 */         Item item = stack.getItem();
/* 206:209 */         return (item.showDurabilityBar(stack)) && (item.getDurabilityForDisplay(stack) >= 0.5D);
/* 207:    */       }
/* 208:212 */     });
/* 209:213 */     addEntry(new Matcher("DurabilityBar<50", false)
/* 210:    */     {
/* 211:    */       public boolean matchItem(ItemStack stack)
/* 212:    */       {
/* 213:216 */         Item item = stack.getItem();
/* 214:217 */         return (item.showDurabilityBar(stack)) && (item.getDurabilityForDisplay(stack) >= 0.5D);
/* 215:    */       }
/* 216:220 */     });
/* 217:221 */     addEntry(new Matcher("DurabilityBar<10", false)
/* 218:    */     {
/* 219:    */       public boolean matchItem(ItemStack stack)
/* 220:    */       {
/* 221:224 */         Item item = stack.getItem();
/* 222:225 */         return (item.showDurabilityBar(stack)) && (item.getDurabilityForDisplay(stack) >= 0.9D);
/* 223:    */       }
/* 224:228 */     });
/* 225:229 */     addEntry(new Matcher("DurabilityBarEmpty")
/* 226:    */     {
/* 227:    */       public boolean matchItem(ItemStack stack)
/* 228:    */       {
/* 229:232 */         Item item = stack.getItem();
/* 230:233 */         return (item.showDurabilityBar(stack)) && (item.getDurabilityForDisplay(stack) >= 1.0D);
/* 231:    */       }
/* 232:237 */     });
/* 233:238 */     addEntry(new Matcher("HasDisplayName", false)
/* 234:    */     {
/* 235:    */       public boolean matchItem(ItemStack stack)
/* 236:    */       {
/* 237:241 */         return stack.hasDisplayName();
/* 238:    */       }
/* 239:244 */     });
/* 240:245 */     addEntry(new Matcher("Repairable")
/* 241:    */     {
/* 242:    */       public boolean matchItem(ItemStack stack)
/* 243:    */       {
/* 244:248 */         Item item = stack.getItem();
/* 245:249 */         return item.isRepairable();
/* 246:    */       }
/* 247:252 */     });
/* 248:253 */     addEntry(new Matcher.MatcherItem("HasDispenserBehaviour")
/* 249:    */     {
/* 250:    */       public boolean matchItem(Item item)
/* 251:    */       {
/* 252:256 */         return ((RegistrySimple)BlockDispenser.dispenseBehaviorRegistry).containsKey(item);
/* 253:    */       }
/* 254:259 */     });
/* 255:260 */     addEntry(new Matcher.MatcherItem("Plantable")
/* 256:    */     {
/* 257:    */       protected boolean matchItem(Item item)
/* 258:    */       {
/* 259:263 */         return item instanceof IPlantable;
/* 260:    */       }
/* 261:    */     });
/* 262:268 */     if (LogHelper.isDeObf)
/* 263:    */     {
/* 264:269 */       StringBuilder builder = new StringBuilder();
/* 265:270 */       for (Matcher matcher : entryList)
/* 266:    */       {
/* 267:271 */         builder.append('\n');
/* 268:272 */         builder.append(matcher.unlocalizedName);
/* 269:273 */         builder.append("=");
/* 270:274 */         builder.append(matcher.name);
/* 271:275 */         builder.append(".exe");
/* 272:    */       }
/* 273:277 */       LogHelper.info(builder.toString(), new Object[0]);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public static Matcher getMatcher(ItemStack itemStack)
/* 278:    */   {
/* 279:283 */     if (itemStack.hasTagCompound())
/* 280:    */     {
/* 281:284 */       Matcher matcher = (Matcher)matcherMap.get(itemStack.getTagCompound().getString("Matcher"));
/* 282:285 */       if (matcher != null) {
/* 283:285 */         return matcher;
/* 284:    */       }
/* 285:    */     }
/* 286:287 */     return nullMatcher;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public static Matcher nextEntry(ItemStack itemStack, boolean next)
/* 290:    */   {
/* 291:292 */     int i = 0;
/* 292:293 */     if (!itemStack.hasTagCompound()) {
/* 293:294 */       itemStack.setTagCompound(new NBTTagCompound());
/* 294:    */     }
/* 295:296 */     NBTTagCompound tags = itemStack.getTagCompound();
/* 296:    */     
/* 297:298 */     Matcher matcher = (Matcher)matcherMap.get(tags.getString("Matcher"));
/* 298:299 */     if (matcher != null) {
/* 299:300 */       i = matcher.index;
/* 300:    */     }
/* 301:    */     do
/* 302:    */     {
/* 303:303 */       if (next)
/* 304:    */       {
/* 305:304 */         i++;
/* 306:305 */         if (i >= entryList.size()) {
/* 307:305 */           i = 0;
/* 308:    */         }
/* 309:    */       }
/* 310:    */       else
/* 311:    */       {
/* 312:307 */         i--;
/* 313:308 */         if (i < 0) {
/* 314:308 */           i = entryList.size() - 1;
/* 315:    */         }
/* 316:    */       }
/* 317:310 */       matcher = (Matcher)entryList.get(i);
/* 318:311 */     } while (!matcher.isSelectable());
/* 319:313 */     tags.setString("Matcher", matcher.name);
/* 320:314 */     return matcher;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static void addEntry(Matcher matcher)
/* 324:    */   {
/* 325:318 */     String entry = matcher.name;
/* 326:319 */     matcher.index = entryList.size();
/* 327:320 */     entryList.add(matcher);
/* 328:321 */     matcherMap.put(entry, matcher);
/* 329:    */   }
/* 330:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.filters.AdvancedNodeUpgrades
 * JD-Core Version:    0.7.0.1
 */