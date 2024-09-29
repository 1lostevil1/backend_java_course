package edu.hw10.Task1;

import com.github.javafaker.Faker;
import edu.hw10.annotations.Max;
import edu.hw10.annotations.Min;
import edu.hw10.annotations.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomObjectGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Faker FAKER = new Faker();

    private RandomObjectGenerator() {
    }

    public static Object nextObject(Class<?> objectClass, String methodName) {
        try {

            Method method = Arrays.stream(objectClass.getDeclaredMethods())
                .filter(declaredMethod ->
                    declaredMethod.getName().equals(methodName)
                        && declaredMethod.getReturnType().equals(objectClass)
                )
                .findAny()
                .orElseThrow(NoSuchMethodException::new);

            List<Object> arguments = generateArgs(method.getParameters());

            return method.invoke(objectClass, arguments.toArray());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOGGER.error(e.getMessage());

            throw new IllegalCallerException(e);
        }
    }

    public static Object nextObject(Class<?> objectClass) {
        var constructors = objectClass.getDeclaredConstructors();

        if (constructors.length == 0) {
            return null;
        }

        // Получаем случайный конструктор
        var constructor = constructors[ThreadLocalRandom.current().nextInt(constructors.length)];

        List<Object> arguments = generateArgs(constructor.getParameters());

        try {
            return constructor.newInstance(arguments.toArray());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.getMessage());

            throw new IllegalCallerException(e);
        }
    }

    private static List<Object> generateArgs(Parameter[] parameters) {
        List<Object> arguments = new LinkedList<>();

        for (Parameter parameter : parameters) {
            // Получаем аннотации для каждого параметра
            Annotation[] annotations = parameter.getAnnotations();
            Class<?> type = parameter.getType();

            arguments.add(generateByType(type, annotations));
        }

        return arguments;
    }

    @SuppressWarnings("checkstyle:ReturnCount")
    private static Object generateByType(Class<?> type, Annotation[] annotations) {
        if (type == String.class) {
            return generateString(annotations);
        } else if (type == Integer.class || type == int.class) {
            return generateInteger(annotations);
        } else if (type == Double.class || type == double.class) {
            return generateDouble(annotations);
        } else if (type == Character.class || type == char.class) {
            return generateChar(annotations);
        } else if (type == Boolean.class || type == boolean.class) {
            return generateBoolean(annotations);
        } else {
            return null;
        }
    }

    private static String generateString(Annotation[] annotations) {
        if (Arrays.stream(annotations).anyMatch(annotation -> annotation.annotationType() == NotNull.class)) {
            return FAKER.lorem().word();
        }

        return FAKER.bool().bool() ? FAKER.lorem().word() : null;
    }

    private static Integer generateInteger(Annotation[] annotations) {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;

        Min minAnnotation = (Min) Arrays.stream(annotations)
            .filter(annotation -> annotation.annotationType() == Min.class)
            .findFirst()
            .orElse(null);

        Max maxAnnotation = (Max) Arrays.stream(annotations)
            .filter(annotation -> annotation.annotationType() == Max.class)
            .findFirst()
            .orElse(null);

        if (minAnnotation != null) {
            min = minAnnotation.value();
        }

        if (maxAnnotation != null) {
            max = maxAnnotation.value();
        }

        return FAKER.number().numberBetween(min, max);
    }

    private static double generateDouble(Annotation[] annotations) {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;

        Min minAnnotation = (Min) Arrays.stream(annotations)
            .filter(annotation -> annotation.annotationType() == Min.class)
            .findFirst()
            .orElse(null);

        Max maxAnnotation = (Max) Arrays.stream(annotations)
            .filter(annotation -> annotation.annotationType() == Min.class)
            .findFirst()
            .orElse(null);

        if (minAnnotation != null) {
            min = minAnnotation.value();
        }

        if (maxAnnotation != null) {
            max = maxAnnotation.value();
        }

        return FAKER.number().randomDouble(2, min, max);
    }

    private static char generateChar(Annotation[] annotations) {
        return (char) FAKER.number().randomNumber();
    }

    private static boolean generateBoolean(Annotation[] annotations) {
        return FAKER.bool().bool();
    }
}
