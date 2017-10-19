/*   1:    */ package com.rwtema.extrautils.dynamicgui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelperClient;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import net.minecraft.client.renderer.Tessellator;
/*  10:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  11:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraftforge.fluids.Fluid;
/*  15:    */ import net.minecraftforge.fluids.FluidStack;
/*  16:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  17:    */ import org.lwjgl.opengl.GL11;
/*  18:    */ 
/*  19:    */ public class WidgetTank
/*  20:    */   implements IWidget
/*  21:    */ {
/*  22: 20 */   public static final int[] ux2 = { 18, 18, 18 };
/*  23: 21 */   public static final int[] uy2 = { 0, 0, 0 };
/*  24: 22 */   public static final int[] uw2 = { 7, 7, 7 };
/*  25: 23 */   public static final int[] uh2 = { 64, 64, 64 };
/*  26: 24 */   public static final int[] ux = { 32, 0, 50 };
/*  27: 25 */   public static final int[] uy = { 0, 0, 0 };
/*  28: 26 */   public static final int[] uw = { 18, 18, 18 };
/*  29: 27 */   public static final int[] uh = { 33, 18, 65 };
/*  30:    */   FluidStack curFluid;
/*  31:    */   int curCapacity;
/*  32:    */   FluidTankInfo tankInfo;
/*  33:    */   int shape;
/*  34:    */   int x;
/*  35:    */   int y;
/*  36:    */   
/*  37:    */   public WidgetTank(FluidTankInfo tankInfo, int x, int y)
/*  38:    */   {
/*  39: 36 */     this(tankInfo, x, y, 0);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public WidgetTank(FluidTankInfo tankInfo, int x, int y, int shape)
/*  43:    */   {
/*  44: 41 */     this.curFluid = null;
/*  45: 42 */     this.curCapacity = 0;
/*  46: 43 */     this.tankInfo = tankInfo;
/*  47: 44 */     this.shape = shape;
/*  48: 45 */     this.x = x;
/*  49: 46 */     this.y = y;
/*  50:    */   }
/*  51:    */   
/*  52:    */   @SideOnly(Side.CLIENT)
/*  53:    */   public static void renderLiquid(FluidStack fluid, TextureManager manager, DynamicGui gui, int x, int y, int w, int h)
/*  54:    */   {
/*  55: 51 */     if (fluid == null) {
/*  56: 52 */       return;
/*  57:    */     }
/*  58: 55 */     if ((w == 0) || (h == 0)) {
/*  59: 56 */       return;
/*  60:    */     }
/*  61: 59 */     manager.bindTexture(fluid.getFluid().getSpriteNumber() == 0 ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
/*  62: 60 */     int color = fluid.getFluid().getColor(fluid);
/*  63: 61 */     float red = (color >> 16 & 0xFF) / 255.0F;
/*  64: 62 */     float green = (color >> 8 & 0xFF) / 255.0F;
/*  65: 63 */     float blue = (color & 0xFF) / 255.0F;
/*  66: 64 */     GL11.glColor4f(red, green, blue, 1.0F);
/*  67: 65 */     IIcon icon = fluid.getFluid().getIcon(fluid);
/*  68:    */     
/*  69:    */ 
/*  70: 68 */     Tessellator tessellator = Tessellator.instance;
/*  71: 70 */     for (int dx1 = x; dx1 < x + w; dx1 += 16) {
/*  72: 71 */       for (int dy1 = y; dy1 < y + h; dy1 += 16)
/*  73:    */       {
/*  74: 72 */         int dx2 = Math.min(dx1 + 16, x + w);
/*  75: 73 */         int dy2 = Math.min(dy1 + 16, y + h);
/*  76: 74 */         tessellator.startDrawingQuads();
/*  77: 75 */         tessellator.addVertexWithUV(dx1, dy2, gui.getZLevel(), icon.getMinU(), icon.getMinV() + (icon.getMaxV() - icon.getMinV()) * (dy2 - dy1) / 16.0F);
/*  78: 76 */         tessellator.addVertexWithUV(dx2, dy2, gui.getZLevel(), icon.getMinU() + (icon.getMaxU() - icon.getMinU()) * (dx2 - dx1) / 16.0F, icon.getMinV() + (icon.getMaxV() - icon.getMinV()) * (dy2 - dy1) / 16.0F);
/*  79:    */         
/*  80: 78 */         tessellator.addVertexWithUV(dx2, dy1, gui.getZLevel(), icon.getMinU() + (icon.getMaxU() - icon.getMinU()) * (dx2 - dx1) / 16.0F, icon.getMinV());
/*  81: 79 */         tessellator.addVertexWithUV(dx1, dy1, gui.getZLevel(), icon.getMinU(), icon.getMinV());
/*  82: 80 */         tessellator.draw();
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getX()
/*  88:    */   {
/*  89: 87 */     return this.x;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getY()
/*  93:    */   {
/*  94: 92 */     return this.y;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getW()
/*  98:    */   {
/*  99: 97 */     return uw[this.shape];
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getH()
/* 103:    */   {
/* 104:102 */     return uh[this.shape];
/* 105:    */   }
/* 106:    */   
/* 107:    */   public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/* 108:    */   {
/* 109:107 */     FluidStack newFluid = this.tankInfo.fluid;
/* 110:109 */     if ((changesOnly) && 
/* 111:110 */       (this.curCapacity == this.tankInfo.capacity)) {
/* 112:111 */       if (this.curFluid == null)
/* 113:    */       {
/* 114:112 */         if (newFluid == null) {
/* 115:113 */           return null;
/* 116:    */         }
/* 117:    */       }
/* 118:115 */       else if ((this.curFluid.isFluidEqual(newFluid)) && 
/* 119:116 */         (newFluid.amount == this.curFluid.amount)) {
/* 120:117 */         return null;
/* 121:    */       }
/* 122:    */     }
/* 123:122 */     this.curFluid = (newFluid != null ? newFluid.copy() : null);
/* 124:123 */     this.curCapacity = this.tankInfo.capacity;
/* 125:124 */     NBTTagCompound tag = new NBTTagCompound();
/* 126:125 */     tag.setInteger("capacity", this.curCapacity);
/* 127:127 */     if (this.curFluid != null) {
/* 128:128 */       this.curFluid.writeToNBT(tag);
/* 129:    */     }
/* 130:131 */     return tag;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void handleDescriptionPacket(NBTTagCompound packet)
/* 134:    */   {
/* 135:136 */     this.curCapacity = packet.getInteger("capacity");
/* 136:137 */     this.curFluid = FluidStack.loadFluidStackFromNBT(packet);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void renderForeground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/* 140:    */   {
/* 141:142 */     if ((this.curFluid != null) && (this.curFluid.getFluid() != null) && (this.curCapacity > 0))
/* 142:    */     {
/* 143:143 */       int a = this.curFluid.amount * (getH() - 2) / this.curCapacity;
/* 144:144 */       renderLiquid(this.curFluid, manager, gui, guiLeft + getX() + 1, guiTop + getY() - 1 + getH() - a, getW() - 2, a);
/* 145:    */     }
/* 146:147 */     manager.bindTexture(gui.getWidgets());
/* 147:148 */     gui.drawTexturedModalRect(guiLeft + getX() + getW() - uw2[this.shape] - 1, guiTop + getY() + 1, ux2[this.shape] + uw2[this.shape], uy2[this.shape], uw2[this.shape], Math.min(getH() - 2, uh2[this.shape]));
/* 148:149 */     gui.drawTexturedModalRect(guiLeft + getX() + 1, guiTop + getY() + 1, ux2[this.shape], uy2[this.shape], uw2[this.shape], Math.min(getH() - 2, uh2[this.shape]));
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void renderBackground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/* 152:    */   {
/* 153:154 */     manager.bindTexture(gui.getWidgets());
/* 154:155 */     gui.drawTexturedModalRect(guiLeft + getX(), guiTop + getY(), ux[this.shape], uy[this.shape], uw[this.shape], uh[this.shape]);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void addToContainer(DynamicContainer container) {}
/* 158:    */   
/* 159:    */   public List<String> getToolTip()
/* 160:    */   {
/* 161:164 */     if (this.curCapacity > 0)
/* 162:    */     {
/* 163:165 */       List l = new ArrayList();
/* 164:167 */       if (this.curFluid == null) {
/* 165:168 */         l.add("0 / " + XUHelperClient.commaDelimited(this.curCapacity));
/* 166:    */       } else {
/* 167:170 */         l.add(XUHelper.getFluidName(this.curFluid) + ": " + XUHelperClient.commaDelimited(this.curFluid.amount) + " / " + XUHelperClient.commaDelimited(this.curCapacity));
/* 168:    */       }
/* 169:173 */       return l;
/* 170:    */     }
/* 171:176 */     return null;
/* 172:    */   }
/* 173:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetTank
 * JD-Core Version:    0.7.0.1
 */