package com.saiproject.sqlite;

public class Computer {

    //Instance variables
    private int id;
    private String computerName;
    private String computerType;




//Constructors

    public Computer() {
        super();
    }

    public Computer(int id, String computerName, String computerType) {
        this.id = id;
        this.computerName = computerName;
        this.computerType = computerType;
    }

    public Computer(String computerName, String computerType) {
        this.computerName = computerName;
        this.computerType = computerType;
    }



    //Getters and setters



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerType() {
        return computerType;
    }

    public void setComputerType(String computerType) {
        this.computerType = computerType;
    }



}
