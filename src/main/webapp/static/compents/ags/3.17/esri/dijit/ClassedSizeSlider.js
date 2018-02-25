// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.17/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/ClassedSizeSlider/templates/ClassedSizeSlider.html":'\x3cdiv class\x3d"${baseClass}"\x3e\r\n  \x3cdiv data-dojo-attach-point\x3d"containerNode"\x3e\r\n  \x3cdiv data-dojo-attach-point\x3d"_titleNode"\x3e\x3c/div\x3e\r\n  \x3cdiv data-dojo-attach-point\x3d"_sliderNode"\x3e\x3c/div\x3e\r\n  \x3cdiv data-dojo-attach-point\x3d"_scaleNode"\x3e\x3c/div\x3e\r\n  \x3c/div\x3e\r\n\x3c/div\x3e'}});
define("esri/dijit/ClassedSizeSlider","../kernel ../numberUtils ../renderers/utils ../dijit/RendererSlider ../dijit/RendererSlider/sliderUtils ../Color dijit/_TemplatedMixin dijit/_WidgetBase dojo/_base/array dojo/_base/declare dojo/_base/lang dojo/debounce dojo/dom-style dojo/Evented dojo/has dojox/gfx dojo/text!./ClassedSizeSlider/templates/ClassedSizeSlider.html".split(" "),function(n,p,m,q,e,r,g,s,h,t,d,u,k,v,w,x,y){g=t([s,g,v],{declaredClass:"esri.dijit.ClassedSizeSlider",baseClass:"esriClassedSizeSlider",
templateString:y,breakInfos:null,histogram:null,handles:[],showHistogram:!0,showStatistics:!0,showLabels:!0,showTicks:!0,showHandles:!0,classificationMethod:null,normalizationType:null,histogramWidth:100,rampWidth:26,_rampNode:null,_sliderHeight:null,_colorRampSurface:null,_histogramSurface:null,_surfaceRect:null,_barsGroup:null,_updateTimer:null,constructor:function(a,b){b&&(this.breakInfos=d.clone(a.breakInfos),this.set("values",this._getHandleInfo(this.breakInfos)),this._updateTimeout=u(this._updateTimeout,
0))},postCreate:function(){this.inherited(arguments);this._setupDataDefaults()},startup:function(){this.inherited(arguments);this._slider=new q({type:"ClassedSizeSlider",values:this.values,minimum:this.minValue,maximum:this.maxValue,showLabels:this.showLabels,showTicks:this.showTicks,showHandles:this.showHandles,classificationMethod:this.classificationMethod,normalizationType:this.normalizationType},this._sliderNode);this._slider.startup();this._rampNode=this._slider._sliderAreaRight;this._sliderHeight=
k.get(this._rampNode,"height")||155;this._createSVGSurfaces();this._slider.on("slide",d.hitch(this,function(a){this._updateBreakInfos(a.values);this._updateBreakInfoLabels();this._fillRamp()}));this._slider.on("change",d.hitch(this,function(a){this.set("values",a.values);this._updateBreakInfos(a.values);this._updateBreakInfoLabels();this._fillRamp();this.emit("change",d.clone(this.breakInfos))}));this._slider.on("handle-value-change",d.hitch(this,function(a){this._updateBreakInfos(a.values);this._updateBreakInfoLabels();
this._fillRamp();this.emit("handle-value-change",d.clone(this.breakInfos))}));this._slider.on("data-value-change",d.hitch(this,function(a){this.set("minValue",a.min);this.breakInfos[0].minValue=a.min;this.set("maxValue",a.max);this.breakInfos[this.breakInfos.length-1].maxValue=a.max;this._updateBreakInfoLabels();this._updateRendererSlider();this.emit("data-value-change",{minValue:this.minValue,maxValue:this.maxValue,breakInfos:d.clone(this.breakInfos)})}));this._slider.on("stop",d.hitch(this,function(a){this.emit("handle-value-change",
d.clone(this.breakInfos))}));this.histogram&&this.showHistogram&&this._generateHistogram();this.statistics&&this.showStatistics&&this._generateStatistics();this.watch("breakInfos",this._updateTimeout);this.watch("handles",this._updateTimeout);this.watch("statistics",this._updateTimeout);this.watch("showHandles",this._updateTimeout);this.watch("showLabels",this._updateTimeout);this.watch("showTicks",this._updateTimeout);this.watch("histogram",this._showHistogram);this.watch("showHistogram",this._toggleHistogram)},
destroy:function(){this.inherited(arguments);this._slider&&this._slider.destroy();this._avgHandleObjs&&this._avgHandleObjs.avgHandleTooltip&&this._avgHandleObjs.avgHandleTooltip.destroy();this.countTooltips&&h.forEach(this.countTooltips,function(a){a.destroy()})},_updateTimeout:function(){this._updateRendererSlider()},_updateRendererSlider:function(){this.set({minValue:this.breakInfos[0].minValue,maxValue:this.breakInfos[this.breakInfos.length-1].maxValue});this._slider.set({minimum:this.minValue,
maximum:this.maxValue,values:this._getHandleInfo(this.breakInfos),handles:this.handles});this._slider._reset();this._slider._updateRoundedLabels();this._slider._generateMoveables();this._clearRect();this._createSVGSurfaces();this.histogram&&this.showHistogram&&this._generateHistogram();this.statistics&&this.showStatistics&&this._generateStatistics()},_getHandleInfo:function(a){var b=[],c;for(c=0;c<a.length-1;c++)b.push(a[c].maxValue);return b},_updateBreakInfos:function(a){var b=this.breakInfos,c;
m.updateClassBreak({classBreaks:b,normalizationType:this.normalizationType,classificationMethod:this.classificationMethod,change:a});for(c=0;c<a.length;c++)b[c].maxValue=a[c],b[c+1]&&(b[c+1].minValue=a[c])},_updateBreakInfoLabels:function(){m.setLabelsForClassBreaks({classBreaks:this.breakInfos,normalizationType:this.normalizationType,classificationMethod:this.classificationMethod,round:!0})},_setupDataDefaults:function(){null!==this.breakInfos&&1<this.breakInfos.length?this.set({minValue:this.breakInfos[0].minValue,
maxValue:this.breakInfos[this.breakInfos.length-1].maxValue}):null!==this.breakInfos&&1===this.breakInfos.length?this.set({minValue:this.breakInfos[0].minValue,maxValue:this.breakInfos[0].maxValue}):(this.set({minValue:0,maxValue:100,breakInfos:[{minValue:0,maxValue:20},{minValue:20,maxValue:80},{minValue:80,maxValue:100}]}),this.set("values",this._getHandleInfo(this.breakInfos)),this._updateBreakInfoLabels())},_createSVGSurfaces:function(){this._colorRampSurface=x.createSurface(this._rampNode,this.rampWidth,
this._sliderHeight);this._surfaceRect=this._colorRampSurface.createRect({width:this.rampWidth,height:this._sliderHeight});this._histogramSurface=e.generateHistogramSurface(this._rampNode,this.histogramWidth,this._sliderHeight,this.rampWidth);this._fillRamp()},_clearRect:function(){this._colorRampSurface.destroy();this._histogramSurface.destroy()},_fillRamp:function(){var a=this.breakInfos,b=this._slider,c=this._sliderHeight,l=[],f,e,g,l=h.map(a,function(a){return[c-Math.round((a.minValue-b.minimum)/
(b.maximum-b.minimum)*c),c-Math.round((a.maxValue-b.minimum)/(b.maximum-b.minimum)*c)]});l.reverse();this._colorRampSurface.clear();f=this.rampWidth/a.length;e=this.rampWidth;g=this._colorRampSurface.createPath().moveTo(e,0);h.forEach(l,d.hitch(this,function(a,b){g.lineTo(e,a[0]);e=this.rampWidth-f*(b+1);g.lineTo(e,a[0])}));g.lineTo(1,c).lineTo(0,c).lineTo(0,0).closePath().setFill(new r([0,121,193,0.8]))},_showHistogram:function(){this.histogram?this._generateHistogram():this._barsGroup&&(this._barsGroup.destroy(),
this._barsGroup=null)},_toggleHistogram:function(){this.showHistogram?(k.set(this._barsGroup.rawNode,"display","inline-block"),this._showHistogram()):k.set(this._barsGroup.rawNode,"display","none")},_generateHistogram:function(){this._barsGroup=e.generateHistogram(this._histogramSurface,this.histogram,this.histogramWidth,this.rampWidth,this.isLeftToRight());this.countTooltips=e.generateCountTooltips(this.histogram,this._barsGroup)},_generateStatistics:function(){if(!(2>this.statistics.count||isNaN(this.statistics.avg))){var a=
this.statistics,b=this._slider,c=e.getPrecision(this.maxValue),d,f;a.min===a.max&&a.min===a.avg?(d=0,f=2*a.avg):(d=a.min,f=a.max);if(d!==b.minimum||f!==b.maximum)d=b.minimum,f=b.maximum;b=this._sliderHeight*(f-a.avg)/(f-d);a=p.round([a.avg,f,d])[0];this._avgHandleObjs=e.generateAvgLine(this._histogramSurface,a,b,c,this.isLeftToRight())}}});w("extend-esri")&&d.setObject("dijit.ClassedSizeSlider",g,n);return g});