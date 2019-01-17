package no.dervis.pact;

public class Car {

    private String type;

    private String licenseNr;

    public Car() {}

    public Car(String type, String licenseNr) {
        this.type = type;
        this.licenseNr = licenseNr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicenseNr() {
        return licenseNr;
    }

    public void setLicenseNr(String licenseNr) {
        this.licenseNr = licenseNr;
    }

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", licenseNr='" + licenseNr + '\'' +
                '}';
    }
}
