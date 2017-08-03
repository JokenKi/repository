const Sequelize = require('sequelize');
var sequelize = require('../sequelize');

var User = sequelize.define('user',{
    id:{
        type:Sequelize.BIGINT,
        primaryKey:true,
        autoIncrement: true
    },
    phone:{
        type:Sequelize.STRING(13),
    },
    nickName:{
        type:Sequelize.STRING(50),
        field:'nick_name'
    },
    password:{
        type:Sequelize.STRING(20)
    },
    createTime:{
        type:Sequelize.BIGINT,
        field:'create_time'
    },
    updateTime:{
        type:Sequelize.BIGINT,
        field:'update_time'
    }
}, {
        timestamps: false,
        freezeTableName:true
    }

);


var createUser = (async (phone,nickName,birth) => {
    var now = Date.now();
    var user = await User.create({
        phone: phone,
        nickName: nickName,
        birth: birth,
        password:'123456',
        createTime: now,
        updateTime: now,
    });
});

var findUser = (async(phone,nickName)=>{
    var params = {};
    if(phone){
        params.phone = phone;
    }
    if(nickName){
        params.nickName = nickName;
    }
    if(!params){
        return null;
    }
    return await User.findOne({'where':params});
});

module.exports = {'createUser':createUser,
    'findUser':findUser
};