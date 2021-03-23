var availableMotifs = [],
    availableMotifGroups = [],
    selectedMotifs = [],
    selectedMotifGroups = [],
    selectedDataSource = "UniProtKB/Swiss-Prot",
    identifiers,
    dataSourceBlackList = [ "NCBI Protein - GenPept/RefSeq" ]; // too large to allow them for bulk searches
    baseUrl = "/bulksearch/rest"; // SERVER
 // baseUrl = "/rest";            // DEVELOPMENT

function loadDataSourceNames() {
    $.ajax({
        url: baseUrl + "/data-source-display-names",
        success: function(result){
            var options = "";
            $.each(result, function (idx) {
                if (dataSourceBlackList.indexOf(result[idx]) < 0) {
                    options += "<option>" + result[idx] + "</option>";
                }
            });
            if (result.length > 0) {
                selectedDataSource = result[0];
            }
            document.getElementById("proteinDataSourceSelect").innerHTML = options;
        },
        error: function (result) {
            instances = "Could not load data!";
        }
    })
}


function loadMotifNames() {
    $.ajax({
        url: baseUrl + "/motif-display-names",
        success: function(result){
            availableMotifs = [];
            $.each(result, function (idx) {
                availableMotifs.push(result[idx]);
            });
            availableMotifs.sort();
            updateMotifSelection();
        },
        error: function (result) {
            instances = "Could not load data!";
        }
    })
}


function loadMotifGroupNames() {
    $.ajax({
        url: baseUrl + "/motif-group-display-names",
        success: function(result){
            availableMotifGroups = [];
            $.each(result, function (idx) {
                availableMotifGroups.push(result[idx]);
            });
            availableMotifGroups.sort();
            updateMotifGroupSelection();
        },
        error: function (result) {
            instances = "Could not load data!";
        }
    })
}

// CHANGING DATA SOURCE
$('#proteinDataSourceSelect').change(function () {
    debugger;
    selectedDataSource = this.value;
    identifiers.clear();
    identifiers = null;
    $('.tt-menu').remove();

    updateIdentifierSource();
    identifiers.initialize(true);
    defineTypeAhead();
    $('.typeahead.form-control.select-entry.tt-hint.tt-input').remove();
});

// MOTIF AND MOTIF GROUP SELECTION HANDLING
$('#selectMotifs').click(function () {
    if (!this.value) {
        return;
    }
    var index = availableMotifs.indexOf(this.value);
    if (index > -1) {
        availableMotifs.splice(index, 1);
    }
    selectedMotifs.push(this.value);
    selectedMotifs.sort();

    updateMotifSelection();
});

$('#selectMotifGroups').click(function () {
    if (!this.value) {
        return;
    }
    var index = availableMotifGroups.indexOf(this.value);
    if (index > -1) {
        availableMotifGroups.splice(index, 1);
    }
    selectedMotifGroups.push(this.value);
    selectedMotifGroups.sort();

    updateMotifGroupSelection();
});

// DESELECTS
function deselectMotif(name) {
    var index = selectedMotifs.indexOf(name);
    if (index > -1) {
        selectedMotifs.splice(index, 1);
    }
    availableMotifs.push(name);
    availableMotifs.sort();

    updateMotifSelection();
}

function deselectMotifGroup(name) {
    var index = selectedMotifGroups.indexOf(name);
    if (index > -1) {
        selectedMotifGroups.splice(index, 1);
    }
    availableMotifGroups.push(name);
    availableMotifGroups.sort();

    updateMotifGroupSelection();
}

function updateMotifSelection() {
    var remainingAvailable = "",
        selectedEntries = "";
    for (var i = 0; i < availableMotifs.length; i++) {
        remainingAvailable += "<option>" + availableMotifs[i] +"</option>";
    }

    for (var j = 0; j < selectedMotifs.length; j++) {
        selectedEntries += "<button type=\"button\" class=\"btn btn-info\" " +
            "onclick=\"deselectMotif(this.name)\" " +
            "name=\"" + selectedMotifs[j] + "\" " +
            "style=\"margin-bottom: 5px; margin-right: 5px;\">"
            + selectedMotifs[j] + " &nbsp;&#8855;</button>";
    }

    if (remainingAvailable) {
        document.getElementById("selectMotifs").innerHTML = remainingAvailable;
    } else {
        document.getElementById("selectMotifs").innerHTML = "<option disabled>All Motifs Were Selected</option>";
    }

    if (selectedEntries) {
        document.getElementById("selectedMotifs").innerHTML = selectedEntries;
    } else {
        document.getElementById("selectedMotifs").innerHTML = "<p>No Motifs are selected.</p>"
    }
}


function updateMotifGroupSelection() {
    var remainingAvailable = "",
        selectedEntries = "";
    for (var i = 0; i < availableMotifGroups.length; i++) {
        remainingAvailable += "<option>" + availableMotifGroups[i] +"</option>";
    }

    for (var j = 0; j < selectedMotifGroups.length; j++) {
        selectedEntries += "<button type=\"button\" class=\"btn btn-info\" " +
            "onclick=\"deselectMotifGroup(this.name)\" " +
            "name=\"" + selectedMotifGroups[j] + "\" " +
            "style=\"margin-bottom: 5px; margin-right: 5px;\">"
            + selectedMotifGroups[j] + " &nbsp;&#8855;</button>";
    }

    if (remainingAvailable) {
        document.getElementById("selectMotifGroups").innerHTML = remainingAvailable;
    } else {
        document.getElementById("selectMotifGroups").innerHTML = "<option disabled>All Motif Groups Were Selected</option>";
    }

    if (selectedEntries) {
        document.getElementById("selectedMotifGroups").innerHTML = selectedEntries;
    } else {
        document.getElementById("selectedMotifGroups").innerHTML = "<p>No Motif Groups are selected.</p>"
    }
}

// AUTO COMPLETE
function updateIdentifierSource() {
    identifiers = new Bloodhound({
        datumTokenizer: function(data) {
            return Bloodhound.tokenizers.whitespace(data)
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,

        remote: {
            wildcard: '%QUERY',
            url: baseUrl + '/protein-identifiers?searchPattern=%QUERY&dataSource=' + selectedDataSource,
            transform: function(response) {
                return response;
            }
        }
    });
}


function defineTypeAhead() {
    $('#bloodhound .typeahead').typeahead({
            hint: true,
            highlight: true,
            minLength: 3
        },
        {
            limit: 10,
            name: 'identifiers',
            source: identifiers.ttAdapter()
        });
}

updateIdentifierSource();
defineTypeAhead();