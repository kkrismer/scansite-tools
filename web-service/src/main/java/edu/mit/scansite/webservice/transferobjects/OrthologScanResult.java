package edu.mit.scansite.webservice.transferobjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Thomas on 3/9/2017.
 */
@XmlRootElement
public class OrthologScanResult implements Serializable {
    private static final long serialVersionUID = 6129165072131138851L;

    private OrthologScanResultEntry[] orthologousProteins;
    private String sequenceAlignment;

    public OrthologScanResult() {}

    public OrthologScanResult(OrthologScanResultEntry[] orthologousProteins, String sequenceAlignment) {
        this.orthologousProteins = orthologousProteins;
        this.sequenceAlignment = sequenceAlignment;
    }

    public OrthologScanResultEntry[] getOrthologousProteins() {
        return orthologousProteins;
    }

    public void setOrthologousProteins(OrthologScanResultEntry[] orthologousProteins) {
        this.orthologousProteins = orthologousProteins;
    }

    public String getSequenceAlignment() {
        return sequenceAlignment;
    }

    public void setSequenceAlignment(String sequenceAlignment) {
        this.sequenceAlignment = sequenceAlignment;
    }
}
