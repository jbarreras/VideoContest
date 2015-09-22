package DTO;
import java.util.Date;
import models.Contest;




public class ContestDTO implements java.io.Serializable {

	private int Id = 0;
	private UserDTO fk_Contest_User = new UserDTO();
	private String name = "";
	private String banner = "";
	private String url = "";
	private Date startDate = new Date();
	private Date finishDate = new Date();
	private String description = "";


	public int getId() {
		return this.Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}


	public UserDTO getUser() {
		return fk_Contest_User;
	}

	public void setUser(UserDTO fk_Contest_User) {
		this.fk_Contest_User = fk_Contest_User;
	}



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}



	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}



	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}



	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public static models.Contest ConvertirEntidad(
			ContestDTO dto) {
		models.Contest entidad = new Contest();
		if (dto != null) {
			entidad.setId(dto.getId());
			entidad.setUser(UserDTO.ConvertirEntidad(dto.getUser()));
			entidad.setName(dto.getName());
			entidad.setBanner(dto.getBanner());
			entidad.setUrl(dto.getUrl());
			entidad.setStartDate(dto.getStartDate());
			entidad.setFinishDate(dto.getFinishDate());
			entidad.setDescription(dto.getDescription());

		}
		return entidad;
	}

	public static ContestDTO ConvertirDTO(
			Contest entidad) {
		ContestDTO dto = new ContestDTO();
		if (entidad != null) {
			dto.setId(entidad.getId());
			dto.setUser(UserDTO.ConvertirDTO(entidad.getUser()));
			dto.setName(entidad.getName());
			dto.setBanner(entidad.getBanner());
			dto.setUrl(entidad.getUrl());
			dto.setStartDate(entidad.getStartDate());
			dto.setFinishDate(entidad.getFinishDate());
			dto.setDescription(entidad.getDescription());
		}
		return dto;
	}


}
