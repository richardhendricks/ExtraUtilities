/*  1:   */ package baubles.api;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.FMLLog;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.inventory.IInventory;
/*  7:   */ 
/*  8:   */ public class BaublesApi
/*  9:   */ {
/* 10:   */   static Method getBaubles;
/* 11:   */   
/* 12:   */   public static IInventory getBaubles(EntityPlayer player)
/* 13:   */   {
/* 14:21 */     IInventory ot = null;
/* 15:   */     try
/* 16:   */     {
/* 17:23 */       if (getBaubles == null)
/* 18:   */       {
/* 19:24 */         Class fake = Class.forName("baubles.common.lib.PlayerHandler");
/* 20:25 */         getBaubles = fake.getMethod("getPlayerBaubles", new Class[] { EntityPlayer.class });
/* 21:   */       }
/* 22:27 */       ot = (IInventory)getBaubles.invoke(null, new Object[] { player });
/* 23:   */     }
/* 24:   */     catch (Exception ex)
/* 25:   */     {
/* 26:29 */       FMLLog.warning("[Baubles API] Could not invoke baubles.common.lib.PlayerHandler method getPlayerBaubles", new Object[0]);
/* 27:   */     }
/* 28:31 */     return ot;
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     baubles.api.BaublesApi
 * JD-Core Version:    0.7.0.1
 */