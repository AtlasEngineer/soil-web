//云标注
var bz={};
var stylespz={};
var zdzname=[];
var shpLayer="";
var curqj=[];
var popup="";
var fieldlist=[];
//var curStyles="";
//高亮layer
var tempLayer="";
$(function(){
	//初始化十色谱
//	$(".xiala .m_body").html("");
//	$.each(ssp,function(n,c){
//		var item="";
//		$.each(c,function(n1,c1){
//			item+='<span style="background-color:'+c1+'"></span>';
//		})
//		$(".xiala .m_body").append('<div class="m_item" value="'+n+'">'+item+'</div>');
//	})
	
	$(".xiala .m_head").click(function(e){
		e.stopPropagation();
		$(this).next(".m_body").show();
	})
	$(".xiala").on("click",".m_body .m_item",function(){
		var id=$(this).attr("value");
		$(".xiala .m_head").html($(this).html());
		var shptype=$("#curshptype").val();
		if(shptype=="polygon")
		{
			var i=0;
			$.each(stylespz.styles,function(n,c){
				if(!ssp[id][i])
				{
					i=0;
				}
				c.color="#555555";
				c.fillColor=ssp[id][i];
				i++;
			})
			saveStyles();
			layerSetStyle();
		}
	})
	$(document).click(function(){
		setTimeout(function(){
			$(".m_body").hide();
		},50);
	})
	
	$("#toumingdu").mouseover(function(e){
		e.stopPropagation();
		Lg.map.dragging.disable();
	})
	$("#toumingdu").mouseout(function(e){
		e.stopPropagation();
		Lg.map.dragging.enable();
	})
	$("#toumingdu").bind('input porpertychange',function(){
		$(this).next("#tmdzhi").text($(this).val());
		var tmd=$(this).val();
		if(shpLayer)
		{
			if(shpLayer._layers)
			{
				console.log(stylespz.styles)
				var stype=$("#curshptype").val();
				if(stype=="polygon" || stype=="line")
				{
					$.each(stylespz.styles,function(n,c){
						if(stype=="polygon")
						{
							stylespz.styles[n].opacity=tmd;
							stylespz.styles[n].fillOpacity=tmd;
						}
						else
						{
							stylespz.styles[n].opacity=tmd;
						}
					})
					if(stype=="polygon")
					{
						$("#fieldpzbox #slmztmdxz").val(tmd);
						$("#fieldpzbox #slxttmdxz").val(tmd);
					}
					else
					{
						$("#fieldpzbox #slxttmdxz").val(tmd);
					}
					
				}
				saveStyles();
				layerSetStyle();
			}
			else
			{
				shpLayer.setOpacity(tmd);
			}
		}
	//逻辑部分
	});
	
	$(".serselect").click(function(e){
		e.stopPropagation();
	})
	$(".searinput").click(function(e){
		e.stopPropagation();
	})
	//单数据搜索
	$(".tsearch").click(function(e){
		e.stopPropagation();
		var tagid=$("#curid").val();
		if(tagid)
		{
			$.get("/search/page?fldtbid="+tagid,function(data){
				$(".serselect").html("");
				$.each(data.page,function(n,c){
					$(".serselect").append("<option value='"+c.fldname+"'>"+c.fldcnn+"</option>")
				})
				$(".mapsearchbox").show();
			})
		}
		else
		{
			alert("请选择数据");
		}
	})
	//单样式修改
	$(".tstyles").click(function(e){
		e.stopPropagation();
		var tagid=$("#curid").val();
		var layers=$("#curlayer").val();
		if(!tagid)
		{
			alert("未选择数据或者此数据无法进行配色！");
		}
		else if(!layers)
		{
			alert("此数据无法配置颜色");
		}
		else
		{
			var name=$(this).val();
			var tagid=$("#curid").val();
			var curshptype=$("#curshptype").val();
			if(stylespz.field)
			{
				$.post("/search/searchzd",{fldtbid:tagid,fldname:stylespz.field,num:5},function(data,s){
					console.log(data);
					//配置配色字段
					$("#fieldpzbox").html("");
					$("#fieldname").val(stylespz.field);
					$(".tmdlt").show();
					if(data.type=="string" && curshptype=="polygon")
					{
						$.each(data.list,function(n,c){
							$("#fieldpzbox").append(tmp.polygonString(c));
						})
						setColpick();
					}
					else if(data.type=="number" && curshptype=="polygon"){
						var i=0;
						$.each(data.qj,function(n,c){
							var x=c.split(",");
							if(i==data.qj.length-1)
							{
								var f="大于";
								var z=x[0];
							}
							else if(i==0)
							{
								var f="小于";
								var z=x[1];
							}
							else
							{
								var f="至";
								var z=x[1];
							}
							$("#fieldpzbox").append(tmp.polygonNumber(i,z,f));
							i++
						})
						setColpick();
					}
					else if(data.type=="string" && curshptype=="line")
					{
						$.each(data.list,function(n,c){
							$("#fieldpzbox").append(tmp.lineString(c));
						})
						setColpick();
					}
					else if(data.type=="number" && curshptype=="line")
					{
						var i=0;
						$.each(data.qj,function(n,c){
							var x=c.split(",");
							if(i==data.qj.length-1)
							{
								var f="大于";
								var z=x[0];
							}
							else if(i==0)
							{
								var f="小于";
								var z=x[1];
							}
							else
							{
								var f="至";
								var z=x[1];
							}
							$("#fieldpzbox").append(tmp.lineNumber(i,z,f));
							i++
						})
						setColpick();
					}
					else if(data.type=="string" && curshptype=="point")
					{
						$(".tmdlt").hide();
						$.each(data.list,function(n,c){
							$("#fieldpzbox").append(tmp.pointString(c));
						})
						setColpick();
					}
					else if(data.type=="number" && curshptype=="point")
					{
						$(".tmdlt").hide();
						var i=0;
						$.each(data.qj,function(n,c){
							var x=c.split(",");
							if(i==data.qj.length-1)
							{
								var f="大于";
								var z=x[0];
							}
							else if(i==0)
							{
								var f="小于";
								var z=x[1];
							}
							else
							{
								var f="至";
								var z=x[1];
							}
							$("#fieldpzbox").append(tmp.pointNumber(i,z,f));
							i++
						})
						setColpick();
					}
					$(".pzstylebox").show();
				})
			}
			else
			{
				$(".pzstylebox").show();
			}
			
		}
	})
	
	//*******************/矢量配色
	$(".pzstylebox").on("change","#slxtxzxz",function(){
		var z=$(this).val();
		var obj=$(this).parents(".pzbox").children(".headstyle").attr("d-v");
		stylespz.styles[obj].dashArray=z;
		saveStyles();
		layerSetStyle();
	})
	
	$(".pzstylebox").on("blur","#slmztmdxz",function(){
		var z=$(this).val();
		var obj=$(this).parents(".pzbox").children(".headstyle").attr("d-v");
		stylespz.styles[obj].fillOpacity=z;
		saveStyles();
		layerSetStyle();
	})
	//更改线透明度
	$(".pzstylebox").on("blur","#slxttmdxz",function(){
		var z=$(this).val();
		var obj=$(this).parents(".pzbox").children(".headstyle").attr("d-v");
		stylespz.styles[obj].opacity=z;
		saveStyles();
		layerSetStyle();
	})
	//更改线宽
	$(".pzstylebox").on("blur","#slxtkdxz",function(){
		var z=$(this).val();
		var obj=$(this).parents(".pzbox").children(".headstyle").attr("d-v");
		stylespz.styles[obj].weight=z;
		saveStyles();
		layerSetStyle();
	})
	//更改数字的大小
	$(".pzstylebox").on("blur",".fw",function(){
		var z=$(this).val();
		var obj=$(this).parents(".headstyle").attr("d-v");
		stylespz.styles[obj].where=z;
		saveStyles();
		layerSetStyle();
	})
	
	//更改点大小
	$(".pzstylebox").on("change","#sltbdxxz",function(){
		var z=$(this).val();
		var obj=$(this).parents(".pzbox").children(".headstyle").attr("d-v");
		var dwz=$(this).parents(".pzbox").find("#sltbwz").val();
		stylespz.styles[obj].iconSize = [z,z];
		stylespz.styles[obj].isize=z;
		if(dwz=="bottom")
		{
			stylespz.styles[obj].iconAnchor=[z/2,z];
			stylespz.styles[obj].popupAnchor= [0, -(z/2)];
		}
		else if(dwz=="top")
		{
			stylespz.styles[obj].iconAnchor=[z/2,0];
			stylespz.styles[obj].popupAnchor= [0, z/2];
		}
		else if(dwz=="left")
		{
			stylespz.styles[obj].iconAnchor=[0,z/2];
			stylespz.styles[obj].popupAnchor= [z/2, 0];
		}
		else if(dwz=="right")
		{
			stylespz.styles[obj].iconAnchor=[z,z/2];
			stylespz.styles[obj].popupAnchor= [-(z/2), 0];
		}
		else if(dwz=="center")
		{
			stylespz.styles[obj].iconAnchor=[z/2,z/2];
			stylespz.styles[obj].popupAnchor= [0, 0];
		}
		
		
		saveStyles();
		layerSetStyle();
	})
	$(".pzstylebox").on("change","#sltbwz",function(){
		var dwz=$(this).val();
		var obj=$(this).parents(".pzbox").children(".headstyle").attr("d-v");
		var z=$(this).parents(".pzbox").find("#sltbdxxz").val();
		
		if(dwz=="bottom")
		{
			stylespz.styles[obj].iconAnchor=[z/2,z];
			stylespz.styles[obj].popupAnchor= [0, -(z/2)];
		}
		else if(dwz=="top")
		{
			stylespz.styles[obj].iconAnchor=[z/2,0];
			stylespz.styles[obj].popupAnchor= [0, z/2];
		}
		else if(dwz=="left")
		{
			stylespz.styles[obj].iconAnchor=[0,z/2];
			stylespz.styles[obj].popupAnchor= [z/2, 0];
		}
		else if(dwz=="right")
		{
			stylespz.styles[obj].iconAnchor=[z,z/2];
			stylespz.styles[obj].popupAnchor= [-(z/2), 0];
		}
		else if(dwz=="center")
		{
			stylespz.styles[obj].iconAnchor=[z/2,z/2];
			stylespz.styles[obj].popupAnchor= [0, 0];
		}
		stylespz.styles[obj].zx=dwz;
		saveStyles();
		layerSetStyle();
	})
	/******************/
	
	
	//搜索
	$(".searinput").keyup(function(){
		removeTempLayer();
		var serselect=$(".serselect").val();
		var p=$("#shpsearch").serializeArray();
			p.push({name:"fldtbid",value:$("#curid").val()})
		$.post("/search/searchlist",p,function(data,s){
			console.log(data)
			$("#serachlist").html("");
			$.each(data.list,function(n,c){
				$("#serachlist").append("<p>"+c[serselect]+"</p>");
			})
		})
	})
	$("#shpsearchbtn").click(function(e){
		e.stopPropagation();
		var p=$("#shpsearch").serializeArray();
			p.push({name:"fldtbid",value:$("#curid").val()})
		$.post("/search/searchlist",p,function(data,s){
			console.log(data)
			$.each(data.list,function(n,c){
				
			})
		})
	})
	$("#serachlist").on("click","p",function(e){
		e.stopPropagation();
		removeTempLayer();
		var nr=$(this).text();
		var url=$("#cururl").val();
		var layers=$("#curlayer").val();
		var fieldname=$(".serselect").val();
		$(".searinput").val(nr);
		$("#serachlist").html("");
		tempLayer=L.esri.featureLayer({
		    url: url+"/"+layers,
		    simplifyFactor: 0.5,
		    precision: 5,
		}).addTo(Lg.map)
		tempLayer.options.style=function (feature) {
	    	console.log(feature.properties[fieldname])
	      	if(feature.properties[fieldname] === nr){
	      		
	        	return {color:"#ffba00",weight:2, fillColor:"#ffba00", fillOpacity:0.7,dashArray:1};
	      	}
	      	else
	      	{
	      		return {color:"#ffba00",weight:0, fillColor:"#ffba00", fillOpacity:0,dashArray:1};
	      	}
	    }
	})
	
	$(".pzstylebox").click(function(e){
		e.stopPropagation();
		setTimeout(function(){
			$(".m_body").hide();
		},100);
	})
	
	$("#fieldname").change(function(){
		var name=$(this).val();
		var tagid=$("#curid").val();
		var shptype=$("#curshptype").val();
		Lg.map.off("click",searchfeatureFn);
		$.post("/search/searchzd",{fldtbid:tagid,fldname:name,num:5},function(data,s){
			console.log(data);
			//配置配色字段
			stylespz.field=name;
			stylespz.fieldType=data.type;
			stylespz.styles={};
			$("#fieldpzbox").html("");
			if(data.type=="string" && shptype=="polygon")
			{
				//zdzname=[];
				$.each(data.list,function(n,c){
					stylespz.styles[c]={where:c,color:"#555555",opacity:1,weight:1,dashArray:1,fillColor:"#bdfff3",fillOpacity:1};
					$("#fieldpzbox").append(tmp.polygonString(c));
				})
				setColpick();
				saveStyles();
				layerSetStyle();
			}
			else if(data.type=="number" && shptype=="polygon")
			{
				var i=0;
				$.each(data.qj,function(n,c){
					var x=c.split(",");
					if(i==data.qj.length-1)
					{
						var f="大于";
						var z=x[0];
					}
					else if(i==0)
					{
						var f="小于";
						var z=x[1];
					}
					else
					{
						var f="至";
						var z=x[1];
					}
					stylespz.styles[i]={where:z,color:"#555555",opacity:1,weight:1,dashArray:1,fillColor:"#bdfff3",fillOpacity:1};
					$("#fieldpzbox").append(tmp.polygonNumber(i,z,f));
					i++
				})
				setColpick();
				saveStyles();
				layerSetStyle();
			}
			else if(data.type=="string" && shptype=="line")
			{
				//zdzname=[];
				$.each(data.list,function(n,c){
					stylespz.styles[c]={where:c,color:"#555555",opacity:1,weight:1,dashArray:1};
					$("#fieldpzbox").append(tmp.lineString(c));
				})
				setColpick();
				saveStyles();
				layerSetStyle();
			}
			else if(data.type=="number" && shptype=="line")
			{
				var i=0;
				$.each(data.qj,function(n,c){
					var x=c.split(",");
					if(i==data.qj.length-1)
					{
						var f="大于";
						var z=x[0];
					}
					else if(i==0)
					{
						var f="小于";
						var z=x[1];
					}
					else
					{
						var f="至";
						var z=x[1];
					}
					stylespz.styles[i]={where:z,color:"#555555",opacity:1,weight:1,dashArray:1};
					$("#fieldpzbox").append(tmp.lineNumber(i,z,f));
					i++
				})
				setColpick();
				saveStyles();
				layerSetStyle();
			}
			else if(data.type=="string" && shptype=="point")
			{
				$.each(data.list,function(n,c){
					stylespz.styles[c]={where:c,iconUrl:"/src/pointicon/icon0.png",iconSize:[20,20],iconAnchor:[10,20],popupAnchor:[0,-10],zx:"bottom",isize:20};
					$("#fieldpzbox").append(tmp.pointString(c));
				})
				setColpick();
				saveStyles();
				layerSetStyle();
			}
			else if(data.type=="number" && shptype=="point")
			{
				var i=0;
				$.each(data.qj,function(n,c){
					var x=c.split(",");
					if(i==data.qj.length-1)
					{
						var f="大于";
						var z=x[0];
					}
					else if(i==0)
					{
						var f="小于";
						var z=x[1];
					}
					else
					{
						var f="至";
						var z=x[1];
					}
					stylespz.styles[i]={where:z,iconUrl:"/src/pointicon/icon0.png",iconSize:[20,20],iconAnchor:[10,20],popupAnchor:[0,-11],zx:"bottom",isize:20};
					$("#fieldpzbox").append(tmp.pointNumber(i,z,f));
					i++
				})
				setColpick();
				saveStyles();
				layerSetStyle();
			}
		})
	})
	
	$(".l_title").click(function(){
		if($(this).hasClass("hov"))
		{
			$(this).removeClass("hov");
		}
		else
		{
			$(this).addClass("hov");
		}
		
		$(this).next("ul").toggle();
	})
	
	$(".gbysxg").click(function(){
		$(this).parents(".pzstylebox").hide();
	})
	
	//tab切换
	$(".tabtop .tabbtn").click(function(){
		$(this).siblings(".hov").removeClass("hov");
		$(this).addClass("hov");
		var n=$(this).index();
		$(this).parents(".tabtop").next(".tabbox").children(".tabitem").hide();
		$(this).parents(".tabtop").next(".tabbox").children(".tabitem").eq(n).show();
	})
	
	//退出对比模式
	$(".closedb").click(function(){
		$(".dbmap").remove();
		$(".duibiarea").hide();
		$(".duibiarea #compare").remove();
	})
	
	//进入卷帘对比模式
	$("#quedingjl").click(function(){
		var layerbottom=$("#layerbottom").val();
		var layerbottomlayers=$("#layerbottom").find("option:selected").attr("layers");
		var layertop=$("#layertop").val();
		var layertoplayers=$("#layertop").find("option:selected").attr("layers");
		$(".dbmap").remove();
		if(layerbottom && layertop)
		{
			$(".duibiarea").show();
			$(".duibiarea #compare").remove();
			compare.layertop=layertop;
			compare.layertoplayers=layertoplayers;
			compare.layerbottom=layerbottom;
			compare.layerbottomlayers=layerbottomlayers;
			compare.init(".duibiarea");
		}
		else
		{
			alert("请选择数据");
		}
	})
	$(".qxbtn").click(function(){
		$(this).parents(".tabitem").hide();
		$(this).parents(".tabbox").prev(".tabtop").children(".hov").removeClass("hov");
		$(".duibiarea #compare").remove();
	})
	$("#quxiaojl,#quxiaofp").click(function(){
		$(".duibiarea").hide();
	})
	
	/******导航*****/
	$(".daohangbtn").unbind("click");
	$(".daohangbtn").on("click",function(e){
		e.stopPropagation();
		if($(this).hasClass("hov"))
		{
			$(this).removeClass("hov");
			$(this).parents(".caozuo").css("width",42);
			$(this).parents(".tabtop").next(".tabbox").children(".tabitem").hide();
		}
		else
		{
			$(this).addClass("hov");
			$(this).parents(".caozuo").css("width",304);
			$(this).parents(".tabtop").next(".tabbox").children(".tabitem").eq($(this).index()).show();
		}
	})
	/***标注***/
	
	//var bz={};
	
	//点标注
	$(".dbiaozhu").unbind("click");
	$(".dbiaozhu").on("click",function(e){
		e.stopPropagation();
		var mlid=$("#curmlid").val();
		if(!mlid)
		{
			alert("请打开要标注的图层");
			return;
		}
		if(shpLayer._layers)
		{
			shpLayer.unbindPopup();
		}
		else
		{
			Lg.map.off("click",searchfeatureFn);
		}
		
		var me=$(this);
		Plotting.disabled();
		$(this).siblings(".hov").removeClass("hov");
		$(this).addClass("hov");
		Plotting.map=Lg.map;
		Plotting.point(function(e){
			console.log(e)
			bz["l"+e._leaflet_id]=e.bindPopup("<div class='toubu'></div><div class='sqadd'><p><input type='text' id='sqbiaoti' placeholder='标题'></p><p><textarea id='sqbz' placeholder='备注'></textarea></p><p><input type='checkbox' id='gz'><label for='gz'>公开</label></p><p class='rightbtnbox'><input type='button' did='l"+e._leaflet_id+"' dtype='point' class='baocunbz btn bluebtn' value='保存'><input type='button' did='l"+e._leaflet_id+"' dtype='point' class='baocunys btn bluebtn' value='样式'><input type='button' value='删除' did='l"+e._leaflet_id+"' class='shanchubz btn graybtn'></p></div>");
			setTimeout(function(){
				bz["l"+e._leaflet_id].openPopup();
			},10)
			me.removeClass("hov");
			if(shpLayer._layers)
			{
				shpLayerAddPopup();
			}
			else
			{
				Lg.map.on("click",searchfeatureFn);
			}
		});
	})
	//线标注
	$(".xbiaozhu").unbind("click");
	$(".xbiaozhu").on("click",function(e){
		e.stopPropagation();
		var mlid=$("#curmlid").val();
		if(!mlid)
		{
			alert("请打开要标注的图层");
			return;
		}
		if(shpLayer._layers)
		{
			shpLayer.unbindPopup();
		}
		else
		{
			Lg.map.off("click",searchfeatureFn);
		}
		var me=$(this);
		Plotting.disabled();
		$(this).siblings(".hov").removeClass("hov");
		$(this).addClass("hov");
		Plotting.map=Lg.map;
		Plotting.line(function(e){
			console.log(e)
			bz["l"+e._leaflet_id]=e.bindPopup("<div class='toubu'></div><div class='sqadd'><p><input type='text' id='sqbiaoti' placeholder='标题'></p><p><textarea id='sqbz' placeholder='备注'></textarea></p><p><input type='checkbox' id='gz'><label for='gz'>公开</label></p><p class='rightbtnbox'><input type='button' did='l"+e._leaflet_id+"' class='baocunbz btn bluebtn' dtype='line' value='保存'><input type='button' did='l"+e._leaflet_id+"' dtype='line' class='baocunys btn bluebtn' value='样式'><input type='button' value='删除' did='l"+e._leaflet_id+"' class='shanchubz btn graybtn'></p></div>");
			setTimeout(function(){
				bz["l"+e._leaflet_id].openPopup();
			},10)
			me.removeClass("hov");
			if(shpLayer._layers)
			{
				shpLayerAddPopup();
			}
			else
			{
				Lg.map.on("click",searchfeatureFn);
			}
		});
	})
	//面标注
	$(".mbiaozhu").unbind("click");
	$(".mbiaozhu").on("click",function(e){
		e.stopPropagation();
		var mlid=$("#curmlid").val();
		if(!mlid)
		{
			alert("请打开要标注的图层");
			return;
		}
		if(shpLayer._layers)
		{
			shpLayer.unbindPopup();
		}
		else
		{
			Lg.map.off("click",searchfeatureFn);
		}
		var me=$(this);
		Plotting.disabled();
		$(this).siblings(".hov").removeClass("hov");
		$(this).addClass("hov");
		Plotting.map=Lg.map;
		Plotting.polygon(function(e){
			console.log(e)
			bz["l"+e._leaflet_id]=e.bindPopup("<div class='toubu'></div><div class='sqadd'><p><input type='text' id='sqbiaoti' placeholder='标题'></p><p><textarea id='sqbz' placeholder='备注'></textarea></p><p><input type='checkbox' id='gz'><label for='gz'>公开</label></p><p class='rightbtnbox'><input type='button' did='l"+e._leaflet_id+"' class='baocunbz btn bluebtn' dtype='polygon' value='保存'><input type='button' did='l"+e._leaflet_id+"' dtype='polygon' class='baocunys btn bluebtn' value='样式'><input type='button' value='删除' did='l"+e._leaflet_id+"' class='shanchubz btn graybtn'></p></div>");
			setTimeout(function(){
				bz["l"+e._leaflet_id].openPopup();
			},10)
			me.removeClass("hov");
			if(shpLayer._layers)
			{
				shpLayerAddPopup();
			}
			else
			{
				Lg.map.on("click",searchfeatureFn);
			}
		});
	})
	
	var stylehtml={
			point:'<div class="pzbox pointstyle">'
						+'<div class="headstyle">样式编辑<span class="icon-cancel gbys" title="关闭"></span></div>'
						+'<div class="item tbxz">图标形状<img src="" width="20" class="xztb"></div>'
//						+'<div class="item">图标颜色<input type="text" id=""></div>'
						+'<div class="item tbdx">图标大小<select id="tbdxxz"><option value="40">40*40</option><option value="30">30*30</option><option value="20">20*20</option></select></div>'
				   +'</div>',
			line:'<div class="pzbox linestyle">'
						+'<div class="headstyle">样式编辑<span class="icon-cancel gbys" title="关闭"></span></div>'
						+'<div class="item xtxz">线条形状<select id="xtxzxz"><option value="1">实线</option><option value="4">虚线</option></select></div>'
						+'<div class="item xtys">线条颜色<input type="text" readonly="readonly" class="newjscolor" id="xtysxz"></div>'
						+'<div class="item xtkd">线条宽度<input type="text" id="xtkdxz">px</div>'
						+'<div class="item xttmd">线透明度<input type="text" id="xttmdxz">0-1</div>'
				   +'</div>',
			polygon:'<div class="pzbox polygonstyle">'
						+'<div class="headstyle">样式编辑<span class="icon-cancel gbys" title="关闭"></span></div>'
						+'<div class="item xtxz">边线形状<select id="xtxzxz"><option value="1">实线</option><option value="4">虚线</option></select></div>'
						+'<div class="item mzys">面状颜色<input type="text" readonly="readonly" class="newjscolor" id="mzysxz"></div>'
						+'<div class="item mztmd">面透明度<input type="text" id="mztmdxz">0-1</div>'
						+'<div class="item xtys">边线颜色<input type="text" readonly="readonly" class="newjscolor" id="xtysxz"></div>'
						+'<div class="item xtkd">边线宽度<input type="text" id="xtkdxz">px</div>'
						+'<div class="item xttmd">边透明度<input type="text" id="xttmdxz">0-1</div>'
				   +'</div>',
		}
	var bzid="";
	//修改标注样式
	$("#map").on("click",".baocunys",function(){
		var type=$(this).attr("dtype");
		bzid=$(this).attr("did");
		if(type=="point")
		{
			var url=bz[bzid]._icon.src;
			var size=bz[bzid]._icon.height;
			$(this).parents(".sqadd").append(stylehtml[type]);
			$(".tbxz img").attr("src",url);
			$(".tbdx select").val(size);
		}
		else if(type=="line")
		{
			$(this).parents(".sqadd").append(stylehtml[type]);
			
			console.log(bz[bzid]._path.attributes)
			var color=bz[bzid]._path.attributes.stroke.value;
			var xwidth=bz[bzid]._path.attributes["stroke-width"].value;
			var xtmd=bz[bzid]._path.attributes["stroke-opacity"].value;
			var xtxz=bz[bzid]._path.attributes["stroke-dasharray"] ? bz[bzid]._path.attributes["stroke-dasharray"].value : 0;
			$("#xtxzxz").val(xtxz);
			$('#xtysxz').val(color).css('background-color', color);
			$('#xtkdxz').val(xwidth);
			$("#xttmdxz").val(xtmd);
			$('#xtysxz').colpick({
				colorScheme:'dark',
				layout:'rgbhex',
				color:color,
				onSubmit:function(hsb,hex,rgb,el) {
					$(el).css('background-color', '#'+hex);
					$(el).val("#"+hex);
					bz[bzid]._path.attributes.stroke.value="#"+hex;
					$(el).colpickHide();
				}
			})
		}
		else if(type=="polygon")
		{
			$(this).parents(".sqadd").append(stylehtml[type]);
			
			var color=bz[bzid]._path.attributes.stroke.value;
			var xwidth=bz[bzid]._path.attributes["stroke-width"].value;
			var xtmd=bz[bzid]._path.attributes["stroke-opacity"].value;
			var xtxz=bz[bzid]._path.attributes["stroke-dasharray"] ? bz[bzid]._path.attributes["stroke-dasharray"].value : 0;
			
			var fillColor=bz[bzid]._path.attributes["fill"].value;
			var fill_opacity=bz[bzid]._path.attributes["fill-opacity"].value;
			
			
			$("#xtxzxz").val(xtxz);
			$('#xtysxz').val(color).css('background-color', color);
			$('#mzysxz').val(fillColor).css('background-color', fillColor);
			$('#xtkdxz').val(xwidth);
			$("#xttmdxz").val(xtmd);
			$("#mztmdxz").val(fill_opacity);
			$('#xtysxz').colpick({
				colorScheme:'dark',
				layout:'rgbhex',
				color:color,
				onSubmit:function(hsb,hex,rgb,el) {
					$(el).css('background-color', '#'+hex);
					$(el).val("#"+hex);
					bz[bzid]._path.attributes.stroke.value="#"+hex;
					$(el).colpickHide();
				}
			})
			$('#mzysxz').colpick({
				colorScheme:'dark',
				layout:'rgbhex',
				color:fillColor,
				onSubmit:function(hsb,hex,rgb,el) {
					$(el).css('background-color', '#'+hex);
					$(el).val("#"+hex);
					bz[bzid]._path.attributes.fill.value="#"+hex;
					$(el).colpickHide();
				}
			})
		}
	})
	//修改线宽度
	$("#map").on("blur","#xtkdxz",function(){
		bz[bzid]._path.attributes["stroke-width"].value=$(this).val();
	})
	//修改线透明度
	$("#map").on("blur","#xttmdxz",function(){
		if($(this).val()<0)
		{
			$(this).val(0);
		}
		else if($(this).val()>1)
		{
			$(this).val(1);
		}
		bz[bzid]._path.attributes["stroke-opacity"].value=$(this).val();
	})
	//修改面透明度
	$("#map").on("blur","#mztmdxz",function(){
		if($(this).val()<0)
		{
			$(this).val(0);
		}
		else if($(this).val()>1)
		{
			$(this).val(1);
		}
		bz[bzid]._path.attributes["fill-opacity"].value=$(this).val();
	})
	//修改线形
	$("#map").on("change","#xtxzxz",function(){
		var v=$(this).val();
		bz[bzid]._path.attributes["stroke-dasharray"].value=v;
	})
	
	//点击图标进行修改
	$("#map,.pzstylebox").on("click",".xztb",function(){
		$("#tbmb").remove();
		var html="<a class='zjtb'><img src='/src/pointicon/addicon.png' width='20' height='20'></a>";
		$.each(pointlist,function(n,c){
			html+="<a><img src='"+c+"' width='20' height='20'></a>"
		})
		$(this).parents(".tbxz").append("<div id='tbmb'>"+html+"</div>")
	})
	
	//新增图标
	$("#map,.pzstylebox").on("click",".zjtb img",function(e){
		e.stopPropagation();
		$("#wjxzip").click();
	})
	$("#wjxzip").change(function(){
		$("#ycscwj").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/upload/topictrue',// 需要提交的 url
            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
                console.log(data)
               if(data['error'])
				{
					for(x in data)
					{
						if(x.indexOf("msg")!=-1)
						{
							alert(data[x]);
						}
					}
				}
				else
				{
					$("#tbmb").append("<a><img src='"+data.fileUrl+"' width='20' height='20'></a>")
					refreshicon();
					$("#wjxzip").val("");
                }
            }
        })
	});
	$("#map").on("click","#tbmb a",function(){
		if($(this).hasClass("zjtb"))
		{
			return;
		}
		var url=$(this).children("img").attr("src");
		$(".xztb").attr("src",url);
		bz[bzid]._icon.src=url;
		$("#tbmb").remove();
	})
	$(".pzstylebox").on("click","#tbmb a",function(){
		if($(this).hasClass("zjtb"))
		{
			return;
		}
		var url=$(this).children("img").attr("src");
		$(this).parents(".tbxz").children(".xztb").attr("src",url);
		var obj=$(this).parents(".pzbox").children(".headstyle").attr("d-v");
		stylespz.styles[obj].iconUrl=url;
		$("#tbmb").remove();
		saveStyles();
		layerSetStyle();
	})
	
	$("#map").on("change","#tbdxxz",function(){
		var size=$(this).val();
		var url=bz[bzid]._icon.src;
		bz[bzid].setIcon(markerIcon.iconConfig(url,size));
	})
	
	$("#map").on("click",".gbys",function(){
		$(this).parents(".pzbox").remove();
	})
	
	//删除标注
	$("#map").on("click",".shanchubz",function(){
		var id=$(this).attr("did");
		bz[id].remove();
		delete bz[id];
	})
	//删除云标注
	$("#map").on("click",".shanchuybz",function(){
		var id=$(this).attr("did");
		$.post("/biaozhu/delete",{id:id},function(data){
			if(data.message=="success")
			{
				bz[id].remove();
				delete bz[id];
			}
		})
	})
	
	//点击修改云标注按钮
	$("#map").on("click",".editbz",function(){
		var id=$(this).attr("did");
		var type=$(this).attr("dtype");
		var title=$("#sqbt").text();
		var content= $("#sqbz").text();
		var status=$(this).attr("status");
		alert(status)
			if(status=="true")
			{
				var xz="checked='checked'";
			}
			else
			{
				var xz="";
			}
		    content = content ? content : "";
		bz[id]._popup.setContent("<div class='toubu'></div><div class='sqadd'><p><input type='text' id='sqbiaoti' placeholder='标题' value='"+title+"'></p><p><textarea id='sqbz' placeholder='备注'>"+content+"</textarea></p><p><input type='checkbox' "+xz+" id='gz'><label for='gz'>公开</label></p><p class='rightbtnbox'><input type='button' did='"+id+"' dtype='"+type+"' class='xiugaiybz btn bluebtn' value='保存'><input type='button' did='"+id+"' dtype='"+type+"' class='baocunys btn bluebtn' value='样式'><input type='button' value='删除' did='"+id+"' class='shanchuybz btn graybtn'></p></div>")
	})
	
	//确认修改云标注
	$("#map").on("click",".xiugaiybz",function(){
		var id=$(this).attr("did");
		var type=$(this).attr("dtype");
		var title=$("#sqbiaoti").val();
		var content= $("#sqbz").val();
		    content = content ? content : "";
		var gk=$("#gz").prop("checked");
		if(type=="point")
	    {
	    	var styles={url:bz[id]._icon.src,size:bz[id]._icon.height}
	    	
	    }
		else if(type=="line")
		{
			var color=bz[id]._path.attributes.stroke.value;
			var xwidth=bz[id]._path.attributes["stroke-width"].value;
			var xtmd=bz[id]._path.attributes["stroke-opacity"].value;
			var xtxz=bz[id]._path.attributes["stroke-dasharray"] ? bz[id]._path.attributes["stroke-dasharray"].value : 1;
			var styles={stroke:color,weight:xwidth,opacity:xtmd,dasharray:xtxz}
		}
		else if(type=="polygon"){
			var color=bz[id]._path.attributes.stroke.value;
			var xwidth=bz[id]._path.attributes["stroke-width"].value;
			var xtmd=bz[id]._path.attributes["stroke-opacity"].value;
			var xtxz=bz[id]._path.attributes["stroke-dasharray"] ? bz[id]._path.attributes["stroke-dasharray"].value : 0;
			
			var fillColor=bz[id]._path.attributes["fill"].value;
			var fill_opacity=bz[id]._path.attributes["fill-opacity"].value;
			var styles={stroke:color,weight:xwidth,opacity:xtmd,dasharray:xtxz,fillColor:fillColor,fill_opacity:fill_opacity}
		}
		$.post("/biaozhu/update",{id:id,title:title,bz:content,styles:JSON.stringify(styles),gk:gk},function(data){
			console.log(data)
			if(data.message=="success")
			{
				bz[id]._popup.setContent("<div class='toubu'><span class='icon-pencil editbz' status='"+gk+"' did='"+id+"' title='修改'></span><span class='icon-cancel shanchuybz' did='"+id+"' title='移除'></span></div><div class='sqadd'><p id='sqbt'>"+title+"</p><p id='sqbz'>"+content+"</p></div>");
			}
		})
	})
	
	//添加标注
	$("#map").on("click",".baocunbz",function(){
		var id=$(this).attr("did");
		var type=$(this).attr("dtype");
		var title=$("#sqbiaoti").val();
		var content= $("#sqbz").val();
		    content = content ? content : "";
	    var gk=$("#gz").prop("checked");
		    console.log(bz[bzid])
		    console.log(bz[id]._latlngs)
		    if(type=="point")
		    {
		    	var latlng=[bz[id]._latlng.lat,bz[id]._latlng.lng];
		    	var styles={url:bz[id]._icon.src,size:bz[id]._icon.height}
		    	
		    }
			else if(type=="line")
			{
				console.log(bz[id])
				var latlng=[];
				$.each(bz[id]._latlngs,function(n,c){
					latlng.push([c.lat,c.lng]);
				})
				var color=bz[id]._path.attributes.stroke.value;
				var xwidth=bz[id]._path.attributes["stroke-width"].value;
				var xtmd=bz[id]._path.attributes["stroke-opacity"].value;
				var xtxz=bz[id]._path.attributes["stroke-dasharray"] ? bz[id]._path.attributes["stroke-dasharray"].value : 1;
				var styles={stroke:color,weight:xwidth,opacity:xtmd,dasharray:xtxz}
			}
			else if(type=="polygon"){
				var latlng=[];
				$.each(bz[id]._latlngs[0],function(n,c){
					latlng.push([c.lat,c.lng]);
				})
				var color=bz[id]._path.attributes.stroke.value;
				var xwidth=bz[id]._path.attributes["stroke-width"].value;
				var xtmd=bz[id]._path.attributes["stroke-opacity"].value;
				var xtxz=bz[id]._path.attributes["stroke-dasharray"] ? bz[id]._path.attributes["stroke-dasharray"].value : 0;
				
				var fillColor=bz[id]._path.attributes["fill"].value;
				var fill_opacity=bz[id]._path.attributes["fill-opacity"].value;
				var styles={stroke:color,weight:xwidth,opacity:xtmd,dasharray:xtxz,fillColor:fillColor,fill_opacity:fill_opacity}
			}
			var xy= JSON.stringify(latlng);
			var mlid=$("#curmlid").val();
		$.post("/biaozhu/save",{type:type,title:title,bz:content,xy:xy,styles:JSON.stringify(styles),id:mlid,gk:gk},function(data,s){
			console.log(data);
			if(data.message=="success")
			{
				bz[id].remove();
				delete bz[id];
				var html="<div class='toubu'><span class='icon-pencil editbz' dtype='"+type+"' status='"+gk+"' did='"+data.data+"' title='修改'></span><span class='icon-cancel shanchuybz' did='"+data.data+"' title='移除'></span></div><div class='sqadd'><p id='sqbt'>"+title+"</p><p id='sqbz'>"+content+"</p></div>"
				if(type=="point")
				{
					bz[data.data]=L.marker(latlng,{icon:markerIcon.iconConfig(styles.url,styles.size)}).bindPopup(html).addTo(Lg.map);
				}
				else if(type=="line")
				{
					bz[data.data]=L.polyline(latlng,{color:styles.stroke,weight:styles.weight,opacity:styles.opacity,dasharray:styles.dasharray}).bindPopup(html).addTo(Lg.map);
				}
				else if(type="polygon")
				{
					bz[data.data]=L.polygon(latlng,{color:styles.stroke,weight:styles.weight,opacity:styles.opacity,dasharray:styles.dasharray,fillColor:styles.fillColor,fillOpacity:styles.fill_opacity}).bindPopup(html).addTo(Lg.map);
				}
				setTimeout(function(){
					bz[data.data].openPopup();
				},10)
			}
			else
			{
				alert(data.data);
			}
		})
	})
	
	
	
	/*****查询分析****/
	//圆形查询
	$(".dhccx").unbind('click');
	$(".dhccx").on("click",function(e){
		e.stopPropagation();
		Lg.map.off("click",searchfeatureFn);
		var me=$(this);
		$(this).siblings("span").removeClass("hov");
		$(this).parents(".tabtop").next(".tabbox").children(".tabitem").hide();
		var n=$(this).index();
		$(this).parents(".tabtop").next(".tabbox").children(".tabitem").eq(n).show();
		$(this).addClass("hov");
		Plotting.remove();
	})
	
	
	$(".yxcx").unbind('click');
	$(".yxcx").on("click",function(e){
		e.stopPropagation();
		removeTempLayer();
		var me=$(this);
		if(shpLayer._layers)
		{
			shpLayer.unbindPopup();
		}
		else
		{
			Lg.map.off("click",searchfeatureFn);
		}
		$(this).siblings("span").removeClass("hov");
		$(this).parents(".tabtop").next(".tabbox").children(".tabitem").hide();
		$(this).addClass("hov");
		Plotting.remove();
		Plotting.map=Lg.map;
		Plotting.circle(function(e){
			console.log(e)
			var x=e._latlng.lat;
			var y=e._latlng.lng;
			var radius=e._mRadius;
			e.remove();
			var tag=$("#curid").val();
			
			var cururl=$("#cururl").val();
			var curlayer=$("#curlayer").val();
			$.get('/gjson/list?tag='+tag+'&searchType=yx&xy='+x+'@'+y+'&jl='+radius,function(data,s){
				var data1={
					"type": "FeatureCollection",
					"features": data.list
					}
				tempLayer=L.geoJSON(data1, {
				    style: function (feature) {
				        return {fillColor:"#ff0000",fillOpacity:0.8,color:"#d80000"};
				    }
				}).addTo(Lg.map);
			})
			if(!tag)
			{
				me.removeClass("hov");
				alert("请选择要分析的图层");
				Plotting.remove();
				return;
			}
			
//			var query = L.esri.query({
//			    url: cururl+"/"+curlayer
//			});
//			
//			query.bboxIntersects(e)
//			query.run(function(error, featureCollection, response){
//				console.log(featureCollection);
//			})
			if(shpLayer._layers)
			{
				shpLayerAddPopup();
			}
			else
			{
				Lg.map.on("click",searchfeatureFn);
			}
			layer.open({
			  type: 2
			  ,content: '/view/list?tag='+tag+'&searchType=yx&xy='+x+'@'+y+'&jl='+radius
			  ,btnAlign: 'c' //按钮居中
			  ,shade: 0.3 //不显示遮罩
			  ,area: ['800px', '600px']
			  ,yes: function(){
			    layer.closeAll();
			  }
			});
			me.removeClass("hov");
		});
	})
	//多边形查询
	$(".dbxcx").unbind("click");
	$(".dbxcx").click(function(e){
		var me=$(this);
		if(shpLayer._layers)
		{
			shpLayer.unbindPopup();
		}
		else
		{
			Lg.map.off("click",searchfeatureFn);
		}
		removeTempLayer();
		e.stopPropagation();
		$(this).siblings("span").removeClass("hov");
		$(this).parents(".tabtop").next(".tabbox").children(".tabitem").hide();
		$(this).addClass("hov");
		Plotting.remove();
		Plotting.map=Lg.map;
		Plotting.polygon(function(e){
			console.log(e)
			e.remove();
			var latlngs=e._latlngs;
			var latlng1=[];
			$.each(latlngs[0],function(n,c){
				latlng1.push(c.lat+"@"+c.lng);
			})
			latlng1.push(latlngs[0][0].lat+"@"+latlngs[0][0].lng);
			var tag=$("#curid").val();
			$.get('/gjson/list?tag='+tag+'&searchType=dbx&xy='+latlng1+'&jl=',function(data,s){
				var data1={
					"type": "FeatureCollection",
					"features": data.list
					}
				tempLayer=L.geoJSON(data1, {
				    style: function (feature) {
				        return {fillColor:"#ff0000",fillOpacity:0.8,color:"#d80000"};
				    }
				}).addTo(Lg.map);
			})
			
			if(!tag)
			{
				me.removeClass("hov");
				alert("请选择要分析的图层");
				Plotting.remove();
				return;
			}
			if(shpLayer._layers)
			{
				shpLayerAddPopup();
			}
			else
			{
				Lg.map.on("click",searchfeatureFn);
			}
			layer.open({
			  type: 2
			  ,content: '/view/list?tag='+tag+'&searchType=dbx&xy='+latlng1+'&jl='
			  ,btnAlign: 'c' //按钮居中
			  ,shade: 0.3 //不显示遮罩
			  ,area: ['800px', '600px']
			  ,yes: function(){
			    layer.closeAll();
			  }
			});
			me.removeClass("hov");
		});
	})
	//矩形查询
	$(".jxcx").unbind("click");
	$(".jxcx").click(function(e){
		e.stopPropagation();
		removeTempLayer();
		var me=$(this);
		if(shpLayer._layers)
		{
			shpLayer.unbindPopup();
		}
		else
		{
			Lg.map.off("click",searchfeatureFn);
		}
		$(this).siblings("span").removeClass("hov");
		$(this).parents(".tabtop").next(".tabbox").children(".tabitem").hide();
		$(this).addClass("hov");
		Plotting.remove();
		Plotting.map=Lg.map;
		Plotting.rectangle(function(e){
			console.log(e)
			var latlngs=e.getBounds();
			var tag=$("#curid").val();
			$.get("/gjson/list?tag="+tag+'&searchType=jx&xy='+latlngs._northEast.lat+'@'+latlngs._northEast.lng+'@'+latlngs._southWest.lat+'@'+latlngs._southWest.lng+'&jl=',function(data,s){
				console.log(data.list)
				var data1={
					"type": "FeatureCollection",
					"features": data.list
					}
				tempLayer=L.geoJSON(data1, {
				    style: function (feature) {
				        return {fillColor:"#ff0000",fillOpacity:0.8,color:"#d80000"};
				    }
				}).addTo(Lg.map);
			})
			
			if(!tag)
			{
				me.removeClass("hov");
				alert("请选择要分析的图层");
				Plotting.remove();
				return;
			}
			if(shpLayer._layers)
			{
				shpLayerAddPopup();
			}
			else
			{
				Lg.map.on("click",searchfeatureFn);
			}
			layer.open({
			  type: 2
			  ,content: '/view/list?tag='+tag+'&searchType=jx&xy='+latlngs._northEast.lat+'@'+latlngs._northEast.lng+'@'+latlngs._southWest.lat+'@'+latlngs._southWest.lng+'&jl='
			  ,btnAlign: 'c' //按钮居中
			  ,shade: 0.3 //不显示遮罩
			  ,area: ['800px', '600px']
			  ,yes: function(){
			    layer.closeAll();
			  }
			});
			me.removeClass("hov");
		});
	})
	
	
	//选取坐标
	$(".xqzb").click(function(){
		setTimeout(function(){
			var Fn = function(e){
				var latlng=e.latlng;
				$("#latlng").val(latlng.lat+"@"+latlng.lng);
				Lg.map.off("click",Fn);
			}
			Lg.map.on("click",Fn);
		},10)
	})
	$("#quedingfx").click(function(e){
		e.stopPropagation();
		
	})
	$("#mycollect").on("click",function(){
		if($(this).hasClass("hov"))
		{
			$(".sclist").hide();
			$(this).removeClass("hov")
		}
		else
		{
			$(this).addClass("hov");
			$.get("/collect/list",function(data,s){
				$(".sclist").html("");
				$.each(data.list,function(n,c){
					$(".sclist").append("<li title='"+c.title+"' x='"+c.x+"' y='"+c.y+"' id='"+c.id+"' de='"+c.bz+"'>"+c.title+"</li>");
				})
			})
			$(".sclist").show();
		}
		
	})
	$(".sclist").on("click","li",function(e){
		e.stopPropagation();
		var x=$(this).attr("x");
		var y=$(this).attr("y");
		var id=$(this).attr("id");
		var sqbt=$(this).attr("title");
		var sqbz=$(this).attr("de");
		$(".sclist").hide();
		$("#mycollect").removeClass("hov")
		if(SQ.markerArr["y"+id])
		{
			Lg.map.setView([x,y]);
		}
		else
		{
			Lg.map.setView([x,y]);
			SQ.markerArr["y"+id]=L.marker([x,y]).bindPopup("<div class='toubu'><span class='icon-pencil editsq' ybq='y"+id+"' did='"+id+"' title='修改'></span><span class='icon-cancel shanchusq' ybq='y"+id+"' did='"+id+"' title='移除'></span></div><div class='sqadd'><p id='sqbt'>"+sqbt+"</p><p id='sqbz'>"+sqbz+"</p></div>").addTo(Lg.map).openPopup();
		}
	})
	//禁止div上滚动地图
	if($("#divid").length>0)
	{
		el=document.getElementById("divid");
	    L.DomEvent.addListener(el, 'mousedown dblclick mousewheel', function (e) {
	      L.DomEvent.stopPropagation(e);
	    });
	}
});
function searchfeatureFn(e){
	console.log(e)
	searchfeature(e.latlng,e.layerPoint);
}

function queryBounds(url,Bounds){
	var query=L.esri.query({
		url:"http://192.168.1.146:6080/arcgis/rest/services/population/MapServer/0"
	})
	query.within(e.getBounds());
	query.run(function(error,data,response){
		console.log(data)
	})
}
function hcbjfx(e,obj){
	e.stopPropagation();
	removeTempLayer();
	var latlng=$("#latlng").val();
	var bj=$("#bj").val();
	var tag=$("#curid").val();
	if(!tag)
	{
		alert("请选择要分析的图层");
		return;
	}
	if(!latlng)
	{
		alert("请选择坐标");
		return;
	}
	else if(!bj)
	{
		alert("请输入半径");
		return;
	}
	$.get('/gjson/list?tag='+tag+'&searchType=yx&xy='+latlng+'&jl='+bj,function(data,s){
		console.log(data.list)
		var data1={
			"type": "FeatureCollection",
			"features": data.list
			}
		tempLayer=L.geoJSON(data1, {
		    style: function (feature) {
		        return {fillColor:"#ff0000",fillOpacity:0.8,color:"#d80000"};
		    }
		}).addTo(Lg.map);
	})
	layer.open({
	    type: 2
	    ,content: '/view/list?tag='+tag+'&searchType=yx&xy='+latlng+'&jl='+bj
	    ,btnAlign: 'c' //按钮居中
	    ,shade: 0.3 //不显示遮罩
	    ,area: ['800px', '600px']
	    ,yes: function(){
	    layer.closeAll();
	  }
	});
	Lg.map.on("click",searchfeatureFn);
}

var data="";
var alayer="";
function checkshp(obj,mlid,id,url,layers,styles,curshptype){
	$("#curid").val("");
	$("#curmlid").val("");
	stylespz={};
	removebiaozhu();
	if(popup)
	{
		popup.remove();
		popup="";
	}
	$("#divid").hide();
	if(url)
	{
		$("#cururl").val(url);
	}
	if(id)
	{
		$("#curid").val(id);
	}
	if(layers)
	{
		$("#curlayer").val(layers);
	}
	if(mlid)
	{
		$("#curmlid").val(mlid);
	}
	if(curshptype)
	{
		$("#curshptype").val(curshptype);
	}
	if(styles)
	{
		$("#curshpstyles").val(styles);
	}
	if(alayer)
	{
		alayer.remove();
		alayer="";
	}
	if(!$(obj).hasClass("hov"))
	{
		$(obj).parents(".left_info ").find(".hov").removeClass("hov");
		if($("#fieldname").length>0)
		{
			//如有有配色字段的
			$.ajaxSettings.async = false;
			$.get("/search/all?fldtbid="+id,function(data,s){
				$("#fieldname").html("<option value=''>请选择</option>");
				$.each(data.page,function(n,c){
					$("#fieldname").append("<option value='"+c.fldname+"'>"+c.fldcnn+"</option>");
				})
			})
			$.ajaxSettings.async = true;
		}
		$(obj).addClass("hov");
		alayer=new L.layerGroup();
		alayer.addTo(Lg.map);
		if(styles==0)
		{
			$("#toumingdu").val(0.5);
			$("#tmdzhi").text(0.5);
			shpLayer=L.esri.dynamicMapLayer({
				url:url,
				layers:[layers],
				opacity:0.5
			}).addTo(alayer)
			setTimeout(function(){
				//点击地图查询属性
				Lg.map.on("click",searchfeatureFn)
			},200)
			console.log(shpLayer);
		}
		else
		{
			fieldlist=[];
			$.ajax({
				url:"/search/list",
				async:false,
				data:{tag:id},
				dataType:"json",
				success:function(data){
					fieldlist=data.page;
				}
			})
			
			if(curshptype=="point")
			{
				var spz=getPointStyles();
				shpLayer=L.esri.featureLayer({
				    url: url+"/"+layers,
				    simplifyFactor: 0.5,
				    precision: 8,
				    pointToLayer: spz
				}).addTo(alayer)
			}
			else
			{
				var spz=getStyles();
				shpLayer=L.esri.featureLayer({
				    url: url+"/"+layers,
				    simplifyFactor: 0.5,
				    precision: 8,
				    style:spz
				}).addTo(alayer)
			}
			shpLayer.bindPopup(function (layer) {
				var html=""
				$.each(fieldlist,function(n,c){
					console.log(c.fldname);
					html+="<p>"+c.fldcnn+"："+layer.feature.properties[c.fldname]+"</p>";
				})
			    return html;
			})
		}
		shpLayer.on("load",addBiaoZhu)
	}
	else
	{
		removebiaozhu();
		$(obj).removeClass("hov");
		$("#cururl").val("");
		$("#curid").val("");
		$("#curlayer").val("");
	}
}

//查询要素服务
function searchfeature(latlng,layerPoint){
	//以下为使用服务查询属性
	/*if(data)
	{
		data.query().layer(0).contains(latlng).run(function(error, featureCollection, response){
		    console.log(featureCollection);
		})
	}*/
	
	
	var zdzb=Lg.map.layerPointToLatLng([layerPoint.x+10,layerPoint.y]);
	var radius=latlng.distanceTo(zdzb);
	//以下为查询程序查询属性
	var curid=$("#curid").val();
	if(curid)
	{
		$.post("/search/list",{tag:curid,lat:latlng.lng,lng:latlng.lat,radius:radius},function(data){
			console.log(data)
			if(data.error)
			{
				
			}
			else
			{
				var html=""
				$.each(data.page,function(n,c){
					console.log(c.fldname);
					html+="<p>"+c.fldcnn+"："+data.tab[c.fldname]+"</p>";
				})
				popup = L.popup()
				    .setLatLng(latlng)
				    .setContent(html)
				    .openOn(Lg.map);
			}
		})
	}
}

function checkwmsserver(wmsurl,layers,sld){
	shpdatatomap = L.tileLayer.wmsf(wmsurl, {
		layers: [layers],
		format: 'image/png',
		transparent: true,
		identify:false,
		sld : sld
	})
	Lg.map.addLayer(shpdatatomap);
}

function removeTempLayer(){
	if(tempLayer)
	{
		tempLayer.remove();
		tempLayer="";
	}
}

function saveStyles(){
	var id=$("#curmlid").val();
	var styles=JSON.stringify(stylespz);
	$.post("/catalog/update",{id:id,styles:styles},function(data,s){
		console.log(data)
	})
}

function getStyles(){
	var id=$("#curmlid").val();
	var curshptype=$("#curshptype").val();
	
	var stylespzfn;
	if(JSON.stringify(stylespz) == "{}")
	{
		$.ajaxSettings.async = false;
		$.post("/catalog/search",{id:id},function(data,s){
			console.log(data.data)
			var stylepz=JSON.parse(data.data);
				stylespz=stylepz;
			var field=stylespz.field;
			curqj=[];
			$.each(stylespz.styles,function(n,c){
				curqj.push(c.where);
			})
			stylespzfn=function(feature){
				if((curshptype=="line" || curshptype=="polygon") && stylespz.fieldType=="string")
				{
					var pz=stylespz.styles[feature.properties[field]];
				}
				else if((curshptype=="line" || curshptype=="polygon") && stylespz.fieldType=="number")
				{
					var index=pdqj(feature.properties[field]);
					var pz=stylespz.styles[index];
				}
				return pz;
			}
		})
		$.ajaxSettings.async = true;
	}
	else
	{
		curqj=[];
		$.each(stylespz.styles,function(n,c){
			curqj.push(c.where);
		})
		var field=stylespz.field;
		stylespzfn=function(feature){
			if((curshptype=="line" || curshptype=="polygon") && stylespz.fieldType=="string")
			{
				var pz=stylespz.styles[feature.properties[field]];
			}
			else if((curshptype=="line" || curshptype=="polygon") && stylespz.fieldType=="number")
			{
				console.log(feature.properties)
				var index=pdqj(feature.properties[field]);
				var pz=stylespz.styles[index];
			}
			return pz;
		}
	}
	return stylespzfn;
}
function getPointStyles(){
	var id=$("#curmlid").val();
	var curshptype=$("#curshptype").val();
	
	var stylespzfn;
	if(JSON.stringify(stylespz) == "{}")
	{
		$.ajaxSettings.async = false;
		$.post("/catalog/search",{id:id},function(data,s){
			console.log(data.data)
			var stylepz=JSON.parse(data.data);
				stylespz=stylepz;
			var field=stylespz.field;
			curqj=[];
			$.each(stylespz.styles,function(n,c){
				curqj.push(c.where);
			})
			
			stylespzfn=function(feature, latlng){
				if(curshptype=="point" && stylespz.fieldType=="string")
				{
					var pz=stylespz.styles[feature.properties[field]];
				}
				else if(curshptype=="point" && stylespz.fieldType=="number")
				{
					var index=pdqj(feature.properties[field]);
					var pz=stylespz.styles[index];
				}
				return L.marker(latlng, {
				       	icon: L.icon(pz)
				       });
			}
		})
		$.ajaxSettings.async = true;
	}
	else
	{
		curqj=[];
		$.each(stylespz.styles,function(n,c){
			curqj.push(c.where);
		})
		var field=stylespz.field;
		stylespzfn=function(feature, latlng){
			if(curshptype=="point" && stylespz.fieldType=="string")
			{
				var pz=stylespz.styles[feature.properties[field]];
			}
			else if(curshptype=="point" && stylespz.fieldType=="number")
			{
				var index=pdqj(feature.properties[field]);
				var pz=stylespz.styles[index];
			}
			return L.marker(latlng, {
			       	icon: L.icon(pz)
			       });
		}
	}
	return stylespzfn;
}
function pdqj(val){
	var array=curqj;
	//如果值小于revenue最小的值时，则奖励0
	if(val < Math.min.apply(null,array)){
	
	return 0;
	
	}
	
	//如果值大于revenue最大的值时，则奖励最高一档
	
	if(val > Math.max.apply(null,array)){
	
	return array.length-1;
	
	}
	
	var idx = 0,i = 0,j = array.length;
	
	for(i;i<j;i++){
		
		if(array[i] > val){
		
		idx = i;
		
		break;
		
		};
	
	};

return idx;

};
function layerSetStyle(){
	var url=$("#cururl").val();
	var layers=$("#curlayer").val();
	var curshptype=$("#curshptype").val();
	var id=$("#curid").val();
	alayer.clearLayers();
	Lg.map.off("click",searchfeatureFn);
	var fieldlist=[];
	$.ajax({
		url:"/search/list",
		async:false,
		data:{tag:id},
		dataType:"json",
		success:function(data){
			fieldlist=data.page;
		}
	})
	if(curshptype=="point")
	{
		var spz=getPointStyles();
		shpLayer=L.esri.featureLayer({
		    url: url+"/"+layers,
		    simplifyFactor: 0.5,
		    precision: 8,
		    pointToLayer: spz
		}).addTo(alayer)
	}
	else
	{
		var spz=getStyles();
		shpLayer=L.esri.featureLayer({
		    url: url+"/"+layers,
		    simplifyFactor: 0.5,
		    precision: 8,
		    style:spz
		}).addTo(alayer)
	}
	
	shpLayer.bindPopup(function (layer) {
		var html=""
		$.each(fieldlist,function(n,c){
			console.log(c.fldname);
			html+="<p>"+c.fldcnn+"："+layer.feature.properties[c.fldname]+"</p>";
		})
	    return html;
	})
}

function setColpick(){
	$('.newjscolor').each(function(){
		$(this).colpick({
			colorScheme:'dark',
			layout:'rgbhex',
			color:$(this).val(),
			onSubmit:function(hsb,hex,rgb,el) {
				$(el).css('background-color', '#'+hex);
				$(el).val("#"+hex);
				$(el).colpickHide();
				var obj=$(el).parents(".pzbox").children(".headstyle").attr("d-v");
				if($(el).attr("v")=="mzs")
				{
					var z="fillColor";
				}
				else if(($(el).attr("v")=="xzs"))
				{
					var z="color";
				}
				stylespz.styles[obj][z]="#"+hex;
				saveStyles();
				layerSetStyle();
			}
		})
	})
}

function refreshicon(){
	pointlist=[];
	$.get("/icon/list",function(data,s){
		console.log(data)
		$.each(data.data,function(n,c){
			pointlist.push(c.url);
		})
	})
}

function biaozhumanage(mlid){
	$.post("/biaozhu/cataloglist",{id:mlid},function(data,s){
		console.log(data)
		$.each(data.list,function(n,c){
			dhtml="";
			uhtml="";
			
			if(c.delete==1)
			{
				dhtml="<span class='icon-cancel shanchuybz' did='"+c.id+"' title='移除'></span>";
			}
			if(c.update==1)
			{
				uhtml="<span class='icon-pencil editbz' dtype='"+c.type+"' status='"+c.status+"' did='"+c.id+"' title='修改'></span>";
			}
			var html="<div class='toubu'>"+uhtml+dhtml+"</div><div class='sqadd'><p id='sqbt'>"+c.title+"</p><p id='sqbz'>"+c.bz+"</p></div>";
			var styles=JSON.parse(c.styles);
			if(c.type=="point")
			{
				bz[c.id]=L.marker(JSON.parse(c.xy),{icon:markerIcon.iconConfig(styles.url,styles.size)}).bindPopup(html)
				bz[c.id].addTo(alayer);
			}
			else if(c.type=="line")
			{
				bz[c.id]=L.polyline(JSON.parse(c.xy),{color:styles.stroke,weight:styles.weight,opacity:styles.opacity,dashArray:styles.dasharray}).bindPopup(html);
				bz[c.id].addTo(alayer);
			}
			else if(c.type=="polygon")
			{
				bz[c.id]=L.polygon(JSON.parse(c.xy),{color:styles.stroke,weight:styles.weight,opacity:styles.opacity,dashArray:styles.dasharray,fillColor:styles.fillColor,fillOpacity:styles.fill_opacity}).bindPopup(html);
				bz[c.id].addTo(alayer);
			}
		})
		shpLayer.off("load",addBiaoZhu);
	})
}
function biaozhucheck(mlid){
	$.post("/biaozhu/cataloglist",{id:mlid},function(data,s){
		$.each(data.list,function(n,c){
			var html="<div class='toubu'></div><div class='sqadd'><p id='sqbt'>"+c.title+"</p><p id='sqbz'>"+c.bz+"</p></div>";
			var styles=JSON.parse(c.styles);
			if(c.type=="point")
			{
				bz[c.id]=L.marker(JSON.parse(c.xy),{icon:markerIcon.iconConfig(styles.url,styles.size)}).bindPopup(html);
				bz[c.id].addTo(alayer);
			}
			else if(c.type=="line")
			{
				bz[c.id]=L.polyline(JSON.parse(c.xy),{color:styles.stroke,weight:styles.weight,opacity:styles.opacity,dashArray:styles.dasharray}).bindPopup(html)
				bz[c.id].addTo(alayer);
			}
			else if(c.type=="polygon")
			{
				bz[c.id]=L.polygon(JSON.parse(c.xy),{color:styles.stroke,weight:styles.weight,opacity:styles.opacity,dashArray:styles.dasharray,fillColor:styles.fillColor,fillOpacity:styles.fill_opacity}).bindPopup(html);
				bz[c.id].addTo(alayer);
			}
		})
	})
}
function shpLayerAddPopup(){
	shpLayer.bindPopup(function (layer) {
		var html=""
		$.each(fieldlist,function(n,c){
			console.log(c.fldname);
			html+="<p>"+c.fldcnn+"："+layer.feature.properties[c.fldname]+"</p>";
		})
	    return html;
	})
}

function removebiaozhu(){
	$.each(bz,function(n,c){
		c.remove();
		delete c;
	})
}
function addBiaoZhu(){
	var mlid=$("#curmlid").val();
	if($("#curpage").val()=="biaozhu")
	{
		removebiaozhu();
		biaozhumanage(mlid);
	}
	else if($("#curpage").val()=="biaozhucheck")
	{
		removebiaozhu();
		biaozhucheck(mlid);
	}
}

//更新首页地震列表
function updateIndexList(){
	$.get("",function(){
		
	})
}

//更新首页场域
function updateIndexCy(){
	$(".eventlist li.hov").click();
}

//以下为万能输入小数
function keyPress(ob) {
 if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/)) ob.value = ob.t_value; else ob.t_value = ob.value; if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)) ob.o_value = ob.value;
}
function keyUp(ob) {
 if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/)) ob.value = ob.t_value; else ob.t_value = ob.value; if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)) ob.o_value = ob.value;
        }
function onBlur(ob) {
if(!ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))ob.value=ob.o_value;else{if(ob.value.match(/^\.\d+$/))ob.value=0+ob.value;if(ob.value.match(/^\.$/))ob.value=0;ob.o_value=ob.value};
}

