/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.utils.tables.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author daniel
 */
@Target({ ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TableDescriptor {
    String name(); // nombre de la tabla
    String schema() default ""; // esquema de la tabla
}
