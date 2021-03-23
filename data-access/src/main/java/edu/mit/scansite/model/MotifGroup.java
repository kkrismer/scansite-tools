package edu.mit.scansite.model;

public class MotifGroup {
    private int motifGroupId;
    private String motifGroupDisplayName;
    private String motifGroupShortName;

    public MotifGroup() {
    }

    public int getMotifGroupId() {
        return motifGroupId;
    }

    public void setMotifGroupId(int motifGroupId) {
        this.motifGroupId = motifGroupId;
    }

    public String getMotifGroupDisplayName() {
        return motifGroupDisplayName;
    }

    public void setMotifGroupDisplayName(String motifGroupDisplayName) {
        this.motifGroupDisplayName = motifGroupDisplayName;
    }

    public String getMotifGroupShortName() {
        return motifGroupShortName;
    }

    public void setMotifGroupShortName(String motifGroupShortName) {
        this.motifGroupShortName = motifGroupShortName;
    }
}
