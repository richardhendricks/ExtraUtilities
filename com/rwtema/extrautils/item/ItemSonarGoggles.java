/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.client.gui.ScaledResolution;
/*  8:   */ import net.minecraft.client.renderer.Tessellator;
/*  9:   */ import net.minecraft.client.renderer.texture.TextureManager;
/* 10:   */ import net.minecraft.entity.Entity;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.item.ItemArmor;
/* 13:   */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/* 14:   */ import net.minecraft.item.ItemStack;
/* 15:   */ import net.minecraft.util.ResourceLocation;
/* 16:   */ import org.lwjgl.opengl.GL11;
/* 17:   */ 
/* 18:   */ public class ItemSonarGoggles
/* 19:   */   extends ItemArmor
/* 20:   */ {
/* 21:17 */   private static final ResourceLocation texture = new ResourceLocation("extrautils", "textures/goggle_overlay.png");
/* 22:   */   
/* 23:   */   public ItemSonarGoggles()
/* 24:   */   {
/* 25:20 */     super(ItemArmor.ArmorMaterial.IRON, 0, 0);
/* 26:21 */     setMaxDamage(1800);
/* 27:22 */     setUnlocalizedName("extrautils:sonar_goggles");
/* 28:23 */     setTextureName("extrautils:sonar_goggles");
/* 29:24 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 30:25 */     setMaxStackSize(1);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/* 34:   */   {
/* 35:31 */     return "extrautils:textures/sonar_lens.png";
/* 36:   */   }
/* 37:   */   
/* 38:   */   @SideOnly(Side.CLIENT)
/* 39:   */   public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY)
/* 40:   */   {
/* 41:37 */     double w = resolution.getScaledWidth_double();
/* 42:38 */     double h = resolution.getScaledHeight_double();
/* 43:39 */     GL11.glDisable(2929);
/* 44:   */     
/* 45:41 */     GL11.glBlendFunc(770, 771);
/* 46:42 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.2F);
/* 47:43 */     GL11.glDisable(3008);
/* 48:44 */     GL11.glDisable(3553);
/* 49:45 */     Tessellator tessellator = Tessellator.instance;
/* 50:46 */     tessellator.startDrawingQuads();
/* 51:47 */     tessellator.addVertex(0.0D, h, -90.0D);
/* 52:48 */     tessellator.addVertex(w, h, -90.0D);
/* 53:49 */     tessellator.addVertex(w, 0.0D, -90.0D);
/* 54:50 */     tessellator.addVertex(0.0D, 0.0D, -90.0D);
/* 55:51 */     tessellator.draw();
/* 56:52 */     GL11.glEnable(3553);
/* 57:53 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 58:54 */     Minecraft.getMinecraft().renderEngine.bindTexture(texture);
/* 59:55 */     tessellator.startDrawingQuads();
/* 60:56 */     tessellator.addVertexWithUV(0.0D, h, -90.0D, 0.0D, 1.0D);
/* 61:57 */     tessellator.addVertexWithUV(w, h, -90.0D, 1.0D, 1.0D);
/* 62:58 */     tessellator.addVertexWithUV(w, 0.0D, -90.0D, 1.0D, 0.0D);
/* 63:59 */     tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
/* 64:60 */     tessellator.draw();
/* 65:   */     
/* 66:62 */     GL11.glEnable(2929);
/* 67:63 */     GL11.glEnable(3008);
/* 68:64 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 69:   */   }
/* 70:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemSonarGoggles
 * JD-Core Version:    0.7.0.1
 */