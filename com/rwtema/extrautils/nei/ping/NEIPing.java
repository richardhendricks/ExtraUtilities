/*  1:   */ package com.rwtema.extrautils.nei.ping;
/*  2:   */ 
/*  3:   */ import codechicken.nei.LayoutManager;
/*  4:   */ import codechicken.nei.NEIClientConfig;
/*  5:   */ import codechicken.nei.api.API;
/*  6:   */ import codechicken.nei.guihook.GuiContainerManager;
/*  7:   */ import codechicken.nei.guihook.IContainerInputHandler;
/*  8:   */ import com.rwtema.extrautils.network.NetworkHandler;
/*  9:   */ import com.rwtema.extrautils.network.packets.PacketNEIPing;
/* 10:   */ import net.minecraft.client.gui.inventory.GuiContainer;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class NEIPing
/* 14:   */   implements IContainerInputHandler
/* 15:   */ {
/* 16:   */   public static final String KEY_IDENTIFIER = "gui.xu_ping";
/* 17:   */   
/* 18:   */   public boolean keyTyped(GuiContainer guiContainer, char c, int i)
/* 19:   */   {
/* 20:21 */     int keyBinding = NEIClientConfig.getKeyBinding("gui.xu_ping");
/* 21:22 */     if (i != keyBinding) {
/* 22:23 */       return false;
/* 23:   */     }
/* 24:26 */     LayoutManager layout = LayoutManager.instance();
/* 25:27 */     if ((layout == null) || (LayoutManager.itemPanel == null) || (NEIClientConfig.isHidden())) {
/* 26:28 */       return false;
/* 27:   */     }
/* 28:30 */     ItemStack stackMouseOver = GuiContainerManager.getStackMouseOver(guiContainer);
/* 29:32 */     if ((stackMouseOver == null) || (stackMouseOver.getItem() == null)) {
/* 30:33 */       return false;
/* 31:   */     }
/* 32:35 */     NetworkHandler.sendPacketToServer(new PacketNEIPing(stackMouseOver));
/* 33:36 */     return true;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void init()
/* 37:   */   {
/* 38:40 */     API.addKeyBind("gui.xu_ping", 20);
/* 39:41 */     GuiContainerManager.addInputHandler(new NEIPing());
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void onKeyTyped(GuiContainer guiContainer, char c, int i) {}
/* 43:   */   
/* 44:   */   public boolean lastKeyTyped(GuiContainer guiContainer, char c, int i)
/* 45:   */   {
/* 46:51 */     return false;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean mouseClicked(GuiContainer guiContainer, int i, int i1, int i2)
/* 50:   */   {
/* 51:56 */     return false;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void onMouseClicked(GuiContainer guiContainer, int i, int i1, int i2) {}
/* 55:   */   
/* 56:   */   public void onMouseUp(GuiContainer guiContainer, int i, int i1, int i2) {}
/* 57:   */   
/* 58:   */   public boolean mouseScrolled(GuiContainer guiContainer, int i, int i1, int i2)
/* 59:   */   {
/* 60:71 */     return false;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void onMouseScrolled(GuiContainer guiContainer, int i, int i1, int i2) {}
/* 64:   */   
/* 65:   */   public void onMouseDragged(GuiContainer guiContainer, int i, int i1, int i2, long l) {}
/* 66:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.ping.NEIPing
 * JD-Core Version:    0.7.0.1
 */