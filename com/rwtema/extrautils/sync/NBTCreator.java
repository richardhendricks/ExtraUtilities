/*   1:    */ package com.rwtema.extrautils.sync;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import org.objectweb.asm.ClassReader;
/*  11:    */ import org.objectweb.asm.ClassWriter;
/*  12:    */ import org.objectweb.asm.MethodVisitor;
/*  13:    */ import org.objectweb.asm.Type;
/*  14:    */ import org.objectweb.asm.tree.AnnotationNode;
/*  15:    */ import org.objectweb.asm.tree.ClassNode;
/*  16:    */ import org.objectweb.asm.tree.FieldNode;
/*  17:    */ 
/*  18:    */ public class NBTCreator
/*  19:    */ {
/*  20: 28 */   public static LaunchClassLoader cl = (LaunchClassLoader)NBTCreator.class.getClassLoader();
/*  21: 30 */   public static String type = Type.getDescriptor(Sync.class);
/*  22:    */   
/*  23:    */   public static AutoNBT createAutoNBT(Class<?> clazz)
/*  24:    */   {
/*  25: 34 */     String targetName = clazz.getName();
/*  26: 35 */     String targetType = Type.getDescriptor(clazz);
/*  27:    */     byte[] bytes;
/*  28:    */     try
/*  29:    */     {
/*  30: 38 */       bytes = cl.getClassBytes(targetName);
/*  31:    */     }
/*  32:    */     catch (Exception e)
/*  33:    */     {
/*  34: 40 */       throw new RuntimeException(e);
/*  35:    */     }
/*  36: 42 */     List<FieldNode> fields = new ArrayList();
/*  37:    */     
/*  38:    */ 
/*  39: 45 */     ClassNode classNode = new ClassNode();
/*  40: 46 */     ClassReader classReader = new ClassReader(bytes);
/*  41: 47 */     classReader.accept(classNode, 1);
/*  42: 48 */     for (Iterator i$ = classNode.fields.iterator(); i$.hasNext();)
/*  43:    */     {
/*  44: 48 */       field = (FieldNode)i$.next();
/*  45: 49 */       for (AnnotationNode ann : field.visibleAnnotations) {
/*  46: 50 */         if (type.equals(ann.desc))
/*  47:    */         {
/*  48: 51 */           fields.add(field);
/*  49: 52 */           break;
/*  50:    */         }
/*  51:    */       }
/*  52:    */     }
/*  53:    */     FieldNode field;
/*  54: 59 */     ClassWriter cw = new ClassWriter(1);
/*  55:    */     
/*  56:    */ 
/*  57: 62 */     String name = "FLM_ItemWrench";
/*  58: 63 */     String superName = Type.getInternalName(AutoNBT.class);
/*  59:    */     
/*  60: 65 */     cw.visit(50, 33, name, null, superName, new String[0]);
/*  61:    */     
/*  62: 67 */     cw.visitSource(".dynamic", null);
/*  63:    */     
/*  64:    */ 
/*  65: 70 */     MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", null, null);
/*  66: 71 */     mv.visitCode();
/*  67: 72 */     mv.visitVarInsn(25, 0);
/*  68: 73 */     mv.visitMethodInsn(183, superName, "<init>", "()V", false);
/*  69: 74 */     mv.visitInsn(177);
/*  70: 75 */     mv.visitMaxs(1, 1);
/*  71: 76 */     mv.visitEnd();
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75: 80 */     mv = cw.visitMethod(1, "writeToNBT", "(Lnet/minecraft/nbt/NBTTagCompound;Ljava/lang/Object;)V", null, null);
/*  76: 81 */     mv.visitCode();
/*  77: 82 */     mv.visitVarInsn(25, 2);
/*  78: 83 */     mv.visitTypeInsn(192, targetType);
/*  79: 84 */     mv.visitVarInsn(58, 3);
/*  80: 86 */     for (FieldNode field : fields)
/*  81:    */     {
/*  82: 87 */       mv.visitVarInsn(25, 1);
/*  83: 88 */       mv.visitLdcInsn(field.name);
/*  84: 89 */       mv.visitVarInsn(25, 3);
/*  85: 90 */       mv.visitFieldInsn(180, targetType, field.name, field.desc);
/*  86: 91 */       mv.visitMethodInsn(184, getTargetForField(field), "save", "(Lnet/minecraft/nbt/NBTTagCompound;Ljava/lang/String;" + field.desc + ")V", false);
/*  87:    */     }
/*  88: 94 */     mv.visitInsn(177);
/*  89: 95 */     mv.visitEnd();
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93: 99 */     mv = cw.visitMethod(1, "readFromNBT", "(Lnet/minecraft/nbt/NBTTagCompound;Ljava/lang/Object;)V", null, null);
/*  94:100 */     mv.visitCode();
/*  95:101 */     mv.visitVarInsn(25, 2);
/*  96:102 */     mv.visitTypeInsn(192, targetType);
/*  97:103 */     mv.visitVarInsn(58, 3);
/*  98:105 */     for (FieldNode field : fields)
/*  99:    */     {
/* 100:106 */       mv.visitVarInsn(25, 1);
/* 101:107 */       mv.visitLdcInsn(field.name);
/* 102:108 */       mv.visitVarInsn(25, 3);
/* 103:109 */       mv.visitFieldInsn(180, targetType, field.name, field.desc);
/* 104:110 */       mv.visitMethodInsn(184, getTargetForField(field), "save", "(Lnet/minecraft/nbt/NBTTagCompound;Ljava/lang/String;" + field.desc + ")V", false);
/* 105:    */     }
/* 106:113 */     mv.visitInsn(177);
/* 107:114 */     mv.visitEnd();
/* 108:    */     
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:157 */     cw.visitEnd();
/* 151:    */     
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:167 */     return null;
/* 161:    */   }
/* 162:    */   
/* 163:170 */   private static final HashMap<Type, Class<?>> handlers = new HashMap();
/* 164:    */   
/* 165:    */   static
/* 166:    */   {
/* 167:173 */     handlers.put(Type.BOOLEAN_TYPE, NBTHandlers.NBTHandlerBoolean.class);
/* 168:174 */     handlers.put(Type.BYTE_TYPE, NBTHandlers.NBTHandlerByte.class);
/* 169:175 */     handlers.put(Type.SHORT_TYPE, NBTHandlers.NBTHandlerShort.class);
/* 170:176 */     handlers.put(Type.INT_TYPE, NBTHandlers.NBTHandlerInt.class);
/* 171:177 */     handlers.put(Type.LONG_TYPE, NBTHandlers.NBTHandlerLong.class);
/* 172:178 */     handlers.put(Type.FLOAT_TYPE, NBTHandlers.NBTHandlerFloat.class);
/* 173:179 */     handlers.put(Type.DOUBLE_TYPE, NBTHandlers.NBTHandlerDouble.class);
/* 174:180 */     handlers.put(Type.getType([B.class), NBTHandlers.NBTHandlerByteArray.class);
/* 175:181 */     handlers.put(Type.getType(String.class), NBTHandlers.NBTHandlerString.class);
/* 176:182 */     handlers.put(Type.getType(NBTTagCompound.class), NBTHandlers.NBTHandlerNBT.class);
/* 177:183 */     handlers.put(Type.getType([I.class), NBTHandlers.NBTHandlerIntArray.class);
/* 178:184 */     handlers.put(Type.getType(ItemStack.class), NBTHandlers.NBTHandlerItemStack.class);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static String getTargetForField(FieldNode field)
/* 182:    */   {
/* 183:189 */     return Type.getDescriptor((Class)handlers.get(Type.getType(field.desc)));
/* 184:    */   }
/* 185:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.sync.NBTCreator
 * JD-Core Version:    0.7.0.1
 */