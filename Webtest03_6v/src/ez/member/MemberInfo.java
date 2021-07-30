package ez.member;
//자바빈, DTO, VO, Command Object 등등으로 불리며, 
//가장 중요한 핵심내용은 클래스를 만들고 그 안에 변수를 선언한다는 것이다.
//선언된 변수는 가방에 담겨져있는 물건과 같다.
//필요한곳에서 가방을 꺼내고 그 안에 내용들을 활용한다.

import java.util.Date;

public class MemberInfo {
	
	private String id;
	private String password;
	private String name;
	private String address ;
	private Date registerDate;
	private String email;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
