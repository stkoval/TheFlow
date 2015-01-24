/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function createIssueCall() {
    var title = $("#title").val();
    var description = $("#description").val();
    var type = $("#type").val();
    var priority = $("#priority").val();
    var assignee_id = $("#assignee_id").val();
    var creator_id = $("#creator_id").val();
    var project_id = $("#project_id").val();
    var estimated_time = $("#estimated_time").val();
    alert(priority);
    $.ajax({
        type: "post",
        url: "http://localhost:8084/theFlow/issues/create",
        cache: false,
        data: {'title' : title, 'description' : description, 'type' : type, 'status' : status, 'priority' : priority, 'assignee_id' : assignee_id, 'creator_id' : creator_id, 'project_id' : project_id, 'estimated_time' : estimated_time},
        success: function () {
            alert('success');
        },
        error: function () {
            alert('Error while request..');
        }
    });
}
