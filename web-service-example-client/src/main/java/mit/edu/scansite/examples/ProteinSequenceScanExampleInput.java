package mit.edu.scansite.examples;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

public class ProteinSequenceScanExampleInput {
    private String identifier;
    private String sequence;
    private String motifClass;
    private String stringency;

    public ProteinSequenceScanExampleInput() {

    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getMotifClass() {
        return motifClass;
    }

    public void setMotifClass(String motifClass) {
        this.motifClass = motifClass;
    }

    public String getStringency() {
        return stringency;
    }

    public void setStringency(String stringency) {
        this.stringency = stringency;
    }
}
