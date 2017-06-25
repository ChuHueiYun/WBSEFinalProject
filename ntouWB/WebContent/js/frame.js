$("#title").append("<center><img class='img-responsive' src='img/title.png' /></center>");
//$("#title").css("margin-bottom", "20vh");		//先放空的

//棕色menu
$("#menu").append(
"					<nav class='navbar navbar-default'>" + "\n" + 
"						<div class='container-fluid'>" + "\n" + 
"							<div class='navbar-header'>" + "\n" + 
"								<button type='button' class='navbar-toggle' data-toggle='collapse' data-target='#myNavbar'>" + "\n" + 
"									<span class='icon-bar'></span>" + "\n" + 
"									<span class='icon-bar'></span>" + "\n" + 
"									<span class='icon-bar'></span>" + "\n" + 
"								</button>" + "\n" + 
"								<a class='navbar-brand' href='Homepage.html'>NTOU WB</a>" + "\n" + 
"							</div>" + "\n" + 
"							<div class='collapse navbar-collapse' id='myNavbar'>" + "\n" + 
"								<ul class='nav navbar-nav brownList'>" + "\n" + 
"									<li><a href='Homepage.html'>首頁</a></li>" + "\n" +
"									<li class='dropdown'>" + "\n" + 
"										<a class='dropdown-toggle' data-toggle='dropdown' href='#'>" + "\n" + 
"											比賽數據" + "\n" + 
"											<span class='caret'></span>" + "\n" + 
"										</a>" + "\n" + 
"										<ul class='dropdown-menu'>" + "\n" + 
"											<li><a href='GameRecord.html'>個人數據</a></li>" + "\n" + 
"											<li><a href='TeamGameRecordList.html'>全隊比賽</a></li>" + "\n" + 
"											<li><a href='NewGameRecord.html' id='newRecordLi'>新增數據</a></li>" + "\n" + 
"										</ul>" + "\n" + 
"									</li>" + "\n" + 
"									<li><a href='TrainingLog.html'>訓練日誌</a></li>" + "\n" + 
"									<li><a href='Photo.html'>球隊相簿</a></li>" + "\n" + 
"									<!-- 如果是教練 會多一個隊員管理 -->" + "\n" + 
"								</ul>" + "\n" + 
"								<ul class='nav navbar-nav navbar-right'>" + "\n" + 
"									<li onclick='accountSetting()'><a href='#' >帳戶設定</a></li>" + "\n" + 
"									<li onclick='logout()'><a href='#' >登出</a></li>" + "\n" + 
"								</ul>" + "\n" + 
"							</div>" + "\n" + 
"						</div>" + "\n" + 
"					</nav>");

//根據頁面名稱來加menu的active class
function setActiveClass(){
	switch(window.location.pathname.split("/").pop()) {
	case "Homepage.html":
		$("nav.navbar > div.container-fluid > .navbar-collapse > .navbar-nav:first > li:eq(0)").attr("class", "active");
		break;
	case "GameRecord.html":
	case "MemberGameRecord.html":
	case "TeamGameRecordList.html":
	case "TeamGameRecord.html":
	case "EditTeamGameRecord.html":
	case "NewGameRecord.html":
		$("nav.navbar > div.container-fluid > .navbar-collapse > .navbar-nav:first > li:eq(1)").attr("class", "active");
		break;
	case "TrainingLog.html":
	case "TrainingLogForCoach.html":
		$("nav.navbar > div.container-fluid > .navbar-collapse > .navbar-nav:first > li:eq(2)").attr("class", "active");
		break;
	case "Photo.html":
	case "EditPhoto.html":
		$("nav.navbar > div.container-fluid > .navbar-collapse > .navbar-nav:first > li:eq(3)").attr("class", "active");
		break;
	case "MemberManagement.html":
		$("nav.navbar > div.container-fluid > .navbar-collapse > .navbar-nav:first > li:eq(4)").attr("class", "active");
		break;
	default:
		$("nav.navbar > div.container-fluid > .navbar-collapse > .navbar-nav:first > li:eq(0)").attr("class", "active");
		break;
	}
}


//隊員管理menu連結
$(document).ready(function() {
	//帳戶設定
	$.getScript("js/accountSetting.js");
	
	setActiveClass();

	$.ajax({
		type: "POST",
		url: "LoginServlet",	 
		data: { option : "getIdentity" },
		dataType: "text",
															
		success : function(response){
			if(response=="coach"||response=="leader"){
				$("nav.navbar > div.container-fluid > .navbar-collapse > .navbar-nav:first").append("<li><a href='MemberManagement.html'>隊員管理</a></li>");
				setActiveClass();
			}
		},
		error : function(){
			console.log("server沒有回應");
		}
	});	
});


//登出按鈕
function logout(){
	$.ajax({
		type: "POST",
		url: "LoginServlet",	 
		data: { option : "logout" },
		dataType: "text",
															
		success : function(response){
			if(response=="登出成功"){
				window.location.href='Login.html';
			}
		},
		error : function(){
			console.log("server沒有回應");
		}
	});	
}

/*依身份判斷是否拿掉比賽數據 新增數據*/
$(document).ready(function() {
	$.ajax({
		type: "POST",
		url: "GameRecordServlet",
		data: {
			option: "checkIdentity"
		},
		dataType: "text",
		success: function(response) {
			if(response == "general" || response == "graduated") {
				$("#newRecordLi").remove();
			}
		},
		error: function() {
			console.log("frame.js checkIdentity error\n");
		}
	});
});
