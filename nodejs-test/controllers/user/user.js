const userServcie = require('../../service/user/UserServcie');

var fn_getUsers = async(ctx,next)=>{
    console.log(JSON.stringify(ctx.request.query));
   userServcie.regist(ctx.request.query.phone,ctx.request.query.nickName,ctx.request.query.birth);
    ctx.response.body = '<h3>小小</h3>';
};

module.exports = {
    'GET*getUsers':fn_getUsers
};