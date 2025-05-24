package br.com.ganhos.capitalgains.adapter;

import br.com.ganhos.capitalgains.application.usecase.CalculateTaxUseCase;
import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.infrastructure.io.InputReader;
import br.com.ganhos.capitalgains.infrastructure.io.OutputWriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MainTest {

    @Test
    void runApp_shouldProcessInputAndWriteOutput() throws Exception {
        // Arrange
        CalculateTaxUseCase useCaseMock = mock(CalculateTaxUseCase.class);
        List<Operation> fakeOperations = List.of(new Operation()); // use dados reais conforme seu modelo
        List<BigDecimal> fakeTaxes = List.of(BigDecimal.valueOf(10.00), BigDecimal.ZERO);

        // Mock estático para InputReader e OutputWriter
        try (
                MockedStatic<InputReader> inputReaderMock = mockStatic(InputReader.class);
                MockedStatic<OutputWriter> outputWriterMock = mockStatic(OutputWriter.class)
        ) {
            inputReaderMock.when(() -> InputReader.readOperationsFromResource("input.json"))
                    .thenReturn(fakeOperations);

            when(useCaseMock.calculateTaxes(fakeOperations)).thenReturn(fakeTaxes);

            CommandLineRunner runner = new Main().runApp(useCaseMock);

            // Act
            runner.run(new String[]{});

            // Assert
            inputReaderMock.verify(() -> InputReader.readOperationsFromResource("input.json"), times(1));
            verify(useCaseMock, times(1)).calculateTaxes(fakeOperations);
            outputWriterMock.verify(() -> OutputWriter.writeTaxes(fakeTaxes), times(1));
        }
    }
}
