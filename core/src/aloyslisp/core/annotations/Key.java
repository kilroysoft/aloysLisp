/**
 * 
 */
package aloyslisp.core.annotations;

import java.lang.annotation.*;

/**
 * Argument is a keyword
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key
{
	/**
	 * Lisp name of key
	 * 
	 * @return
	 */
	String name();

	/**
	 * Default value as a string
	 * 
	 * @return
	 */
	String def() default "";

}
