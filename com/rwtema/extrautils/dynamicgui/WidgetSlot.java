/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.inventory.IInventory;
/*  9:   */ import net.minecraft.inventory.ISidedInventory;
/* 10:   */ import net.minecraft.inventory.Slot;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.nbt.NBTTagCompound;
/* 13:   */ 
/* 14:   */ public class WidgetSlot
/* 15:   */   extends Slot
/* 16:   */   implements IWidget
/* 17:   */ {
/* 18:   */   public boolean playerSlot;
/* 19:   */   boolean isISided;
/* 20:   */   int side;
/* 21:   */   
/* 22:   */   public WidgetSlot(IInventory inv, int slot, int x, int y)
/* 23:   */   {
/* 24:21 */     super(inv, slot, x + 1, y + 1);
/* 25:22 */     this.isISided = (inv instanceof ISidedInventory);
/* 26:23 */     this.side = 0;
/* 27:25 */     if (this.isISided) {
/* 28:26 */       for (this.side = 0; this.side < 6; this.side += 1)
/* 29:   */       {
/* 30:27 */         int[] slots = ((ISidedInventory)inv).getAccessibleSlotsFromSide(this.side);
/* 31:29 */         for (int s : slots) {
/* 32:30 */           if (s == slot) {
/* 33:31 */             return;
/* 34:   */           }
/* 35:   */         }
/* 36:   */       }
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean isItemValid(ItemStack par1ItemStack)
/* 41:   */   {
/* 42:40 */     return this.inventory.isItemValidForSlot(this.slotNumber, par1ItemStack);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/* 46:   */   {
/* 47:45 */     return true;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int getX()
/* 51:   */   {
/* 52:50 */     return this.xDisplayPosition - 1;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public int getY()
/* 56:   */   {
/* 57:55 */     return this.yDisplayPosition - 1;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int getW()
/* 61:   */   {
/* 62:60 */     return 18;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public int getH()
/* 66:   */   {
/* 67:65 */     return 18;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void addToContainer(DynamicContainer container)
/* 71:   */   {
/* 72:70 */     container.addSlot(this);
/* 73:   */   }
/* 74:   */   
/* 75:   */   @SideOnly(Side.CLIENT)
/* 76:   */   public void renderBackground(TextureManager manager, DynamicGui gui, int x, int y)
/* 77:   */   {
/* 78:76 */     gui.drawTexturedModalRect(x + getX(), y + getY(), 0, 0, 18, 18);
/* 79:   */   }
/* 80:   */   
/* 81:   */   public void handleDescriptionPacket(NBTTagCompound packet) {}
/* 82:   */   
/* 83:   */   public void renderForeground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop) {}
/* 84:   */   
/* 85:   */   public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/* 86:   */   {
/* 87:89 */     return null;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public List<String> getToolTip()
/* 91:   */   {
/* 92:95 */     return null;
/* 93:   */   }
/* 94:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetSlot
 * JD-Core Version:    0.7.0.1
 */