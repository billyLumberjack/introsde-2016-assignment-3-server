package introsde.assignment.soap.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.ehealth.dao.MyDao;


/**
 * The persistent class for the "Person" database table.
 * 
 */
@Entity
@Table(name="\"Person\"")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	@Column(name="\"birthdate\"")
	private String birthdate;

	@GeneratedValue(generator="sqlite_person")
	@TableGenerator(name="sqlite_person", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="Person")
	@XmlElement
	@Column(name="\"id\"")	
	@Id private int id;

	@XmlElement
	@Column(name="\"firstname\"")
	private String firstname;

	@XmlElement
	@Column(name="\"lastname\"")
	private String lastname;
	
	// mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation
	@OneToMany(cascade = CascadeType.ALL, mappedBy="person")
	@XmlElementWrapper(name="healthHistory")
	private List<Measure> healthHistory; // all measurements
	
	@Transient
	@XmlElementWrapper(name="currentHealth")
	private List<Measure> currentHealth; // one for each type of measure
	
	
	

	

	public Person() {
	}
	
	public Date strToDate(String str){
        
        try {
        	DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			return format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	public void cleanMeasures(){
		if(this.healthHistory != null || this.healthHistory.isEmpty()==false){
		     //now I iterate and I report only the more recent for each type
		     List<String>  matchString = new ArrayList<String>();
		     this.currentHealth = new ArrayList<Measure>();
		     this.currentHealth.clear();
		     
		     for(int i=0; i< this.healthHistory.size();i++){
		    	 if(!matchString.contains(this.healthHistory.get(i).getMeasureType())){
		    		 matchString.add(this.healthHistory.get(i).getMeasureType());
		    		 this.currentHealth.add(this.healthHistory.get(i));
		    		 }
		    	 else
		    	 {
		    		 Date a = strToDate(this.healthHistory.get(i).getDateRegistered());
		    		 Date b = strToDate(
		    				 this.currentHealth.get(matchString.indexOf(this.healthHistory.get(i).getMeasureType())).getDateRegistered()
		    				 );
		    		 if(a.after(b))
		    			 this.currentHealth.set(matchString.indexOf(this.healthHistory.get(i).getMeasureType()), this.healthHistory.get(i));
		    		 }
		    	}
		     }
	     }	

	public String getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(String bd) {
		this.birthdate = bd;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

    public static List<Person> getAll() {
        EntityManager em = MyDao.instance.createEntityManager();
        List<Person> pp = em.createNamedQuery("Person.findAll", Person.class).getResultList();
        MyDao.instance.closeConnections(em);
        for(Person p : pp){
        	p.cleanMeasures();
        }
        return pp;
    }
    
    public static Person getPersonById(int personId) {
        EntityManager em = MyDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        MyDao.instance.closeConnections(em);
        p.cleanMeasures();
        return p;
	}

	public static Person updatePerson(Person p) {
        EntityManager em = MyDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        MyDao.instance.closeConnections(em);
        p.cleanMeasures();
        return p;
    }    
    


    public static Person createPerson(Person p) {
        EntityManager em = MyDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        if(p.getCurrentHealth() != null){
	        for(Measure m :p.getCurrentHealth() ){
	        	m.setPersonId(p.getId());
	        	em.merge(m);
	        }
        }
        p = em.merge(p);tx.commit();
        MyDao.instance.closeConnections(em);
        p.cleanMeasures();
        return p;
    } 
    
    public static void removePerson(Person p) {
        EntityManager em = MyDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        MyDao.instance.closeConnections(em);
    }

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<Measure> getHealthHistory() {
		return healthHistory;
	}

	public void setHealthHistory(List<Measure> healthHistory) {
		this.healthHistory = healthHistory;
	}

	public List<Measure> getCurrentHealth() {
		this.cleanMeasures();
		return currentHealth;
	}

	public void setCurrentHealth(List<Measure> currentHealth) {
		this.currentHealth = currentHealth;
	}
}