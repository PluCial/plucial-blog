//open main menu
function open_main_menu() {
	$("#main-menu").animate({
        width: 'toggle'
    }, 150);
	return false;
}

//pop のリセット(共通)
function ajax_start() {
	$('#lean_overlay').css({"display":"block", "display":"-moz-box", "display":"-webkit-box"});
}

// pop のリセット(共通)
function reset_pop() {
	$('#result-box').css({"display":"none"});
	$('#lean_overlay').css({"display":"none"});
}

// 正常終了(共通)
function result_success(result) {
	$('#lean_overlay').css({"display":"none"});
	$('#result-value-box').html(result);
	$('#result-box').css({"display":"block", "display":"-moz-box", "display":"-webkit-box"});
}

// 異常終了(共通)
function result_error() {
	$('#lean_overlay').css({"display":"none"});
	$('#result-box').html('処理失敗しました。時間をおいてから再度実行してください。');
	$('#result-box').css({"display":"block", "display":"-moz-box", "display":"-webkit-box"});
}

//もっと読む
function read_more(href) {
	ajax_start();
	$.ajax({
		type : 'GET',
		url : href,
		dataType : 'html',
		timeout : 1000000000,
		success : function(result) {
			$('#activity-section').append(result);
			reset_pop();
		},
		error : function() {
			reset_pop();
		}
	});
};