package elpackage.postmee;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PostItNote {

    //FIELDS
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long noteId;
    private String theMessage;
    private String status;
    private Date theDate = new Date();

    //FIELDS FOR JOINING CLASSES
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="theUser_id")
    private AUser aAUser;
    //END OF FIELDS FOR JOINING CLASSES

    //Constructor
    public PostItNote(){}

    public PostItNote(String theMessage, String status) {
        this.theMessage = theMessage;
        this.status = status;
    }

    //getters and setters
    public String getTheMessage() {
        return theMessage;
    }

    public void setTheMessage(String theMessage) {
        this.theMessage = theMessage;
    }

    public Date getTheDate() {
        return theDate;
    }

    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

    public AUser getaAUser() {
        return aAUser;
    }

    public void setaAUser(AUser aAUser) {
        this.aAUser = aAUser;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
