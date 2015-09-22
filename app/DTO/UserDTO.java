package DTO;
import java.util.Date;
import models.User;




public class UserDTO implements java.io.Serializable {

	private int Id = 0;
	private String name = "";
	private String lastName = "";
	private String email = "";
	private String password = "";


	public int getId() {
		return this.Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public static User ConvertirEntidad(
			UserDTO dto) {
		User entidad = new User();
		if (dto != null) {
			entidad.setId(dto.getId());
			entidad.setName(dto.getName());
			entidad.setLastName(dto.getLastName());
			entidad.setEmail(dto.getEmail());
			entidad.setPassword(dto.getPassword());

		}
		return entidad;
	}

	public static UserDTO ConvertirDTO(
			User entidad) {
		UserDTO dto = new UserDTO();
		if (entidad != null) {
			dto.setId(entidad.getId());
			dto.setName(entidad.getName());
			dto.setLastName(entidad.getLastName());
			dto.setEmail(entidad.getEmail());
			dto.setPassword(entidad.getPassword());
		}
		return dto;
	}


}
