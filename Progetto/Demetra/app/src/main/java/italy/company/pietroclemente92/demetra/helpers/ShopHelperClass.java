package italy.company.pietroclemente92.demetra.helpers;

public class ShopHelperClass {
    String idUsername;
    String fullName, address, city, nationality, region, phoneNumber, site;

    public ShopHelperClass(String idUsername, String fullName, String address, String city, String nationality, String region, String phoneNumber, String site) {
        this.idUsername = idUsername;
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.nationality = nationality;
        this.region = region;
        this.phoneNumber = phoneNumber;
        this.site = site;
    }

    public String getIdUsername() {
        return idUsername;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getNationality() {
        return nationality;
    }

    public String getRegion() { return region; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSite() {
        return site;
    }
}
