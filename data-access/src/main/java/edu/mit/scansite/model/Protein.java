package edu.mit.scansite.model;

public class Protein {
    private String proteinIdentifier;
    private int taxaId;
    private String proteinClass;
    private double proteinMolecularWeight;
    private double proteinPI;
    private double proteinPiPhos1;
    private double proteinPiPhos2;
    private double proteinPiPhos3;
    private String proteinSequence;

    public String getProteinIdentifier() {
        return proteinIdentifier;
    }

    public void setProteinIdentifier(String proteinIdentifier) {
        this.proteinIdentifier = proteinIdentifier;
    }

    public int getTaxaId() {
        return taxaId;
    }

    public void setTaxaId(int taxaId) {
        this.taxaId = taxaId;
    }

    public String getProteinClass() {
        return proteinClass;
    }

    public void setProteinClass(String proteinClass) {
        this.proteinClass = proteinClass;
    }

    public double getProteinMolecularWeight() {
        return proteinMolecularWeight;
    }

    public void setProteinMolecularWeight(double proteinMolecularWeight) {
        this.proteinMolecularWeight = proteinMolecularWeight;
    }

    public double getProteinPI() {
        return proteinPI;
    }

    public void setProteinPI(double proteinPI) {
        this.proteinPI = proteinPI;
    }

    public double getProteinPiPhos1() {
        return proteinPiPhos1;
    }

    public void setProteinPiPhos1(double proteinPiPhos1) {
        this.proteinPiPhos1 = proteinPiPhos1;
    }

    public double getProteinPiPhos2() {
        return proteinPiPhos2;
    }

    public void setProteinPiPhos2(double proteinPiPhos2) {
        this.proteinPiPhos2 = proteinPiPhos2;
    }

    public double getProteinPiPhos3() {
        return proteinPiPhos3;
    }

    public void setProteinPiPhos3(double proteinPiPhos3) {
        this.proteinPiPhos3 = proteinPiPhos3;
    }

    public String getProteinSequence() {
        return proteinSequence;
    }

    public void setProteinSequence(String proteinSequence) {
        this.proteinSequence = proteinSequence;
    }
}
