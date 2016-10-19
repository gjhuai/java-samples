var gcfutil = {
	/**
	 * 是否是IE浏览器并且版本小于等于IE8
	 */
	isLessEqualIE8 : function() {
		if (!domutils.isIE()) {
			return false;
		} else {
			// IE8: Trident/4.0
			// IE9: Trident/5.0
			// IE10: Trident/6.0
			// IE11: Trident/7.0
			//
			// IE6: MSIE 6.0
			// IE7: MSIE 7.0 (注：在IE11中“X-UA-Compatible，IE=6”时也有“MSIE 7.0”)
			var UA = /\bTrident\/([\w.]+)/.exec(navigator.userAgent);
			if (UA && parseFloat(UA[1]) > 6) { // IE11除外
				return false;
			} else {
				return true;
			}
		}
	},

	/**
	 * 是否已安装Chrome插件
	 */
	isGcfAvailable : function() {
		if ('undefined' == typeof CFInstall) {
			//TODO
		}
		return CFInstall.isAvailable();
	},

	/**
	 * 是否需要提示安装Chrome插件
	 */
	isNeedPromptSetupGCF : function() {
		if (!this.isLessEqualIE8()) {
			return false;
		} else {
			return !this.isGcfAvailable();
		}
	},

	/**
	 * 显示安装Chrome插件的提示
	 */
	showSetupGcfPrompt : function() {
		if (!this.isNeedPromptSetupGCF()) {
			return;
		}
		var id = 'gcfbar';
		var elem = document.getElementById(id);
		if (elem) {
			if (elem.style.display == 'none') {
				elem.style.display = '';
			}
			return;
		}
		elem = document.createElement('div');
		elem.id = id;
		var buf = [];
		buf.push('width:100%;padding:5px 0px;border-top:1px solid #666;position:absolute;');
		buf.push('left:0;bottom:0;text-align:center;z-index:10000;');
		buf.push('font-family:SimSun,Arial !important;font-size:12px;');
		buf.push('color:#000 !important;background:#FFFFE1;cursor:default;');
		elem.style.cssText = buf.join('');
		//
		buf = [];
		buf.push('<img src="icon.png" style="float:left;" />');
		buf.push('您的IE浏览器版本过低，部分功能可能无法正常使用，请升级到IE11或安装');
		buf.push('<a href="ChromeFrame.zip">ChromeFrame</a>');
		buf.push('插件。');
		elem.innerHTML = buf.join('');
		document.body.appendChild(elem);
	}
};
var domutils={
		isIE : function() {
			return this.Browser.ie === true;
		}
};
( function(exporter) {
	// exporter = exporter || window;
	var ua = navigator.userAgent.toLowerCase();
	var platform = navigator.platform.toLowerCase();
	var regexEdge = /(edge)[\s\/:]([\w\d\.]+)?.*?(safari|version[\s\/:]([\w\d\.]+)|$)/;
	var regex = /(opera|ie|firefox|chrome|version|iphone os|ipad os)[\s\/:]([\w\d\.]+)?.*?(safari|version[\s\/:]([\w\d\.]+)|$)/;
	var UA = ua.match(regexEdge) || ua.match(regex) || [ null, 'unknown', 0 ];
	var mode = null; // UA[1] == 'ie' && document.documentMode;
	var Browser = {
		name : (UA[1] == 'version') ? UA[3] : UA[1],
		version : mode || ((UA[1] == 'opera' && UA[4]) ? UA[4] : UA[2]),
		Platform : {
			name : ua.match(/ip(?:ad|od|hone)/) ? 'ios' : (ua.match(/(?:webos|android)/)
					|| platform.match(/mac|win|linux/) || [ 'other' ])[0],
			version : ua.match(/ip(?:ad|od|hone)/) ? null : (ua.match(/android(\s|\d|\.)*/)|| [ 'other' ])[0]
		}
	};
	if (Browser.version && (typeof Browser.version =="string")) {
		Browser.version = parseFloat(Browser.version.replace('_', '.'));
	}
	if (Browser.name == 'unknown') {
		if ((UA = /(msie\s|trident.*rv:)([\w.]+)/.exec(ua))) {
			Browser.name = 'ie';
			Browser.version = parseFloat(UA[2] || '0');
		}
	}
	Browser[Browser.name] = true;
	Browser[Browser.name + parseInt(Browser.version, 10)] = true;
	Browser.Platform[Browser.Platform.name] = true;
	if (Browser.Platform.android) {
		//Browser.Platform.androidApp = true;
	}
	Browser.Lang = (navigator.userLanguage || navigator.language || '').toLowerCase();
	if (ua.indexOf("qqbrowser") >= 0)
		Browser.qqBrowser = true;
	exporter['Browser'] = Browser;
})(domutils);