//JavaScript代码区域
layui.use(['element', 'layer', 'form', 'table','jquery','upload'], function () {

    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var classInfo = {};

    //第一个实例
    table.render({
        elem: '#tb'
        , id : 'teacherInfo'
        , height: 500
        , url: '/admin/teacher/list' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', width:50, sort: false, fixed: 'left'}
            , {field: 'teacherName', title: '姓名', width: 140, sort: false}
            , {field: 'mobileNo', title: '教师手机', width: 150, sort: false}
            , {field: 'age', title: '年龄', width:80, sort: false}
            , {field: 'subjectName', title: '学科', width: 80, sort: false}
            , {field: 'jobTitleName', title: '班主任', width: 100, sort: false}
            , {field: 'schoolName', title: '学校', width: 200, sort: false}
            , {field: 'classNames', title: '班级', width: 150, sort: false}
            , {
                field: '', title: '操作', width: 170, sort: false, templet: function (d) {
                    var status = "";
                    if(d.delFlag){
                        status = "启用";
                    }else{
                        status = "禁用";
                    }
                    return '<div class="layui-btn-group"><button class="layui-btn layui-btn-sm" lay-event="detail">'+status+'</button><button class="layui-btn layui-btn-sm" lay-event="edit">修改</button>' +
                        '<button class="layui-btn layui-btn-sm" lay-event="del">删除</button></div>'
                }
            }
        ]]
    });

    $("#downloadFile").click(function () {
        document.location.href = '/admin/file/teacher/excel';
    })

    //执行实例
    upload.render({
        elem: '#uploadFile' //绑定元素
        ,url: '/admin/file/uploadTeacherInfo' //上传接口
        ,accept: 'file' //普通文件
        ,done: function(res){
            //上传完毕回调
            layer.msg(res.message)
            table.reload('teacherInfo', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
        ,error: function(){
            //请求异常回调
            layer.msg("error")
        }
    });

    $("#addTeacher").click(function () {
        layer.open({
            type: 2,
            title:'增加教师信息',
            area: ['510px', '530px'],
            content: 'addTeacher',
            offset: 'auto',
            end: function () {
                location.href = '/admin/teacher';
            }
        });
    })

    $("#selectTeacher").click(function () {
        if($("#selectDiv").hasClass('hiden')){
            $("#selectDiv").removeClass('hiden');
            $("#selectDiv").addClass('show');
        }else{
            $("#selectDiv").removeClass('show');
            $("#selectDiv").addClass('hiden');
        }
    })

    //监听工具条
    table.on('tool(demo)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                deleteInfo(data.id);
                layer.close(index);
            });
        }else if(obj.event === 'edit'){
            editTeacherStatus(data.id);
        }else if(obj.event === 'detail'){
            layer.confirm('确定改变教师状态？', function(index){
                changeTeacherStatus(data);
            });
        }
    });

    //改变启用禁用状态
    function changeTeacherStatus(data){
        var statusObj = {};
        statusObj.id = data.id;
        statusObj.delFlag = !data.delFlag;
        $.ajax({
            url:'/admin/teacher/changeStatus',
            data:statusObj,
            dataType:'json',
            type:'post',
            success:function (data) {
                if (data.result == 1){
                    layer.msg('成功');
                    location.href = '/admin/teacher';
                    layer.close(index);
                }else{
                    layer.msg('错误');
                }
            }
        })
    }

    function editTeacherStatus(id) {
        layer.open({
            type: 2,
            area: ['680px', '580px'],
            content: 'editTeacher'+'?id='+id,
            end: function () {
                location.href = '/admin/teacher';
            }
        });
    }

    //删除操作
    function deleteInfo(id){
        $.ajax({
            url:'/admin/teacher/delete',
            type:'GET',
            dataType:'json',
            data:{id:id},
            success:function (data) {
                if (data.result == 1){
                    layer.msg('删除成功');
                }else{
                    layer.msg('删除失败');
                }
            }
        })
    }


    /*---筛选模块-----*/
    $(document).ready(function () {

        //请求获取学校信息
        $.ajax({
            url:'/admin/school/getAllInfo',
            dataType:'json',
            type:'post',
            success:function (data) {
                if (data.result == 1){
                    initSchoolInfo(data.data)
                }else{
                    layer.msg('学校列表查询失败');
                }
            }
        })

        //请求获取学科信息
        $.ajax({
            url: '/admin/subject/allSubjectInfo',
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if (data.result == 1) {
                    initSubjectInfo(data.data)
                } else {
                    layer.msg('学科列表查询失败');
                }
            }
        })

        initJobInfo(); 
    });
    
    //初始化职业下拉
    function initJobInfo() {
        var JobTitle = [{val:1,name:'班主任'},{val:0,name:'普通教师'}];
        for (var i = 0; i < JobTitle.length; i++) {
            var val = JobTitle[i].val;
            var name = JobTitle[i].name;
            $("#selectJob").append('<option value=' + val + '>' + name + '</option>');
        }
    }

    //初始化学科下拉框
    function initSubjectInfo(subjectArray) {
        for (var i = 0; i < subjectArray.length; i++) {
            var subjectId = subjectArray[i].id;
            var subjectName = subjectArray[i].subjectName;
            $("#selectSubject").append('<option value=' + subjectId + '>' + subjectName + '</option>');
        }
    }

    //初始化学校下拉框
    function initSchoolInfo(schoolArray){
        for(var i=0;i<schoolArray.length;i++){
            var schoolId = schoolArray[i].id;
            var schoolName = schoolArray[i].schoolName;
            $("#selectSchool").append('<option value='+schoolId+'>'+schoolName+'</option>');
        }
    }

    // 二级联动
    $("#selectSchool").change(function () {
        classInfo = {};
        clearGradeAndClassInfo();
        var schoolId = $("#selectSchool").val();
        $.ajax({
            url:'/admin/class/getClassInfoBySchoolId',
            type:'POST',
            dataType:'json',
            data:{schoolId:schoolId},
            success:function (data) {
                if (data.result == 1){
                    classInfo = data;
                    initGradeandClass(data.data);
                }else{
                    layer.msg('班级列表查询失败');
                }
            }
        })
    })

    //清空班级列表
    function clearGradeAndClassInfo(){
        $("#selectGrade").children().remove();
        $("#selectGrade").append('<option value="">'+'年级'+'</option>');
        $("#selectClass").children().remove();
        $("#selectClass").append('<option value="">'+'班级'+'</option>');
    }

    function initGradeandClass(data) {
        var temp = [];
        for (var i = 0; i < data.length; i++) {
            if (temp.indexOf(data[i].grade) == -1) {
                var grade = data[i].grade;
                temp.push(grade);
                $("#selectGrade").append('<option value=' + grade + '>' + grade + '年级' + '</option>');
            }
        }
    }

    // 三级联动
    $("#selectGrade").change(function () {
        clearClassSelect();
        var clas = classInfo.data;
        var gradeInfo = $("#selectGrade").val();
        for (var i=0;i<clas.length;i++){
            var selGrade = clas[i].grade;
            var classNo = clas[i].classNo;
            if(selGrade==gradeInfo){
                $("#selectClass").append('<option value='+classNo+'>'+clas[i].classNo+'班'+'</option>');
            }
        }
    })

    function clearClassSelect() {
        $("#selectClass").children().remove();
        $("#selectClass").append('<option value="">'+'班级'+'</option>');
    }

    function getClassId(gradnSel, classNoSel) {
        var classInfos = classInfo.data;
        for (var i = 0; i < classInfos.length; i++) {
            var gradeSrc = classInfos[i].grade;
            var classNoSrc = classInfos[i].classNo;
            var classId = classInfos[i].id;
            if (gradnSel == gradeSrc && classNoSel == classNoSrc) {
                return classId
            } else {
                continue;
            }
        }
    }

    function getClassIdsByGrade(gradnSel){
        var classInfos = classInfo.data;
        var classIds = [];
        for (var i = 0; i < classInfos.length; i++) {
            var gradeSrc = classInfos[i].grade;
            var classId = classInfos[i].id;
            if (gradnSel === gradeSrc) {
                classIds.push(classId)
            }
        }
        return classIds.join(",")
    }

    //筛选查询
    form.on('submit(select)',function (data) {
        var teacherName = data.field.teacherName;
        var mobileNo = data.field.mobileNo;
        var jobTitle = data.field.jobTitle;
        var subjectId = data.field.subjectId;

        var schoolId = data.field.schoolId;
        var grade = data.field.grade;
        var classNo = data.field.classNo;

        var classIds = '';


        if (grade != '' && classNo != '') {
            classIds = getClassId(grade, classNo);
        } else if(grade != '' && classNo == ''){
            classIds = getClassIdsByGrade(parseInt(grade));
        }else{
            classIds = '';
        }

        table.reload('teacherInfo', {
            where: { //设定异步数据接口的额外参数，任意设
                teacherName:teacherName,
                mobileNo:mobileNo,
                jobTitle:jobTitle,
                schoolId:schoolId,
                classIdstr:classIds,
                subjectId:subjectId
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
        return false;
    })


});
