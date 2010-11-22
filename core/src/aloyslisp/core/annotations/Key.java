/**
 * 
 */
package aloyslisp.core.annotations;

/**
 * @author ivan
 *
 */
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
