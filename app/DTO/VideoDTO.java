package DTO;
import java.util.Date;
import models.Video;
import models.VideoState;


public class VideoDTO implements java.io.Serializable {

	private int Id = 0;
	private ContestDTO fk_Video_Contest = new ContestDTO();
	private String name = "";
	private String lastname = "";
	private String description = "";
	private String urlVideo = "";
	private String urlConvertVideo = "";
	private Date uploadDate = new Date();
	private Date startDateConversion = new Date();
	private Date finishConversion = new Date();
	private VideoState state = VideoState.PENDING;
	private String email = "";


	public int getId() {
		return this.Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}


	public ContestDTO getContest() {
		return fk_Video_Contest;
	}

	public void setContest(ContestDTO fk_Video_Contest) {
		this.fk_Video_Contest = fk_Video_Contest;
	}



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public String getUrlVideo() {
		return this.urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}



	public String getUrlConvertVideo() {
		return this.urlConvertVideo;
	}

	public void setUrlConvertVideo(String urlConvertVideo) {
		this.urlConvertVideo = urlConvertVideo;
	}



	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}



	public Date getStartDateConversion() {
		return this.startDateConversion;
	}

	public void setStartDateConversion(Date startDateConversion) {
		this.startDateConversion = startDateConversion;
	}



	public Date getFinishConversion() {
		return this.finishConversion;
	}

	public void setFinishConversion(Date finishConversion) {
		this.finishConversion = finishConversion;
	}



	public VideoState getState() {
		return this.state;
	}

	public void setState(VideoState state) {
		this.state = state;
	}



	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public static Video ConvertirEntidad(
			VideoDTO dto) {
		Video entidad = new Video();
		if (dto != null) {
			entidad.setId(dto.getId());
			entidad.setContest(ContestDTO.ConvertirEntidad(dto.getContest()));
			entidad.setName(dto.getName());
			entidad.setLastname(dto.getLastname());
			entidad.setDescription(dto.getDescription());
			entidad.setUrlVideo(dto.getUrlVideo());
			entidad.setUrlConvertVideo(dto.getUrlConvertVideo());
			entidad.setUploadDate(dto.getUploadDate());
			entidad.setStartDateConversion(dto.getStartDateConversion());
			entidad.setFinishConversion(dto.getFinishConversion());
			entidad.setState(dto.getState());
			entidad.setEmail(dto.getEmail());

		}
		return entidad;
	}

	public static VideoDTO ConvertirDTO(
			Video entidad) {
		VideoDTO dto = new VideoDTO();
		if (entidad != null) {
			dto.setId(entidad.getId());
			dto.setContest(ContestDTO.ConvertirDTO(entidad.getContest()));
			dto.setName(entidad.getName());
			dto.setLastname(entidad.getLastname());
			dto.setDescription(entidad.getDescription());
			dto.setUrlVideo(entidad.getUrlVideo());
			dto.setUrlConvertVideo(entidad.getUrlConvertVideo());
			dto.setUploadDate(entidad.getUploadDate());
			dto.setStartDateConversion(entidad.getStartDateConversion());
			dto.setFinishConversion(entidad.getFinishConversion());
			dto.setState(entidad.getState());
			dto.setEmail(entidad.getEmail());
		}
		return dto;
	}


}
