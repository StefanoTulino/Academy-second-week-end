package com.corso.marketExercise;

import lombok.Data;

@Data
public class Order {
    private int orderId;
    private int orderNumber;
    private int personId;

    public Order(){

    }

    /**
     *
     * @param orderId
     * @param orderNumber
     * @param personId
     */
    public Order(int orderId, int orderNumber, int personId) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.personId = personId;
    }
}
