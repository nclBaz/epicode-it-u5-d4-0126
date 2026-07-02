package riccardogulin.u5d4.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import riccardogulin.u5d4.entities.User;
import riccardogulin.u5d4.exceptions.NotFoundException;
import riccardogulin.u5d4.exceptions.ValidationException;
import riccardogulin.u5d4.repositories.UsersRepository;

import java.util.List;

@Service // Anche questa è una specializzazione di @Component
@Slf4j
public class UsersService {
	private final UsersRepository usersRepository;

	public UsersService(UsersRepository usersRepository) { //
		this.usersRepository = usersRepository;
	}

	public void saveNewUser(User newUser) {

//		if (this.usersRepository.findByEmail(newUser.getEmail()).isPresent())
//			throw new ValidationException("L'email " + newUser.getEmail() + " è già in uso!");

		if (this.usersRepository.existsByEmail(newUser.getEmail())) throw new ValidationException("L'email " + newUser.getEmail() + " è già in uso!");

		// 2. Altri controlli di validazione (es. password > 8 caratteri)
		if (newUser.getPassword().length() < 8) throw new ValidationException("La password deve avere più di 8 caratteri");

		// 3. Inizializzo ulteriori campi dell'utente (se mi dovesse servire)

		// 4. Salvo l'utente utilizzando la repository
		this.usersRepository.save(newUser);

		// 5. Log di avvenuto salvataggio
		log.info("L'utente " + newUser.getSurname() + " è stato salvato correttamente");
	}


	public List<User> findAll() {
		return this.usersRepository.findAll();
	}

	public User findById(long userID) {
//		Optional<User> foundOrNot = this.usersRepository.findById(userID);
//		if (foundOrNot.isPresent()) return foundOrNot.get();
//		else throw new NotFoundException(userID);

		return this.usersRepository.findById(userID).orElseThrow(() -> new NotFoundException(userID)); // Alternativa più compatta alle 3 righe di sopra
	}

	public void findByIdAndDelete(long userId) {
		User found = this.findById(userId);
		this.usersRepository.delete(found);
		log.info("L'utente " + found.getId() + " è stato correttamente cancellato!");
	}

	public void findByIdAndUpdate(long userId, User updatedUser) {
		// 1. Faccio tutti i controlli che mi servono
		if (updatedUser.getPassword().length() < 8) throw new ValidationException("La password deve avere più di 8 caratteri");

		// 2. Cerco l'utente tramite id
		User found = this.findById(userId);

		// 3. Aggiorno i campi dell'utente con quelli di updatedUser
		found.setName(updatedUser.getName());
		found.setSurname(updatedUser.getSurname());
		found.setEmail(updatedUser.getEmail());
		found.setPassword(updatedUser.getPassword());

		// 4. Risalvo l'utente così modificato
		this.usersRepository.save(found);

		// 5. Log
		log.info("L'utente " + found.getId() + " è stato correttamente modificato!");
	}

	public List<User> filterBySurname(String surname) {
		return this.usersRepository.findBySurname(surname);
	}

	public List<User> filterByNameAndSurname(String name, String surname) {
		return this.usersRepository.findByNameAndSurname(name, surname);
	}

	public List<User> filterByPartialName(String partialName) {
		return this.usersRepository.findByNameStartingWithIgnoreCase(partialName);
	}

	public List<User> filterByNames(List<String> names) {
		return this.usersRepository.findByNameIn(names);
	}
}

