package elpackage.postmee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<AUser, Long> {

    //method below is a way to retrieve user data, given a user's username

    AUser findByUserName(@Param("userName") String userName);


}

