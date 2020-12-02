
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
    var sqlRunResponse = JSON.parse(data.responseText);
    var responseData = sqlRunResponse.data;

    //console.log("Success: "+sqlRunResponse.success+" Message: "+sqlRunResponse.message);
//    var resultTable = "<table>";
//    resultTable += "<tr><td>Success</td><td>"+sqlRunResponse.success+"</td></tr>";
//    resultTable += "<tr><td>Message</td><td>"+sqlRunResponse.message+"</td></tr></table>";
//    $("div.sqlrunner_run_result_area").html(resultTable);
    $("div.sqlrunner_run_result_area").html("<b>Success:</b> "+sqlRunResponse.success+" <b>Message:</b> "+sqlRunResponse.message);

    if(sqlRunResponse.success === true) {
        //console.log("Parsing data: "+responseData)
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
    else {
        //console.log("Success is not true");
        $("div.sqlrunner_output_area").html("<table></table>");
    }

    $("div.sqlrunner_footer").html("end of line");

}
