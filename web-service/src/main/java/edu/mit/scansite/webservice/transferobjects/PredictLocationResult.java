package edu.mit.scansite.webservice.transferobjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Thomas on 3/9/2017.
 */
@XmlRootElement
public class PredictLocationResult implements Serializable {
    private static final long serialVersionUID = -4263860378222876823L;

    private Integer numberProteinLocations;
    private String localization;
    private Integer predictionScore;
    private String[] goTermsCodes;

    public PredictLocationResult() {
    }

    public PredictLocationResult(Integer numberProteinLocations, String localization, Integer predictionScore, String[] goTermsCodes) {
        this.numberProteinLocations = numberProteinLocations;
        this.localization = localization;
        this.predictionScore = predictionScore;
        this.goTermsCodes = goTermsCodes;
    }

    public Integer getNumberProteinLocations() {
        return numberProteinLocations;
    }

    public void setNumberProteinLocations(Integer numberProteinLocations) {
        this.numberProteinLocations = numberProteinLocations;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Integer getPredictionScore() {
        return predictionScore;
    }

    public void setPredictionScore(Integer predictionScore) {
        this.predictionScore = predictionScore;
    }

    public String[] getGoTermsCodes() {
        return goTermsCodes;
    }

    public void setGoTermsCodes(String[] goTermsCodes) {
        this.goTermsCodes = goTermsCodes;
    }
}
