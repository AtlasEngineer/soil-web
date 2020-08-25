//时间计算
function DateAdd(interval, number, date) {
	switch (interval) {
	case "y": {
		date.setFullYear(date.getFullYear() + number);
		return date;
		break;
	}
	case "M": {
		date.setMonth(date.getMonth() + number);
		return date;
		break;
	}
	case "w": {
		date.setDate(date.getDate() + number * 7);
		return date;
		break;
	}
	case "d": {
		date.setDate(date.getDate() + number);
		return date;
		break;
	}
	case "h": {
		date.setHours(date.getHours() + number);
		return date;
		break;
	}
	case "m": {
		date.setMinutes(date.getMinutes() + number);
		return date;
		break;
	}
	case "s": {
		date.setSeconds(date.getSeconds() + number);
		return date;
		break;
	}
	default: {
		date.setDate(d.getDate() + number);
		return date;
		break;
	}
	}
}
//时间格式化
function dateFtt(fmt,date)   
{ 
  var o = {   
	"M+" : date.getMonth()+1,                 //月份   
	"d+" : date.getDate(),                    //日   
	"h+" : date.getHours(),                   //小时   
	"m+" : date.getMinutes(),                 //分   
	"s+" : date.getSeconds(),                 //秒   
	"q+" : Math.floor((date.getMonth()+3)/3), //季度   
	"S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
	fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
	if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 