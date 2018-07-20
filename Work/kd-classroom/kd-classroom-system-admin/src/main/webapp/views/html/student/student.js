//JavaScript代码区域
layui.use(['element', 'layer', 'form', 'table'], function () {
    var table = layui.table;

    //第一个实例
    table.render({
        elem: '#tb'
        , height: 315
        , url: '/admin/student/list' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', width: 100, sort: false, fixed: 'left'}
            , {field: 'studentNo', title: '学号', width: 140, sort: false}
            , {field: 'studentName', title: '姓名', width: 100, sort: false}
            , {field: 'beginTimeStr', title: '入学时间', width: 130, sort: false}
            , {
                field: 'age', title: '年龄', width: 80, sort: false, templet: function (d) {
                    return emptyToStr(d.age);
                }
            }
            , {field: 'schoolName', title: '学校', width: 180, sort: false}
            , {field: 'className', title: '班级', width: 120, sort: false}
            , {
                field: 'parentPhone', title: '家长手机号', width: 120, sort: false, templet: function (d) {
                    return emptyToStr(d.parentPhone);
                }
            }
            , {
                field: '', title: '操作', width: 150, sort: false, templet: function (d) {
                    return '<button class="layui-btn layui-btn-sm" onclick="del(' + d.id + ')">禁用</button><button class="layui-btn layui-btn-sm" onclick="update(' + d.id + ')">修改</button>'
                }
            }
        ]]
    });

});