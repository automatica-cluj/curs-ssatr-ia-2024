package ro.ucv.ace.etl.model;

import java.time.LocalDate;

/**
 * Customer data model
 */
public class Customer {
    private int id;
    private String name;
    private String email;
    private int age;
    private String city;
    private String country;
    private LocalDate registrationDate;

    public Customer() {
    }

    public Customer(int id, String name, String email, int age, String city, String country, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.city = city;
        this.country = country;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
