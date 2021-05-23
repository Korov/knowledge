const express = require('express')
const mongoose = require('mongoose')
const router = express.Router()
mongoose.connect('mongodb://admin:admin@localhost:27017/admin')
const db = mongoose.connection
db.on('error', console.error.bind(console, 'MongoDB 连接错误：'))

const keyCountSchema = mongoose.Schema({
    id: {type: String, required: true},
    key: {type: String, required: true},
    value: {type: String},
    count: {type: Number}
})

const KeyCount = module.exports = mongoose.model('key-count', keyCountSchema);


// 查询所有电影
router.get('/all', (req, res) => {
    KeyCount.find({})
        .sort({ count : -1})
        .then(keyCounts => {
            res.json(keyCounts)
        })
        .catch(err => {
            res.json(err)
        })
})

module.exports = router