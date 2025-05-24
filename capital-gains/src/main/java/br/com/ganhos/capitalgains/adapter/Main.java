package br.com.ganhos.capitalgains.adapter;

import br.com.ganhos.capitalgains.application.usecase.CalculateTaxUseCase;
import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.infrastructure.io.InputReader;
import br.com.ganhos.capitalgains.infrastructure.io.OutputWriter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Operation> operations = InputReader.readOperations();
        List<Double> taxes = new CalculateTaxUseCase().execute(operations);
        OutputWriter.writeTaxes(taxes);
    }
}
