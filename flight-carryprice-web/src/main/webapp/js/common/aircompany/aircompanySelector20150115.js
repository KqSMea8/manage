/* *
 * ---------------------------------------- *
 * 城市选择组件 v1.0
 * Author: VVG
 * QQ: 83816819
 * Mail: mysheller@163.com
 * http://www.cnblogs.com/NNUF/
 * ---------------------------------------- *
 * Date: 2012-07-10
 * ---------------------------------------- *
 * */

/* *
 * 全局空间 Jair
 * */
var Jair = {};
/* *
 * 静态方法集
 * @name _m
 * */
Jair._m = {
    /* 选择元素 */
    $:function (arg, context) {
        var tagAll, n, eles = [], i, sub = arg.substring(1);
        context = context || document;
        if (typeof arg == 'string') {
            switch (arg.charAt(0)) {
                case '#':
                    return document.getElementById(sub);
                    break;
                case '.':
                    if (context.getElementsByClassName) return context.getElementsByClassName(sub);
                    tagAll = Jair._m.$('*', context);
                    n = tagAll.length;
                    for (i = 0; i < n; i++) {
                        if (tagAll[i].className.indexOf(sub) > -1) eles.push(tagAll[i]);
                    }
                    return eles;
                    break;
                default:
                    return context.getElementsByTagName(arg);
                    break;
            }
        }
    },

    /* 绑定事件 */
    on:function (node, type, handler) {
        node.addEventListener ? node.addEventListener(type, handler, false) : node.attachEvent('on' + type, handler);
    },

    /* 获取事件 */
    getEvent:function(event){
        return event || window.event;
    },

    /* 获取事件目标 */
    getTarget:function(event){
        return event.target || event.srcElement;
    },

    /* 获取元素位置 */
    getPos:function (node) {
        var scrollx = document.documentElement.scrollLeft || document.body.scrollLeft,
            scrollt = document.documentElement.scrollTop || document.body.scrollTop;
        var pos = node.getBoundingClientRect();
        return {top:pos.top + scrollt, right:pos.right + scrollx, bottom:pos.bottom + scrollt, left:pos.left + scrollx }
    },

    /* 添加样式名 */
    addClass:function (c, node) {
        if(!node)return;
        node.className = Jair._m.hasClass(c,node) ? node.className : node.className + ' ' + c ;
    },

    /* 移除样式名 */
    removeClass:function (c, node) {
        var reg = new RegExp("(^|\\s+)" + c + "(\\s+|$)", "g");
        if(!Jair._m.hasClass(c,node))return;
        node.className = reg.test(node.className) ? node.className.replace(reg, '') : node.className;
    },

    /* 是否含有CLASS */
    hasClass:function (c, node) {
        if(!node || !node.className)return false;
        return node.className.indexOf(c)>-1;
    },

    /* 阻止冒泡 */
    stopPropagation:function (event) {
        event = event || window.event;
        event.stopPropagation ? event.stopPropagation() : event.cancelBubble = true;
    },
    /* 去除两端空格 */
    trim:function (str) {
        return str.replace(/^\s+|\s+$/g,'');
    }
};

/* 所有城市数据,可以按照格式自行添加（北京|beijing|bj），前16条为热门城市 */
/* airname数据源来源于airname.js文件 */
Jair.allAir = aircomArray;
/*
/* 正则表达式 筛选中文城市名、拼音、首字母 */

Jair.regEx = /^([\u4E00-\u9FA5\uf900-\ufa2d]+)\|(\w+)\|(\w)\w*\|(\w*)$/i;
Jair.regExChiese = /([\u4E00-\u9FA5\uf900-\ufa2d]+)/;
Jair.regExEnglish = /([^\u4E00-\u9FA5\uf900-\ufa2d]+)/;
Jair.regExThreecode = /(\|[a-zA-Z]{2})|(\|[a-zA-Z]{1}[0-9]{1})|(\|[0-9]{1}[a-zA-Z]{1})/;
/* *
 * 格式化航司数组为对象oAir，按照hot热门航司分组：
 * {HOT:{hot:[]}}
 * */

(function () {
    var airs = Jair.allAir, match, letter,
        regEx = Jair.regEx,
        reg2 = /^[a-h]$/i, reg3 = /^[i-p]$/i, reg4 = /^[q-z]$/i;
    if (!Jair.oAir) {
        Jair.oAir = {hot:{}};
        for (var i = 0, n = airs.length; i < n; i++) {
            match = regEx.exec(airs[i]);
            /* 航司 显示全部 2014.11.26 fuys */
            if(i<airs.length){
                if(!Jair.oAir.hot['hot']) Jair.oAir.hot['hot'] = [];
                Jair.oAir.hot['hot'].push(airs[i]);//match[1]+match[4]
            }
        }
    }
})();

/* *
 * 城市控件构造函数
 * @aircompanySelector
 * */

Jair.aircompanySelector = function () {
    this.initialize.apply(this, arguments);
};

Jair.aircompanySelector.prototype = {

    constructor: Jair.aircompanySelector,

    /* 初始化 */

    initialize: function (options) {
        var input = options.input;
        this.input = Jair._m.$('#' + input);
        var inputHidden = options.inputHidden;
        this.inputHidden = Jair._m.$('#' + inputHidden);
        this.inputEvent();
    },

    /* *
    * @createWarp
    * 创建航司BOX HTML 框架
    * */
    createWarp: function () {
        var inputPos = Jair._m.getPos(this.input);
        var div = this.rootDiv = document.createElement('div');
        var that = this;

        // 设置DIV阻止冒泡
        Jair._m.on(this.rootDiv, 'click', function (event) {
            Jair._m.stopPropagation(event);
        });

        // 设置点击文档隐藏弹出的城市选择框
        Jair._m.on(document, 'click', function (event) {
            event = Jair._m.getEvent(event);
            var target = Jair._m.getTarget(event);
            if (target == that.input) return false;
            if (that.airBox) Jair._m.addClass('hide', that.airBox);
            if (that.ul) Jair._m.addClass('hide', that.ul);
            if (that.myIframe) Jair._m.addClass('hide', that.myIframe);
        });
        div.className = 'aircompanySelector';
        div.style.position = 'absolute';
        div.style.left = inputPos.left + 'px';
        div.style.top = inputPos.bottom + 'px';
        div.style.zIndex = 999999;

        // 判断是否IE6，如果是IE6需要添加iframe才能遮住SELECT框
        var isIe = (document.all) ? true : false;
        var isIE6 = this.isIE6 = isIe && !window.XMLHttpRequest;
        if (isIE6) {
            var myIframe = this.myIframe = document.createElement('iframe');
            myIframe.frameborder = '0';
            myIframe.src = 'about:blank';
            myIframe.style.position = 'absolute';
            myIframe.style.zIndex = '-1';
            this.rootDiv.appendChild(this.myIframe);
        }
        this.createHotAir();
    },
    /* *
    * @createHotAir
    * TAB下面DIV：hot 分类HTML生成，DOM操作
    * {HOT:{hot:[]}}
    **/

    createHotAir: function () {
        var odiv, odl, odt, odd, odda = [], str, key, ckey, sortKey, regEx = Jair.regEx,
            oAir = Jair.oAir;
        odda = [];
        for (sortKey in oAir['hot']['hot']) {
            var a = oAir['hot']['hot'][sortKey];
            match = Jair.regEx.exec(oAir['hot']['hot'][sortKey]);
            str = '<li><b class="airname">' + match[1] + '</b><b class="airspell">' + match[4] + '</b><b class="airthreecode" style="display:none">|' + match[4] + '</b></li>';
            odda.push(str);
        }
        // 如果slideul不存在则添加ul
        if (!this.ul) {
            var ul = this.ul = document.createElement('ul');
            ul.className = 'airslide';            
            this.rootDiv && this.rootDiv.appendChild(ul);
            // 记录按键次数，方向键
            this.count = 0;
        } else if (this.ul && Jair._m.hasClass('hide', this.ul)) {
            this.count = 0;
            Jair._m.removeClass('hide', this.ul);
        }
        this.ul.innerHTML = odda.join('');
        document.body.appendChild(this.rootDiv);
        /* IE6 */
        this.changeIframe();

        // 绑定Li事件
        this.liEvent();
    },

    /* *
    *  tab按字母顺序切换
    *  @ tabChange
    * */

    tabChange: function () {
        var lis = Jair._m.$('li', this.airBox);
        var divs = Jair._m.$('div', this.hotAir);
        var that = this;
        for (var i = 0, n = lis.length; i < n; i++) {
            lis[i].index = i;
            lis[i].onclick = function () {
                for (var j = 0; j < n; j++) {
                    Jair._m.removeClass('on', lis[j]);
                    Jair._m.addClass('hide', divs[j]);
                }
                Jair._m.addClass('on', this);
                Jair._m.removeClass('hide', divs[this.index]);
                /* IE6 改变TAB的时候 改变Iframe 大小*/
                that.changeIframe();
            };
        }
    },

    /* *
    * INPUT航司输入框事件
    * @inputEvent
    * */

    inputEvent: function () {
        var that = this;
        Jair._m.on(this.input, 'click', function (event) {
            event = event || window.event;
            if (!that.airBox) {
                // 生成航司初始化框
                that.createWarp();
            } else if (!!that.airBox && Jair._m.hasClass('hide', that.airBox)) {
                // slideul 不存在或者 slideul存在但是是隐藏的时候 两者不能共存
                if (!that.ul || (that.ul && Jair._m.hasClass('hide', that.ul))) {
                    Jair._m.removeClass('hide', that.airBox);
                    // IE6 移除iframe 的hide 样式 
                    Jair._m.removeClass('hide', that.myIframe);
                    that.changeIframe();
                }
            }
        });
        Jair._m.on(this.input, 'focus', function () {
            that.input.select();
            if (that.input.value == '航司名') that.input.value = '';
        });
        Jair._m.on(this.input, 'blur', function () {
            if (that.input.value == '') {
                that.input.value = '';
            }else{
	            if(that.isEmpty){
	               that.input.value = "";
	               that.inputHidden.value = "";
	            }else{
		            // ul中的第一个li的航司中文名
		           var inputValue = that.ul.childNodes[0].childNodes[0].innerHTML;
		            // ul中的第一个li的航司二字码
		           var hiddenValue = that.ul.childNodes[0].childNodes[1].innerHTML;
                   that.input.value = inputValue;
                   that.inputHidden.value = hiddenValue;
	            }
            }
        });
        Jair._m.on(this.input, 'keyup', function (event) {

            //add by whx,2014.09.25
            //set the value of hidden input,to support this function :key in 3Code of one airport,search for results. 
            //that.inputHidden.value = that.input.value = that.input.value.toUpperCase();
            //add by whx,2014.09.25
            event = event || window.event;
            var keycode = event.keyCode;
            Jair._m.addClass('hide', that.airBox);
            that.createUl();

            /* 移除iframe 的hide 样式 */
            Jair._m.removeClass('hide', that.myIframe);

            // 下拉菜单显示的时候捕捉按键事件
            if (that.ul && !Jair._m.hasClass('hide', that.ul) && !that.isEmpty) {
                that.KeyboardEvent(event, keycode);
            }
        });
    },

    /* *
    * 生成下拉选择列表
    * @ createUl
    * */

    createUl: function () {
        //console.log('createUL');
        var str;
        var value = Jair._m.trim(this.input.value);
        // 当value不等于空的时候执行
        if (value != '') {
            var reg = new RegExp("^" + value + "|\\|" + value, 'gi');
            var searchResult = [];
            for (var i = 0, n = Jair.allAir.length; i < n; i++) {
                if (reg.test(Jair.allAir[i])) {
                    var match = Jair.regEx.exec(Jair.allAir[i]);
                    if (searchResult.length !== 0) {
                        str = '<li><b class="airname">' + match[1] + '</b><b class="airspell">' + match[4] + '</b><b class="airthreecode" style="display:none">|' + match[4] + '</b></li>';
                    } else {
                        str = '<li class="on"><b class="airname">' + match[1] + '</b><b class="airspell">' + match[4] + '</b><b class="airthreecode" style="display:none">|' + match[4] + '</b></li>';
                        inputValue = match[1];
                        hiddenValue = match[4];
                    }
                    searchResult.push(str);
                }
            }
            this.isEmpty = false;
            // 如果搜索数据为空
            if (searchResult.length == 0) {
                this.isEmpty = true;
                str = '<li class="empty">对不起，没有找到数据 "<em>' + value + '</em>"</li>';
                searchResult.push(str);
            }
            // 如果slideul不存在则添加ul
            if (!this.ul) {
                var ul = this.ul = document.createElement('ul');
                ul.className = 'airslide';
                this.rootDiv && this.rootDiv.appendChild(ul);
                // 记录按键次数，方向键
                this.count = 0;
            } else if (this.ul && Jair._m.hasClass('hide', this.ul)) {
                this.count = 0;
                Jair._m.removeClass('hide', this.ul);
            }
            this.ul.innerHTML = searchResult.join('');

            /* IE6 */
            this.changeIframe();

            // 绑定Li事件
            this.liEvent();
            
        } else {
            //clear the value of hidden input,by whx,2014-09-19
            this.inputHidden.value = '';
            //clear the value of hidden input,by whx,2014-09-19

            Jair._m.addClass('hide', this.ul);
            Jair._m.removeClass('hide', this.airBox);

            Jair._m.removeClass('hide', this.myIframe);

            this.changeIframe();
        }
    },

    /* IE6的改变遮罩SELECT 的 IFRAME尺寸大小 */
    changeIframe: function () {
        if (!this.isIE6) return;
        this.myIframe.style.width = this.rootDiv.offsetWidth + 'px';
        this.myIframe.style.height = this.rootDiv.offsetHeight + 'px';
    },

    /* *
    * 特定键盘事件，上、下、Enter键
    * @ KeyboardEvent
    * */

    KeyboardEvent: function (event, keycode) {
        var lis = Jair._m.$('li', this.ul);
        var len = lis.length;
        switch (keycode) {
            case 40: //向下箭头↓
                this.count++;
                if (this.count > len - 1) this.count = 0;
                for (var i = 0; i < len; i++) {
                    Jair._m.removeClass('on', lis[i]);
                }
                Jair._m.addClass('on', lis[this.count]);
                break;
            case 38: //向上箭头↑
                this.count--;
                if (this.count < 0) this.count = len - 1;
                for (i = 0; i < len; i++) {
                    Jair._m.removeClass('on', lis[i]);
                }
                Jair._m.addClass('on', lis[this.count]);
                break;
            case 13: // enter键
                this.input.value = Jair.regExChiese.exec(lis[this.count].innerHTML)[0];
                this.inputHidden.value = Jair.regExThreecode.exec(lis[this.count].innerHTML)[0].substring(1);
                Jair._m.addClass('hide', this.ul);
                Jair._m.addClass('hide', this.ul);
                /* IE6 */
                Jair._m.addClass('hide', this.myIframe);
                break;
            default:
                break;
        }
    },

    /* *
    * 下拉列表的li事件
    * @ liEvent
    * */

    liEvent: function () {
        var that = this;
        var lis = Jair._m.$('li', this.ul);
        for (var i = 0, n = lis.length; i < n; i++) {
        	var li = lis[i];
            Jair._m.on(lis[i], 'click', function (event) {
                //event.stopImmediatePropagation();
                event = Jair._m.getEvent(event);
                var target = Jair._m.getTarget(event);
                //add by whx,20140108
                var html = target.innerHTML;
               if(html.indexOf('</b>')<0 && html.indexOf("</B>") < 0)
                	target = target.parentNode;
                //add by whx,20140108
                that.input.value = Jair.regExChiese.exec(target.innerHTML)[0];
                that.inputHidden.value = Jair.regExThreecode.exec(target.innerHTML)[0].substring(1);
                Jair._m.addClass('hide', that.ul);
                /* IE6 下拉菜单点击事件 */
                Jair._m.addClass('hide', that.myIframe);
                
                Jair._m.stopPropagation(event);
            });
            Jair._m.on(lis[i], 'mouseover', function (event) {
                event = Jair._m.getEvent(event);
                var target = Jair._m.getTarget(event);
                Jair._m.addClass('on', target);
            });
            Jair._m.on(lis[i], 'mouseout', function (event) {
                event = Jair._m.getEvent(event);
                var target = Jair._m.getTarget(event);
                Jair._m.removeClass('on', target);
            })
        }
    }
};