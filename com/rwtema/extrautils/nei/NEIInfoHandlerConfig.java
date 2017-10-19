/*  1:   */ package com.rwtema.extrautils.nei;
/*  2:   */ 
/*  3:   */ import codechicken.nei.api.API;
/*  4:   */ import codechicken.nei.api.IConfigureNEI;
/*  5:   */ import com.rwtema.extrautils.ExtraUtils;
/*  6:   */ import com.rwtema.extrautils.LogHelper;
/*  7:   */ import com.rwtema.extrautils.block.BlockColor;
/*  8:   */ import com.rwtema.extrautils.block.BlockDecoration;
/*  9:   */ import com.rwtema.extrautils.item.filters.AdvancedNodeUpgrades;
/* 10:   */ import com.rwtema.extrautils.item.filters.Matcher;
/* 11:   */ import com.rwtema.extrautils.item.filters.Matcher.Type;
/* 12:   */ import com.rwtema.extrautils.nei.ping.NEIPing;
/* 13:   */ import cpw.mods.fml.common.Loader;
/* 14:   */ import cpw.mods.fml.relauncher.Side;
/* 15:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 16:   */ import net.minecraft.client.gui.inventory.GuiCrafting;
/* 17:   */ import net.minecraft.client.gui.inventory.GuiInventory;
/* 18:   */ import net.minecraft.item.Item;
/* 19:   */ import net.minecraft.item.ItemStack;
/* 20:   */ 
/* 21:   */ @SideOnly(Side.CLIENT)
/* 22:   */ public class NEIInfoHandlerConfig
/* 23:   */   implements IConfigureNEI
/* 24:   */ {
/* 25:   */   public void loadConfig()
/* 26:   */   {
/* 27:   */     
/* 28:27 */     if (ExtraUtils.drum != null) {
/* 29:28 */       API.addSubset("Extra Common.Drums", new SubsetItemsNBT(Item.getItemFromBlock(ExtraUtils.drum)));
/* 30:   */     }
/* 31:30 */     if (ExtraUtils.microBlocks != null) {
/* 32:31 */       API.addSubset("Extra Common.Extra Microblocks", new SubsetItemsNBT(ExtraUtils.microBlocks));
/* 33:   */     }
/* 34:33 */     if (ExtraUtils.colorBlockDataEnabled) {
/* 35:34 */       API.addSubset("Extra Common.Colored Blocks", new SubsetBlockClass(BlockColor.class));
/* 36:   */     }
/* 37:36 */     if (ExtraUtils.greenScreen != null) {
/* 38:37 */       API.addSubset("Extra Common.Lapis Caelestis", new SubsetItems(new Item[] { Item.getItemFromBlock(ExtraUtils.greenScreen) }));
/* 39:   */     }
/* 40:39 */     if (ExtraUtils.cobblestoneCompr != null) {
/* 41:40 */       API.addSubset("Extra Common.Compressed Blocks", new SubsetItems(new Item[] { Item.getItemFromBlock(ExtraUtils.cobblestoneCompr) }));
/* 42:   */     }
/* 43:42 */     if ((ExtraUtils.decorative1Enabled) || (ExtraUtils.decorative2Enabled)) {
/* 44:43 */       API.addSubset("Extra Common.Decorative Blocks", new SubsetBlockClass(BlockDecoration.class));
/* 45:   */     }
/* 46:45 */     if (ExtraUtils.generator2 != null)
/* 47:   */     {
/* 48:46 */       SubsetItems s = new SubsetItems(new Item[] { Item.getItemFromBlock(ExtraUtils.generator2) });
/* 49:47 */       if (ExtraUtils.generator3 != null) {
/* 50:48 */         s.addItem(Item.getItemFromBlock(ExtraUtils.generator3));
/* 51:   */       }
/* 52:50 */       API.addSubset("Extra Common.Higher Tier Generators", s);
/* 53:   */     }
/* 54:54 */     for (Matcher matcher : AdvancedNodeUpgrades.entryList) {
/* 55:55 */       if ((matcher != AdvancedNodeUpgrades.nullMatcher) && (matcher.type == Matcher.Type.ITEM) && (matcher.shouldAddToNEI()) && (matcher.isSelectable()))
/* 56:   */       {
/* 57:56 */         String localizedName = matcher.getLocalizedName();
/* 58:57 */         localizedName = localizedName.replace(".exe", "");
/* 59:58 */         localizedName = localizedName.replaceAll("\\.", "");
/* 60:59 */         API.addSubset("Extra Filtering." + localizedName, new ItemFilterWrapper(matcher));
/* 61:   */       }
/* 62:   */     }
/* 63:64 */     API.registerRecipeHandler(new EnderConstructorHandler());
/* 64:65 */     API.registerUsageHandler(new EnderConstructorHandler());
/* 65:   */     
/* 66:67 */     API.registerRecipeHandler(new InfoHandler());
/* 67:68 */     API.registerUsageHandler(new InfoHandler());
/* 68:   */     
/* 69:70 */     API.registerRecipeHandler(new SoulHandler());
/* 70:71 */     API.registerUsageHandler(new SoulHandler());
/* 71:74 */     if (Loader.isModLoaded("ForgeMultipart"))
/* 72:   */     {
/* 73:75 */       API.registerRecipeHandler(new FMPMicroBlocksHandler());
/* 74:76 */       API.registerUsageHandler(new FMPMicroBlocksHandler());
/* 75:77 */       API.registerRecipeHandler(new MicroBlocksHandler());
/* 76:78 */       API.registerUsageHandler(new MicroBlocksHandler());
/* 77:   */       
/* 78:80 */       API.registerGuiOverlayHandler(GuiCrafting.class, new FMPMicroBlocksOverlayHandler(), "microblocks");
/* 79:81 */       API.registerGuiOverlayHandler(GuiInventory.class, new FMPMicroBlocksOverlayHandler(63, 20), "microblocks2x2");
/* 80:   */     }
/* 81:84 */     if (ExtraUtils.colorBlockData != null) {
/* 82:85 */       API.hideItem(new ItemStack(ExtraUtils.colorBlockData));
/* 83:   */     }
/* 84:87 */     LogHelper.info("Added NEI integration", new Object[0]);
/* 85:   */   }
/* 86:   */   
/* 87:   */   public String getName()
/* 88:   */   {
/* 89:92 */     return "Extra Utilities: Nei Integration";
/* 90:   */   }
/* 91:   */   
/* 92:   */   public String getVersion()
/* 93:   */   {
/* 94:97 */     return "1";
/* 95:   */   }
/* 96:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.NEIInfoHandlerConfig
 * JD-Core Version:    0.7.0.1
 */