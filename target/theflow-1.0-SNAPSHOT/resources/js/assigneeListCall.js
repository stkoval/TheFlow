function assigneeCall(){
 $.ajax({
  type: "post",
  url: "http://localhost:8084/theFlow/projects/getAddedUsers",
  cache: false,    
  data:'issue_criteria=' + $("#issue_criteria").val(),
  success: function(response){
      alert(response);
    },
  error: function(){      
    alert('Error while request..');
  }
 });
}


