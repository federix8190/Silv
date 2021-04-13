/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.utils.tables.annotation;

import py.com.ceamso.utils.tables.filter.StringFilter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author daniel
 */
@Target({ ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AttributeDescriptor {
    Class<?> filter() default StringFilter.class; // indica el tipo de filtrado a utilizar
    String path(); //indica la columna de la tabla correspondiente en la BD
}
