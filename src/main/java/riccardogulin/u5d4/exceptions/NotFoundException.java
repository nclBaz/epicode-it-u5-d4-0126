package riccardogulin.u5d4.exceptions;

public class NotFoundException extends RuntimeException {
	public NotFoundException(long id) {
		super("La risorsa con id " + id + " non è stata trovata!");
	}
}
