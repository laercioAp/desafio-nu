package br.com.ganhos.capitalgains.application.usecase;

import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.domain.model.OperationType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateTaxUseCase {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.20");
    private static final BigDecimal EXEMPTION_LIMIT = new BigDecimal("20000");

    public List<BigDecimal> calculateTaxes(List<Operation> operations) {
        List<BigDecimal> taxes = new ArrayList<>();
        BigDecimal totalShares = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal lossToCompensate = BigDecimal.ZERO;

        for (Operation op : operations) {
            if (op.getType() == OperationType.BUY) {
                BigDecimal quantity = BigDecimal.valueOf(op.getQuantity());
                BigDecimal unitCost = BigDecimal.valueOf(op.getUnitCost());

                totalCost = totalCost.add(unitCost.multiply(quantity));
                totalShares = totalShares.add(quantity);

                taxes.add(BigDecimal.ZERO.setScale(2));
            } else if (op.getType() == OperationType.SELL) {
                BigDecimal quantity = BigDecimal.valueOf(op.getQuantity());
                BigDecimal unitCost = BigDecimal.valueOf(op.getUnitCost());

                if (quantity.compareTo(totalShares) > 0) {
                    throw new IllegalArgumentException("Venda maior que quantidade em carteira");
                }

                BigDecimal avgPrice = totalShares.compareTo(BigDecimal.ZERO) > 0
                        ? totalCost.divide(totalShares, 10, RoundingMode.HALF_UP)
                        : BigDecimal.ZERO;

                BigDecimal profit = quantity.multiply(unitCost.subtract(avgPrice));

                // Atualiza total de ações e custo após venda
                totalShares = totalShares.subtract(quantity);
                totalCost = avgPrice.multiply(totalShares);

                BigDecimal taxableProfit = profit.subtract(lossToCompensate);
                if (taxableProfit.compareTo(BigDecimal.ZERO) < 0) {
                    lossToCompensate = taxableProfit.abs();
                    taxableProfit = BigDecimal.ZERO;
                } else {
                    lossToCompensate = BigDecimal.ZERO;
                }

                BigDecimal saleValue = quantity.multiply(unitCost);
                BigDecimal tax = BigDecimal.ZERO;

                if (saleValue.compareTo(EXEMPTION_LIMIT) > 0) {
                    tax = taxableProfit.multiply(TAX_RATE);
                }

                taxes.add(round(tax));
            } else {
                taxes.add(BigDecimal.ZERO.setScale(2));
            }
        }

        return taxes;
    }

    private BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
