/*   1:    */ package com.rwtema.extrautils.modintegration;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Throwables;
/*   4:    */ import com.rwtema.extrautils.LogHelper;
/*   5:    */ import cpw.mods.fml.client.FMLClientHandler;
/*   6:    */ import cpw.mods.fml.common.ObfuscationReflectionHelper;
/*   7:    */ import java.awt.image.BufferedImage;
/*   8:    */ import java.awt.image.DirectColorModel;
/*   9:    */ import java.io.ByteArrayInputStream;
/*  10:    */ import java.io.ByteArrayOutputStream;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.InputStream;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Set;
/*  17:    */ import javax.imageio.ImageIO;
/*  18:    */ import net.minecraft.client.Minecraft;
/*  19:    */ import net.minecraft.client.resources.IReloadableResourceManager;
/*  20:    */ import net.minecraft.client.resources.IResourceManager;
/*  21:    */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*  22:    */ import net.minecraft.client.resources.IResourcePack;
/*  23:    */ import net.minecraft.client.resources.ResourcePackRepository;
/*  24:    */ import net.minecraft.client.resources.ResourcePackRepository.Entry;
/*  25:    */ import net.minecraft.client.resources.data.IMetadataSection;
/*  26:    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*  27:    */ import net.minecraft.util.ResourceLocation;
/*  28:    */ 
/*  29:    */ public abstract class TConTextureResourcePackBase
/*  30:    */   implements IResourcePack, IResourceManagerReloadListener
/*  31:    */ {
/*  32:    */   public static List<IResourcePack> packs;
/*  33: 27 */   protected static DirectColorModel rgb = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
/*  34:    */   protected final String name;
/*  35: 34 */   public HashMap<ResourceLocation, byte[]> cachedImages = new HashMap();
/*  36:    */   protected IResourcePack delegate;
/*  37: 36 */   protected List<IResourcePack> resourcePackz = null;
/*  38:    */   
/*  39:    */   public TConTextureResourcePackBase(String name)
/*  40:    */   {
/*  41: 39 */     this.name = name.toLowerCase();
/*  42: 40 */     this.delegate = FMLClientHandler.instance().getResourcePackFor("TConstruct");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int brightness(int col)
/*  46:    */   {
/*  47: 44 */     return brightness(rgb.getRed(col), rgb.getGreen(col), rgb.getBlue(col));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int brightness(int r, int g, int b)
/*  51:    */   {
/*  52: 48 */     return (int)(r * 0.2126F + g * 0.7152F + b * 0.0722F);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void register()
/*  56:    */   {
/*  57: 52 */     List<IResourcePack> packs = getiResourcePacks();
/*  58: 53 */     packs.add(this);
/*  59: 54 */     ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
/*  60: 55 */     LogHelper.info("Registered TCon Resource Pack (" + this.name + ") - " + getClass().getSimpleName(), new Object[0]);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<IResourcePack> getiResourcePacks()
/*  64:    */   {
/*  65: 59 */     List<IResourcePack> packs1 = packs;
/*  66: 60 */     if (packs1 == null) {
/*  67: 61 */       packs1 = (List)ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), new String[] { "resourcePackList" });
/*  68:    */     }
/*  69: 62 */     return packs1;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public InputStream getStream(ResourceLocation location)
/*  73:    */   {
/*  74: 66 */     InputStream stream = null;
/*  75: 67 */     for (IResourcePack iResourcePack : getPacks()) {
/*  76: 68 */       if (iResourcePack.resourceExists(location)) {
/*  77:    */         try
/*  78:    */         {
/*  79: 70 */           stream = iResourcePack.getInputStream(location);
/*  80:    */         }
/*  81:    */         catch (IOException ignore) {}
/*  82:    */       }
/*  83:    */     }
/*  84: 76 */     return stream;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<IResourcePack> getPacks()
/*  88:    */   {
/*  89: 80 */     if (this.resourcePackz == null)
/*  90:    */     {
/*  91: 81 */       this.resourcePackz = new ArrayList();
/*  92: 82 */       this.resourcePackz.add(this.delegate);
/*  93: 83 */       List<ResourcePackRepository.Entry> t = Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries();
/*  94: 84 */       for (ResourcePackRepository.Entry entry : t)
/*  95:    */       {
/*  96: 85 */         IResourcePack resourcePack = entry.getResourcePack();
/*  97: 86 */         if (resourcePack.getResourceDomains().contains("tinker")) {
/*  98: 87 */           this.resourcePackz.add(resourcePack);
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102: 92 */     return this.resourcePackz;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public InputStream getInputStream(ResourceLocation p_110590_1_)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108: 97 */     byte[] bytes = (byte[])this.cachedImages.get(p_110590_1_);
/* 109: 98 */     if (bytes == null)
/* 110:    */     {
/* 111: 99 */       ResourceLocation location = new ResourceLocation("tinker", p_110590_1_.getResourcePath().replace(this.name, ""));
/* 112:100 */       InputStream inputStream = getStream(location);
/* 113:102 */       if (inputStream == null)
/* 114:    */       {
/* 115:103 */         location = new ResourceLocation("tinker", p_110590_1_.getResourcePath().replace(this.name, "iron"));
/* 116:104 */         inputStream = getStream(location);
/* 117:    */       }
/* 118:107 */       if (inputStream == null)
/* 119:    */       {
/* 120:108 */         location = new ResourceLocation("tinker", p_110590_1_.getResourcePath().replace(this.name, "stone"));
/* 121:109 */         inputStream = getStream(location);
/* 122:    */       }
/* 123:112 */       if (inputStream == null) {
/* 124:113 */         return this.delegate.getInputStream(p_110590_1_);
/* 125:    */       }
/* 126:    */       BufferedImage bufferedimage;
/* 127:    */       try
/* 128:    */       {
/* 129:117 */         bufferedimage = ImageIO.read(inputStream);
/* 130:    */       }
/* 131:    */       catch (IOException err)
/* 132:    */       {
/* 133:119 */         throw Throwables.propagate(err);
/* 134:    */       }
/* 135:    */       BufferedImage image;
/* 136:    */       try
/* 137:    */       {
/* 138:124 */         image = modifyImage(bufferedimage);
/* 139:    */       }
/* 140:    */       catch (Throwable t)
/* 141:    */       {
/* 142:126 */         t.printStackTrace();
/* 143:127 */         return this.delegate.getInputStream(location);
/* 144:    */       }
/* 145:130 */       ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 146:131 */       ImageIO.write(image, "PNG", stream);
/* 147:132 */       bytes = stream.toByteArray();
/* 148:133 */       this.cachedImages.put(location, bytes);
/* 149:    */     }
/* 150:136 */     return new ByteArrayInputStream(bytes);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public abstract BufferedImage modifyImage(BufferedImage paramBufferedImage);
/* 154:    */   
/* 155:    */   public boolean resourceExists(ResourceLocation p_110589_1_)
/* 156:    */   {
/* 157:143 */     if (!"tinker".equals(p_110589_1_.getResourceDomain())) {
/* 158:144 */       return false;
/* 159:    */     }
/* 160:147 */     String resourcePath = p_110589_1_.getResourcePath();
/* 161:148 */     if ((!resourcePath.startsWith("textures/items/")) || (!resourcePath.endsWith(".png"))) {
/* 162:149 */       return false;
/* 163:    */     }
/* 164:151 */     if (this.delegate.resourceExists(p_110589_1_)) {
/* 165:152 */       return false;
/* 166:    */     }
/* 167:154 */     if (!resourcePath.contains(this.name)) {
/* 168:155 */       return false;
/* 169:    */     }
/* 170:157 */     return (this.delegate.resourceExists(new ResourceLocation("tinker", resourcePath.replace(this.name, "stone")))) || (this.delegate.resourceExists(new ResourceLocation("tinker", resourcePath.replace(this.name, "iron")))) || (this.delegate.resourceExists(new ResourceLocation("tinker", resourcePath.replace(this.name, ""))));
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Set getResourceDomains()
/* 174:    */   {
/* 175:165 */     return this.delegate.getResourceDomains();
/* 176:    */   }
/* 177:    */   
/* 178:    */   public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_)
/* 179:    */     throws IOException
/* 180:    */   {
/* 181:170 */     return null;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public BufferedImage getPackImage()
/* 185:    */     throws IOException
/* 186:    */   {
/* 187:175 */     return null;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getPackName()
/* 191:    */   {
/* 192:180 */     return "XU_Delegate_Pack";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void onResourceManagerReload(IResourceManager p_110549_1_)
/* 196:    */   {
/* 197:185 */     this.cachedImages.clear();
/* 198:186 */     this.resourcePackz = null;
/* 199:    */   }
/* 200:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.TConTextureResourcePackBase
 * JD-Core Version:    0.7.0.1
 */