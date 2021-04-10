package com.company;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.reflect.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;
import javax.lang.model.element.Element;

@SupportedAnnotationTypes("*")
public class InstanceVariablePreProcessor extends AbstractProcessor {



    public static <T> void checkIsInstanceVariable(Class<T> clazz) {
        try {
            if (clazz.isAnnotationPresent(IsInstanceVariable.class)) {
                Field[] fields = clazz.getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if(getAnnotation(field, IsInstanceVariable.class) !=null &&
                            Modifier.isStatic(field.getModifiers())){
                        throw new IllegalArgumentException("field "+field.getName()+" is not instance");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static <T> T getAnnotation(Field field, Class clazz) {
        Annotation[] anns = field.getAnnotationsByType(clazz);
        if (anns != null && anns.length > 0) {
            return (T) anns[0];
        }

        return null;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(IsInstanceVariable.class);
        for(Element element: elements){
            Set<javax.lang.model.element.Modifier> modifiers = element.getModifiers();
            for(javax.lang.model.element.Modifier modifier: modifiers){
                if(modifier == javax.lang.model.element.Modifier.STATIC){
                    return false;
                }
            }
        }
        return true;
    }
}

