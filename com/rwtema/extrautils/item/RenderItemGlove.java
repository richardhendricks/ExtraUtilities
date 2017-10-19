/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.common.FMLCommonHandler;
/*   5:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*   6:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*   7:    */ import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
/*   8:    */ import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  11:    */ import net.minecraft.client.model.ModelBiped;
/*  12:    */ import net.minecraft.client.model.ModelRenderer;
/*  13:    */ import net.minecraft.client.renderer.entity.RenderManager;
/*  14:    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*  15:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.util.MathHelper;
/*  19:    */ import net.minecraft.util.ResourceLocation;
/*  20:    */ import net.minecraftforge.client.IItemRenderer;
/*  21:    */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/*  22:    */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/*  23:    */ import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;
/*  24:    */ import net.minecraftforge.common.MinecraftForge;
/*  25:    */ import org.lwjgl.opengl.GL11;
/*  26:    */ 
/*  27:    */ public class RenderItemGlove
/*  28:    */   implements IItemRenderer
/*  29:    */ {
/*  30: 26 */   public static final RenderItemGlove INSTANCE = new RenderItemGlove();
/*  31:    */   float renderTickTime;
/*  32:    */   
/*  33:    */   static
/*  34:    */   {
/*  35: 27 */     FMLCommonHandler.instance().bus().register(INSTANCE);
/*  36: 28 */     MinecraftForge.EVENT_BUS.register(INSTANCE);
/*  37:    */   }
/*  38:    */   
/*  39:    */   @SubscribeEvent
/*  40:    */   public void getFrame(TickEvent.RenderTickEvent event)
/*  41:    */   {
/*  42: 36 */     this.renderTickTime = event.renderTickTime;
/*  43:    */   }
/*  44:    */   
/*  45:    */   @SubscribeEvent
/*  46:    */   public void tickCol(TickEvent.ClientTickEvent event)
/*  47:    */   {
/*  48: 41 */     ItemGlove.genericDmg = 0x50 | ItemGlove.genericDmg + 1 & 0xF;
/*  49:    */   }
/*  50:    */   
/*  51:    */   @SubscribeEvent
/*  52:    */   public void renderEquippedGlove(RenderPlayerEvent.Specials.Post event)
/*  53:    */   {
/*  54: 46 */     if (event.entityPlayer == null) {
/*  55: 47 */       return;
/*  56:    */     }
/*  57: 49 */     ItemStack heldItem = event.entityPlayer.getHeldItem();
/*  58: 50 */     if ((heldItem == null) || (heldItem.getItem() != ExtraUtils.glove)) {
/*  59: 51 */       return;
/*  60:    */     }
/*  61: 53 */     int dmg = heldItem.getItemDamage();
/*  62:    */     
/*  63: 55 */     RenderPlayer renderplayer = event.renderer;
/*  64:    */     
/*  65: 57 */     float[] col = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[ItemGlove.getColIndex(1, dmg)];
/*  66: 58 */     GL11.glColor3f(col[0], col[1], col[2]);
/*  67: 59 */     Minecraft.getMinecraft().getTextureManager().bindTexture(glove1);
/*  68:    */     
/*  69: 61 */     renderplayer.modelBipedMain.bipedRightArm.render(0.0625F);
/*  70:    */     
/*  71: 63 */     col = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[ItemGlove.getColIndex(0, dmg)];
/*  72: 64 */     GL11.glColor3f(col[0], col[1], col[2]);
/*  73: 65 */     Minecraft.getMinecraft().getTextureManager().bindTexture(glove2);
/*  74: 66 */     renderplayer.modelBipedMain.bipedRightArm.render(0.0625F);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
/*  78:    */   {
/*  79: 73 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) || (type == IItemRenderer.ItemRenderType.EQUIPPED);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
/*  83:    */   {
/*  84: 78 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) && (helper == IItemRenderer.ItemRendererHelper.EQUIPPED_BLOCK);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
/*  88:    */   {
/*  89: 83 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
/*  90: 84 */       return;
/*  91:    */     }
/*  92: 86 */     GL11.glEnable(2896);
/*  93: 87 */     GL11.glEnable(2929);
/*  94:    */     
/*  95:    */ 
/*  96: 90 */     EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
/*  97:    */     
/*  98:    */ 
/*  99: 93 */     GL11.glPushMatrix();
/* 100:    */     
/* 101: 95 */     GL11.glEnable(32826);
/* 102:    */     
/* 103:    */ 
/* 104: 98 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 105:    */     
/* 106:100 */     float p_78440_1_ = this.renderTickTime;
/* 107:101 */     float f1 = 1.0F;
/* 108:102 */     float f13 = 0.8F;
/* 109:    */     
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:109 */     float f8 = 2.5F;
/* 116:110 */     GL11.glScalef(f8, f8, f8);
/* 117:    */     
/* 118:112 */     float f5 = player.getSwingProgress(p_78440_1_);
/* 119:113 */     float f6 = MathHelper.sin(f5 * f5 * 3.141593F);
/* 120:114 */     float f7 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F);
/* 121:115 */     GL11.glRotatef(f7 * 80.0F, 1.0F, 0.0F, 0.0F);
/* 122:116 */     GL11.glRotatef(f7 * 20.0F, 0.0F, 0.0F, 1.0F);
/* 123:117 */     GL11.glRotatef(f6 * 20.0F, 0.0F, 1.0F, 0.0F);
/* 124:    */     
/* 125:119 */     GL11.glRotatef(-45.0F, 0.0F, 1.0F, 0.0F);
/* 126:    */     
/* 127:121 */     GL11.glTranslatef(-0.7F * f13, 0.65F * f13 + (1.0F - f1) * 0.6F, 0.9F * f13);
/* 128:    */     
/* 129:123 */     f5 = player.getSwingProgress(p_78440_1_);
/* 130:124 */     f6 = MathHelper.sin(f5 * 3.141593F);
/* 131:125 */     f7 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F);
/* 132:126 */     GL11.glTranslatef(f7 * 0.4F, -MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F * 2.0F) * 0.2F, f6 * 0.2F);
/* 133:    */     
/* 134:    */ 
/* 135:    */ 
/* 136:130 */     f5 = player.getSwingProgress(p_78440_1_);
/* 137:131 */     f6 = MathHelper.sin(f5 * 3.141593F);
/* 138:132 */     f7 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F);
/* 139:133 */     GL11.glTranslatef(-f7 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F * 2.0F) * 0.4F, -f6 * 0.4F);
/* 140:134 */     GL11.glTranslatef(0.8F * f13, -0.75F * f13 - (1.0F - f1) * 0.6F, -0.9F * f13);
/* 141:135 */     GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 142:136 */     GL11.glEnable(32826);
/* 143:137 */     f5 = player.getSwingProgress(p_78440_1_);
/* 144:138 */     f6 = MathHelper.sin(f5 * f5 * 3.141593F);
/* 145:139 */     f7 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F);
/* 146:140 */     GL11.glRotatef(f7 * 70.0F, 0.0F, 1.0F, 0.0F);
/* 147:141 */     GL11.glRotatef(-f6 * 20.0F, 0.0F, 0.0F, 1.0F);
/* 148:142 */     Minecraft.getMinecraft().getTextureManager().bindTexture(player.getLocationSkin());
/* 149:143 */     GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
/* 150:144 */     GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
/* 151:145 */     GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
/* 152:146 */     GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
/* 153:147 */     GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 154:148 */     GL11.glTranslatef(5.6F, 0.0F, 0.0F);
/* 155:149 */     RenderPlayer renderplayer = (RenderPlayer)RenderManager.instance.getEntityRenderObject(player);
/* 156:150 */     float f10 = 1.0F;
/* 157:151 */     GL11.glScalef(f10, f10, f10);
/* 158:152 */     renderplayer.renderFirstPersonArm(player);
/* 159:153 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 160:154 */     renderplayer.modelBipedMain.onGround = 0.0F;
/* 161:155 */     renderplayer.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);
/* 162:156 */     renderplayer.modelBipedMain.bipedRightArm.render(0.0625F);
/* 163:    */     
/* 164:158 */     int dmg = item.getItemDamage();
/* 165:    */     
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:163 */     float[] col = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[ItemGlove.getColIndex(1, dmg)];
/* 170:164 */     GL11.glColor3f(col[0], col[1], col[2]);
/* 171:165 */     Minecraft.getMinecraft().getTextureManager().bindTexture(glove1);
/* 172:166 */     renderplayer.modelBipedMain.bipedRightArm.render(0.0625F);
/* 173:    */     
/* 174:168 */     col = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[ItemGlove.getColIndex(0, dmg)];
/* 175:169 */     GL11.glColor3f(col[0], col[1], col[2]);
/* 176:170 */     Minecraft.getMinecraft().getTextureManager().bindTexture(glove2);
/* 177:171 */     renderplayer.modelBipedMain.bipedRightArm.render(0.0625F);
/* 178:    */     
/* 179:173 */     GL11.glPopMatrix();
/* 180:    */   }
/* 181:    */   
/* 182:176 */   private static final ResourceLocation glove1 = new ResourceLocation("extrautils", "textures/glove0.png");
/* 183:177 */   private static final ResourceLocation glove2 = new ResourceLocation("extrautils", "textures/glove1.png");
/* 184:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.RenderItemGlove
 * JD-Core Version:    0.7.0.1
 */