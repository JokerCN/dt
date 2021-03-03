package com.iquantex.paas.nacostest.utils.mockito;

import com.iquantex.paas.nacostest.utils.mockito.intel.Statement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultSetHandler {

    public void handleResultSets(Statement statement){
        String queryString = statement.query();
        log.info("> {}", queryString);
    }
}
