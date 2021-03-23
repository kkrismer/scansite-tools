package edu.mit.scansite.model;

public class Motif {
    private int motifId;
    private String motifDisplayName;
    private String motifShortName;
    private int motifGroupId;
    private String userEmail;
    private double motifOptimalScore;
    private boolean motifIsPublic;
    private String motifClass;

    public Motif() {
    }

    public int getMotifId() {
        return motifId;
    }

    public void setMotifId(int motifId) {
        this.motifId = motifId;
    }

    public String getMotifDisplayName() {
        return motifDisplayName;
    }

    public void setMotifDisplayName(String motifDisplayName) {
        this.motifDisplayName = motifDisplayName;
    }

    public String getMotifShortName() {
        return motifShortName;
    }

    public void setMotifShortName(String motifShortName) {
        this.motifShortName = motifShortName;
    }

    public int getMotifGroupId() {
        return motifGroupId;
    }

    public void setMotifGroupId(int motifGroupId) {
        this.motifGroupId = motifGroupId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getMotifOptimalScore() {
        return motifOptimalScore;
    }

    public void setMotifOptimalScore(double motifOptimalScore) {
        this.motifOptimalScore = motifOptimalScore;
    }

    public boolean isMotifIsPublic() {
        return motifIsPublic;
    }

    public void setMotifIsPublic(boolean motifIsPublic) {
        this.motifIsPublic = motifIsPublic;
    }

    public String getMotifClass() {
        return motifClass;
    }

    public void setMotifClass(String motifClass) {
        this.motifClass = motifClass;
    }
}
