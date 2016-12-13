package introsde.assignment.soap.ws;

import java.util.List;

import javax.jws.WebService;

import introsde.assignment.soap.model.Measure;
import introsde.assignment.soap.model.Person;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment.soap.ws.People",serviceName="PeopleService")
public class PeopleImpl implements People {
	
	/*************** M1 *******************/
	// returns all the person records inside the db
    @Override
    public List<Person> getPeople() {
        return Person.getAll();
    }
    
    /*************** M2 *******************/
	//returns the person instance with a specifi id
    @Override
    public Person readPerson(int id) {
        System.out.println("---> Reading Person by id = "+id);
        Person p = Person.getPersonById(id);
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getFirstname());
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
        return p;
    }

    /*************** M4 *******************/
	// changes the value(s) of an already present person in the database to the given one(s)
	@Override
	public Person updatePerson(Person person) {
	    return Person.updatePerson(person);
	}

	/*************** M3 *******************/
	// inserts in the db the given person instance
    @Override
    public Person createPerson(Person person) {
        return Person.createPerson(person);
    }

    /*************** M5 *******************/
	//removes the person with a specific id
    @Override
    public int deletePerson(int id) {
        Person p = Person.getPersonById(id);
        if (p!=null) {
            Person.removePerson(p);
            return 0;
        } else {
            return -1;
        }
    }

    /*************** M6 *******************/
	//return all the measures belonging to the specified person
	@Override
	public List<Measure> readPersonHistory(int pId, String type) {
		// TODO Auto-generated method stub
		List<Measure> mm = Person.getPersonById(pId).getHealthHistory();
		return mm;
	}

	/*************** M7 *******************/
	//return the string-list of the different measure types inside the Measure table
	@Override
	public List<String> readMeasuresTypes() {
		return Measure.getMeasureTypes();
	}

	/*************** M8 *******************/
	//returns a specific measure
	@Override
	public Measure readPersonMeasure(int pId, String type, int mId) {
		for(Measure m : Person.getPersonById(pId).getHealthHistory()){
			if(m.getId() == mId)
				return m;
		}
		return null;
	}

	/*************** M9 *******************/
	//store in the db the given measure
	@Override
	public Measure savePersonMeasure(int pId, Measure m) {
		// TODO Auto-generated method stub
		m.setPersonId(pId);
		return Measure.saveMeasure(m);
	}

	/*************** M10 *******************/
	//changes the value(s) of an already present measure in the database to the given one
	@Override
	public Measure updatePersonMeasure(int pId, Measure m) {
		// TODO Auto-generated method stub
		m.setPersonId(pId);
		return Measure.updateMeasure(m);
	}
}