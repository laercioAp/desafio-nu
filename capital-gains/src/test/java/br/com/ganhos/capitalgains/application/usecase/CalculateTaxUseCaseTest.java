package br.com.ganhos.capitalgains.application.usecase;

import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.domain.model.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateTaxUseCaseTest {

    private CalculateTaxUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CalculateTaxUseCase();
    }

    @Test
    void shouldReturnZeroTaxForBuyOperation() {
        Operation buy = new Operation(OperationType.BUY, 10.0, (int) 100.0);
        List<BigDecimal> taxes = useCase.calculateTaxes(List.of(buy));
        assertEquals(List.of(BigDecimal.ZERO.setScale(2)), taxes);
    }

    @Test
    void shouldReturnZeroTaxForSellBelowExemptionLimit() {
        List<Operation> operations = List.of(
                new Operation(OperationType.BUY, 10.0, 100), // compra: 100 x 10 = 1000
                new Operation(OperationType.SELL, 15.0, 100) // venda: 100 x 15 = 1500 (< 20000) => isento
        );

        List<BigDecimal> taxes = useCase.calculateTaxes(operations);
        assertEquals(List.of(
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2)
        ), taxes);
    }


    @Test
    void shouldReturnCorrectTaxForSellAboveExemptionLimit() {
        List<Operation> operations = List.of(
                new Operation(OperationType.BUY, 100.0, 100),   // compra: 100 ações a R$100 = R$10.000
                new Operation(OperationType.SELL, 300.0, 100)   // venda: 100 ações a R$300 = R$30.000 => lucro R$20.000
        );

        List<BigDecimal> taxes = useCase.calculateTaxes(operations);

        assertEquals(List.of(
                BigDecimal.ZERO.setScale(2),
                new BigDecimal("4000.00") // 20% sobre lucro de R$20.000
        ), taxes);
    }



    @Test
    void shouldCompensateLossInNextSell() {
        List<Operation> operations = List.of(
                new Operation(OperationType.BUY, 100.0, 100),   // custo total: 100 x 100 = 10.000
                new Operation(OperationType.SELL, 50.0, 100),   // venda total: 100 x 50 = 5.000 => prejuízo: -5.000
                new Operation(OperationType.BUY, 100.0, 100),   // nova compra: 100 x 100 = 10.000
                new Operation(OperationType.SELL, 150.0, 100)   // venda: 100 x 150 = 15.000 => lucro: 5.000 (compensa prejuízo anterior)
        );

        List<BigDecimal> taxes = useCase.calculateTaxes(operations);

        assertEquals(List.of(
                BigDecimal.ZERO.setScale(2),   // compra
                BigDecimal.ZERO.setScale(2),   // prejuízo, sem imposto
                BigDecimal.ZERO.setScale(2),   // compra
                BigDecimal.ZERO.setScale(2)    // lucro compensado, ainda sem imposto
        ), taxes);
    }


    @Test
    void shouldThrowExceptionIfSellingMoreThanOwned() {
        List<Operation> operations = List.of(
                new Operation(OperationType.BUY, 10.0, (int) 100.0),
                new Operation(OperationType.SELL, 20.0, (int) 150.0)
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.calculateTaxes(operations);
        });

        assertEquals("Venda maior que quantidade em carteira", exception.getMessage());
    }
}
