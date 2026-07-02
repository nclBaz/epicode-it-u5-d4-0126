package riccardogulin.u5d4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riccardogulin.u5d4.entities.User;

@Repository // Specializzazione di @Component che segue le regole dei Component
public interface UsersRepository extends JpaRepository<User, Long> {
	// <User, Long> sono rispettivamente la Entity di riferimento e il Tipo della PK di quell'entità
	// Solo estendendo JpaRepository ottengo automaticamente tutti i metodi base già pronti all'uso (.save(), .findById(), ecc ecc)
	// Posso aggiungere ulteriori metodi a questa repository se non dovessero bastarmi i metodi base
}
