const staticValue = (()=>{
    let choseId = '1';//选中的id
    let currentPageName = '1'; //当前选中页面,默认1
    let chosePageNumber = '1';//当前选中的页数,默认1
    let that = window; //入口this
    let mainContentThis = window;  //主页this
    let listToolThis = window; //工具栏this
    let editMainThis = window; //编辑添加删除页this
    let topicSelectTea = '';//选题页搜索的教师

    return{
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
        //设置当前选中的id
        setChoseId(id = '1') {
            choseId = id;
        },
        getChoseId() {
            if (choseId == undefined) {
                return 1;
            }
            return choseId;
        },

        //设置this
        setThat(_this = '1') {
            that = _this;
        },
        getThat() {
            if (that == undefined) {
                return 1;
            }
            return that;
        },
        //主页this
        setMainContentThis(_this = '1') {
            mainContentThis = _this;
        },
        getMainContentThis() {
            if (mainContentThis == undefined) {
                return 1;
            }
            return mainContentThis;
        },

        //工具栏this
        setListToolThis(_this = '1') {
            listToolThis = _this;
        },
        getListToolThis() {
            if (listToolThis == undefined) {
                return 1;
            }
            return listToolThis;
        },

        //编辑页this
        setEditMainThis(_this = '1') {
            editMainThis = _this;
        },
        getEditMainThis() {
            if (editMainThis == undefined) {
                return 1;
            }
            return editMainThis;
        },

        //选题页搜索的教师
        setTopicSelectTea(_this = '1'){
            topicSelectTea = _this;
        },
        getTopicSelectTea(){
            return topicSelectTea;
        }
    }
})();

export { staticValue };