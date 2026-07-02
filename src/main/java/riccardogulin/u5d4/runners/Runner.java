package riccardogulin.u5d4.runners;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import riccardogulin.u5d4.entities.User;
import riccardogulin.u5d4.exceptions.NotFoundException;
import riccardogulin.u5d4.services.UsersService;

import java.util.Locale;

@Component
public class Runner implements CommandLineRunner {

	//@Autowired
//	private final UsersRepository usersRepository; ❌ MAI USARE DIRETTAMENTE LE REPOSITORIES. BISOGNA UTILIZZARE I SERVICES

//	public Runner(UsersRepository usersRepository) {
//		this.usersRepository = usersRepository;
//	}

	private final UsersService usersService;

	public Runner(UsersService usersService) {
		this.usersService = usersService;
	}

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(Locale.ITALIAN);
		User newUser = new User(faker.lordOfTheRings().character(), faker.name().lastName(), faker.internet().emailAddress(), faker.internet().password());

		this.usersService.saveNewUser(newUser);


		this.usersService.findAll().forEach(System.out::println);

		try {
			System.out.println(this.usersService.findById(20));
		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		try {
			this.usersService.findByIdAndDelete(1);
		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		try {
			this.usersService.findByIdAndUpdate(8, new User("Ajeje", "Brazorf", "ajeje@gmail.com", "123456789"));
		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}


	}
}
