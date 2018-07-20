//JavaScript代码区域
layui.use(['element', 'layer', 'form', 'table','jquery'], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    //第一个实例
    table.render({
        elem: '#tbs'
        , height: 500
        , width : 500
        , id:'subjectInfo'
        , url: '/admin/subject/list' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', width: 100, sort: false, fixed: 'left'}
            , {field: 'subjectName', title: '学科名', width: 150, sort: false}
            , {
                field: '', title: '操作', width: 300, sort: false, templet: function (d) {
                    return '<div class="layui-btn-group"><button class="layui-btn layui-btn-sm" lay-event="edit">修改</button>' +
                        '<button class="layui-btn layui-btn-sm" lay-event="del">删除</button></div>'
                }
            }
        ]]
    });

    var $ = layui.jquery;

    $("#addSubject").click(function () {
        layer.open({
            type: 2,
            title:'增加学科信息',
            area: ['410px', '410px'],
            content: 'addSubject',
            offset: 'auto',
            end: function () {
                location.href = '/admin/subject';
            }
        });
    })

    $("#selectSubject").click(function () {
        console.log($("#selectDiv").hasClass('hiden'))
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
                    url:'/admin/check/subjectAndTeacher',
                    type:'GET',
                    dataType:'json',
                    data:{subjectId:data.id},
                    success:function (resultData) {
                        if (resultData.result === 1){
                            obj.del();
                            deleteInfo(data.id);
                            layer.close(index);
                        }else{
                            layer.msg("请先解除该学科子节点信息");
                        }
                    }
                })
            });
        } else if(obj.event === 'edit'){
            editSubject(data.id);
        }
    });

    function editSubject(id) {
        console.log(id)
        layer.open({
            type: 2,
            title:'修改学科信息',
            area: ['410px', '410px'],
            content: 'editSubject'+'?id='+id,
            offset: 'auto',
            end: function () {
                location.href = '/admin/subject';
            }
        });
    }

    //删除操作
    function deleteInfo(id){
        $.ajax({
            url:'/admin/subject/delete',
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

    //筛选查询
    form.on('submit(select)',function (data) {
        console.log(data.field);
        var subjectName = data.field.subjectName;
        table.reload('subjectInfo', {
            where: { //设定异步数据接口的额外参数，任意设
                subjectName:subjectName
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
        return false;
    })
});