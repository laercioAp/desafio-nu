package br.com.ganhos.capitalgains.infrastructure.io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputWriter {

    public static void writeTaxes(List<Double> taxes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Double tax : taxes) {
                Map<String, String> output = new HashMap<>();
                output.put("tax", String.format("%.2f", tax));
                System.out.println(mapper.writeValueAsString(output));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao escrever saída", e);
        }
    }
}
