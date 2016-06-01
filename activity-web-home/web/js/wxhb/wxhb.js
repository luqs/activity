function renderHbType(value,row,index){
	if (value == 'NORMAL') {
		return '普通红包';
	} else if (value == 'GROUP') {
		return '裂变红包';
	} else {
		return value;
	}
}

/*function dateFormatter(value,row,index){
	if (value != null) {
		 var dt = new Date(value);
		 return dt.format("yyyy-MM-dd mm:hh:ss")
	} else {
		return value;
	}
}*/

function dateFormatter(value,row,index){
	if (value != null) {
		var now = new Date(value);
		var year = now.getFullYear(); 
		var month = preZeroFill(now.getMonth()+1, 2); 
		var date = preZeroFill(now.getDate(), 2); 
		var hour = preZeroFill(now.getHours(), 2); 
		var minute = preZeroFill(now.getMinutes(),2); 
		var second = preZeroFill(now.getSeconds(), 2); 
		return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
	} else {
		return value;
	}
}

function preZeroFill(num, size) {
    var s = "000000000" + num;
    return s.substr(s.length-size);
}

function renderStatus(value,row,index){
	if (value == 0) {
		return '关闭';
	} else if (value == 1) {
		return '开启';
	} else {
		return '未知';
	}	
}

function rendorAmountType(value,row,index){
	if (value == 'FIX') {
		return '固定金额红包';
	} else if (value == 'RANDOM') {
		return '金额随机红包';
	} else {
		return value;
	}
}

function renderProcessStatus(value,row,index){
	if (value == '0') {
		return '<font style="color:#BF3EFF;">初始化</font>';
	} else if (value == '1') {
		return '<font style="color:yellow;">处理中</font>';
	} else if (value == '2') {
		return '<font style="color:green;">处理成功</font>';
	} else if (value == '3') {
		return '<font style="color:red;">处理失败</font>';
	} else {
		return value;
	}
}