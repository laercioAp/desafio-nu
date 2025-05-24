package br.com.ganhos.capitalgains.adapter;

import br.com.ganhos.capitalgains.application.usecase.CalculateTaxUseCase;
import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.infrastructure.io.InputReader;
import br.com.ganhos.capitalgains.infrastructure.io.OutputWriter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.ganhos.capitalgains")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner runApp(CalculateTaxUseCase calculateTaxUseCase) {
        return args -> {
            try {
                // Lê o JSON do arquivo input.json em src/main/resources
                List<Operation> operations = InputReader.readOperationsFromResource("input.json");

                // Calcula os impostos
                List<BigDecimal> taxes = calculateTaxUseCase.calculateTaxes(operations);

                System.out.println("Resultado dos impostos calculados:");
                OutputWriter.writeTaxes(taxes);

            } catch (Exception e) {
                System.err.println("Erro ao processar operações: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
