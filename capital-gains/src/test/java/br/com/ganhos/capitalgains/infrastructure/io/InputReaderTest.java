package br.com.ganhos.capitalgains.infrastructure.io;

import br.com.ganhos.capitalgains.domain.model.Operation;
import br.com.ganhos.capitalgains.domain.model.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputReaderTest {

    private final InputStream systemIn = System.in;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn);
    }

    @Test
    void shouldReadOperationsFromSystemIn() throws Exception {
        String jsonInput = "[{\"type\":\"BUY\",\"unitCost\":100.0,\"quantity\":50},{\"type\":\"SELL\",\"unitCost\":110.0,\"quantity\":50}]";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonInput.getBytes());
        System.setIn(inputStream);

        List<Operation> operations = InputReader.readOperations();
        assertNotNull(operations);
        assertEquals(2, operations.size());

        Operation buyOp = operations.get(0);
        assertEquals(OperationType.BUY, buyOp.getType());
        assertEquals(100.0, buyOp.getUnitCost());
        assertEquals(50, buyOp.getQuantity());

        Operation sellOp = operations.get(1);
        assertEquals(OperationType.SELL, sellOp.getType());
        assertEquals(110.0, sellOp.getUnitCost());
        assertEquals(50, sellOp.getQuantity());
    }

    @Test
    void shouldThrowExceptionIfResourceFileNotFound() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            InputReader.readOperationsFromResource("nonexistent_file.json");
        });

        assertTrue(thrown.getMessage().contains("Arquivo nonexistent_file.json não encontrado no classpath."));
    }
}
