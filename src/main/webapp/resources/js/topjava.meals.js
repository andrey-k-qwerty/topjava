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
    $.get(context.ajaxUrl + "filter/?"+ serialize, function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}