package com.iquantex.paas.nacostest.utils.mockito;


import com.iquantex.paas.nacostest.utils.mockito.intel.Statement;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

@Slf4j
public class MockitoTest {

    @Mock
    private Statement statement;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testMockito(){
        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
    }

    @Test
    public void testMockitoSpy(){
        Statement mockStatement = mock(Statement.class);
        when(mockStatement.query()).thenReturn("test");
//        ResultSetHandler mockHandler = spy(ResultSetHandler.class);
//        mockHandler.handleResultSets(statement);
        ResultSetHandler mockHandler = new ResultSetHandler();
        mockHandler.handleResultSets(mockStatement);
    }

    @Test
    public void testRule(){
        when(statement.query()).thenReturn("abc");
        log.info("> statement.query {}", statement.query());
    }

    @Test
    public void testParameter(){
        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(anyInt())).thenReturn("element");
        log.info("> {}", mockedList.get(100));
    }
}
