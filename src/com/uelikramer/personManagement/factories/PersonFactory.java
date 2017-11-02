/*
 * Copyright (c) 2017, Ueli Kramer
 */
package com.uelikramer.personManagement.factories;

import com.uelikramer.personManagement.models.Country;
import com.uelikramer.personManagement.models.Sex;
import com.uelikramer.personManagement.models.Person;

import java.util.ArrayList;
import java.util.Random;

/**
 * PersonFactory which will generate random Person objects
 * @author Ueli Kramer
 */
public abstract class PersonFactory {
    private static String[] firstNames = {"Nick", "Adriana", "Jamaal", "Margart", "Reba", "Nigel", "Lawerence", "Hong", "Margaret", "Micha", "Janis", "Alisha", "Lenita", "Tabitha", "Charmain", "Refugio", "Cesar", "Santiago", "Thora", "Sherell"};
    private static String[] lastNames = {"Kramer", "Meyer", "Mustermann", "Jung", "Fenstermacher", "Lowe", "Beyer", "Wolf", "Unger"};

    public static ArrayList<Person> getSomePersons() {
        ArrayList<Person> persons = new ArrayList<>();
        Country[] countries = Country.values();
        Sex[] sexes = Sex.values();

        Random r = new Random();
        int randomAmount = r.nextInt(150);
        for (int i = 1; i <= randomAmount; i++) {
            int randomAge = (int) r.nextInt(100);
            double randomSalary = (double) r.nextInt(2500 * 100) / 100;
            int randomFirstNameIndex = r.nextInt(firstNames.length);
            int randomLastNameIndex = r.nextInt(lastNames.length);
            int randomCountryIndex = r.nextInt(countries.length);
            int randomSexIndex = r.nextInt(sexes.length);

            persons.add(new Person(randomAge, randomSalary, firstNames[randomFirstNameIndex], lastNames[randomLastNameIndex], countries[randomCountryIndex], sexes[randomSexIndex]));
        }

        return persons;
    }
}
