package riccardogulin.u5d4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import riccardogulin.u5d4.entities.User;

import java.util.List;
import java.util.Optional;

@Repository // Specializzazione di @Component che segue le regole dei Component
public interface UsersRepository extends JpaRepository<User, Long> {
	// <User, Long> sono rispettivamente la Entity di riferimento e il Tipo della PK di quell'entità
	// Solo estendendo JpaRepository ottengo automaticamente tutti i metodi base già pronti all'uso (.save(), .findById(), ecc ecc)
	// Posso aggiungere ulteriori metodi a questa repository se non dovessero bastarmi i metodi base

	// --------------------------------------- DERIVED QUERIES -------------------------------------------------
	List<User> findBySurname(String surname); // SELECT * FROM users WHERE surname = :surname

	List<User> findByNameAndSurname(String name, String surname); // SELECT * FROM users WHERE surname = :surname AND name = :name

	List<User> findByNameStartingWithIgnoreCase(String partialName); // SELECT * FROM users WHERE name ILIKE CONCAT(:partialName, "%")

	List<User> findByNameIn(List<String> names);

	List<User> findByEmailIsNull();

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	// LINK ALLA DOCUMENTAZIONE PER LE DERIVED QUERIES
	// https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

	// --------------------------------------- JPQL QUERIES -------------------------------------------------
	@Query("SELECT u FROM User u WHERE u.name = :name")
	List<User> filterByNome(String name);

	// --------------------------------------- SQL QUERIES -------------------------------------------------
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE name = :name")
	List<User> filterByNomeSQL(String name);


}
