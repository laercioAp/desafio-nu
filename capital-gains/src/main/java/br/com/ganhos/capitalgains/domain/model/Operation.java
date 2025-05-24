package br.com.ganhos.capitalgains.domain.model;

public class Operation {
    private OperationType operation;
    private int quantity;
    private double unitCost;

    public Operation() {}

    public Operation(OperationType operation, int quantity, double unitCost) {
        this.operation = operation;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }
}
