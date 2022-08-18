package org.korov.demo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ValueRecordRepository extends MongoRepository<ValueRecord, String> {
}
