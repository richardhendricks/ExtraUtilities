package invtweaks.api.container;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface ChestContainer
{
  boolean showButtons() default true;
  
  int rowSize() default 9;
  
  boolean isLargeChest() default false;
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.METHOD})
  public static @interface IsLargeCallback {}
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.METHOD})
  public static @interface RowSizeCallback {}
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     invtweaks.api.container.ChestContainer
 * JD-Core Version:    0.7.0.1
 */