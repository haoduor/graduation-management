import {tools} from '/js/tool.js';
const tableModel = (() =>{
    return{
        //学生表行
        getStudentTableData(data){
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
                            <el-button @click="showEdit('${items['id']}')" type="text" icon="el-icon-edit">编辑</el-button>
                            <el-button @click="deleteList('${items['id']}')" type="text" icon="el-icon-delete">删除</el-button>
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
        },
        //教师表行
        getTeacherTableData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['teacherId']}</p>
                        <p class="nmDate">${items['name']}</p>
                        <p class="nmDate">${items['department']}</p>
                        <div class="staticDate">
                            <el-button @click="showEdit('${items['id']}')" type="text" icon="el-icon-edit">编辑</el-button>
                            <el-button @click="deleteList('${items['id']}')" type="text" icon="el-icon-delete">删除</el-button>
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
        },
        //选题表行
        getTopicTableData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataDifficulty = items['difficult'] == '0'?'容易':items['difficult'] == '1'?'普通':items['difficult'] == '2'?'困难':'?';//难度
                    let dataTime =tools.dateFormat(new Date(parseInt(items['createTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataTag = ``;//标签
                    items['tags'].forEach((items, index)=>{
                        dataTag += `
                            <div class="nmTag">${items['name']}</div>
                        `;
                    });

                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['title']}</p>
                        <p class="nmDate">${items['source']}</p>
                        <p class="nmDate">${items['content']}</p>
                        <p class="nmDate">${dataDifficulty}</p>
                        <p class="nmDate">${dataTime}</p>
                        <div class="nmDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;">${dataTag}</div>
                        <p class="nmDate">${items['teacherName']}</p>                        
                        <div class="staticDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;
                        width: 200px;">
                            <el-button @click="showEdit('${items['id']}',1)" type="text" icon="el-icon-edit">编辑选题</el-button>
                            <el-button @click="showEdit('${items['id']}',2)" type="text" icon="el-icon-edit">编辑标签</el-button>
                            <el-button @click="deleteList('${items['id']}')" type="text" icon="el-icon-delete">删除</el-button>
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
        },
        //公告单个模块
        getNoticeData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataTime =tools.dateFormat(new Date(parseInt(items['createTime'])),'yyyy-MM-dd');//时间
                    let dataColumn = `
                             <div class="noticeItems">
                                <p class="noticeHead">
                                     <span class="title">公告${index+1}</span>
                                     <span class="data">${dataTime}</span>
                                </p>
                                <p class="noticeMessage">
                                   ${items['content']}
                                </p>
                                <div class="noticeFooter">
                                     <el-button @click="showEdit('${items['id']}')" type="text" icon="el-icon-edit">编辑</el-button>
                                     <el-button @click="deleteList('${items['id']}')" type="text" icon="el-icon-delete">删除</el-button>
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
        },
        //模板文件表行
        getTemplateTableData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataTime =tools.dateFormat(new Date(parseInt(items['uploadTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['fileName']}</p>
                        <p class="nmDate">${dataTime}</p>
                        <div class="staticDate">
                            <el-button @click="deleteList('${items['sha256']}')" type="text" icon="el-icon-delete">删除</el-button>
                            <el-button @click="downLoadFile('${items['sha256']}')" type="text" icon="el-icon-download">下载</el-button>
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
        },
        //模板文件表行2
        getTemplateTableData2(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataTime =tools.dateFormat(new Date(parseInt(items['uploadTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['fileName']}</p>
                        <p class="nmDate">${dataTime}</p>
                        <div class="staticDate">
                            <el-button @click="downLoadFile('${items['sha256']}')" type="text" icon="el-icon-download">下载</el-button>
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
        },
        

        //最终选题学生表行
        getFinalStuData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataTime =tools.dateFormat(new Date(parseInt(items['chosenTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataDifficulty = items['difficult'] == '0'?'容易':items['difficult'] == '1'?'普通':items['difficult'] == '2'?'困难':'?';//难度
                    let score = items['score'] == '-1'?'未评分':items['score'];
                    let fileList = '';//文件列表
                    if(items['uploadFileList'] != ''){
                        items['uploadFileList'].forEach(items=>{
                            fileList += `
                                <div class="fileModel">
                                    <span class="fileName">${items['filename']}</span>
                                    <el-button type="text" @click="subjectFileDownLoad('${items['sha256']}')">下载</el-button>
                                    <el-button type="text" @click="deleteList({studentId:'${items['studentId']}',fileId:'${items['id']}'})">删除</el-button>
                                </div>                   
                            `;
                        });
                    }else{
                        fileList = '无';
                    }

                    let dataColumn = `
                    <div class="dateColumn" style='padding:20px 0px' >
                        <p class="nmDate">${items['studentName']}</p>
                        <div class="nmDate InnerBox">
                            <p class="nmInnerData"><span>指导教师：</span>${items['teacherName']}</p>
                            <p class="nmInnerData"><span>选题标题：</span>${items['title']}</p>
                            <p class="nmInnerData"><span>选题来源：</span>${items['source']}</p>
                            <p class="nmInnerData"><span>选题难度：</span>${dataDifficulty}</p>
                            <p class="nmInnerData"><span>选择时间：</span>${dataTime}</p>
                        </div>
                        <p class="nmDate">${items['content']}</p>
                        <div class="nmDate InnerBox">
                           ${fileList}
                        </div>
                        <p class="nmDate stressData">${score}</p>   
                         <div class="staticDate" style="width:10rem">
                            <el-button @click="showEdit({studentId:'${items['studentId'].toString()}',subjectId:'${items['id'].toString()}'})" type="text" icon="el-icon-edit">评分</el-button>
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
        },
        //时间管理表行
        getPeriodTableData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataTimeStart =tools.dateFormat(new Date(parseInt(items['startTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataTimeEnd =tools.dateFormat(new Date(parseInt(items['endTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['name']}</p>
                        <p class="nmDate">${dataTimeStart}</p>
                        <p class="nmDate">${dataTimeEnd}</p>                  
                        <div class="staticDate">
                            <el-button @click="showEdit('${items['id']}')" type="text" icon="el-icon-edit">编辑时间</el-button>
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
        },
        //登录页公告单个模块
        getLoginNoticeData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataColumn = `                           
                                <el-collapse-item title="公告${index+1}" name="${index+1}">
                                    <div>${items['content']}</div>
                                </el-collapse-item>                                                          
                    `;
                    html += dataColumn;
                });
                if(html != null){
                    let lastHtml = ` <el-collapse v-model="topicItems">${html}</el-collapse>`;
                    resolve(lastHtml);
                }else{
                    resolve('空页面');
                }
            });
        },
        //学生端
        //选题表行
        getStuTopicTableData(data,choseId){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataDifficulty = items['difficult'] == '0'?'容易':items['difficult'] == '1'?'普通':items['difficult'] == '2'?'困难':'?';//难度
                    let dataTime =tools.dateFormat(new Date(parseInt(items['createTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataTag = ``;//标签
                    items['tags'].forEach((items, index)=>{
                        dataTag += `
                            <div class="nmTag">${items['name']}</div>
                        `;
                    });
                    //是否选中
                    let choseBtn = `<el-button @click="showEdit('${items['id']}')" type="text" icon="el-icon-s-order">选择课题</el-button>`;
                    choseId.forEach(id=>{

                        if(items['id'] == id){
                            choseBtn = `<el-button @click="showEdit('${items['id']}')" type="text" disabled icon="el-icon-s-order">选择课题</el-button>`;
                            return;
                        }
                    });
                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['title']}</p>
                        <p class="nmDate">${items['source']}</p>
                        <p class="nmDate">${items['content']}</p>
                        <p class="nmDate">${dataDifficulty}</p>
                        <p class="nmDate">${dataTime}</p>
                        <div class="nmDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;">${dataTag}</div>
                        <p class="nmDate">${items['teacherName']}</p>                        
                        <div class="staticDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;
                        width: 200px;">
                                ${choseBtn}                    
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
        },
        //我的选题表行
        getMyTopicTableData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataDifficulty = items['difficult'] == '0'?'容易':items['difficult'] == '1'?'普通':items['difficult'] == '2'?'困难':'?';//难度
                    let dataTag = ``;//标签
                    items['tags'].forEach((items, index)=>{
                        dataTag += `
                            <div class="nmTag">${items['name']}</div>
                        `;
                    });

                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['title']}</p>
                        <p class="nmDate">${items['source']}</p>
                        <p class="nmDate">${items['content']}</p>
                        <p class="nmDate">${dataDifficulty}</p>
                        <div class="nmDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;">${dataTag}</div>
                        <p class="nmDate">${items['teacherName']}</p>                     
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
        },
        //上传文件列表
        getStuUploadFileData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataColumn = `                           
                       <div class="fileItems">
                            <span class="fileName">${items['filename']}</span>
                            <el-button type="text" style="font-size: 1.1rem" @click="subjectFileDownLoad('${items['sha256']}')">下载</el-button>
                            <el-button type="text" @click="deleteList({studentId:'${items['studentId']}',fileId:'${items['id']}'})" >删除</el-button>
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
        },


        //教师
        //教师的选题表行
        getTeaTopicTableData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataDifficulty = items['difficult'] == '0'?'容易':items['difficult'] == '1'?'普通':items['difficult'] == '2'?'困难':'?';//难度
                    let dataTime =tools.dateFormat(new Date(parseInt(items['createTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataTag = ``;//标签
                    items['tags'].forEach((items, index)=>{
                        dataTag += `
                            <div class="nmTag">${items['name']}</div>
                        `;
                    });
                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['title']}</p>
                        <p class="nmDate">${items['source']}</p>
                        <p class="nmDate">${items['content']}</p>
                        <p class="nmDate">${dataDifficulty}</p>
                        <p class="nmDate">${dataTime}</p>
                        <div class="nmDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;">${dataTag}</div>                  
                        <div class="staticDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;
                        width: 200px;">
                            <el-button @click="showEdit('${items['id']}',1)" type="text" icon="el-icon-edit">编辑选题</el-button>
                            <el-button @click="showEdit('${items['id']}',2)" type="text" icon="el-icon-edit">编辑标签</el-button>
                            <el-button @click="deleteList('${items['id']}')" type="text" icon="el-icon-delete">删除</el-button>
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
        },
        //教师的学生选题审核
        getStuAndSubject(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {
                    let dataDifficulty = items['difficult'] == '0'?'容易':items['difficult'] == '1'?'普通':items['difficult'] == '2'?'困难':'?';//难度
                    let dataTime =tools.dateFormat(new Date(parseInt(items['createTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataTag = ``;//标签
                    items['tags'].forEach((items, index)=>{
                        dataTag += `
                            <div class="nmTag">${items['name']}</div>
                        `;
                    });
                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['title']}</p>
                        <p class="nmDate">${items['source']}</p>
                        <p class="nmDate">${items['content']}</p>
                        <p class="nmDate">${dataDifficulty}</p>
                        <div class="nmDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;">${dataTag}</div>      
                         <p class="nmDate">${items['studentName']}</p>            
                        <div class="staticDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;
                        width: 200px;">
                            <el-button @click="showEdit({sbjId:'${items['id']}',stuId:'${items['studentId']}'})" type="text" icon="el-icon-edit">通过审核</el-button>                        
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
        },
        //最终选题学生表行(教师端)
        getTeaFinalStuData(data){
            let html = '';
            return new Promise((resolve,reject)=> {
                data.forEach((items, index) => {

                    let dataTime =tools.dateFormat(new Date(parseInt(items['chosenTime'])),'yyyy-MM-dd hh:mm:ss');//时间
                    let dataDifficulty = items['difficult'] == '0'?'容易':items['difficult'] == '1'?'普通':items['difficult'] == '2'?'困难':'?';//难度
                    let score = items['score'] == '-1'?'未评分':items['score'];
                    let fileList = '';//文件列表
                    if(items['uploadFileList'] != ''){
                        items['uploadFileList'].forEach(items=>{
                            fileList += `
                                <div class="fileModel">
                                    <span class="fileName">${items['filename']}</span>
                                    <el-button type="text" @click="subjectFileDownLoad('${items['sha256']}')">下载</el-button>                                    
                                </div>                   
                            `;
                        });
                    }else{
                        fileList = '无';
                    }

                    let dataColumn = `
                    <div class="dateColumn" style='padding:20px 0px' >
                        <p class="nmDate">${items['studentName']}</p>
                        <div class="nmDate InnerBox">
                            <p class="nmInnerData"><span>选题标题：</span>${items['title']}</p>
                            <p class="nmInnerData"><span>选题来源：</span>${items['source']}</p>
                            <p class="nmInnerData"><span>选题难度：</span>${dataDifficulty}</p>
                            <p class="nmInnerData"><span>选择时间：</span>${dataTime}</p>
                        </div>
                        <p class="nmDate">${items['content']}</p>
                        <div class="nmDate InnerBox">
                           ${fileList}
                        </div>
                        <p class="nmDate stressData">${score}</p>   
                         <div class="staticDate" style="width:10rem">
                            <el-button @click="showEdit({studentId:'${items['studentId'].toString()}',subjectId:'${items['id'].toString()}'})" type="text" icon="el-icon-edit">评分</el-button>
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
        },
    }
})();

export { tableModel };