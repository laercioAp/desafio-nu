package br.com.ganhos.capitalgains.domain.service;

import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.domain.model.OperationType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaxCalculatorService {

    // Essas variáveis mantêm o estado do cálculo para toda a lista de operações,
    // se você quer calcular separado para várias listas, faça o reset antes de cada cálculo.
    private double averagePrice = 0.0;
    private int totalQuantity = 0;
    private double accumulatedLoss = 0.0;

    public List<Double> calculateTaxes(List<Operation> operations) {
        List<Double> taxes = new ArrayList<>();

        for (Operation op : operations) {
            if (op.getType() == OperationType.BUY) {
                double totalCost = averagePrice * totalQuantity + op.getUnitCost() * op.getQuantity();
                totalQuantity += op.getQuantity();
                averagePrice = totalCost / totalQuantity;
                taxes.add(0.0);  // Compras não geram imposto
            } else if (op.getType() == OperationType.SELL) {
                double sellTotal = op.getUnitCost() * op.getQuantity();
                double costBasis = averagePrice * op.getQuantity();
                double profit = sellTotal - costBasis;

                double tax = 0.0;

                if (sellTotal > 20000.0) { // regra isenção venda <= 20k
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
            } else {
                // caso apareça operação desconhecida, adicione 0 de imposto por segurança
                taxes.add(0.0);
            }
        }

        return taxes;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
