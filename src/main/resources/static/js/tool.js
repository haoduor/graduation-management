const tools = (() => {
    let xmlhttp;
    return {
        //简化绑定
        $(id){
            return document.querySelector(id);
        },
        //简化链接跳转
        jump(href){
            window.location.href = href;
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
        setPage(id, url) {
            return new Promise((resolve, reject) => {
                this.readFile(url).then((pageDoc) => {
                    document.getElementById(id).innerHTML = pageDoc;
                    resolve(true);
                });
            });
        },
        //编码
        buildFormData(object){
            let formData = new FormData();
            for(let key in object){
                formData.append(key,object[key]);
            }
            return formData;
        },
        //定时器改
        setTimeOutP(time,cb){
            return new Promise((resolve,reject)=>{
                cb();
                setTimeout(()=>{
                    resolve(true);
                },time);
            });
        }
    }
})();

export { tools };