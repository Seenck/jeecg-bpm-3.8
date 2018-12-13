package org.apache;

import java.lang.reflect.InvocationTargetException;

import javax.naming.NamingException;

/**
 * JEECG-BPM Tomcat7支持需要这个类
 * @author zhangdaihao
 *
 */
public interface AnnotationProcessor {
    public void postConstruct(Object instance) throws IllegalAccessException,
            InvocationTargetException;

    public void preDestroy(Object instance) throws IllegalAccessException,
            InvocationTargetException;

    public void processAnnotations(Object instance)
            throws IllegalAccessException, InvocationTargetException,
            NamingException;
}