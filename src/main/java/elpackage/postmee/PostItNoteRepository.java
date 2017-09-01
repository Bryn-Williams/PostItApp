package elpackage.postmee;

import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PostItNoteRepository extends JpaRepository<PostItNote, Long> {

    PostItNote findByNoteId(@Param("noteId") Long noteId);

}
