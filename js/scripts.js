$(document).ready(function (e) {

    var searchLabel = $('#search-issue-top').attr('rel');

    var dtable = $('#issuesList').DataTable({"order": [[0, 'desc']], "bDestroy": true, "searching": true});

    var ptable = $('#projectList').DataTable({"order": [[0, 'desc']], "bDestroy": true, "searching": true});

    var dutable = $('#usersList').DataTable({"order": [[0, 'desc']], "bDestroy": true, "searching": true});

    $('#logtimepicker').datetimepicker({
        pickDate: false,
        dayViewHeaderFormat: 'H'
    });

    $('.selectpicker').selectpicker();

    $.cookie.json = true;

    var selectedFilters = $.cookie('filterFlow');
    if (selectedFilters) {
        var parsedJSON = JSON.parse(selectedFilters);
        console.log(parsedJSON);
        for (var i = 0; i < parsedJSON.length; i++) {
            if (parsedJSON[i].filter != 'all') {
                dtable.column(parsedJSON[i].col).search(parsedJSON[i].filter).draw();
                $('#search-issue-top option[value="' + parsedJSON[i].filter + '"]').attr('selected', 'selected');
            }
        }
    }

    $('#search-issue-top').multiselect({
        buttonText: function (options, select) {
            var sValue = $('.dataTables_filter input').val();
            dtable.search('').columns().search('').draw();
            $('.dataTables_filter input').val(sValue.replace('"', ""));

            if (options.length === 0) {
                $.removeCookie('filterFlow');
                return searchLabel;
            }
            else {
                var labels = [];
                var filters = [];
                var clearLabels = [];
                dtable.search('').columns().search('').draw();
                options.each(function () {

                    var labelType = 'label-info';

                    var $filter = $(this).val().replace('"', "");
                    var $col = $(this).attr('data-col');

                    if ($(this).attr('label-type') !== undefined) {
                        labelType = $(this).attr('label-type').replace('"', "");
                    }

                    if ($(this).attr('label') !== undefined) {
                        labels.push('<span class="label ' + labelType + '">' + $(this).attr('label') + '</span>');
                    }
                    else {
                        labels.push('<span class="label ' + labelType + '">' + $(this).html() + '</span>');
                    }

                    filters.push({col: $col, filter: $filter});

                    if ($filter == 'all') {
                        clearLabels.push('<span class="label ' + labelType + '">' + $(this).html() + '</span>');
                    }
                    else {
                        //console.log($filter);
                        dtable.column($col).search($filter).draw();
                        $('.dataTables_filter input').val(sValue);
                    }

                });
                $.removeCookie('filterFlow');
                $.cookie('filterFlow', JSON.stringify(filters));

                if (clearLabels.length > 0) {
                    dtable.search('').columns().search('').draw();
                    $('.dataTables_filter input').val(sValue);
                    return searchLabel + ':&nbsp;' + clearLabels.join('&nbsp;') + ' ';
                } else {
                    return searchLabel + ':&nbsp;' + labels.join('&nbsp;') + ' ';
                }
            }
        }
    });

    var $scrollTree = $('.scroll-tree');

    if ($scrollTree.length && !$scrollTree.hasClass('collapsed')) {
        slimScrollInit($scrollTree);
    }

    var $logtimepicker = $('.logtimepicker');

    if ($logtimepicker.length) {
        $logtimepicker.datetimepicker({
            format: 'YYYY-MM-DD',
            sideBySide: true
        });
    }

    var $logtimerange = $('.logtimerange');

    var d = new Date();
    var month = d.getMonth();
    var day = d.getDate();
    var year = d.getFullYear();
    var defaultTime = new Date(year, month, day, 00, 00);

    if ($logtimerange.length) {
        $logtimerange.datetimepicker({
            format: 'HH:mm',
            sideBySide: true,
            defaultDate: defaultTime
        });
    }

    var $isactivecheck = $(".switcher");

    if ($isactivecheck.length) {
        $.fn.bootstrapSwitch.defaults.size = 'small';
        $isactivecheck.bootstrapSwitch();

    }

});

function slimScrollInit($scrollList) {
    $scrollList.slimScroll({height: '290px'});
}
function destroySlimScroll($scrollList) {
    $scrollList.parent().replaceWith($scrollList);
}