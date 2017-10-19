/*   1:    */ package com.rwtema.extrautils.network;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.dynamicgui.DynamicGui;
/*   5:    */ import com.rwtema.extrautils.dynamicgui.IDynamicContainerProvider;
/*   6:    */ import com.rwtema.extrautils.gui.ContainerFilingCabinet;
/*   7:    */ import com.rwtema.extrautils.gui.ContainerFilter;
/*   8:    */ import com.rwtema.extrautils.gui.ContainerFilterPipe;
/*   9:    */ import com.rwtema.extrautils.gui.ContainerHoldingBag;
/*  10:    */ import com.rwtema.extrautils.gui.ContainerTransferNode;
/*  11:    */ import com.rwtema.extrautils.gui.ContainerTrashCan;
/*  12:    */ import com.rwtema.extrautils.gui.GuiFilingCabinet;
/*  13:    */ import com.rwtema.extrautils.gui.GuiFilter;
/*  14:    */ import com.rwtema.extrautils.gui.GuiFilterPipe;
/*  15:    */ import com.rwtema.extrautils.gui.GuiHoldingBag;
/*  16:    */ import com.rwtema.extrautils.gui.GuiTradingPost;
/*  17:    */ import com.rwtema.extrautils.gui.GuiTransferNode;
/*  18:    */ import com.rwtema.extrautils.gui.GuiTrashCan;
/*  19:    */ import com.rwtema.extrautils.tileentity.TileEntityFilingCabinet;
/*  20:    */ import com.rwtema.extrautils.tileentity.TileEntityTradingPost;
/*  21:    */ import com.rwtema.extrautils.tileentity.TileEntityTrashCan;
/*  22:    */ import com.rwtema.extrautils.tileentity.enderconstructor.DynamicContainerEnderConstructor;
/*  23:    */ import com.rwtema.extrautils.tileentity.enderconstructor.DynamicGuiEnderConstructor;
/*  24:    */ import com.rwtema.extrautils.tileentity.enderconstructor.TileEnderConstructor;
/*  25:    */ import com.rwtema.extrautils.tileentity.generators.DynamicContainerGenerator;
/*  26:    */ import com.rwtema.extrautils.tileentity.generators.TileEntityGenerator;
/*  27:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode;
/*  28:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IFilterPipe;
/*  29:    */ import cpw.mods.fml.common.network.IGuiHandler;
/*  30:    */ import cpw.mods.fml.relauncher.Side;
/*  31:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  32:    */ import net.minecraft.entity.player.EntityPlayer;
/*  33:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  34:    */ import net.minecraft.item.ItemStack;
/*  35:    */ import net.minecraft.tileentity.TileEntity;
/*  36:    */ import net.minecraft.world.World;
/*  37:    */ 
/*  38:    */ public class GuiHandler
/*  39:    */   implements IGuiHandler
/*  40:    */ {
/*  41:    */   public static final int TILE_ENTITY = 0;
/*  42:    */   public static final int PLAYER_INVENTORY = 1;
/*  43:    */   
/*  44:    */   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
/*  45:    */   {
/*  46: 43 */     if (ID == 0)
/*  47:    */     {
/*  48: 44 */       TileEntity tile = world.getTileEntity(x, y, z);
/*  49: 46 */       if ((tile instanceof INode)) {
/*  50: 47 */         return new ContainerTransferNode(player.inventory, ((INode)tile).getNode());
/*  51:    */       }
/*  52: 48 */       if ((tile instanceof TileEntityTrashCan)) {
/*  53: 49 */         return new ContainerTrashCan(player.inventory, (TileEntityTrashCan)tile);
/*  54:    */       }
/*  55: 50 */       if ((tile instanceof IFilterPipe)) {
/*  56: 51 */         return new ContainerFilterPipe(player.inventory, ((IFilterPipe)tile).getFilterInventory(world, x, y, z));
/*  57:    */       }
/*  58: 52 */       if ((tile instanceof TileEntityFilingCabinet)) {
/*  59: 53 */         return new ContainerFilingCabinet(player.inventory, (TileEntityFilingCabinet)tile, false);
/*  60:    */       }
/*  61: 54 */       if ((tile instanceof TileEntityGenerator)) {
/*  62: 55 */         return new DynamicContainerGenerator(player.inventory, (TileEntityGenerator)tile);
/*  63:    */       }
/*  64: 56 */       if ((tile instanceof TileEnderConstructor)) {
/*  65: 57 */         return new DynamicContainerEnderConstructor(player.inventory, (TileEnderConstructor)tile);
/*  66:    */       }
/*  67: 58 */       if ((tile instanceof IDynamicContainerProvider)) {
/*  68: 59 */         return ((IDynamicContainerProvider)tile).getContainer(player);
/*  69:    */       }
/*  70:    */     }
/*  71: 61 */     else if (ID == 1)
/*  72:    */     {
/*  73: 62 */       if (x >= player.inventory.mainInventory.length) {
/*  74: 63 */         return null;
/*  75:    */       }
/*  76: 66 */       ItemStack item = player.inventory.getStackInSlot(x);
/*  77: 68 */       if (item != null)
/*  78:    */       {
/*  79: 69 */         if ((ExtraUtils.nodeUpgrade != null) && (item.getItem() == ExtraUtils.nodeUpgrade) && (item.getItemDamage() == 1)) {
/*  80: 70 */           return new ContainerFilter(player, x);
/*  81:    */         }
/*  82: 71 */         if ((ExtraUtils.goldenBag != null) && (item.getItem() == ExtraUtils.goldenBag)) {
/*  83: 72 */           return new ContainerHoldingBag(player, x);
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87: 77 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @SideOnly(Side.CLIENT)
/*  91:    */   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
/*  92:    */   {
/*  93: 83 */     TileEntity tile = world.getTileEntity(x, y, z);
/*  94: 85 */     if (ID == 0)
/*  95:    */     {
/*  96: 86 */       if ((tile instanceof TileEntityTradingPost))
/*  97:    */       {
/*  98: 87 */         TileEntityTradingPost trade = (TileEntityTradingPost)tile;
/*  99: 88 */         return new GuiTradingPost(player, trade.ids, trade.data, trade);
/* 100:    */       }
/* 101: 89 */       if ((tile instanceof INode)) {
/* 102: 90 */         return new GuiTransferNode(player.inventory, ((INode)tile).getNode());
/* 103:    */       }
/* 104: 91 */       if ((tile instanceof TileEntityTrashCan)) {
/* 105: 92 */         return new GuiTrashCan(player.inventory, (TileEntityTrashCan)tile);
/* 106:    */       }
/* 107: 93 */       if ((tile instanceof IFilterPipe)) {
/* 108: 94 */         return new GuiFilterPipe(player.inventory, ((IFilterPipe)tile).getFilterInventory(world, x, y, z));
/* 109:    */       }
/* 110: 95 */       if ((tile instanceof TileEntityFilingCabinet)) {
/* 111: 96 */         return new GuiFilingCabinet(player.inventory, (TileEntityFilingCabinet)tile);
/* 112:    */       }
/* 113: 97 */       if ((tile instanceof TileEntityGenerator)) {
/* 114: 98 */         return new DynamicGui(new DynamicContainerGenerator(player.inventory, (TileEntityGenerator)tile));
/* 115:    */       }
/* 116: 99 */       if ((tile instanceof TileEnderConstructor)) {
/* 117:100 */         return new DynamicGuiEnderConstructor(new DynamicContainerEnderConstructor(player.inventory, (TileEnderConstructor)tile));
/* 118:    */       }
/* 119:101 */       if ((tile instanceof IDynamicContainerProvider)) {
/* 120:102 */         return new DynamicGui(((IDynamicContainerProvider)tile).getContainer(player));
/* 121:    */       }
/* 122:    */     }
/* 123:104 */     else if (ID == 1)
/* 124:    */     {
/* 125:105 */       if (x >= player.inventory.mainInventory.length) {
/* 126:106 */         return null;
/* 127:    */       }
/* 128:109 */       ItemStack item = player.inventory.getStackInSlot(x);
/* 129:111 */       if (item != null)
/* 130:    */       {
/* 131:112 */         if ((ExtraUtils.nodeUpgrade != null) && (item.getItem() == ExtraUtils.nodeUpgrade) && (item.getItemDamage() == 1)) {
/* 132:113 */           return new GuiFilter(player, x);
/* 133:    */         }
/* 134:116 */         if ((ExtraUtils.goldenBag != null) && (item.getItem() == ExtraUtils.goldenBag)) {
/* 135:117 */           return new GuiHoldingBag(player, x);
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:122 */     return null;
/* 140:    */   }
/* 141:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.GuiHandler
 * JD-Core Version:    0.7.0.1
 */