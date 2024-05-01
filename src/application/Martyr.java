package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*this class for martyr , has attribute info about martyr
 * 
*/
public class Martyr {
	//this is all info about martyr
	private String name;
	private String location;
	private int age;
	private char gender ;
	private Date dod;
	
//	public Martyr()  {
//	}
	
	//constructor with arguments all info about mertyr
	public Martyr(String name, int age, String location , char gender, String dod) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.location = location;
		this.dod = dateFormat.parse(dod);
		
	}
	//setters and getters 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public Date getDod() {
		return dod;
	}
	public void setDod(Date dod) {
		this.dod = dod;
	}
	//method to string to return all info for martyr
	@Override
	public String toString() {
		return "\nMartyr [name = " + name + " , age = " + age + " , Location = "+getLocation()+" , gender = "
	+ gender + " , dod = " + toStringDate() + "]";
	}
	//method to return date
	private String toStringDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
	    return dateFormat.format(this.dod);
	}

//	private String toStringDate() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        
//		return  dateFormat.format(this.dod);
//	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
}

