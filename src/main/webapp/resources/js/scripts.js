$(document).ready(function(e){
	
	var searchLabel =$('#search-issue-top').attr('rel');
	
	$('#issuesList').dataTable();
	
	$('#logtimepicker').datetimepicker({
    	pickDate: false,
		dayViewHeaderFormat:'H'
    });

	$('.selectpicker').selectpicker();
	
	$('#search-issue-top').multiselect({
		buttonText: function(options, select) {
			if (options.length === 0) { return searchLabel; }
			else {
				var labels = [];
				var clearLabels = [];
				options.each(function() {
				
				var labelType = 'label-info';	
				
				if ($(this).attr('label-type') !== undefined) { labelType = $(this).attr('label-type'); }
					
				if ($(this).attr('label') !== undefined) {
					labels.push('<span class="label '+labelType+'">'+$(this).attr('label')+'</span>');
				}
				else {
					labels.push('<span class="label '+labelType+'">'+$(this).html()+'</span>');
				}
				
				if($(this).val() == 'all') { clearLabels.push('<span class="label '+labelType+'">'+$(this).html()+'</span>'); }
				
			});
			if(clearLabels.length > 0) {
				return searchLabel + ':&nbsp;' + clearLabels.join('&nbsp;') + ' ';				
			} else {
				return searchLabel + ':&nbsp;' + labels.join('&nbsp;') + ' ';
			}			
			}
		}
	});
	
	var $scrollTree = $('.scroll-tree');
	
	if($scrollTree.length && !$scrollTree.hasClass('collapsed')) {
		slimScrollInit($scrollTree);
	}
	
	var $logtimepicker = $('#logtimepicker');
	
	if($logtimepicker.length) {
		$logtimepicker.datetimepicker({
			pickDate: false,
			sideBySide: true
		});
	}
	
	var $issuetimepicker = $('#issuetimepicker');
	
	if($issuetimepicker.length) {
		$issuetimepicker.datetimepicker({
			pickDate: false,
			sideBySide: true
		});
	}
	
	var $isactivecheck = $(".switcher");
	
	if($isactivecheck.length) {
		$.fn.bootstrapSwitch.defaults.size = 'small';
		$isactivecheck.bootstrapSwitch();
		
	}
	
});

function slimScrollInit($scrollList) {
	$scrollList.slimScroll({ height : '290px' });
}
function destroySlimScroll($scrollList) {
	$scrollList.parent().replaceWith($scrollList);
}