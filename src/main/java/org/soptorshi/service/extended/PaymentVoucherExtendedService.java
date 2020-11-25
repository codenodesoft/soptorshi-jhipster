package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.AccountType;
import org.soptorshi.domain.enumeration.ApplicationType;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.CurrencyFlag;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.PaymentVoucherGeneratorRepository;
import org.soptorshi.repository.PaymentVoucherRepository;
import org.soptorshi.repository.SalaryVoucherRelationRepository;
import org.soptorshi.repository.extended.*;
import org.soptorshi.repository.search.PaymentVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CurrencyQueryService;
import org.soptorshi.service.DtTransactionQueryService;
import org.soptorshi.service.PaymentVoucherService;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.soptorshi.service.mapper.PaymentVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentVoucherExtendedService extends PaymentVoucherService {

    private final PaymentVoucherGeneratorRepository paymentVoucherGeneratorRepository;
    private final DtTransactionQueryService dtTransactionQueryService;
    private final DtTransactionRepository dtTransactionRepository;
    private final DtTransactionExtendedService dtTransactionExtendedService;
    private final CurrencyQueryService currencyQueryService;
    private final DtTransactionMapper dtTransactionMapper;
    private final CurrencyExtendedRepository currencyExtendedRepository;
    private final SystemAccountMapExtendedRepository systemAccountMapExtendedRepository;
    private final SalaryVoucherRelationRepository salaryVoucherRelationRepository;
    private final RequisitionVoucherRelationExtendedService requisitionVoucherRelationService;
    private final RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository;
    private final JournalVoucherExtendedService journalVoucherExtendedService;
    private final PaymentVoucherExtendedRepository paymentVoucherExtendedRepository;
    private final PaymentVoucherMapper paymentVoucherMapper;
    private final PurchaseOrderVoucherRelationExtendedService purchaseOrderVoucherRelationExtendedService;
    private final PurchaseOrderVoucherRelationExtendedRepository purchaseOrderVoucherRelationExtendedRepository;
    private final MstAccountExtendedRepository mstAccountExtendedRepository;

    public PaymentVoucherExtendedService(PaymentVoucherRepository paymentVoucherRepository, PaymentVoucherMapper paymentVoucherMapper, PaymentVoucherSearchRepository paymentVoucherSearchRepository, PaymentVoucherGeneratorRepository paymentVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionExtendedService, CurrencyQueryService currencyQueryService, DtTransactionMapper dtTransactionMapper, CurrencyExtendedRepository currencyExtendedRepository, SystemAccountMapExtendedRepository systemAccountMapExtendedRepository, SalaryVoucherRelationRepository salaryVoucherRelationRepository, RequisitionVoucherRelationExtendedService requisitionVoucherRelationService, RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository, JournalVoucherExtendedService journalVoucherExtendedService, PaymentVoucherExtendedRepository paymentVoucherExtendedRepository, PaymentVoucherMapper paymentVoucherMapper1, PurchaseOrderVoucherRelationExtendedService purchaseOrderVoucherRelationExtendedService, PurchaseOrderVoucherRelationExtendedRepository purchaseOrderVoucherRelationExtendedRepository, MstAccountExtendedRepository mstAccountExtendedRepository) {
        super(paymentVoucherRepository, paymentVoucherMapper, paymentVoucherSearchRepository);
        this.paymentVoucherGeneratorRepository = paymentVoucherGeneratorRepository;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionExtendedService = dtTransactionExtendedService;
        this.currencyQueryService = currencyQueryService;
        this.dtTransactionMapper = dtTransactionMapper;
        this.currencyExtendedRepository = currencyExtendedRepository;
        this.systemAccountMapExtendedRepository = systemAccountMapExtendedRepository;
        this.salaryVoucherRelationRepository = salaryVoucherRelationRepository;
        this.requisitionVoucherRelationService = requisitionVoucherRelationService;
        this.requisitionVoucherRelationExtendedRepository = requisitionVoucherRelationExtendedRepository;
        this.journalVoucherExtendedService = journalVoucherExtendedService;
        this.paymentVoucherExtendedRepository = paymentVoucherExtendedRepository;
        this.paymentVoucherMapper = paymentVoucherMapper1;
        this.purchaseOrderVoucherRelationExtendedService = purchaseOrderVoucherRelationExtendedService;
        this.purchaseOrderVoucherRelationExtendedRepository = purchaseOrderVoucherRelationExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
    }

    @Override
    public PaymentVoucherDTO save(PaymentVoucherDTO paymentVoucherDTO) {
        if(paymentVoucherDTO.getVoucherNo()==null){
            PaymentVoucherGenerator paymentVoucherGenerator = new PaymentVoucherGenerator();
            paymentVoucherGeneratorRepository.save(paymentVoucherGenerator);
            LocalDate currentDate = LocalDate.now();
            String voucherNo = String.format("%06d", paymentVoucherGenerator.getId());
            paymentVoucherDTO.setVoucherNo(currentDate.getYear()+"BP"+voucherNo);
        }
        paymentVoucherDTO.setPostDate(paymentVoucherDTO.getPostDate()==null?paymentVoucherDTO.getPostDate():LocalDate.now());
        paymentVoucherDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        paymentVoucherDTO.setModifiedOn(LocalDate.now());
        updateTransactions(paymentVoucherDTO);

        paymentVoucherDTO = super.save(paymentVoucherDTO);
        if(paymentVoucherDTO.getApplicationType()!=null && paymentVoucherDTO.getId()==null)
            storeApplicationVoucherRelation(paymentVoucherDTO);
        return paymentVoucherDTO;
    }


    private void storeApplicationVoucherRelation(PaymentVoucherDTO paymentVoucherDTO) {
        if(!requisitionVoucherRelationExtendedRepository.existsByVoucherNo(paymentVoucherDTO.getVoucherNo())){
            if(paymentVoucherDTO.getApplicationType().equals(ApplicationType.REQUISITION)){
                requisitionVoucherRelationService.storeRequisitionVoucherRelation(paymentVoucherDTO.getVoucherNo(),
                    paymentVoucherDTO.getApplicationType(),
                    paymentVoucherDTO.getApplicationId(),
                    paymentVoucherDTO.getId(),
                    "Payment Voucher");
            }
            else if(paymentVoucherDTO.getApplicationType().equals(ApplicationType.PURCHASE_ORDER)){
                purchaseOrderVoucherRelationExtendedService.storePurchaseOrderVoucherRelation(paymentVoucherDTO.getVoucherNo(),
                    paymentVoucherDTO.getApplicationId(),
                    paymentVoucherDTO.getId(),
                    "Payment Voucher");
            }
        }
    }


    public void updateTransactions(PaymentVoucherDTO paymentVoucherDTO){
        DtTransactionCriteria creditVoucherCriteria = new DtTransactionCriteria();

        StringFilter voucherNoFilter = new StringFilter();
        voucherNoFilter.setEquals(paymentVoucherDTO.getVoucherNo());
        LocalDateFilter voucherDateFilter = new LocalDateFilter();
        voucherDateFilter.setEquals(paymentVoucherDTO.getVoucherDate());
        DtTransactionCriteria.BalanceTypeFilter balanceTypeFilter = new DtTransactionCriteria.BalanceTypeFilter();
        balanceTypeFilter.setEquals(BalanceType.CREDIT);

        creditVoucherCriteria.setVoucherNo(voucherNoFilter);
        creditVoucherCriteria.setVoucherDate(voucherDateFilter);
        creditVoucherCriteria.setBalanceType(balanceTypeFilter);
        List<DtTransactionDTO> debitTransactions = dtTransactionQueryService.findByCriteria(creditVoucherCriteria);

        DtTransactionCriteria.BalanceTypeFilter creditBalanceTypeFilter = new DtTransactionCriteria.BalanceTypeFilter();
        creditBalanceTypeFilter.setEquals(BalanceType.DEBIT);
        DtTransactionCriteria debitVoucherCriteria = new DtTransactionCriteria();
        debitVoucherCriteria.setVoucherNo(voucherNoFilter);
        debitVoucherCriteria.setVoucherDate(voucherDateFilter);
        debitVoucherCriteria.setBalanceType(creditBalanceTypeFilter);
        List<DtTransactionDTO> creditTransactions = dtTransactionQueryService.findByCriteria(debitVoucherCriteria);

        BigDecimal totalAmount = creditTransactions.stream()
            .map(t->t.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(debitTransactions.size()==0)
            debitTransactions.add(createCreditTransaction(paymentVoucherDTO));

        debitTransactions.forEach(t->{
            t.setAmount(totalAmount);
        });

        List<DtTransactionDTO> combinedTransactions = new ArrayList<>();
        combinedTransactions.addAll(debitTransactions);
        combinedTransactions.addAll(creditTransactions);

        CurrencyCriteria currencyCriteria = new CurrencyCriteria();
        CurrencyCriteria.CurrencyFlagFilter currencyFlagFilter = new CurrencyCriteria.CurrencyFlagFilter();
        currencyCriteria.equals(CurrencyFlag.BASE);
        currencyCriteria.setFlag(currencyFlagFilter);
        CurrencyDTO baseCurrency = currencyQueryService.findByCriteria(currencyCriteria).get(0);

        combinedTransactions.forEach(t->{
            t.setCurrencyId(baseCurrency.getId());
            t.setfCurrency(BigDecimal.ONE);
            t.setConvFactor(BigDecimal.ONE);
            t.setPostDate(paymentVoucherDTO.getPostDate());
            t.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
            t.setModifiedOn(LocalDate.now());
        });

        List<DtTransaction> dtTransactions = dtTransactionMapper.toEntity(combinedTransactions);
        dtTransactions = dtTransactionRepository.saveAll(dtTransactions);

        if(paymentVoucherDTO.getApplicationType()!=null)
            journalVoucherExtendedService.updateApplicationVoucherRelationAmount(paymentVoucherDTO.getApplicationType(),paymentVoucherDTO.getVoucherNo(), combinedTransactions);

        if(paymentVoucherDTO.getPostDate()!=null){
            dtTransactionExtendedService.updateAccountBalance(dtTransactionMapper.toDto(dtTransactions));
        }
    }




    private DtTransactionDTO createCreditTransaction(PaymentVoucherDTO paymentVoucherDTO){
        DtTransactionDTO debitTransaction = new DtTransactionDTO();
        debitTransaction.setVoucherNo(paymentVoucherDTO.getVoucherNo());
        debitTransaction.setVoucherDate(paymentVoucherDTO.getVoucherDate());
        debitTransaction.setAmount(BigDecimal.ZERO);
        debitTransaction.setBalanceType(BalanceType.CREDIT);
        debitTransaction.setAccountId(paymentVoucherDTO.getAccountId());
        return debitTransaction;
    }


    void createPayrollPaymentVoucherEntry(List<MonthlySalary> monthlySalaries, SalaryVoucherRelation salaryVoucherRelation){
        PaymentVoucherDTO paymentVoucherDTO = new PaymentVoucherDTO();
        Currency baseCurrency = currencyExtendedRepository.findByFlag(CurrencyFlag.BASE);
        SystemAccountMap salaryAccountMap = systemAccountMapExtendedRepository.findByAccountType(AccountType.SALARY_ACCOUNT);
        paymentVoucherDTO.setAccountId(salaryAccountMap.getAccount().getId());
        paymentVoucherDTO.setVoucherDate(LocalDate.now());
        paymentVoucherDTO = save(paymentVoucherDTO);

        salaryVoucherRelation.setVoucherNo(paymentVoucherDTO.getVoucherNo());
        salaryVoucherRelation.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        salaryVoucherRelation.setModifiedOn(LocalDate.now());
        salaryVoucherRelationRepository.save(salaryVoucherRelation);

        BigDecimal totalAmount = monthlySalaries
            .stream()
            .map(s->s.getPayable())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<DtTransaction> voucherTransactions = new ArrayList<>();
        SystemAccountMap salaryPayableAccount = systemAccountMapExtendedRepository.findByAccountType(AccountType.SALARY_PAYABLE);
        DtTransaction salaryPayableTransaction = new DtTransaction();
        salaryPayableTransaction.setAccount(salaryPayableAccount.getAccount());
        salaryPayableTransaction.setCurrency(baseCurrency);
        salaryPayableTransaction.setAmount(totalAmount);
        salaryPayableTransaction.setBalanceType(BalanceType.DEBIT);
        salaryPayableTransaction.setConvFactor(BigDecimal.ONE);
        salaryPayableTransaction.setfCurrency(BigDecimal.ONE);
        salaryPayableTransaction.setNarration("Monthly Salary Payment Voucher");
        salaryPayableTransaction.setVoucherDate(paymentVoucherDTO.getVoucherDate());
        salaryPayableTransaction.setVoucherNo(paymentVoucherDTO.getVoucherNo());
        salaryPayableTransaction.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        salaryPayableTransaction.setModifiedOn(LocalDate.now());
        voucherTransactions.add(salaryPayableTransaction);

        dtTransactionRepository.saveAll(voucherTransactions);
        paymentVoucherDTO.setPostDate(LocalDate.now());
        save(paymentVoucherDTO);
    }


    public Optional<PaymentVoucherDTO> findByVoucherNo(String voucherNo){
        return paymentVoucherExtendedRepository.getByVoucherNo(voucherNo)
            .map(paymentVoucherMapper::toDto);
    }

}
