/*   1:    */ package com.rwtema.extrautils.modintegration;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.texture.TextureBedrockLava;
/*   5:    */ import com.rwtema.extrautils.texture.TextureUnstableLava;
/*   6:    */ import cpw.mods.fml.common.eventhandler.EventPriority;
/*   7:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*   8:    */ import cpw.mods.fml.common.gameevent.TickEvent.Phase;
/*   9:    */ import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
/*  10:    */ import cpw.mods.fml.relauncher.Side;
/*  11:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Locale;
/*  14:    */ import java.util.UUID;
/*  15:    */ import net.minecraft.client.Minecraft;
/*  16:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  17:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  18:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  19:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  20:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  21:    */ import net.minecraft.entity.player.EntityPlayer;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.nbt.NBTTagCompound;
/*  24:    */ import net.minecraft.nbt.NBTTagList;
/*  25:    */ import net.minecraft.util.EnumChatFormatting;
/*  26:    */ import net.minecraft.world.World;
/*  27:    */ import net.minecraft.world.WorldProvider;
/*  28:    */ import net.minecraft.world.WorldServer;
/*  29:    */ import net.minecraftforge.client.event.TextureStitchEvent.Pre;
/*  30:    */ import net.minecraftforge.common.DimensionManager;
/*  31:    */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*  32:    */ import net.minecraftforge.fluids.Fluid;
/*  33:    */ import tconstruct.library.crafting.ToolBuilder;
/*  34:    */ import tconstruct.library.event.SmelteryCastedEvent.CastingTable;
/*  35:    */ import tconstruct.library.event.ToolBuildEvent;
/*  36:    */ import tconstruct.library.event.ToolCraftEvent.NormalTool;
/*  37:    */ import tconstruct.library.event.ToolCraftedEvent;
/*  38:    */ import tconstruct.library.tools.ToolCore;
/*  39:    */ import tconstruct.library.util.IToolPart;
/*  40:    */ import tconstruct.tools.TinkerTools;
/*  41:    */ 
/*  42:    */ public class TConEvents
/*  43:    */ {
/*  44: 37 */   public final double SPEED_REDUCTION = -0.1D;
/*  45:    */   public static final String TAG_DEADLINE = "XUDeadline";
/*  46: 39 */   public final String TAG_LOCALDEADLINE = "XULocalDeadline";
/*  47: 40 */   public final String TAG_LOCALDIM = "XULocalDim";
/*  48:    */   public static final int TICKSTILDESTRUCTION = 200;
/*  49: 43 */   public static final UUID uuid = UUID.fromString("52ca0342-0a6b-11e5-a6c0-1697f925ec7b");
/*  50: 44 */   public final String TAG_PREFIX = "[TCon]";
/*  51:    */   public static final String iconName = "extrautils:unstableFluid";
/*  52:    */   public static final String iconName2 = "extrautils:bedrockFluid";
/*  53:    */   int curDim;
/*  54:    */   
/*  55:    */   @SubscribeEvent
/*  56:    */   public void getCurrentWorldTicking(TickEvent.WorldTickEvent event)
/*  57:    */   {
/*  58: 53 */     if ((event.side == Side.SERVER) && (event.phase == TickEvent.Phase.START)) {
/*  59: 54 */       this.curDim = event.world.provider.dimensionId;
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   @SubscribeEvent
/*  64:    */   public void addUnstableTimer(SmelteryCastedEvent.CastingTable event)
/*  65:    */   {
/*  66: 60 */     if (ExtraUtils.tcon_unstable_material_id <= 0) {
/*  67: 61 */       return;
/*  68:    */     }
/*  69: 62 */     ItemStack output = event.output;
/*  70: 63 */     if ((output == null) || (!(output.getItem() instanceof IToolPart))) {
/*  71: 64 */       return;
/*  72:    */     }
/*  73: 66 */     IToolPart part = (IToolPart)output.getItem();
/*  74: 71 */     if (part.getMaterialID(output) != ExtraUtils.tcon_unstable_material_id) {
/*  75: 72 */       return;
/*  76:    */     }
/*  77: 74 */     NBTTagCompound tag = getOrInitTag(output);
/*  78:    */     
/*  79: 76 */     WorldServer world = DimensionManager.getWorld(0);
/*  80: 77 */     if (world == null) {
/*  81: 78 */       return;
/*  82:    */     }
/*  83: 80 */     tag.setLong("XUDeadline", world.getTotalWorldTime());
/*  84:    */     
/*  85:    */ 
/*  86: 83 */     WorldServer localWorld = DimensionManager.getWorld(this.curDim);
/*  87: 84 */     if (localWorld != null)
/*  88:    */     {
/*  89: 85 */       tag.setLong("XULocalDeadline", localWorld.getTotalWorldTime());
/*  90: 86 */       tag.setInteger("XULocalDim", this.curDim);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   @SubscribeEvent(priority=EventPriority.LOWEST)
/*  95:    */   public void denyCraft(ToolBuildEvent event)
/*  96:    */   {
/*  97: 92 */     if (ExtraUtils.tcon_unstable_material_id <= 0) {
/*  98: 93 */       return;
/*  99:    */     }
/* 100: 95 */     WorldServer world = DimensionManager.getWorld(0);
/* 101: 96 */     if (world == null) {
/* 102: 96 */       return;
/* 103:    */     }
/* 104: 98 */     if ((isToolExpired(event.headStack, world)) || (isToolExpired(event.handleStack, world)) || (isToolExpired(event.accessoryStack, world)) || (isToolExpired(event.extraStack, world)))
/* 105:    */     {
/* 106:102 */       event.headStack = null;
/* 107:103 */       event.handleStack = null;
/* 108:104 */       event.accessoryStack = null;
/* 109:105 */       event.extraStack = null;
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public ItemStack handleToolPart(ItemStack stack, WorldServer world)
/* 114:    */   {
/* 115:110 */     return !isToolExpired(stack, world) ? stack : null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static boolean isToolExpired(ItemStack stack)
/* 119:    */   {
/* 120:114 */     WorldServer world = DimensionManager.getWorld(0);
/* 121:115 */     return (world != null) && (isToolExpired(stack, world));
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static boolean isToolExpired(ItemStack stack, WorldServer world)
/* 125:    */   {
/* 126:119 */     if (stack == null) {
/* 127:119 */       return false;
/* 128:    */     }
/* 129:120 */     if ((ToolBuilder.instance.getMaterialID(stack) == ExtraUtils.tcon_unstable_material_id) && (stack.hasTagCompound()))
/* 130:    */     {
/* 131:121 */       NBTTagCompound tag = stack.getTagCompound();
/* 132:122 */       if (tag.func_150297_b("XUDeadline", 4))
/* 133:    */       {
/* 134:123 */         long deadline = tag.getLong("XUDeadline");
/* 135:124 */         if (world.getTotalWorldTime() - deadline > 200L) {
/* 136:125 */           return true;
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:129 */     return false;
/* 141:    */   }
/* 142:    */   
/* 143:    */   @SubscribeEvent
/* 144:    */   @SideOnly(Side.CLIENT)
/* 145:    */   public void unstableTooltip(ItemTooltipEvent event)
/* 146:    */   {
/* 147:135 */     if ((event.itemStack == null) || (event.entityPlayer == null) || (event.entityPlayer.worldObj == null)) {
/* 148:136 */       return;
/* 149:    */     }
/* 150:138 */     if (ExtraUtils.tcon_unstable_material_id <= 0) {
/* 151:139 */       return;
/* 152:    */     }
/* 153:141 */     if (ToolBuilder.instance.getMaterialID(event.itemStack) != ExtraUtils.tcon_unstable_material_id) {
/* 154:142 */       return;
/* 155:    */     }
/* 156:144 */     NBTTagCompound tag = event.itemStack.getTagCompound();
/* 157:146 */     if ((tag == null) || (!tag.hasKey("XULocalDeadline")) || (tag.getInteger("XULocalDim") != event.entityPlayer.worldObj.provider.dimensionId))
/* 158:    */     {
/* 159:147 */       event.toolTip.add(EnumChatFormatting.RED + "Unstable parts will denature after " + 10 + " seconds" + EnumChatFormatting.RESET);
/* 160:148 */       return;
/* 161:    */     }
/* 162:151 */     long finalTime = tag.getLong("XULocalDeadline") + 200L;
/* 163:152 */     long curTime = event.entityPlayer.worldObj.getTotalWorldTime();
/* 164:153 */     if (curTime <= finalTime)
/* 165:    */     {
/* 166:154 */       EnumChatFormatting col = EnumChatFormatting.RED;
/* 167:155 */       if ((curTime >= finalTime - 100L) && 
/* 168:156 */         (Minecraft.getSystemTime() % 200L < 100L)) {
/* 169:157 */         col = EnumChatFormatting.YELLOW;
/* 170:    */       }
/* 171:161 */       event.toolTip.add(col + "Part will denature in " + String.format(Locale.ENGLISH, "%.1f", new Object[] { Float.valueOf((float)(finalTime - curTime) / 20.0F) }) + " seconds" + EnumChatFormatting.RESET);
/* 172:162 */       event.toolTip.add(col + "After that it will become useless" + EnumChatFormatting.RESET);
/* 173:    */     }
/* 174:    */     else
/* 175:    */     {
/* 176:164 */       event.toolTip.add(EnumChatFormatting.RED + "Denatured" + EnumChatFormatting.RESET);
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   @SubscribeEvent
/* 181:    */   public void addBedrockiumPartSlowness(SmelteryCastedEvent.CastingTable event)
/* 182:    */   {
/* 183:171 */     if (ExtraUtils.tcon_bedrock_material_id <= 0) {
/* 184:172 */       return;
/* 185:    */     }
/* 186:174 */     ItemStack output = event.output;
/* 187:175 */     if ((output == null) || (!(output.getItem() instanceof IToolPart))) {
/* 188:176 */       return;
/* 189:    */     }
/* 190:178 */     IToolPart part = (IToolPart)output.getItem();
/* 191:180 */     if (part.getMaterialID(output) != ExtraUtils.tcon_bedrock_material_id) {
/* 192:181 */       return;
/* 193:    */     }
/* 194:183 */     NBTTagCompound tag = getOrInitTag(output);
/* 195:    */     
/* 196:185 */     assignAttribute(tag, SharedMonsterAttributes.movementSpeed, new AttributeModifier(uuid, "[TCon]Bedrockium Weight", -0.1D, 2));
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static NBTTagCompound getOrInitTag(ItemStack output)
/* 200:    */   {
/* 201:189 */     NBTTagCompound tag = output.getTagCompound();
/* 202:190 */     if (tag == null)
/* 203:    */     {
/* 204:191 */       tag = new NBTTagCompound();
/* 205:192 */       output.setTagCompound(tag);
/* 206:    */     }
/* 207:194 */     return tag;
/* 208:    */   }
/* 209:    */   
/* 210:    */   @SubscribeEvent
/* 211:    */   public void handleBedrockMod(ToolCraftedEvent event)
/* 212:    */   {
/* 213:199 */     if (!(event.tool.getItem() instanceof ToolCore)) {
/* 214:199 */       return;
/* 215:    */     }
/* 216:200 */     NBTTagCompound tag = event.tool.getTagCompound();
/* 217:201 */     if (tag == null) {
/* 218:201 */       return;
/* 219:    */     }
/* 220:202 */     assignProperSlowness(tag);
/* 221:    */   }
/* 222:    */   
/* 223:    */   @SubscribeEvent
/* 224:    */   public void handleBedrockModification(ToolCraftEvent.NormalTool event)
/* 225:    */   {
/* 226:207 */     assignProperSlowness(event.toolTag);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void assignProperSlowness(NBTTagCompound tag)
/* 230:    */   {
/* 231:211 */     removeTags(tag);
/* 232:213 */     if (ExtraUtils.tcon_bedrock_material_id <= 0) {
/* 233:214 */       return;
/* 234:    */     }
/* 235:215 */     int i = getNumMaterials(tag.getCompoundTag("InfiTool"), ExtraUtils.tcon_bedrock_material_id);
/* 236:216 */     if (i == 0) {
/* 237:216 */       return;
/* 238:    */     }
/* 239:217 */     assignAttribute(tag, SharedMonsterAttributes.movementSpeed, new AttributeModifier(uuid, "[TCon]Bedrockium Weight", -0.1D * i, 2));
/* 240:218 */     assignAttribute(tag, SharedMonsterAttributes.knockbackResistance, new AttributeModifier(uuid, "[TCon]Bedrockium Weight", 0.5D * i, 2));
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void removeTags(NBTTagCompound tag)
/* 244:    */   {
/* 245:222 */     NBTTagList nbttaglist = tag.getTagList("AttributeModifiers", 10);
/* 246:224 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/* 247:    */     {
/* 248:225 */       NBTTagCompound tagAt = nbttaglist.getCompoundTagAt(i);
/* 249:227 */       if (tagAt.getString("Name").startsWith("[TCon]")) {
/* 250:228 */         nbttaglist.removeTag(i--);
/* 251:    */       }
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void assignAttribute(NBTTagCompound tag, IAttribute attribute, AttributeModifier modifier)
/* 256:    */   {
/* 257:234 */     NBTTagList nbttaglist = tag.getTagList("AttributeModifiers", 10);
/* 258:    */     
/* 259:236 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 260:237 */     nbttagcompound.setString("AttributeName", attribute.getAttributeUnlocalizedName());
/* 261:238 */     nbttagcompound.setString("Name", modifier.getName());
/* 262:239 */     nbttagcompound.setDouble("Amount", modifier.getAmount());
/* 263:240 */     nbttagcompound.setInteger("Operation", modifier.getOperation());
/* 264:241 */     nbttagcompound.setLong("UUIDMost", modifier.getID().getMostSignificantBits());
/* 265:242 */     nbttagcompound.setLong("UUIDLeast", modifier.getID().getLeastSignificantBits());
/* 266:    */     
/* 267:244 */     nbttaglist.appendTag(nbttagcompound);
/* 268:    */     
/* 269:246 */     tag.setTag("AttributeModifiers", nbttaglist);
/* 270:    */   }
/* 271:    */   
/* 272:    */   @SubscribeEvent
/* 273:    */   public void handleUnstableCrafting(ToolCraftEvent.NormalTool event)
/* 274:    */   {
/* 275:251 */     if (ExtraUtils.tcon_unstable_material_id <= 0) {
/* 276:252 */       return;
/* 277:    */     }
/* 278:254 */     NBTTagCompound toolTag = event.toolTag.getCompoundTag("InfiTool");
/* 279:256 */     if (!isUniformTool(toolTag, ExtraUtils.tcon_unstable_material_id)) {
/* 280:256 */       return;
/* 281:    */     }
/* 282:258 */     toolTag.setInteger("Unbreaking", 10);
/* 283:    */   }
/* 284:    */   
/* 285:    */   @SubscribeEvent
/* 286:    */   public void handleMagicWood(ToolCraftEvent.NormalTool event)
/* 287:    */   {
/* 288:263 */     if (ExtraUtils.tcon_magical_wood_id <= 0) {
/* 289:264 */       return;
/* 290:    */     }
/* 291:266 */     NBTTagCompound toolTag = event.toolTag.getCompoundTag("InfiTool");
/* 292:    */     
/* 293:268 */     int modifiers = toolTag.getInteger("Modifiers");
/* 294:269 */     if (!isUniformTool(toolTag, ExtraUtils.tcon_magical_wood_id))
/* 295:    */     {
/* 296:270 */       int bonusModifiers = getNumMaterials(toolTag, ExtraUtils.tcon_magical_wood_id);
/* 297:    */       
/* 298:272 */       modifiers += bonusModifiers;
/* 299:273 */       toolTag.setInteger("Modifiers", modifiers);
/* 300:    */     }
/* 301:    */     else
/* 302:    */     {
/* 303:275 */       if (event.tool == TinkerTools.battlesign) {
/* 304:275 */         modifiers += 3;
/* 305:    */       }
/* 306:276 */       toolTag.setInteger("Modifiers", modifiers + 8);
/* 307:    */     }
/* 308:    */   }
/* 309:    */   
/* 310:    */   public int getNumMaterials(NBTTagCompound toolTag, int materialID)
/* 311:    */   {
/* 312:281 */     int bonusModifiers = 0;
/* 313:282 */     if (toolTag.getInteger("Head") == materialID) {
/* 314:283 */       bonusModifiers++;
/* 315:    */     }
/* 316:284 */     if (toolTag.getInteger("Handle") == materialID) {
/* 317:285 */       bonusModifiers++;
/* 318:    */     }
/* 319:286 */     if (toolTag.getInteger("Accessory") == materialID) {
/* 320:287 */       bonusModifiers++;
/* 321:    */     }
/* 322:288 */     if (toolTag.getInteger("Extra") == materialID) {
/* 323:289 */       bonusModifiers++;
/* 324:    */     }
/* 325:290 */     return bonusModifiers;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public boolean isUniformTool(NBTTagCompound toolTag, int materialId)
/* 329:    */   {
/* 330:294 */     return (toolTag.getInteger("Head") == materialId) && (toolTag.getInteger("Handle") == materialId) && (valid(toolTag.getInteger("Accessory"), materialId)) && (valid(toolTag.getInteger("Extra"), materialId));
/* 331:    */   }
/* 332:    */   
/* 333:    */   public boolean valid(int i, int materialId)
/* 334:    */   {
/* 335:301 */     return (i == materialId) || (i == -1) || (i == 0);
/* 336:    */   }
/* 337:    */   
/* 338:    */   @SideOnly(Side.CLIENT)
/* 339:    */   @SubscribeEvent
/* 340:    */   public void handleStich(TextureStitchEvent.Pre event)
/* 341:    */   {
/* 342:308 */     if (event.map.getTextureType() != 0) {
/* 343:309 */       return;
/* 344:    */     }
/* 345:313 */     TConIntegration.bedrock.setIcons(event.map.registerIcon("TConIntegration.bedrock"));
/* 346:    */     
/* 347:315 */     TextureAtlasSprite sprite = new TextureBedrockLava("extrautils:bedrockFluid", "lava_still");
/* 348:316 */     event.map.setTextureEntry("extrautils:bedrockFluid", sprite);
/* 349:317 */     if (TConIntegration.bedrock != null) {
/* 350:318 */       TConIntegration.bedrock.setIcons(sprite);
/* 351:    */     }
/* 352:320 */     sprite = new TextureBedrockLava("extrautils:bedrockFluid_flowing", "lava_flow");
/* 353:322 */     if ((event.map.setTextureEntry("extrautils:bedrockFluid_flowing", sprite)) && 
/* 354:323 */       (TConIntegration.bedrock != null)) {
/* 355:324 */       TConIntegration.bedrock.setFlowingIcon(sprite);
/* 356:    */     }
/* 357:327 */     sprite = new TextureUnstableLava("extrautils:unstableFluid", "water_still");
/* 358:328 */     event.map.setTextureEntry("extrautils:unstableFluid", sprite);
/* 359:329 */     if (TConIntegration.unstable != null) {
/* 360:330 */       TConIntegration.unstable.setIcons(sprite);
/* 361:    */     }
/* 362:332 */     sprite = new TextureUnstableLava("extrautils:unstableFluid_flowing", "water_flow");
/* 363:334 */     if ((event.map.setTextureEntry("extrautils:unstableFluid_flowing", sprite)) && 
/* 364:335 */       (TConIntegration.unstable != null)) {
/* 365:336 */       TConIntegration.unstable.setFlowingIcon(sprite);
/* 366:    */     }
/* 367:    */   }
/* 368:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.TConEvents
 * JD-Core Version:    0.7.0.1
 */