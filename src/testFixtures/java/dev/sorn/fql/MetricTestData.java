package dev.sorn.fql;

import dev.sorn.fql.api.Metric;
import java.util.concurrent.ThreadLocalRandom;

public interface MetricTestData {
    default Metric aRandomMetric() {
        String[] metrics = {
            // Income Statement
            "REVENUE",
            "COST_OF_GOODS_SOLD",
            "GROSS_PROFIT",
            "OPERATING_EXPENSES",
            "OPERATING_INCOME",
            "INTEREST_EXPENSE",
            "INCOME_BEFORE_TAX",
            "INCOME_TAX_EXPENSE",
            "NET_INCOME",
            "EBITDA",
            "DIVIDENDS_PER_SHARE",

            // Balance Sheet
            "TOTAL_ASSETS",
            "CURRENT_ASSETS",
            "CASH_AND_CASH_EQUIVALENTS",
            "ACCOUNTS_RECEIVABLE",
            "ACCOUNTS_PAYABLE",
            "INVENTORY",
            "TOTAL_LIABILITIES",
            "CURRENT_LIABILITIES",
            "TOTAL_DEBT",
            "SHAREHOLDERS_EQUITY",
            "RETAINED_EARNINGS",
            "DEBT_TO_EQUITY",

            // Cash Flow Statement
            "CASH_FLOW_FROM_OPERATING_ACTIVITIES",
            "CASH_FLOW_FROM_INVESTING_ACTIVITIES",
            "CASH_FLOW_FROM_FINANCING_ACTIVITIES",
            "FCF",
            "CAPEX",
        };
        String chosen = metrics[ThreadLocalRandom.current().nextInt(metrics.length)];
        return () -> chosen;
    }
}