/**
 * 
 */
package aloyslisp.annotations;

import java.lang.annotation.*;

/**
 * Argument is a keyword
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface aKey
{
	/**
	 * Lisp definition of keys
	 * 
	 * @return Lisp definition of keys
	 */
	String keys() default "NIL";

}
