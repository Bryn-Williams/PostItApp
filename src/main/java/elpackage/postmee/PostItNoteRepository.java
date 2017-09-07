package elpackage.postmee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PostItNoteRepository extends CrudRepository<PostItNote, Long> {

    PostItNote findByNoteId(@Param("noteId") Long noteId);

}
