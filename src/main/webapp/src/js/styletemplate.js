var tmp={
	polygonString:function(c){
		var d=stylespz.styles[c];
		if(d.dashArray=='1')
		{
			var sx="selected='selected'";
			var xx="";
		}
		else
		{
			var sx="";
			var xx="selected='selected'";
		}
		return '<div class="pzbox polygonstyle" style="position:initial">'
					+'<div class="headstyle" d-v="'+c+'">'+c+'</div>'
					+'<div class="item xtxz">边线形状'
					+	'<select id="slxtxzxz" style="outline: none;">'
					+		'<option value="1" '+sx+'>实线</option>'
					+		'<option value="4" '+xx+'>虚线</option>'
					+	'</select>'
					+'</div>'
					+'<div class="item mzys">面状颜色<input type="text" readonly="readonly" v="mzs" class="newjscolor" value="'+d.fillColor+'" style="background-color: '+d.fillColor+';"></div>'
					+'<div class="item mztmd">面透明度<input type="text" value="'+d.fillOpacity+'" id="slmztmdxz">0-1</div>'
					+'<div class="item xtys">边线颜色<input type="text" readonly="readonly" v="xzs" class="newjscolor" value="'+d.color+'" style="background-color: '+d.color+';"></div>'
					+'<div class="item xtkd">边线宽度<input type="text" value="'+d.weight+'" id="slxtkdxz">px</div>'
					+'<div class="item xttmd">边透明度<input type="text" value="'+d.opacity+'" id="slxttmdxz">0-1</div>'
				+'</div>'
	},
	polygonNumber:function(i,c,f){
		var d=stylespz.styles[i];
		if(d.dashArray=='1')
		{
			var sx="selected='selected'";
			var xx="";
		}
		else
		{
			var sx="";
			var xx="selected='selected'";
		}
		return '<div class="pzbox polygonstyle" style="position:initial">'
					+'<div class="headstyle" d-v="'+i+'">'+f+'<input type="text" class="fw w60" value="'+c+'"></div>'
					+'<div class="item xtxz">边线形状'
					+	'<select id="slxtxzxz" style="outline: none;">'
					+		'<option value="1" '+sx+'>实线</option>'
					+		'<option value="4" '+xx+'>虚线</option>'
					+	'</select>'
					+'</div>'
					+'<div class="item mzys">面状颜色<input type="text" readonly="readonly" v="mzs" class="newjscolor" value="'+d.fillColor+'" style="background-color: '+d.fillColor+';"></div>'
					+'<div class="item mztmd">面透明度<input type="text" value="'+d.fillOpacity+'" id="slmztmdxz">0-1</div>'
					+'<div class="item xtys">边线颜色<input type="text" readonly="readonly" v="xzs" class="newjscolor" value="'+d.color+'" style="background-color: '+d.color+';"></div>'
					+'<div class="item xtkd">边线宽度<input type="text" value="'+d.weight+'" id="slxtkdxz">px</div>'
					+'<div class="item xttmd">边透明度<input type="text" value="'+d.opacity+'" id="slxttmdxz">0-1</div>'
				+'</div>'
	},
	lineString:function(c){
		var d=stylespz.styles[c];
		if(d.dashArray=='1')
		{
			var sx="selected='selected'";
			var xx="";
		}
		else
		{
			var sx="";
			var xx="selected='selected'";
		}
		return '<div class="pzbox polygonstyle" style="position:initial">'
					+'<div class="headstyle" d-v="'+c+'">'+c+'</div>'
					+'<div class="item xtxz">线条形状'
					+	'<select id="slxtxzxz" style="outline: none;">'
					+		'<option value="1" '+sx+'>实线</option>'
					+		'<option value="4" '+xx+'>虚线</option>'
					+	'</select>'
					+'</div>'
					+'<div class="item xtys">线条颜色<input type="text" readonly="readonly" v="xzs" class="newjscolor" value="'+d.color+'" style="background-color: '+d.color+';"></div>'
					+'<div class="item xtkd">线条宽度<input type="text" value="'+d.weight+'" id="slxtkdxz">px</div>'
					+'<div class="item xttmd">线透明度<input type="text" value="'+d.opacity+'" id="slxttmdxz">0-1</div>'
				+'</div>'
	},
	lineNumber:function(i,c,f){
		var d=stylespz.styles[i];
		if(d.dashArray=='1')
		{
			var sx="selected='selected'";
			var xx="";
		}
		else
		{
			var sx="";
			var xx="selected='selected'";
		}
		return '<div class="pzbox polygonstyle" style="position:initial">'
					+'<div class="headstyle" d-v="'+i+'">'+f+'<input type="text" class="fw w60" value="'+c+'"></div>'
					+'<div class="item xtxz">边线形状'
					+	'<select id="slxtxzxz" style="outline: none;">'
					+		'<option value="1" '+sx+'>实线</option>'
					+		'<option value="4" '+xx+'>虚线</option>'
					+	'</select>'
					+'</div>'
					+'<div class="item xtys">线条颜色<input type="text" readonly="readonly" v="xzs" class="newjscolor" value="'+d.color+'" style="background-color: '+d.color+';"></div>'
					+'<div class="item xtkd">线条宽度<input type="text" value="'+d.weight+'" id="slxtkdxz">px</div>'
					+'<div class="item xttmd">线透明度<input type="text" value="'+d.opacity+'" id="slxttmdxz">0-1</div>'
				+'</div>'
	},
	pointString:function(c){
		var d=stylespz.styles[c];
		var w40="",w30="",w20="",w_bottom="",w_top="",w_left="",w_right="",w_center="";
		if(d.isize=="40"){
			w40="selected='selected'";
		}
		else if(d.isize=="30"){
			w30="selected='selected'";
		}
		else if(d.isize=="20"){
			w20="selected='selected'";
		}
		
		if(d.zx=="bottom"){
			w_bottom="selected='selected'";
		}
		else if(d.zx=="top"){
			w_top="selected='selected'";
		}
		else if(d.zx=="left"){
			w_left="selected='selected'";
		}
		else if(d.zx=="right"){
			w_right="selected='selected'";
		}
		else if(d.zx=="center"){
			w_center="selected='selected'";
		}
		return '<div class="pzbox polygonstyle" style="position:initial; height:120px;">'
					+'<div class="headstyle" d-v="'+c+'">'+c+'</div>'
					+'<div class="item xtxz tbxz">更换图标<img src="'+d.iconUrl+'" width="20" class="xztb"></div>'
					+'<div class="item xtys">图标大小<select id="sltbdxxz"><option value="40" '+w40+'>40*40</option><option value="30" '+w30+'>30*30</option><option value="20" '+w20+'>20*20</option></select></div>'
					+'<div class="item xtkd">图标位置<select id="sltbwz"><option value="bottom" '+w_bottom+'>下为点</option><option value="top" '+w_top+'>上为点</option><option value="left" '+w_left+'>左为点</option><option value="right" '+w_right+'>右为点</option><option value="center" '+w_center+'>居中</option></select></div>'
				+'</div>'
	},
	pointNumber:function(i,c,f){
		var d=stylespz.styles[i];
		var w40="",w30="",w20="",w_bottom="",w_top="",w_left="",w_right="",w_center="";
		if(d.isize=="40"){
			w40="selected='selected'";
		}
		else if(d.isize=="30"){
			w30="selected='selected'";
		}
		else if(d.isize=="20"){
			w20="selected='selected'";
		}
		
		if(d.zx=="bottom"){
			w_bottom="selected='selected'";
		}
		else if(d.zx=="top"){
			w_top="selected='selected'";
		}
		else if(d.zx=="left"){
			w_left="selected='selected'";
		}
		else if(d.zx=="right"){
			w_right="selected='selected'";
		}
		else if(d.zx=="center"){
			w_center="selected='selected'";
		}
		return '<div class="pzbox polygonstyle" style="position:initial; height:120px;">'
					+'<div class="headstyle" d-v="'+i+'">'+f+'<input type="text" class="fw w60" value="'+c+'"></div>'
					+'<div class="item xtxz tbxz">更换图标<img src="'+d.iconUrl+'" width="20" class="xztb"></div>'
					+'<div class="item xtys">图标大小<select id="sltbdxxz"><option value="40" '+w40+'>40*40</option><option value="30" '+w30+'>30*30</option><option value="20" '+w20+'>20*20</option></select></div>'
					+'<div class="item xtkd">图标位置<select id="sltbwz"><option value="bottom" '+w_bottom+'>下为点</option><option value="top" '+w_top+'>上为点</option><option value="left" '+w_left+'>左为点</option><option value="right" '+w_right+'>右为点</option><option value="center" '+w_center+'>居中</option></select></div>'
				+'</div>'
	},
}
