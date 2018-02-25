/**
 * index首页mapjs
 */
var indexmap = {};
indexmap.refresh = {};
indexmap.afterinit = function (mymap) {
    var map = mymap.map;
    indexmap.map = mymap.map;
    var mask_control = mymap.mask_control;
    var symbol = mymap.symbol;
    mask_control.hide();
    require(["dojo/on","esri/layers/GraphicsLayer","esri/InfoTemplate"],
        function (on,GraphicsLayer,InfoTemplate) {
            var GraphicsLayerOptions = {
                "id": "plan_index_gragpicslayer"
            };
            var plan_index_gragpicslayer=new GraphicsLayer(GraphicsLayerOptions);
            map.addLayer(plan_index_gragpicslayer);
            on(plan_index_gragpicslayer,"click",function (e) {
                var attr = e.graphic.attributes;
                map.infoWindow.setTitle("人员信息");
                map.infoWindow.resize(250, 200);
                map.infoWindow.setContent("<table class='maptab_table'>"
                    + "<tr><td class='maptab_table_title'>当前用户:" + "</td><td>" + mapInit.objNullResult(attr.user,"string") + "</td></tr>"
                    + "<tr><td class='maptab_table_title'>记录时间:" + "</td><td>" + mapInit.objNullResult(attr.time,"string") + "</td></tr>"
                    + "<tr><td class='maptab_table_title'>联系电话:" + "</td><td>" + mapInit.objNullResult(attr.phone,"string") + "</td></tr>"
                    + "<tr><td class='maptab_table_title'>登陆ip:" + "</td><td>" + mapInit.objNullResult(attr.ip,"string") + "</td></tr>"
                    + "<tr><td class='maptab_table_title'>X坐标:" + "</td><td>" + mapInit.objNullResult(attr.Xcoord,"string") + "</td></tr>"
                    + "<tr><td class='maptab_table_title'>Y坐标:" + "</td><td>" + mapInit.objNullResult(attr.Ycoord,"string") + "</td></tr>");
                if (e.graphic.geometry.type==="point"){
                    map.infoWindow.show(e.graphic.geometry, map.getInfoWindowAnchor(e.screenPoint));
                }else {
                    map.infoWindow.show(e.mapPoint, map.getInfoWindowAnchor(e.screenPoint));
                }
            });
            indexmap.refresh = setInterval(getUsersTrack,3*60*1000);
            getUsersTrack();
            function getUsersTrack() {
                var path = mapInit.root_path + "/monitor/getUsersTrack";
                $.ajax({
                    url: path,
                    dataType: 'json',
                    type: 'post',
                    data: {
                    },
                    success: function (_r, _s) {
                        if (_r.success) {
                            var data = _r.result;
                            plan_index_gragpicslayer.clear();
                            var pointsymbol = symbol.point;
                            var ie = webUtils.IEVersion();
                            if (typeof (ie)!== 'undefined' && ( ie === '-IE10')){
                                pointsymbol = symbol.pointGeneral;
                            }
                            indexmap.addPointtoLayer(plan_index_gragpicslayer,data,pointsymbol,indexmap.map.spatialReference);
                            if (data.length === 0){
                                layer.msg('今日尚无巡查位置信息');
                            }
                        } else {
                            layer.msg('查询出错');
                        }
                    },
                    error: function (_ex) {
                        layer.msg('查询出错'+_ex);
                    }
                });
            }
        })
}

/*对应图层上添加点符号*/
indexmap.addPointtoLayer = function (layer,points,symbol,sp) {
    require(["dojo/colors","esri/symbols/Font","esri/symbols/PictureMarkerSymbol","esri/symbols/TextSymbol","esri/InfoTemplate","esri/geometry/Point"],
        function (Color,Font,PictureMarkerSymbol,TextSymbol,InfoTemplate,Point) {
            for (var i = 0;i<points.length;i++){
                var point = points[i];
                var pointgeo = new Point(point.pointx,point.pointy,sp);
                var textSymbol =  new TextSymbol(point.aliasname + "  " +point.newtime).setColor(
                    new Color([238,238,238])).setAlign(Font.ALIGN_START).setAngle(0).setFont(
                    new Font("10pt").setWeight(Font.WEIGHT_BOLD)).setOffset(0, -20) ;
                var attr = {"user":point.aliasname,"time":point.newtime,"Xcoord":mapInit.cutstrellipse(point.pointx,11,"..."),"Ycoord":mapInit.cutstrellipse(point.pointy,11,"..."),
                "phone":point.phone,"ip":point.login_ip};
                var graphic1 = new esri.Graphic(pointgeo, symbol,attr,null);
                var graphic2 = new esri.Graphic(pointgeo, textSymbol,attr,null);
                layer.add(graphic1);
                layer.add(graphic2);
            }
        })
}

var indexHeatmap = {};

indexHeatmap.afterinit = function (mymap) {
    indexHeatmap.map = mymap.map;
    indexHeatmap.mask_control = mymap.mask_control;
    indexHeatmap.symbol = mymap.symbol;
    require(["dojo/on","esri/layers/GraphicsLayer","esri/layers/FeatureLayer","esri/renderers/HeatmapRenderer","esri/tasks/query",
            "esri/tasks/QueryTask"],
        function (on,GraphicsLayer,FeatureLayer,HeatmapRenderer,Query,QueryTask) {

            var layerDefinition = {
                "geometryType": "esriGeometryPoint",
                "fields": [{
                    "name": "ID",
                    "type": "esriFieldTypeInteger",
                    "alias": "ID"
                }]
            };

            var featureCollection = {
                layerDefinition: layerDefinition,
                featureSet:null
            };

            indexHeatmap.heatmap_FeatureLayer = new FeatureLayer(featureCollection,{
                mode: FeatureLayer.MODE_SNAPSHOT,
                opacity:1});

            indexHeatmap.map.addLayer(indexHeatmap.heatmap_FeatureLayer);

            indexHeatmap.heatmapRenderer = new HeatmapRenderer({
                //field: "FID",
                blurRadius: 5,
                maxPixelIntensity: 50,
                minPixelIntensity: 5
            });
            indexHeatmap.heatmap_FeatureLayer.setRenderer(indexHeatmap.heatmapRenderer);
            indexHeatmap.mask_control.show();
            var date = new Date();
            date.setDate(date.getDate()-10);
            var heatmap_param = {};
            heatmap_param.starttime = heatmapUtil.formaterYMD(date, "yyyy-MM-dd");
            heatmap_param.endtime = heatmapUtil.formaterYMD(new Date(), "yyyy-MM-dd");
            var path = mapInit.root_path + "/monitor/getRegionHistoryTrack";
            $.ajax({
                url: path,
                dataType: 'json',
                type: 'post',
                data: {
                    "paramsStr":JSON.stringify(heatmap_param),
                },
                success: function (_r, _s) {
                    indexHeatmap.mask_control.hide();
                    if (_r.success) {
                        var data = _r.result;
                        if (typeof (data) === "string"){
                            data = JSON.parse(data);
                        }
                        if (data.length === 0){
                            layer.msg('查询结果为空');
                            return;
                        }
                        var track = "";
                        for(var i = 0;i<data.length;i++){
                            track = track + data[i].track;
                            if (i<data.length-1){
                                track += ";";
                            }
                        }
                        var array = mapInit.handlePointStrData(track);
                        var heatmap_graphics = mapInit.toGrapgicPointArrayLimit(array,indexHeatmap.map.spatialReference,indexHeatmap.symbol.point,5000);
                        heatmapUtil.reDraw(indexHeatmap.heatmap_FeatureLayer,heatmap_graphics);
                    } else {
                        layer.msg('查询出错');
                    }
                },
                error: function (_ex) {
                    layer.msg('查询出错');
                }
            });
        })
}

var heatmapUtil = {};
heatmapUtil.reDraw = function(layer,graphics) {
    layer.clear();
    var l = graphics.length;
    for (var i=0;i<l;i++){
        layer.add(graphics[i]);
    }
    layer.redraw();
};
heatmapUtil.formaterYMD = function (date, fmt) {
    var o = {
        "M+": date.getMonth() + 1,
        "d+": date.getDate(),
    };
    if (/(y+)/.test(fmt)){
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

