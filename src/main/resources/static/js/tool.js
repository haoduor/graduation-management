const tools = (() => {
    let xmlhttp;
    let currentPageName = '1'; //当前选中页面,默认1
    let chosePageNumber = '1';//当前选中的页数,默认1
    let defaultPage = {};//默认显示页
    return {
        $(id) {
            return document.querySelector(id);
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
        //设置当前选中的页面
        setCurrentPage(pageName = 1) {
            currentPageName = pageName;
        },
        getCurrentPage() {
            if (currentPageName == undefined) {
                return 1;
            }
            return currentPageName;
        },
        //设置当前选中的页数
        setPageNumber(pageNumber = '1') {
            chosePageNumber = pageNumber;
        },
        getPageNumber() {
            if (chosePageNumber == undefined) {
                return 1;
            }
            return chosePageNumber;
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
        getStudentTableDate(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['studentId']}</p>
                        <p class="nmDate">${items['classname']}</p>
                        <p class="nmDate">${items['department']}</p>
                        <p class="nmDate">${items['name']}</p>
                        <div class="staticDate">
                            <el-button @click="showEdit(${items['id']})" type="text" icon="el-icon-edit">编辑</el-button>
                            <el-button @click="deleteList(${items['id']})" type="text" icon="el-icon-delete">删除</el-button>
                        </div>
                     </div>
                    `;
                    html += dataColumn;
                });
                if(html != null){
                    resolve(html);
                }else{
                    resolve('空页面');
                }
            });
        }
    }
})();

export { tools };