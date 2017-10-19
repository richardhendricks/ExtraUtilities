/*   1:    */ package com.rwtema.extrautils.dynamicgui;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.IEnergyHandler;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelperClient;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  12:    */ 
/*  13:    */ public class WidgetEnergy
/*  14:    */   implements IWidget
/*  15:    */ {
/*  16:    */   int curEnergy;
/*  17:    */   int curMax;
/*  18:    */   IEnergyHandler tile;
/*  19:    */   ForgeDirection dir;
/*  20:    */   int x;
/*  21:    */   int y;
/*  22:    */   
/*  23:    */   public WidgetEnergy(IEnergyHandler tile, ForgeDirection dir, int x, int y)
/*  24:    */   {
/*  25: 25 */     this.dir = dir;
/*  26: 26 */     this.tile = tile;
/*  27: 27 */     this.x = x;
/*  28: 28 */     this.y = y;
/*  29: 29 */     this.curEnergy = 0;
/*  30: 30 */     this.curMax = 0;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getX()
/*  34:    */   {
/*  35: 35 */     return this.x;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getY()
/*  39:    */   {
/*  40: 40 */     return this.y;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getW()
/*  44:    */   {
/*  45: 45 */     return 18;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getH()
/*  49:    */   {
/*  50: 50 */     return 53;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/*  54:    */   {
/*  55: 55 */     NBTTagCompound tag = null;
/*  56: 57 */     if ((!changesOnly) || (this.curEnergy != this.tile.getEnergyStored(this.dir)))
/*  57:    */     {
/*  58: 58 */       tag = new NBTTagCompound();
/*  59: 59 */       tag.setInteger("cur", this.tile.getEnergyStored(this.dir));
/*  60:    */     }
/*  61: 62 */     if ((!changesOnly) || (this.curMax != this.tile.getMaxEnergyStored(this.dir)))
/*  62:    */     {
/*  63: 63 */       if (tag == null) {
/*  64: 64 */         tag = new NBTTagCompound();
/*  65:    */       }
/*  66: 67 */       tag.setInteger("max", this.tile.getMaxEnergyStored(this.dir));
/*  67:    */     }
/*  68: 70 */     this.curEnergy = this.tile.getEnergyStored(this.dir);
/*  69: 71 */     this.curMax = this.tile.getMaxEnergyStored(this.dir);
/*  70: 72 */     return tag;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void handleDescriptionPacket(NBTTagCompound packet)
/*  74:    */   {
/*  75: 77 */     if (packet.hasKey("cur")) {
/*  76: 78 */       this.curEnergy = packet.getInteger("cur");
/*  77:    */     }
/*  78: 81 */     if (packet.hasKey("max")) {
/*  79: 82 */       this.curMax = packet.getInteger("max");
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void renderForeground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop) {}
/*  84:    */   
/*  85:    */   @SideOnly(Side.CLIENT)
/*  86:    */   public void renderBackground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/*  87:    */   {
/*  88: 93 */     manager.bindTexture(gui.getWidgets());
/*  89: 94 */     int y = 0;
/*  90: 96 */     if ((this.curMax > 0) && (this.curEnergy > 0))
/*  91:    */     {
/*  92: 97 */       y = 54 * this.curEnergy / this.curMax;
/*  93: 99 */       if (y < 0) {
/*  94:100 */         y = 0;
/*  95:    */       }
/*  96:    */     }
/*  97:104 */     gui.drawTexturedModalRect(guiLeft + getX(), guiTop + getY(), 160, 0, 18, 54 - y);
/*  98:105 */     gui.drawTexturedModalRect(guiLeft + getX(), guiTop + getY() + 54 - y, 178, 54 - y, 18, y);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void addToContainer(DynamicContainer container) {}
/* 102:    */   
/* 103:    */   @SideOnly(Side.CLIENT)
/* 104:    */   public List<String> getToolTip()
/* 105:    */   {
/* 106:115 */     if (this.curMax > 0)
/* 107:    */     {
/* 108:116 */       List l = new ArrayList();
/* 109:117 */       l.add(XUHelperClient.commaDelimited(this.curEnergy) + " / " + XUHelperClient.commaDelimited(this.curMax) + " RF");
/* 110:118 */       return l;
/* 111:    */     }
/* 112:121 */     return null;
/* 113:    */   }
/* 114:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetEnergy
 * JD-Core Version:    0.7.0.1
 */