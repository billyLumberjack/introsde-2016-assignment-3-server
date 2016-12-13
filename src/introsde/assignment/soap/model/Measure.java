package introsde.assignment.soap.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.ehealth.dao.MyDao;


/**
 * The persistent class for the "Measure" database table.
 * 
 */
@XmlRootElement(name="measure")
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Table(name="\"Measure\"")
@NamedQuery(name="Measure.findAll", query="SELECT m FROM Measure m")
public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(generator="sqlite_measure")
	@TableGenerator(name="sqlite_measure", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="Measure")
	@Column(name="\"id\"")
	@XmlElement
	@Id private int id;	

	@XmlElement
	@Column(name="\"dateRegistered\"")
	private String dateRegistered;

	@XmlElement
	@Column(name="\"measureType\"")
	private String measureType;

	@XmlElement
	@Column(name="\"measureValue\"")
	private String measureValue;

	@XmlElement
	@Column(name="\"measureValueType\"")
	private String measureValueType; // string, integer, real	
	
	@XmlElement
	@Column(name="\"personId\"")
	private int personId;	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"personId\"" , insertable=false, updatable=false)
	@XmlTransient
	private Person person;
	
    public static Measure saveMeasure(Measure m) {
        EntityManager em = MyDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(m);
        tx.commit();
        MyDao.instance.closeConnections(em);
        return m;
    } 	
    
    public static Measure updateMeasure(Measure m) {
        EntityManager em = MyDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        m=em.merge(m);
        tx.commit();
        MyDao.instance.closeConnections(em);
        return m;
    }    

    public static List<Measure> getAll() {
        EntityManager em = MyDao.instance.createEntityManager();
        List<Measure> list = em.createQuery("SELECT m FROM Measure m", Measure.class).getResultList();
        MyDao.instance.closeConnections(em);
        return list;
    }
	
	public static Measure getOne(int id) {
    	EntityManager em = MyDao.instance.createEntityManager();
    	Measure m = (Measure) em.createQuery(
    	        "SELECT m FROM Measure m WHERE m.id LIKE :identifier")
    	        .setParameter("identifier",id).getSingleResult();
    	MyDao.instance.closeConnections(em);
    	return m;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(String  dr) {
		this.dateRegistered = dr;
	}

	public String getMeasureType() {
		return measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	public String getMeasureValue() {
		return measureValue;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public String getMeasureValueType() {
		return measureValueType;
	}

	public void setMeasureValueType(String measureValueType) {
		this.measureValueType = measureValueType;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public static List<String> getMeasureTypes() {
		
		// TODO Auto-generated method stub
    	EntityManager em = MyDao.instance.createEntityManager();
    	/*List<String> tt =em.createQuery(
    	        "SELECT distinct(measureType) FROM Measure",String.class)
    	        .getResultList();*/
    	List<String> tt = em.createQuery("select DISTINCT(m.measureType) from Measure m", String.class).getResultList();
    	MyDao.instance.closeConnections(em);
    	return tt;
	}	

}