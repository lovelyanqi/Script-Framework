/*
 * Copyright (c) 2018, 2020, LovelyAnQi. All rights reserved.
 *
 * GitHub: https://github.com/LovelyAnQi/Script-Framework/
 * License: GPL 3.0
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package lovelyanqi.script;

/**
 * Class {@code Functional} is Script's interface support for
 * functions, and Functional is also a functional interface that
 * can be compatible with Java lambda expressions.
 *
 * @author  LovelyAnQi
 * @see     lovelyanqi.script.ScriptProtocol
 * @see     lovelyanqi.script.TypeFunction
 * @since   3.0
 */
public interface Functional {
	
	/**
	 * Unique interface function, The subclass of the interface
	 * implements the function, which expresses the function of a function.
	 * <br />
	 * Lambda expressions are allowed and it is recommended to return
	 * <code>Script.Undefined()</code> when no return value is available.
	 */
	public java.lang.Object run(ScriptProtocol... parameters);
}
