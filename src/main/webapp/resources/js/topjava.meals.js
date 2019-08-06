$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },

                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );
});

function filterTable() {
    let filter = $("#filter");
    let serialize = filter.serialize();
    $.get(context.ajaxUrl + "filter/?" + serialize, function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
    successNoty("Filter");
}

function saveWithFilter() {
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        filterTable();
        successNoty("Saved");
    });
}

function cancelFilter() {
    let filter = $("#filter");
    filter.find(":input").val("");
    updateTable();
    successNoty("Cancel");
}