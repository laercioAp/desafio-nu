package br.com.ganhos.capitalgains.infrastructure.io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

public class OutputWriter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeTaxes(List<BigDecimal> taxes) throws Exception {
        System.out.println(mapper.writeValueAsString(taxes));
    }
}
