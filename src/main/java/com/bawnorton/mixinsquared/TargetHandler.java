package com.bawnorton.mixinsquared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetHandler {
    /**
     * The name of the handler to target
     */
    String name();

    /**
     * The fully qualified name of the mixin class that contains the handler
     */
    String mixin();

    /**
     * The prefix of the handler method to match
     * <table>
     *     <tr>
     *         <th style="width:150px">Annotation</th>
     *         <th>Prefix</th>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @Inject}</b></td>
     *         <td>handler</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @ModifyArg}</b></td>
     *         <td>modify</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @ModifyArgs}</b></td>
     *         <td>args</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @ModifyConstant}</b></td>
     *         <td>constant</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @ModifyVariable}</b></td>
     *         <td>localvar</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @Redirect}</b></td>
     *         <td>redirect</td>
     *     </tr>
     *     <tr>
     *         <td><i>Mixin Extras</i></td>
     *         <td></td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @ModifyExpressionValue}</b></td>
     *         <td>modifyExpressionValue</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @ModifyReciever}</b></td>
     *         <td>modifyReciever</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @ModifyReturnValue}</b></td>
     *         <td>modifyReturnValue</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @WrapWithCondition}</b></td>
     *         <td>wrapWithCondition</td>
     *     </tr>
     *     <tr>
     *         <td><b>{@code @WrapOperation}</b></td>
     *         <td>wrapOperation</td>
     *     </tr>
     * </table>
     */
    String prefix() default "";
}
