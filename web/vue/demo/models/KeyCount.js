const mongoose = require('mongoose')

const keyCountSchema = mongoose.Schema({
    id: {type: String, required: true},
    key: {type: String, required: true},
    value: {type: String},
    count: {type: Number}
})

const KeyCount = module.exports = mongoose.model('key-count', keyCountSchema);