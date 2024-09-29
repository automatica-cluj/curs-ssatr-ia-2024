package exemple.ui.ex5_mvc.model;

/**
 * @author Radu Miron
 * @version 1
 */
import java.util.ArrayList;
import java.util.List;

public class PersonModel {
    private List<Person> persons;

    public PersonModel() {
        persons = new ArrayList<>();
    }

    public void addPerson(int id, String firstName, String lastName) {
        Person person = new Person(id, firstName, lastName);
        persons.add(person);
    }

    public List<Person> getPersons() {
        return persons;
    }
}
