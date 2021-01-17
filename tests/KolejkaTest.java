import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Dijkstra.DoubleV2;
import Dijkstra.GraphNode;
import Dijkstra.Kolejka;
import Dijkstra.QueueNode;


class KolejkaTest {

	@Test
	void testWstaw() {
		try {
		    Kolejka kolejka = new Kolejka();
		    kolejka.wstaw(new QueueNode(new GraphNode(0,null,1) , new DoubleV2(5.0)));
		    Field listaField = Kolejka.class.getDeclaredField("lista");
		    listaField.setAccessible(true);
			@SuppressWarnings("unchecked")
			ArrayList<QueueNode> fieldValue = (ArrayList<QueueNode>) listaField.get(kolejka);
			
			double result = 5.0;
		    assertEquals(result, fieldValue.get(fieldValue.size()-1).getPriority());
		    result = 4.0;
		    kolejka.wstaw(new QueueNode(new GraphNode(0,null,1) , new DoubleV2(4.0)));
		    assertEquals(result, fieldValue.get(fieldValue.size()-1).getPriority());
		    
		} catch (ReflectiveOperationException ignored) {fail(ignored.getLocalizedMessage());}
	
		
	}

	@Test
	void testPobierz() {
		Kolejka kolejka = new Kolejka();
	    kolejka.wstaw(new QueueNode(new GraphNode(0,null,1) , new DoubleV2(5.0)));
	    kolejka.wstaw(new QueueNode(new GraphNode(1,null,1) , new DoubleV2(2.0)));
	    kolejka.wstaw(new QueueNode(new GraphNode(2,null,1) , new DoubleV2(4.0)));
	    kolejka.wstaw(new QueueNode(new GraphNode(3,null,1) , new DoubleV2(1.0)));
	    kolejka.wstaw(new QueueNode(new GraphNode(4,null,1) , new DoubleV2(3.0)));
	    assertEquals(3,kolejka.pobierz().getId());
	    assertEquals(1,kolejka.pobierz().getId());
	    assertEquals(4,kolejka.pobierz().getId());
	    assertEquals(2,kolejka.pobierz().getId());
	    assertEquals(0,kolejka.pobierz().getId());
	    
	    kolejka.wstaw(new QueueNode(new GraphNode(1,null,1) , new DoubleV2(1.0)));
	    kolejka.wstaw(new QueueNode(new GraphNode(2,null,1) , new DoubleV2(1.0)));
	    assertEquals(1,kolejka.pobierz().getId());
	    assertEquals(2,kolejka.pobierz().getId());
	}

	@Test
	void testIsNotEmpty() {
		Kolejka kolejka = new Kolejka();
		assertEquals(false,kolejka.isNotEmpty());
	    kolejka.wstaw(new QueueNode(new GraphNode(0,null,1) , new DoubleV2(5.0)));
	    assertEquals(true,kolejka.isNotEmpty());
	    @SuppressWarnings("unused")
		Object o = kolejka.pobierz();
	    assertEquals(false,kolejka.isNotEmpty());
	}

}
