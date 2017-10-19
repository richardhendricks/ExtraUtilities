/*   1:    */ package com.rwtema.extrautils.tileentity.enderquarry;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.LogHelper;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.BlockFence;
/*  11:    */ import net.minecraft.block.BlockLiquid;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*  14:    */ import net.minecraft.util.RegistryNamespaced;
/*  15:    */ import net.minecraftforge.fluids.IFluidBlock;
/*  16:    */ import org.objectweb.asm.ClassReader;
/*  17:    */ import org.objectweb.asm.tree.ClassNode;
/*  18:    */ import org.objectweb.asm.tree.MethodNode;
/*  19:    */ 
/*  20:    */ public class BlockBreakingRegistry
/*  21:    */ {
/*  22: 20 */   public static BlockBreakingRegistry instance = new BlockBreakingRegistry();
/*  23: 21 */   public static HashMap<Block, entry> entries = new HashMap();
/*  24: 22 */   public static Set<String> methodNames = null;
/*  25: 23 */   public static Map<String, Boolean> names = new HashMap();
/*  26: 24 */   public static LaunchClassLoader cl = (LaunchClassLoader)BlockBreakingRegistry.class.getClassLoader();
/*  27:    */   
/*  28:    */   public static boolean blackList(Block id)
/*  29:    */   {
/*  30: 27 */     return ((entry)entries.get(id)).blackList;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static boolean isSpecial(Block id)
/*  34:    */   {
/*  35: 31 */     return ((entry)entries.get(id)).isSpecial;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static boolean isFence(Block id)
/*  39:    */   {
/*  40: 35 */     return ((entry)entries.get(id)).isFence;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static boolean isFluid(Block id)
/*  44:    */   {
/*  45: 39 */     return ((entry)entries.get(id)).isFluid;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setupBreaking()
/*  49:    */   {
/*  50: 43 */     if (methodNames == null)
/*  51:    */     {
/*  52: 44 */       methodNames = new HashSet();
/*  53: 46 */       for (Method m : BlockDummy.class.getDeclaredMethods()) {
/*  54: 47 */         methodNames.add(m.getName());
/*  55:    */       }
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 50 */       return;
/*  60:    */     }
/*  61: 53 */     for (Object aBlockRegistry : Block.blockRegistry) {
/*  62: 54 */       entries.put((Block)aBlockRegistry, new entry());
/*  63:    */     }
/*  64: 57 */     ((entry)entries.get(Blocks.torch)).blackList = true;
/*  65: 59 */     for (Object aBlockRegistry : Block.blockRegistry)
/*  66:    */     {
/*  67: 60 */       Block block = (Block)aBlockRegistry;
/*  68: 61 */       entry e = (entry)entries.get(block);
/*  69: 62 */       String name = block.getClass().getName();
/*  70: 64 */       if (block.getUnlocalizedName() != null) {
/*  71: 65 */         name = block.getUnlocalizedName();
/*  72:    */       }
/*  73:    */       try
/*  74:    */       {
/*  75: 68 */         name = Block.blockRegistry.getNameForObject(block);
/*  76:    */       }
/*  77:    */       catch (Exception err)
/*  78:    */       {
/*  79: 71 */         LogHelper.error("Error getting name for block " + name, new Object[0]);
/*  80: 72 */         err.printStackTrace();
/*  81:    */       }
/*  82: 75 */       e.isFence = false;
/*  83:    */       try
/*  84:    */       {
/*  85: 78 */         e.isFence = (block.getRenderType() == 11);
/*  86:    */       }
/*  87:    */       catch (Exception err)
/*  88:    */       {
/*  89: 80 */         LogHelper.error("Error checking block class code: Exception calling getRenderType() on block " + name, new Object[0]);
/*  90: 81 */         err.printStackTrace();
/*  91:    */       }
/*  92:    */       catch (NoClassDefFoundError err)
/*  93:    */       {
/*  94: 83 */         throw new RuntimeException("Serious error calling getRenderType() on block " + name + " : Likely cause is client-side code is being called server-side", err);
/*  95:    */       }
/*  96:    */       catch (Throwable err)
/*  97:    */       {
/*  98: 85 */         throw new RuntimeException("Serious error calling getRenderType() on block " + name, err);
/*  99:    */       }
/* 100: 88 */       e.isFence = ((e.isFence) || ((block instanceof BlockFence)));
/* 101: 90 */       if (((block instanceof BlockLiquid)) || ((block instanceof IFluidBlock)))
/* 102:    */       {
/* 103: 91 */         e.blackList = true;
/* 104: 92 */         e.isFluid = true;
/* 105:    */       }
/* 106: 95 */       e.isSpecial = hasSpecialBreaking(block.getClass());
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean hasSpecialBreaking(Class clazz)
/* 111:    */   {
/* 112:100 */     if ((clazz == null) || (clazz.equals(Block.class))) {
/* 113:101 */       return false;
/* 114:    */     }
/* 115:104 */     if (names.containsKey(clazz.getName())) {
/* 116:105 */       return ((Boolean)names.get(clazz.getName())).booleanValue();
/* 117:    */     }
/* 118:108 */     LogHelper.fine("Checking class for special block breaking code: " + clazz.getName(), new Object[0]);
/* 119:    */     try
/* 120:    */     {
/* 121:    */       byte[] bytes;
/* 122:    */       byte[] bytes;
/* 123:112 */       if ((clazz.getClassLoader() instanceof LaunchClassLoader)) {
/* 124:113 */         bytes = ((LaunchClassLoader)clazz.getClassLoader()).getClassBytes(clazz.getName());
/* 125:    */       } else {
/* 126:115 */         bytes = cl.getClassBytes(clazz.getName());
/* 127:    */       }
/* 128:118 */       ClassNode classNode = new ClassNode();
/* 129:119 */       ClassReader classReader = new ClassReader(bytes);
/* 130:120 */       classReader.accept(classNode, 0);
/* 131:122 */       for (MethodNode method : classNode.methods) {
/* 132:123 */         if (methodNames.contains(method.name))
/* 133:    */         {
/* 134:124 */           LogHelper.fine("Detected special block breaking code in class: " + clazz.getName(), new Object[0]);
/* 135:125 */           names.put(clazz.getName(), Boolean.valueOf(true));
/* 136:126 */           return true;
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:    */     catch (Throwable e)
/* 141:    */     {
/* 142:    */       try
/* 143:    */       {
/* 144:131 */         for (Method m : clazz.getDeclaredMethods()) {
/* 145:132 */           if (methodNames.contains(m.getName()))
/* 146:    */           {
/* 147:133 */             LogHelper.fine("Detected special block breaking code in class: " + clazz.getName(), new Object[0]);
/* 148:134 */             names.put(clazz.getName(), Boolean.valueOf(true));
/* 149:135 */             return true;
/* 150:    */           }
/* 151:    */         }
/* 152:    */       }
/* 153:    */       catch (Throwable e2)
/* 154:    */       {
/* 155:139 */         LogHelper.error("Error checking block class code: " + clazz.getName(), new Object[0]);
/* 156:140 */         e.printStackTrace();
/* 157:141 */         e2.printStackTrace();
/* 158:142 */         names.put(clazz.getName(), Boolean.valueOf(true));
/* 159:143 */         return true;
/* 160:    */       }
/* 161:    */     }
/* 162:147 */     boolean result = hasSpecialBreaking(clazz.getSuperclass());
/* 163:148 */     names.put(clazz.getName(), Boolean.valueOf(result));
/* 164:149 */     return result;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static class entry
/* 168:    */   {
/* 169:153 */     public boolean isSpecial = false;
/* 170:154 */     public boolean blackList = false;
/* 171:155 */     public boolean isFence = false;
/* 172:156 */     public boolean isFluid = false;
/* 173:    */   }
/* 174:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderquarry.BlockBreakingRegistry
 * JD-Core Version:    0.7.0.1
 */