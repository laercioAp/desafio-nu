package br.com.ganhos.capitalgains.domain.service;

import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.domain.model.OperationType;

import java.util.ArrayList;
import java.util.List;

public class TaxCalculatorService {

    private double averagePrice = 0.0;
    private int totalQuantity = 0;
    private double accumulatedLoss = 0.0;

    public List<Double> calculateTaxes(List<Operation> operations) {
        List<Double> taxes = new ArrayList<>();

        for (Operation op : operations) {
            if (op.getOperation() == OperationType.buy) {
                double totalCost = averagePrice * totalQuantity + op.getUnitCost() * op.getQuantity();
                totalQuantity += op.getQuantity();
                averagePrice = totalCost / totalQuantity;
                taxes.add(0.0);
            } else {
                double sellTotal = op.getUnitCost() * op.getQuantity();
                double costBasis = averagePrice * op.getQuantity();
                double profit = sellTotal - costBasis;

                double tax = 0.0;
                if (sellTotal > 20000.0) {
                    double netProfit = profit - accumulatedLoss;
                    if (netProfit > 0) {
                        tax = netProfit * 0.20;
                        accumulatedLoss = 0.0;
                    } else {
                        accumulatedLoss = Math.abs(netProfit);
                    }
                } else {
                    accumulatedLoss = Math.max(accumulatedLoss - profit, 0);
                }

                totalQuantity -= op.getQuantity();
                taxes.add(Math.max(0.0, round(tax)));
            }
        }

        return taxes;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
