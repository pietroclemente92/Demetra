package italy.company.pietroclemente92.demetra.helpers;

public class VegetableHelperClass extends ProductHelperClass{
    private boolean pesticide, km;

    public VegetableHelperClass(String idUsername, String name, String type, String release, String region, double price, int count, boolean pesticide, boolean km, String dataInsert) {
        super(idUsername, name, type, release, region, price, count, dataInsert);
        this. pesticide = pesticide;
        this.km = km;
    }

    public boolean getPesticide() {
        return pesticide;
    }

    public boolean getKm() {
        return km;
    }
}
