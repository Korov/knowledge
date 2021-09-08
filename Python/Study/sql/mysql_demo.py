import pymysql.cursors

connection = pymysql.connect(host='localhost',
                             user='test',
                             password='test',
                             database='backup',
                             cursorclass=pymysql.cursors.DictCursor)

# 打开变量之后会自动关闭
with connection:
    with connection.cursor() as cursor:
        # Create a new record
        sql = "insert into value_record(count, value_key, message, value_name, value_time) VALUES (%s,%s,%s,%s,%s);"
        cursor.execute(sql, (1,'aaa','aaa','aaa',123456))

    # connection is not autocommit by default. So you must commit to save
    # your changes.
    connection.commit()

    with connection.cursor() as cursor:
        # Read a single record
        sql = "SELECT count, value_key, message, value_name, value_time FROM `value_record` WHERE `count`=%s"
        cursor.execute(sql, (1))
        result = cursor.fetchone()
        print(result)