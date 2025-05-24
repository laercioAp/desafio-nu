package br.com.ganhos.capitalgains.infrastructure.io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class OutputWriter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeTaxes(List<Double> taxes) throws Exception {
        System.out.println(mapper.writeValueAsString(taxes));
    }
}
