package carddeck;
import java.util.List;

public interface CardDeck<E> {

	public List<E> shuffle(List<E> cards);  // Ä«µå ¼¯±â
	
	public E getCard();                     // Ä«µå »Ì±â
}
