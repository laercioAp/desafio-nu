package br.com.ganhos.capitalgains.application.usecase;

import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.domain.service.TaxCalculatorService;

import java.util.List;

public class CalculateTaxUseCase {

    public List<Double> execute(List<Operation> operations) {
        return new TaxCalculatorService().calculateTaxes(operations);
    }
}
