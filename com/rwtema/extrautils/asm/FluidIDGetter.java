/*  1:   */ package com.rwtema.extrautils.asm;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Throwables;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*  6:   */ import net.minecraftforge.fluids.FluidStack;
/*  7:   */ import org.objectweb.asm.ClassWriter;
/*  8:   */ import org.objectweb.asm.MethodVisitor;
/*  9:   */ import org.objectweb.asm.Type;
/* 10:   */ 
/* 11:   */ public class FluidIDGetter
/* 12:   */ {
/* 13:   */   public static final Class<? extends IFluidLegacy> clazz;
/* 14:   */   public static final IFluidLegacy fluidLegacy;
/* 15:   */   
/* 16:   */   static
/* 17:   */   {
/* 18:21 */     LaunchClassLoader cl = (LaunchClassLoader)FluidIDGetter.class.getClassLoader();
/* 19:   */     Method m_defineClass;
/* 20:   */     try
/* 21:   */     {
/* 22:25 */       m_defineClass = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, [B.class, Integer.TYPE, Integer.TYPE });
/* 23:26 */       m_defineClass.setAccessible(true);
/* 24:   */     }
/* 25:   */     catch (Exception e)
/* 26:   */     {
/* 27:28 */       throw new RuntimeException(e);
/* 28:   */     }
/* 29:31 */     String classname = "XU_fluidstack_compat_code";
/* 30:32 */     String superName = Type.getInternalName(Object.class);
/* 31:   */     
/* 32:34 */     ClassWriter cw = new ClassWriter(0);
/* 33:   */     
/* 34:36 */     cw.visit(50, 33, classname, null, superName, new String[] { Type.getInternalName(IFluidLegacy.class) });
/* 35:37 */     cw.visitSource(".dynamic", null);
/* 36:   */     
/* 37:39 */     MethodVisitor constructor = cw.visitMethod(1, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), null, null);
/* 38:40 */     constructor.visitCode();
/* 39:41 */     constructor.visitVarInsn(25, 0);
/* 40:42 */     constructor.visitMethodInsn(183, superName, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), false);
/* 41:43 */     constructor.visitInsn(177);
/* 42:44 */     constructor.visitMaxs(1, 1);
/* 43:45 */     constructor.visitEnd();
/* 44:   */     
/* 45:47 */     MethodVisitor getData = cw.visitMethod(1, "getID", Type.getMethodDescriptor(Type.INT_TYPE, new Type[] { Type.getType(FluidStack.class) }), null, null);
/* 46:48 */     getData.visitCode();
/* 47:49 */     getData.visitVarInsn(25, 1);
/* 48:   */     try
/* 49:   */     {
/* 50:51 */       FluidStack.class.getDeclaredMethod("getFluidID", new Class[0]);
/* 51:52 */       getData.visitMethodInsn(182, "net/minecraftforge/fluids/FluidStack", "getFluidID", "()I", false);
/* 52:   */     }
/* 53:   */     catch (NoSuchMethodException e)
/* 54:   */     {
/* 55:54 */       getData.visitFieldInsn(180, "net/minecraftforge/fluids/FluidStack", "fluidID", "I");
/* 56:   */     }
/* 57:56 */     getData.visitInsn(172);
/* 58:57 */     getData.visitMaxs(1, 2);
/* 59:58 */     getData.visitEnd();
/* 60:   */     
/* 61:60 */     cw.visitEnd();
/* 62:   */     
/* 63:62 */     byte[] b = cw.toByteArray();
/* 64:   */     try
/* 65:   */     {
/* 66:65 */       clazz = (Class)m_defineClass.invoke(cl, new Object[] { classname, b, Integer.valueOf(0), Integer.valueOf(b.length) });
/* 67:66 */       fluidLegacy = (IFluidLegacy)clazz.newInstance();
/* 68:   */     }
/* 69:   */     catch (Exception e)
/* 70:   */     {
/* 71:68 */       throw Throwables.propagate(e);
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static abstract interface IFluidLegacy
/* 76:   */   {
/* 77:   */     public abstract int getID(FluidStack paramFluidStack);
/* 78:   */   }
/* 79:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.asm.FluidIDGetter
 * JD-Core Version:    0.7.0.1
 */