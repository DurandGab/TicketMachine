package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : on n’imprime pas leticket si le montant inséré est insuffisant
	void imprimePasAssezDargent(){
		machine.insertMoney(PRICE-1);
		assertFalse(machine.printTicket(),"pas assez d'argent");
	}

	@Test
	//S4 : on imprime le ticket si le montant inséré est suffisant

	void imprimeTicket(){
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(),"ticket non imprimé");
	}

	@Test
	//S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket

	void balanceDecremented(){
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(0,machine.getBalance(),"La balance n'est pas correctement décrémentée");
	}

	@Test
	//S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)

	void totalAmountNotUpdated(){
		machine.insertMoney(PRICE-1);
		machine.printTicket();
		assertEquals(0,machine.getTotal(),"Le montant collecté est mis à jour avant l'impression du ticket");
	}

	@Test
	//S7 : refund()rend correctement la monnaie

	void refundCorrect(){
		machine.insertMoney(PRICE);
		assertEquals(PRICE,machine.refund(),"refund ne rend pas correctement la monnaie");
		assertEquals(0,machine.getBalance(),"refund ne remet pas la balance à 0");
	}

	@Test
	//S8 : refund()remet la balance à zéro

	void refundBalance(){
		machine.insertMoney(PRICE);
		machine.refund();
		assertEquals(0,machine.getBalance(),"refund ne remet pas la balance à 0");
	}

	@Test
	//S9 : on ne peut pas insérer un montant négatif

	void negativeAmount(){
		assertThrows(IllegalArgumentException.class,()->machine.insertMoney(-1),"montant négatif accepté");
	}

	@Test
	//S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif

	void negativePrice(){
		assertThrows(IllegalArgumentException.class,()->new TicketMachine(-1),"prix négatif accepté");
	}
}
