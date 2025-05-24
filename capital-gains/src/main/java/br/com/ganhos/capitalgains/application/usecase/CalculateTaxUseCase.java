package br.com.ganhos.capitalgains.application.usecase;

import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.domain.model.OperationType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateTaxUseCase {

    public List<Double> calculateTaxes(List<Operation> operations) {
        List<Double> taxes = new ArrayList<>();
        double totalShares = 0;
        double totalCost = 0;
        double lossToCompensate = 0;

        for (Operation op : operations) {
            if (op.getType() == OperationType.BUY) {
                // Atualiza custo total e quantidade
                totalCost += op.getUnitCost() * op.getQuantity();
                totalShares += op.getQuantity();
                taxes.add(0.0);  // não paga imposto na compra
            } else if (op.getType() == OperationType.SELL) {
                // Calcula preço médio
                double avgPrice = totalShares > 0 ? totalCost / totalShares : 0;
                // Calcula lucro da operação
                double profit = op.getQuantity() * (op.getUnitCost() - avgPrice);

                if (op.getQuantity() > totalShares) {
                    // Caso venda maior que ações em carteira - aqui ajusta para não calcular lucro negativo
                    profit = 0;
                }

                // Atualiza total de ações e custo após venda
                totalShares -= op.getQuantity();
                totalCost = avgPrice * totalShares;

                double taxableProfit = profit - lossToCompensate;
                if (taxableProfit < 0) {
                    lossToCompensate = -taxableProfit;
                    taxableProfit = 0;
                } else {
                    lossToCompensate = 0;
                }

                // Isenção para vendas até R$ 20.000
                double saleValue = op.getQuantity() * op.getUnitCost();
                double tax = 0;
                if (saleValue > 20000) {
                    tax = taxableProfit * 0.20;
                }

                taxes.add(round(tax));
            } else {
                // Caso operação desconhecida, assume zero imposto
                taxes.add(0.0);
            }
        }

        return taxes;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
