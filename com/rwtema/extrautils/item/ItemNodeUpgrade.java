/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import cofh.api.item.ISpecialFilterFluid;
/*   4:    */ import cofh.api.item.ISpecialFilterItem;
/*   5:    */ import com.mojang.authlib.GameProfile;
/*   6:    */ import com.rwtema.extrautils.ExtraUtils;
/*   7:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   8:    */ import com.rwtema.extrautils.ICreativeTabSorting;
/*   9:    */ import com.rwtema.extrautils.helper.XUHelper;
/*  10:    */ import com.rwtema.extrautils.item.filters.AdvancedNodeUpgrades;
/*  11:    */ import com.rwtema.extrautils.item.filters.Matcher;
/*  12:    */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*  13:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  14:    */ import cpw.mods.fml.relauncher.Side;
/*  15:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  16:    */ import java.util.List;
/*  17:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  18:    */ import net.minecraft.creativetab.CreativeTabs;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  21:    */ import net.minecraft.item.Item;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.nbt.NBTTagCompound;
/*  24:    */ import net.minecraft.util.ChatComponentText;
/*  25:    */ import net.minecraft.util.ChatComponentTranslation;
/*  26:    */ import net.minecraft.util.EnumChatFormatting;
/*  27:    */ import net.minecraft.util.IIcon;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ import net.minecraftforge.fluids.FluidStack;
/*  30:    */ import net.minecraftforge.fluids.FluidTank;
/*  31:    */ 
/*  32:    */ public class ItemNodeUpgrade
/*  33:    */   extends Item
/*  34:    */   implements ICreativeTabSorting, ISpecialFilterItem, ISpecialFilterFluid
/*  35:    */ {
/*  36:    */   private static final int numUpgrades = 11;
/*  37: 35 */   private IIcon[] icons = new IIcon[11];
/*  38:    */   
/*  39:    */   public ItemNodeUpgrade()
/*  40:    */   {
/*  41: 38 */     setHasSubtypes(true);
/*  42: 39 */     setUnlocalizedName("extrautils:nodeUpgrade");
/*  43: 40 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static boolean hasKey(ItemStack filter, String key)
/*  47:    */   {
/*  48: 44 */     if (filter != null)
/*  49:    */     {
/*  50: 45 */       NBTTagCompound tags = filter.getTagCompound();
/*  51: 47 */       if ((tags != null) && 
/*  52: 48 */         (tags.hasKey(key))) {
/*  53: 49 */         return true;
/*  54:    */       }
/*  55:    */     }
/*  56: 54 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static boolean getPolarity(ItemStack filter)
/*  60:    */   {
/*  61: 58 */     return hasKey(filter, "Inverted");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static boolean getFuzzyMetadata(ItemStack filter)
/*  65:    */   {
/*  66: 62 */     return hasKey(filter, "FuzzyMeta");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static boolean getFuzzyNBT(ItemStack filter)
/*  70:    */   {
/*  71: 66 */     return hasKey(filter, "FuzzyNBT");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static boolean matchesFilterBuffer(INodeBuffer item, ItemStack filter)
/*  75:    */   {
/*  76: 72 */     if (item == null) {
/*  77: 73 */       return false;
/*  78:    */     }
/*  79: 76 */     Object buffer = item.getBuffer();
/*  80: 77 */     if (buffer == null) {
/*  81: 78 */       return false;
/*  82:    */     }
/*  83: 81 */     if ((buffer instanceof ItemStack)) {
/*  84: 82 */       return matchesFilterItem((ItemStack)buffer, filter);
/*  85:    */     }
/*  86: 84 */     return (!(buffer instanceof FluidTank)) || (matchesFilterLiquid(((FluidTank)buffer).getFluid(), filter));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static boolean isFilter(ItemStack filter)
/*  90:    */   {
/*  91: 89 */     if (filter == null) {
/*  92: 89 */       return false;
/*  93:    */     }
/*  94: 91 */     if ((ExtraUtils.nodeUpgrade != null) && (filter.getItem() == ExtraUtils.nodeUpgrade)) {
/*  95: 92 */       return (filter.getItemDamage() == 1) || (filter.getItemDamage() == 10);
/*  96:    */     }
/*  97: 95 */     return filter.getItem() instanceof ISpecialFilterItem;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static boolean matchesFilterItem(ItemStack item, ItemStack filter)
/* 101:    */   {
/* 102: 99 */     if ((item == null) || (item.getItem() == null) || (filter == null)) {
/* 103:100 */       return false;
/* 104:    */     }
/* 105:101 */     if ((ExtraUtils.nodeUpgrade != null) && (filter.getItem() == ExtraUtils.nodeUpgrade))
/* 106:    */     {
/* 107:102 */       if (filter.getItemDamage() == 1)
/* 108:    */       {
/* 109:103 */         boolean polarity = !getPolarity(filter);
/* 110:104 */         boolean fuzzyMeta = getFuzzyMetadata(filter);
/* 111:105 */         boolean fuzzyNBT = getFuzzyNBT(filter);
/* 112:    */         
/* 113:107 */         NBTTagCompound tags = filter.getTagCompound();
/* 114:109 */         if (tags != null) {
/* 115:110 */           for (int i = 0; i < 9; i++) {
/* 116:111 */             if (tags.hasKey("items_" + i))
/* 117:    */             {
/* 118:112 */               ItemStack f = ItemStack.loadItemStackFromNBT(tags.getCompoundTag("items_" + i));
/* 119:114 */               if (f != null)
/* 120:    */               {
/* 121:119 */                 if (XUHelper.canItemsStack(f, item, fuzzyMeta, true, fuzzyNBT)) {
/* 122:120 */                   return polarity;
/* 123:    */                 }
/* 124:121 */                 if ((isFilter(f)) && 
/* 125:122 */                   (matchesFilterItem(item, f))) {
/* 126:123 */                   return polarity;
/* 127:    */                 }
/* 128:    */               }
/* 129:    */             }
/* 130:    */           }
/* 131:    */         }
/* 132:129 */         return !polarity;
/* 133:    */       }
/* 134:130 */       if (filter.getItemDamage() == 10)
/* 135:    */       {
/* 136:131 */         Matcher matcher = AdvancedNodeUpgrades.getMatcher(filter);
/* 137:132 */         return matcher.matchItem(item) != getPolarity(filter);
/* 138:    */       }
/* 139:    */     }
/* 140:136 */     if ((filter.getItem() instanceof ISpecialFilterItem)) {
/* 141:137 */       return ((ISpecialFilterItem)filter.getItem()).matchesItem(filter, item);
/* 142:    */     }
/* 143:140 */     return XUHelper.canItemsStack(item, filter, false, true);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static boolean matchesFilterLiquid(FluidStack fluid, ItemStack filter)
/* 147:    */   {
/* 148:144 */     if ((fluid == null) || (filter == null)) {
/* 149:144 */       return false;
/* 150:    */     }
/* 151:145 */     if ((ExtraUtils.nodeUpgrade != null) && (filter.getItem() == ExtraUtils.nodeUpgrade))
/* 152:    */     {
/* 153:146 */       if (filter.getItemDamage() == 1)
/* 154:    */       {
/* 155:147 */         boolean polarity = !getPolarity(filter);
/* 156:    */         
/* 157:149 */         NBTTagCompound tags = filter.getTagCompound();
/* 158:151 */         if (tags != null) {
/* 159:152 */           for (int i = 0; i < 9; i++) {
/* 160:153 */             if (tags.hasKey("items_" + i))
/* 161:    */             {
/* 162:154 */               ItemStack f = ItemStack.loadItemStackFromNBT(tags.getCompoundTag("items_" + i));
/* 163:155 */               if (f != null)
/* 164:    */               {
/* 165:159 */                 if (fluid.isFluidEqual(f)) {
/* 166:160 */                   return polarity;
/* 167:    */                 }
/* 168:161 */                 if ((isFilter(f)) && 
/* 169:162 */                   (matchesFilterLiquid(fluid, f))) {
/* 170:163 */                   return polarity;
/* 171:    */                 }
/* 172:    */               }
/* 173:    */             }
/* 174:    */           }
/* 175:    */         }
/* 176:169 */         return !polarity;
/* 177:    */       }
/* 178:170 */       if (filter.getItemDamage() == 10)
/* 179:    */       {
/* 180:171 */         Matcher matcher = AdvancedNodeUpgrades.getMatcher(filter);
/* 181:172 */         return matcher.matchFluid(fluid) != getPolarity(filter);
/* 182:    */       }
/* 183:    */     }
/* 184:176 */     if ((filter.getItem() instanceof ISpecialFilterFluid)) {
/* 185:177 */       ((ISpecialFilterFluid)filter.getItem()).matchesFluid(filter, fluid);
/* 186:    */     }
/* 187:180 */     return fluid.isFluidEqual(filter);
/* 188:    */   }
/* 189:    */   
/* 190:    */   @SideOnly(Side.CLIENT)
/* 191:    */   public void registerIcons(IIconRegister par1IIconRegister)
/* 192:    */   {
/* 193:186 */     this.icons[0] = par1IIconRegister.registerIcon("extrautils:nodeUpgrade");
/* 194:187 */     this.icons[1] = par1IIconRegister.registerIcon("extrautils:filter");
/* 195:188 */     this.icons[2] = par1IIconRegister.registerIcon("extrautils:nodeUpgradeMining");
/* 196:189 */     this.icons[3] = par1IIconRegister.registerIcon("extrautils:nodeUpgradeStack");
/* 197:190 */     this.icons[4] = par1IIconRegister.registerIcon("extrautils:nodeUpgradeCreative");
/* 198:191 */     this.icons[5] = par1IIconRegister.registerIcon("extrautils:nodeUpgradeEnder");
/* 199:192 */     this.icons[6] = par1IIconRegister.registerIcon("extrautils:nodeUpgradeEnderReceiver");
/* 200:193 */     this.icons[7] = par1IIconRegister.registerIcon("extrautils:nodeUpgradeDepth");
/* 201:194 */     this.icons[8] = par1IIconRegister.registerIcon("extrautils:nodeUpgradeBreadth");
/* 202:195 */     this.icons[9] = par1IIconRegister.registerIcon("extrautils:nodeUpgradePatience");
/* 203:196 */     this.icons[10] = par1IIconRegister.registerIcon("extrautils:filter2");
/* 204:    */   }
/* 205:    */   
/* 206:    */   @SideOnly(Side.CLIENT)
/* 207:    */   public IIcon getIconFromDamage(int par1)
/* 208:    */   {
/* 209:205 */     return this.icons[(par1 % 11)];
/* 210:    */   }
/* 211:    */   
/* 212:    */   @SideOnly(Side.CLIENT)
/* 213:    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 214:    */   {
/* 215:214 */     for (int i = 0; i < 11; i++) {
/* 216:215 */       par3List.add(new ItemStack(par1, 1, i));
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   @SideOnly(Side.CLIENT)
/* 221:    */   public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 222:    */   {
/* 223:225 */     if (item.getItemDamage() == 1) {
/* 224:226 */       if (item.getTagCompound() != null)
/* 225:    */       {
/* 226:227 */         if (getPolarity(item)) {
/* 227:228 */           par3List.add("Inverted");
/* 228:    */         }
/* 229:231 */         if (getFuzzyMetadata(item)) {
/* 230:232 */           par3List.add("Fuzzy - Ignores Metadata");
/* 231:    */         }
/* 232:235 */         if (getFuzzyNBT(item)) {
/* 233:236 */           par3List.add("Fuzzy - Ignores NBT");
/* 234:    */         }
/* 235:239 */         for (int i = 0; i < 9; i++) {
/* 236:240 */           if (item.getTagCompound().hasKey("items_" + i))
/* 237:    */           {
/* 238:241 */             ItemStack temp = ItemStack.loadItemStackFromNBT(item.getTagCompound().getCompoundTag("items_" + i));
/* 239:242 */             List tempList = temp.getTooltip(par2EntityPlayer, false);
/* 240:244 */             for (int j = 0; j < tempList.size(); j++) {
/* 241:245 */               if (j == 0) {
/* 242:246 */                 par3List.add("  " + tempList.get(j));
/* 243:    */               } else {
/* 244:248 */                 par3List.add("     " + tempList.get(j));
/* 245:    */               }
/* 246:    */             }
/* 247:252 */             tempList.clear();
/* 248:    */           }
/* 249:    */         }
/* 250:    */       }
/* 251:    */       else
/* 252:    */       {
/* 253:256 */         par3List.add(EnumChatFormatting.ITALIC + "Right click to select items to filter" + EnumChatFormatting.RESET);
/* 254:257 */         par3List.add(EnumChatFormatting.ITALIC + "Filters can be placed within other filters to create advanced behaviours" + EnumChatFormatting.RESET);
/* 255:258 */         par3List.add(EnumChatFormatting.ITALIC + "Craft with" + EnumChatFormatting.RESET);
/* 256:    */       }
/* 257:    */     }
/* 258:262 */     if (item.getItemDamage() == 10)
/* 259:    */     {
/* 260:263 */       Matcher matcher = AdvancedNodeUpgrades.getMatcher(item);
/* 261:264 */       par3List.add("Filter Program: " + matcher.getLocalizedName());
/* 262:265 */       if (getPolarity(item))
/* 263:    */       {
/* 264:266 */         par3List.add("Inverted");
/* 265:    */       }
/* 266:267 */       else if (matcher == AdvancedNodeUpgrades.nullMatcher)
/* 267:    */       {
/* 268:268 */         par3List.add(EnumChatFormatting.ITALIC + "Right-click to change Filter Program" + EnumChatFormatting.RESET);
/* 269:269 */         par3List.add(EnumChatFormatting.ITALIC + "Craft with a redstone torch to Invert" + EnumChatFormatting.RESET);
/* 270:270 */         par3List.add(EnumChatFormatting.ITALIC + "Can be placed in normal filters to create advanced behaviours" + EnumChatFormatting.RESET);
/* 271:    */       }
/* 272:    */     }
/* 273:274 */     if ((item.getItemDamage() == 5) || (item.getItemDamage() == 6))
/* 274:    */     {
/* 275:275 */       par3List.set(0, ((String)par3List.get(0)).replaceFirst(EnumChatFormatting.ITALIC + item.getDisplayName() + EnumChatFormatting.RESET, getItemStackDisplayName(item)));
/* 276:277 */       if (!item.hasDisplayName())
/* 277:    */       {
/* 278:278 */         par3List.add("Unspecified Frequency: You must name this upgrade in an anvil to choose a frequency");
/* 279:279 */         par3List.add("You cannot use this upgrade until it has a frequency");
/* 280:    */       }
/* 281:    */       else
/* 282:    */       {
/* 283:281 */         par3List.add("Frequency: " + item.getDisplayName());
/* 284:    */         
/* 285:283 */         String s = XUHelper.getPlayerOwner(item);
/* 286:284 */         if (s.equals("")) {
/* 287:285 */           par3List.add("Public Spectrum");
/* 288:    */         } else {
/* 289:287 */           par3List.add("Private Spectrum - " + s);
/* 290:    */         }
/* 291:    */       }
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 296:    */   {
/* 297:299 */     if (!par2World.isClient) {
/* 298:300 */       switch (par1ItemStack.getItemDamage())
/* 299:    */       {
/* 300:    */       case 1: 
/* 301:302 */         par3EntityPlayer.openGui(ExtraUtilsMod.instance, 1, par2World, par3EntityPlayer.inventory.currentItem, 0, 0);
/* 302:303 */         break;
/* 303:    */       case 5: 
/* 304:    */       case 6: 
/* 305:307 */         if (XUHelper.getPlayerOwner(par1ItemStack).equals(""))
/* 306:    */         {
/* 307:308 */           PacketTempChat.sendChat(par3EntityPlayer, new ChatComponentText("Spectrum set to private"));
/* 308:309 */           XUHelper.setPlayerOwner(par1ItemStack, par3EntityPlayer.getGameProfile().getName());
/* 309:    */         }
/* 310:    */         else
/* 311:    */         {
/* 312:311 */           PacketTempChat.sendChat(par3EntityPlayer, new ChatComponentText("Spectrum set to public"));
/* 313:312 */           XUHelper.setPlayerOwner(par1ItemStack, "");
/* 314:    */         }
/* 315:314 */         break;
/* 316:    */       case 10: 
/* 317:316 */         Matcher matcher = AdvancedNodeUpgrades.nextEntry(par1ItemStack, !par3EntityPlayer.isSneaking());
/* 318:317 */         PacketTempChat.sendChat(par3EntityPlayer, new ChatComponentText("Filter Program: ").appendSibling(new ChatComponentTranslation(matcher.unlocalizedName, new Object[0])));
/* 319:318 */         break;
/* 320:    */       }
/* 321:    */     }
/* 322:323 */     return par1ItemStack;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 326:    */   {
/* 327:333 */     return super.getUnlocalizedName(par1ItemStack) + "." + par1ItemStack.getItemDamage();
/* 328:    */   }
/* 329:    */   
/* 330:    */   public String getSortingName(ItemStack item)
/* 331:    */   {
/* 332:338 */     if (item.getItemDamage() == 1) {
/* 333:339 */       return item.getDisplayName();
/* 334:    */     }
/* 335:342 */     ItemStack i = item.copy();
/* 336:343 */     i.setItemDamage(-1);
/* 337:344 */     return i.getDisplayName() + item.getDisplayName();
/* 338:    */   }
/* 339:    */   
/* 340:    */   public boolean matchesItem(ItemStack filter, ItemStack item)
/* 341:    */   {
/* 342:349 */     return matchesFilterItem(item, filter);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public boolean matchesFluid(ItemStack filter, FluidStack fluidstack)
/* 346:    */   {
/* 347:354 */     return matchesFilterLiquid(fluidstack, filter);
/* 348:    */   }
/* 349:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemNodeUpgrade
 * JD-Core Version:    0.7.0.1
 */