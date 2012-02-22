package com.google.code.qualitas.internal.installation.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.code.qualitas.engines.api.component.Component;
import com.google.code.qualitas.engines.api.core.ProcessType;

/**
 * The Class AbstractProcessor.
 */
public abstract class AbstractProcessor implements Processor {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(AbstractProcessor.class);

    /** The context. */
    @Autowired
    private ApplicationContext context;

    /**
     * Finds qualitas component.
     * 
     * @param <T>
     *            the generic type
     * @param type
     *            the type
     * @param processType
     *            the process type
     * @return the T
     * @throws ComponentNotFound
     *             the component not found
     */
    protected <T extends Component> T findQualitasComponent(Class<T> type, ProcessType processType)
            throws ComponentNotFound {
        Map<String, T> components = context.getBeansOfType(type);

        for (String key : components.keySet()) {
            T component = components.get(key);
            if (component.isSupported(processType)) {
                return component;
            }
        }

        throw new ComponentNotFound("Could not find a component of type " + type
                + " for process bundle of type " + processType);
    }

    /**
     * Find qualitas components.
     * 
     * @param <T>
     *            the generic type
     * @param type
     *            the type
     * @param processType
     *            the process type
     * @return the list
     * @throws ComponentNotFound
     *             the component not found
     */
    protected <T extends Component> List<T> findQualitasComponents(Class<T> type,
            ProcessType processType) throws ComponentNotFound {
        Map<String, T> allComponents = context.getBeansOfType(type);

        List<T> components = new ArrayList<T>();

        for (String key : allComponents.keySet()) {
            T component = allComponents.get(key);
            if (component.isSupported(processType)) {
                components.add(component);
            }
        }

        if (components.size() == 0) {
            throw new ComponentNotFound("Could not find a component of type " + type
                    + " for process bundle of type " + processType);
        }

        return components;
    }

    /**
     * Find qualitas component.
     * 
     * @param <T>
     *            the generic type
     * @param <A>
     *            the generic type
     * @param type
     *            the type
     * @param annotationType
     *            the annotation type
     * @param annotationValue
     *            the annotation value
     * @param processType
     *            the process type
     * @return the t
     * @throws ComponentNotFound
     *             the component not found
     */
    protected <T extends Component, A extends Annotation> T findQualitasComponent(Class<T> type,
            Class<A> annotationType, Object annotationValue, ProcessType processType)
            throws ComponentNotFound {
        List<T> components = findQualitasComponents(type, processType);

        T component = null;
        A annotation = null;

        for (T c : components) {
            annotation = c.getClass().getAnnotation(annotationType);
            if (annotation != null) {
                component = c;
                break;
            }
        }

        boolean annotationValueMatched = false;
        if (annotation != null && annotationValue != null) {
            annotationValueMatched = doValuesMatch(annotationValue, annotation);
        }

        if (component == null
                || (annotation != null && annotationValue != null && !annotationValueMatched)) {
            throw new ComponentNotFound("Could not find a component of type " + type
                    + " for process bundle of type " + processType);
        }

        return component;
    }

    /**
     * Do values match.
     * 
     * @param <A>
     *            the generic type
     * @param annotationValue
     *            the annotation value
     * @param annotation
     *            the annotation
     * @return true, if successful
     */
    private <A> boolean doValuesMatch(Object annotationValue, A annotation) {
        try {
            Method m = annotation.getClass().getMethod("value", (Class<?>[]) null);
            Object value = m.invoke(annotation, (Object[]) null);
            return annotationValue.equals(value);
        } catch (Exception e) {
            LOG.info("Could not get value for annotation " + annotation, e);
        }
        return false;
    }

}
