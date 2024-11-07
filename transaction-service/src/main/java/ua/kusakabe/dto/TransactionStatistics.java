package ua.kusakabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class TransactionStatistics {

    private double monthTotalIncome;
    private double monthTotalSpending;
    private double lastMonthTotalIncome;
    private double lastMonthTotalSpending;

}
