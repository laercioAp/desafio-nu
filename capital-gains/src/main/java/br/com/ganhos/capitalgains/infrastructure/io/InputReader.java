package br.com.ganhos.capitalgains.infrastructure.io;

import br.com.ganhos.capitalgains.domain.model.Operation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStreamReader;
import java.util.List;

public class InputReader {

    public static List<Operation> readOperations() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new InputStreamReader(System.in), new TypeReference<List<Operation>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler JSON de entrada", e);
        }
    }
}
