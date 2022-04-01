package com.corso.marketExercise;


import lombok.Data;

@Data
public class Person {

    private int id;
    private String name;
    private String lastName;
    private int age;

    public Person(){

    }

    /**
     *
     * @param id
     * @param name
     * @param lastName
     * @param age
     */
    public Person(int id, String name, String lastName, int age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }
}
