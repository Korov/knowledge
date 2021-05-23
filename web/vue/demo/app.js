const express = require('express')
const mongoose = require('mongoose')
mongoose.connect('mongodb://admin:admin@localhost:27017/admin')
const app = express()

const index = require('./router/index')
const keyCount = require('./router/keyCount')

app.use('/index', index)
app.use('/keyCount', keyCount)
app.use('/', (req, res) => {
    res.send('Yo!')
})

app.listen(4000, () => {
    console.log('app listening on port 3000.')
})