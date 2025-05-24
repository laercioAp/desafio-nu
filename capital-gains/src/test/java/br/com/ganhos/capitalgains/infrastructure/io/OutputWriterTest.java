package br.com.ganhos.capitalgains.infrastructure.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutputWriterTest {

    @Test
    void shouldWriteTaxesAsJsonToSystemOut() throws Exception {
        // Captura o output do console
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            List<BigDecimal> taxes = List.of(
                    new BigDecimal("0.00"),
                    new BigDecimal("1000.50"),
                    new BigDecimal("250.75")
            );

            OutputWriter.writeTaxes(taxes);

            // O output deve ser um JSON representando a lista de BigDecimal
            String expectedJson = "[0.00,1000.50,250.75]";
            String actualOutput = outContent.toString().trim();

            assertEquals(expectedJson, actualOutput);
        } finally {
            // Restaura o System.out original para não afetar outros testes
            System.setOut(originalOut);
        }
    }
}
