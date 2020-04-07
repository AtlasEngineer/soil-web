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

SQ={
	map:{},
	index:0,
	markerArr:{},
	markertemp:"",
	latlngArr:"",
	init:function(callback){
		var _this=this;
		Lg.map.on("mousemove",_this.addmarker);
		Lg.map.on("click",function(e){
			var _this=SQ;
			_this.removemarker();
			_this.index=_this.index+1;
			_this.latlngArr=[e.latlng.lat,e.latlng.lng];
			_this.markerArr[_this.index]=L.marker(_this.latlngArr,{id:_this.index}).bindPopup("<div class='toubu'></div><div class='sqadd'><p><input type='text' id='sqbiaoti' placeholder='标题'></p><p><textarea id='sqbz' placeholder='备注'></textarea></p><p class='rightbtnbox'><input type='button' did="+_this.index+" class='baocunsq btn bluebtn' value='临存'><input type='button' did="+_this.index+" class='shoucangsq btn bluebtn' value='收藏'><input type='button' value='删除' did="+_this.index+" class='shanchusq btn graybtn'></p></div>").addTo(_this.map);
			_this.markerArr[_this.index].openPopup();
			if(callback)
			{
				callback(_this.latlngArr);
			}
			Lg.map.off("mousemove",_this.addmarker).off("click");
		});
		$("#map").css("cursor","default");
	},
	addmarker:function(e){
		var _this=SQ;
		_this.removemarker();
		_this.index=_this.index+1;
		_this.markertemp=L.marker([e.latlng.lat,e.latlng.lng]).addTo(_this.map);
	},
	addmarker1:function(e){
		var _this=SQ;
		_this.removemarker();
		_this.index=_this.index+1;
		_this.latlngArr=[e.latlng.lat,e.latlng.lng];
		_this.markertemp[_this.index]=L.marker(_this.latlngArr,{id:_this.index}).addTo(_this.map);
		Lg.map.off("mousemove",_this.addmarker).off("click",_this.addmarker1);
	},
	removemarker:function(){
		var _this=SQ;
		if(_this.markertemp)
		{
			_this.markertemp.remove();
		}
	},
	removeMarker:function(index){
		markerArr[index].remove();
		delete markerArr[index];
	}
}
