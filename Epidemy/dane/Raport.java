package dane;

import bledy.BladRaport;
import symulacja.Symulator;

import java.io.*;

public class Raport {
	PrintWriter raport;

	public Raport(String plikZRaportem) throws BladRaport {
		try {
			File raport = new File(plikZRaportem);
			raport.createNewFile();
			this.raport = new PrintWriter(raport, "UTF-8");

		}
		catch (IOException e) {
			throw new BladRaport();
		}
	}

	public void uzupelnijRaport(Dane dane, Symulator symulator) {
		dopisz("# twoje wyniki powinny zawierać te komentarze\n");
		dopisz(dane.parametryDoRaportu());
		dopisz(symulator.listaAgentow());
		dopisz(symulator.graf());
		dopisz(symulator.licznoscKolejneDni());

		this.raport.close();
	}

	private void dopisz(String s) {
		this.raport.print(s);
	}
}

