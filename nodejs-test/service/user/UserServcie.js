const UserDao = require('../../dao/user/UserDao');


var regist = (async(phone,nickName,birth)=>{
    var user = await UserDao.findUser(phone);
    if(user){
       console.log('user:' + JSON.stringify(user));
        return;
    }

    await UserDao.createUser(phone,nickName,birth);
});

module.exports = {'regist':regist
};