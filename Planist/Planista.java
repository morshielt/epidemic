import java.util.Scanner;
import java.io.File;

public class Planista {

	public static void main(String[] args) {
		String plik = "";

		if (args.length != 0) {
			plik = args[0];
		}
		else {
			Scanner skaner = new Scanner(System.in);
			plik = skaner.nextLine();
		}

		if (new File(plik).isFile()) {
			Bufor bufor = new Bufor();
			Dane dane = bufor.pobierzDaneZPliku(plik);
			Symulator symulator = new Symulator(dane);

			symulator.przeprowadzSymulacje();
		}
		else System.out.println("Plik z danymi nie jest dostępny.");
	}
}
