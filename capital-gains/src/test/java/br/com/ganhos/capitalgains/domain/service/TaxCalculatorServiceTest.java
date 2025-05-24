package br.com.ganhos.capitalgains.domain.service;

import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.domain.model.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxCalculatorServiceTest {

    private TaxCalculatorService service;

    @BeforeEach
    void setup() {
        service = new TaxCalculatorService();
    }

    @Test
    void shouldReturnZeroTaxForBuyOperation() {
        List<Operation> operations = List.of(
                new Operation(OperationType.BUY, 10.0, 100)
        );

        List<Double> taxes = service.calculateTaxes(operations);
        assertEquals(List.of(0.0), taxes);
    }

    @Test
    void shouldReturnZeroTaxForSellBelowExemptionLimit() {
        List<Operation> operations = List.of(
                new Operation(OperationType.BUY, 10.0, 100),     // total: 1000
                new Operation(OperationType.SELL, 15.0, 100)     // venda: 1500 (abaixo de 20k) => isento
        );

        List<Double> taxes = service.calculateTaxes(operations);
        assertEquals(List.of(0.0, 0.0), taxes);
    }

    @Test
    void shouldReturnTaxForSellAboveExemptionLimit() {
        List<Operation> operations = List.of(
                new Operation(OperationType.BUY, 100.0, 100),    // total: 10.000
                new Operation(OperationType.SELL, 300.0, 100)    // venda: 30.000 => lucro: 20.000 => imposto: 4.000
        );

        List<Double> taxes = service.calculateTaxes(operations);
        assertEquals(List.of(0.0, 4000.0), taxes);
    }

    @Test
    void shouldAccumulateLossWhenProfitNotEnoughToUseAll() {
        List<Operation> operations = List.of(
                new Operation(OperationType.BUY, 100.0, 100),     // custo: 10.000
                new Operation(OperationType.SELL, 50.0, 100),     // venda: 5.000 => prejuízo: -5.000
                new Operation(OperationType.BUY, 100.0, 100),     // nova compra
                new Operation(OperationType.SELL, 150.0, 100)     // venda: 15.000 => lucro: 5.000 => isento => acumula mais prejuízo
        );

        List<Double> taxes = service.calculateTaxes(operations);
        assertEquals(List.of(0.0, 0.0, 0.0, 0.0), taxes);
    }

}
