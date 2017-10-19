/*  1:   */ package com.rwtema.extrautils.asm;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Throwables;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.lang.reflect.Modifier;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*  8:   */ import org.objectweb.asm.ClassWriter;
/*  9:   */ import org.objectweb.asm.MethodVisitor;
/* 10:   */ import org.objectweb.asm.Type;
/* 11:   */ 
/* 12:   */ public class RemoteCallFactory
/* 13:   */ {
/* 14:   */   static LaunchClassLoader cl;
/* 15:   */   static Method m_defineClass;
/* 16:   */   
/* 17:   */   static
/* 18:   */   {
/* 19:17 */     cl = (LaunchClassLoader)RemoteCallFactory.class.getClassLoader();
/* 20:   */     try
/* 21:   */     {
/* 22:22 */       m_defineClass = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, [B.class, Integer.TYPE, Integer.TYPE });
/* 23:23 */       m_defineClass.setAccessible(true);
/* 24:   */     }
/* 25:   */     catch (Exception e)
/* 26:   */     {
/* 27:25 */       throw new RuntimeException(e);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:29 */   public static IObjectEvaluate<ItemStack> pulverizer = getEvaluator("cofh.thermalexpansion.util.crafting.PulverizerManager", "recipeExists", ItemStack.class);
/* 32:   */   
/* 33:   */   public static <T> IObjectEvaluate<T> getEvaluator(String baseClass, String baseMethod, Class param)
/* 34:   */   {
/* 35:   */     try
/* 36:   */     {
/* 37:33 */       Class<?> clazz = Class.forName(baseClass);
/* 38:34 */       Method method = clazz.getDeclaredMethod(baseMethod, new Class[] { param });
/* 39:35 */       if ((!$assertionsDisabled) && (!Modifier.isStatic(method.getModifiers()))) {
/* 40:35 */         throw new AssertionError();
/* 41:   */       }
/* 42:   */     }
/* 43:   */     catch (Exception e)
/* 44:   */     {
/* 45:37 */       return null;
/* 46:   */     }
/* 47:40 */     String classname = "XU_caller_" + baseClass.replace('.', '_') + "_" + baseMethod + "_" + param.getSimpleName();
/* 48:41 */     String superName = Type.getInternalName(Object.class);
/* 49:   */     
/* 50:43 */     ClassWriter cw = new ClassWriter(0);
/* 51:   */     
/* 52:45 */     cw.visit(50, 33, classname, null, superName, new String[] { Type.getInternalName(IObjectEvaluate.class) });
/* 53:46 */     cw.visitSource(".dynamic", null);
/* 54:   */     
/* 55:48 */     MethodVisitor constructor = cw.visitMethod(1, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), null, null);
/* 56:49 */     constructor.visitCode();
/* 57:50 */     constructor.visitVarInsn(25, 0);
/* 58:51 */     constructor.visitMethodInsn(183, superName, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), false);
/* 59:52 */     constructor.visitInsn(177);
/* 60:53 */     constructor.visitMaxs(1, 1);
/* 61:54 */     constructor.visitEnd();
/* 62:   */     
/* 63:56 */     MethodVisitor getData = cw.visitMethod(1, "evaluate", Type.getMethodDescriptor(Type.BOOLEAN_TYPE, new Type[] { Type.getType(Object.class) }), null, null);
/* 64:57 */     getData.visitCode();
/* 65:58 */     if (param != null)
/* 66:   */     {
/* 67:59 */       getData.visitVarInsn(25, 1);
/* 68:60 */       if (param != Object.class) {
/* 69:61 */         getData.visitTypeInsn(192, Type.getInternalName(param));
/* 70:   */       }
/* 71:63 */       getData.visitMethodInsn(184, baseClass.replace('.', '/'), baseMethod, Type.getMethodDescriptor(Type.BOOLEAN_TYPE, new Type[] { Type.getType(param) }), false);
/* 72:   */     }
/* 73:   */     else
/* 74:   */     {
/* 75:65 */       getData.visitMethodInsn(184, baseClass.replace('.', '/'), baseMethod, Type.getMethodDescriptor(Type.BOOLEAN_TYPE, new Type[0]), false);
/* 76:   */     }
/* 77:67 */     getData.visitInsn(172);
/* 78:68 */     getData.visitMaxs(1, 2);
/* 79:69 */     getData.visitEnd();
/* 80:   */     
/* 81:71 */     cw.visitEnd();
/* 82:   */     
/* 83:73 */     byte[] b = cw.toByteArray();
/* 84:   */     try
/* 85:   */     {
/* 86:76 */       Class<? extends IObjectEvaluate> clazz = (Class)m_defineClass.invoke(cl, new Object[] { classname, b, Integer.valueOf(0), Integer.valueOf(b.length) });
/* 87:77 */       return (IObjectEvaluate)clazz.newInstance();
/* 88:   */     }
/* 89:   */     catch (Throwable e)
/* 90:   */     {
/* 91:79 */       throw Throwables.propagate(e);
/* 92:   */     }
/* 93:   */   }
/* 94:   */   
/* 95:87 */   public static IObjectEvaluate nullValuate = new IObjectEvaluate()
/* 96:   */   {
/* 97:   */     public boolean evaluate(Object object)
/* 98:   */     {
/* 99:90 */       return false;
/* :0:   */     }
/* :1:   */   };
/* :2:   */   
/* :3:   */   public static abstract interface IObjectEvaluate<T>
/* :4:   */   {
/* :5:   */     public abstract boolean evaluate(T paramT);
/* :6:   */   }
/* :7:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.asm.RemoteCallFactory
 * JD-Core Version:    0.7.0.1
 */