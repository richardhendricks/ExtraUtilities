package invtweaks.api;

public abstract interface IItemTreeItem
  extends Comparable<IItemTreeItem>
{
  public abstract String getName();
  
  public abstract String getId();
  
  public abstract int getDamage();
  
  public abstract int getOrder();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     invtweaks.api.IItemTreeItem
 * JD-Core Version:    0.7.0.1
 */