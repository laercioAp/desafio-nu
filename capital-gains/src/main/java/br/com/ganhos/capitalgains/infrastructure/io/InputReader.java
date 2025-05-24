package br.com.ganhos.capitalgains.infrastructure.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.ganhos.capitalgains.domain.model.Operation;

import java.io.InputStream;
import java.util.List;

public class InputReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Operation> readOperations() throws Exception {
        return mapper.readValue(System.in, new TypeReference<List<Operation>>() {});
    }

    public static List<Operation> readOperationsFromResource(String resourceFileName) throws Exception {
        InputStream is = InputReader.class.getClassLoader().getResourceAsStream(resourceFileName);
        if (is == null) {
            throw new IllegalArgumentException("Arquivo " + resourceFileName + " não encontrado no classpath.");
        }
        return mapper.readValue(is, new TypeReference<List<Operation>>() {});
    }
}
