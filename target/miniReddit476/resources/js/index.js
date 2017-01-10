function openModal(modalId) {
	$('#'+modalId).openModal();
}

function attachCommentValue(e){
	var thisForm = e.parentNode.parentNode.parentNode;
	var post = thisForm.childNodes[3].childNodes[5].childNodes[1].childNodes[0].value.trim();
	$(thisForm).attr('action',$(thisForm).attr('action') + "?comments="+post);
}
function openCommentModal(e){
	$(e).next().openModal();
}