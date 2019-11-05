import pymysql

# cursor不支持%d传参，整数也按照%s传
conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='root123', db='test')
cur = conn.cursor()
cur.execute("USE test")
# 增加
cur.execute("insert into test(`id`, `name`, `age`) VALUES (%s,%s,%s)", ('6', 'name3', '40'))
conn.commit()
# 更新
cur.execute("update test set name=%s where id=%s", ('name6', '6'))
conn.commit()
# 查询
cur.execute("SELECT * FROM test")
print(cur.fetchall())
# 删除
cur.execute("delete from test where id=%s", ('6'))
conn.commit()
cur.close()
conn.close()
