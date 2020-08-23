package org.soptorshi.service.extended;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.soptorshi.config.JxlsGenerator;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.extended.DtTransactionExtendedRepository;
import org.soptorshi.repository.extended.MstAccountExtendedRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.soptorshi.repository.extended.SystemGroupMapExtendedRepository;
import org.soptorshi.service.dto.extended.MonthWithProfitAndLossAmountDTO;
import org.soptorshi.service.dto.extended.ProfitAndLossGroupDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CashFlowService {
    private final ResourceLoader resourceLoader;
    private final JxlsGenerator jxlsGenerator;
    private final MstGroupExtendedRepository mstGroupExtendedRepository;
    private final SystemGroupMapExtendedRepository systemGroupMapExtendedRepository;
    private final MstAccountExtendedRepository mstAccountExtendedRepository;
    private final DtTransactionExtendedRepository dtTransactionExtendedRepository;
    private final ProfitLossService profitLossService;


    List<SystemGroupMap> systemGroupMaps;
    Map<GroupType, Long> groupTypeSystemAccountMapMap;
    List<MstAccount> accounts;
    Map<Long, List<MstAccount>> groupMapWithAccounts;

    public CashFlowService(ResourceLoader resourceLoader, JxlsGenerator jxlsGenerator, MstGroupExtendedRepository mstGroupExtendedRepository, SystemGroupMapExtendedRepository systemGroupMapExtendedRepository, MstAccountExtendedRepository mstAccountExtendedRepository, DtTransactionExtendedRepository dtTransactionExtendedRepository, ProfitLossService profitLossService) {
        this.resourceLoader = resourceLoader;
        this.jxlsGenerator = jxlsGenerator;
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.systemGroupMapExtendedRepository = systemGroupMapExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
        this.profitLossService = profitLossService;
    }

    public ByteArrayInputStream createReport(LocalDate fromDate, LocalDate toDate) throws Exception{
        accounts = mstAccountExtendedRepository.findAll();
        systemGroupMaps = systemGroupMapExtendedRepository.findAll();
        groupTypeSystemAccountMapMap = systemGroupMaps.stream().collect(Collectors.toMap(s->s.getGroupType(), s->s.getGroup().getId()));
        groupMapWithAccounts = accounts.stream().collect(Collectors.groupingBy(a->a.getGroup().getId()));

        List<String> months = profitLossService.generateMonths(fromDate, toDate);
        List<ProfitAndLossGroupDTO> assetGroups = profitLossService.generateGroupsAndSubgroups(GroupType.ASSETS, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> liabilityGroups = profitLossService.generateGroupsAndSubgroups(GroupType.LIABILITIES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> equitiesGroups = profitLossService.generateGroupsAndSubgroups(GroupType.EQUITIES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> incomeGroups = profitLossService.generateGroupsAndSubgroups(GroupType.INCOME, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> expenseGroups = profitLossService.generateGroupsAndSubgroups(GroupType.EXPENSES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> depreciationGroups = profitLossService.generateGroupsAndSubgroups(GroupType.DEPRECIATION, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> currentAssetGroups = profitLossService.generateGroupsAndSubgroups(GroupType.CURRENT_ASSETS, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> fixedAssetGroups = profitLossService.generateGroupsAndSubgroups(GroupType.FIXED_ASSETS, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> currentLiabilityGroups = profitLossService.generateGroupsAndSubgroups(GroupType.CURRENT_LIABILITIES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> loanGroups = profitLossService.generateGroupsAndSubgroups(GroupType.LOAN, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> shareCapitalGroups = profitLossService.generateGroupsAndSubgroups(GroupType.SHARE_CAPITAL, groupTypeSystemAccountMapMap, groupMapWithAccounts);




        List<MonthWithProfitAndLossAmountDTO> assetGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.ASSETS, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.LIABILITIES, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> equitiesGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.EQUITIES, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> incomeGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.INCOME, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> expenditureGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.EXPENSES, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> depreciationGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.DEPRECIATION, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> currentAssetGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.CURRENT_ASSETS, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> fixedAssetGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.FIXED_ASSETS, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> currentLiabilityGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.CURRENT_LIABILITIES, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> loanGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.LOAN, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> shareCapitalGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.SHARE_CAPITAL, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);



        List<BigDecimal> differences = profitLossService.calculateDifference(incomeGroupAmount, expenditureGroupAmount);

        Resource resource = resourceLoader.getResource("classpath:/templates/jxls/CashFlow.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(resource.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(barray);
        InputStream template = resource.getInputStream();
        OutputStream outputStream = new ByteArrayOutputStream() ; // new FileOutputStream(outputResource.getFile());
        jxlsGenerator.cashFlowBuilder( months, assetGroups, liabilityGroups, equitiesGroups, incomeGroups, expenseGroups,depreciationGroups, currentAssetGroups, fixedAssetGroups, currentLiabilityGroups, loanGroups,
            shareCapitalGroups, assetGroupAmount, equitiesGroupAmount, liabilityGroupAmount, incomeGroupAmount, expenditureGroupAmount, depreciationGroupAmount, currentAssetGroupAmount, fixedAssetGroupAmount, currentLiabilityGroupAmount, loanGroupAmount, shareCapitalGroupAmount, differences, outputStream, template);
        ByteArrayOutputStream baos =(ByteArrayOutputStream) outputStream; //(ByteArrayOutputStream) outputStream; //new ByteArrayOutputStream();
        byte[] data = baos.toByteArray();
        outputStream.write(data);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public List<BigDecimal> calculateDifference(List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount, List<MonthWithProfitAndLossAmountDTO> equitiesGroiupAmount){
        List<BigDecimal> differences = new ArrayList<>();
        for(int i=0; i<equitiesGroiupAmount.size();i++){
            BigDecimal difference = ( liabilityGroupAmount.get(i).getGroupTypeTotal().add(equitiesGroiupAmount.get(i).getGroupTypeTotal()));
            differences.add(difference);
        }
        return differences;
    }
}
