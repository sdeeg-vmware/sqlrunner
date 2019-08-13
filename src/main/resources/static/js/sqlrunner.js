
/*
   Ask the server for information about the available data sources.
*/
function initializePage() {
    $.ajax({
        url: "/getDataSources",
        type: "GET",
        complete: processDataSourceList
    });
}

/*
   Look at the result, if there's only a single source write it as
   a string.  If more than one create a select that allows switching.
*/
function processDataSourceList(data) {
    var responseData = JSON.parse(data.responseText);
    var sel = document.getElementById("data-source-list");
    $.each(responseData, function(i,val) {
        var opt = document.createElement("option");
        opt.text = val;
        sel.add(opt);
    });
}

function submitSQL() {
    var data = { "ds": $("#data-source-list").val(), "sql": $("#sqlTextArea").val() };
    $.ajax({
        url: "/runsql",
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        complete: sqlRunResponse
    });
}

function sqlRunResponse(data) {
    var responseData = JSON.parse(data.responseText);
    var table = "<table>";
    var tableHeadings;
    $.each(responseData, function(i,val) {
        if(!tableHeadings) {
            tableHeadings = Object.keys(responseData[0]);
            table += "<tr>";
            $.each(tableHeadings, function(i,val) {
                table += "<th>"+val+"</th>";
            });
            table += "</tr>";
        }
        table += "<tr>";
        $.each(tableHeadings, function(i2,key) {
            table += "<td>"+val[key]+"</td>";
        });
        table += "</tr>";
    });
    table += "</table>"

    $("div.sqlrunner_output_area").html(table);
}
