//var map = L.map('map').setView([39.744984, 115.932075], 13);;
//
//L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
//	attribution: 'Powered by <a href="https://www.esri.com">Esri</a> | &copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
//}).addTo(map);
$(function(){
	setTimeout(function(){
		var control = L.Routing.control({
			waypoints: [
				L.latLng(39.734984,115.912075),
				L.latLng(39.750256,115.955094)
			],
			router: L.Routing.esri({
		    liveTraffic: true,
		    profile: 'Driving',
		    //serviceUrl: 'https://utility.arcgis.com/usrsvcs/appservices/rdcfU1A3eVNshs0d/rest/services/World/Route/NAServer/Route_World',
			serviceUrl: 'http://192.168.1.142:6080/arcgis/rest/services/route/NAServer/route'
		  }),
			geocoder: L.Control.Geocoder.nominatim(),
			routeWhileDragging: true,
			reverseWaypoints: true
		})
		control.addTo(Lg.map);
		L.Routing.errorControl(control).addTo(Lg.map);
	},1000)
})
