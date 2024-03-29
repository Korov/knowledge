package org.korov.flink.name.count.frauddetection.source;

import org.apache.flink.annotation.Internal;
import org.apache.flink.api.common.io.GenericInputFormat;
import org.apache.flink.api.common.io.NonParallelInput;
import org.apache.flink.core.io.GenericInputSplit;
import org.apache.flink.types.Row;
import org.korov.flink.name.count.frauddetection.common.Transaction;

import java.sql.Timestamp;
import java.util.Iterator;

@Internal
public class TransactionRowInputFormat extends GenericInputFormat<Row> implements NonParallelInput {

    private static final long serialVersionUID = 1L;

    private transient Iterator<Transaction> transactions;

    @Override
    public void open(GenericInputSplit split) {
        transactions = TransactionIterator.bounded();
    }

    @Override
    public boolean reachedEnd() {
        return !transactions.hasNext();
    }

    @Override
    public Row nextRecord(Row reuse) {
        Transaction transaction = transactions.next();
        reuse.setField(0, transaction.getAccountId());
        reuse.setField(1, new Timestamp(transaction.getTimestamp()));
        reuse.setField(2, transaction.getAmount());

        return reuse;
    }
}
