var isCoach = false;

$(document).ready(function(){
	$.ajax({
		type: "POST",
		url: "LoginServlet",	 
		data: { option : "getIdentity" },
		dataType: "text",
															
		success : function(response){
			if(response=="coach"){
				isCoach = true;
				initCoachSettingModal();
			}
			else{
				isCoach = false;
				initSettingModal();
			}
		},
		error : function(){
			console.log("server沒有回應");
		}
	});
});

function initCoachSettingModal(){
		$("body").append(
		"<div id='coachSettingModal' class='modal fade' role='dialog'>" +
			"<div class='modal-dialog modal-sm'>" +
				"<div class='modal-content'>" +
					"<div class='modal-header'>" +
						"<button type='button' class='close' data-dismiss='modal'>&times;</button>" +
						"<h4 class='modal-title'>帳戶設定</h4>" +
					"</div>" +
					"<div class='modal-body'>" +						
						"<label>姓名:</label>" +
  						"<input id='accountSettingName' type='text' class='form-control input-sm' style='margin-bottom: 1vh;'>" +
  						"<h6 id='settingNameMessage' style='color: red;'></h6><hr>" +
  						
	  					"<label>帳號:</label>"  +
	  					"<div class='input-group' style='margin-bottom: 1vh;'>" +
							"<span class='input-group-addon'><i class='glyphicon glyphicon-user'></i></span>" +
							"<input id='accountSettingAccount' type='text' class='form-control' disabled=''>" +
						"</div>" +
							"<label>密碼(介於6~12字元間):</label>"  +
							"<div class='input-group' style='margin-bottom: 1vh;'>" +
								"<span class='input-group-addon'><i class='glyphicon glyphicon-lock'></i></span>" +
								"<input id='accountSettingPassword' type='password' class='form-control'>" +
							"</div>" +
							"<label>確認密碼:</label>"  +
							"<div class='input-group' style='margin-bottom: 1vh;'>" +
								"<span class='input-group-addon'><i class='glyphicon glyphicon-ok-sign'></i></span>" +
								"<input id='accountSecondPassword' type='password' class='form-control'>" +
							"</div>" +
							"<h6 id='settingPasswordMessage' style='color: red;'></h6>" +
					"</div>" +
					"<div class='modal-footer'>" +				
						"<button type='button' class='btn btn-darkBlue' data-dismiss='' onclick='accountSettingSaving();'>儲存設定</button>" +
					"</div>" +
				"</div>" +
			"</div>" +
		"</div>");
}

function initSettingModal(){
	$("body").append(
		"<div id='accountSettingModal' class='modal fade' role='dialog'>" +
			"<div class='modal-dialog modal-sm'>" +
				"<div class='modal-content'>" +
					"<div class='modal-header'>" +
						"<button type='button' class='close' data-dismiss='modal'>&times;</button>" +
						"<h4 class='modal-title'>帳戶設定</h4>" +
					"</div>" +
					"<div class='modal-body'>" +						
						"<label>姓名:</label>" +
  						"<input id='accountSettingName' type='text' class='form-control input-sm' style='margin-bottom: 1vh;'>" +
  						"<h6 id='settingNameMessage' style='color: red;'></h6>" +
  						"<label>身高:</label>" +
  						"<input id='accountSettingHeight' type='number' min='0' max='300' class='form-control input-sm' style='margin-bottom: 1vh;'>" +
  						"<h6 id='settingHeightMessage' style='color: red;'></h6>" +
  						"<label>位置:</label>" +
	  						"<select id='accountSettingPosition' class='form-control input-sm' style='margin-bottom: 1vh;'>" +	    						
	    						"<option value='控球後衛'>控球後衛</option>" +
	    						"<option value='得分後衛'>得分後衛</option>" +
	    						"<option value='小前鋒'>小前鋒</option>" +
	    						"<option value='大前鋒'>大前鋒</option>" +
	    						"<option value='中鋒'>中鋒</option>" +
	  						"</select>" +
	  						"<h6 id='settingPositionMessage' style='color: red;'></h6><hr>" +
	  						"<label>帳號:</label>"  +
	  						"<div class='input-group' style='margin-bottom: 1vh;'>" +
								"<span class='input-group-addon'><i class='glyphicon glyphicon-user'></i></span>" +
								"<input id='accountSettingAccount' type='text' class='form-control' disabled=''>" +
							"</div>" +
							"<label>密碼(介於6~12字元間):</label>"  +
							"<div class='input-group' style='margin-bottom: 1vh;'>" +
								"<span class='input-group-addon'><i class='glyphicon glyphicon-lock'></i></span>" +
								"<input id='accountSettingPassword' type='password' class='form-control'>" +
							"</div>" +
							"<label>確認密碼:</label>"  +
							"<div class='input-group' style='margin-bottom: 1vh;'>" +
								"<span class='input-group-addon'><i class='glyphicon glyphicon-ok-sign'></i></span>" +
								"<input id='accountSecondPassword' type='password' class='form-control'>" +
							"</div>" +
							"<h6 id='settingPasswordMessage' style='color: red;'></h6>" +
					"</div>" +
					"<div class='modal-footer'>" +				
						"<button type='button' class='btn btn-darkBlue' data-dismiss='' onclick='accountSettingSaving();'>儲存設定</button>" +
					"</div>" +
				"</div>" +
			"</div>" +
		"</div>");
}

//帳戶設定
function accountSetting(){
	$('#settingNameMessage').empty();
	$('#settingHeightMessage').empty();
	$('#settingPositionMessage').empty();
	$('#settingPasswordMessage').empty();
	//取得帳戶資訊	
	$.ajax({
		type: "POST",
		url: "AccountSettingServlet",	 
		data: { option : "getAccountInfo" },
		dataType: "json",
															
		success : function(response){
			$("#accountSettingName").val(response[0]["name"]);

			if(!isCoach){
				$("#accountSettingHeight").val(response[0]["height"]);
				$("#accountSettingPosition").val(response[0]["position"]);
			}

			$("#accountSettingAccount").val(response[0]["account"]);
			$("#accountSettingPassword").val(response[0]["password"]);		
			$("#accountSecondPassword").val(response[0]["password"]);
		},
		error : function(){
			console.log("server沒有回應");
		}
	});	
	//Modal出現
	if(isCoach){
		$("#coachSettingModal").modal("show");
	}
	else{
		$("#accountSettingModal").modal("show");
	}	
}

//儲存帳戶設定
function accountSettingSaving(){
	$('#settingNameMessage').empty();
	$('#settingHeightMessage').empty();
	$('#settingPositionMessage').empty();
	$('#settingPasswordMessage').empty();

	var isCorrect = true;

	//檢查輸入
	if($("#accountSettingName").val().length == 0 || $("#accountSettingName").val().length > 15){
		$('#settingNameMessage').append("姓名不符合格式。");
		isCorrect = false;
	}

	if(!isCoach){
		if(parseInt($("#accountSettingHeight").val()) < 0 || parseInt($("#accountSettingHeight").val()) > 300 || $('#accountSettingHeight').val() == ""){
			$('#settingHeightMessage').append("請輸入正確身高。");
			isCorrect = false;
		}
		if($("#accountSettingPosition").val() == null || $('#accountSettingPosition').val() == ""){
			$('#settingPositionMessage').append("請選擇位置。");
			isCorrect = false;
		}
	}

	if($("#accountSettingPassword").val().length < 6 || $("#accountSettingPassword").val().length > 12){
		$('#settingPasswordMessage').append("密碼不符合格式。");
		isCorrect = false;
	}
	else if($("#accountSettingPassword").val() != $("#accountSecondPassword").val()){
		$('#settingPasswordMessage').append("密碼不符。請再輸入一次。");
		isCorrect = false;
	}

	if(isCorrect){
		//alert($("#accountSettingName").val()+"\n"+$("#accountSettingHeight").val()+"\n"+$("#accountSettingPosition").val()+"\n"+$("#accountSettingPassword").val()+"\n");
		$.ajax({
			type: "POST",
			url: "AccountSettingServlet",	 
			data: { 
				name : $("#accountSettingName").val(),
				height : $("#accountSettingHeight").val(),
				position : $("#accountSettingPosition").val(),
				password : $("#accountSettingPassword").val(),
				option : "editAccount"
			},
			dataType: "text",
																
			success : function(response){
				if(response=="儲存帳戶資訊失敗"){
					alert(response);
				}
			},
			error : function(){
				console.log("server沒有回應");
			}
		});
		//隱藏modal
		if(isCoach){
			$("#coachSettingModal").modal("hide");
		}
		else{
			$("#accountSettingModal").modal("hide");
		}
	}
}


