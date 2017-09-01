package elpackage.postmee;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AUser {

    //FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String userName;
    private String thePassword;

    //FIELDS FOR JOINING CLASSES
    @OneToMany(mappedBy="aAUser", fetch=FetchType.EAGER)
    Set<PostItNote> collectionOfNotes = new HashSet<>();

    //CONSTRUCTOR
    public AUser(){}

    public AUser(String userName, String thePassword) {
        this.userName = userName;
        this.thePassword = thePassword;
    }

    //GETTERS & SETTERS
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<PostItNote> getCollectionOfNotes() {
        return collectionOfNotes;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getThePassword() {
        return thePassword;
    }

    public void setThePassword(String thePassword) {
        this.thePassword = thePassword;
    }


    //METHODS
    public void addPostItNote(PostItNote note){

        note.setaAUser(this);
        collectionOfNotes.add(note);
    }

}
