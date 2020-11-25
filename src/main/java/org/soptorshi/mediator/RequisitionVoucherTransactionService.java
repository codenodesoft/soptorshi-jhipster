package org.soptorshi.mediator;

import org.soptorshi.domain.Currency;
import org.soptorshi.domain.PurchaseOrderVoucherRelation;
import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.RequisitionVoucherRelation;
import org.soptorshi.domain.enumeration.*;
import org.soptorshi.repository.PurchaseOrderRepository;
import org.soptorshi.repository.extended.*;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.dto.PaymentVoucherDTO;
import org.soptorshi.service.dto.RequisitionDTO;
import org.soptorshi.service.extended.PaymentVoucherExtendedService;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequisitionVoucherTransactionService {
    private final RequisitionExtendedRepository requisitionExtendedRepository;
    private final RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository;
    private final PurchaseOrderVoucherRelationExtendedRepository purchaseOrderVoucherRelationExtendedRepository;
    private final SystemAccountMapExtendedRepository systemAccountMapExtendedRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PaymentVoucherExtendedService paymentVoucherExtendedService;
    private final CurrencyExtendedRepository currencyExtendedRepository;
    private final QuotationExtendedRepository quotationExtendedRepository;
    private final DtTransactionMapper dtTransactionMapper;
    private final DtTransactionExtendedRepository dtTransactionExtendedRepository;

    public RequisitionVoucherTransactionService(RequisitionExtendedRepository requisitionExtendedRepository, RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository, PurchaseOrderVoucherRelationExtendedRepository purchaseOrderVoucherRelationExtendedRepository, SystemAccountMapExtendedRepository systemAccountMapExtendedRepository, PurchaseOrderRepository purchaseOrderRepository, PaymentVoucherExtendedService paymentVoucherExtendedService, CurrencyExtendedRepository currencyExtendedRepository, QuotationExtendedRepository quotationExtendedRepository, DtTransactionMapper dtTransactionMapper, DtTransactionExtendedRepository dtTransactionExtendedRepository) {
        this.requisitionExtendedRepository = requisitionExtendedRepository;
        this.requisitionVoucherRelationExtendedRepository = requisitionVoucherRelationExtendedRepository;
        this.purchaseOrderVoucherRelationExtendedRepository = purchaseOrderVoucherRelationExtendedRepository;
        this.systemAccountMapExtendedRepository = systemAccountMapExtendedRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.paymentVoucherExtendedService = paymentVoucherExtendedService;
        this.currencyExtendedRepository = currencyExtendedRepository;
        this.quotationExtendedRepository = quotationExtendedRepository;
        this.dtTransactionMapper = dtTransactionMapper;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
    }

    public void createPaymentVoucher(RequisitionDTO requisitionDTO) {

        BigDecimal alreadyPaidAmount = purchaseOrderVoucherRelationExtendedRepository.findByPurchaseOrder_Requisition_Id(requisitionDTO.getId())
            .stream()
            .filter(v-> v.getVoucherNo().contains("BP")) // fetching already paid vouchers so that it can be excluded
            .map(v-> v.getAmount())
            .reduce(BigDecimal::add)
            .get();

        PaymentVoucherDTO paymentVoucherDTO = generatePaymentVoucher(requisitionDTO);
        createTransactions(requisitionDTO, paymentVoucherDTO, alreadyPaidAmount);
        paymentVoucherDTO.setPostDate(LocalDate.now());
        paymentVoucherExtendedService.save(paymentVoucherDTO);
    }

    private PaymentVoucherDTO generatePaymentVoucher(RequisitionDTO requisitionDTO){
        PaymentVoucherDTO paymentVoucherDTO = new PaymentVoucherDTO();
        paymentVoucherDTO.setAccountId(systemAccountMapExtendedRepository.findByAccountType(AccountType.SALARY_PAYABLE).getAccount().getId());
        paymentVoucherDTO.setVoucherDate(LocalDate.now());
        paymentVoucherDTO.setApplicationId(purchaseOrderRepository.findByRequisition_Id(requisitionDTO.getId()).getId());
        paymentVoucherDTO.setApplicationType(ApplicationType.PURCHASE_ORDER);
        paymentVoucherDTO = paymentVoucherExtendedService.save(paymentVoucherDTO);
        return paymentVoucherDTO;
    }

    private void createTransactions(RequisitionDTO requisitionDTO, PaymentVoucherDTO paymentVoucherDTO, BigDecimal alreadyPaidAmount){
        Currency activeCurrency = currencyExtendedRepository.findByFlag(CurrencyFlag.BASE);
        Quotation selectedQuotation = quotationExtendedRepository.findByRequisition_IdAndAndSelectionStatus(requisitionDTO.getId(), SelectionType.SELECTED).get();

        DtTransactionDTO creditTransaction = new DtTransactionDTO();
        creditTransaction.setVoucherNo(paymentVoucherDTO.getVoucherNo());
        creditTransaction.setVoucherDate(paymentVoucherDTO.getVoucherDate());
        creditTransaction.setVoucherId(paymentVoucherDTO.getId());
        creditTransaction.setBalanceType(BalanceType.CREDIT);
        creditTransaction.setAmount(requisitionDTO.getAmount().subtract(alreadyPaidAmount));
        creditTransaction.setAccountId(systemAccountMapExtendedRepository.findByAccountType(AccountType.SALARY_PAYABLE).getAccount().getId());
        creditTransaction.setConvFactor(BigDecimal.ONE);
        creditTransaction.setCurrencyId(activeCurrency.getId());
        creditTransaction.setCurrencyNotation("BDT");
        creditTransaction.setSerialNo(1);
        creditTransaction.setVoucherName("PAYMENT VOUCHER");
        dtTransactionExtendedRepository.saveAndFlush(dtTransactionMapper.toEntity(creditTransaction));

        DtTransactionDTO debitTransaction = new DtTransactionDTO();
        debitTransaction.setVoucherNo(paymentVoucherDTO.getVoucherNo());
        debitTransaction.setVoucherId(paymentVoucherDTO.getId());
        debitTransaction.setVoucherDate(paymentVoucherDTO.getVoucherDate());
        debitTransaction.setVoucherName("PAYMENT VOUCHER");
        debitTransaction.setBalanceType(BalanceType.DEBIT);
        debitTransaction.setAmount(requisitionDTO.getAmount().subtract(alreadyPaidAmount));
        debitTransaction.setAccountId(selectedQuotation.getVendor().getAccount().getId());
        debitTransaction.setConvFactor(BigDecimal.ONE);
        debitTransaction.setCurrencyId(activeCurrency.getId());
        debitTransaction.setCurrencyNotation("BDT");
        debitTransaction.setSerialNo(2);
        debitTransaction.setVoucherName("PAYMENT VOUCHER");
        dtTransactionExtendedRepository.saveAndFlush(dtTransactionMapper.toEntity(debitTransaction));
    }
}
