/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function searchIssueCall(){
 $.ajax({
  type: "post",
  url: "http://localhost:8084/theFlow/issues/search",
  cache: false,    
  data:'issue_criteria=' + $("#issue_criteria").val(),
  success: function(response){
      alert(response);
    $("#issues").empty();
    var obj = JSON.parse(response);
        $.each(obj, function(i, issue) {
            $("#issues").append("<li class=\"btn-info\" role=\"presentation\" id='" + issue.id + "' >" + issue.title + "<li>");
        });
    },
  error: function(){      
    alert('Error while request..');
  }
 });
}

