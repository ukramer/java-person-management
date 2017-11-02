/*
 * Copyright (c) 2017, Ueli Kramer
 */

package com.uelikramer.personManagement.models;

/**
 * This class represents a Person
 *
 * @author Ueli Kramer
 */
public class Person implements Comparable {
    /**
     * id generator incremented integer
     */
    private static int idGenerator = 0;

    private int id;
    private int age;
    private double salary;
    private String firstName;
    private String lastName;
    private Country country;
    private Sex sex;

    /**
     * Constructor
     * @param age
     * @param salary
     * @param firstName
     * @param lastName
     * @param country
     * @param sex
     */
    public Person(int age, double salary, String firstName, String lastName, Country country, Sex sex) {
        super();

        this.id = ++idGenerator;
        this.age = age;
        this.salary = salary;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String toString() {
        return firstName + " " + lastName + " " + String.format("%.2f", salary) + " " + country + " " + age + " " + sex;
    }

    public boolean equals(Person p) {
        if (p.toString().equalsIgnoreCase(this.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        if (equals(o)) {
            return 0;
        }
        Person p = (Person)o;
        if (firstName.compareTo(p.firstName) == 0) {
            if (lastName.compareTo(p.lastName) == 0) {
                if (sex == p.sex) {
                    return 0;
                }
                if (sex == Sex.f) {
                    return 1;
                }
                return -1;
            }
            return lastName.compareTo(p.lastName);
        }
        return firstName.compareTo(p.firstName);
    }
}
