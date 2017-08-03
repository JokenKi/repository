const Sequelize = require('sequelize');

const config = require('../config');

var sequelize = new Sequelize(config.database.dbname,config.database.username,config.database.password,{
    host:config.database.host,
    dialect:'mysql',
    pool:{
        max:20,
        min:1,
        idle:100000
    }
});

module.exports = sequelize;