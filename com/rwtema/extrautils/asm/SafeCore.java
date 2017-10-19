/*   1:    */ package com.rwtema.extrautils.asm;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.FMLLaunchHandler;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import net.minecraft.launchwrapper.IClassTransformer;
/*  11:    */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*  12:    */ import org.objectweb.asm.ClassReader;
/*  13:    */ import org.objectweb.asm.ClassWriter;
/*  14:    */ import org.objectweb.asm.Type;
/*  15:    */ import org.objectweb.asm.tree.AnnotationNode;
/*  16:    */ import org.objectweb.asm.tree.ClassNode;
/*  17:    */ import org.objectweb.asm.tree.FieldNode;
/*  18:    */ import org.objectweb.asm.tree.MethodNode;
/*  19:    */ 
/*  20:    */ public class SafeCore
/*  21:    */   implements IClassTransformer
/*  22:    */ {
/*  23: 22 */   private static Side INVALID_SIDE = FMLLaunchHandler.side() == Side.CLIENT ? Side.SERVER : Side.CLIENT;
/*  24: 24 */   private static String SIDE_SERVER = Side.SERVER.name();
/*  25: 25 */   private static String SIDE_CLIENT = Side.CLIENT.name();
/*  26: 27 */   LaunchClassLoader cl = (LaunchClassLoader)SafeCore.class.getClassLoader();
/*  27: 30 */   private static HashMap<String, Side> classSideHashMap = new HashMap();
/*  28:    */   
/*  29:    */   static
/*  30:    */   {
/*  31: 31 */     classSideHashMap.put(Boolean.TYPE.getName(), null);
/*  32: 32 */     classSideHashMap.put(Byte.TYPE.getName(), null);
/*  33: 33 */     classSideHashMap.put(Character.TYPE.getName(), null);
/*  34: 34 */     classSideHashMap.put(Double.TYPE.getName(), null);
/*  35: 35 */     classSideHashMap.put(Float.TYPE.getName(), null);
/*  36: 36 */     classSideHashMap.put(Integer.TYPE.getName(), null);
/*  37: 37 */     classSideHashMap.put(Long.TYPE.getName(), null);
/*  38: 38 */     classSideHashMap.put(Short.TYPE.getName(), null);
/*  39: 39 */     classSideHashMap.put(Void.TYPE.getName(), null);
/*  40:    */   }
/*  41:    */   
/*  42: 42 */   public String[] clientPrefixes = { "net.minecraft.", "net.minecraftforge.", "cpw.mods.fml", "com.rwtema.extrautils." };
/*  43:    */   
/*  44:    */   public Side getSide(String clazz)
/*  45:    */   {
/*  46: 50 */     if (classSideHashMap.containsKey(clazz)) {
/*  47: 51 */       return (Side)classSideHashMap.get(clazz);
/*  48:    */     }
/*  49: 53 */     Side side = getSide_do(clazz);
/*  50: 54 */     classSideHashMap.put(clazz, side);
/*  51: 55 */     return side;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Side getSide_do(String clazz)
/*  55:    */   {
/*  56: 59 */     if (clazz.indexOf('.') == -1) {
/*  57: 60 */       return null;
/*  58:    */     }
/*  59:    */     byte[] bytes;
/*  60:    */     try
/*  61:    */     {
/*  62: 64 */       bytes = this.cl.getClassBytes(clazz);
/*  63:    */     }
/*  64:    */     catch (IOException e)
/*  65:    */     {
/*  66: 66 */       return INVALID_SIDE;
/*  67:    */     }
/*  68: 69 */     if (bytes == null) {
/*  69: 70 */       return INVALID_SIDE;
/*  70:    */     }
/*  71: 72 */     boolean flag = true;
/*  72: 73 */     for (String clientPrefix : this.clientPrefixes) {
/*  73: 74 */       if (clazz.startsWith(clientPrefix))
/*  74:    */       {
/*  75: 75 */         flag = false;
/*  76: 76 */         break;
/*  77:    */       }
/*  78:    */     }
/*  79: 79 */     if (flag) {
/*  80: 80 */       return null;
/*  81:    */     }
/*  82: 82 */     ClassNode classNode = new ClassNode();
/*  83: 83 */     ClassReader classReader = new ClassReader(bytes);
/*  84: 84 */     classReader.accept(classNode, 0);
/*  85:    */     
/*  86: 86 */     return getSide(classNode.visibleAnnotations);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Side getSide(List<AnnotationNode> anns)
/*  90:    */   {
/*  91: 90 */     if (anns == null) {
/*  92: 91 */       return null;
/*  93:    */     }
/*  94: 93 */     for (AnnotationNode ann : anns) {
/*  95: 94 */       if ((ann.desc.equals(Type.getDescriptor(SideOnly.class))) && 
/*  96: 95 */         (ann.values != null)) {
/*  97: 96 */         for (int x = 0; x < ann.values.size() - 1; x += 2)
/*  98:    */         {
/*  99: 97 */           Object key = ann.values.get(x);
/* 100: 98 */           Object value = ann.values.get(x + 1);
/* 101: 99 */           if (((key instanceof String)) && (key.equals("value")) && 
/* 102:100 */             ((value instanceof String[])))
/* 103:    */           {
/* 104:101 */             String s = ((String[])(String[])value)[1];
/* 105:102 */             if (s.equals(SIDE_SERVER)) {
/* 106:103 */               return Side.SERVER;
/* 107:    */             }
/* 108:105 */             if (s.equals(SIDE_CLIENT)) {
/* 109:106 */               return Side.CLIENT;
/* 110:    */             }
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:115 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isInvalid(String clazz)
/* 119:    */   {
/* 120:119 */     return getSide(clazz) == INVALID_SIDE;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isInvalid(Type type)
/* 124:    */   {
/* 125:123 */     while (type.getSort() == 9) {
/* 126:124 */       type = type.getElementType();
/* 127:    */     }
/* 128:125 */     return (type.getSort() != 11) && (isInvalid(type.getClassName()));
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isInvalid(Type[] types)
/* 132:    */   {
/* 133:129 */     for (Type type : types) {
/* 134:130 */       if (isInvalid(type)) {
/* 135:131 */         return true;
/* 136:    */       }
/* 137:    */     }
/* 138:133 */     return false;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public byte[] transform(String s, String s2, byte[] bytes)
/* 142:    */   {
/* 143:138 */     if (!s.startsWith("com.rwtema.extrautils")) {
/* 144:139 */       return bytes;
/* 145:    */     }
/* 146:141 */     ClassNode classNode = new ClassNode();
/* 147:142 */     ClassReader reader = new ClassReader(bytes);
/* 148:143 */     reader.accept(classNode, 0);
/* 149:    */     
/* 150:145 */     Side side = getSide(classNode.visibleAnnotations);
/* 151:146 */     classSideHashMap.put(s, side);
/* 152:    */     
/* 153:148 */     stripInvalidAnnotations(classNode.visibleAnnotations);
/* 154:150 */     for (Iterator<FieldNode> iterator = classNode.fields.iterator(); iterator.hasNext();)
/* 155:    */     {
/* 156:151 */       FieldNode field = (FieldNode)iterator.next();
/* 157:152 */       if (isInvalid(Type.getType(field.desc))) {
/* 158:153 */         iterator.remove();
/* 159:    */       } else {
/* 160:155 */         stripInvalidAnnotations(field.visibleAnnotations);
/* 161:    */       }
/* 162:    */     }
/* 163:159 */     for (Iterator<MethodNode> iterator = classNode.methods.iterator(); iterator.hasNext();)
/* 164:    */     {
/* 165:160 */       MethodNode method = (MethodNode)iterator.next();
/* 166:162 */       if (invalidMethod(method)) {
/* 167:163 */         iterator.remove();
/* 168:    */       } else {
/* 169:165 */         stripInvalidAnnotations(method.visibleAnnotations);
/* 170:    */       }
/* 171:    */     }
/* 172:168 */     ClassWriter writer = new ClassWriter(1);
/* 173:169 */     classNode.accept(writer);
/* 174:170 */     return writer.toByteArray();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void stripInvalidAnnotations(List<AnnotationNode> visibleAnnotations)
/* 178:    */   {
/* 179:174 */     if (visibleAnnotations == null) {
/* 180:175 */       return;
/* 181:    */     }
/* 182:177 */     for (Iterator<AnnotationNode> iterator = visibleAnnotations.iterator(); iterator.hasNext();)
/* 183:    */     {
/* 184:178 */       AnnotationNode visibleAnnotation = (AnnotationNode)iterator.next();
/* 185:179 */       if (isInvalid(Type.getType(visibleAnnotation.desc))) {
/* 186:180 */         iterator.remove();
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean invalidMethod(MethodNode method)
/* 192:    */   {
/* 193:185 */     if (isInvalid(Type.getReturnType(method.desc))) {
/* 194:186 */       return true;
/* 195:    */     }
/* 196:189 */     for (Type type : Type.getArgumentTypes(method.desc)) {
/* 197:190 */       if (isInvalid(type)) {
/* 198:191 */         return true;
/* 199:    */       }
/* 200:    */     }
/* 201:195 */     return false;
/* 202:    */   }
/* 203:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.asm.SafeCore
 * JD-Core Version:    0.7.0.1
 */