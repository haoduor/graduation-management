const tools = (() => {
    let xmlhttp;
    let defaultPage = {};//默认显示页

    return {
        $(id) {
            return document.querySelector(id);
        },
        jump(uri){
            window.location.href =uri;
        },
        loadData(url, cfunc) {
            if (window.XMLHttpRequest) {
                // code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = cfunc;
            xmlhttp.open("GET", url, true);
            xmlhttp.send();
        },
        //读取文件
        readFile(url) {
            return new Promise((resolve, reject) => {
                this.loadData(url, function () {
                    // console.log(xmlhttp);      
                    if (xmlhttp.responseText != '') {
                        resolve(xmlhttp.responseText);
                    }
                });
            });
        },
        //设置页面
        setPage(id, url, data = {}) {
            return new Promise((resolve, reject) => {
                this.readFile(url).then(pageDoc => {
                    let defaultPageCopy = JSON.parse(JSON.stringify(defaultPage));//拷贝默认配置
                    if (JSON.stringify(data) != '{}') {   
                        //现有配置替换默认配置                    
                        for (const key in data) {
                            if (data.hasOwnProperty(key)) {
                                const element = data[key];
                                defaultPageCopy[key] = element;
                            }
                        }
                        //页面适配
                        for (const key in defaultPageCopy) {
                            if (defaultPageCopy.hasOwnProperty(key)) {
                                const element = defaultPageCopy[key];
                                if (typeof element == 'string') {
                                    pageDoc = `${pageDoc}`.replace(`\$${key}\$`, element);
                                }
                                if (element instanceof Array) {
                                    element.forEach((items, index) => {
                                        pageDoc = `${pageDoc}`.replace(`\$${key}[${index}]\$`, items);
                                    });
                                }
                            }
                        }
                    }
                    this.$(`#${id}`).innerHTML = pageDoc;
                    resolve(true);
                });
            });
        },
        //设置默认页面配置
        setDefaultPage(data) {
            if (data instanceof Object) {
                defaultPage = data;
                return;
            }
            defaultPage = {};
        },
        //编码
        buildFormData(object) {
            let formData = new FormData();
            for (let key in object) {
                formData.append(key, object[key]);
            }
            return formData;
        },
        //定时器改
        setTimeOutP(time, cb) {
            return new Promise((resolve, reject) => {
                cb();
                setTimeout(() => {
                    resolve(true);
                }, time);
            });
        },
        //格式化日期
        dateFormat(thisDate, dataType) {
            let o = {
                "M+": thisDate.getMonth() + 1,
                "d+": thisDate.getDate(),
                "h+": thisDate.getHours(),
                "m+": thisDate.getMinutes(),
                "s+": thisDate.getSeconds(),
                "q+": Math.floor((thisDate.getMonth() + 3) / 3),
                "S": thisDate.getMilliseconds()
            };
            if (/(y+)/.test(dataType))
                dataType = dataType.replace(RegExp.$1, (thisDate.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (let k in o)
                if (new RegExp("(" + k + ")").test(dataType))
                    dataType = dataType.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return dataType;
        },
        //设置cookie
        setCookie(name, value, days) {
                let d = new Date();
                d.setTime(d.getTime() + (days*24*60*60*1000));
                let expires = "expires="+d.toUTCString();
                document.cookie = name + "=" + value + "; " + expires+"; path=/ ;domain=127.0.0.1";
        },
        //获取cookie
         getCookie(cname) {
            let name = cname + "=";
            let ca = document.cookie.split(';');
            for(let i=0; i<ca.length; i++) {
                let c = ca[i];
                while (c.charAt(0)==' ') c = c.substring(1);
                if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
            }
            return "";
        },
        //清除cookie
        clearCookie(name) {
            this.setCookie(name, "", -1);
        },
        //axios获取
        getAxiosData(url){
            return new Promise( (resolve,reject)=>{
                axios.get(url).then(val=>{
                    resolve(val);
                }).catch(err=>{
                    console.log("获取失败"+err);
                    resolve(false);
                });
            });
        },
        //判空校验
        inputIsNull(inputId,type){
            this.$(`${inputId}`).addEventListener(type,function () {
                if(tools.$(`${inputId}`).value == ''){
                    tools.$(`${inputId}Rule`).style.opacity = '1';
                    tools.$(`${inputId}`).style.border = '1px solid #F56C6C';
                }else{
                    tools.$(`${inputId}Rule`).style.opacity = '0';
                    tools.$(`${inputId}`).style.border = '1px solid #DCDFE6';
                }
            });
        },
        inputIsNullValue(inputId){
            if(this.$(`${inputId}`).value == ''){
                return false;
            }else{
                return true;
            }
        }
    }
})();

export { tools };