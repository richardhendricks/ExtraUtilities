/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ActivationRitual;
/*   4:    */ import com.rwtema.extrautils.EventHandlerSiege;
/*   5:    */ import com.rwtema.extrautils.ExtraUtils;
/*   6:    */ import com.rwtema.extrautils.network.packets.PacketTempChatMultiline;
/*   7:    */ import cpw.mods.fml.relauncher.Side;
/*   8:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   9:    */ import java.util.List;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.multiplayer.WorldClient;
/*  12:    */ import net.minecraft.creativetab.CreativeTabs;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.init.Blocks;
/*  15:    */ import net.minecraft.item.Item;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.nbt.NBTTagCompound;
/*  18:    */ import net.minecraft.tileentity.TileEntityHopper;
/*  19:    */ import net.minecraft.util.ChatComponentText;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ import net.minecraft.world.WorldProvider;
/*  22:    */ 
/*  23:    */ public class ItemDivisionSigil
/*  24:    */   extends Item
/*  25:    */ {
/*  26: 23 */   public static int maxdamage = 256;
/*  27:    */   
/*  28:    */   public ItemDivisionSigil()
/*  29:    */   {
/*  30: 27 */     setMaxStackSize(1);
/*  31: 28 */     setUnlocalizedName("extrautils:divisionSigil");
/*  32: 29 */     setTextureName("extrautils:divisionSigil");
/*  33: 30 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static ItemStack newActiveSigil()
/*  37:    */   {
/*  38: 34 */     ItemStack item = new ItemStack(ExtraUtils.divisionSigil);
/*  39: 35 */     NBTTagCompound t = new NBTTagCompound();
/*  40: 36 */     t.setInteger("damage", maxdamage);
/*  41: 37 */     item.setTagCompound(t);
/*  42: 38 */     return item;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static ItemStack newStableSigil()
/*  46:    */   {
/*  47: 42 */     ItemStack item = new ItemStack(ExtraUtils.divisionSigil);
/*  48: 43 */     NBTTagCompound t = new NBTTagCompound();
/*  49: 44 */     t.setBoolean("stable", true);
/*  50: 45 */     item.setTagCompound(t);
/*  51: 46 */     return item;
/*  52:    */   }
/*  53:    */   
/*  54:    */   @SideOnly(Side.CLIENT)
/*  55:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/*  56:    */   {
/*  57: 52 */     if ((par1ItemStack.hasTagCompound()) && ((par1ItemStack.getTagCompound().hasKey("damage")) || (par1ItemStack.getTagCompound().hasKey("stable")))) {
/*  58: 53 */       return true;
/*  59:    */     }
/*  60: 54 */     if (Minecraft.getMinecraft().theWorld != null) {
/*  61: 55 */       return ActivationRitual.checkTime(Minecraft.getMinecraft().theWorld.getWorldTime()) == 0;
/*  62:    */     }
/*  63: 58 */     return false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getUnlocalizedName(ItemStack item)
/*  67:    */   {
/*  68: 63 */     String name = super.getUnlocalizedName();
/*  69: 65 */     if (item.hasTagCompound())
/*  70:    */     {
/*  71: 66 */       if (item.getTagCompound().hasKey("stable")) {
/*  72: 67 */         return name + ".stable";
/*  73:    */       }
/*  74: 70 */       if (item.getTagCompound().hasKey("damage")) {
/*  75: 71 */         return name + ".active";
/*  76:    */       }
/*  77:    */     }
/*  78: 75 */     return name;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
/*  82:    */   {
/*  83: 86 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  87:    */   {
/*  88: 95 */     return par1ItemStack;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public ItemStack getContainerItem(ItemStack itemStack)
/*  92:    */   {
/*  93:108 */     if ((itemStack.hasTagCompound()) && (itemStack.getTagCompound().hasKey("damage")))
/*  94:    */     {
/*  95:109 */       int damage = itemStack.getTagCompound().getInteger("damage");
/*  96:110 */       damage--;
/*  97:112 */       if (damage <= 0)
/*  98:    */       {
/*  99:113 */         itemStack.getTagCompound().removeTag("damage");
/* 100:115 */         if (itemStack.getTagCompound().hasNoTags()) {
/* 101:116 */           itemStack.setTagCompound(null);
/* 102:    */         }
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106:119 */         itemStack.getTagCompound().setInteger("damage", damage);
/* 107:    */       }
/* 108:122 */       return itemStack;
/* 109:    */     }
/* 110:124 */     return itemStack;
/* 111:    */   }
/* 112:    */   
/* 113:    */   @SideOnly(Side.CLIENT)
/* 114:    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 115:    */   {
/* 116:134 */     par3List.add(new ItemStack(par1, 1, 0));
/* 117:135 */     par3List.add(newActiveSigil());
/* 118:136 */     par3List.add(newStableSigil());
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean hasContainerItem(ItemStack itemStack)
/* 122:    */   {
/* 123:144 */     if (itemStack.hasTagCompound())
/* 124:    */     {
/* 125:145 */       if (itemStack.getTagCompound().hasKey("damage")) {
/* 126:146 */         return true;
/* 127:    */       }
/* 128:147 */       if (itemStack.getTagCompound().hasKey("stable")) {
/* 129:148 */         return true;
/* 130:    */       }
/* 131:    */     }
/* 132:150 */     return false;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
/* 136:    */   {
/* 137:160 */     if (world.isClient)
/* 138:    */     {
/* 139:161 */       if (((!item.hasTagCompound()) || (!item.getTagCompound().hasKey("damage"))) && (world.getBlock(x, y, z) == Blocks.enchanting_table))
/* 140:    */       {
/* 141:162 */         boolean flag = true;
/* 142:163 */         PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Activation Ritual"));
/* 143:165 */         if (ActivationRitual.redstoneCirclePresent(world, x, y, z))
/* 144:    */         {
/* 145:166 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- Altar has a redstone circle"));
/* 146:168 */           if (ActivationRitual.altarOnEarth(world, x, y, z))
/* 147:    */           {
/* 148:169 */             PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- Altar and Circle placed on dirt"));
/* 149:    */           }
/* 150:    */           else
/* 151:    */           {
/* 152:171 */             flag = false;
/* 153:172 */             PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Altar and Circle not placed on dirt"));
/* 154:    */           }
/* 155:    */         }
/* 156:    */         else
/* 157:    */         {
/* 158:175 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Altar does not have a redstone circle"));
/* 159:176 */           flag = false;
/* 160:    */         }
/* 161:179 */         if (ActivationRitual.altarCanSeeMoon(world, x, y, z))
/* 162:    */         {
/* 163:180 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- Altar can see the moon"));
/* 164:    */         }
/* 165:    */         else
/* 166:    */         {
/* 167:182 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Altar cannot see the moon"));
/* 168:183 */           flag = false;
/* 169:    */         }
/* 170:186 */         if (ActivationRitual.naturalEarth(world, x, y, z))
/* 171:    */         {
/* 172:187 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- Altar has sufficient natural earth"));
/* 173:    */         }
/* 174:    */         else
/* 175:    */         {
/* 176:189 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Area lacks sufficient natural earth"));
/* 177:190 */           flag = false;
/* 178:    */         }
/* 179:193 */         if (ActivationRitual.altarInDarkness_Client(world, x, y, z)) {
/* 180:194 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- Altar is in darkness"));
/* 181:    */         } else {
/* 182:196 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Altar must not be lit by outside sources"));
/* 183:    */         }
/* 184:199 */         int i = ActivationRitual.checkTime(world.getWorldTime());
/* 185:201 */         if (i == -1)
/* 186:    */         {
/* 187:202 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Too early, sacrifice must be made at midnight"));
/* 188:    */         }
/* 189:203 */         else if (i == 1)
/* 190:    */         {
/* 191:204 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Too late, sacrifice must be made at midnight"));
/* 192:    */         }
/* 193:    */         else
/* 194:    */         {
/* 195:206 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- Time is right"));
/* 196:208 */           if (flag) {
/* 197:209 */             PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Perform the sacrifice"));
/* 198:    */           }
/* 199:    */         }
/* 200:    */       }
/* 201:    */     }
/* 202:213 */     else if ((item.hasTagCompound()) && (item.getTagCompound().hasKey("damage")) && (world.getBlock(x, y, z) == Blocks.beacon))
/* 203:    */     {
/* 204:214 */       PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Stabilization Ritual"));
/* 205:215 */       PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText(""));
/* 206:217 */       if (world.provider.dimensionId != 1)
/* 207:    */       {
/* 208:218 */         if (world.provider.dimensionId == -1) {
/* 209:219 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- Too hot"));
/* 210:    */         } else {
/* 211:221 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- Too much natural earth"));
/* 212:    */         }
/* 213:    */       }
/* 214:    */       else
/* 215:    */       {
/* 216:224 */         int f = 0;int e = 0;int a = 0;int w = 0;
/* 217:226 */         if (TileEntityHopper.func_145893_b(world, x, y, z - 5) != null) {
/* 218:227 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- To the north, Children of Fire: " + (f = EventHandlerSiege.checkChestFire(TileEntityHopper.func_145893_b(world, x, y, z - 5), false)) + " / 12"));
/* 219:    */         } else {
/* 220:230 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Northern chest not present"));
/* 221:    */         }
/* 222:233 */         if (TileEntityHopper.func_145893_b(world, x, y, z + 5) != null) {
/* 223:234 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- To the south, Gifts of Earth: " + (e = EventHandlerSiege.checkChestEarth(TileEntityHopper.func_145893_b(world, x, y, z + 5), false)) + " / 12"));
/* 224:    */         } else {
/* 225:237 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Southern chest not present"));
/* 226:    */         }
/* 227:240 */         if (TileEntityHopper.func_145893_b(world, x + 5, y, z) != null) {
/* 228:241 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- To the east, Descendants of Water: " + (w = EventHandlerSiege.checkChestWater(TileEntityHopper.func_145893_b(world, x + 5, y, z), false)) + " / 12"));
/* 229:    */         } else {
/* 230:244 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Eastern chest not present"));
/* 231:    */         }
/* 232:247 */         if (TileEntityHopper.func_145893_b(world, x - 5, y, z) != null) {
/* 233:248 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("- To the west, Spices of Air: " + (a = EventHandlerSiege.checkChestAir(TileEntityHopper.func_145893_b(world, x - 5, y, z), false)) + " / 12"));
/* 234:    */         } else {
/* 235:251 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("! Western chest not present"));
/* 236:    */         }
/* 237:254 */         PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText(""));
/* 238:255 */         int[] k = EventHandlerSiege.getStrength(world, x, y, z);
/* 239:256 */         boolean hasString = (world.getBlock(x - 1, y, z) == Blocks.tripwire) || (world.getBlock(x + 1, y, z) == Blocks.tripwire) || (world.getBlock(x, y, z - 1) == Blocks.tripwire) || (world.getBlock(x, y, z + 1) == Blocks.tripwire);
/* 240:    */         
/* 241:258 */         boolean hasRedstone = (world.getBlock(x - 1, y, z) == Blocks.redstone_wire) || (world.getBlock(x + 1, y, z) == Blocks.redstone_wire) || (world.getBlock(x, y, z - 1) == Blocks.redstone_wire) || (world.getBlock(x, y, z + 1) == Blocks.redstone_wire);
/* 242:261 */         if (k[1] == 0)
/* 243:    */         {
/* 244:262 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Ritual Markings: No markings present"));
/* 245:    */         }
/* 246:263 */         else if (k[0] == 0)
/* 247:    */         {
/* 248:264 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Ritual Markings: Only 1 type of marking present"));
/* 249:    */         }
/* 250:    */         else
/* 251:    */         {
/* 252:266 */           String t = k[0] + "";
/* 253:268 */           for (int i = 1; i < k.length; i++) {
/* 254:269 */             t = t + " / " + k[i] + " / 64";
/* 255:    */           }
/* 256:272 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Ritual Markings: Strength - " + t));
/* 257:    */         }
/* 258:275 */         if ((f >= 12) && (e >= 12) && (w >= 12) && (a >= 12) && (k[0] >= 64))
/* 259:    */         {
/* 260:276 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText(""));
/* 261:277 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Everything is prepared."));
/* 262:278 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText(""));
/* 263:279 */           PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Sacrifice one who would sacrifice himself."));
/* 264:    */         }
/* 265:    */       }
/* 266:    */     }
/* 267:284 */     PacketTempChatMultiline.sendCached(player);
/* 268:    */     
/* 269:286 */     return true;
/* 270:    */   }
/* 271:    */   
/* 272:    */   @SideOnly(Side.CLIENT)
/* 273:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 274:    */   {
/* 275:295 */     if (par1ItemStack.hasTagCompound())
/* 276:    */     {
/* 277:296 */       if (par1ItemStack.getTagCompound().hasKey("stable")) {
/* 278:297 */         par3List.add("STABLE");
/* 279:298 */       } else if (par1ItemStack.getTagCompound().hasKey("damage")) {
/* 280:299 */         par3List.add("ACTIVE: Number of uses remaining - " + par1ItemStack.getTagCompound().getInteger("damage"));
/* 281:    */       }
/* 282:    */     }
/* 283:    */     else
/* 284:    */     {
/* 285:302 */       par3List.add("INACTIVE: You must perform Activation Ritual.");
/* 286:303 */       par3List.add("Sneak right-click on an enchanting table");
/* 287:304 */       par3List.add("for more details");
/* 288:    */     }
/* 289:    */   }
/* 290:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemDivisionSigil
 * JD-Core Version:    0.7.0.1
 */