/*   1:    */ package com.rwtema.extrautils.dynamicgui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.LogHelper;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import net.minecraft.client.gui.FontRenderer;
/*   7:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   8:    */ import net.minecraft.nbt.NBTBase;
/*   9:    */ import net.minecraft.nbt.NBTTagByte;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.nbt.NBTTagDouble;
/*  12:    */ import net.minecraft.nbt.NBTTagFloat;
/*  13:    */ import net.minecraft.nbt.NBTTagInt;
/*  14:    */ import net.minecraft.nbt.NBTTagLong;
/*  15:    */ import net.minecraft.nbt.NBTTagShort;
/*  16:    */ import net.minecraft.nbt.NBTTagString;
/*  17:    */ import org.lwjgl.opengl.GL11;
/*  18:    */ 
/*  19:    */ public abstract class WidgetTextData
/*  20:    */   extends WidgetText
/*  21:    */   implements IWidget
/*  22:    */ {
/*  23: 19 */   public Object[] curData = null;
/*  24:    */   
/*  25:    */   public WidgetTextData(int x, int y, int align, int color)
/*  26:    */   {
/*  27: 22 */     super(x, y, align, color, null);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public WidgetTextData(int x, int y, int w, int h, int align, int color)
/*  31:    */   {
/*  32: 26 */     super(x, y, w, h, align, color, null);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public WidgetTextData(int x, int y, int w)
/*  36:    */   {
/*  37: 30 */     super(x, y, null, w);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static Object getNBTBaseData(NBTBase nbt)
/*  41:    */   {
/*  42: 34 */     if (nbt == null) {
/*  43: 35 */       return null;
/*  44:    */     }
/*  45: 37 */     switch (nbt.getId())
/*  46:    */     {
/*  47:    */     case 1: 
/*  48: 39 */       return Byte.valueOf(((NBTTagByte)nbt).func_150290_f());
/*  49:    */     case 2: 
/*  50: 42 */       return Short.valueOf(((NBTTagShort)nbt).func_150289_e());
/*  51:    */     case 3: 
/*  52: 45 */       return Integer.valueOf(((NBTTagInt)nbt).func_150287_d());
/*  53:    */     case 4: 
/*  54: 48 */       return Long.valueOf(((NBTTagLong)nbt).func_150291_c());
/*  55:    */     case 5: 
/*  56: 51 */       return Float.valueOf(((NBTTagFloat)nbt).func_150288_h());
/*  57:    */     case 6: 
/*  58: 54 */       return Double.valueOf(((NBTTagDouble)nbt).func_150286_g());
/*  59:    */     case 8: 
/*  60: 57 */       return ((NBTTagString)nbt).func_150285_a_();
/*  61:    */     case 10: 
/*  62: 60 */       return nbt;
/*  63:    */     }
/*  64: 63 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static NBTBase getNBTBase(Object o)
/*  68:    */   {
/*  69: 68 */     if ((o instanceof Integer)) {
/*  70: 69 */       return new NBTTagInt(((Integer)o).intValue());
/*  71:    */     }
/*  72: 72 */     if ((o instanceof Short)) {
/*  73: 73 */       return new NBTTagShort(((Short)o).shortValue());
/*  74:    */     }
/*  75: 76 */     if ((o instanceof Long)) {
/*  76: 77 */       return new NBTTagLong(((Long)o).longValue());
/*  77:    */     }
/*  78: 80 */     if ((o instanceof String)) {
/*  79: 81 */       return new NBTTagString((String)o);
/*  80:    */     }
/*  81: 84 */     if ((o instanceof Double)) {
/*  82: 85 */       return new NBTTagDouble(((Double)o).doubleValue());
/*  83:    */     }
/*  84: 88 */     if ((o instanceof Float)) {
/*  85: 89 */       return new NBTTagFloat(((Float)o).floatValue());
/*  86:    */     }
/*  87: 92 */     if ((o instanceof NBTTagCompound)) {
/*  88: 93 */       return (NBTTagCompound)o;
/*  89:    */     }
/*  90: 96 */     if ((o instanceof Byte)) {
/*  91: 97 */       return new NBTTagByte(((Byte)o).byteValue());
/*  92:    */     }
/*  93:100 */     LogHelper.debug("Can't find type for " + o, new Object[0]);
/*  94:101 */     throw null;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public abstract int getNumParams();
/*  98:    */   
/*  99:    */   public abstract Object[] getData();
/* 100:    */   
/* 101:    */   public abstract String getConstructedText();
/* 102:    */   
/* 103:    */   public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/* 104:    */   {
/* 105:112 */     Object[] newData = getData();
/* 106:114 */     if (this.curData == null)
/* 107:    */     {
/* 108:115 */       this.curData = new Object[getNumParams()];
/* 109:116 */       changesOnly = false;
/* 110:    */     }
/* 111:119 */     NBTTagCompound tag = new NBTTagCompound();
/* 112:121 */     for (int i = 0; i < this.curData.length; i++)
/* 113:    */     {
/* 114:123 */       if ((newData[i] != null) && ((!changesOnly) || (this.curData[i] == null) || (!this.curData[i].toString().equals(newData[i].toString()))))
/* 115:    */       {
/* 116:124 */         NBTBase nbtBase = getNBTBase(newData[i]);
/* 117:125 */         if (nbtBase != null) {
/* 118:126 */           tag.setTag(Integer.toString(i), nbtBase);
/* 119:    */         }
/* 120:    */       }
/* 121:129 */       this.curData[i] = newData[i];
/* 122:    */     }
/* 123:132 */     if (tag.hasNoTags()) {
/* 124:133 */       return null;
/* 125:    */     }
/* 126:135 */     return tag;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void handleDescriptionPacket(NBTTagCompound packet)
/* 130:    */   {
/* 131:141 */     if (this.curData == null) {
/* 132:142 */       this.curData = new Object[getNumParams()];
/* 133:    */     }
/* 134:145 */     int n = getNumParams();
/* 135:146 */     for (int i = 0; i < n; i++)
/* 136:    */     {
/* 137:147 */       NBTBase base = packet.getTag(Integer.toString(i));
/* 138:148 */       if (base != null) {
/* 139:149 */         this.curData[i] = getNBTBaseData(base);
/* 140:    */       }
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   @SideOnly(Side.CLIENT)
/* 145:    */   public void renderBackground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/* 146:    */   {
/* 147:156 */     if (this.curData == null) {
/* 148:157 */       return;
/* 149:    */     }
/* 150:160 */     int x = getX() + (1 - this.align) * (getW() - gui.getFontRenderer().getStringWidth(getMsgClient())) / 2;
/* 151:161 */     gui.getFontRenderer().drawSplitString(getConstructedText(), guiLeft + x, guiTop + getY(), getW(), 4210752);
/* 152:162 */     manager.bindTexture(gui.getWidgets());
/* 153:163 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 154:    */   }
/* 155:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetTextData
 * JD-Core Version:    0.7.0.1
 */