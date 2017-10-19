/*   1:    */ package com.rwtema.extrautils.sync;
/*   2:    */ 
/*   3:    */ import net.minecraft.item.ItemStack;
/*   4:    */ import net.minecraft.nbt.NBTTagCompound;
/*   5:    */ 
/*   6:    */ public class NBTHandlers
/*   7:    */ {
/*   8:    */   public static class NBTHandlerByte
/*   9:    */   {
/*  10:    */     public static void save(NBTTagCompound tag, String fieldName, byte b)
/*  11:    */     {
/*  12: 11 */       tag.setByte(fieldName, b);
/*  13:    */     }
/*  14:    */     
/*  15:    */     public static byte load(NBTTagCompound tag, String fieldName)
/*  16:    */     {
/*  17: 15 */       return tag.getByte(fieldName);
/*  18:    */     }
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static class NBTHandlerShort
/*  22:    */   {
/*  23:    */     public static void save(NBTTagCompound tag, String fieldName, short s)
/*  24:    */     {
/*  25: 21 */       tag.setShort(fieldName, s);
/*  26:    */     }
/*  27:    */     
/*  28:    */     public static short load(NBTTagCompound tag, String fieldName)
/*  29:    */     {
/*  30: 25 */       return tag.getShort(fieldName);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static class NBTHandlerInt
/*  35:    */   {
/*  36:    */     public static void save(NBTTagCompound tag, String fieldName, int s)
/*  37:    */     {
/*  38: 31 */       tag.setInteger(fieldName, s);
/*  39:    */     }
/*  40:    */     
/*  41:    */     public static int load(NBTTagCompound tag, String fieldName)
/*  42:    */     {
/*  43: 35 */       return tag.getInteger(fieldName);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static class NBTHandlerLong
/*  48:    */   {
/*  49:    */     public static void save(NBTTagCompound tag, String fieldName, long s)
/*  50:    */     {
/*  51: 41 */       tag.setLong(fieldName, s);
/*  52:    */     }
/*  53:    */     
/*  54:    */     public static long load(NBTTagCompound tag, String fieldName)
/*  55:    */     {
/*  56: 45 */       return tag.getLong(fieldName);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static class NBTHandlerFloat
/*  61:    */   {
/*  62:    */     public static void save(NBTTagCompound tag, String fieldName, float s)
/*  63:    */     {
/*  64: 51 */       tag.setFloat(fieldName, s);
/*  65:    */     }
/*  66:    */     
/*  67:    */     public static float load(NBTTagCompound tag, String fieldName)
/*  68:    */     {
/*  69: 55 */       return tag.getFloat(fieldName);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static class NBTHandlerDouble
/*  74:    */   {
/*  75:    */     public static void save(NBTTagCompound tag, String fieldName, double s)
/*  76:    */     {
/*  77: 61 */       tag.setDouble(fieldName, s);
/*  78:    */     }
/*  79:    */     
/*  80:    */     public static double load(NBTTagCompound tag, String fieldName)
/*  81:    */     {
/*  82: 65 */       return tag.getDouble(fieldName);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static class NBTHandlerByteArray
/*  87:    */   {
/*  88:    */     public static void save(NBTTagCompound tag, String fieldName, byte[] s)
/*  89:    */     {
/*  90: 71 */       tag.setByteArray(fieldName, s);
/*  91:    */     }
/*  92:    */     
/*  93:    */     public static byte[] load(NBTTagCompound tag, String fieldName)
/*  94:    */     {
/*  95: 75 */       return tag.getByteArray(fieldName);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static class NBTHandlerString
/* 100:    */   {
/* 101:    */     public static void save(NBTTagCompound tag, String fieldName, String s)
/* 102:    */     {
/* 103: 81 */       tag.setString(fieldName, s);
/* 104:    */     }
/* 105:    */     
/* 106:    */     public static String load(NBTTagCompound tag, String fieldName)
/* 107:    */     {
/* 108: 85 */       return tag.getString(fieldName);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static class NBTHandlerNBT
/* 113:    */   {
/* 114:    */     public static void save(NBTTagCompound tag, String fieldName, NBTTagCompound s)
/* 115:    */     {
/* 116: 91 */       tag.setTag(fieldName, s);
/* 117:    */     }
/* 118:    */     
/* 119:    */     public static NBTTagCompound load(NBTTagCompound tag, String fieldName)
/* 120:    */     {
/* 121: 95 */       return tag.getCompoundTag(fieldName);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static class NBTHandlerIntArray
/* 126:    */   {
/* 127:    */     public static void save(NBTTagCompound tag, String fieldName, int[] s)
/* 128:    */     {
/* 129:101 */       tag.setIntArray(fieldName, s);
/* 130:    */     }
/* 131:    */     
/* 132:    */     public static int[] load(NBTTagCompound tag, String fieldName)
/* 133:    */     {
/* 134:105 */       return tag.getIntArray(fieldName);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static class NBTHandlerItemStack
/* 139:    */   {
/* 140:    */     public static void save(NBTTagCompound tag, String fieldName, ItemStack s)
/* 141:    */     {
/* 142:111 */       if (s != null) {
/* 143:112 */         tag.setTag(fieldName, s.writeToNBT(new NBTTagCompound()));
/* 144:    */       }
/* 145:    */     }
/* 146:    */     
/* 147:    */     public static ItemStack load(NBTTagCompound tag, String fieldName)
/* 148:    */     {
/* 149:116 */       return ItemStack.loadItemStackFromNBT(tag.getCompoundTag(fieldName));
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static class NBTHandlerBoolean
/* 154:    */   {
/* 155:    */     public static void save(NBTTagCompound tag, String fieldName, boolean s)
/* 156:    */     {
/* 157:122 */       tag.setBoolean(fieldName, s);
/* 158:    */     }
/* 159:    */     
/* 160:    */     public static boolean load(NBTTagCompound tag, String fieldName)
/* 161:    */     {
/* 162:126 */       return tag.getBoolean(fieldName);
/* 163:    */     }
/* 164:    */   }
/* 165:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.sync.NBTHandlers
 * JD-Core Version:    0.7.0.1
 */