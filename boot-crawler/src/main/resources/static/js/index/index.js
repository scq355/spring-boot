var totalRecord;
var currentPage;



/*重置表单*/
function reset_form(elem) {
    $(elem)[0].reset();
    $(elem).find("*").removeClass("has-error has-success");
    $(elem).find(".help-block").text("");
}


/*校验*/
function validate_add_form() {
    /*拿到数据，正则校验*/
    var webName = $("#name_add_input").val();
    var webUrl = $("#url_add_input").val();
    var params = $("#params_add_input").val();
    return true;
}

/*显示校验提示信息*/
function show_validate_info(elem, status, msg) {
    $(elem).parent().removeClass("has-success has-error"); /*清除之前的校验状态*/
    $(elem).next("span").text("");
    if (status == "success") {
        $(elem).parent().addClass("has-success");
        $(elem).next("span").text(msg)
    } else if (status == "error"){
        $(elem).parent().addClass("has-error");
        $(elem).next("span").text(msg);
    }
}

/*校验网站地址是否可用*/
$("#url_add_input").change(function () {
    var webUrl = this.value; /*发送ajax请求校验用户名是否可用*/
    $.ajax({
        url:"/webInfo/isUsable",
        data:{"webUrl":webUrl},
        type:"POST",
        success:function (info) {
            if (info.data == null) {
                show_validate_info("#url_add_input", "success", "用户名可用");
                $("#web_save_btn").attr("ajax_va", "success");
            } else {
                show_validate_info("#url_add_input", "error", info.context.va_msg);
                $("#web_save_btn").attr("ajax_va", "error");
            }
        }
    })

});

/*点击保存*/
$("#web_save_btn").click(function () {
    /*填写的表单数据提交给后端*/
    if (!validate_add_form()) { /*合法性校验*/
        return false;
    }
    if ($(this).attr("ajax_va") == "error") { /*判断用户名存在性校验*/
        return false;
    }
    $.ajax({
        url:"/webInfo/save",
        type:"POST",
        data:$("#empAddModal form").serialize(),
        success:function (info) {
            /*alert(info.msg);*/

            if (info.code == 1) {
                $("#empAddModal").modal('hide');
                to_page(totalRecord); /*来到最后一页显示刚才保存的数据，发送ajax请求显示最后一页数据*/
            } else {
            }
        }
    })
});

/*点击编辑按钮显示员工信息*/
$(document).on("click", ".edit_btn", function () {
    /*alert("edit");*/
    /*查出部门信息显示部门列表*/
    getDepts("#empUpdateModal select");
    /*查出员工信息，显示详细信息*/
    getEmp($(this).attr("edit-id"));
    /*把员工id传递给模态框的更新按钮*/
    $("#emp_update_btn").attr("edit-id", $(this).attr("edit-id"));
    $("#empUpdateModal").modal({
        backdrop:"static"
    });
})

/*获取员工信息*/
function getEmp(id) {
    $.ajax({
        url:"/emp/" + id,
        type:"GET",
        success:function (info) {
            console.log(info);
            var empData = info.context.emp;
            $("#empName_update_static").text(empData.empName);
            $("#email_update_input").val(empData.email);
            $("#empUpdateModal input[name=gender]").val([empData.gender]);
            $("#empUpdateModal select").val([empData.dId]);
        }
    });
}

/*点击更新*/
$("#emp_update_btn").click(function () {
    /*验证邮箱合法性*/
    var email = $("#email_update_input").val();
    var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    if (!regEmail.test(email)) {
        //alert("邮箱格式不正确");
        show_validate_info("#email_update_input", "error", "邮箱格式不正确");
        return false
    } else {
        show_validate_info("#email_update_input", "success", "");
    }
    /*保存更新数据*/
    $.ajax({
        url:"/emp/"+$(this).attr("edit-id"),
        type:"PUT",
        data:$("#empUpdateModal form").serialize(),
        success:function (info) {
            alert(info.msg);
            $("#empUpdateModal").modal("hide");
            to_page(currentPage);
        }
    });
    /*post方式提交*/
    /*$.ajax({
        url:"/emp/"+$(this).attr("edit-id"),
        type:"POST",
        data:$("#empUpdateModal form").serialize()+"&_method=PUT",
        success:function (info) {
            alert(info.msg);
        }
    });*/
});


$(document).on("click", ".delete_btn", function () {
    var empName = $(this).parents("tr").find("td:eq(2)").text();
    var empId = $(this).attr("del-id");
    if (confirm("确认删除[" + empName + "]吗?")) {
        $.ajax({
            url:"/emp/"+empId,
            type:"DELETE",
            success:function (info) {
                alert(info.msg);
                /*回到本页*/
                to_page(currentPage);
            }
        })
    }
});

/*全选/全不选*/
$("#check_all").click(function () {
    /*alert($(this).prop("checked"));*/
    $(".check_item").prop("checked", $(this).prop("checked"))
});

$(document).on("click", ".check_item", function () {
    var flag = $(".check_item:checked").length == $(".check_item").length;
    $("#check_all").prop("checked", flag);
});

/*点击全部删除*/
$("#emp_delete_all_btn").click(function () {

    var empNames = "";
    var del_idstr = "";

    $.each($(".check_item:checked"), function () {
        empNames += $(this).parents("tr").find("td:eq(2)").text() + "|";
        del_idstr += $(this).parents("tr").find("td:eq(1)").text() + "-";
    });

    /*去除多余的逗号*/
    empNames = empNames.substring(0, empNames.length - 1);
    del_idstr = del_idstr.substring(0, del_idstr.length - 1);
    if (confirm("确认删除[" + empNames + "]吗?")) {
        $.ajax({
            url:"/emp/"+del_idstr,
            type:"DELETE",
            success:function (info) {
                alert(info.msg);
                to_page(currentPage);
            }
        });
    }
});