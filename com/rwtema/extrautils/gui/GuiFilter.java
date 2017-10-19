/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  12:    */ import net.minecraft.inventory.Container;
/*  13:    */ import net.minecraft.inventory.Slot;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.util.ResourceLocation;
/*  17:    */ import net.minecraft.util.StatCollector;
/*  18:    */ import net.minecraftforge.fluids.Fluid;
/*  19:    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*  20:    */ import net.minecraftforge.fluids.FluidStack;
/*  21:    */ import org.lwjgl.opengl.GL11;
/*  22:    */ 
/*  23:    */ public class GuiFilter
/*  24:    */   extends GuiContainer
/*  25:    */ {
/*  26: 18 */   private static final ResourceLocation texture = new ResourceLocation("extrautils", "textures/guiFilter.png");
/*  27:    */   private EntityPlayer player;
/*  28: 20 */   private int currentFilter = -1;
/*  29:    */   
/*  30:    */   public GuiFilter(EntityPlayer player, int currentFilter)
/*  31:    */   {
/*  32: 24 */     super(new ContainerFilter(player, currentFilter));
/*  33: 25 */     this.currentFilter = currentFilter;
/*  34: 26 */     this.player = player;
/*  35: 27 */     this.field_146999_f = 176;
/*  36: 28 */     this.field_147000_g = 131;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<String> handleItemTooltip(ItemStack stack, int mousex, int mousey, List<String> tooltip)
/*  40:    */   {
/*  41: 32 */     List<String> overide = getOveride(stack, mousex, mousey);
/*  42: 34 */     if (overide != null) {
/*  43: 35 */       return overide;
/*  44:    */     }
/*  45: 37 */     return tooltip;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<String> getOveride(ItemStack par1ItemStack, int par2, int par3)
/*  49:    */   {
/*  50: 42 */     for (int j1 = 0; j1 < this.field_147002_h.inventorySlots.size(); j1++)
/*  51:    */     {
/*  52: 43 */       Slot slot = (Slot)this.field_147002_h.inventorySlots.get(j1);
/*  53: 45 */       if (((slot instanceof SlotGhostItemContainer)) && (slot.getHasStack()))
/*  54:    */       {
/*  55: 46 */         if ((func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, par2, par3)) && (slot.func_111238_b()))
/*  56:    */         {
/*  57: 47 */           ItemStack filter = this.player.inventory.getStackInSlot(this.currentFilter);
/*  58: 49 */           if ((filter != null) && (filter.hasTagCompound()) && (filter.getTagCompound().hasKey("isLiquid_" + slot.slotNumber)))
/*  59:    */           {
/*  60: 50 */             FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(slot.getStack());
/*  61: 51 */             List t = new ArrayList();
/*  62: 52 */             t.add(XUHelper.getFluidName(liquid));
/*  63: 53 */             return t;
/*  64:    */           }
/*  65: 56 */           return null;
/*  66:    */         }
/*  67: 59 */         return null;
/*  68:    */       }
/*  69:    */     }
/*  70: 63 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected void drawSlotInventory(Slot slot)
/*  74:    */   {
/*  75: 78 */     int i = slot.slotNumber;
/*  76: 80 */     if (((slot instanceof SlotGhostItemContainer)) && (slot.getHasStack()))
/*  77:    */     {
/*  78: 81 */       ItemStack filter = this.player.inventory.getStackInSlot(this.currentFilter);
/*  79: 83 */       if ((filter != null) && (filter.hasTagCompound()) && (filter.getTagCompound().hasKey("isLiquid_" + i)))
/*  80:    */       {
/*  81: 84 */         FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(slot.getStack());
/*  82: 86 */         if ((liquid != null) && (liquid.getFluid().getIcon() != null))
/*  83:    */         {
/*  84: 87 */           GL11.glDisable(2896);
/*  85:    */           
/*  86: 89 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  87: 90 */           drawTexturedModelRectFromIcon(slot.xDisplayPosition, slot.yDisplayPosition, liquid.getFluid().getIcon(liquid), 16, 16);
/*  88: 91 */           GL11.glEnable(2896);
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void func_146979_b(int par1, int par2)
/*  95:    */   {
/*  96:100 */     if (this.player.inventory.getStackInSlot(this.currentFilter) != null) {
/*  97:101 */       this.fontRendererObj.drawString(this.player.inventory.getStackInSlot(this.currentFilter).getDisplayName(), 8, 6, 4210752);
/*  98:    */     }
/*  99:104 */     this.fontRendererObj.drawString(this.player.inventory.isInventoryNameLocalized() ? this.player.inventory.getInventoryName() : StatCollector.translateToLocal(this.player.inventory.getInventoryName()), 8, this.field_147000_g - 96 + 2, 4210752);
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected void func_146976_a(float f, int i, int j)
/* 103:    */   {
/* 104:112 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 105:113 */     this.mc.renderEngine.bindTexture(texture);
/* 106:114 */     int k = (this.width - this.field_146999_f) / 2;
/* 107:115 */     int l = (this.height - this.field_147000_g) / 2;
/* 108:116 */     drawTexturedModalRect(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/* 109:    */   }
/* 110:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.GuiFilter
 * JD-Core Version:    0.7.0.1
 */