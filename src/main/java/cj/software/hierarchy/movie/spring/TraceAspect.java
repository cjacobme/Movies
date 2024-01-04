package cj.software.hierarchy.movie.spring;

import cj.software.hierarchy.movie.util.MethodDescriptionGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

@Component
@Aspect
@Order(0)
public class TraceAspect {
    private static final String KEY = "indent";

    private static final String INDENT = "    ";

    @Autowired
    MethodDescriptionGenerator descriptionGenerator;

    @Pointcut("execution(* cj.software.hierarchy.movie..*.*(..)) && ! within(TraceAspect)")
    public void hierarchyMovieMethod() {
        // marks all methods of all classes under the specified package
    }

    @Pointcut("execution(* cj.software.hierarchy.movie..entity..*.*(..))")
    public void entity() {
        // marks all methods of all classes for which the package path contains "entity"
        // they should not be logged in order not to clog the logging
    }

    @Pointcut("execution(* cj.software.hierarchy.movie..*.get*())")
    public void objectGetter() {
        // marks all getters
        // they should not be logged in order not to clog the logging
    }

    @Pointcut("execution(boolean cj.software.hierarchy.movie..*.is*())")
    public void booleanGetter() {
        // marks all getters for simple booleans
        // they should not be logger in order not to clog the logging
    }

    @Pointcut("@annotation(cj.software.hierarchy.movie.spring.Trace)")
    public void traceAnnotated() {
        // marks all methods with the annotation @Trace
    }

    @Pointcut("@annotation(cj.software.hierarchy.movie.spring.TraceSize)")
    public void traceSizeAnnotated() {
        // marks all methods with the annotation @TraceSize
    }

    @Pointcut("(hierarchyMovieMethod() && ! entity() && ! objectGetter() && ! booleanGetter()) || traceAnnotated() || traceSizeAnnotated()")
    public void toBeTraced() {
        // sums everything up
    }

    @Before("toBeTraced()")
    public void reportMethodEntry(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Class<?> declaringType = methodSignature.getDeclaringType();
        Logger logger = LogManager.getFormatterLogger(declaringType);
        if (canLog(method, logger)) {
            Object[] args = joinPoint.getArgs();
            String[] parameterNames = methodSignature.getParameterNames();
            SortedSet<Integer> indexes = determineIndexesOfLoggedParameters(methodSignature);
            String description = descriptionGenerator.generateMethodEntry(method, parameterNames, args, indexes);
            logger.info(">%s", description);
        }
        String indentValue = MDC.get(KEY);
        if (indentValue == null) {
            indentValue = "";
        }
        indentValue = String.format("%s%s", INDENT, indentValue);
        MDC.put(KEY, indentValue);
    }

    private boolean canLog(Method method, Logger logger) {
        TraceAtLogLevel traceAtLogLevel = method.getAnnotation(TraceAtLogLevel.class);
        if (traceAtLogLevel == null) {
            Class<?> clazz = method.getDeclaringClass();
            traceAtLogLevel = clazz.getAnnotation(TraceAtLogLevel.class);
        }
        int requiredLevel = (traceAtLogLevel != null ? traceAtLogLevel.level().intLevel() : Level.INFO.intLevel());
        int loggerLevel = logger.getLevel().intLevel();
        boolean result = (loggerLevel >= requiredLevel);
        return result;
    }

    private SortedSet<Integer> determineIndexesOfLoggedParameters(MethodSignature methodSignature) {
        SortedSet<Integer> result = new TreeSet<>();
        Method method = methodSignature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();
        int outerCount = annotations.length;
        for (int outer = 0; outer < outerCount; outer++) {
            Annotation[] innerAnnotations = annotations[outer];
            for (Annotation innerAnnotation : innerAnnotations) {
                Class<?> annoClass = innerAnnotation.annotationType();
                if (annoClass.isAssignableFrom(Trace.class)) {
                    result.add(outer);
                    break;
                }
            }
        }
        return result;
    }
    @AfterReturning(value = "traceAnnotated()", returning = "result")
    public void reportMethodReturnWithResult(JoinPoint joinPoint, Object result) {
        decreaseIndent();
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        Method method = sig.getMethod();
        Class<?> declaringType = sig.getDeclaringType();
        Logger logger = LogManager.getFormatterLogger(declaringType);
        if (canLog(method, logger)) {
            String description = descriptionGenerator.generateMethodExit(method, result);
            logger.info("<%s", description);
        }
    }

    private void decreaseIndent() {
        String indentValue = MDC.get(KEY);
        if (indentValue != null && ! indentValue.isEmpty()) {
            indentValue = indentValue.substring(INDENT.length());
            MDC.put(KEY, indentValue);
        }
    }

    @AfterReturning(value = "traceSizeAnnotated()", returning = "result")
    public void reportMethodReturnWithSize(JoinPoint joinPoint, Object result) {
        decreaseIndent();
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        Method method = sig.getMethod();
        Class<?> declaringType = sig.getDeclaringType();
        Class<?> resultClass = result.getClass();
        if (result instanceof Collection<?> collection) {
            Logger logger = LogManager.getFormatterLogger(declaringType);
            if (canLog(method, logger)) {
                String description = descriptionGenerator.generateMethodExitCollection(method, collection);
                logger.info("<%s", description);
            }
        } else if (result instanceof Map<?, ?> map) {
            Logger logger = LogManager.getFormatterLogger(declaringType);
            if (canLog(method, logger)) {
                String description = descriptionGenerator.generateMethodExitMap(method, map);
                logger.info("<%s", description);
            }
        } else if (resultClass.isArray()) {
            Logger logger = LogManager.getFormatterLogger(declaringType);
            if (canLog(method, logger)) {
                String description = descriptionGenerator.generateMethodExitArray(method, result);
                logger.info("<%s", description);
            }
        } else {
            throw new IllegalArgumentException(
                    String.format("Object can't report a size: %s", resultClass.getName()));
        }
    }

    @AfterReturning("toBeTraced() && ! traceAnnotated() && ! traceSizeAnnotated()")
    public void reportMethodReturnWithoutResult(JoinPoint joinPoint) {
        decreaseIndent();
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        Class<?> declaringType = sig.getDeclaringType();
        Logger logger = LogManager.getFormatterLogger(declaringType);
        Method method = sig.getMethod();
        if (canLog(method, logger)) {
            String description = descriptionGenerator.generateMethodExit(method);
            logger.info("<%s", description);
        }
    }

    @AfterThrowing(value = "toBeTraced()", throwing = "throwable")
    public void reportMethodReturnWithException(JoinPoint joinPoint, Throwable throwable) {
        decreaseIndent();
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        Class<?> declaringType = sig.getDeclaringType();
        Logger logger = LogManager.getFormatterLogger(declaringType);
        Method method = sig.getMethod();
        if (canLog(method, logger)) {
            String description = descriptionGenerator.generateMethodThrew(method, throwable);
            logger.error("<%s", description);
        }

    }

}
