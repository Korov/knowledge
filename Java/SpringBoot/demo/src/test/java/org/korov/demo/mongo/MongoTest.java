package org.korov.demo.mongo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.korov.demo.DemoApplicationTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MongoTest extends DemoApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(MongoTest.class);

    private ValueRecordRepository valueRecordRepository;

    @Autowired
    public void setValueRecordRepository(ValueRecordRepository valueRecordRepository) {
        this.valueRecordRepository = valueRecordRepository;
    }

    @Disabled
    @Test
    public void queryTest() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<ValueRecord> page = valueRecordRepository.findAll(pageable);
        List<ValueRecord> recordList = page.toList();
        log.info("page size:{}", recordList.size());
    }
}
