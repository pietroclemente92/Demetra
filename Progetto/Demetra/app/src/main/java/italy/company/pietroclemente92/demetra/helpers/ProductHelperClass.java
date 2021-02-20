package italy.company.pietroclemente92.demetra.helpers;

public class ProductHelperClass {

    String idUsername, name, type, release, region;
    String dataInsert;
    double price;
    int count;

    public ProductHelperClass(String idUsername, String name, String type, String release, String region, double price, int count, String dataInsert) {
        this.idUsername = idUsername;
        this.name = name;
        this.type = type;
        this.release = release;
        this.region = region;
        this.price = price;
        this.count = count;
        this.dataInsert = dataInsert;
    }

    public String getIdUsername() {
        return idUsername;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRelease() {
        return release;
    }

    public String getRegion() { return region; }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getDataInsert() { return dataInsert; }
}