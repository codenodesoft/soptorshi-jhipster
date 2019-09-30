import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatTabsModule } from '@angular/material';
import { VendorContactPersonExtendedUpdateComponent } from 'app/entities/vendor-contact-person-extended';
import { requisitionExtendedPopupRoute, requisitionExtendedRoute } from 'app/entities/requisition-extended';
import { requisitionDetailsExtendedPopupRoute, requisitionDetailsExtendedRoute } from 'app/entities/requisition-details-extended';

@NgModule({
    imports: [
        MatTabsModule,
        RouterModule.forChild([
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'designation',
                loadChildren: './designation/designation.module#SoptorshiDesignationModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'attachment',
                loadChildren: './attachment/attachment.module#SoptorshiAttachmentModule'
            },
            {
                path: 'academic-information',
                loadChildren: './academic-information/academic-information.module#SoptorshiAcademicInformationModule'
            },
            {
                path: 'training-information',
                loadChildren: './training-information/training-information.module#SoptorshiTrainingInformationModule'
            },
            {
                path: 'family-information',
                loadChildren: './family-information/family-information.module#SoptorshiFamilyInformationModule'
            },
            {
                path: 'reference-information',
                loadChildren: './reference-information/reference-information.module#SoptorshiReferenceInformationModule'
            },
            {
                path: 'experience-information',
                loadChildren: './experience-information/experience-information.module#SoptorshiExperienceInformationModule'
            },
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'designation',
                loadChildren: './designation/designation.module#SoptorshiDesignationModule'
            },
            {
                path: 'attachment',
                loadChildren: './attachment/attachment.module#SoptorshiAttachmentModule'
            },

            {
                path: 'training-information',
                loadChildren: './training-information/training-information.module#SoptorshiTrainingInformationModule'
            },
            {
                path: 'family-information',
                loadChildren: './family-information/family-information.module#SoptorshiFamilyInformationModule'
            },
            {
                path: 'reference-information',
                loadChildren: './reference-information/reference-information.module#SoptorshiReferenceInformationModule'
            },
            {
                path: 'experience-information',
                loadChildren: './experience-information/experience-information.module#SoptorshiExperienceInformationModule'
            },
            {
                path: 'attachment',
                loadChildren: './attachment/attachment.module#SoptorshiAttachmentModule'
            },
            {
                path: 'academic-information-attachment',
                loadChildren:
                    './academic-information-attachment/academic-information-attachment.module#SoptorshiAcademicInformationAttachmentModule'
            },
            {
                path: 'experience-information-attachment',
                loadChildren:
                    './experience-information-attachment/experience-information-attachment.module#SoptorshiExperienceInformationAttachmentModule'
            },
            {
                path: 'training-information-attachment',
                loadChildren:
                    './training-information-attachment/training-information-attachment.module#SoptorshiTrainingInformationAttachmentModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },

            {
                path: 'training-information',
                loadChildren: './training-information/training-information.module#SoptorshiTrainingInformationModule'
            },
            {
                path: 'experience-information',
                loadChildren: './experience-information/experience-information.module#SoptorshiExperienceInformationModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'office',
                loadChildren: './office/office.module#SoptorshiOfficeModule'
            },
            {
                path: 'financial-account-year',
                loadChildren:
                    './financial-account-year-extended/financial-account-year-extended.module#SoptorshiFinancialAccountYearExtendedModule'
            },
            {
                path: 'tax',
                loadChildren: './tax/tax.module#SoptorshiTaxModule'
            },
            {
                path: 'salary',
                loadChildren: './salary/salary.module#SoptorshiSalaryModule'
            },
            {
                path: 'provident-fund',
                loadChildren: './provident-fund/provident-fund.module#SoptorshiProvidentFundModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary-extended/monthly-salary-extended.module#SoptorshiMonthlySalaryExtendedModule'
            },
            {
                path: 'advance',
                loadChildren: './advance/advance.module#SoptorshiAdvanceModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'loan',
                loadChildren: './loan/loan.module#SoptorshiLoanModule'
            },
            {
                path: 'holiday-type',
                loadChildren: './holiday-type/holiday-type.module#SoptorshiHolidayTypeModule'
            },
            {
                path: 'holiday',
                loadChildren: './holiday/holiday.module#SoptorshiHolidayModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'tax',
                loadChildren: './tax/tax.module#SoptorshiTaxModule'
            },
            {
                path: 'salary',
                loadChildren: './salary/salary.module#SoptorshiSalaryModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'payroll-management',
                loadChildren: './payroll-management/payroll-management.module#SoptorshiPayrollManagementModule'
            },
            {
                path: 'tax',
                loadChildren: './tax/tax.module#SoptorshiTaxModule'
            },
            {
                path: 'designation-wise-allowance',
                loadChildren:
                    './designation-wise-allowance-extended/designation-wise-allowance-extended.module#SoptorshiDesignationWiseAllowanceExtendedModule'
            },
            {
                path: 'provident-fund',
                loadChildren: './provident-fund/provident-fund.module#SoptorshiProvidentFundModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'advance',
                loadChildren: './advance/advance.module#SoptorshiAdvanceModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'loan',
                loadChildren: './loan/loan.module#SoptorshiLoanModule'
            },
            {
                path: 'fine-management',
                loadChildren: './fine-management/fine-management.module#SoptorshiFineManagementModule'
            },
            {
                path: 'advance-management',
                loadChildren: './advance-management/advance-management.module#SoptorshiAdvanceManagementModule'
            },
            {
                path: 'loan-management',
                loadChildren: './loan-management/loan-management.module#SoptorshiLoanManagementModule'
            },
            {
                path: 'provident-management',
                loadChildren: './provident-management/provident-management.module#SoptorshiProvidentManagementModule'
            },
            {
                path: 'allowance-management',
                loadChildren: './allowance-management/allowance-management.module#SoptorshiAllowanceManagementModule'
            },
            {
                path: 'salary',
                loadChildren: './salary/salary.module#SoptorshiSalaryModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'advance',
                loadChildren: './advance/advance.module#SoptorshiAdvanceModule'
            },
            {
                path: 'fine',
                loadChildren: './fine/fine.module#SoptorshiFineModule'
            },
            {
                path: 'loan',
                loadChildren: './loan/loan.module#SoptorshiLoanModule'
            },
            {
                path: 'fine-advance-loan-management',
                loadChildren: './fine-advance-loan-management/fine-advance-loan-management.module#SoptorshiFineAdvanceLoanManagementModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'manager',
                loadChildren: './manager/manager.module#SoptorshiManagerModule'
            },
            {
                path: 'manager',
                loadChildren: './manager/manager.module#SoptorshiManagerModule'
            },
            {
                path: 'provident-fund',
                loadChildren: './provident-fund/provident-fund.module#SoptorshiProvidentFundModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'special-allowance-time-line',
                loadChildren: './special-allowance-time-line/special-allowance-time-line.module#SoptorshiSpecialAllowanceTimeLineModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'provident-fund',
                loadChildren: './provident-fund/provident-fund.module#SoptorshiProvidentFundModule'
            },
            {
                path: 'advance',
                loadChildren: './advance/advance.module#SoptorshiAdvanceModule'
            },
            {
                path: 'leave-type',
                loadChildren: './leave-type/leave-type.module#SoptorshiLeaveTypeModule'
            },
            {
                path: 'leave-application',
                loadChildren: './leave-application/leave-application.module#SoptorshiLeaveApplicationModule'
            },
            {
                path: 'leave-attachment',
                loadChildren: './leave-attachment/leave-attachment.module#SoptorshiLeaveAttachmentModule'
            },
            {
                path: 'leave-balance',
                loadChildren: './leave-balance/leave-balance.module#SoptorshiLeaveBalanceModule'
            },
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'purchase-committee',
                loadChildren: './purchase-committee-extended/purchase-committee-extended.module#SoptorshiPurchaseCommitteeExtendedModule'
            },
            {
                path: 'vendor',
                loadChildren: './vendor-extended/vendor-extended.module#SoptorshiVendorExtendedModule'
            },
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'bill',
                loadChildren: './bill/bill.module#SoptorshiBillModule'
            },
            {
                path: 'tax',
                loadChildren: './tax/tax.module#SoptorshiTaxModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'monthly-salary',
                loadChildren: './monthly-salary/monthly-salary.module#SoptorshiMonthlySalaryModule'
            },
            {
                path: 'department-head',
                loadChildren: './department-head-extended/department-head-extended.module#SoptorshiDepartmentHeadExtendedModule'
            },
            {
                path: 'budget-allocation',
                loadChildren: './budget-allocation-extended/budget-allocation-extended.module#SoptorshiBudgetAllocationExtendedModule'
            },
            {
                path: 'financial-account-year',
                loadChildren: './financial-account-year/financial-account-year.module#SoptorshiFinancialAccountYearModule'
            },
            {
                path: 'product-category',
                loadChildren: './product-category-extended/product-category-extended.module#SoptorshiProductCategoryExtendedModule'
            },
            {
                path: 'product',
                loadChildren: './product-extended/product-extended.module#SoptorshiProductExtendedModule'
            },
            {
                path: 'product-price',
                loadChildren: './product-price/product-price.module#SoptorshiProductPriceModule'
            },
            {
                path: 'requisition',
                loadChildren: './requisition-extended/requisition-extended.module#SoptorshiRequisitionExtendedModule'
            },
            {
                path: 'requisition-details',
                loadChildren: './requisition-details-extended/requisition-details-extended.module#SoptorshiRequisitionDetailsExtendedModule'
            },
            {
                path: 'quotation',
                loadChildren: './quotation-extended/quotation-extended.module#SoptorshiQuotationExtendedModule'
            },
            {
                path: 'quotation-details',
                loadChildren: './quotation-details-extended/quotation-details-extended.module#SoptorshiQuotationDetailsExtendedModule'
            },
            {
                path: 'work-order',
                loadChildren: './work-order/work-order.module#SoptorshiWorkOrderModule'
            },
            {
                path: 'terms-and-conditions',
                loadChildren: './terms-and-conditions-extended/terms-and-conditions-extended.module#SoptorshiTermsAndConditionsModule'
            },

            {
                path: 'vendor-contact-person',
                loadChildren:
                    './vendor-contact-person-extended/vendor-contact-person-extended.module#SoptorshiVendorContactPersonExtendedModule'
            },
            {
                path: 'purchase-order',
                loadChildren: './purchase-order-extended/purchase-order-extended.module#SoptorshiPurchaseOrderExtendedModule'
            },

            {
                path: 'attendance-excel-upload',
                loadChildren: './attendance-excel-upload/attendance-excel-upload.module#SoptorshiAttendanceExcelUploadModule'
            },
            {
                path: 'attendance',
                loadChildren: './attendance/attendance.module#SoptorshiAttendanceModule'
            },
            {
                path: 'manufacturer',
                loadChildren: './manufacturer/manufacturer.module#SoptorshiManufacturerModule'
            },
            {
                path: 'item-category',
                loadChildren: './item-category/item-category.module#SoptorshiItemCategoryModule'
            },
            {
                path: 'item-sub-category',
                loadChildren: './item-sub-category/item-sub-category.module#SoptorshiItemSubCategoryModule'
            },
            {
                path: 'inventory-location',
                loadChildren: './inventory-location/inventory-location.module#SoptorshiInventoryLocationModule'
            },
            {
                path: 'inventory-sub-location',
                loadChildren: './inventory-sub-location/inventory-sub-location.module#SoptorshiInventorySubLocationModule'
            },
            {
                path: 'stock-in-process',
                loadChildren: './stock-in-process/stock-in-process.module#SoptorshiStockInProcessModule'
            },
            {
                path: 'stock-in-item',
                loadChildren: './stock-in-item/stock-in-item.module#SoptorshiStockInItemModule'
            },
            {
                path: 'stock-out-item',
                loadChildren: './stock-out-item/stock-out-item.module#SoptorshiStockOutItemModule'
            },
            {
                path: 'stock-status',
                loadChildren: './stock-status/stock-status.module#SoptorshiStockStatusModule'
            },
            {
                path: 'stock-status',
                loadChildren: './stock-status/stock-status.module#SoptorshiStockStatusModule'
            },
            {
                path: 'stock-status',
                loadChildren: './stock-status/stock-status.module#SoptorshiStockStatusModule'
            },
            {
                path: 'stock-status',
                loadChildren: './stock-status/stock-status.module#SoptorshiStockStatusModule'
            },
            {
                path: 'stock-status',
                loadChildren: './stock-status/stock-status.module#SoptorshiStockStatusModule'
            },
            {
                path: 'stock-status',
                loadChildren: './stock-status/stock-status.module#SoptorshiStockStatusModule'
            },
            {
                path: 'inventory-sub-location',
                loadChildren: './inventory-sub-location/inventory-sub-location.module#SoptorshiInventorySubLocationModule'
            },
            {
                path: 'stock-in-process',
                loadChildren: './stock-in-process/stock-in-process.module#SoptorshiStockInProcessModule'
            },
            {
                path: 'stock-in-item',
                loadChildren: './stock-in-item/stock-in-item.module#SoptorshiStockInItemModule'
            },
            {
                path: 'stock-status',
                loadChildren: './stock-status/stock-status.module#SoptorshiStockStatusModule'
            },
            {
                path: 'stock-out-item',
                loadChildren: './stock-out-item/stock-out-item.module#SoptorshiStockOutItemModule'
            },
            {
                path: 'vendor',
                loadChildren: './vendor/vendor.module#SoptorshiVendorModule'
            },
            {
                path: 'voucher',
                loadChildren: './voucher/voucher.module#SoptorshiVoucherModule'
            },
            {
                path: 'voucher-number-control',
                loadChildren: './voucher-number-control/voucher-number-control.module#SoptorshiVoucherNumberControlModule'
            },
            {
                path: 'period-close',
                loadChildren: './period-close/period-close.module#SoptorshiPeriodCloseModule'
            },
            {
                path: 'currency',
                loadChildren: './currency/currency.module#SoptorshiCurrencyModule'
            },
            {
                path: 'mst-group',
                loadChildren: './mst-group-extended/mst-group-extended.module#SoptorshiMstGroupModule'
            },
            {
                path: 'mst-account',
                loadChildren: './mst-account/mst-account.module#SoptorshiMstAccountModule'
            },
            {
                path: 'account-balance',
                loadChildren: './account-balance/account-balance.module#SoptorshiAccountBalanceModule'
            },
            {
                path: 'monthly-balance',
                loadChildren: './monthly-balance/monthly-balance.module#SoptorshiMonthlyBalanceModule'
            },
            {
                path: 'predefined-narration',
                loadChildren: './predefined-narration/predefined-narration.module#SoptorshiPredefinedNarrationModule'
            },
            {
                path: 'conversion-factor',
                loadChildren: './conversion-factor/conversion-factor.module#SoptorshiConversionFactorModule'
            },
            {
                path: 'cheque-register',
                loadChildren: './cheque-register/cheque-register.module#SoptorshiChequeRegisterModule'
            },
            {
                path: 'dt-transaction',
                loadChildren: './dt-transaction/dt-transaction.module#SoptorshiDtTransactionModule'
            },
            {
                path: 'customer',
                loadChildren: './customer/customer.module#SoptorshiCustomerModule'
            },
            {
                path: 'creditor-ledger',
                loadChildren: './creditor-ledger/creditor-ledger.module#SoptorshiCreditorLedgerModule'
            },
            {
                path: 'debtor-ledger',
                loadChildren: './debtor-ledger/debtor-ledger.module#SoptorshiDebtorLedgerModule'
            },
            {
                path: 'system-account-map',
                loadChildren: './system-account-map/system-account-map.module#SoptorshiSystemAccountMapModule'
            },
            {
                path: 'system-group-map',
                loadChildren: './system-group-map/system-group-map.module#SoptorshiSystemGroupMapModule'
            },
            {
                path: 'academic-information-attachment',
                loadChildren:
                    './academic-information-attachment/academic-information-attachment.module#SoptorshiAcademicInformationAttachmentModule'
            },
            {
                path: 'experience-information-attachment',
                loadChildren:
                    './experience-information-attachment/experience-information-attachment.module#SoptorshiExperienceInformationAttachmentModule'
            },
            {
                path: 'training-information-attachment',
                loadChildren:
                    './training-information-attachment/training-information-attachment.module#SoptorshiTrainingInformationAttachmentModule'
            },
            {
                path: 'mst-account',
                loadChildren: './mst-account/mst-account.module#SoptorshiMstAccountModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiEntityModule {}
