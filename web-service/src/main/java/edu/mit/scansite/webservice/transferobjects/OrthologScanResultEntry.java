package edu.mit.scansite.webservice.transferobjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Thomas on 3/10/2017.
 */
@XmlRootElement
public class OrthologScanResultEntry implements Serializable {
    private static final long serialVersionUID = 4239126027755862577L;

    private String proteinName;
    private String annotation;
    private Double molWeight;
    private Double pI;
    private MotifSite [] predictedSite;

    public OrthologScanResultEntry() {
    }

    public OrthologScanResultEntry(String proteinName, String annotation, Double molWeight, Double pI, MotifSite[] predictedSite) {
        this.proteinName = proteinName;
        this.annotation = annotation;
        this.molWeight = molWeight;
        this.pI = pI;
        this.predictedSite = predictedSite;
    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Double getMolWeight() {
        return molWeight;
    }

    public void setMolWeight(Double molWeight) {
        this.molWeight = molWeight;
    }

    public Double getpI() {
        return pI;
    }

    public void setpI(Double pI) {
        this.pI = pI;
    }

    public MotifSite[] getPredictedSite() {
        return predictedSite;
    }

    public void setPredictedSite(MotifSite[] predictedSite) {
        this.predictedSite = predictedSite;
    }
}
