/*
 * tab切换组件
 * @author:xizi
 * @params:
	 * @handlers 		聚焦控制者
	 * @targetsDom 		聚焦控制者所控制的单位区域
	 * @focusClass 		聚焦控制者聚焦时所添加的样式名
	 * @type 			聚焦表现形式 click--单机  hover--移动
 * @callback: 回调函数，自带参数 function(t){console.log(t)} 查看参数信息
 */
	var $ = jQuery;
	var __bind = function(fn,me){
		return function(){
			return fn.apply(me,arguments);
		}
	}
	
	function SwitchOver(params,callback){
		this.p = $.extend({
			handlers : '',
			targetsDom : '',
			focusClass : 'cur',
			type : 'click',
			effect : false,
			auto:false
		},params || {});
		this.callback = callback || '';
		this._init();
	}

	//初始化
	SwitchOver.prototype._init = function(){
		this.p.handlers.each(__bind(this._eachHandler,this));
		if(this.p.auto){
			this.index = 0;
			this.len = this.p.handlers.length;
			this._eachHandlerAuto();
		}
	}

	//循环每一个聚焦控制者
	SwitchOver.prototype._eachHandlerAuto = function(){
		var _t = this;
		this.timeout = setTimeout(function(){
			_t.index++;
			if(_t.index > _t.len) _t.index = 0;
			_t._eachFocus.apply({_t:_t,i:_t.index,o:_t.p.handlers.eq(_t.index)});  //初始化第一个聚焦
			_t._eachHandlerAuto();
		},4000)
	}

	//循环每一个聚焦控制者
	SwitchOver.prototype._eachHandler = function(i,o){
		var o = jQuery(o);
		if(this.p.type === 'hover')	o.hover(__bind(this._eachOn,{_t:this,i:i,o:o}),__bind(this._eachOut,this));
		else o.click(__bind(this._eachFocus,{_t:this,i:i,o:o}));
	}

	//聚焦事件
	SwitchOver.prototype._eachFocus = function(){
		var _p = this._t.p;
		if(this.o.hasClass(_p.focusClass)) return;
		_p.handlers.removeClass(_p.focusClass);
		this.o.addClass(_p.focusClass);
		if(_p.effect) _p.targetsDom.eq(this.i).siblings().fadeOut(500);
		else _p.targetsDom.eq(this.i).siblings().css('display','none');
		_p.targetsDom.eq(this.i).fadeIn(500);
		if(typeof this._t.callback === 'function') this._t.callback(this);
	}

	//hover事件
	SwitchOver.prototype._eachOn = function(){
		var _eachOn = function(){
			this._t._eachFocus.apply({_t:this._t,i:this.i,o:this.o});
		}
		this._t.outTime = setTimeout(__bind(_eachOn,this),200);
		clearTimeout(this._t.timeout);
	}

	SwitchOver.prototype._eachOut = function(){
		clearTimeout(this.outTime);
		if(this.p.auto){
			this._eachHandlerAuto();
		}
	}

	SwitchOver.prototype.setFocusOne = function(n){
		this._eachFocus.apply({_t:this,i:n,o:this.p.handlers.eq(n)});
	}

