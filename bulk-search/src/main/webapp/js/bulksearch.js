// ##### PROTEIN SCAN #####

// SHARED VARIABLES
var selectedTab = document.getElementById('menu1'),
    addedEntries = [],
    displayedEntries = [];


// PROTEIN IDENTIFIER CHECK BOX
$('#multipleProteinIdentifierCheckBox').change(function() {
    if ($(this).is(':checked')) {
        document.getElementById("singleProteinIdentifierInput").disabled = true;
        document.getElementById("multipleProteinIdentifierInput").disabled = false;
    } else {
        document.getElementById("singleProteinIdentifierInput").disabled = false;
        document.getElementById("multipleProteinIdentifierInput").disabled = true;
    }
});

// REFERENCE PROTEOME CHECK BOX
$('#referenceProteomeCheckBox').change(function() {
    if ($(this).is(':checked')) {
        document.getElementById("refProteomeSelect").disabled = false;
    } else {
        document.getElementById("refProteomeSelect").disabled = true;
    }
});


// PROTEIN SCAN ADD ENTRY BUTTON
$('#addIdentifierSourceEntryButton').click(function () {
    addInputData();
});


$('#addSequenceSourceEntryButton').click(function () {
    addInputData();
});


$('#addFileSourceEntryButton').click(function () {
    var fileInput = document.getElementById("proteinScanInputFile");
    if (fileInput.value && !$(this).attr('disabled')) {
        fileInput.disabled = true;
        $(this).attr("disabled", "true");
        addInputData();
    }
});

function addInputData() {
    var entryData = "entry:{",
        displayData = "";
    processedIdentifiers = [];

    if ($('#menu2Selector').attr('aria-expanded') === "true") {
        debugger;
        entryData += "type:sequence, ";
        displayData += "Sequence: ";
        var proteinSequenceName = document.getElementById('proteinSequenceName'),
            proteinScanSequenceTextArea = document.getElementById('proteinScanSequenceTextArea'),
            sequenceName = proteinSequenceName.value,
            proteinSequence = proteinScanSequenceTextArea.value;

        if (!sequenceName) {
            sequenceName = "Custom Protein"
        }
        if (!proteinSequence) {
            alert("Missing protein sequence!");
            return;
        } else {
            proteinSequenceName.value = "";
            proteinScanSequenceTextArea.value = "";
        }
        entryData += "name:" + sequenceName.toString() + ", " + "seq:" + proteinSequence;
        displayData += proteinSequence;

    } else if ($('#menu3Selector').attr('aria-expanded') === "true") {
        selectedTab = document.getElementById('menu3');
        entryData += "type:file, ";

        var fullPath = document.getElementById('proteinScanInputFile').value,
            startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/')),
            filename = fullPath.substring(startIndex);
        if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
            filename = filename.substring(1);
        }

        entryData += "filename:" + filename;
        displayData += "File Input: Filename = " + filename;
    } else {
        // if ($('#menu1Selector').attr('aria-expanded') === "true")
        var dataSource = $('#proteinDataSourceSelect').find(':selected').text();
        entryData += "type:identifiers, dataSource:" + dataSource + ", ";
        if (!document.getElementById('multipleProteinIdentifierCheckBox').checked) {
            var singleProteinIdentifierInput = document.getElementById('singleProteinIdentifierInput');
            processedIdentifiers = singleProteinIdentifierInput.value;
            singleProteinIdentifierInput.value = "";
        } else {
            var multipleProteinIdentifierInput = document.getElementById('multipleProteinIdentifierInput');
            var identifiers = multipleProteinIdentifierInput.value.split('\n');
            multipleProteinIdentifierInput.value = "";
            $.each(identifiers, function (i, item) {
                var buffer = item.split(',');
                $.each(buffer, function (j, subitem) {
                    processedIdentifiers.push(subitem.trim());
                });
            });
        }
        if (!processedIdentifiers) {
            console.log("Missing identifiers");
            return;
        } else {
            console.log("Identifiers: " + processedIdentifiers.toString());
            entryData += "identifiers:[" + processedIdentifiers.toString() + "]";
            displayData += "Identifier(s): " + processedIdentifiers.toString();
        }

    }


    entryData += "}";

    addedEntries.push(entryData);
    displayedEntries.push(displayData);
    updateAddedEntriesContent();
}

function updateAddedEntriesContent() {
    var entriesHtml = "",
        entryHtml;
   $.each(displayedEntries, function (i, item) {
       if (item.substr(0, 4) === "File") {
           entryHtml = '<div id="addedEntries" class="row select-entry">'
               +'<input class="form-control proteinScanRemoveEntryButton" type="text" size="80px" readonly value="'
               + item + '">'
               + '</div>';
       } else {
           entryHtml = '<div id="addedEntries" class="row select-entry">'
               +'<input class="form-control proteinScanRemoveEntryButton" type="text" size="80px" readonly value="'
               + item + '">'
               + '&nbsp;<button onclick="removeButtonAction(this.name)" name="' + i +'" class="btn btn-default" type="button">Remove</button> '
               + '</div>';
       }

      entriesHtml += entryHtml;
   });

    $('#addedEntries').html(entriesHtml + "<br/>");
}


function removeButtonAction(name) {
    var buffer = [],
        displayBuffer = [];
    for (var i = 0; i < addedEntries.length; i += 1) {
        if (name.toString() !== i.toString()) {
            buffer.push(addedEntries[i]);
            displayBuffer.push(displayedEntries[i]);
        }
    }
    addedEntries = buffer;
    displayedEntries = displayBuffer;
    updateAddedEntriesContent();
}
