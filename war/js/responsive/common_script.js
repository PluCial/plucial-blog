//open main menu
function open_main_menu() {
	$("#main-menu").animate({
        width: 'toggle'
    }, 150);
	return false;
}

//open main menu
function open_calendar() {
	$('#calendar').calendario({
        caldata : null,
        weeks : [ '日', '月', '火', '水', '木', '金', '土' ],
        weekabbrs : [ '日', '月', '火', '水', '木', '金', '土' ],
    });
	$('#calendar_overlay').css("display","block");
	return false;
}

//open main menu tab
function main_menu_tab_change_to_search() {
	$('#main-menu-tab li a').removeClass('select');
	$('#main-menu-search-tab').addClass('select');

	$("#main-menu-search").css("display","block");
	$("#main-menu-etc").css("display","none");
}

//open main menu tab
function main_menu_tab_change_to_etc() {
	$('#main-menu-tab li a').removeClass('select');
	$('#main-menu-etc-tab').addClass('select');

	$("#main-menu-search").css("display","none");
	$("#main-menu-etc").css("display","block");
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

//アクティビティをもっと読む
function read_more_activity(href) {
	ajax_start();
	$.ajax({
		type : 'GET',
		url : href,
		dataType : 'html',
		timeout : 1000000000,
		success : function(result) {
			$('#activity-contents').append(result);
			$('.fotorama').on('fotorama:show').fotorama();
			reset_pop();
		},
		error : function() {
			reset_pop();
		}
	});
};

//日付リストをもっと読む
function read_more_dates(href) {
	ajax_start();
	$.ajax({
		type : 'GET',
		url : href,
		dataType : 'html',
		timeout : 1000000000,
		success : function(result) {
			$('#navigation-date-list').append(result);
			reset_pop();
		},
		error : function() {
			reset_pop();
		}
	});
};

//ユーザーリストをもっと読む
function read_more_users(href) {
	ajax_start();
	$.ajax({
		type : 'GET',
		url : href,
		dataType : 'html',
		timeout : 1000000000,
		success : function(result) {
			$('#activity-contents').append(result);
			reset_pop();
		},
		error : function() {
			reset_pop();
		}
	});
};