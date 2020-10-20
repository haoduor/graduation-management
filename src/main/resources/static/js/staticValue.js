const staticValue = (()=>{
    let choseId = '1';//选中的id
    let currentPageName = '1'; //当前选中页面,默认1
    let chosePageNumber = '1';//当前选中的页数,默认1
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

    }
})();

export { staticValue };