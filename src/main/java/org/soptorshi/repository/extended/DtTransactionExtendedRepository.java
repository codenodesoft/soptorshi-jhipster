package org.soptorshi.repository.extended;

import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.repository.DtTransactionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DtTransactionExtendedRepository extends DtTransactionRepository {
    List<DtTransaction> findByVoucherNoAndVoucherDate(String vocherNo, LocalDate voucherDate);

    List<DtTransaction> findByAccountAndVoucherDateBetween(MstAccount account, LocalDate fromDate, LocalDate toDate);

    List<DtTransaction> findByVoucherDateBetween(LocalDate fromDate, LocalDate toDate);
}
