package invtweaks.api;

import java.util.Collection;
import java.util.List;

public abstract interface IItemTreeCategory
{
  public abstract boolean contains(IItemTreeItem paramIItemTreeItem);
  
  public abstract void addCategory(IItemTreeCategory paramIItemTreeCategory);
  
  public abstract void addItem(IItemTreeItem paramIItemTreeItem);
  
  public abstract Collection<IItemTreeCategory> getSubCategories();
  
  public abstract Collection<List<IItemTreeItem>> getItems();
  
  public abstract String getName();
  
  public abstract int getCategoryOrder();
  
  public abstract int findCategoryOrder(String paramString);
  
  public abstract int findKeywordDepth(String paramString);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     invtweaks.api.IItemTreeCategory
 * JD-Core Version:    0.7.0.1
 */