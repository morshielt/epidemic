public class Symulator {
    private Dane dane;

    public Symulator(Dane dane) {
        this.dane = dane;
    }

    public void przeprowadzSymulacje() {
        StrategiaPlanisty fcfs = new FCFS();
        fcfs.przydzielajProcesor(dane);

        StrategiaPlanisty sjf = new SJF();
        sjf.przydzielajProcesor(dane);

        StrategiaPlanisty srt = new SRT();
        srt.przydzielajProcesor(dane);

        StrategiaPlanisty ps = new PS();
        ps.przydzielajProcesor(dane);

        StrategiaPlanisty rr = new RR();
        rr.przydzielajProcesor(dane);
    }
}
