/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeInventory;
/*   5:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeLiquid;
/*   6:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
/*   7:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeEnergy;
/*   8:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeInventory;
/*   9:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeLiquid;
/*  10:    */ import cpw.mods.fml.relauncher.Side;
/*  11:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  12:    */ import java.util.Locale;
/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.client.gui.FontRenderer;
/*  15:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  16:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  17:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  18:    */ import net.minecraft.inventory.Container;
/*  19:    */ import net.minecraft.inventory.IInventory;
/*  20:    */ import net.minecraft.inventory.Slot;
/*  21:    */ import net.minecraft.util.ResourceLocation;
/*  22:    */ import net.minecraft.util.StatCollector;
/*  23:    */ import net.minecraftforge.fluids.Fluid;
/*  24:    */ import net.minecraftforge.fluids.FluidStack;
/*  25:    */ import org.lwjgl.opengl.GL11;
/*  26:    */ 
/*  27:    */ @SideOnly(Side.CLIENT)
/*  28:    */ public class GuiTransferNode
/*  29:    */   extends GuiContainer
/*  30:    */ {
/*  31: 19 */   private static final ResourceLocation texture = new ResourceLocation("extrautils", "textures/guiTransferNode.png");
/*  32:    */   TileEntityTransferNode node;
/*  33:    */   IInventory player;
/*  34:    */   FluidStack liquid;
/*  35:    */   
/*  36:    */   public GuiTransferNode(IInventory player, TileEntityTransferNode node)
/*  37:    */   {
/*  38: 25 */     super(new ContainerTransferNode(player, node));
/*  39: 26 */     this.node = node;
/*  40: 27 */     this.player = player;
/*  41: 28 */     this.field_146999_f = 176;
/*  42: 29 */     this.field_147000_g = 222;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void func_146979_b(int par1, int par2)
/*  46:    */   {
/*  47: 38 */     if ((this.node instanceof TileEntityTransferNodeInventory))
/*  48:    */     {
/*  49: 39 */       this.fontRendererObj.drawString(((IInventory)this.node).isInventoryNameLocalized() ? ((IInventory)this.node).getInventoryName() : StatCollector.translateToLocal(((IInventory)this.node).getInventoryName()), 8, 6, 4210752);
/*  50: 42 */       if ((this.field_147002_h.getSlot(0).getHasStack()) || ((this.node instanceof TileEntityRetrievalNodeInventory)))
/*  51:    */       {
/*  52: 43 */         String msg = StatCollector.translateToLocal("gui.transferNode.searching");
/*  53: 44 */         this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 26, 4210752);
/*  54: 45 */         msg = "x: " + formatRelCoord(this.node.pipe_x) + " y: " + formatRelCoord(this.node.pipe_y) + " z: " + formatRelCoord(this.node.pipe_z);
/*  55: 46 */         this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 36, 4210752);
/*  56:    */       }
/*  57:    */     }
/*  58: 48 */     else if ((this.node instanceof TileEntityTransferNodeEnergy))
/*  59:    */     {
/*  60: 49 */       String msg = "Holding: " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(((ContainerTransferNode)this.field_147002_h).lastenergy) }) + " RF";
/*  61: 50 */       this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 26, 4210752);
/*  62: 51 */       msg = "Powering: " + ((ContainerTransferNode)this.field_147002_h).lastenergycount + " Connection";
/*  63: 53 */       if (((ContainerTransferNode)this.field_147002_h).lastenergycount != 1) {
/*  64: 54 */         msg = msg + "s";
/*  65:    */       }
/*  66: 57 */       this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 36, 4210752);
/*  67: 58 */       msg = StatCollector.translateToLocal("gui.transferNode.searching");
/*  68: 59 */       this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 46, 4210752);
/*  69: 60 */       msg = "x: " + formatRelCoord(this.node.pipe_x) + " y: " + formatRelCoord(this.node.pipe_y) + " z: " + formatRelCoord(this.node.pipe_z);
/*  70: 61 */       this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 56, 4210752);
/*  71:    */     }
/*  72: 62 */     else if ((this.node instanceof TileEntityTransferNodeLiquid))
/*  73:    */     {
/*  74: 63 */       if (((ContainerTransferNode)this.field_147002_h).liquid_amount > 0)
/*  75:    */       {
/*  76: 64 */         FluidStack liquid = new FluidStack(((ContainerTransferNode)this.field_147002_h).liquid_type, ((ContainerTransferNode)this.field_147002_h).liquid_amount);
/*  77: 65 */         String msg = XUHelper.getFluidName(liquid);
/*  78: 66 */         msg = "Holding: " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(liquid.amount) }) + "mB of " + msg;
/*  79: 67 */         this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 26, 4210752);
/*  80: 68 */         msg = "x: " + formatRelCoord(this.node.pipe_x) + " y: " + formatRelCoord(this.node.pipe_y) + " z: " + formatRelCoord(this.node.pipe_z);
/*  81: 69 */         this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 36, 4210752);
/*  82: 71 */         if (liquid.getFluid().getIcon() != null)
/*  83:    */         {
/*  84: 72 */           this.mc.renderEngine.bindTexture(liquid.getFluid().getSpriteNumber() == 0 ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
/*  85: 73 */           int col = liquid.getFluid().getColor(liquid);
/*  86: 74 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  87: 75 */           drawTexturedModelRectFromIcon(80, 83, liquid.getFluid().getIcon(liquid), 16, 16);
/*  88:    */         }
/*  89:    */       }
/*  90: 77 */       else if ((this.node instanceof TileEntityRetrievalNodeLiquid))
/*  91:    */       {
/*  92: 78 */         String msg = StatCollector.translateToLocal("gui.transferNode.searching");
/*  93: 79 */         this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 26, 4210752);
/*  94: 80 */         msg = "x: " + formatRelCoord(this.node.pipe_x) + " y: " + formatRelCoord(this.node.pipe_y) + " z: " + formatRelCoord(this.node.pipe_z);
/*  95: 81 */         this.fontRendererObj.drawString(msg, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(msg) / 2, 36, 4210752);
/*  96:    */       }
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private String formatRelCoord(int no)
/* 101:    */   {
/* 102: 87 */     if (no > 0) {
/* 103: 88 */       return "+" + no;
/* 104:    */     }
/* 105: 89 */     if (no == 0) {
/* 106: 90 */       return " " + no;
/* 107:    */     }
/* 108: 92 */     return "" + no;
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void func_146976_a(float f, int i, int j)
/* 112:    */   {
/* 113: 98 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 114: 99 */     this.mc.renderEngine.bindTexture(texture);
/* 115:100 */     int k = (this.width - this.field_146999_f) / 2;
/* 116:101 */     int l = (this.height - this.field_147000_g) / 2;
/* 117:102 */     drawTexturedModalRect(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/* 118:    */   }
/* 119:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.GuiTransferNode
 * JD-Core Version:    0.7.0.1
 */