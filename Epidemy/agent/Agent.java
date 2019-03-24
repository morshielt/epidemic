package agent;

import dane.Dane;

import java.util.ArrayList;
import java.util.Random;

public abstract class Agent implements Comparable<Agent> {
	protected int id;
	protected ArrayList<Agent> znajomi;
	protected Stan stan;
	protected double prawdSpotkania;
	protected double prawdZarazenia;
	protected double prawdWyzdrowienia;
	protected double smiertelnosc;
	protected String typ;

	public Agent(int id, Dane parametry) {
		this.id = id;
		this.stan = Stan.ZDROWY;
		this.znajomi = new ArrayList<Agent>();
		this.prawdSpotkania = parametry.prawdSpotkania();
		this.prawdZarazenia = parametry.prawdZarazenia();
		this.prawdWyzdrowienia = parametry.prawdWyzdrowienia();
		this.smiertelnosc = parametry.smiertelnosc();
	}

	public void zmienStan(Stan stan) {
		if (stan == Stan.CHORY) {
			this.stan = Stan.CHORY;
		}
		else if (stan == Stan.ODPORNY) {
			this.stan = Stan.ODPORNY;
			this.prawdZarazenia = 0;
		}
	}

	public Agent losujZnajomego(Random generator) {
		int liczbaZnajomych = znajomi.size();
		if (liczbaZnajomych > 0) {
			return znajomi.get(generator.nextInt(liczbaZnajomych));
		}
		return null;
	}

	public Stan stan() {
		return stan;
	}

	public boolean umrzyj(Random generator) {
		return (generator.nextDouble() < this.smiertelnosc);
	}

	public boolean wyzdrowiej(Random generator) {
		return (generator.nextDouble() < this.prawdWyzdrowienia);
	}

	public boolean zachoruj(Random generator) {
		return (generator.nextDouble() < this.prawdZarazenia);
	}

	public boolean spotkajSie(Random generator) {
		return (generator.nextDouble() < this.prawdSpotkania);
	}

	public ArrayList<Agent> znajomi() {
		return this.znajomi;
	}

	@Override
	public String toString() {
		String agent = String.valueOf(this.id);
		if (this.stan == Stan.CHORY) {
			agent += "*";
		}
		agent += " " + this.typ;

		return agent;
	}

	@Override
	public int compareTo(Agent o) {
		if (o.id() > this.id) {
			return -1;
		}
		else return 1;
	}

	public int id() {
		return id;
	}
}
