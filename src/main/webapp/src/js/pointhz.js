//(function(win,doc,undefined){
//	var SQ=function(map,callback){
//		
//	}
//	SQ.prototype={
//		init:function(){
//			
//		},
//	}
//	
//	win.SQ = SQ; 
//})(window,document)

DW={
	map:{},
	index:0,
	markerArr:{},
	markertemp:"",
	latlngArr:"",
	init:function(url,callback){
		var _this=this;
		var a=new L.layerGroup();
		a.addTo(_this.map);
		var ydmarker=function(e){
			_this.removemarker();
			_this.index=_this.index+1;
			_this.markertemp=L.marker([e.latlng.lat,e.latlng.lng],{icon:markerIcon.iconConfig(url)}).addTo(a);
		}
		var clickmap=function(e){
			var _this=DW;
			_this.removemarker();
			_this.index=_this.index+1;
			_this.latlngArr=[e.latlng.lat,e.latlng.lng];
			_this.markerArr[_this.index]=L.marker(_this.latlngArr,{icon:markerIcon.iconConfig(url),id:_this.index}).addTo(a);
			_this.markerArr[_this.index].openPopup();
			if(callback)
			{
				callback(_this.markerArr[_this.index]);
			}
			Lg.map.off("mousemove",ydmarker).off("mousedown",clickmap);
		}
		
		Lg.map.on("mousemove",ydmarker);
		Lg.map.on("mousedown",clickmap);
		$("#map").css("cursor","default");
	},
	addmarker:function(e){
		var _this=DW;
		_this.removemarker();
		_this.index=_this.index+1;
		_this.markertemp=L.marker([e.latlng.lat,e.latlng.lng],{icon:markerIcon.iconConfig(url)}).addTo(_this.map);
	},
	addmarker1:function(e){
		var _this=DW;
		_this.removemarker();
		_this.index=_this.index+1;
		_this.latlngArr=[e.latlng.lat,e.latlng.lng];
		_this.markertemp[_this.index]=L.marker(_this.latlngArr,{id:_this.index}).addTo(_this.map);
		Lg.map.off("mousemove",_this.addmarker).off("click",_this.addmarker1);
	},
	removemarker:function(){
		var _this=DW;
		if(_this.markertemp)
		{
			_this.markertemp.remove();
//			_this.markertemp="";
		}
	},
	removeMarker:function(index){
		markerArr[index].remove();
		delete markerArr[index];
	}
}
