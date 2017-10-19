/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.crafting.RecipeUnstableCrafting;
/*   5:    */ import com.rwtema.extrautils.damgesource.DamageSourceDivByZero;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Locale;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.creativetab.CreativeTabs;
/*  12:    */ import net.minecraft.entity.Entity;
/*  13:    */ import net.minecraft.entity.item.EntityItem;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  16:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  17:    */ import net.minecraft.inventory.ContainerPlayer;
/*  18:    */ import net.minecraft.item.Item;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.nbt.NBTTagCompound;
/*  21:    */ import net.minecraft.server.MinecraftServer;
/*  22:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  23:    */ import net.minecraft.util.IIcon;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ import net.minecraft.world.WorldProvider;
/*  26:    */ 
/*  27:    */ public class ItemUnstableIngot
/*  28:    */   extends Item
/*  29:    */   implements IItemMultiTransparency
/*  30:    */ {
/*  31:    */   public static final int numTickstilDestruction = 200;
/*  32: 26 */   private IIcon[] iconIngot = new IIcon[2];
/*  33: 27 */   private IIcon[] iconNugget = new IIcon[2];
/*  34:    */   
/*  35:    */   public ItemUnstableIngot()
/*  36:    */   {
/*  37: 31 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  38: 32 */     this.maxStackSize = 1;
/*  39: 33 */     setUnlocalizedName("extrautils:unstableingot");
/*  40: 34 */     setHasSubtypes(true);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getItemStackLimit(ItemStack item)
/*  44:    */   {
/*  45: 38 */     if (item != null)
/*  46:    */     {
/*  47: 39 */       if ((item.getItem() == ExtraUtils.unstableIngot) && (item.getItemDamage() == 0) && 
/*  48: 40 */         (item.hasTagCompound()))
/*  49:    */       {
/*  50: 41 */         if (!item.getTagCompound().hasKey("stable")) {
/*  51: 42 */           return 1;
/*  52:    */         }
/*  53: 43 */         if (item.getTagCompound().hasKey("time")) {
/*  54: 44 */           return 1;
/*  55:    */         }
/*  56:    */       }
/*  57: 48 */       return 64;
/*  58:    */     }
/*  59: 50 */     return 1;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void explode(EntityPlayer player)
/*  63:    */   {
/*  64: 55 */     stripPlayerOfIngots(player);
/*  65: 57 */     if (ExtraUtils.unstableIngotExplosion)
/*  66:    */     {
/*  67: 58 */       player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 1.0F, false);
/*  68: 59 */       player.attackEntityFrom(DamageSourceDivByZero.divbyzero, 32767.0F);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static void stripPlayerOfIngots(EntityPlayer player)
/*  73:    */   {
/*  74: 64 */     if ((player != null) && (player.inventory != null))
/*  75:    */     {
/*  76: 65 */       for (int i = 0; i < player.inventory.getSizeInventory(); i++)
/*  77:    */       {
/*  78: 66 */         ItemStack item = player.inventory.getStackInSlot(i);
/*  79: 68 */         if ((item != null) && (item.getItem() == ExtraUtils.unstableIngot) && (item.hasTagCompound()) && ((item.getTagCompound().hasKey("crafting")) || (item.getTagCompound().hasKey("time")))) {
/*  80: 69 */           player.inventory.setInventorySlotContents(i, null);
/*  81:    */         }
/*  82:    */       }
/*  83: 73 */       ItemStack item = player.inventory.getItemStack();
/*  84: 75 */       if ((item != null) && (item.getItem() == ExtraUtils.unstableIngot) && (item.getItemDamage() == 0) && (item.hasTagCompound()) && ((item.getTagCompound().hasKey("crafting")) || (item.getTagCompound().hasKey("time")))) {
/*  85: 77 */         player.inventory.setItemStack(null);
/*  86:    */       }
/*  87: 80 */       player.inventory.onInventoryChanged();
/*  88: 81 */       updatePlayer(player);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static void updatePlayer(Entity player)
/*  93:    */   {
/*  94: 86 */     if ((player instanceof EntityPlayerMP)) {
/*  95: 87 */       ((EntityPlayerMP)player).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP)player);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 100:    */   {
/* 101: 93 */     return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static boolean isStable(ItemStack item)
/* 105:    */   {
/* 106: 97 */     return (item.getItemDamage() == 2) || ((item.hasTagCompound()) && (item.getTagCompound().hasKey("stable")));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static boolean isSuperStable(ItemStack item)
/* 110:    */   {
/* 111:101 */     return (item.getItemDamage() == 2) || ((item.hasTagCompound()) && (item.getTagCompound().hasKey("superstable")));
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void onUpdate(ItemStack item, World par2World, Entity par3Entity, int par4, boolean par5)
/* 115:    */   {
/* 116:110 */     if (item.stackSize == 0) {
/* 117:111 */       return;
/* 118:    */     }
/* 119:114 */     if (par2World.isClient) {
/* 120:115 */       return;
/* 121:    */     }
/* 122:118 */     if (!(par3Entity instanceof EntityPlayer)) {
/* 123:119 */       return;
/* 124:    */     }
/* 125:122 */     EntityPlayer player = (EntityPlayer)par3Entity;
/* 126:124 */     if (item.getItemDamage() > 0) {
/* 127:125 */       return;
/* 128:    */     }
/* 129:128 */     if (item.hasTagCompound())
/* 130:    */     {
/* 131:129 */       boolean deleteIngot = false;
/* 132:130 */       boolean explode = false;
/* 133:132 */       if ((item.getTagCompound().hasKey("creative")) || (isStable(item))) {
/* 134:133 */         return;
/* 135:    */       }
/* 136:136 */       if (item.getTagCompound().hasKey("bug"))
/* 137:    */       {
/* 138:137 */         item.getTagCompound().removeTag("bug");
/* 139:138 */         item.getTagCompound().setBoolean("bug_show", true);
/* 140:139 */         return;
/* 141:    */       }
/* 142:142 */       if ((item.getTagCompound().hasKey("crafting")) && 
/* 143:143 */         (player.openContainer != null))
/* 144:    */       {
/* 145:144 */         if (player.openContainer.getClass() != ContainerPlayer.class)
/* 146:    */         {
/* 147:145 */           addTimeStamp(item, par2World);
/* 148:146 */           return;
/* 149:    */         }
/* 150:148 */         stripPlayerOfIngots(player);
/* 151:149 */         return;
/* 152:    */       }
/* 153:154 */       if ((player.openContainer != null) && 
/* 154:155 */         (player.openContainer.getClass() == ContainerPlayer.class)) {
/* 155:156 */         explode(player);
/* 156:    */       }
/* 157:160 */       if ((item.getTagCompound().hasKey("time")) && (item.getTagCompound().hasKey("dimension")))
/* 158:    */       {
/* 159:161 */         float t = (float)(200L - (par2World.getTotalWorldTime() - item.getTagCompound().getLong("time"))) / 20.0F;
/* 160:163 */         if (((par3Entity.worldObj.provider.dimensionId != item.getTagCompound().getInteger("dimension") ? 1 : 0) | (t < 0.0F ? 1 : 0)) != 0) {
/* 161:164 */           if (par3Entity.worldObj.provider.dimensionId == item.getTagCompound().getInteger("dimension")) {
/* 162:165 */             explode(player);
/* 163:    */           } else {
/* 164:167 */             stripPlayerOfIngots(player);
/* 165:    */           }
/* 166:    */         }
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void addTimeStamp(ItemStack item, World world)
/* 172:    */   {
/* 173:176 */     NBTTagCompound ts = new NBTTagCompound();
/* 174:178 */     if (ts.hasKey("crafting")) {
/* 175:179 */       ts.removeTag("crafting");
/* 176:    */     }
/* 177:182 */     if (item.getItemDamage() > 0) {
/* 178:183 */       return;
/* 179:    */     }
/* 180:186 */     ts.setInteger("dimension", world.provider.dimensionId);
/* 181:187 */     ts.setLong("time", world.getTotalWorldTime());
/* 182:188 */     item.setTagCompound(ts);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 186:    */   {
/* 187:196 */     if (isStable(par1ItemStack)) {
/* 188:197 */       return;
/* 189:    */     }
/* 190:200 */     if (par1ItemStack.getItemDamage() > 0) {
/* 191:201 */       return;
/* 192:    */     }
/* 193:204 */     addTimeStamp(par1ItemStack, par2World);
/* 194:206 */     if (!par2World.isClient) {
/* 195:207 */       updatePlayer(par3EntityPlayer);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   @SideOnly(Side.CLIENT)
/* 200:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/* 201:    */   {
/* 202:214 */     return false;
/* 203:    */   }
/* 204:    */   
/* 205:    */   @SideOnly(Side.CLIENT)
/* 206:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 207:    */   {
/* 208:224 */     NBTTagCompound tag = par1ItemStack.getTagCompound();
/* 209:225 */     if ((tag != null) && (
/* 210:226 */       (tag == RecipeUnstableCrafting.nbt) || (tag.getBoolean("isNEI")))) {
/* 211:227 */       par1ItemStack = new ItemStack(this, 1, 0);
/* 212:    */     }
/* 213:231 */     if (par1ItemStack.getItemDamage() > 0) {
/* 214:232 */       return;
/* 215:    */     }
/* 216:235 */     if (isStable(par1ItemStack))
/* 217:    */     {
/* 218:236 */       par3List.add("Stable");
/* 219:237 */       return;
/* 220:    */     }
/* 221:240 */     if ((par1ItemStack.hasTagCompound()) && (!par1ItemStack.getTagCompound().hasKey("crafting")) && (!par1ItemStack.getTagCompound().hasKey("creative")) && (!par1ItemStack.getTagCompound().hasKey("bug")))
/* 222:    */     {
/* 223:243 */       if ((par1ItemStack.getTagCompound().hasKey("dimension")) && (par2EntityPlayer.worldObj.provider.dimensionId == par1ItemStack.getTagCompound().getInteger("dimension")))
/* 224:    */       {
/* 225:244 */         float t = (float)(200L - (par2EntityPlayer.worldObj.getTotalWorldTime() - par1ItemStack.getTagCompound().getLong("time"))) / 20.0F;
/* 226:246 */         if (t < 0.0F) {
/* 227:247 */           t = 0.0F;
/* 228:    */         }
/* 229:250 */         par3List.add("Explodes in " + String.format(Locale.ENGLISH, "%.1f", new Object[] { Float.valueOf(t) }) + " seconds");
/* 230:    */       }
/* 231:252 */       else if (par1ItemStack.getTagCompound().hasKey("bug_show"))
/* 232:    */       {
/* 233:253 */         par3List.add("This ingot was created incorrectly");
/* 234:254 */         par3List.add("using getRecipeOutput() instead of getCraftingResult()");
/* 235:255 */         par3List.add("if this ingot was made legitimately please");
/* 236:256 */         par3List.add("report this to the mod developer.");
/* 237:257 */         par3List.add("(don't spam them though - check to see if");
/* 238:258 */         par3List.add("it hasn't already been reported)");
/* 239:    */       }
/* 240:    */     }
/* 241:    */     else
/* 242:    */     {
/* 243:262 */       par3List.add("ERROR: Divide by diamond");
/* 244:263 */       par3List.add("This ingot is highly unstable and will explode");
/* 245:264 */       par3List.add("after 10 seconds.");
/* 246:265 */       par3List.add("Will also explode if the crafting window is closed");
/* 247:266 */       par3List.add("or the ingot is thrown on the ground.");
/* 248:267 */       par3List.add("Additionally these ingots do not stack");
/* 249:268 */       par3List.add(" - Do not craft unless ready -");
/* 250:269 */       par3List.add("");
/* 251:270 */       par3List.add("Must be crafted in a vanilla crafting table.");
/* 252:272 */       if ((par1ItemStack.hasTagCompound()) && (par1ItemStack.getTagCompound().hasKey("creative")))
/* 253:    */       {
/* 254:273 */         par3List.add("");
/* 255:274 */         par3List.add("Creative Spawned - Stable");
/* 256:    */       }
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public boolean hasCustomEntity(ItemStack stack)
/* 261:    */   {
/* 262:281 */     return true;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Entity createEntity(World world, Entity location, ItemStack itemstack)
/* 266:    */   {
/* 267:286 */     if (((location instanceof EntityItem)) && (itemstack.hasTagCompound()) && ((itemstack.getTagCompound().hasKey("crafting")) || (itemstack.getTagCompound().hasKey("time"))))
/* 268:    */     {
/* 269:287 */       ((EntityItem)location).age = 1;
/* 270:288 */       location.setDead();
/* 271:    */     }
/* 272:291 */     return null;
/* 273:    */   }
/* 274:    */   
/* 275:    */   @SideOnly(Side.CLIENT)
/* 276:    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 277:    */   {
/* 278:300 */     NBTTagCompound tag = new NBTTagCompound();
/* 279:301 */     tag.setBoolean("creative", true);
/* 280:302 */     ItemStack item = new ItemStack(par1, 1, 0);
/* 281:303 */     item.setTagCompound(tag);
/* 282:304 */     par3List.add(item);
/* 283:305 */     item = new ItemStack(par1, 1, 1);
/* 284:306 */     par3List.add(item);
/* 285:    */     
/* 286:308 */     par3List.add(new ItemStack(par1, 1, 2));
/* 287:    */   }
/* 288:    */   
/* 289:    */   @SideOnly(Side.CLIENT)
/* 290:    */   public void registerIcons(IIconRegister par1IIconRegister)
/* 291:    */   {
/* 292:314 */     this.itemIcon = (this.iconIngot[0] =  = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5)));
/* 293:315 */     this.iconIngot[1] = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5) + "1");
/* 294:316 */     this.iconNugget[0] = par1IIconRegister.registerIcon("extrautils:unstablenugget");
/* 295:317 */     this.iconNugget[1] = par1IIconRegister.registerIcon("extrautils:unstablenugget1");
/* 296:    */   }
/* 297:    */   
/* 298:    */   public int numIcons(ItemStack item)
/* 299:    */   {
/* 300:323 */     return 2;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public IIcon getIconForTransparentRender(ItemStack item, int pass)
/* 304:    */   {
/* 305:328 */     if (item.getItemDamage() == 1) {
/* 306:329 */       return this.iconNugget[pass];
/* 307:    */     }
/* 308:332 */     return this.iconIngot[pass];
/* 309:    */   }
/* 310:    */   
/* 311:    */   public float getIconTransparency(ItemStack item, int pass)
/* 312:    */   {
/* 313:337 */     if (pass == 1) {
/* 314:338 */       return 0.5F;
/* 315:    */     }
/* 316:340 */     return 1.0F;
/* 317:    */   }
/* 318:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemUnstableIngot
 * JD-Core Version:    0.7.0.1
 */