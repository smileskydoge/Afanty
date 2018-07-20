//JavaScript代码区域
layui.use(['element', 'layer', 'form', 'table','jquery','form'], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    //第一个实例
    table.render({
        elem: '#tb'
        , height: 500
        , id:'schoolInfo'
        , url: '/admin/school/list' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', width: 100, sort: false, fixed: 'left'}
            , {field: 'schoolName', title: '学校名', width: 140, sort: false}
            , {field: 'area', title: '集团', width: 100, sort: false}
            , {field: 'educationLengthInfo', title: '学籍制', width: 130, sort: false}
            , {
                field: '', title: '操作', width: 150, sort: false, templet: function (d) {
                    return '<div class="layui-btn-group"><button class="layui-btn layui-btn-sm" lay-event="edit">修改</button>' +
                        '<button class="layui-btn layui-btn-sm" lay-event="del">删除</button></div>'
                }
            }
        ]]
    });

    $("#addSchool").click(function () {
        layer.open({
            type: 2,
            title:'增加学校信息',
            area: ['500px', '500px'],
            content: 'addSchool',
            offset: 'auto',
            end: function () {
                location.href = '/admin/school';
            }
        });
    })

    $("#selectSchool").click(function () {
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
        console.log(obj)
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    url:'/admin/check/schoolChild',
                    type:'GET',
                    dataType:'json',
                    data:{schoolId:data.id},
                    success:function (resultData) {
                        if (resultData.result === 1){
                            obj.del();
                            deleteInfo(data.id);
                            layer.close(index);
                        }else{
                            layer.msg("请先解除该学校子节点信息");
                        }
                    }
                })
            });
        }  else if(obj.event === 'edit'){
            editSchool(data.id);
        }
    });

    function editSchool(id) {
        layer.open({
            type: 2,
            title:'修改学校信息',
            area: ['500px', '500px'],
            content: 'editSchool'+'?id='+id,
            end: function () {
                location.href = '/admin/school';
            }
        });
    }

    function checkSchoolInfo(id) {
        $.ajax({
            url:'/admin/check/schoolChild',
            type:'GET',
            dataType:'json',
            data:{schoolId:id},
            success:function (data) {
                if (data.result == 1){
                   return true;
                }else{
                    return false;
                }
            }
        })
    }

    //删除操作
    function deleteInfo(id){
        $.ajax({
            url:'/admin/school/delete',
            type:'GET',
            dataType:'json',
            data:{id:id},
            success:function (data) {
                if (data.result == 1){
                    layer.msg('删除成功');
                    location.href = '/admin/school';
                }else{
                    layer.msg('删除失败');
                }
            }
        })
    }

    //筛选查询
    form.on('submit(select)',function (data) {
        console.log(data.field);
        var schoolName = data.field.schoolName;
        var area = data.field.area;
        table.reload('schoolInfo', {
            where: { //设定异步数据接口的额外参数，任意设
                schoolName:schoolName,
                area:area
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
        return false;
    })

});