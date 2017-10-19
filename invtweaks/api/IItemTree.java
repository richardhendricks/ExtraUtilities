package invtweaks.api;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract interface IItemTree
{
  public abstract void registerOre(String paramString1, String paramString2, String paramString3, int paramInt);
  
  public abstract boolean matches(List<IItemTreeItem> paramList, String paramString);
  
  public abstract boolean isKeywordValid(String paramString);
  
  public abstract Collection<IItemTreeCategory> getAllCategories();
  
  public abstract IItemTreeCategory getRootCategory();
  
  public abstract IItemTreeCategory getCategory(String paramString);
  
  public abstract boolean isItemUnknown(String paramString, int paramInt);
  
  public abstract List<IItemTreeItem> getItems(String paramString, int paramInt);
  
  public abstract List<IItemTreeItem> getItems(String paramString);
  
  public abstract IItemTreeItem getRandomItem(Random paramRandom);
  
  public abstract boolean containsItem(String paramString);
  
  public abstract boolean containsCategory(String paramString);
  
  public abstract void setRootCategory(IItemTreeCategory paramIItemTreeCategory);
  
  public abstract IItemTreeCategory addCategory(String paramString1, String paramString2)
    throws NullPointerException;
  
  public abstract void addCategory(String paramString, IItemTreeCategory paramIItemTreeCategory)
    throws NullPointerException;
  
  public abstract IItemTreeItem addItem(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
    throws NullPointerException;
  
  public abstract void addItem(String paramString, IItemTreeItem paramIItemTreeItem)
    throws NullPointerException;
  
  public abstract int getKeywordDepth(String paramString);
  
  public abstract int getKeywordOrder(String paramString);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     invtweaks.api.IItemTree
 * JD-Core Version:    0.7.0.1
 */