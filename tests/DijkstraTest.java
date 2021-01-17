import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import Data.Dane;
import Data.Szpital;
import Dijkstra.Dijkstra;
import Dijkstra.DoubleV2;
import Dijkstra.GraphNode;
import Dijkstra.Kolejka;
import Dijkstra.QueueNode;

class DijkstraTest {

	@Test
	void testDrogaPacjenta() {
		Dane.read("data/daneTestowe.txt");
		Dane.readPacjent("data/daneTestowePacjenci.txt");
		Dane.clearObjects();
		Dane.skrzyzowania();

		Dijkstra dijkstra = new Dijkstra(Dane.szpitale);
		int[] result = dijkstra.drogaPacjenta(1);
		int[] test = { 1 };
		try {
			for (int i = 0; i < test.length; i++) {
				assertEquals(test[i], result[i]);
			}
			
			result = dijkstra.drogaPacjenta(1);
			test = new int[] { 1 };
			for (int i = 0; i < test.length; i++) {
				assertEquals(test[i], result[i]);
			}
			
			result = dijkstra.drogaPacjenta(2);
			test = new int[] { 2 };
			for (int i = 0; i < test.length; i++) {
				assertEquals(test[i], result[i]);
			}
			
			result = dijkstra.drogaPacjenta(3);
			test = new int[] { 3,2 };
			for (int i = 0; i < test.length; i++) {
				assertEquals(test[i], result[i]);
			}
			
			result = dijkstra.drogaPacjenta(4);
			test = new int[] { 4 };
			for (int i = 0; i < test.length; i++) {
				assertEquals(test[i], result[i]);
			}
			
			result = dijkstra.drogaPacjenta(5);
			test = new int[] { 5,3,2 };
			for (int i = 0; i < test.length; i++) {
				assertEquals(test[i], result[i]);
			}
		
		
		
		
		} catch (IndexOutOfBoundsException e) {
			fail("metoda nie dzia³a");
		}

	}

	@Test
	void testNotContains() {
		try {
			Dijkstra dijkstra = new Dijkstra(Dane.szpitale);
			Class[] attributesTypes = new Class[2];
			attributesTypes[0] = int[].class;
			attributesTypes[1] = int.class;

			Method notContains = Dijkstra.class.getDeclaredMethod("notContains", attributesTypes);
			notContains.setAccessible(true);

			boolean result = (boolean) notContains.invoke(dijkstra, new int[] { 1, 2, -1 }, 1);
			assertEquals(false, result);

			result = (boolean) notContains.invoke(dijkstra, new int[] {}, 1);
			assertEquals(true, result);

			result = (boolean) notContains.invoke(dijkstra, new int[] { 2, 3, 4, 5, 0 }, 1);
			assertEquals(true, result);

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			fail("Metoda Trim nie istnieje");
		}
	}
	
	@Test
	void testZnajdywanieTrasy() {
		try {
			Dijkstra dijkstra = new Dijkstra(Dane.szpitale);
			Class[] attributesTypes = new Class[3];
			ArrayList<Integer> example = new ArrayList<Integer>();
			attributesTypes[0] = example.getClass();
			attributesTypes[1] = int.class;
			attributesTypes[2] = int.class;
			Method znajdywanieTrasy = Dijkstra.class.getDeclaredMethod("znajdywanieTrasy", attributesTypes);
			znajdywanieTrasy.setAccessible(true);
			
			Class[] attributesTypes2 = new Class[1];
			attributesTypes2[0] = int.class;
			Method algorithm = Dijkstra.class.getDeclaredMethod("algorithm", attributesTypes2);
			algorithm.setAccessible(true);
			DoubleV2[] result = (DoubleV2[]) algorithm.invoke(dijkstra, 1);
			
			
			znajdywanieTrasy.invoke(dijkstra, example, 2,1);
			assertEquals(example.get(0), 6);
			assertEquals(example.get(1), 2);

			example = new ArrayList<Integer>();
			znajdywanieTrasy.invoke(dijkstra, example, 3,1);
			assertEquals(example.get(0), 6);
			assertEquals(example.get(1), 2);
			assertEquals(example.get(2), 3);

			example = new ArrayList<Integer>();
			znajdywanieTrasy.invoke(dijkstra, example, 4,1);
			assertEquals(example.get(0), 4);

			example = new ArrayList<Integer>();
			znajdywanieTrasy.invoke(dijkstra, example, 5,1);
			assertEquals(example.get(0), 5);

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail("Metoda znajdywanieTrasy nie istnieje");
		}
	}

	@Test
	void testAlgorithm() {
		Dane.read("data/daneTestowe.txt");
		Dane.readPacjent("data/daneTestowePacjenci.txt");
		Dane.clearObjects();
		Dane.skrzyzowania();

		Dijkstra dijkstra = new Dijkstra(Dane.szpitale);

		try {
			Class[] attributesTypes = new Class[1];
			attributesTypes[0] = int.class;
			Method algorithm = Dijkstra.class.getDeclaredMethod("algorithm", attributesTypes);
			algorithm.setAccessible(true);
			DoubleV2[] result = (DoubleV2[]) algorithm.invoke(dijkstra, 1);
			Arrays.sort(result);
			double[] test1 = { 1, 6, 4, 2, 5, 3 };
			for (int i = 0; i < result.length; i++) {
				assertEquals(test1[i], result[i].id);
			}

			result = (DoubleV2[]) algorithm.invoke(dijkstra, 2);
			Arrays.sort(result);
			double[] test2 = { 2, 6, 3, 4, 5, 1 };
			for (int i = 0; i < result.length; i++) {
				assertEquals(test2[i], result[i].id);
			}

			result = (DoubleV2[]) algorithm.invoke(dijkstra, 3);
			Arrays.sort(result);
			double[] test3 = { 3, 2, 6, 5, 4, 1 };
			for (int i = 0; i < result.length; i++) {
				assertEquals(test3[i], result[i].id);
			}

			result = (DoubleV2[]) algorithm.invoke(dijkstra, 4);
			Arrays.sort(result);
			double[] test4 = { 4, 6, 2, 1, 5, 3 };
			for (int i = 0; i < result.length; i++) {
				if (test4[i] != 2 && test4[i] != 1) {
					assertEquals(test4[i], result[i].id);
				}
			}

			result = (DoubleV2[]) algorithm.invoke(dijkstra, 5);
			Arrays.sort(result);
			double[] test5 = { 5, 6, 3, 2, 4, 1 };
			for (int i = 0; i < result.length; i++) {
				assertEquals(test5[i], result[i].id);
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			fail("metoda algorithm nie istnieje");
		}

	}

}
