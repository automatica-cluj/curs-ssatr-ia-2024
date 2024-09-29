package utcluj.isp.curs3.oop.inner1builder;

/**
 * Demonstrate the Builder pattern and inner classes.
 */
public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private String phone;
    private String email;

    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.address = builder.address;
        this.phone = builder.phone;
        this.email = builder.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private int age;
        private String address;
        private String phone;
        private String email;

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    public static void main(String[] args) {
        Person person = new Person.Builder()
                .setFirstName("John")
                .setLastName("Doe")
                .setAge(30)
                .setAddress("123 Main St")
                .setPhone("555-1234")
                .setEmail("john.doe@example.com")
                .build();

        System.out.println(person);
    }
}
