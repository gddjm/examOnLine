function student(id){
    $(function (){
        $('.header').load('/student-header.htm',function (){
            $('#'+id).addClass('active');
        });
    });
}
function teacher(id){
    $(function (){
        $('.header').load('/teacher-header.htm',function (){
            $('#'+id).addClass('active');
        });
    });
}
function admin(id){
    $(function (){
        $('.header').load('/admin-header.htm',function (){
            $('#'+id).addClass('active');
        });
    });
}