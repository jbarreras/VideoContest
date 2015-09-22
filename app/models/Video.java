package models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Table(name = "Video", catalog = "videoContest")
public class Video extends utilities.repository.Entity implements java.io.Serializable {

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
    private User user = new User();
    private Contest contest = new Contest();

    @Column(name = "name", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "lastname", length = 100)
    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "description", length = 2000)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "urlVideo", length = 500)
    public String getUrlVideo() {
        return this.urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    @Column(name = "urlConvertVideo", length = 500)
    public String getUrlConvertVideo() {
        return this.urlConvertVideo;
    }

    public void setUrlConvertVideo(String urlConvertVideo) {
        this.urlConvertVideo = urlConvertVideo;
    }

    @Column(name = "uploadDate", length = 10)
    public Date getUploadDate() {
        return this.uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Column(name = "startDateConversion", length = 10)
    public Date getStartDateConversion() {
        return this.startDateConversion;
    }

    public void setStartDateConversion(Date startDateConversion) {
        this.startDateConversion = startDateConversion;
    }

    @Column(name = "finishConversion", length = 10)
    public Date getFinishConversion() {
        return this.finishConversion;
    }

    public void setFinishConversion(Date finishConversion) {
        this.finishConversion = finishConversion;
    }

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    public VideoState getState() {
        return this.state;
    }

    public void setState(VideoState state) {
        this.state = state;
    }

    @Column(name = "email", length = 100)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne()
    @JoinColumn(name = "user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne()
    @JoinColumn(name = "contest")
    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }
}