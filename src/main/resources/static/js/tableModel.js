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
    }
})();

export { tableModel };