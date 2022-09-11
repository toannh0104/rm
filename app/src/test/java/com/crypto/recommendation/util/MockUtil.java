package com.crypto.recommendation.util;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

public class MockUtil {
    public static void mockAllAttributes(final Class clazz) {
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> Attribute.class.isAssignableFrom(field.getType()))
                .collect(Collectors.toList())
                .forEach((ThrowingConsumer<Field>) MockUtil::mockAllAttributeField);
    }

    private static void mockAllAttributeField(final Field field) throws IllegalAccessException {
        field.setAccessible(Boolean.TRUE);
        Attribute mock = null;

        if(field.getType().equals(SingularAttribute.class)) {
            mock = mock(SingularAttribute.class);
        }

        lenient().when(mock.getName()).thenReturn(field.getName());
        field.set(null, mock);
    }
}
