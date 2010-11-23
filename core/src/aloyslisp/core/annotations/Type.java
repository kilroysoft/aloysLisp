/**
 * 
 */
package aloyslisp.core.annotations;

import java.lang.annotation.*;

/**
 * @author ivan
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Type
{

	/**
	 * Lisp Class type name
	 * 
	 * @return
	 */
	String name();

}
