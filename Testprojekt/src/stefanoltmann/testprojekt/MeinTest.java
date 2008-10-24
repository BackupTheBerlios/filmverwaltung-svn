package stefanoltmann.testprojekt;
import junit.framework.TestCase;

public class MeinTest extends TestCase {

	private EineKlasse eineKlasse;
	
	protected void setUp() throws Exception {
		eineKlasse = new EineKlasse();
	}

	protected void tearDown() throws Exception {
		eineKlasse = null;
	}

	/**
	 * Test method for {@link EineKlasse#multiplizieren(double, double)}.
	 */
	public void testMultiplizieren() {
		assertEquals( eineKlasse.multiplizieren(3, 4), 12.0);
		assertNotSame(eineKlasse.multiplizieren(3, 4), 13.0);
	}

	/**
	 * Test method for {@link EineKlasse#addieren(double, double)}.
	 */
	public void testAddieren() {
		assertEquals(eineKlasse.addieren(3, 4), 7.0);
		assertNotSame(eineKlasse.addieren(3, 5), 7.0);
	}

}
