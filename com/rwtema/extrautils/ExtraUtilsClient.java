/*   1:    */ package com.rwtema.extrautils;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.block.BlockColor;
/*   4:    */ import com.rwtema.extrautils.block.render.RenderBlockColor;
/*   5:    */ import com.rwtema.extrautils.block.render.RenderBlockConnectedTextures;
/*   6:    */ import com.rwtema.extrautils.block.render.RenderBlockConnectedTexturesEthereal;
/*   7:    */ import com.rwtema.extrautils.block.render.RenderBlockDrum;
/*   8:    */ import com.rwtema.extrautils.block.render.RenderBlockFullBright;
/*   9:    */ import com.rwtema.extrautils.block.render.RenderBlockMultiBlock;
/*  10:    */ import com.rwtema.extrautils.block.render.RenderBlockSpike;
/*  11:    */ import com.rwtema.extrautils.command.CommandDumpNEIInfo;
/*  12:    */ import com.rwtema.extrautils.command.CommandDumpNEIInfo2;
/*  13:    */ import com.rwtema.extrautils.command.CommandDumpTextureSheet;
/*  14:    */ import com.rwtema.extrautils.command.CommandHologram;
/*  15:    */ import com.rwtema.extrautils.command.CommandUUID;
/*  16:    */ import com.rwtema.extrautils.helper.XUHelper;
/*  17:    */ import com.rwtema.extrautils.item.ItemBlockSpike;
/*  18:    */ import com.rwtema.extrautils.item.RenderItemBlockColor;
/*  19:    */ import com.rwtema.extrautils.item.RenderItemBlockDrum;
/*  20:    */ import com.rwtema.extrautils.item.RenderItemGlove;
/*  21:    */ import com.rwtema.extrautils.item.RenderItemLawSword;
/*  22:    */ import com.rwtema.extrautils.item.RenderItemMultiTransparency;
/*  23:    */ import com.rwtema.extrautils.item.RenderItemSpikeSword;
/*  24:    */ import com.rwtema.extrautils.item.RenderItemUnstable;
/*  25:    */ import com.rwtema.extrautils.multipart.FakeRenderBlocksMultiPart;
/*  26:    */ import com.rwtema.extrautils.multipart.microblock.RenderItemMicroblock;
/*  27:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*  28:    */ import com.rwtema.extrautils.network.PacketHandler;
/*  29:    */ import com.rwtema.extrautils.network.PacketHandlerClient;
/*  30:    */ import com.rwtema.extrautils.network.packets.PacketUseItemAlt;
/*  31:    */ import com.rwtema.extrautils.particle.ParticleHelperClient;
/*  32:    */ import com.rwtema.extrautils.texture.LiquidColorRegistry;
/*  33:    */ import com.rwtema.extrautils.tileentity.RenderTileEntitySpike;
/*  34:    */ import com.rwtema.extrautils.tileentity.TileEntityEnchantedSpike;
/*  35:    */ import cpw.mods.fml.client.registry.ClientRegistry;
/*  36:    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*  37:    */ import cpw.mods.fml.common.FMLCommonHandler;
/*  38:    */ import cpw.mods.fml.common.Loader;
/*  39:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*  40:    */ import cpw.mods.fml.relauncher.Side;
/*  41:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  42:    */ import net.minecraft.client.Minecraft;
/*  43:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  44:    */ import net.minecraft.client.resources.IReloadableResourceManager;
/*  45:    */ import net.minecraft.client.settings.GameSettings;
/*  46:    */ import net.minecraft.entity.player.EntityPlayer;
/*  47:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  48:    */ import net.minecraft.item.Item;
/*  49:    */ import net.minecraft.item.ItemStack;
/*  50:    */ import net.minecraft.network.INetHandler;
/*  51:    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*  52:    */ import net.minecraft.util.MovingObjectPosition;
/*  53:    */ import net.minecraft.util.Session;
/*  54:    */ import net.minecraft.util.Vec3;
/*  55:    */ import net.minecraft.world.World;
/*  56:    */ import net.minecraftforge.client.ClientCommandHandler;
/*  57:    */ import net.minecraftforge.client.MinecraftForgeClient;
/*  58:    */ import net.minecraftforge.common.MinecraftForge;
/*  59:    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*  60:    */ 
/*  61:    */ @SideOnly(Side.CLIENT)
/*  62:    */ public class ExtraUtilsClient
/*  63:    */   extends ExtraUtilsProxy
/*  64:    */ {
/*  65: 40 */   public static final RenderItemBlockColor renderItemBlockColor = new RenderItemBlockColor();
/*  66:    */   
/*  67:    */   public ExtraUtilsClient()
/*  68:    */   {
/*  69: 44 */     INSTANCE = this;
/*  70:    */   }
/*  71:    */   
/*  72: 47 */   public static final RenderItemUnstable renderItemUnstable = new RenderItemUnstable();
/*  73: 48 */   public static final RenderItemSpikeSword renderItemSpikeSword = new RenderItemSpikeSword();
/*  74: 49 */   public static final RenderItemMultiTransparency renderItemMultiTransparency = new RenderItemMultiTransparency();
/*  75: 50 */   public static final RenderItemBlockDrum renderItemDrum = new RenderItemBlockDrum();
/*  76: 51 */   public static final RenderBlockSpike renderBlockSpike = new RenderBlockSpike();
/*  77:    */   
/*  78:    */   public void registerClientCommands()
/*  79:    */   {
/*  80: 57 */     if ((XUHelper.deObf) || (XUHelper.isTema(Minecraft.getMinecraft().getSession().func_148256_e())))
/*  81:    */     {
/*  82: 58 */       ClientCommandHandler.instance.registerCommand(new CommandDumpNEIInfo());
/*  83: 59 */       ClientCommandHandler.instance.registerCommand(new CommandDumpNEIInfo2());
/*  84: 60 */       ClientCommandHandler.instance.registerCommand(new CommandDumpTextureSheet());
/*  85:    */     }
/*  86: 64 */     ClientCommandHandler.instance.registerCommand(CommandTPSTimer.INSTANCE);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public EntityPlayer getPlayerFromNetHandler(INetHandler handler)
/*  90:    */   {
/*  91: 69 */     EntityPlayer player = super.getPlayerFromNetHandler(handler);
/*  92: 70 */     if (player == null) {
/*  93: 70 */       return getClientPlayer();
/*  94:    */     }
/*  95: 71 */     return player;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void postInit() {}
/*  99:    */   
/* 100:    */   public void registerEventHandler()
/* 101:    */   {
/* 102: 80 */     MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
/* 103: 81 */     FMLCommonHandler.instance().bus().register(new EventHandlerClient());
/* 104: 82 */     ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new LiquidColorRegistry());
/* 105: 83 */     ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new ParticleHelperClient());
/* 106: 84 */     ExtraUtils.handlesClientMethods = true;
/* 107: 86 */     if (Loader.isModLoaded("ForgeMultipart")) {
/* 108: 87 */       RenderBlockConnectedTextures.fakeRender = new FakeRenderBlocksMultiPart();
/* 109:    */     }
/* 110: 90 */     ClientCommandHandler.instance.registerCommand(new CommandHologram());
/* 111: 91 */     ClientCommandHandler.instance.registerCommand(new CommandUUID());
/* 112:    */     
/* 113: 93 */     KeyHandler.INSTANCE.register();
/* 114:    */     
/* 115: 95 */     super.registerEventHandler();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void throwLoadingError(String cause, String... message)
/* 119:    */   {
/* 120:101 */     throw new CustomErrorWGui(cause, message);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isClientSideAvailable()
/* 124:    */   {
/* 125:106 */     return true;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void newServerStart()
/* 129:    */   {
/* 130:111 */     super.newServerStart();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void registerRenderInformation()
/* 134:    */   {
/* 135:116 */     colorBlockID = RenderingRegistry.getNextAvailableRenderId();
/* 136:117 */     fullBrightBlockID = RenderingRegistry.getNextAvailableRenderId();
/* 137:118 */     multiBlockID = RenderingRegistry.getNextAvailableRenderId();
/* 138:119 */     spikeBlockID = RenderingRegistry.getNextAvailableRenderId();
/* 139:120 */     drumRendererID = RenderingRegistry.getNextAvailableRenderId();
/* 140:121 */     connectedTextureID = RenderingRegistry.getNextAvailableRenderId();
/* 141:122 */     connectedTextureEtheralID = RenderingRegistry.getNextAvailableRenderId();
/* 142:123 */     RenderingRegistry.registerBlockHandler(new RenderBlockColor());
/* 143:124 */     RenderingRegistry.registerBlockHandler(new RenderBlockFullBright());
/* 144:125 */     RenderingRegistry.registerBlockHandler(new RenderBlockMultiBlock());
/* 145:126 */     RenderingRegistry.registerBlockHandler(renderBlockSpike);
/* 146:128 */     for (Item item : ItemBlockSpike.itemHashSet) {
/* 147:129 */       MinecraftForgeClient.registerItemRenderer(item, renderItemSpikeSword);
/* 148:    */     }
/* 149:132 */     RenderingRegistry.registerBlockHandler(new RenderBlockDrum());
/* 150:133 */     RenderingRegistry.registerBlockHandler(new RenderBlockConnectedTextures());
/* 151:134 */     RenderingRegistry.registerBlockHandler(new RenderBlockConnectedTexturesEthereal());
/* 152:136 */     if (ExtraUtils.spikeGoldEnabled) {
/* 153:137 */       ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantedSpike.class, new RenderTileEntitySpike());
/* 154:    */     }
/* 155:139 */     if (ExtraUtils.colorBlockDataEnabled) {
/* 156:140 */       for (BlockColor b : ExtraUtils.colorblocks) {
/* 157:141 */         MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(b), renderItemBlockColor);
/* 158:    */       }
/* 159:    */     }
/* 160:143 */     if (ExtraUtils.unstableIngot != null) {
/* 161:144 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.unstableIngot, renderItemUnstable);
/* 162:    */     }
/* 163:146 */     if (ExtraUtils.erosionShovel != null) {
/* 164:147 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.erosionShovel, renderItemMultiTransparency);
/* 165:    */     }
/* 166:149 */     if (ExtraUtils.destructionPickaxe != null) {
/* 167:150 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.destructionPickaxe, renderItemMultiTransparency);
/* 168:    */     }
/* 169:152 */     if (ExtraUtils.buildersWand != null) {
/* 170:153 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.buildersWand, renderItemMultiTransparency);
/* 171:    */     }
/* 172:155 */     if (ExtraUtils.ethericSword != null) {
/* 173:156 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.ethericSword, renderItemMultiTransparency);
/* 174:    */     }
/* 175:158 */     if (ExtraUtils.healingAxe != null) {
/* 176:159 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.healingAxe, renderItemMultiTransparency);
/* 177:    */     }
/* 178:161 */     if (ExtraUtils.creativeBuildersWand != null) {
/* 179:162 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.creativeBuildersWand, renderItemMultiTransparency);
/* 180:    */     }
/* 181:164 */     if (ExtraUtils.precisionShears != null) {
/* 182:165 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.precisionShears, renderItemMultiTransparency);
/* 183:    */     }
/* 184:167 */     if (ExtraUtils.temporalHoe != null) {
/* 185:168 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.temporalHoe, renderItemMultiTransparency);
/* 186:    */     }
/* 187:170 */     if (ExtraUtils.drum != null) {
/* 188:171 */       MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ExtraUtils.drum), renderItemDrum);
/* 189:    */     }
/* 190:173 */     if (ExtraUtils.microBlocks != null) {
/* 191:174 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.microBlocks, new RenderItemMicroblock());
/* 192:    */     }
/* 193:176 */     if (ExtraUtils.lawSwordEnabled) {
/* 194:177 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.lawSword, new RenderItemLawSword());
/* 195:    */     }
/* 196:179 */     if (ExtraUtils.glove != null) {
/* 197:180 */       MinecraftForgeClient.registerItemRenderer(ExtraUtils.glove, RenderItemGlove.INSTANCE);
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public EntityPlayer getClientPlayer()
/* 202:    */   {
/* 203:186 */     return Minecraft.getMinecraft().thePlayer;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public World getClientWorld()
/* 207:    */   {
/* 208:191 */     return Minecraft.getMinecraft().theWorld;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public PacketHandler getNewPacketHandler()
/* 212:    */   {
/* 213:196 */     return new PacketHandlerClient();
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void exectuteClientCode(IClientCode clientCode)
/* 217:    */   {
/* 218:201 */     clientCode.exectuteClientCode();
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void sendUsePacket(PlayerInteractEvent event)
/* 222:    */   {
/* 223:206 */     if (event.world.isClient)
/* 224:    */     {
/* 225:207 */       Vec3 hitVec = Minecraft.getMinecraft().objectMouseOver.hitVec;
/* 226:208 */       float f = (float)hitVec.xCoord - event.x;
/* 227:209 */       float f1 = (float)hitVec.yCoord - event.y;
/* 228:210 */       float f2 = (float)hitVec.zCoord - event.z;
/* 229:211 */       Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(event.x, event.y, event.z, event.face, event.entityPlayer.inventory.getCurrentItem(), f, f1, f2));
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void sendUsePacket(int x, int y, int z, int face, ItemStack item, float hitX, float hitY, float hitZ)
/* 234:    */   {
/* 235:217 */     if (isAltSneaking()) {
/* 236:218 */       sendAltUsePacket(x, y, z, face, item, hitX, hitY, hitZ);
/* 237:    */     } else {
/* 238:220 */       Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(x, y, z, face, item, hitX, hitY, hitZ));
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void sendAltUsePacket(int x, int y, int z, int face, ItemStack item, float hitX, float hitY, float hitZ)
/* 243:    */   {
/* 244:226 */     NetworkHandler.sendPacketToServer(new PacketUseItemAlt(x, y, z, face, item, hitX, hitY, hitZ));
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void sendAltUsePacket(ItemStack item)
/* 248:    */   {
/* 249:231 */     sendAltUsePacket(-1, -1, -1, 255, item, 0.0F, 0.0F, 0.0F);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean isAltSneaking()
/* 253:    */   {
/* 254:236 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/* 255:237 */       return KeyHandler.getIsKeyPressed(Minecraft.getMinecraft().gameSettings.keyBindSprint);
/* 256:    */     }
/* 257:239 */     return super.isAltSneaking();
/* 258:    */   }
/* 259:    */   
/* 260:    */   public <F, T> T apply(ISidedFunction<F, T> func, F input)
/* 261:    */   {
/* 262:243 */     return func.applyClient(input);
/* 263:    */   }
/* 264:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.ExtraUtilsClient
 * JD-Core Version:    0.7.0.1
 */