package carddeck;
import java.util.List;

public interface CardDeck<E> {

	public List<E> shuffle(List<E> cards);  // ī�� ����
	
	public E getCard();                     // ī�� �̱�
}
