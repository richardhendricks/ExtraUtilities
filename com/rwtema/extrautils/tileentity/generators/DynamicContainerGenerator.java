/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.dynamicgui.DynamicContainer;
/*  4:   */ import com.rwtema.extrautils.dynamicgui.IWidget;
/*  5:   */ import com.rwtema.extrautils.dynamicgui.WidgetEnergy;
/*  6:   */ import com.rwtema.extrautils.dynamicgui.WidgetSlot;
/*  7:   */ import com.rwtema.extrautils.dynamicgui.WidgetTank;
/*  8:   */ import com.rwtema.extrautils.dynamicgui.WidgetText;
/*  9:   */ import com.rwtema.extrautils.dynamicgui.WidgetTextData;
/* 10:   */ import invtweaks.api.container.InventoryContainer;
/* 11:   */ import java.util.List;
/* 12:   */ import net.minecraft.entity.player.EntityPlayer;
/* 13:   */ import net.minecraft.inventory.IInventory;
/* 14:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 15:   */ import net.minecraftforge.fluids.FluidTankInfo;
/* 16:   */ import net.minecraftforge.fluids.IFluidHandler;
/* 17:   */ 
/* 18:   */ @InventoryContainer
/* 19:   */ public class DynamicContainerGenerator
/* 20:   */   extends DynamicContainer
/* 21:   */ {
/* 22:   */   TileEntityGenerator gen;
/* 23:14 */   public TileEntityGeneratorFurnace genFurnace = null;
/* 24:   */   
/* 25:   */   public DynamicContainerGenerator(IInventory player, TileEntityGenerator gen)
/* 26:   */   {
/* 27:18 */     this.gen = gen;
/* 28:19 */     if ((this.gen instanceof TileEntityGeneratorFurnace)) {
/* 29:20 */       this.genFurnace = ((TileEntityGeneratorFurnace)this.gen);
/* 30:   */     }
/* 31:23 */     this.widgets.add(new WidgetText(5, 5, BlockGenerator.names[gen.getBlockMetadata()] + " Generator", 162));
/* 32:24 */     int x = 5;int y = 19;
/* 33:26 */     if ((gen instanceof IInventory))
/* 34:   */     {
/* 35:27 */       IInventory inv = (IInventory)gen;
/* 36:29 */       for (int i = 0; i < inv.getSizeInventory(); i++)
/* 37:   */       {
/* 38:30 */         IWidget widg = new WidgetSlot(inv, i, x, y);
/* 39:31 */         this.widgets.add(widg);
/* 40:32 */         x += widg.getW() + 5;
/* 41:   */       }
/* 42:   */     }
/* 43:36 */     if ((gen instanceof IFluidHandler))
/* 44:   */     {
/* 45:37 */       FluidTankInfo[] tanks = gen.getTankInfo(null);
/* 46:39 */       for (FluidTankInfo tank : tanks)
/* 47:   */       {
/* 48:40 */         IWidget widg = new WidgetTank(tank, x, y, 2);
/* 49:41 */         this.widgets.add(widg);
/* 50:42 */         x += widg.getW() + 5;
/* 51:   */       }
/* 52:   */     }
/* 53:46 */     IWidget w = new WidgetTextCooldown(gen, x, y, 120);
/* 54:47 */     this.widgets.add(w);
/* 55:48 */     x += w.getW() + 5;
/* 56:49 */     this.widgets.add(new WidgetEnergy(gen, ForgeDirection.UP, x, y));
/* 57:50 */     cropAndAddPlayerSlots(player);
/* 58:51 */     validate();
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean canInteractWith(EntityPlayer entityplayer)
/* 62:   */   {
/* 63:56 */     return true;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public class WidgetTextCooldown
/* 67:   */     extends WidgetTextData
/* 68:   */   {
/* 69:   */     TileEntityGenerator gen;
/* 70:   */     
/* 71:   */     public WidgetTextCooldown(TileEntityGenerator gen, int x, int y, int w)
/* 72:   */     {
/* 73:63 */       super(y, w);
/* 74:64 */       this.gen = gen;
/* 75:   */     }
/* 76:   */     
/* 77:   */     public int getNumParams()
/* 78:   */     {
/* 79:69 */       return 2;
/* 80:   */     }
/* 81:   */     
/* 82:   */     public Object[] getData()
/* 83:   */     {
/* 84:74 */       return new Object[] { Long.valueOf((10.0D * this.gen.coolDown)), Long.valueOf(Math.ceil(10.0D * this.gen.genLevel() * this.gen.getMultiplier())) };
/* 85:   */     }
/* 86:   */     
/* 87:   */     public String getConstructedText()
/* 88:   */     {
/* 89:79 */       if ((this.curData == null) || (this.curData[0] == null)) {
/* 90:80 */         return "";
/* 91:   */       }
/* 92:   */       double t;
/* 93:   */       double t2;
/* 94:   */       try
/* 95:   */       {
/* 96:85 */         t = ((Long)this.curData[0]).longValue() / 200.0D;
/* 97:86 */         t2 = ((Long)this.curData[1]).longValue() / 10.0D;
/* 98:   */       }
/* 99:   */       catch (Exception e)
/* :0:   */       {
/* :1:88 */         return "";
/* :2:   */       }
/* :3:90 */       return this.gen.getBlurb(t, t2);
/* :4:   */     }
/* :5:   */   }
/* :6:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.DynamicContainerGenerator
 * JD-Core Version:    0.7.0.1
 */