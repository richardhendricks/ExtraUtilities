/*   1:    */ package com.rwtema.extrautils.item.scanner;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.IEnergyHandler;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper.NBTIds;
/*   5:    */ import java.io.ByteArrayOutputStream;
/*   6:    */ import java.io.DataOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Comparator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Set;
/*  13:    */ import net.minecraft.entity.Entity;
/*  14:    */ import net.minecraft.entity.EntityLivingBase;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  17:    */ import net.minecraft.inventory.IInventory;
/*  18:    */ import net.minecraft.inventory.ISidedInventory;
/*  19:    */ import net.minecraft.nbt.CompressedStreamTools;
/*  20:    */ import net.minecraft.nbt.NBTBase;
/*  21:    */ import net.minecraft.nbt.NBTTagCompound;
/*  22:    */ import net.minecraft.nbt.NBTTagList;
/*  23:    */ import net.minecraft.tileentity.TileEntity;
/*  24:    */ import net.minecraftforge.common.IShearable;
/*  25:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  26:    */ import net.minecraftforge.fluids.Fluid;
/*  27:    */ import net.minecraftforge.fluids.FluidStack;
/*  28:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  29:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  30:    */ 
/*  31:    */ public class ScannerRegistry
/*  32:    */ {
/*  33: 29 */   public static List<IScanner> scanners = new ArrayList();
/*  34: 30 */   private static boolean isSorted = false;
/*  35:    */   
/*  36:    */   static
/*  37:    */   {
/*  38: 33 */     addScanner(new scanTE());
/*  39: 34 */     addScanner(new scanEntity());
/*  40: 35 */     addScanner(new scanInv());
/*  41: 36 */     addScanner(new scanSidedInv());
/*  42: 37 */     addScanner(new scanTank());
/*  43:    */     
/*  44:    */ 
/*  45: 40 */     addScanner(new scanTE3Power());
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void addScanner(IScanner scan)
/*  49:    */   {
/*  50: 45 */     scanners.add(scan);
/*  51: 46 */     isSorted = false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void sort()
/*  55:    */   {
/*  56: 50 */     Collections.sort(scanners, new SortScanners());
/*  57: 51 */     isSorted = true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static List<String> getData(Object obj, ForgeDirection side, EntityPlayer player)
/*  61:    */   {
/*  62: 55 */     List<String> data = new ArrayList();
/*  63: 57 */     if (!isSorted) {
/*  64: 58 */       sort();
/*  65:    */     }
/*  66: 61 */     for (IScanner scan : scanners) {
/*  67: 62 */       if (scan.getTargetClass().isAssignableFrom(obj.getClass())) {
/*  68: 63 */         scan.addData(obj, data, side, player);
/*  69:    */       }
/*  70:    */     }
/*  71: 66 */     return data;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static class SortScanners
/*  75:    */     implements Comparator<IScanner>
/*  76:    */   {
/*  77:    */     public int compare(IScanner arg0, IScanner arg1)
/*  78:    */     {
/*  79: 72 */       int a = arg0.getPriority();
/*  80: 73 */       int b = arg1.getPriority();
/*  81: 75 */       if (a == b) {
/*  82: 76 */         return 0;
/*  83:    */       }
/*  84: 77 */       if (a < b) {
/*  85: 78 */         return -1;
/*  86:    */       }
/*  87: 80 */       return 1;
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static class scanTE
/*  92:    */     implements IScanner
/*  93:    */   {
/*  94:    */     public Class getTargetClass()
/*  95:    */     {
/*  96: 88 */       return TileEntity.class;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public void addData(Object tile, List<String> data, ForgeDirection side, EntityPlayer player)
/* 100:    */     {
/* 101: 93 */       NBTTagCompound tags = new NBTTagCompound();
/* 102: 94 */       ((TileEntity)tile).writeToNBT(tags);
/* 103: 95 */       data.add("~~ " + tags.getString("id") + " ~~");
/* 104:    */       
/* 105:    */ 
/* 106: 98 */       ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 107: 99 */       DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
/* 108:    */       try
/* 109:    */       {
/* 110:    */         try
/* 111:    */         {
/* 112:103 */           CompressedStreamTools.write(tags, dataoutputstream);
/* 113:104 */           data.add("Tile Data: " + bytearrayoutputstream.size());
/* 114:    */         }
/* 115:    */         finally
/* 116:    */         {
/* 117:106 */           dataoutputstream.close();
/* 118:    */         }
/* 119:    */       }
/* 120:    */       catch (IOException ignored) {}
/* 121:112 */       if (player.capabilities.isCreativeMode) {
/* 122:113 */         data.addAll(getString(tags));
/* 123:    */       }
/* 124:    */     }
/* 125:    */     
/* 126:    */     public List<String> getString(NBTTagCompound tag)
/* 127:    */     {
/* 128:118 */       List<String> v = new ArrayList();
/* 129:119 */       appendStrings(v, tag, "", "Tags");
/* 130:120 */       return v;
/* 131:    */     }
/* 132:    */     
/* 133:    */     public void appendStrings(List<String> strings, NBTBase nbt, String prefix, String name)
/* 134:    */     {
/* 135:124 */       if (nbt.getId() == XUHelper.NBTIds.NBT.id)
/* 136:    */       {
/* 137:125 */         NBTTagCompound tag = (NBTTagCompound)nbt;
/* 138:126 */         if (tag.func_150296_c().isEmpty())
/* 139:    */         {
/* 140:127 */           strings.add(prefix + name + " = NBT{}");
/* 141:    */         }
/* 142:    */         else
/* 143:    */         {
/* 144:129 */           strings.add(prefix + name + " = NBT{");
/* 145:    */           
/* 146:131 */           ArrayList<String> l = new ArrayList();
/* 147:132 */           l.addAll(tag.func_150296_c());
/* 148:133 */           Collections.sort(l);
/* 149:134 */           for (String key : l) {
/* 150:135 */             appendStrings(strings, tag.getTag(key), prefix + "   ", key);
/* 151:    */           }
/* 152:140 */           strings.add(prefix + "}");
/* 153:    */         }
/* 154:    */       }
/* 155:142 */       else if (nbt.getId() == XUHelper.NBTIds.List.id)
/* 156:    */       {
/* 157:143 */         NBTTagList tag = (NBTTagList)nbt;
/* 158:145 */         if (tag.tagCount() == 0)
/* 159:    */         {
/* 160:146 */           strings.add(prefix + name + " = List{}");
/* 161:    */         }
/* 162:    */         else
/* 163:    */         {
/* 164:148 */           strings.add(prefix + name + " = List{");
/* 165:149 */           for (int i = 0; i < tag.tagCount(); i++) {
/* 166:150 */             appendStrings(strings, tag.getCompoundTagAt(i), prefix + "   ", i + "");
/* 167:    */           }
/* 168:156 */           strings.add(prefix + "}");
/* 169:    */         }
/* 170:    */       }
/* 171:    */       else
/* 172:    */       {
/* 173:159 */         strings.add(prefix + name + " = " + nbt.toString());
/* 174:    */       }
/* 175:    */     }
/* 176:    */     
/* 177:    */     public int getPriority()
/* 178:    */     {
/* 179:166 */       return -2147483647;
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static class scanEntity
/* 184:    */     implements IScanner
/* 185:    */   {
/* 186:    */     public Class getTargetClass()
/* 187:    */     {
/* 188:173 */       return Entity.class;
/* 189:    */     }
/* 190:    */     
/* 191:    */     public void addData(Object tile, List<String> data, ForgeDirection side, EntityPlayer player)
/* 192:    */     {
/* 193:178 */       NBTTagCompound tags = new NBTTagCompound();
/* 194:180 */       if (((Entity)tile).writeToNBTOptional(tags))
/* 195:    */       {
/* 196:181 */         data.add("~~ " + tags.getString("id") + " ~~");
/* 197:182 */         data.add("Entity Data: " + tags.toString().length());
/* 198:    */       }
/* 199:    */     }
/* 200:    */     
/* 201:    */     public int getPriority()
/* 202:    */     {
/* 203:188 */       return -2147483648;
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static class scanEntityLiv
/* 208:    */     implements IScanner
/* 209:    */   {
/* 210:    */     public Class getTargetClass()
/* 211:    */     {
/* 212:195 */       return EntityLivingBase.class;
/* 213:    */     }
/* 214:    */     
/* 215:    */     public void addData(Object target, List<String> data, ForgeDirection side, EntityPlayer player)
/* 216:    */     {
/* 217:200 */       EntityLivingBase e = (EntityLivingBase)target;
/* 218:201 */       data.add(e.getHealth() + " / " + e.getMaxHealth());
/* 219:    */     }
/* 220:    */     
/* 221:    */     public int getPriority()
/* 222:    */     {
/* 223:206 */       return -110;
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static class scanInv
/* 228:    */     implements IScanner
/* 229:    */   {
/* 230:    */     public Class getTargetClass()
/* 231:    */     {
/* 232:213 */       return IInventory.class;
/* 233:    */     }
/* 234:    */     
/* 235:    */     public void addData(Object tile, List<String> data, ForgeDirection side, EntityPlayer player)
/* 236:    */     {
/* 237:218 */       int n = ((IInventory)tile).getSizeInventory();
/* 238:220 */       if (n > 0)
/* 239:    */       {
/* 240:221 */         int k = 0;
/* 241:223 */         for (int i = 0; i < n; i++) {
/* 242:224 */           if (((IInventory)tile).getStackInSlot(i) != null) {
/* 243:225 */             k++;
/* 244:    */           }
/* 245:    */         }
/* 246:228 */         data.add("Inventory Slots: " + k + " / " + n);
/* 247:    */       }
/* 248:    */     }
/* 249:    */     
/* 250:    */     public int getPriority()
/* 251:    */     {
/* 252:234 */       return -100;
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public static class scanSidedInv
/* 257:    */     implements IScanner
/* 258:    */   {
/* 259:    */     public Class getTargetClass()
/* 260:    */     {
/* 261:241 */       return ISidedInventory.class;
/* 262:    */     }
/* 263:    */     
/* 264:    */     public void addData(Object tile, List<String> data, ForgeDirection side, EntityPlayer player)
/* 265:    */     {
/* 266:246 */       int[] slots = ((ISidedInventory)tile).getAccessibleSlotsFromSide(side.ordinal());
/* 267:247 */       int k = 0;
/* 268:249 */       if (slots.length > 0)
/* 269:    */       {
/* 270:250 */         for (int i = 0; i < slots.length; i++) {
/* 271:251 */           if (((ISidedInventory)tile).getStackInSlot(i) != null) {
/* 272:252 */             k++;
/* 273:    */           }
/* 274:    */         }
/* 275:255 */         data.add("Inventory Side Slots: " + k + " / " + slots.length);
/* 276:    */       }
/* 277:    */     }
/* 278:    */     
/* 279:    */     public int getPriority()
/* 280:    */     {
/* 281:261 */       return -99;
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public static class scanTank
/* 286:    */     implements IScanner
/* 287:    */   {
/* 288:    */     public Class getTargetClass()
/* 289:    */     {
/* 290:268 */       return IFluidHandler.class;
/* 291:    */     }
/* 292:    */     
/* 293:    */     public void addData(Object tile, List<String> data, ForgeDirection side, EntityPlayer player)
/* 294:    */     {
/* 295:273 */       FluidTankInfo[] tanks = ((IFluidHandler)tile).getTankInfo(side);
/* 296:275 */       if (tanks != null) {
/* 297:276 */         if (tanks.length == 1)
/* 298:    */         {
/* 299:277 */           if ((tanks[0].fluid != null) && (tanks[0].fluid.amount > 0)) {
/* 300:278 */             data.add("Fluid Tank: " + tanks[0].fluid.getFluid().getLocalizedName(tanks[0].fluid) + " - " + tanks[0].fluid.amount + " / " + tanks[0].capacity);
/* 301:    */           } else {
/* 302:280 */             data.add("Fluid Tank: Empty - 0 / " + tanks[0].capacity);
/* 303:    */           }
/* 304:    */         }
/* 305:    */         else {
/* 306:283 */           for (int i = 0; i < tanks.length; i++) {
/* 307:284 */             if ((tanks[i].fluid != null) && (tanks[i].fluid.amount > 0)) {
/* 308:285 */               data.add("Fluid Tank " + i + ": " + tanks[i].fluid.getFluid().getLocalizedName(tanks[i].fluid) + " - " + tanks[i].fluid.amount + " / " + tanks[i].capacity);
/* 309:    */             } else {
/* 310:287 */               data.add("Fluid Tank " + i + ": Empty - 0 / " + tanks[i].capacity);
/* 311:    */             }
/* 312:    */           }
/* 313:    */         }
/* 314:    */       }
/* 315:    */     }
/* 316:    */     
/* 317:    */     public int getPriority()
/* 318:    */     {
/* 319:296 */       return -98;
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static class scanTE3Power
/* 324:    */     implements IScanner
/* 325:    */   {
/* 326:    */     public Class getTargetClass()
/* 327:    */     {
/* 328:352 */       return IEnergyHandler.class;
/* 329:    */     }
/* 330:    */     
/* 331:    */     public void addData(Object tile, List<String> data, ForgeDirection side, EntityPlayer player)
/* 332:    */     {
/* 333:357 */       IEnergyHandler a = (IEnergyHandler)tile;
/* 334:358 */       data.add(" TE3 Side Energy: " + a.getEnergyStored(side) + " / " + a.getMaxEnergyStored(side));
/* 335:    */     }
/* 336:    */     
/* 337:    */     public int getPriority()
/* 338:    */     {
/* 339:363 */       return 0;
/* 340:    */     }
/* 341:    */   }
/* 342:    */   
/* 343:    */   public static class scanShears
/* 344:    */     implements IScanner
/* 345:    */   {
/* 346:    */     public Class getTargetClass()
/* 347:    */     {
/* 348:389 */       return IShearable.class;
/* 349:    */     }
/* 350:    */     
/* 351:    */     public void addData(Object target, List<String> data, ForgeDirection side, EntityPlayer player)
/* 352:    */     {
/* 353:394 */       IShearable a = (IShearable)target;
/* 354:395 */       data.add("- Shearable");
/* 355:    */     }
/* 356:    */     
/* 357:    */     public int getPriority()
/* 358:    */     {
/* 359:400 */       return 0;
/* 360:    */     }
/* 361:    */   }
/* 362:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.scanner.ScannerRegistry
 * JD-Core Version:    0.7.0.1
 */