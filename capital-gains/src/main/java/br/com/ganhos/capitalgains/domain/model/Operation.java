package br.com.ganhos.capitalgains.domain.model;

import br.com.ganhos.capitalgains.domain.model.OperationType;

public class Operation {
    private OperationType type;
    private double unitCost;
    private int quantity;

    // Construtor padrão
    public Operation() {
    }

    // Construtor com parâmetros
    public Operation(OperationType type, double unitCost, int quantity) {
        this.type = type;
        this.unitCost = unitCost;
        this.quantity = quantity;
    }

    // Getters
    public OperationType getType() {
        return type;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setType(OperationType type) {
        this.type = type;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "type=" + type +
                ", unitCost=" + unitCost +
                ", quantity=" + quantity +
                '}';
    }
}
