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
                    let dataDifficulty = items['difficulty'] == 1?'容易':items['difficulty'] == 2?'普通':items['difficulty'] == 3?'困难':'?';
                    let dataTime = new Date(parseInt(items['createTime']));
                    let dataTag = ``;
                    items['tags'].forEach((items, index)=>{
                        dataTag += `
                            <div class="nmTag">${items}</div>
                        `;
                    });

                    let dataColumn = `
                    <div class="dateColumn">
                        <p class="nmDate">${items['title']}</p>
                        <p class="nmDate">${items['source']}</p>
                        <p class="nmDate">${items['content']}</p>
                        <p class="nmDate">${dataDifficulty}</p>
                        <p class="nmDate">2008-12-21</p>
                        <div class="nmDate" 
                        style="display: flex;
                        flex-flow: column nowrap;">${dataTag}</div>
                        <p class="nmDate">${items['teacherId']}</p>
                        <p class="nmDate">${items['teacherName']}</p>                        
                        <div class="staticDate" 
                        style="display: flex;
                        flex-flow: row wrap;
                        justify-content: center;
                        width: 200px;">
                            <el-button @click="showEdit('${items['id']}',1)" type="text" icon="el-icon-edit">编辑选题</el-button>
                            <el-button @click="showEdit('${items['id']}',2)" type="text" icon="el-icon-edit">编辑标签</el-button>
                            <el-button @click="showEdit('${items['id']}',3)" type="text" icon="el-icon-edit">编辑指导老师</el-button>
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
        }
    }
})();

export { tableModel };