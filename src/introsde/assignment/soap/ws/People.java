package introsde.assignment.soap.ws;


import introsde.assignment.soap.model.Measure;
import introsde.assignment.soap.model.Person;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
	/*************** M1 *******************/
    @WebMethod(operationName="getPeopleList")
    @WebResult(name="people") 
    public List<Person> getPeople();
    
    /*************** M2 *******************/
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int pId);    
    
    /*************** M4 *******************/
	@WebMethod(operationName="createPerson")
	@WebResult(name="personCreated") 
	public Person createPerson(@WebParam(name="personToCreate") Person person);

	/*************** M3 *******************/
    @WebMethod(operationName="updatePerson")
    @WebResult(name="updatedPerson") 
    public Person updatePerson(@WebParam(name="personToUpdate") Person person);    
    
    /*************** M5 *******************/
    @WebMethod(operationName="deletePerson")
    @WebResult(name="idDeleted") 
    public int deletePerson(@WebParam(name="idToDelete") int pId);
    
    /*************** M6 *******************/
    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="personHistory") 
    public List<Measure> readPersonHistory(
    		@WebParam(name="personId") int pId,
    		@WebParam(name="measureType") String type
    		);  
    
    /*************** M7 *******************/
    @WebMethod(operationName="readMeasuresTypes")
    @WebResult(name="measuresTypes") 
    public List<String> readMeasuresTypes();  

    /*************** M8 *******************/
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="personMeasure") 
    public Measure readPersonMeasure(
    		@WebParam(name="personId") int pId,
    		@WebParam(name="measureType") String type,
    		@WebParam(name="measureId") int mId
    		);      

    /*************** M9 *******************/
    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="personMeasure") 
    public Measure savePersonMeasure(
    		@WebParam(name="personId") int pId,
    		@WebParam(name="measure") Measure m
    		);  
    
    /*************** M10 *******************/
    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="personMeasure") 
    public Measure updatePersonMeasure(
    		@WebParam(name="personId") int pId,
    		@WebParam(name="measure") Measure m
    		);     
}