/*
 * MIT License
 *
 * Copyright (c) 2023-present Bawnorton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bawnorton.mixinsquared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetHandler {
	/**
	 * The exact name of the original handler method. Optionally followed by the method descriptor for more specificity.
	 */
	String name();

	/**
	 * The fully qualified name of the mixin class that contains the handler method.
	 */
	String mixin();

	/**
	 * The prefix applied to the merged handler by the injector.
	 * <table>
	 *     <caption>Prefixes</caption>
	 *     <tr>
	 *         <th>Annotation</th>
	 *         <th>Prefix</th>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@Inject</b></td>
	 *         <td>handler</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@ModifyArg</b></td>
	 *         <td>modify</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@ModifyArgs</b></td>
	 *         <td>args</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@ModifyConstant</b></td>
	 *         <td>constant</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@ModifyVariable</b></td>
	 *         <td>localvar</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@Redirect</b></td>
	 *         <td>redirect</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@ModifyExpressionValue</b></td>
	 *         <td>modifyExpressionValue</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@ModifyReciever</b></td>
	 *         <td>modifyReciever</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@ModifyReturnValue</b></td>
	 *         <td>modifyReturnValue</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@WrapWithCondition</b></td>
	 *         <td>wrapWithCondition</td>
	 *     </tr>
	 *     <tr>
	 *         <td><b>@WrapOperation</b></td>
	 *         <td>wrapOperation</td>
	 *     </tr>
	 * </table>
	 */
	String prefix() default "";

	/**
	 * Print all possible candidates for the handler method.
	 * Will disable selector matching.
	 */
	boolean print() default false;
}
