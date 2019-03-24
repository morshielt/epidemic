package symulacja;

import agent.*;
import dane.Dane;

import java.util.*;

public class Symulator {
	private Dane parametry;
	private int chorzy;
	private int uodpornieni;
	private int zdrowi;
	private PriorityQueue<Spotkanie> spotkania;
	private ArrayList<Agent> agenci;
	private Random generator;
	private static final int zarazeni = 1;
	private String licznoscKolejneDni = "# liczność w kolejnych dniach\n";
	private String agenciDoRaportu = "";
	private String graf = "";

	public Symulator(Dane dane) {
		this.parametry = dane;
		this.chorzy = zarazeni;
		this.uodpornieni = 0;
		this.zdrowi = this.parametry.liczbaAgentow() - zarazeni;
		this.spotkania = new PriorityQueue<>();
		this.agenci = new ArrayList<>();
		this.generator = new Random(this.parametry.seed());
	}

	public void symulujEpidemie() {
		nowaSiecSpolecznosciowa();

		for (int dzien = 1; dzien <= parametry.liczbaDni() && !agenci.isEmpty(); dzien++) {
			wypiszDzisiejszyStan();

			zmienStanAgentow();
			zaplanujSpotkania(dzien);
			przeprowadzSpotkania(dzien);
		}
		wypiszDzisiejszyStan();
	}

	private void zmienStanAgentow() {
		ArrayList<Agent> kostnica = new ArrayList<>();

		for (Agent agent : agenci) {
			if (agent.stan() == Stan.CHORY) {
				if (agent.umrzyj(this.generator)) {
					chorzy--;
					ArrayList<Agent> znajomi = agent.znajomi();
					for (Agent znajomy : znajomi) {
						znajomy.znajomi().remove(agent);
					}
					kostnica.add(agent);
				}
				else if (agent.wyzdrowiej(this.generator)) {
					chorzy--;
					uodpornieni++;
					agent.zmienStan(Stan.ODPORNY);
				}
			}
		}

		agenci.removeAll(kostnica);
		kostnica.clear();
	}

	private void zaplanujSpotkania(int dzien) {
		if (dzien != parametry.liczbaDni()) {
			for (Agent agent : agenci) {
				while (agent.spotkajSie(this.generator) && !(agent.znajomi().isEmpty())) {
					Agent znajomy = agent.losujZnajomego(this.generator);
					int dataSpotkania = generator.nextInt(parametry.liczbaDni() - dzien) + dzien + 1;
					spotkania.add(new Spotkanie(agent, znajomy, dataSpotkania));
				}
			}
		}
	}

	private void przeprowadzSpotkania(int dzien) {
		boolean today = true;
		while (!(spotkania.isEmpty()) && today) {
			Spotkanie spotkanie = spotkania.poll();

			if (spotkanie.dzien() == dzien) {
				if (agenci.contains(spotkanie.agent1()) && agenci.contains(spotkanie.agent2())) {
					if (spotkanie.przeprowadzSpotkanie(this.generator)) {
						chorzy++;
						zdrowi--;
					}
				}
			}
			else {
				spotkania.add(spotkanie);
				today = false;
			}
		}
	}

	private void nowaSiecSpolecznosciowa() {
		for (int i = 1; i <= parametry.liczbaAgentow(); i++) {
			losujNowegoAgenta(i);
		}

		int liczbaZnajomosci = Math.round((parametry.srZnajomych() * parametry.liczbaAgentow()) / 2);
		ArrayList<Znajomosc> potencjalneZnajomosci = mozliweZnajomosci();
		int i = 0;
		while (i < liczbaZnajomosci) {
			losujNowaZnajomosc(potencjalneZnajomosci);
			i++;
		}
		uzupelnijGraf();

		int j = 0;
		while (j < zarazeni) {
			if (losujChorego()) {
				j++;
			}
		}
		uzupelnijListeAgentow();
	}

	private void losujNowegoAgenta(int i) {
		Agent agent = null;
		if (generator.nextDouble() < parametry.prawdTowarzyski()) {
			agent = new AgentTowarzyski(i, parametry);
		}
		else {
			agent = new AgentZwykly(i, parametry);
		}
		agenci.add(agent);
	}

	private void losujNowaZnajomosc(ArrayList<Znajomosc> potencjalneZnajomosci) {
		Znajomosc znajomosc = potencjalneZnajomosci.remove(generator.nextInt(potencjalneZnajomosci.size()));
		Agent agent1 = agenci.get(znajomosc.kto());
		Agent agent2 = agenci.get(znajomosc.kogo());
		agent1.znajomi().add(agent2);
		agent2.znajomi().add(agent1);
	}

	private boolean losujChorego() {
		int chory = generator.nextInt(parametry.liczbaAgentow());
		if (agenci.get(chory).stan() != Stan.CHORY) {
			agenci.get(chory).zmienStan(Stan.CHORY);
			return true;
		}
		else {
			return false;
		}
	}

	private ArrayList<Znajomosc> mozliweZnajomosci() {
		ArrayList<Znajomosc> znajomosci = new ArrayList<>();
		for (int i = 0; i < parametry.liczbaAgentow(); i++) {
			for (int j = i + 1; j < parametry.liczbaAgentow(); j++) {
				znajomosci.add(new Znajomosc(i, j));
			}
		}
		return znajomosci;
	}

	private void uzupelnijGraf() {
		this.graf += "# graf\n";

		for (Agent agent : agenci) {
			this.graf += (agent.id() + " ");
			for (Agent znajomy : agent.znajomi()) {
				this.graf += (znajomy.id() + " ");
			}
			this.graf += ('\n');
		}
		this.graf += '\n';
	}

	public String graf() {
		return graf;
	}

	private void uzupelnijListeAgentow() {
		this.agenciDoRaportu += "# agenci jako: id typ lub id* typ dla chorego\n";

		for (Agent agent : agenci) {
			this.agenciDoRaportu += (agent.toString() + '\n');
		}
		this.agenciDoRaportu += '\n';
	}

	public String listaAgentow() {
		return agenciDoRaportu;
	}

	private void wypiszDzisiejszyStan() {
		this.licznoscKolejneDni += (this.zdrowi + " " + this.chorzy + " " + this.uodpornieni + '\n');
	}

	public String licznoscKolejneDni() {
		return licznoscKolejneDni;
	}
}
