//JavaScript代码区域
layui.use(['element', 'layer', 'form', 'table','jquery','upload'], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

    //第一个实例
    table.render({
        elem: '#tb'
        , height: 500
        , id:'classInfo'
        , url: '/admin/class/list' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', width: 100, sort: false, fixed: 'left'}
            , {field: 'classNo', title: '班级名', width: 140, sort: false}
            , {field: 'grade', title: '年级', width: 100, sort: false}
            , {field: 'studentNum', title: '学生人数', width: 100, sort: false}
            , {field: 'termStartTimeStr', title: '学期开始时间', width: 130, sort: false}
            , {field: 'termEndTimeStr', title: '学期结束时间', width: 130, sort: false}
            , {field: 'schoolId', title: '学校ID', width: 80, sort: false}
            , {field: 'schoolName', title: '学校名', width: 180, sort: false}
            // , {field: 'delFlag', title: '状态', width: 120, sort: false}
            , {
                field: '', title: '操作', width: 150, sort: false, templet: function (d) {
                    return '<div class="layui-btn-group"><button class="layui-btn layui-btn-sm" lay-event="edit">修改</button>' +
                        '<button class="layui-btn layui-btn-sm" lay-event="del">删除</button></div>'
                }
            }
        ]]
    });
    $("#addClass").click(function () {
        layer.open({
            type: 2,
            title:'增加班级信息',
            area: ['510px', '510px'],
            content: 'addClass',
            offset: 'auto',
            end: function () {
                location.href = '/admin/class';
            }
        });
    })

    $("#selectClass").click(function () {
        if($("#selectDiv").hasClass('hiden')){
            $("#selectDiv").removeClass('hiden');
            $("#selectDiv").addClass('show');
        }else{
            $("#selectDiv").removeClass('show');
            $("#selectDiv").addClass('hiden');
        }
    })

    $("#downloadFile").click(function () {
        document.location.href = '/admin/file/class/excel';
    })

    //执行实例
    upload.render({
        elem: '#uploadFile' //绑定元素
        ,url: '/admin/file/uploadClassInfo' //上传接口
        ,accept: 'file' //普通文件
        ,done: function(res){
            //上传完毕回调
            layer.msg(res.message)
            table.reload('classInfo', {
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

    //监听工具条
    table.on('tool(demo)', function(obj){
        console.log(obj)
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    url:'/admin/check/classChildNode',
                    type:'GET',
                    dataType:'json',
                    data:{classId:data.id},
                    success:function (resultData) {
                        if (resultData.result == 1){
                            obj.del();
                            deleteInfo(data.id);
                            layer.close(index);
                        }else{
                            layer.msg("请先解除该班级子节点信息");
                        }
                    }
                })
            });
        }  else if(obj.event === 'edit'){
            editClassStatus(data.id);
        }
    });

    function editClassStatus(id) {
        console.log(id)
        layer.open({
            type: 2,
            title:'修改班级信息',
            area: ['410px', '410px'],
            content: 'editClass'+'?id='+id,
            offset: 'auto',
            end: function () {
                location.href = '/admin/class';
            }
        });
    }

    //删除操作
    function deleteInfo(id){
        $.ajax({
            url:'/admin/class/delete',
            type:'GET',
            dataType:'json',
            data:{id:id},
            success:function (data) {
                console.log(data)
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
    });

    //初始化学校下拉框
    function initSchoolInfo(schoolArray){
        for(var i=0;i<schoolArray.length;i++){
            var schoolId = schoolArray[i].id;
            var schoolName = schoolArray[i].schoolName;
            $("#selectSchool").append('<option value='+schoolId+'>'+schoolName+'</option>');
        }
    }

    //清空年级列表
    function clearGradeInfo(){
        $("#selectGrade").children().remove();
        $("#selectGrade").append('<option value="">'+'年级'+'</option>');
    }

    // 二级联动
    $("#selectSchool").change(function () {
        clearGradeInfo();
        var schoolId = $("#selectSchool").val();
        console.log(schoolId)
        $.ajax({
            url:'/admin/class/getClassInfoBySchoolId',
            type:'POST',
            dataType:'json',
            data:{schoolId:schoolId},
            success:function (data) {
                console.log(data)
                if (data.result == 1){
                    classInfo = data;
                    initGradeandClass(data.data);
                }else{
                    layer.msg('班级列表查询失败');
                }
            }
        })
    })

    function initGradeandClass(data) {
        var temp = [];
        for (var i = 0; i < data.length; i++) {
            if (temp.indexOf(data[i].grade) == -1) {
                var grade = data[i].grade;
                temp.push(grade)
                $("#selectGrade").append('<option value=' + grade + '>' + grade + '年级' + '</option>');
            }
        }
    }

    //筛选查询
    form.on('submit(select)',function (data) {
        console.log(data.field);
        var schoolId = data.field.schoolId;
        var grade = data.field.grade;
        table.reload('classInfo', {
            where: { //设定异步数据接口的额外参数，任意设
                schoolId:schoolId,
                grade:grade
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
        return false;
    })
});