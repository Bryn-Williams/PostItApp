package elpackage.postmee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api")
public class PostMeeController {

    @Autowired
    private PostItNoteRepository postItRepo;

    @Autowired
    UserRepository userRepo;

    @RequestMapping("/messages/{xxx}")
    public List<Map<String, Object>> displayPostIts(@PathVariable String xxx, Authentication authentication){

        List<Map<String, Object>> theDto = new LinkedList<>();

        List<PostItNote> listOfPostIts = (List<PostItNote>) postItRepo.findAll();
        Set<PostItNote> setOfPostIts = new HashSet<PostItNote>(listOfPostIts);

        //GET AUTHENTICATED PLAYER!!
        AUser loggedInUser = userRepo.findByUserName(authentication.getName());

        for(PostItNote postVar : setOfPostIts ){

            Map<String, Object> dtooo = new LinkedHashMap<>();

            if(postVar.getaAUser().getUserName().equals(xxx) && postVar.getaAUser().getUserName().equals(loggedInUser.getUserName())){
                dtooo.put("theUser", postVar.getaAUser().getUserName());
                dtooo.put("status", postVar.getStatus());
                dtooo.put("theMessage", postVar.getTheMessage());
                dtooo.put("postItID", postVar.getNoteId());
                dtooo.put("theDate", postVar.getTheDate());

                theDto.add(dtooo);
            }
        }
        return theDto;
    }

    //METHOD TO CREATE PLAYER
    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestParam String userName, @RequestParam String thePassword){

        AUser seeIfExists = userRepo.findByUserName(userName);

        if(seeIfExists == null) {
            AUser theNewUser = new AUser(userName, thePassword);
            userRepo.save(theNewUser);

            return new ResponseEntity<>("You created a new player, well done", HttpStatus.ACCEPTED);

        }else{
            return new ResponseEntity<>("Name already in use", HttpStatus.CONFLICT);
        }
    }

    //METHOD TO CREATE AND SAVE NEW MESSAGES
    @RequestMapping(path="/saveMessage", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> saveNewMessage(@RequestParam String theMessage, @RequestParam String loggedUser){

        String status = "toDo";
        PostItNote newMsg = new PostItNote(theMessage, status);

        AUser loggedInUser = userRepo.findByUserName(loggedUser);

        loggedInUser.addPostItNote(newMsg);

        postItRepo.save(newMsg);

        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("theDate", newMsg.getTheDate());
        dto.put("theMessage", newMsg.getTheMessage());
        dto.put("postItID", newMsg.getNoteId());
        dto.put("status", newMsg.getStatus());

        return new ResponseEntity<Map<String, Object>>(dto, HttpStatus.ACCEPTED);
    }

    //METHOD TO DELETE MESSAGES
    @RequestMapping(path="/deleteMessage", method = RequestMethod.POST)
    public ResponseEntity<String> deleteMsgs(@RequestParam Long noteId){

        PostItNote toBeDeleted = postItRepo.findByNoteId(noteId);

        postItRepo.delete(toBeDeleted);

        return new ResponseEntity<>("You deleted the message, boom!", HttpStatus.ACCEPTED);
    }

    //METHOD TO CHANGE THE STATUS!!
    @RequestMapping(path = "/changeTheStatus", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> changeStatus(@RequestParam Long noteId, @RequestParam String status){

        PostItNote toChangeStatus = postItRepo.findByNoteId(noteId);
        toChangeStatus.setStatus(status);
        postItRepo.save(toChangeStatus);

        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("theMessage", toChangeStatus.getTheMessage());
        dto.put("theDate", toChangeStatus.getTheDate());
        dto.put("status", toChangeStatus.getStatus());
        dto.put("postItID", toChangeStatus.getNoteId());
        dto.put("theUser", toChangeStatus.getaAUser().getUserName());

        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }



}
