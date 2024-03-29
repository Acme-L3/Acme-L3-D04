
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialSessionTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssistantUsername(String username);

	@Query("select ts from TutorialSession ts where ts.tutorial.assistant.userAccount.username = :username")
	Collection<TutorialSession> findManyTutorialSessionsByAssistantUsername(String username);

}
