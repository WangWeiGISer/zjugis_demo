var plan_map = {};

//base
mapInit.root_path = {};
plan_map.detailmapconfig = {};
var imgPage = {};
imgPage.images = [];
imgPage.index = 0;


plan_map.returnobj = [];
plan_map.config = {};
plan_map.current_config = {};
plan_map.mask_control = {};
plan_map.getInspectPoint_gragpicslayer = {};
plan_map.listview = {};
plan_map.clickbtn = {};
plan_map.polygonbtn = {};
plan_map.extentbtn = {};
plan_map.selected_graphics = {};
plan_map.select_points = [];
plan_map.map = {};
plan_map.polygonsymbol = {};
plan_map.plansketchs = [];

plan_map.queryIndex = 0;
plan_map.queryGeometry = {};
plan_map.cache = [];
plan_map.inspectpoint_graghics = [];

//detailmap
plan_map.detail_map = {};
plan_map.detailmap_mask = {};
plan_map.inspect_detail_graphiclayer = {};
plan_map.symbols = {};
plan_map.detail_layer = {};
plan_map.showTrace = {};
plan_map.itemLocate = {};
plan_map.showSubDetail = {};

plan_map.accordion = {};
plan_map.getAccordionData = {};
plan_map.accordion_data = [];

detailmap_infotable = {};
plan_map.infotabVue = {};
plan_map.infotab_hide_node = {};
plan_map.handleInfodata = {};


plan_map.op = {
    inline:true,
    button:false,
    navbar:false,
    title:false,
    toolbar:true,
    tooltip:true,
    movable:true,
    zoomable:true,
    rotatable:true,
    transition:true,
    fullscreen:false,
    keyboard:true,
    interval:5000,
    minWidth:600,
    minHeight:557

};

plan_map.reset = function(){
    if (typeof (plan_map.map)!=="undefined"){
        plan_map.map.setExtent(new esri.geometry.Extent(plan_map.Extent), true);
        getInspectPoint_gragpicslayer.clear();
        plan_map.extent_gragpicslayer.clear();
    }
    plan_map.point_list.$data.select_points = [];
    plan_map.ids = [];
    plan_map.plansketchs = [];
}

plan_map.detailMapReset = function(){
    plan_map.infotabVue.hide();
    plan_map.infotabVue.reset();
    plan_map.detail_map.setExtent(new esri.geometry.Extent(plan_map.Extent), true);
    plan_map.inspect_detail_graphiclayer.clear();
}

plan_map.reDrawGraphics = function (data) {
    getInspectPoint_gragpicslayer.clear();
    for (var i=0;i<data.length;i++){
        var graphic = new esri.Graphic(data[i].geometry, plan_map.polygonsymbol);
        getInspectPoint_gragpicslayer.add(graphic);
    }
}

plan_map.restorePoints = function (data) {
    require(["dojo/on","esri/tasks/query","esri/tasks/QueryTask","esri/layers/GraphicsLayer","esri/toolbars/draw"],
        function (on,Query,QueryTask,GraphicsLayer,Draw) {
            plan_map.point_list.$data.select_points = data;

            getInspectPoint_gragpicslayer.clear();
            for (var i=0;i<data.length;i++){
                var item = data[i];
                if (item.isIllegal != 0){
                    var polygon = geometryJsonUtils.fromJson(JSON.parse(item.sketchs));
                    var graphic = new esri.Graphic(polygon, plan_map.symbols.polygon);
                    getInspectPoint_gragpicslayer.add(graphic);
                }else {
                    var layer = plan_map.getLayerbyID(plan_map.config,item['layerId']);
                    var url = layer.url;
                    var outfields = ["*"];
                    var queryTask = new QueryTask(url);
                    var query = new Query();
                    query.outFields = outfields;
                    query.returnGeometry = true;
                    query.where = item['objectKey'] + "=" + item['objectValue'];
                    queryTask.execute(query, afterquery,queryErrback);
                }
            }
            function afterquery(result) {
                if (result.features.length>0){
                    var feature = result.features[0];
                    var attr = feature.attributes;
                    var geometry = feature.geometry;
                    var graphic = new esri.Graphic(geometry, plan_map.symbols.polygon);
                    getInspectPoint_gragpicslayer.add(graphic);
                }

            }

            function queryErrback(result) {
                var info = result;
            }
        });
}

/*初始化添加检查地块的地图*/
plan_map.initPointsMap = function(mymap) {
    plan_map.listview = $("#point_list_view");
    plan_map.polygonbtn = $("#point_select_polygon");
    plan_map.clickbtn = $("#point_select_click");
    plan_map.extentbtn = $("#point_select_cancel");
    plan_map.drawregion = $("#drawregion");
    var map = mymap.map;
    plan_map.map = map;
    plan_map.mask_control = mymap.mask_control;
    var symbol = mymap.symbol;
    plan_map.polygonsymbol = symbol.dashlinepolygon;
    plan_map.mask_control.hide();
    require(["dojo/on","esri/tasks/query","esri/tasks/QueryTask","esri/layers/GraphicsLayer","esri/toolbars/draw"],
        function (on,Query,QueryTask,GraphicsLayer,Draw) {
            var GraphicsLayerOptions = {
                "id": "getInspectPoint_gragpicslayer"
            };
            var extentGraphicsLayerOptions = {
                "id": "extent_gragpicslayer"
            };
            if (typeof (getInspectPoint_gragpicslayer) === "undefined")
            getInspectPoint_gragpicslayer=new GraphicsLayer(GraphicsLayerOptions);
            if (typeof (plan_map.extent_gragpicslayer) === "undefined")
            plan_map.extent_gragpicslayer=new GraphicsLayer(extentGraphicsLayerOptions);
            map.addLayer(getInspectPoint_gragpicslayer);
            map.addLayer(plan_map.extent_gragpicslayer);
            var toolBar = new Draw(map);
            var selectflag = false;
            var drawflag = false;

            on(map,"click",function (e) {
                if (selectflag === false && drawflag===false){
                    plan_map.queryGeometry = e.mapPoint;
                    plan_map.queryIndex = 0;
                    doQuery(plan_map.queryGeometry,plan_map.queryIndex);
                }
            });
            on(plan_map.drawregion,"click",function () {
                if (selectflag === true){
                    layer.msg("请先结束监察点选择操作");
                }else {
                    drawflag = true;
                    toolBar.activate(Draw.POLYGON);
                    plan_map.polygonbtn.attr("disabled", true);
                    plan_map.clickbtn.attr("disabled", true);
                    plan_map.extentbtn.attr("disabled", true);
                }
            });
            on(plan_map.polygonbtn,"click",function () {
                if (selectflag === false){
                    toolBar.activate(Draw.POLYGON);
                    selectflag = true;
                }else {
                    toolBar.finishDrawing();
                    selectflag = false;
                }
            });
            on(plan_map.extentbtn,"click",function () {
                toolBar.activate(Draw.EXTENT);
            });
            on(plan_map.clickbtn,"click",function () {
                toolBar.deactivate();
                selectflag = false;
            });
            on(toolBar, "draw-complete", function (result) {
                if (drawflag === true){
                    drawflag = false;
                    plan_map.polygonbtn.attr("disabled", false);
                    plan_map.clickbtn.attr("disabled", false);
                    plan_map.extentbtn.attr("disabled", false);
                    toolBar.deactivate();
                    if (result.geometry.type === "polygon"){
                        plan_map.plansketchs.push(result.geometry.toJson());
                        var graphic = new esri.Graphic(result.geometry, plan_map.polygonsymbol);
                        plan_map.extent_gragpicslayer.add(graphic);
                        return;
                    }
                    return;
                }
                if (selectflag === true){
                    selectflag = false;
                }
                toolBar.deactivate();
                plan_map.queryGeometry = result.geometry;
                plan_map.queryIndex = 0;
                doQuery(plan_map.queryGeometry,plan_map.queryIndex);
            });

            function doQuery(geometry,index) {
                plan_map.mask_control.show();
                var length = plan_map.config.length;
                if (index<length){
                    var url = plan_map.config[index].url;
                    var outfields = ["*"];
                    var queryTask = new QueryTask(url);
                    var query = new Query();
                    query.outFields = outfields;
                    query.returnGeometry = true;
                    query.geometry = geometry;
                    queryTask.execute(query, afterquery,queryErrback);
                }else {
                    plan_map.mask_control.hide();
                }
            }

            function queryErrback() {
                var length = plan_map.config.length;
                var current_config = plan_map.config[plan_map.queryIndex];
                layer.msg('图层' + current_config.layerName + '查询出错，请联系技术人员');
                if (plan_map.queryIndex<length){
                    plan_map.queryIndex++;
                    doQuery(plan_map.queryGeometry,plan_map.queryIndex);
                }
            }

            function afterquery(result) {
                var length = plan_map.config.length;
                var current_config = plan_map.config[plan_map.queryIndex];
                var getInspectPoint_gragpicslayer=map.getLayer("getInspectPoint_gragpicslayer");
                var features = result.features;
                for (var i = 0;i<features.length;i++){
                    var geometry = features[i].geometry;
                    var attr = features[i].attributes;
                    attr['geometry'] = geometry;
                    if (isContain(plan_map.cache,JSON.stringify(attr))){
                        continue;
                    }
                    plan_map.cache.push(JSON.stringify(attr));
                    plan_map.point_list.$data.select_points.push(plan_map.handelPointData(attr,current_config));
                    var graphic = new esri.Graphic(geometry, plan_map.polygonsymbol);
                    getInspectPoint_gragpicslayer.add(graphic);
                }
                if (plan_map.queryIndex<length){
                    plan_map.queryIndex++;
                    doQuery(plan_map.queryGeometry,plan_map.queryIndex);
                }
            }

            function isContain(arr,obj) {
                var index = arr.indexOf(obj);
                var o = $.inArray(obj, arr);
                if (index !== -1) {
                    return true;
                }
                return false;
            }
        })
}

/*按添加检查地块的config格式化数据*/
plan_map.handelPointData = function (data,config) {
    var select_point = {};
    select_point['geometry'] = data.geometry;
    select_point['layerId'] = config.layerId;
    select_point['layerName'] = config.layerName;
    select_point['pointName'] = data[config.pointName];
    select_point['objectKey'] = config.objectKey;
    select_point['objectValue'] = data[config.objectKey];
    select_point['id'] = data[config.objectKey];
    select_point['text'] = data[config.pointName];
    return select_point;
}


/*初始化计划查看地图*/
plan_map.initDetailMap = function(mapstr){

    //控制轨迹播放的面板
    plan_map.paramcomponentVue = new Vue({
        el: '#paramcomponent',
        data: {
            traceShow: false,
            item:{}
        },
        methods:{
            endTrace:function (item) {
                plan_map.endTrace(item);
            },
            startTrace:function (item) {
                plan_map.startTrace(item);
            }
        }
    });

    /*查看巡查计划详情手风琴控件vue*/
    plan_map.accordion = new Vue({
        el: '#accordion',
        data: {
            items: [],
            tipshow:true
        },
        methods:{
            dropdown:function(event){
                e = event.currentTarget;
                var $el = $("#accordion");
                $this = $(e);
                $next = $this.next();

                $next.slideToggle();
                $this.parent().toggleClass('open');
                $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
            },
            showDetail:function (item,event) {
                plan_map.showTrace(item);
            },
            showSubDetail:function (item,event) {
                plan_map.showSubDetail(item);
            }
        }
    });

    plan_map.infotab = $("#detail_tab");
    plan_map.infotab_hide_node = $("#tab_close");
    plan_map.detailmapconfig = {};

    var configpath = mapInit.root_path + "/admin/datasource/getds";
    $.ajax({
        url: configpath,
        async:false,
        dataType: 'json',
        type: 'post',
        data: {},
        success: function (_r, _s) {
            if (_r.success) {
                var Json = _r.result;
                if(Json.success === false){
                    layer.msg(Json.configinfo);
                }
                plan_map.Extent = Json.Extent;
                plan_map.detailmapconfig.LoadType = CONSTANT.MapLoadType.Complete;
                plan_map.detailmapconfig.Extent = Json.Extent;
                plan_map.detailmapconfig.MapLayer = Json.MapLayer;
                plan_map.detailmapconfig.wkid = Json.wkid;
                plan_map.detailmapconfig.LayerGroup = Json.LayerGroup;
                plan_map.config = Json.getInspectPoint_config;
                plan_map.detailmapconfig.region_config = Json.region_config;
                plan_map.illegalprops = Json.illegalmap_config.mainField;
                mapInit.initMap(mapstr,plan_map.detailmapconfig,afterDetailMap);
            } else {
                layer.msg('地图配置出错');
            }
        },
        error: function (_ex) {
            layer.msg('查询出错');
        }
    });


    function afterDetailMap(mymap){
        plan_map.detail_map = mymap.map;
        plan_map.detailmap_mask = mymap.mask_control;
        plan_map.symbols = mymap.symbol;
        plan_map.detailmap_mask.hide();
        require(["dojo/on","esri/tasks/query","esri/tasks/QueryTask","esri/layers/GraphicsLayer","esri/toolbars/draw"],
            function (on,Query,QueryTask,GraphicsLayer,Draw) {

                on(plan_map.infotab_hide_node,"click",function () {
                    plan_map.infotab.hide();
                });

                var GraphicsLayerOptions = {
                    "id": "getInspectPoint_gragpicslayer"
                };
                plan_map.inspect_detail_graphiclayer=new GraphicsLayer(GraphicsLayerOptions);
                plan_map.detail_map.addLayer(plan_map.inspect_detail_graphiclayer);

                plan_map.infotabVue = new Vue({
                    el: '#detail_tab',
                    data: {
                        attrs: [],
                        mediaEntity:[],
                        showinfotab:false
                    },
                    methods:{
                        reset:function () {
                            plan_map.infotabVue.$data.attrs = [];
                            plan_map.infotabVue.$data.mediaEntity = [];
                        },
                        hide:function () {
                            this.showinfotab = false;
                        },
                        show:function () {
                            this.showinfotab = true;
                        },
                        showImage:function (id,imgs,index, event) {
                            imgPage.index = index;
                            imgPage.images = imgs;
                            plan_map.showBigImage(id);
                        },
                        showVideo:function (id, event) {
                            plan_map.showBigVideo(id);
                        },
                        showVoice:function (id, event) {
                            plan_map.showBigVoice(id);
                        }
                    }
                });
        });
        detail_map_loaded = true;
    }
}

plan_map.showBigImage = function (id) {
    var path = mapInit.root_path + "/file/file?fileId="+id;
    $('#image_item_show').attr("src", path);
    var viewer = new Viewer(document.getElementById('image_item_show'), plan_map.op);
    viewer.next = function () {
        if (typeof (imgPage) !== 'undefined'){
            var length = imgPage.images.length;
            if (imgPage.index<length-1){
                imgPage.index ++;
                $('.viewer-canvas img').attr("src", mapInit.root_path + '/file/file?fileId='+imgPage.images[imgPage.index]);
            }
        }
    };
    viewer.prev = function () {
        if (typeof (imgPage) !== 'undefined'){
            var length = imgPage.images.length;
            if (imgPage.index>0){
                imgPage.index --;
                $('.viewer-canvas img').attr("src", mapInit.root_path + '/file/file?fileId='+imgPage.images[imgPage.index]);
            }
        }
    };
    plan_map.imageLayer = layer.open({
        type: 1,
        title: '图片查看',
        maxmin: false,
        resize:false,
        shade: [0],
        area: ['600px', '610px'],
        closeBtn: 1,
        content: $('#image_show'),
        cancel: function () {
            $('#image_show').hide();
            try {
                viewer.destroy();
            } catch (e) {
            }
        }
    });
}

plan_map.showBigVideo = function (id) {
    var path = mapInit.root_path + "/file/file?fileId="+id;
    plan_map.imageLayer = layer.open({
        type: 1,
        title: '视频播放',
        maxmin: false,
        resize:false,
        shade: [0],
        area: ['600px', '388px'],
        closeBtn: 1,
        content: $('#video_show'),
        cancel: function () {
            $('#video_show').hide();
        },
        success:function () {
            $('#video_player').attr("src",path);
        }
    });
}

plan_map.showBigVoice = function (id) {
    var path = mapInit.root_path + "/file/file?fileId="+id;
    plan_map.wavesurfer = WaveSurfer.create({
        container: '#waveform',
        waveColor: 'violet',
        progressColor: 'purple'
    });
    plan_map.wavesurfer.load(path);
    plan_map.wavesurfer.on('ready', function () {
        plan_map.wavesurfer.play();
    });
    plan_map.voiceLayer = layer.open({
        type: 1,
        title: '录音播放',
        maxmin: false,
        resize:false,
        shade: [0],
        area: ['600px', '200px'],
        closeBtn: 1,
        content: $('#voice_show'),
        cancel: function () {
            $('#voice_show').hide();
            try {
                plan_map.wavesurfer.destroy();
            } catch (e) {
            }
        }
    });
}

//查询计划下所有的任务
plan_map.getAccordionData = function (planid) {
    var path = mapInit.root_path + "/inspect/getInspectsInfoByPlanID";
    $.ajax({
        url: path,
        dataType: 'json',
        type: 'post',
        data: {"planid":planid},
        success: function (_r, _s) {
            if (_r.success) {
                var data = _r.result;
                plan_map.accordion.$data.items = data;
                plan_map.accordion.$data.tipshow = false;
                if (data.length === 0){
                    plan_map.accordion.$data.tipshow = true;
                }
            } else {
                layer.msg('查询出错');
            }
        },
        error: function (_ex) {
            layer.msg('查询出错');
        }
    });
}//查询单个任务
plan_map.getAccordionDatabyActual = function (actualID) {
    var path = mapInit.root_path + "/inspect/getInspectInfoByID";
    $.ajax({
        url: path,
        dataType: 'json',
        type: 'post',
        data: {"actualID":actualID},
        success: function (_r, _s) {
            if (_r.success) {
                var data = _r.result;
                plan_map.accordion.$data.items = [];
                plan_map.accordion.$data.items.push(data);
                plan_map.accordion.$data.tipshow = false;
                if (typeof (data) === 'undefined'){
                    plan_map.accordion.$data.tipshow = true;
                }
            } else {
                layer.msg('查询出错');
            }
        },
        error: function (_ex) {
            layer.msg('查询出错');
        }
    });
}

/*处理进行展示的监察地块数据*/
plan_map.handelInfoData = function (data,config) {
    var returnattr = [];
    if (config.length === 0)
        return [];
    for (var i=0;i<config.length;i++){
        var attrs = {};
        attrs.key = config[i]['aliasName'];
        attrs.value = data[config[i]['name']];
        returnattr.push(attrs);
    }
    return returnattr;
}

/*监察点的点击事件*/
plan_map.showSubDetail = function (item) {
    plan_map.infotabVue.reset();
    plan_map.infotabVue.$data.mediaEntity = item.mediaEntity;
    require(["dojo/on","esri/tasks/query","esri/tasks/QueryTask","esri/layers/GraphicsLayer","esri/toolbars/draw","esri/geometry/jsonUtils"],
        function (on,Query,QueryTask,GraphicsLayer,Draw,geometryJsonUtils) {
            plan_map.inspect_detail_graphiclayer.clear();
            if (item.isIllegal != 0){
                plan_map.infotabVue.show();
                var polygon = geometryJsonUtils.fromJson(JSON.parse(item.sketchs));
                var attr = JSON.parse(item.props);
                var extent = polygon.getExtent();
                plan_map.detail_map.setExtent(extent,true);
                plan_map.infotabVue.$data.attrs = plan_map.handelInfoData(attr,plan_map.illegalprops);
                var graphic = new esri.Graphic(polygon, plan_map.symbols.polygon);
                plan_map.inspect_detail_graphiclayer.add(graphic);
            }else {
                var layer = plan_map.getLayerbyID(plan_map.config,item['layerId']);
                var url = layer.url;
                var outfields = ["*"];
                var queryTask = new QueryTask(url);
                var query = new Query();
                query.outFields = outfields;
                query.returnGeometry = true;
                query.where = item['objectKey'] + "=" + item['objectValue'];
                queryTask.execute(query, afterquery,queryErrback);
            }
            function afterquery(result) {
                if (result.features.length>0){
                    var feature = result.features[0];
                    var attr = feature.attributes;
                    plan_map.infotabVue.show();
                    plan_map.infotabVue.$data.attrs = plan_map.handelInfoData(attr,layer.showFields)
                    var geometry = feature.geometry;
                    plan_map.detail_map.setExtent(geometry.getExtent(),true);
                    var graphic = new esri.Graphic(geometry, plan_map.symbols.polygon);
                    plan_map.inspect_detail_graphiclayer.add(graphic);
                }

            }

            function queryErrback(result) {
                var info = result;
            }
        });

}

plan_map.showTrace = function (item) {
    plan_map.paramcomponentVue.$data.traceShow = true;
    plan_map.paramcomponentVue.$data.item = item;
    plan_map.paramcomponentVue.$nextTick(function(){
        plan_map.rangepicker = $("#rate_range").rangepicker({
            startValue: 1,
            endValue: 30,
            translateSelectLabel: function(currentPosition, totalPosition) {
                return parseInt(30 * (currentPosition / totalPosition));
            }
        });
    });
}

/*播放轨迹*/
plan_map.startTrace = function () {
    require(["dojo/on","esri/layers/GraphicsLayer","esri/toolbars/draw","esri/geometry/jsonUtils","esri/geometry/Point","esri/geometry/Polyline"],
        function (on,GraphicsLayer,Draw,geometryJsonUtils,Point,Polyline) {
            plan_map.inspect_detail_graphiclayer.clear();
            clearInterval(plan_map.play);
            var item = plan_map.paramcomponentVue.$data.item;
            if (typeof (item.sketchs) ==="undefined"){
                layer.msg('无轨迹数据');
                return;
            }
            var pointArray = item.sketchs.split(";");
            var arrLength = pointArray.length;
            if (arrLength ===0 || arrLength ==="undefined"){
                layer.msg('无轨迹数据');
                return;
            }
            var path = [];
            for (var i = 0;i<arrLength;i++){
                path.push(new Point(pointCoord(pointArray[i]),plan_map.detail_map.spatialReference));
            }
            var startPoint = new Point(pointCoord(pointArray[0]),plan_map.detail_map.spatialReference);
            var endPoint = new Point(pointCoord(pointArray[arrLength-1]),plan_map.detail_map.spatialReference);
            var pathpolyline = new Polyline(plan_map.detail_map.spatialReference);
            pathpolyline.addPath(path);
            var graphicstartPoint = new esri.Graphic(startPoint,plan_map.symbols.pointGeneral);
            var graphicendPoint = new esri.Graphic(endPoint,plan_map.symbols.pointGeneral);
            var graphicpolyline = new esri.Graphic(pathpolyline,plan_map.symbols.line);
            plan_map.inspect_detail_graphiclayer.add(graphicpolyline);
            plan_map.inspect_detail_graphiclayer.add(graphicendPoint);
            plan_map.inspect_detail_graphiclayer.add(graphicstartPoint);
            plan_map.detail_map.setExtent(pathpolyline.getExtent(),true);

            var value = plan_map.rangepicker.getSelectValue();
            var duration = value.endLabel;
            var interval;
            if (duration === 0){
                interval = 1000;
            }else{
                interval = (duration * 1000) /arrLength;
            }
            
            function pointCoord(str) {
                var coord = str.split(",");
                return coord;
            }

            var flag = 0;
            function addPoint() {
                if (flag<arrLength){
                    var point = new Point(pointCoord(pointArray[flag]),plan_map.detail_map.spatialReference);
                    graphicstartPoint.setGeometry(point);
                    plan_map.inspect_detail_graphiclayer.redraw();
                }else {
                    clearInterval(plan_map.play);
                }
                flag++;
            }

            plan_map.play = setInterval(addPoint,interval);

        });
}

plan_map.endTrace = function () {
    clearInterval(plan_map.play);
    plan_map.inspect_detail_graphiclayer.clear();
    plan_map.paramcomponentVue.$data.traceShow = false;
}

plan_map.getLayerbyID = function (layers,layerid) {
    for (var i = 0;i<layers.length;i++){
        if (layers[i].layerId === layerid){
            return layers[i];
        }
    }
}


