/*   1:    */ package com.rwtema.extrautils.command;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.nio.IntBuffer;
/*   6:    */ import org.lwjgl.opengl.GL11;
/*   7:    */ 
/*   8:    */ public class Texture
/*   9:    */ {
/*  10:    */   private int id;
/*  11:    */   public final int w;
/*  12:    */   public final int h;
/*  13:    */   private final IntBuffer pixelBuf;
/*  14:    */   
/*  15:    */   public Texture(int w, int h, int fillColour, int minFilter, int maxFilter, int textureWrap)
/*  16:    */   {
/*  17: 19 */     this.id = GL11.glGenTextures();
/*  18: 20 */     this.w = w;
/*  19: 21 */     this.h = h;
/*  20: 22 */     this.pixelBuf = allocateDirectIntBuffer(w * h);
/*  21: 23 */     fillRect(0, 0, w, h, fillColour);
/*  22: 24 */     this.pixelBuf.position(0);
/*  23: 25 */     bind();
/*  24: 26 */     GL11.glTexImage2D(3553, 0, 32856, w, h, 0, 32993, 5121, this.pixelBuf);
/*  25: 27 */     setTexParameters(minFilter, maxFilter, textureWrap);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static IntBuffer allocateDirectIntBuffer(int size)
/*  29:    */   {
/*  30: 31 */     return ByteBuffer.allocateDirect(size * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Texture(int w, int h, int fillColour)
/*  34:    */   {
/*  35: 34 */     this(w, h, fillColour, 9729, 9728, 33071);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static int getTextureWidth()
/*  39:    */   {
/*  40: 38 */     return GL11.glGetTexLevelParameteri(3553, 0, 4096);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static int getTextureHeight()
/*  44:    */   {
/*  45: 42 */     return GL11.glGetTexLevelParameteri(3553, 0, 4097);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Texture(int id)
/*  49:    */   {
/*  50: 47 */     this.id = id;
/*  51: 48 */     bind();
/*  52: 49 */     this.w = getTextureWidth();
/*  53: 50 */     this.h = getTextureHeight();
/*  54: 51 */     this.pixelBuf = allocateDirectIntBuffer(this.w * this.h);
/*  55: 52 */     getPixelsFromExistingTexture();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public synchronized void close()
/*  59:    */   {
/*  60: 57 */     if (this.id != 0)
/*  61:    */     {
/*  62:    */       try
/*  63:    */       {
/*  64: 59 */         GL11.glDeleteTextures(this.id);
/*  65:    */       }
/*  66:    */       catch (NullPointerException e)
/*  67:    */       {
/*  68: 61 */         log("MwTexture.close: null pointer exception (texture %d)", this.id);
/*  69:    */       }
/*  70: 63 */       this.id = 0;
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public synchronized void fillRect(int x, int y, int w, int h, int colour)
/*  75:    */   {
/*  76: 68 */     int offset = y * this.w + x;
/*  77: 69 */     for (int j = 0; j < h; j++)
/*  78:    */     {
/*  79: 70 */       this.pixelBuf.position(offset + j * this.w);
/*  80: 71 */       for (int i = 0; i < w; i++) {
/*  81: 72 */         this.pixelBuf.put(colour);
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public synchronized void getRGB(int x, int y, int w, int h, int[] pixels, int offset, int scanSize)
/*  87:    */   {
/*  88: 79 */     int bufOffset = y * this.w + x;
/*  89: 80 */     for (int i = 0; i < h; i++)
/*  90:    */     {
/*  91: 81 */       this.pixelBuf.position(bufOffset + i * this.w);
/*  92: 82 */       this.pixelBuf.get(pixels, offset + i * scanSize, w);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void bind()
/*  97:    */   {
/*  98: 87 */     GL11.glBindTexture(3553, this.id);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setTexParameters(int minFilter, int maxFilter, int textureWrap)
/* 102:    */   {
/* 103: 92 */     bind();
/* 104: 93 */     GL11.glTexParameteri(3553, 10242, textureWrap);
/* 105: 94 */     GL11.glTexParameteri(3553, 10243, textureWrap);
/* 106: 95 */     GL11.glTexParameteri(3553, 10241, minFilter);
/* 107: 96 */     GL11.glTexParameteri(3553, 10240, maxFilter);
/* 108:    */   }
/* 109:    */   
/* 110:    */   private synchronized void getPixelsFromExistingTexture()
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:102 */       bind();
/* 115:103 */       this.pixelBuf.clear();
/* 116:104 */       GL11.glGetTexImage(3553, 0, 32993, 5121, this.pixelBuf);
/* 117:    */       
/* 118:    */ 
/* 119:107 */       this.pixelBuf.limit(this.w * this.h);
/* 120:    */     }
/* 121:    */     catch (NullPointerException e)
/* 122:    */     {
/* 123:109 */       log("MwTexture.getPixels: null pointer exception (texture %d)", this.id);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   private void log(String s, int id) {}
/* 128:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.Texture
 * JD-Core Version:    0.7.0.1
 */