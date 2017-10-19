/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.common.Loader;
/*   4:    */ import cpw.mods.fml.common.ModContainer;
/*   5:    */ import cpw.mods.fml.common.registry.GameRegistry;
/*   6:    */ import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.inventory.IInventory;
/*  12:    */ import net.minecraft.inventory.ISidedInventory;
/*  13:    */ import net.minecraft.item.Item;
/*  14:    */ import net.minecraft.item.ItemBlock;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraftforge.oredict.OreDictionary;
/*  17:    */ 
/*  18:    */ public class InvHelper
/*  19:    */ {
/*  20:    */   public static int[] getSlots(IInventory inv, int side)
/*  21:    */   {
/*  22: 21 */     if ((inv instanceof ISidedInventory)) {
/*  23: 22 */       return ((ISidedInventory)inv).getAccessibleSlotsFromSide(side);
/*  24:    */     }
/*  25: 23 */     if (inv != null)
/*  26:    */     {
/*  27: 24 */       int size = inv.getSizeInventory();
/*  28: 25 */       int[] arr = new int[size];
/*  29: 27 */       for (int i = 0; i < size; i++) {
/*  30: 28 */         arr[i] = i;
/*  31:    */       }
/*  32: 31 */       return arr;
/*  33:    */     }
/*  34: 34 */     return new int[0];
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static boolean canStack(ItemStack a, ItemStack b)
/*  38:    */   {
/*  39: 38 */     if ((a == null) || (b == null)) {
/*  40: 39 */       return false;
/*  41:    */     }
/*  42: 42 */     return (a.getItem() == b.getItem()) && (a.getItemDamage() == b.getItemDamage()) && (ItemStack.areItemStackTagsEqual(a, b));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static boolean sameType(ItemStack a, ItemStack b)
/*  46:    */   {
/*  47: 47 */     if ((a == null) || (b == null)) {
/*  48: 48 */       return false;
/*  49:    */     }
/*  50: 51 */     if (canStack(a, b)) {
/*  51: 52 */       return true;
/*  52:    */     }
/*  53: 55 */     int[] t = OreDictionary.getOreIDs(a);
/*  54:    */     
/*  55: 57 */     return (t.length > 0) && (Arrays.equals(t, OreDictionary.getOreIDs(b)));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static boolean sameMod(ItemStack a, ItemStack b)
/*  59:    */   {
/*  60: 61 */     return (a != null) && (b != null) && ((canStack(a, b)) || (a.getItem() == b.getItem()) || (getModID(a).equals(getModID(b))));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String getModID(ItemStack item)
/*  64:    */   {
/*  65: 66 */     ModContainer ID = getModForItemStack(item);
/*  66: 68 */     if ((ID == null) || (ID.getModId() == null)) {
/*  67: 69 */       return "Unknown";
/*  68:    */     }
/*  69: 72 */     return ID.getModId();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static ModContainer getModForItemStack(ItemStack stack)
/*  73:    */   {
/*  74: 76 */     Item item = stack.getItem();
/*  75: 77 */     Class klazz = null;
/*  76: 79 */     if (item == null) {
/*  77: 80 */       return null;
/*  78:    */     }
/*  79: 83 */     GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(item);
/*  80: 84 */     klazz = item.getClass();
/*  81: 86 */     if ((identifier == null) && 
/*  82: 87 */       ((item instanceof ItemBlock)))
/*  83:    */     {
/*  84: 89 */       Block block = ((ItemBlock)item).field_150939_a;
/*  85:    */       
/*  86: 91 */       identifier = GameRegistry.findUniqueIdentifierFor(block);
/*  87: 92 */       klazz = block.getClass();
/*  88:    */     }
/*  89: 97 */     Map<String, ModContainer> modList = Loader.instance().getIndexedModList();
/*  90: 99 */     if (identifier != null)
/*  91:    */     {
/*  92:100 */       ModContainer container = (ModContainer)modList.get(identifier.modId);
/*  93:102 */       if (container != null) {
/*  94:103 */         return container;
/*  95:    */       }
/*  96:    */     }
/*  97:107 */     String[] itemClassParts = klazz.getName().split("\\.");
/*  98:108 */     ModContainer closestMatch = null;
/*  99:109 */     int mostMatchingPackages = 0;
/* 100:111 */     for (Map.Entry<String, ModContainer> entry : modList.entrySet())
/* 101:    */     {
/* 102:112 */       Object mod = ((ModContainer)entry.getValue()).getMod();
/* 103:114 */       if (mod != null)
/* 104:    */       {
/* 105:118 */         String[] modClassParts = mod.getClass().getName().split("\\.");
/* 106:119 */         int packageMatches = 0;
/* 107:121 */         for (int i = 0; i < modClassParts.length; i++)
/* 108:    */         {
/* 109:122 */           if ((i >= itemClassParts.length) || (itemClassParts[i] == null) || (!itemClassParts[i].equals(modClassParts[i]))) {
/* 110:    */             break;
/* 111:    */           }
/* 112:123 */           packageMatches++;
/* 113:    */         }
/* 114:129 */         if (packageMatches > mostMatchingPackages)
/* 115:    */         {
/* 116:130 */           mostMatchingPackages = packageMatches;
/* 117:131 */           closestMatch = (ModContainer)entry.getValue();
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:135 */     return closestMatch;
/* 122:    */   }
/* 123:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.InvHelper
 * JD-Core Version:    0.7.0.1
 */