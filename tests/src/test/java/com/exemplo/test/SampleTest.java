package com.exemplo.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SampleTest {

    @Mock
    private List<String> mockedList;

    @Test
    void testWithMockitoAndAssertJ() {
        // GIVEN
        when(mockedList.size()).thenReturn(10);

        // WHEN
        int size = mockedList.size();

        // THEN
        assertThat(size)
                .isEqualTo(10)
                .isGreaterThan(5);
    }

    @Test
    void simpleJavaTest() {
        String value = "Java Multi-module";
        assertThat(value).contains("Java");
    }
}
