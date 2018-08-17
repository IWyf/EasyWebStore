/**
 * 
 */

$(function(){
		$("#username").blur(function(){
			var value = this.value;
			$(this).parent().find("#onError").remove();
			$("#onError").text("");
			$.post("/store_v5/FindUser", {username:value}, function(data, status){
				if(data == 0){
					$("#divUsername").append("<span id='onError' style='color:red'>用户名不存在</span>");
				}
			});
		});
		
		$("form").submit(function(){
			$("#username").trigger("blur");
			if($("#onError").length > 0){
				return false;
			}else{
				return true;
			}
		});
});